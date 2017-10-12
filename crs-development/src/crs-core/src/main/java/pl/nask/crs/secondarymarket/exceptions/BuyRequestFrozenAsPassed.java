package pl.nask.crs.secondarymarket.exceptions;

import pl.nask.crs.secondarymarket.BuyRequest;

public class BuyRequestFrozenAsPassed extends Exception {
    private final BuyRequest buyRequest;

    public BuyRequestFrozenAsPassed(BuyRequest buyRequest) {this.buyRequest = buyRequest;}

    public BuyRequest getBuyRequest() {
        return buyRequest;
    }
}
