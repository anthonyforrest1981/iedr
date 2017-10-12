package pl.nask.crs.payment;

import java.util.Map;

public class TransactionWithReservationsSummary {

    private long transactionId;
    private String orderId;
    private Payment totalPayment;
    private Map<String, Long> domainReservations;
    private String realexAuthcode;
    private String realexPasref;

    public TransactionWithReservationsSummary(long transactionId, String orderId, Payment totalPayment,
            Map<String, Long> domainReservations, String realexAuthcode, String realexPasref) {
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.totalPayment = totalPayment;
        this.domainReservations = domainReservations;
        this.realexAuthcode = realexAuthcode;
        this.realexPasref = realexPasref;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public Payment getTotalPayment() {
        return totalPayment;
    }

    public Long getReservationId(String domainName) {
        return domainReservations.get(domainName);
    }

    public String getRealexAuthcode() {
        return realexAuthcode;
    }

    public String getRealexPasref() {
        return realexPasref;
    }
}
