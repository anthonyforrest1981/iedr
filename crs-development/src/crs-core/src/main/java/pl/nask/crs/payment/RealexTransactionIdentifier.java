package pl.nask.crs.payment;

public class RealexTransactionIdentifier {

    private String authcode;
    private String orderId;
    private String pasref;

    public RealexTransactionIdentifier(String authcode, String orderId, String pasref) {
        this.authcode = authcode;
        this.orderId = orderId;
        this.pasref = pasref;
    }

    public String getAuthcode() {
        return authcode;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getPasref() {
        return pasref;
    }

}
