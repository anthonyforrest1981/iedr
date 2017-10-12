package pl.nask.crs.secondarymarket.exceptions;

import pl.nask.crs.secondarymarket.BuyRequest;

public class BuyRequestNotCheckedOutToUserException extends Exception {
    private final BuyRequest buyRequest;
    private final String userName;

    public BuyRequestNotCheckedOutToUserException(BuyRequest buyRequest, String userName) {
        this.buyRequest = buyRequest;
        this.userName = userName;
    }

    public BuyRequest getBuyRequest() {
        return buyRequest;
    }

    public String getUserName() {
        return userName;
    }
}
