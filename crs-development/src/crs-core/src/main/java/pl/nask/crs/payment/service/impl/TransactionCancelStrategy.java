package pl.nask.crs.payment.service.impl;

import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.exceptions.CardPaymentException;

public interface TransactionCancelStrategy {

    void handleRealexError(Transaction transaction, CardPaymentException e) throws CardPaymentException;

    boolean shouldPurgeTransaction();

    TransactionCancelStrategy PURGE = new TransactionCancelStrategy() {
        @Override
        public void handleRealexError(Transaction transaction, CardPaymentException e) throws CardPaymentException {
            throw e;
        }

        @Override
        public boolean shouldPurgeTransaction() {
            return true;
        }
    };

    TransactionCancelStrategy LEAVE_AS_CANCELLED = new TransactionCancelStrategy() {
        @Override
        public void handleRealexError(Transaction transaction, CardPaymentException e) throws CardPaymentException {
            throw e;
        }

        @Override
        public boolean shouldPurgeTransaction() {
            return false;
        }
    };

}
