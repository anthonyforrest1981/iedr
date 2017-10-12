package pl.nask.crs.secondarymarket.exceptions;

import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.BuyRequestStatus;

public class BadBuyRequestStatusException extends Exception {

    private final BuyRequest buyRequest;
    private final BuyRequestStatus newStatus;

    public BadBuyRequestStatusException(BuyRequest buyRequest, BuyRequestStatus newStatus) {
        this.buyRequest = buyRequest;
        this.newStatus = newStatus;
    }

    public BuyRequest getBuyRequest() {
        return buyRequest;
    }

    public BuyRequestStatus getNewStatus() {
        return newStatus;
    }
}
