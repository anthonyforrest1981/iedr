package pl.nask.crs.payment.testhelp;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import pl.nask.crs.payment.*;

import static org.junit.Assert.*;
import static pl.nask.crs.commons.utils.DateUtils.getCardExpDateAsString;

public class PaymentTestHelp {

    private static void compareDepositBasics(Deposit expected, Deposit actual) {
        assertNotNull(expected);
        assertNotNull(actual);

        assertEquals(expected.getNicHandleId(), actual.getNicHandleId());
        assertEquals(expected.getOpenBal(), actual.getOpenBal());
        assertEquals(expected.getCloseBal(), actual.getCloseBal());
        assertEquals(expected.getTransactionAmount(), actual.getTransactionAmount());
        assertEquals(expected.getTransactionType(), actual.getTransactionType());
        assertEquals(expected.getCorrectorNH(), actual.getCorrectorNH());
        assertEquals(expected.getRemark(), actual.getRemark());
    }

    public static void compareNewDeposit(Deposit expected, Deposit actual) {
        compareDepositBasics(expected, actual);

        assertNotNull(actual.getOrderId());
        assertNotNull(actual.getTransactionDate());
    }

    public static void compareDeposit(Deposit expected, Deposit actual) {
        compareDepositBasics(expected, actual);

        assertEquals(expected.getOrderId(), actual.getOrderId());
        compareDate(expected.getTransactionDate(), actual.getTransactionDate());
    }

    public static void compareReservation(Reservation expected, Reservation actual) {
        assertNotNull(actual);
        assertNotNull(expected);
        assertEquals(expected.getNicHandleId(), actual.getNicHandleId());
        assertEquals(expected.getDomainName(), actual.getDomainName());
        assertEquals(expected.getDurationMonths(), actual.getDurationMonths());
        compareDate(expected.getCreationDate(), actual.getCreationDate());
        assertEquals(expected.getProductId(), actual.getProductId());
        assertEquals(expected.getNetAmount(), actual.getNetAmount());
        assertEquals(expected.getVatCategory(), actual.getVatCategory());
        assertEquals(expected.getVatRate(), actual.getVatRate());
        assertEquals(expected.getVatAmount(), actual.getVatAmount());
        assertEquals(expected.getTicketId(), actual.getTicketId());
        assertEquals(expected.isReadyForSettlement(), actual.isReadyForSettlement());
        assertEquals(expected.getPaymentMethod(), actual.getPaymentMethod());
        assertEquals(expected.getTransactionId(), actual.getTransactionId());
        assertEquals(expected.getOperationType(), actual.getOperationType());
        assertEquals(expected.getVatId(), actual.getVatId());
        assertEquals(expected.getVatRate(), actual.getVatRate());
        assertEquals(expected.getVatAmount(), actual.getVatAmount());
        assertEquals(expected.getVatCategory(), actual.getVatCategory());

    }

    public static void compareNewReservation(Reservation actual, Reservation expected) {
        compareReservation(expected, actual);
        assertFalse(actual.isSettled());
        assertNull(actual.getSettledDate());
    }

    public static void compareSettledReservation(Reservation actual, Reservation expected) {
        compareReservation(expected, actual);
        assertTrue(actual.isSettled());
        assertNotNull(actual.getSettledDate());
        compareDate(expected.getSettledDate(), actual.getSettledDate());
    }

    public static void compareNewReadyForSettlement(Reservation actual, Reservation expected) {
        compareNewReservation(actual, expected);
        assertTrue(actual.isReadyForSettlement());
    }

    //MYSQL missing miliseconds
    private static void compareDate(Date expected, Date actual) {
        if (expected == null) {
            assertNull(actual);
        } else {
            if (actual == null)
                assert false;

            assertEquals(DateUtils.truncate(expected, Calendar.SECOND), actual);
        }
    }

    public static void compareHistTransactions(Transaction expected, Transaction actual) {
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.getInvoiceId(), actual.getInvoiceId());
        assertEquals(expected.getTotalCost(), actual.getTotalCost());
        assertEquals(expected.getTotalNetAmount(), actual.getTotalNetAmount());
        assertEquals(expected.getTotalVatAmount(), actual.getTotalVatAmount());
        assertEquals(expected.isSettlementStarted(), actual.isSettlementStarted());
        assertEquals(expected.isSettlementEnded(), actual.isSettlementEnded());
        assertEquals(expected.isCancelled(), actual.isCancelled());
        compareDate(expected.getCancelledDate(), actual.getCancelledDate());
        assertEquals(expected.getFinanciallyPassedDate(), actual.getFinanciallyPassedDate());
        assertEquals(expected.getOrderId(), actual.getOrderId());
        assertEquals(expected.isInvalidated(), actual.isInvalidated());
        compareDate(expected.getInvalidatedDate(), actual.getInvalidatedDate());
        assertEquals(expected.getReauthorisedId(), actual.getReauthorisedId());
    }

    public static void compareTransactions(Transaction expected, Transaction actual) {
        compareHistTransactions(expected, actual);
        if (expected.getReservations() != null) {
            assertEquals(expected.getReservations().size(), actual.getReservations().size());
        }
    }

    public static void compareInvoices(Invoice expected, Invoice actual) {
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.getInvoiceNumber(), actual.getInvoiceNumber());
        assertEquals(expected.getAccountName(), actual.getAccountName());
        assertEquals(expected.getAccountNumber(), actual.getAccountNumber());
        assertEquals(expected.getAddress1(), actual.getAddress1());
        assertEquals(expected.getAddress2(), actual.getAddress2());
        assertEquals(expected.getAddress3(), actual.getAddress3());
        assertEquals(expected.getBillingNicHandle(), actual.getBillingNicHandle());
        assertEquals(expected.getCountry().getId(), actual.getCountry().getId());
        assertEquals(expected.getCounty().getId(), actual.getCounty().getId());
        assertEquals(expected.getCrsVersion(), actual.getCrsVersion());
        assertEquals(expected.getMD5(), actual.getMD5());
        compareDate(expected.getInvoiceDate(), actual.getInvoiceDate());
        if (expected.getTransactions() != null) {
            assertEquals(expected.getTransactions().size(), actual.getTransactions().size());
        }
        assertEquals(expected.getTotalCost(), actual.getTotalCost());
        assertEquals(expected.getTotalNetAmount(), actual.getTotalNetAmount());
        assertEquals(expected.getTotalVatAmount(), actual.getTotalVatAmount());
    }

    public static CreditCard createBasicCreditCard() {
        return new CreditCard("4263971921001307", getCardExpDateAsString(new Date()), "VISA", "Holder", "123", null);
    }

}
