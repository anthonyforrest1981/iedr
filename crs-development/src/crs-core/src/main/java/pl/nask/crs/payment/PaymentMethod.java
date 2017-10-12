package pl.nask.crs.payment;

public enum PaymentMethod {
    ADP("Deposit"), CC("Credit Card"),
    // Debit Card is no longer accepted as a payment method. It was removed as a part of CRS-174 and CRS-797. It has to
    // stay here, because we have historical data with this payment method.
    DEB("Debit Card");

    PaymentMethod(String fullName) {
        this.fullName = fullName;
    }

    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public static PaymentMethod forFullName(String fullName) {
        if (fullName == null) {
            return null;
        }
        for (PaymentMethod t : PaymentMethod.values()) {
            if (t.getFullName().equals(fullName)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown payment method " + fullName);
    }
}
