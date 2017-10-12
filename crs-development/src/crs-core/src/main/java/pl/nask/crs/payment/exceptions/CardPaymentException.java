package pl.nask.crs.payment.exceptions;

public class CardPaymentException extends Exception {

    private Type type;

    public CardPaymentException(String message, Type type) {
        super(message);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        DELIVERY,
        RESPONSE_FORMAT,
        FAILED_TRANSACTION,
        BANK_SYSTEM_ERROR,
        REALEX_SYSTEM_ERROR,
        UNKNOWN_ERROR
    }

}
