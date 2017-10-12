package pl.nask.crs.payment;

import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.service.CardPaymentService;

public class CardPaymentServiceMock implements CardPaymentService {

    @Override
    public RealexTransactionIdentifier authorisePaymentTransaction(PaymentRequest request) throws CardPaymentException {
        ExtendedPaymentRequest paymentRequest =
                ExtendedPaymentRequest.authorisationInstance(null, "merchant", "password", request);
        paymentRequest.setAuthorisationData("authcode", "pasref", "bank", "country");
        return new RealexTransactionIdentifier(paymentRequest.getAuthcode(), paymentRequest.getOrderId(),
                paymentRequest.getPasref());
    }

    @Override
    public void settleRealexAuthorisation(RealexTransactionIdentifier transactionIdentifier) throws
            CardPaymentException {

    }

    @Override
    public void cancelRealexAuthorisation(RealexTransactionIdentifier transactionIdentifier) throws
            CardPaymentException {

    }

}
