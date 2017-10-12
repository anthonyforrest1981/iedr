package pl.nask.crs.payment.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.payment.*;
import pl.nask.crs.vat.ProductPriceWithVat;
import pl.nask.crs.vat.Vat;

public class ReservationUTF8DAOTest extends AbstractContextAwareTest {

    @Resource
    ReservationDAO reservationDAO;

    @Resource
    TransactionDAO transactionDAO;

    @Test
    public void testGetUnnormalizedReservation() {
        // Check if internalReservation resultMap has string handlers (used in getAllByBillingNH, getReservationById,
        // findReservations selects).
        Reservation reservation = reservationDAO.get(998L);
        Assert.assertEquals(reservation.getNicHandleId(), "ÄIDL2-IEDR");
        Assert.assertEquals(reservation.getDomainName(), "Äpaymentutf8daotest.ie");
        Assert.assertEquals(reservation.getOrderId(), "ÄorderId998");
    }

    @Test
    public void testInsertUnnormalizedReservation() {
        // Check if insertReservation insert has string handlers.
        long transactionId = transactionDAO.createTransaction(Transaction.newInstance(null, null, true, false,
                MoneyUtils.getRoundedAndScaledValue(50), MoneyUtils.getRoundedAndScaledValue(40),
                MoneyUtils.getRoundedAndScaledValue(10), "order id", null));
        Reservation reservation = Reservation.newInstanceForTicket("A\u0308IDL2-IEDR", "A\u0308paymentutf8daotest.ie",
                1,
                new ProductPriceWithVat(Period.fromYears(1), 1, MoneyUtils.getRoundedAndScaledValue(35.5),
                        new Vat(9, "C", new Date(), MoneyUtils.getScaledVatValue(0.316))),
                1234L, transactionId, OperationType.REGISTRATION, PaymentMethod.ADP);
        long reservationId = reservationDAO.createReservation(reservation);
        Reservation dbReservation = reservationDAO.get(reservationId);
        Assert.assertEquals(dbReservation.getNicHandleId(), "ÄIDL2-IEDR");
        Assert.assertEquals(dbReservation.getDomainName(), "Äpaymentutf8daotest.ie");
    }

    @Test
    public void testFindReservationByUnnormalizedCriteria() {
        // Check if reservationCriteria sql frag has string handlers (used in countFindReservations, findReservations,
        // getTotals selects).
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setBillingNH("U\u0308IDL2-IEDR");
        LimitedSearchResult<Reservation> limitedResult = reservationDAO.getReservations(criteria, 0, 5, null);
        List<Reservation> results = limitedResult.getResults();
        Assert.assertEquals(limitedResult.getTotalResults(), 1);
        Assert.assertEquals(results.size(), 1);
        Reservation reservation = results.get(0);
        Assert.assertEquals(reservation.getId(), 999);
    }

    @Test
    public void testGetUnnormalizedExtendedReservation() {
        // Check if internalExtendedReservation resultMap has string handlers (used in findExtendedReservations select).
        String invoiceNumber = "ÄTEST997";
        ExtendedReservationSearchCriteria criteria = new ExtendedReservationSearchCriteria();
        criteria.setInvoiceNumber(invoiceNumber);
        LimitedSearchResult<ExtendedReservation> limitedResults = reservationDAO.findExtended(criteria, 0, 5, null);
        List<ExtendedReservation> results = limitedResults.getResults();
        Assert.assertEquals(limitedResults.getTotalResults(), 1);
        Assert.assertEquals(results.size(), 1);
        ExtendedReservation extended = results.get(0);
        Assert.assertEquals(extended.getDomainName(), "äpaymentutf8daotest.ie");
        Assert.assertEquals(extended.getOrderId(), "ÄorderId997");
        Assert.assertEquals(extended.getBillingNicHandle(), "ÄIDL2-IEDR");
        Assert.assertEquals(extended.getBillingNicHandleName(), "ÄIRISH DOMAINS LTD");
        Assert.assertEquals(extended.getInvoiceNumber(), invoiceNumber);
    }

    @Test
    public void testFindExtendedReservationByUnnormalizedCriteria() {
        // Check if extendedReservationsCriteria sql frag has string handlers (used in countFindExtendedReservations,
        // findExtendedReservations selects).
        String invoiceNumber = "U\u0308TEST999";
        ExtendedReservationSearchCriteria criteria = new ExtendedReservationSearchCriteria();
        criteria.setInvoiceNumber(invoiceNumber);
        criteria.setInvoiceNumberFrom(invoiceNumber);
        criteria.setInvoiceNumberTo(invoiceNumber);
        LimitedSearchResult<ExtendedReservation> limitedResults = reservationDAO.findExtended(criteria, 0, 5, null);
        List<ExtendedReservation> results = limitedResults.getResults();
        Assert.assertEquals(limitedResults.getTotalResults(), 1);
        Assert.assertEquals(results.size(), 1);
    }
}
