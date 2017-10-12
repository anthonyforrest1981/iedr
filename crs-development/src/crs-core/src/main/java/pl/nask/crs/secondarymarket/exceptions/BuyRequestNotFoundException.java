package pl.nask.crs.secondarymarket.exceptions;

public class BuyRequestNotFoundException extends Exception {
    private long buyRequestId;

    public BuyRequestNotFoundException(long buyRequestId) {
        this.buyRequestId = buyRequestId;
    }

    public long getBuyRequestId() {
        return buyRequestId;
    }
}
