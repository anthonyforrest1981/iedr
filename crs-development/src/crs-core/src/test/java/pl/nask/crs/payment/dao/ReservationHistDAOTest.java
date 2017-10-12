package pl.nask.crs.payment.dao;

import java.util.List;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.payment.AbstractContextAwareTest;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.ReservationSearchCriteria;
import pl.nask.crs.payment.Transaction;

import static pl.nask.crs.payment.testhelp.PaymentTestHelp.compareReservation;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ReservationHistDAOTest extends AbstractContextAwareTest {

    @Resource
    ReservationHistDAO reservationHistDAO;

    @Resource
    ReservationDAO reservationDAO;

    @Resource
    TransactionDAO transactionDAO;

    @Resource
    TransactionHistDAO transactionHistDAO;

    @Test
    public void createTest() {
        Transaction transaction = transactionDAO.get(20L);
        transactionHistDAO.create(transaction);

        int resultOld = reservationHistDAO.count(null);

        Reservation reservation = reservationDAO.get(8L);
        reservationHistDAO.create(reservation);

        Reservation historicalReservation = reservationHistDAO.get(8L);
        compareReservation(reservation, historicalReservation);

        int resultMew = reservationHistDAO.count(null);
        AssertJUnit.assertEquals(resultOld + 1, resultMew);
    }

    @Test
    public void testGetUnnormalizedReservation() {
        // Check if internalReservation resultMap has string handlers (used in getById, findHistReservations selects).
        Reservation reservation = reservationHistDAO.get(998L);
        Assert.assertEquals(reservation.getInvoiceNumber(), "ÄTEST998");
        Assert.assertEquals(reservation.getOrderId(), "ÄorderId998");
        Assert.assertEquals(reservation.getDomainName(), "äpaymentutf8daotest.ie");
        Assert.assertEquals(reservation.getNicHandleId(), "ÄIDL2-IEDR");
    }

    @Test
    public void testFindHistReservationByUnnormalizedCriteria() {
        // Check if reservationHistCriteria sql frag has string handlers (used in countFindHistReservations,
        // findHistReservations selects).
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setBillingNH("U\u0308IDL2-IEDR");
        LimitedSearchResult<Reservation> limitedResult = reservationHistDAO.find(criteria, 0, 5, null);
        List<Reservation> results = limitedResult.getResults();
        Assert.assertEquals(limitedResult.getTotalResults(), 1);
        Assert.assertEquals(results.size(), 1);
        Reservation reservation = results.get(0);
        Assert.assertEquals(reservation.getId(), 999);
    }

    @Test
    public void testLooseUtf8Validation() {
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setBillingNH("IDL3-IEDR\01");
        LimitedSearchResult<Reservation> limitedResult = reservationHistDAO.find(criteria, 0, 5, null);
        Assert.assertEquals(limitedResult.getTotalResults(), 1);
        final Reservation reservation = limitedResult.getResults().get(0);
        Assert.assertEquals(reservation.getDomainName(), "badutf8\01.ie");
    }

}
