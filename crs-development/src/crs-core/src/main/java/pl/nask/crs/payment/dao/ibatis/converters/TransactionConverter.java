package pl.nask.crs.payment.dao.ibatis.converters;

import java.util.Collections;
import java.util.List;

import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.dao.ibatis.objects.InternalReservation;
import pl.nask.crs.payment.dao.ibatis.objects.InternalTransaction;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TransactionConverter extends AbstractConverter<InternalTransaction, Transaction> {

    private ReservationConverter reservationConverter;

    public TransactionConverter(ReservationConverter reservationConverter) {
        this.reservationConverter = reservationConverter;
    }

    @Override
    protected Transaction _to(InternalTransaction internalTransaction) {
        final List<InternalReservation> internalReservations = internalTransaction.getReservations();
        List<Reservation> reservations;
        if (internalReservations != null) {
            reservations = reservationConverter.to(internalReservations);
        } else {
            reservations = Collections.emptyList();
        }
        return new Transaction(internalTransaction.getId(), internalTransaction.getNicHandleId(),
                internalTransaction.getInvoiceId(), internalTransaction.getInvoiceNumber(),
                internalTransaction.getOrderId(), internalTransaction.isSettlementStarted(),
                internalTransaction.isSettlementEnded(), internalTransaction.getTotalCost(),
                internalTransaction.getTotalNetAmount(), internalTransaction.getTotalVatAmount(),
                internalTransaction.isCancelled(), internalTransaction.getCancelledDate(),
                internalTransaction.getFinanciallyPassedDate(), reservations, internalTransaction.isInvalidated(),
                internalTransaction.getInvalidatedDate(), internalTransaction.getReauthorisedId(),
                internalTransaction.getSettlementDate(), internalTransaction.getPaymentMethod(),
                internalTransaction.getOperationType(), internalTransaction.getRealexAuthcode(),
                internalTransaction.getRealexPasref());
    }

    @Override
    protected InternalTransaction _from(Transaction transaction) {
        List<InternalReservation> reservations = reservationConverter.from(transaction.getReservations());
        return new InternalTransaction(transaction.getId(), transaction.getBillNicHandleId(),
                transaction.getInvoiceId(), transaction.getOrderId(), transaction.isSettlementStarted(),
                transaction.isSettlementEnded(), transaction.getTotalCost(), transaction.getTotalNetAmount(),
                transaction.getTotalVatAmount(), transaction.isCancelled(), transaction.getCancelledDate(),
                transaction.getFinanciallyPassedDate(), reservations, transaction.isInvalidated(),
                transaction.getInvalidatedDate(), transaction.getReauthorisedId(), transaction.getSettlementDate(),
                transaction.getPaymentMethod(), transaction.getOperationType(), transaction.getRealexAuthCode(),
                transaction.getRealexPasRef());
    }
}
