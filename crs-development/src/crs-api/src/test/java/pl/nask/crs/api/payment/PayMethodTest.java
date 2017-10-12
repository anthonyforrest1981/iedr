package pl.nask.crs.api.payment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.api.authentication.CRSAuthenticationService;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.CreditCardVO;
import pl.nask.crs.api.vo.DomainWithPeriodVO;
import pl.nask.crs.api.vo.PaymentSummaryVO;
import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.ExtendedDomain;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dao.ExtendedDomainDAO;
import pl.nask.crs.domains.dsm.DomainStateMachine;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.search.ExtendedDomainSearchCriteria;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.dao.*;
import pl.nask.crs.payment.exceptions.DomainIncorrectStateForPaymentException;
import pl.nask.crs.payment.exceptions.DomainManagedByAnotherResellerException;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.NotEnoughDepositFundsException;
import pl.nask.crs.security.authentication.AuthenticationException;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import static pl.nask.crs.api.Helper.createBasicCreditCard;

@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
public class PayMethodTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    CRSPaymentAppService crsPaymentAppService;

    @Resource
    CRSAuthenticationService crsAuthenticationService;

    @Resource
    TransactionDAO transactionDAO;

    @Resource
    ReservationDAO reservationDAO;

    @Resource
    InvoiceDAO invoiceDAO;

    @Resource
    TransactionHistDAO transactionHistDAO;

    @Resource
    ReservationHistDAO reservationHistDAO;

    @Autowired
    DomainDAO domainDAO;

    @Autowired
    ExtendedDomainDAO extendedDomainDAO;

    @Autowired
    DomainStateMachine domainStateMachine;

    AuthenticatedUserVO user;

    private final static String userName = "APITEST-IEDR";

    private final static CreditCardVO creditCard = createBasicCreditCard();

    private static ReservationSearchCriteria readyCriteria;

    static {
        readyCriteria = new ReservationSearchCriteria();
        readyCriteria.setReadyForSettlement(true);
        readyCriteria.setSettled(false);
        readyCriteria.setBillingNH(userName);
    }

    @BeforeMethod
    public void authenticate() throws AuthenticationException, IncorrectUtf8FormatException {
        user = crsAuthenticationService.authenticate("APITEST-IEDR", "Passw0rd!", "1.1.1.1", null);
    }

    @Test
    public void payADPOkTest() throws Exception {
        List<Reservation> reservations = reservationDAO.getReservations(readyCriteria, 0, 10, null).getResults();
        assertEquals(5, reservations.size());
        List<Transaction> transactions = transactionDAO.getAll();
        assertEquals(14, transactions.size());

        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("payDomain.ie", 1),
                new DomainWithPeriodVO("payDomain2.ie", 1));
        PaymentSummaryVO paymentSummaryVO =
                crsPaymentAppService.payForDomainRenewal(user, domainsList, PaymentMethod.ADP, null);
        assertNotNull(paymentSummaryVO);
        assertEquals(MoneyUtils.getRoundedAndScaledValue(40), paymentSummaryVO.getAmount());
        assertEquals(MoneyUtils.getRoundedAndScaledValue(8), paymentSummaryVO.getVat());
        assertEquals(MoneyUtils.getRoundedAndScaledValue(48), paymentSummaryVO.getTotal());
        assertNotNull(paymentSummaryVO.getOrderId());

        reservations = reservationDAO.getReservations(readyCriteria, 0, 10, null).getResults();
        assertEquals(7, reservations.size());
        transactions = transactionDAO.getAll();
        assertEquals(15, transactions.size());
        Reservation reservation = reservationDAO.getReservations(getCriteriaForDomain("payDomain.ie"), 0, 1, null)
                .getResults().get(0);
        assertTrue(reservation.isReadyForSettlement());
        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        assertNotNull(transaction.getFinanciallyPassedDate());
    }

    @Test
    public void payAdpAllDomainsPerformanceTest() throws Exception {
        StopWatch watch = new StopWatch();
        ExtendedDomainSearchCriteria domainSearchCriteria = new ExtendedDomainSearchCriteria();
        domainSearchCriteria.setAccountId(666L);
        domainSearchCriteria.setNrpStatuses(NRPStatus.Active);
        domainSearchCriteria.setDomainHolderTypes(DomainHolderType.Billable);
        List<ExtendedDomain> domains = extendedDomainDAO.find(domainSearchCriteria, null).getResults();
        List<DomainWithPeriodVO> domainsWithPeriod = domainsWithPeriod(domains, 1);
        watch.start();
        crsPaymentAppService.payForDomainRenewal(user, domainsWithPeriod, PaymentMethod.ADP, null);
        watch.stop();
        System.err.println("pay notest" + watch.getTime());
    }

    private List<DomainWithPeriodVO> domainsWithPeriod(List<ExtendedDomain> domains, int period) {
        List<DomainWithPeriodVO> ret = new ArrayList<DomainWithPeriodVO>(domains.size());
        int invalidDomainCount = 0;
        for (ExtendedDomain domain : domains) {
            ReservationSearchCriteria reservationForDomain = new ReservationSearchCriteria();
            reservationForDomain.setDomainName(domain.getName());
            if (reservationDAO.exists(reservationForDomain) || !domainStateMachine
                    .validateEvent(domain.getName(), DsmEventName.PaymentInitiated)) {
                invalidDomainCount++;
                System.err.println("Invalid domain: " + domain);
            } else {
                ret.add(new DomainWithPeriodVO(domain.getName(), period));
            }
        }
        System.err.println("Got " + invalidDomainCount + " invalid domains");
        return ret;
    }

    @Test
    public void payADPOkWithDifferentPeriodsTest() throws Exception {
        List<Reservation> reservations = reservationDAO.getReservations(readyCriteria, 0, 10, null).getResults();
        assertEquals(5, reservations.size());
        List<Transaction> transactions = transactionDAO.getAll();
        assertEquals(14, transactions.size());

        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("payDomain.ie", 1),
                new DomainWithPeriodVO("payDomain2.ie", 2));
        PaymentSummaryVO paymentSummaryVO =
                crsPaymentAppService.payForDomainRenewal(user, domainsList, PaymentMethod.ADP, null);
        assertNotNull(paymentSummaryVO);
        assertEquals(MoneyUtils.getRoundedAndScaledValue(60), paymentSummaryVO.getAmount());
        assertEquals(MoneyUtils.getRoundedAndScaledValue(12), paymentSummaryVO.getVat());
        assertEquals(MoneyUtils.getRoundedAndScaledValue(72), paymentSummaryVO.getTotal());
        assertNotNull(paymentSummaryVO.getOrderId());

        reservations = reservationDAO.getReservations(readyCriteria, 0, 10, null).getResults();
        assertEquals(7, reservations.size());
        transactions = transactionDAO.getAll();
        assertEquals(15, transactions.size());
        Reservation reservation = reservationDAO.getReservations(getCriteriaForDomain("payDomain.ie"), 0, 1, null)
                .getResults().get(0);
        assertTrue(reservation.isReadyForSettlement());
        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        assertNotNull(transaction.getFinanciallyPassedDate());
    }

    private ReservationSearchCriteria getCriteriaForDomain(String domainName) {
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(true);
        criteria.setSettled(false);
        criteria.setDomainName(domainName);
        return criteria;
    }

    @Test(expectedExceptions = DomainNotFoundException.class)
    public void payADPNotExistingDomainTest() throws Exception {
        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("notExisting.ie", 1));
        crsPaymentAppService.payForDomainRenewal(user, domainsList, PaymentMethod.ADP, null);
    }

    @Test(expectedExceptions = DomainManagedByAnotherResellerException.class)
    public void payADPDomainManagedByAnotherResellerTest() throws Exception {
        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("payDomain.ie", 1));
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("SWD2-IEDR", "Passw0rd!", "1.1.1.1", null);
        crsPaymentAppService.payForDomainRenewal(user, domainsList, PaymentMethod.ADP, null);
    }

    @Test(expectedExceptions = DomainIncorrectStateForPaymentException.class)
    public void payADPDomainInInvalidStateTest() throws Exception {
        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("pizzaonline2.ie", 1));
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("IDL2-IEDR", "Passw0rd!", "1.1.1.1", null);
        crsPaymentAppService.payForDomainRenewal(user, domainsList, PaymentMethod.ADP, null);
    }

    @Test(expectedExceptions = NotAdmissiblePeriodException.class)
    public void payADPNotAdmissiblePeriodTest() throws Exception {
        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("payDomain.ie", 12));
        crsPaymentAppService.payForDomainRenewal(user, domainsList, PaymentMethod.ADP, null);
    }

    @Test(expectedExceptions = NotEnoughDepositFundsException.class)
    public void payAPDNotEnoughFundsTest() throws Exception {
        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("payDomain3.ie", 1));
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("SWD2-IEDR", "Passw0rd!", "1.1.1.1", null);
        crsPaymentAppService.payForDomainRenewal(user, domainsList, PaymentMethod.ADP, null);
    }

    @Test
    public void payCCOkTest() throws Exception {
        List<Reservation> reservations = reservationDAO.getReservations(readyCriteria, 0, 10, null).getResults();
        assertEquals(5, reservations.size());
        List<Transaction> transactions = transactionDAO.getAll();
        assertEquals(14, transactions.size());

        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("payDomain.ie", 1),
                new DomainWithPeriodVO("payDomain2.ie", 1));
        PaymentSummaryVO paymentSummaryVO =
                crsPaymentAppService.payForDomainRenewal(user, domainsList, PaymentMethod.CC, creditCard);
        assertNotNull(paymentSummaryVO);
        assertEquals(MoneyUtils.getRoundedAndScaledValue(40), paymentSummaryVO.getAmount());
        assertEquals(MoneyUtils.getRoundedAndScaledValue(8), paymentSummaryVO.getVat());
        assertEquals(MoneyUtils.getRoundedAndScaledValue(48), paymentSummaryVO.getTotal());
        assertNotNull(paymentSummaryVO.getOrderId());

        reservations = reservationDAO.getReservations(readyCriteria, 0, 10, null).getResults();
        assertEquals(7, reservations.size());
        transactions = transactionDAO.getAll();
        assertEquals(15, transactions.size());
        Reservation reservation = reservationDAO.getReservations(getCriteriaForDomain("payDomain.ie"), 0, 1, null)
                .getResults().get(0);
        assertTrue(reservation.isReadyForSettlement());
        Transaction transaction = transactionDAO.get(reservation.getTransactionId());
        assertNotNull(transaction.getFinanciallyPassedDate());
    }

    @Test(expectedExceptions = DomainNotFoundException.class)
    public void payCCNotExistingDomainTest() throws Exception {
        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("notExisting.ie", 1));
        crsPaymentAppService.payForDomainRenewal(user, domainsList, PaymentMethod.CC, creditCard);
    }

    @Test(expectedExceptions = DomainManagedByAnotherResellerException.class)
    public void payCCDomainManagedByAnotherResellerTest() throws Exception {
        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("payDomain.ie", 1));
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("SWD2-IEDR", "Passw0rd!", "1.1.1.1", null);
        crsPaymentAppService.payForDomainRenewal(user, domainsList, PaymentMethod.CC, creditCard);
    }

    @Test(expectedExceptions = DomainIncorrectStateForPaymentException.class)
    public void payCCDomainInInvalidStateTest() throws Exception {
        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("pizzaonline2.ie", 1));
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("IDL2-IEDR", "Passw0rd!", "1.1.1.1", null);
        crsPaymentAppService.payForDomainRenewal(user, domainsList, PaymentMethod.CC, creditCard);
    }

    @Test(expectedExceptions = NotAdmissiblePeriodException.class)
    public void payCCNotAdmissiblePeriodTest() throws Exception {
        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("payDomain.ie", 12));
        crsPaymentAppService.payForDomainRenewal(user, domainsList, PaymentMethod.CC, creditCard);
    }

    @Test
    public void payDirectVatTest() throws Exception {
        AuthenticatedUserVO user = crsAuthenticationService.authenticate("AAU809-IEDR", "Passw0rd!", "1.1.1.1", null);
        List<Reservation> reservations = reservationDAO.getReservations(readyCriteria, 0, 10, null).getResults();
        assertEquals(5, reservations.size());
        List<Transaction> transactions = transactionDAO.getAll();
        assertEquals(14, transactions.size());

        List<DomainWithPeriodVO> domainsList = Arrays.asList(new DomainWithPeriodVO("directDomain.ie", 2));
        PaymentSummaryVO paymentSummaryVO =
                crsPaymentAppService.payForDomainRenewal(user, domainsList, PaymentMethod.CC, creditCard);
        assertNotNull(paymentSummaryVO);
        assertEquals(MoneyUtils.getRoundedAndScaledValue(184), paymentSummaryVO.getAmount());
        assertEquals(MoneyUtils.getRoundedAndScaledValue(174.8), paymentSummaryVO.getVat());
        assertEquals(MoneyUtils.getRoundedAndScaledValue(358.8), paymentSummaryVO.getTotal());
        assertNotNull(paymentSummaryVO.getOrderId());
    }
}
