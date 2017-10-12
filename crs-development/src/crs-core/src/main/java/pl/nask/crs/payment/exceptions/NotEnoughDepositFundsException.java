package pl.nask.crs.payment.exceptions;

public class NotEnoughDepositFundsException extends Exception {

    public NotEnoughDepositFundsException() {}

    public NotEnoughDepositFundsException(Throwable cause) {
        super(cause);
    }

    public NotEnoughDepositFundsException(String message) {
        super(message);
    }

    public NotEnoughDepositFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
