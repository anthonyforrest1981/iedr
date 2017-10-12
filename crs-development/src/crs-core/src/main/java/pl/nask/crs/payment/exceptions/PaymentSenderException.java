package pl.nask.crs.payment.exceptions;

public class PaymentSenderException extends Exception {
    public PaymentSenderException(String message, Throwable t) {
        super(message, t);
    }
}
