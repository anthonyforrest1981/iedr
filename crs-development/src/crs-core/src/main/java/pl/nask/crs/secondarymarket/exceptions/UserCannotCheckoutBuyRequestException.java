package pl.nask.crs.secondarymarket.exceptions;

public class UserCannotCheckoutBuyRequestException extends Exception {
    public UserCannotCheckoutBuyRequestException(String msg) {
        super(msg);
    }
}
