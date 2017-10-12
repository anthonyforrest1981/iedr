package pl.nask.crs.payment.dao;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.payment.*;
import pl.nask.crs.price.dao.DomainPricingDAO;
import pl.nask.crs.vat.ProductPriceWithVat;
import pl.nask.crs.vat.Vat;

import static pl.nask.crs.payment.testhelp.PaymentTestHelp.compareNewReadyForSettlement;
import static pl.nask.crs.payment.testhelp.PaymentTestHelp.compareNewReservation;
import static pl.nask.crs.payment.testhelp.PaymentTestHelp.compareSettledReservation;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ReservationDAOTest extends AbstractContextAwareTest {

    @Resource
    DomainPricingDAO domainPricingDAO;

    @Resource
    ReservationDAO reservationDAO;

    @Resource
    TransactionDAO transactionDAO;

    private long transactionId;
    private int productId;

    @BeforeMethod
    public void initTransaction() {
        transactionId = transactionDAO.createTransaction(Transaction.newInstance(null, null, true, false,
                MoneyUtils.getRoundedAndScaledValue(50), MoneyUtils.getRoundedAndScaledValue(40),
                MoneyUtils.getRoundedAndScaledValue(10), "order id", null));
        productId = domainPricingDAO.getDomainPriceByCode("Std1Year", new Date()).getId();
    }

    @Test
    public void instertReservationTest() {
        final Date aDate = DateUtils.setMilliseconds(new Date(), 999);
        Reservation reservation1 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain1.ie", 1,
                new ProductPriceWithVat(Period.fromYears(1), productId, MoneyUtils.getRoundedAndScaledValue(65),
                        new Vat(1, "A", aDate, MoneyUtils.getScaledVatValue(0.19))), 1234L,
                transactionId, OperationType.REGISTRATION, PaymentMethod.ADP);
        Reservation reservation2 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain2.ie", 1,
                new ProductPriceWithVat(Period.fromYears(1), productId, MoneyUtils.getRoundedAndScaledValue(45.5),
                        new Vat(4, "B", aDate, MoneyUtils.getScaledVatValue(0.09))), 1234L,
                transactionId, OperationType.REGISTRATION, PaymentMethod.ADP);
        Reservation reservation3 = Reservation.newInstanceForTicket("Test2-IEDR", "testDomain3.ie", 1,
                new ProductPriceWithVat(Period.fromYears(1), productId, MoneyUtils.getRoundedAndScaledValue(35.5),
                        new Vat(9, "C", aDate, MoneyUtils.getScaledVatValue(0.316))), 1234L,
                transactionId, OperationType.REGISTRATION, PaymentMethod.ADP);
        long r1 = reservationDAO.createReservation(reservation1);
        long r2 = reservationDAO.createReservation(reservation2);
        long r3 = reservationDAO.createReservation(reservation3);
        AssertJUnit.assertTrue(r1 < r2);
        AssertJUnit.assertTrue(r2 < r3);
        List<Reservation> reservationsFromDB1 = getAllReservationsForBillingNH("Test1-IEDR");
        AssertJUnit.assertEquals(2, reservationsFromDB1.size());
        compareNewReservation(reservationsFromDB1.get(0), reservation1);
        compareNewReservation(reservationsFromDB1.get(1), reservation2);

        List<Reservation> reservationsFromDB2 = getAllReservationsForBillingNH("Test2-IEDR");
        AssertJUnit.assertEquals(1, reservationsFromDB2.size());
        compareNewReservation(reservationsFromDB2.get(0), reservation3);
    }

    private List<Reservation> getAllReservationsForBillingNH(String billingNH) {
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setBillingNH(billingNH);
        return reservationDAO.getAllReservations(criteria);
    }

    @Test
    public void insertReservationTest() {
        Reservation reservation1 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain1.ie", 1,
                new ProductPriceWithVat(Period.fromYears(1), productId, MoneyUtils.getRoundedAndScaledValue(65),
                        new Vat(1, "A", new Date(), MoneyUtils.getScaledVatValue(0.19))), 1234L, transactionId,
                OperationType.REGISTRATION, PaymentMethod.CC);
        Reservation reservation2 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain2.ie", 1,
                new ProductPriceWithVat(Period.fromYears(1), productId, MoneyUtils.getRoundedAndScaledValue(45.5),
                        new Vat(4, "B", new Date(), MoneyUtils.getScaledVatValue(0.09))), 1234L, transactionId,
                OperationType.REGISTRATION, PaymentMethod.CC);
        Reservation reservation3 = Reservation.newInstanceForTicket("Test2-IEDR", "testDomain3.ie", 1,
                new ProductPriceWithVat(Period.fromYears(1), productId, MoneyUtils.getRoundedAndScaledValue(35.5),
                        new Vat(9, "C", new Date(), MoneyUtils.getScaledVatValue(0.316))), 1234L, transactionId,
                OperationType.REGISTRATION, PaymentMethod.CC);
        reservationDAO.createReservation(reservation1);
        reservationDAO.createReservation(reservation2);
        reservationDAO.createReservation(reservation3);

        List<Reservation> reservationsFromDB1 = getAllReservationsForBillingNH("Test1-IEDR");
        AssertJUnit.assertEquals(2, reservationsFromDB1.size());
        compareNewReservation(reservationsFromDB1.get(0), reservation1);
        compareNewReservation(reservationsFromDB1.get(1), reservation2);

        List<Reservation> reservationsFromDB2 = getAllReservationsForBillingNH("Test2-IEDR");
        AssertJUnit.assertEquals(1, reservationsFromDB2.size());
        compareNewReservation(reservationsFromDB2.get(0), reservation3);
    }

    @Test
    public void settleReservationTest() {
        Reservation reservation1 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain1.ie", 1,
                new ProductPriceWithVat(Period.fromYears(1), productId, MoneyUtils.getRoundedAndScaledValue(55.5),
                        new Vat(1, "A", new Date(), MoneyUtils.getScaledVatValue(0.19))), 1234L, transactionId,
                OperationType.REGISTRATION, PaymentMethod.CC);
        Reservation reservation2 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain2.ie", 1,
                new ProductPriceWithVat(Period.fromYears(1), productId, MoneyUtils.getRoundedAndScaledValue(45.5),
                        new Vat(4, "B", new Date(), MoneyUtils.getScaledVatValue(0.09))), 1234L, transactionId,
                OperationType.REGISTRATION, PaymentMethod.CC);
        reservationDAO.createReservation(reservation1);
        reservationDAO.createReservation(reservation2);

        List<Reservation> reservationsFromDB = getAllReservationsForBillingNH("Test1-IEDR");
        AssertJUnit.assertEquals(2, reservationsFromDB.size());
        compareNewReservation(reservationsFromDB.get(0), reservation1);
        compareNewReservation(reservationsFromDB.get(1), reservation2);

        Date settledDate = DateUtils.setMilliseconds(new Date(), 999);
        reservation1 = reservationsFromDB.get(0);
        reservation2 = reservationsFromDB.get(1);
        reservation1.markSettled(settledDate);
        reservation2.markSettled(settledDate);
        reservationDAO.update(reservation1);
        reservationDAO.update(reservation2);

        reservationsFromDB = getAllReservationsForBillingNH("Test1-IEDR");
        AssertJUnit.assertEquals(2, reservationsFromDB.size());
        compareSettledReservation(reservationsFromDB.get(0), reservation1);
        compareSettledReservation(reservationsFromDB.get(1), reservation2);
    }

    @Test
    public void setReadyForSettlementTest() {
        Reservation reservation1 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain1.ie", 1,
                new ProductPriceWithVat(Period.fromYears(1), productId, MoneyUtils.getRoundedAndScaledValue(55.5),
                        new Vat(1, "A", new Date(), MoneyUtils.getScaledVatValue(0.19))), 1234L, transactionId,
                OperationType.REGISTRATION, PaymentMethod.CC);
        Reservation reservation2 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain2.ie", 1,
                new ProductPriceWithVat(Period.fromYears(1), productId, MoneyUtils.getRoundedAndScaledValue(45.5),
                        new Vat(4, "B", new Date(), MoneyUtils.getScaledVatValue(0.09))), 1234L, transactionId,
                OperationType.REGISTRATION, PaymentMethod.CC);
        reservationDAO.createReservation(reservation1);
        reservationDAO.createReservation(reservation2);

        List<Reservation> reservationsFromDB = getAllReservationsForBillingNH("Test1-IEDR");
        AssertJUnit.assertEquals(2, reservationsFromDB.size());
        compareNewReservation(reservationsFromDB.get(0), reservation1);
        compareNewReservation(reservationsFromDB.get(1), reservation2);

        reservation1 = (Reservation) reservationsFromDB.get(0);
        reservation2 = (Reservation) reservationsFromDB.get(1);
        reservation1.setReadyForSettlement(true);
        reservation2.setReadyForSettlement(true);
        reservationDAO.update(reservation1);
        reservationDAO.update(reservation2);

        reservationsFromDB = getAllReservationsForBillingNH("Test1-IEDR");
        AssertJUnit.assertEquals(2, reservationsFromDB.size());
        compareNewReadyForSettlement(reservationsFromDB.get(0), reservation1);
        compareNewReadyForSettlement(reservationsFromDB.get(1), reservation2);
    }

    @Test
    public void deleteReservationTest() {
        Reservation reservation1 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain1.ie", 1,
                new ProductPriceWithVat(Period.fromYears(1), productId, MoneyUtils.getRoundedAndScaledValue(55.5),
                        new Vat(1, "A", new Date(), MoneyUtils.getScaledVatValue(0.19))), 1234L, transactionId,
                OperationType.REGISTRATION, PaymentMethod.CC);
        Reservation reservation2 = Reservation.newInstanceForTicket("Test1-IEDR", "testDomain2.ie", 1,
                new ProductPriceWithVat(Period.fromYears(1), productId, MoneyUtils.getRoundedAndScaledValue(45.5),
                        new Vat(4, "B", new Date(), MoneyUtils.getScaledVatValue(0.09))), 1234L, transactionId,
                OperationType.REGISTRATION, PaymentMethod.CC);
        reservationDAO.createReservation(reservation1);
        reservationDAO.createReservation(reservation2);

        List<Reservation> reservationsFromDB = getAllReservationsForBillingNH("Test1-IEDR");
        AssertJUnit.assertEquals(2, reservationsFromDB.size());

        reservationDAO.deleteById(reservationsFromDB.get(0).getId());
        reservationsFromDB = getAllReservationsForBillingNH("Test1-IEDR");
        AssertJUnit.assertEquals(1, reservationsFromDB.size());
    }

    @Test
    public void getReservationTest() {
        List<Reservation> allReservations = getAllReservationsForBillingNH("APITEST-IEDR");
        AssertJUnit.assertEquals(10, allReservations.size());

        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(true);
        criteria.setSettled(false);
        criteria.setBillingNH("APITEST-IEDR");
        List<Reservation> readyReservations = reservationDAO.getReservations(criteria, 0, 10, null).getResults();
        AssertJUnit.assertEquals(5, readyReservations.size());

        criteria.setReadyForSettlement(false);
        criteria.setBillingNH("APITEST-IEDR");
        List<Reservation> notReadyReservations = reservationDAO.getReservations(criteria, 0, 10, null).getResults();
        AssertJUnit.assertEquals(5, notReadyReservations.size());
    }

    @Test
    public void lockTest() {
        AssertJUnit.assertTrue(reservationDAO.lock(1L));
        Reservation reservation = reservationDAO.get(1L);
        AssertJUnit.assertNotNull(reservation);
        AssertJUnit.assertEquals("createDomainRegistrarBasic.ie", reservation.getDomainName());
    }

    @Test
    public void insertRenewalReservationTest() {
        Reservation reservation1 = Reservation.newInstanceForRenewal("Test1-IEDR", "testDomain1.ie", 12,
                new ProductPriceWithVat(Period.fromYears(1), productId, MoneyUtils.getRoundedAndScaledValue(65),
                        new Vat(1, "A", new Date(), MoneyUtils.getScaledVatValue(0.19))), 1, PaymentMethod.ADP);
        long r1 = reservationDAO.createReservation(reservation1);
        List<Reservation> reservationsFromDB1 = getAllReservationsForBillingNH("Test1-IEDR");
        AssertJUnit.assertEquals(1, reservationsFromDB1.size());
        compareNewReservation(reservationsFromDB1.get(0), reservation1);
    }

    //TODO: CRS-72
    //    @Test(expectedExceptions = Exception.class)
    //    public void insertRenewalReservationWithWrongTransactionTest() {
    //        Reservation reservation1 = Reservation.newInstanceForRenewal("Test1-IEDR", "testDomain1.ie", 12, new ProductPriceWithVat(Period.fromYears(1), "Std1Year", 65, new Vat(1, "A", new Date(), 0.19f)), 666, PaymentMethod.ADP);
    //        long r1 = reservationDAO.createReservation(reservation1);
    //    }

    @Test
    public void insertCCRenewalReservationTest() {
        Reservation reservation1 = Reservation.newInstanceForRenewal("Test1-IEDR", "testDomain1.ie", 12,
                new ProductPriceWithVat(Period.fromYears(1), productId, MoneyUtils.getRoundedAndScaledValue(65),
                        new Vat(1, "A", new Date(), MoneyUtils.getScaledVatValue(0.19))), 1, PaymentMethod.CC);
        long r1 = reservationDAO.createReservation(reservation1);
        List<Reservation> reservationsFromDB1 = getAllReservationsForBillingNH("Test1-IEDR");
        AssertJUnit.assertEquals(1, reservationsFromDB1.size());
        compareNewReservation(reservationsFromDB1.get(0), reservation1);
    }

    //TODO: CRS-72
    //    @Test(expectedExceptions = Exception.class)
    //    public void inserCCtRenewalReservationWithWrongTransactionTest() {
    //        Reservation reservation1 = Reservation.newInstanceForRenewal("Test1-IEDR", "testDomain1.ie", 12, new ProductPriceWithVat(Period.fromYears(1), "Std1Year", 65, new Vat(1, "A", new Date(), 0.19f)), 666, PaymentMethod.CC);
    //        long r1 = reservationDAO.createReservation(reservation1);
    //    }

    @Test
    public void getReservationsWithCriteriaTest() {
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(false);
        criteria.setSettled(false);
        LimitedSearchResult<Reservation> result = null;

        result = reservationDAO.getReservations(null, 0, 20, null);
        AssertJUnit.assertEquals(15, result.getTotalResults());
        AssertJUnit.assertEquals(15, result.getResults().size());

        criteria.setBillingNH("APITEST-IEDR");
        result = reservationDAO.getReservations(criteria, 0, 20, null);
        AssertJUnit.assertEquals(5, result.getTotalResults());
        AssertJUnit.assertEquals(5, result.getResults().size());
        for (Reservation reservation : result.getResults()) {
            AssertJUnit.assertFalse(reservation.isReadyForSettlement());
        }

        criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(true);
        criteria.setSettled(false);
        result = reservationDAO.getReservations(criteria, 0, 10, null);
        AssertJUnit.assertEquals(5, result.getTotalResults());
        AssertJUnit.assertEquals(5, result.getResults().size());
        for (Reservation reservation : result.getResults()) {
            AssertJUnit.assertTrue(reservation.isReadyForSettlement());
        }

        criteria = new ReservationSearchCriteria();
        criteria.setPaymentMethod(PaymentMethod.ADP);
        result = reservationDAO.getReservations(criteria, 0, 10, null);
        AssertJUnit.assertEquals(9, result.getTotalResults());

        criteria = new ReservationSearchCriteria();
        criteria.setOperationType(OperationType.REGISTRATION);
        result = reservationDAO.getReservations(criteria, 0, 10, null);
        AssertJUnit.assertEquals(10, result.getTotalResults());

        criteria = new ReservationSearchCriteria();
        criteria.setCancelled(true);
        result = reservationDAO.getReservations(criteria, 0, 10, null);
        AssertJUnit.assertEquals(0, result.getTotalResults());

        criteria = new ReservationSearchCriteria();
        criteria.setCancelled(false);
        result = reservationDAO.getReservations(criteria, 0, 10, null);
        AssertJUnit.assertEquals(15, result.getTotalResults());
    }

    @Test
    public void getReservationsWithLimitTest() {
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(false);
        criteria.setSettled(false);
        LimitedSearchResult<Reservation> result = null;

        criteria.setBillingNH("APITEST-IEDR");
        result = reservationDAO.getReservations(criteria, 0, 2, null);
        AssertJUnit.assertEquals(5, result.getTotalResults());
        AssertJUnit.assertEquals(2, result.getResults().size());

        criteria.setBillingNH("APITEST-IEDR");
        result = reservationDAO.getReservations(criteria, 2, 10, null);
        AssertJUnit.assertEquals(5, result.getTotalResults());
        AssertJUnit.assertEquals(3, result.getResults().size());
    }

    @Test
    public void getReservationTotalsTest() {
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(true);
        criteria.setSettled(false);
        criteria.setBillingNH("APITEST-IEDR");
        criteria.setPaymentMethod(PaymentMethod.ADP);

        ReservationTotals result = reservationDAO.getTotals(criteria);
        AssertJUnit.assertNotNull(result);
        AssertJUnit.assertEquals(3, result.getTotalResults());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(170), result.getTotalAmount());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(36.38), result.getTotalVat());

        criteria.setReadyForSettlement(false);
        result = reservationDAO.getTotals(criteria);
        AssertJUnit.assertNotNull(result);
        AssertJUnit.assertEquals(2, result.getTotalResults());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(130), result.getTotalAmount());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(27.82), result.getTotalVat());

        criteria.setBillingNH("X");
        result = reservationDAO.getTotals(criteria);
        AssertJUnit.assertNotNull(result);
        AssertJUnit.assertEquals(0, result.getTotalResults());
        AssertJUnit.assertEquals(0, result.getTotalAmount().compareTo(BigDecimal.ZERO));
        AssertJUnit.assertEquals(0, result.getTotalVat().compareTo(BigDecimal.ZERO));
    }

    @Test
    public void getReservationsWithSortTest() {
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(false);
        criteria.setSettled(false);
        LimitedSearchResult<Reservation> result = null;
        criteria.setBillingNH("APITEST-IEDR");
        List<SortCriterion> sortBy = null;

        sortBy = Arrays.asList(new SortCriterion("domainName", true));
        result = reservationDAO.getReservations(criteria, 0, 10, sortBy);
        AssertJUnit.assertEquals(5, result.getTotalResults());
        AssertJUnit.assertEquals("1registerdomaincc.ie", result.getResults().get(0).getDomainName());
        AssertJUnit.assertEquals("createCCDomainTechPassed.ie", result.getResults().get(1).getDomainName());
        AssertJUnit.assertEquals("createDomainRegistrarBasic2.ie", result.getResults().get(2).getDomainName());

        sortBy = Arrays.asList(new SortCriterion("domainName", false));
        result = reservationDAO.getReservations(criteria, 0, 10, sortBy);
        AssertJUnit.assertEquals(5, result.getTotalResults());
        AssertJUnit.assertEquals("registerCCDomainForTripplePass.ie", result.getResults().get(0).getDomainName());
        AssertJUnit.assertEquals("createDomainRegistrarBasic3.ie", result.getResults().get(1).getDomainName());
        AssertJUnit.assertEquals("createDomainRegistrarBasic2.ie", result.getResults().get(2).getDomainName());
    }

    @Test
    public void getReadyReservationsByTransactionIdTest() {
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setReadyForSettlement(true);
        criteria.setSettled(false);
        criteria.setTransactionId(1);
        List<Reservation> res = reservationDAO.getReservations(criteria, 0, 10, null).getResults();
        AssertJUnit.assertEquals(1, res.size());
    }

    @Test
    public void deleteTest() {
        AssertJUnit.assertEquals(15, reservationDAO.count(null));

        Reservation reservation = reservationDAO.get(1L);
        AssertJUnit.assertNotNull(reservation);

        reservationDAO.deleteById(1L);

        AssertJUnit.assertEquals(14, reservationDAO.count(null));

        reservation = reservationDAO.get(1L);
        AssertJUnit.assertNull(reservation);
    }

    @Test
    public void testFindExtended() throws Exception {
        ExtendedReservationSearchCriteria criteria = new ExtendedReservationSearchCriteria();
        LimitedSearchResult<ExtendedReservation> reservations = reservationDAO.findExtended(criteria, 0, 20, null);
        AssertJUnit.assertEquals(16, reservations.getTotalResults());
        AssertJUnit.assertEquals(16, reservations.getResults().size());

        criteria.setInvoiceNumberFrom("101");
        criteria.setInvoiceNumberTo("101");
        reservations = reservationDAO.findExtended(criteria, 0, 10, null);
        AssertJUnit.assertEquals(7, reservations.getTotalResults());
        AssertJUnit.assertEquals(7, reservations.getResults().size());

        // check, if the renewal date is filled
        AssertJUnit.assertNotNull("renewalDate", reservations.getResults().get(0).getRenewalDate());

        AssertJUnit.assertNotNull("orderId", reservations.getResults().get(0).getOrderId());
    }

    @Test
    public void testFindExtendedWithDateRange() throws Exception {
        ExtendedReservationSearchCriteria criteria = new ExtendedReservationSearchCriteria();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012, Calendar.JUNE, 21);
        Date date = calendar.getTime();
        criteria.setInvoiceDateFrom(date);
        criteria.setInvoiceDateTo(date);
        criteria.setSettledFrom(date);
        criteria.setSettledTo(date);
        LimitedSearchResult<ExtendedReservation> reservations = reservationDAO.findExtended(criteria, 0, 10, null);
        AssertJUnit.assertEquals(8, reservations.getTotalResults());
        AssertJUnit.assertEquals(8, reservations.getResults().size());
    }
}
