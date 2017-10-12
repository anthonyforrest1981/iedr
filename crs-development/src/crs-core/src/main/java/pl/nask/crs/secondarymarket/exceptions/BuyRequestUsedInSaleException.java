package pl.nask.crs.secondarymarket.exceptions;

import pl.nask.crs.secondarymarket.BuyRequest;

public class BuyRequestUsedInSaleException extends Exception {

    private final BuyRequest buyRequest;

    public BuyRequestUsedInSaleException(BuyRequest buyRequest) {
        this.buyRequest = buyRequest;
    }

    public BuyRequest getBuyRequest() {
        return buyRequest;
    }

}
