package pl.nask.crs.payment.service;

import pl.nask.crs.payment.PaymentRequest;
import pl.nask.crs.payment.RealexTransactionIdentifier;
import pl.nask.crs.payment.exceptions.CardPaymentException;

public interface CardPaymentService {

    RealexTransactionIdentifier authorisePaymentTransaction(PaymentRequest request) throws CardPaymentException;

    void settleRealexAuthorisation(RealexTransactionIdentifier transactionIdentifier) throws CardPaymentException;

    void cancelRealexAuthorisation(RealexTransactionIdentifier transactionIdentifier) throws CardPaymentException;

}
