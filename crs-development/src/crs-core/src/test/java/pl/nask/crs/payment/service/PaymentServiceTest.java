package pl.nask.crs.payment.service;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.impl.EmailTemplateSenderImpl;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.exceptions.ReservationPendingException;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.dao.*;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.exceptions.TransactionInvalidStateForSettlement;
import pl.nask.crs.payment.service.impl.TransactionCancelStrategy;
import pl.nask.crs.payment.testhelp.PaymentTestHelp;
import pl.nask.crs.price.dao.DomainPricingDAO;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.WsAuthenticationService;
import pl.nask.crs.vat.ProductPriceWithVat;
import pl.nask.crs.vat.Vat;

import mockit.Mocked;
import mockit.NonStrictExpectations;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class PaymentServiceTest extends AbstractContextAwareTest {

    @Resource
    ReservationDAO reservationDAO;

    @Resource
    ReservationHistDAO reservationHistDAO;

    @Resource
    PaymentService paymentService;

    @Resource
    PaymentSearchService paymentSearchService;

    @Resource
    InvoicingService invoicingService;

    @Resource
    TransactionDAO transactionDAO;

    @Resource
    TransactionHistDAO transactionHistDAO;

    @Resource
    DepositService depositService;

    @Autowired
    WsAuthenticationService wsAuthenticationService;

    @Resource
    InvoiceDAO invoiceDAO;

    @Resource
    DomainDAO domainDAO;

    @Resource
    DomainPricingDAO domainPricingDAO;

    @Mocked
    EmailTemplateSenderImpl emailTemplateSender;

    @Test
    public void getReservationForTicketIdTest() {
        long transactionId = transactionDAO.createTransaction(Transaction.newInstance(
                MoneyUtils.getRoundedAndScaledValue(50), MoneyUtils.getRoundedAndScaledValue(40),
                MoneyUtils.getRoundedAndScaledValue(10), "order id", null, null, false));
        Reservation reservation1 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain2.ie", 1,
                new ProductPriceWithVat(Period.fromYears(1), 13, MoneyUtils.getRoundedAndScaledValue(45.5),
                        new Vat(4, "B", new Date(), MoneyUtils.getScaledVatValue(0.09))), 1234L, transactionId,
                OperationType.REGISTRATION, PaymentMethod.CC);
        reservationDAO.createReservation(reservation1);
        Reservation res = paymentSearchService.getReservationForTicket(1234L);
        AssertJUnit.assertNotNull("reservation", res);
        AssertJUnit.assertEquals("PaymentMethod", PaymentMethod.CC, res.getPaymentMethod());
        AssertJUnit.assertEquals(transactionId, (long) res.getTransactionId());
    }

    @Test(expectedExceptions = TransactionInvalidStateForSettlement.class)
    public void settleTransactionInWrongStateTest() throws Exception {
        paymentService.settleTransaction(null, TestOpInfo.DEFAULT, 6);
    }

    @Test
    public void settleTransactionTest() throws Exception {
        long transactionId = 6;
        long reservationId = 1;
        String domainName = "createdomainregistrarbasic.ie";

        Transaction transaction = transactionDAO.get(transactionId);
        AssertJUnit.assertFalse(transaction.isSettlementEnded());

        Reservation reservation = reservationDAO.get(reservationId);
        AssertJUnit.assertFalse(reservation.isSettled());
        AssertJUnit.assertNull(reservation.getStartDate());
        AssertJUnit.assertNull(reservation.getEndDate());

        Date renewalDateBeforeSettlement = domainDAO.get(domainName).getRenewalDate();

        ExtendedDeposit depositBeforeSettlement = depositService.viewDeposit("APITEST-IEDR");

        paymentService.setTransactionStartedSettlement(transactionId);
        paymentService.settleTransaction(null, TestOpInfo.DEFAULT, transactionId);

        transaction = transactionDAO.get(transactionId);
        AssertJUnit.assertTrue(transaction.isSettlementEnded());

        reservation = reservationDAO.get(reservationId);
        AssertJUnit.assertTrue(reservation.isSettled());

        Date renewalDateAfterSettlement = domainDAO.get(domainName).getRenewalDate();
        AssertJUnit.assertEquals(DateUtils.addMonths(renewalDateBeforeSettlement, reservation.getDurationMonths()),
                renewalDateAfterSettlement);

        AssertJUnit.assertEquals(renewalDateBeforeSettlement, reservation.getStartDate());
        AssertJUnit.assertEquals(renewalDateAfterSettlement, reservation.getEndDate());

        ExtendedDeposit depositAfterSettlement = depositService.viewDeposit("APITEST-IEDR");

        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(206.38), depositBeforeSettlement.getReservedFunds());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(2594), depositBeforeSettlement.getCloseBal());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(127.47), depositAfterSettlement.getReservedFunds());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(2515.09), depositAfterSettlement.getCloseBal());
        AssertJUnit.assertEquals(MoneyUtils.subtract(depositBeforeSettlement.getCloseBal(), MoneyUtils.subtract(
                        depositBeforeSettlement.getReservedFunds(), depositAfterSettlement.getReservedFunds())),
                depositAfterSettlement.getCloseBal());
    }

    @Test
    public void cardTransactionSettlementErrorClientsFault(@Mocked final CardPaymentServiceMock cardService)
            throws Exception {
        new NonStrictExpectations() {
            {
                cardService.settleRealexAuthorisation(withInstanceOf(RealexTransactionIdentifier.class));
                result = new CardPaymentException("error", CardPaymentException.Type.FAILED_TRANSACTION);
            }
        };
        testCardTransactionSettlementError(true);
    }

    @Test
    public void cardTransactionSettlementErrorNotClientsFault(@Mocked final CardPaymentServiceMock cardService)
            throws Exception {
        new NonStrictExpectations() {
            {
                cardService.settleRealexAuthorisation(withInstanceOf(RealexTransactionIdentifier.class));
                result = new CardPaymentException("error", CardPaymentException.Type.DELIVERY);
            }
        };
        testCardTransactionSettlementError(false);
    }

    @Test
    public void cardTransactionCancellationErrorDuringSettlement(@Mocked final CardPaymentServiceMock cardService)
            throws Exception {
        new NonStrictExpectations() {
            {
                cardService.settleRealexAuthorisation(withInstanceOf(RealexTransactionIdentifier.class));
                result = new CardPaymentException("error", CardPaymentException.Type.FAILED_TRANSACTION);
                cardService.cancelRealexAuthorisation(withInstanceOf(RealexTransactionIdentifier.class));
                result = new CardPaymentException("error", CardPaymentException.Type.FAILED_TRANSACTION);
            }
        };
        testCardTransactionSettlementError(true);
    }

    private void testCardTransactionSettlementError(boolean clientsFault) throws Exception {
        new NonStrictExpectations() {
            {
                emailTemplateSender.sendEmail(EmailTemplateNamesEnum.INVOICE_FAILURE_CC.getId(),
                        withInstanceOf(EmailParameters.class));
                times = 1;
            }
        };
        long transactionId = 103;
        Transaction transaction = transactionDAO.get(transactionId);
        Reservation reservation = transaction.getReservations().get(0);
        Domain domain = domainDAO.get(reservation.getDomainName());
        assertEquals(domain.getDsmState().getNrpStatus(), NRPStatus.InvoluntaryMailedPaymentPending);
        paymentService.settleTransaction(getUser("APITEST-IEDR"), TestOpInfo.DEFAULT, transactionId);
        domain = domainDAO.get(domain.getName());
        if (clientsFault) {
            assertEquals(domain.getDsmState().getNrpStatus(), NRPStatus.InvoluntaryMailed);
            assertNull(transactionDAO.get(transactionId));
        } else {
            assertEquals(domain.getDsmState().getNrpStatus(), NRPStatus.InvoluntaryMailedPaymentPending);
            assertNotNull(transactionDAO.get(transactionId));
        }
    }

    @Test
    public void cancelTransactionTest() throws Exception {
        long transactionId = 103;
        Transaction transaction = transactionDAO.get(transactionId);
        Reservation reservation = transaction.getReservations().get(0);
        Domain domain = domainDAO.get(reservation.getDomainName());
        assertEquals(domain.getDsmState().getNrpStatus(), NRPStatus.InvoluntaryMailedPaymentPending);

        TransactionCancelStrategy cancelStrategy = new TransactionCancelStrategy() {
            @Override
            public void handleRealexError(Transaction transaction, CardPaymentException e) {}

            @Override
            public boolean shouldPurgeTransaction() {
                return true;
            }
        };
        paymentService.cancelTransaction(getUser("APITEST-IEDR"), transactionId, cancelStrategy);

        domain = domainDAO.get(domain.getName());
        assertEquals(domain.getDsmState().getNrpStatus(), NRPStatus.InvoluntaryMailed);
        assertNull(transactionDAO.get(transactionId));
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void invalidateTransactionInvalidStateTest() throws Exception {
        int transactionId = 1;
        paymentService.invalidateTransactionsIfNeeded(null, transactionId);
    }

    //TODO: CRS-72
    //    @Test
    //    public void invalidateTransactionTest() throws Exception {
    //        long transactionId = 3;
    //
    //        Transaction transactionBefore = transactionDAO.get(transactionId);
    //        AssertJUnit.assertFalse(transactionBefore.isInvalidated());
    //        Assert.assertNull(transactionBefore.getInvalidatedDate());
    //
    //        paymentService.invalidateTransactionsIfNeeded(transactionId);
    //
    //        Transaction transactionAfter = transactionDAO.get(transactionId);
    //        AssertJUnit.assertTrue(transactionAfter.isInvalidated());
    //        Assert.assertNotNull(transactionAfter.getInvalidatedDate());
    //    }

    @Test
    public void getReadyTransactionsReportTest() throws Exception {
        List<TransactionInfo> reports = paymentSearchService.getReadyADPTransactionsReport("APITEST-IEDR");
        AssertJUnit.assertEquals(3, reports.size());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(2545.44),
                reports.get(0).getAvailableDepositBalance());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(2466.53),
                reports.get(1).getAvailableDepositBalance());
    }

    @Test
    public void findDepositsTest() {
        DepositSearchCriteria criteria = new DepositSearchCriteria();
        criteria.setNicHandleId("APITEST-IEDR");
        LimitedSearchResult<ExtendedDeposit> searchResult = depositService.findDeposits(criteria, 0, 10, null);
        AssertJUnit.assertEquals(1, searchResult.getTotalResults());
        AssertJUnit.assertEquals(1, searchResult.getResults().size());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(206.38),
                searchResult.getResults().get(0).getReservedFunds());
    }

    @Test
    public void testCreateInvoiceAndAssociateWithTransactions() throws Exception {
        String nicHandleId = "IDL2-IEDR";
        Long transactionId = 21L;
        Transaction transaction = transactionDAO.get(transactionId);
        List<Reservation> reservations = transaction.getReservations();
        assertEquals(reservations.size(), 1);
        List<Transaction> transactions = Arrays.asList(transaction);
        int invoiceId = invoicingService.createInvoiceAndAssociateWithTransactions(nicHandleId, transactions);
        assertNotNull(invoiceDAO.get(invoiceId));
        assertNull(transactionDAO.get(transactionId));
        Transaction histTransaction = transactionHistDAO.get(transactionId);
        assertNotNull(histTransaction);
        assertEquals(histTransaction.getInvoiceId().intValue(), invoiceId);
        for (Reservation r : reservations) {
            assertNull(reservationDAO.get(r.getId()));
            assertNotNull(reservationHistDAO.get(r.getId()));
        }
    }

    @Test
    public void testPayADP() throws Exception {
        AuthenticatedUser user = getUser("APITEST-IEDR");
        testPayment(user, true);
    }

    @Test
    public void testPayCC() throws Exception {
        AuthenticatedUser user = getUser("APITEST-IEDR");
        testPayment(user, false);
    }

    @Test
    public void testPayADPAsSuperUser() throws Exception {
        AuthenticatedUser user = getUserWithSuperUser("APITEST-IEDR");
        testPayment(user, true);
    }

    @Test
    public void testPayCCAsSuperUser() throws Exception {
        AuthenticatedUser user = getUserWithSuperUser("APITEST-IEDR");
        testPayment(user, false);
    }

    @Test
    public void testPaymentForRenewalPendingReservationsAllowed() throws Exception {
        final String userName = "APITEST-IEDR";
        final String domainName = "paydomain.ie";
        final AuthenticatedUser user = getUser(userName);
        final OpInfo opInfo = new OpInfo(user);
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setDomainName(domainName);

        createReservation(domainName, OperationType.RENEWAL, null);
        assertEquals(reservationDAO.getAllReservations(criteria).size(), 1);

        Map<String, Period> domainsWithPeriods = new HashMap<>();
        domainsWithPeriods.put(domainName, Period.fromYears(1));
        paymentService.authorizePaymentForRenewal(user, opInfo, domainsWithPeriods, PaymentMethod.ADP, null, true);
        assertEquals(reservationDAO.getAllReservations(criteria).size(), 2);
    }

    @Test(expectedExceptions = ReservationPendingException.class)
    public void testPaymentForRenewalPendingReservationsNotAllowed() throws Exception {
        final String userName = "APITEST-IEDR";
        final String domainName = "paydomain.ie";
        final AuthenticatedUser user = getUser(userName);
        final OpInfo opInfo = new OpInfo(user);
        createReservation(domainName, OperationType.RENEWAL, null);
        Map<String, Period> domainsWithPeriods = new HashMap<>();
        domainsWithPeriods.put(domainName, Period.fromYears(1));
        paymentService.authorizePaymentForRenewal(user, opInfo, domainsWithPeriods, PaymentMethod.ADP, null, false);
    }


    @Test(expectedExceptions = ReservationPendingException.class)
    public void testPaymentForRenewalPendingReservationsRenewalAllowed() throws Exception {
        final String userName = "APITEST-IEDR";
        final String domainName = "paydomain.ie";
        final AuthenticatedUser user = getUser(userName);
        final OpInfo opInfo = new OpInfo(user);
        createReservation(domainName, OperationType.TRANSFER, null);
        Map<String, Period> domainsWithPeriods = new HashMap<>();
        domainsWithPeriods.put(domainName, Period.fromYears(1));
        paymentService.authorizePaymentForRenewal(user, opInfo, domainsWithPeriods, PaymentMethod.ADP, null, true);
    }

    private void createReservation(String domainName, OperationType operationType, Long ticketId) {
        ProductPriceWithVat price = new ProductPriceWithVat(Period.fromYears(1),
                domainPricingDAO.getDomainPriceByCode("Std1Year", new Date()).getId(),
                MoneyUtils.getRoundedAndScaledValue(65),
                new Vat(1, "A", new Date(), MoneyUtils.getScaledVatValue(0.19)));
        long transactionId = transactionDAO.createTransaction(Transaction.newInstance(
                MoneyUtils.getRoundedAndScaledValue(50), MoneyUtils.getRoundedAndScaledValue(40),
                MoneyUtils.getRoundedAndScaledValue(10), "order id", "authcode", "pasref", false));
        reservationDAO.createReservation(new Reservation(-1, "APITEST-IEDR", domainName, 12, new Date(), price, true, false, null, ticketId, transactionId,
                operationType, PaymentMethod.ADP, null, null, null, ticketId != null));
    }

    private void testPayment(AuthenticatedUser user, boolean isAdpPayment) throws Exception {
        OpInfo opInfo = new OpInfo(user);
        String domainName = "paydomain.ie";
        Map<String, Period> domainsWithPeriods = new HashMap<>();
        domainsWithPeriods.put(domainName, Period.fromYears(1));
        PaymentSummary summary;
        if (isAdpPayment) {
            summary = paymentService
                    .authorizePaymentForRenewal(user, opInfo, domainsWithPeriods, PaymentMethod.ADP, null, true);
        } else {
            CreditCard creditCard = PaymentTestHelp.createBasicCreditCard();
            summary = paymentService
                    .authorizePaymentForRenewal(user, opInfo, domainsWithPeriods, PaymentMethod.CC, creditCard, true);
        }
        confirmReservationAndTransaction(domainName, domainsWithPeriods, summary);
    }

    private void confirmReservationAndTransaction(String domainName, Map<String, Period> domainsWithPeriods,
            PaymentSummary summary) throws Exception {
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setDomainName(domainName);
        List<SortCriterion> sortBy = Arrays.asList(new SortCriterion("creationDate", false));
        LimitedSearchResult<Reservation> reservations = reservationDAO.getReservations(criteria, 0, 1, sortBy);
        assertEquals(reservations.getResults().size(), 1);
        Reservation reservation = reservations.getResults().get(0);
        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        assertNotNull(transaction);
        assertEquals(reservation.getOrderId(), summary.getOrderId());
        assertEquals(DateUtils.truncate(reservation.getCreationDate(), Calendar.DATE),
                DateUtils.truncate(new Date(), Calendar.DATE));
        assertEquals(transaction.getOrderId(), summary.getOrderId());

        assertEquals(summary.getPaymentDomains().size(), 1);
        PaymentDomain paymentDomain = summary.getPaymentDomains().get(0);
        assertEquals(paymentDomain.getDomainName(), domainName);
        Period period = domainsWithPeriods.get(domainName);
        assertEquals(paymentDomain.getPeriodInYears(), period.getYears());
    }

    private AuthenticatedUser getUser(String userName) throws Exception {
        return wsAuthenticationService.authenticate(userName, "Passw0rd!", false, "127.0.0.1", false, null, false,
                "ws");
    }

    private AuthenticatedUser getUserWithSuperUser(String userName) throws Exception {
        String superUserName = "IDL2-IEDR";
        return wsAuthenticationService.authenticateAndSwitchUser(superUserName, userName, "Passw0rd!", false,
                "127.0.0.1", false);
    }

}
