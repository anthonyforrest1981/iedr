package pl.nask.crs.payment;

public enum OperationType {
    REGISTRATION("registration"), RENEWAL("renewal"), TRANSFER("transfer"), BUY_REQUEST("buy request"),
    SELL_REQUEST("sell request");

    private String typeName;

    OperationType(String typeName) {
        this.typeName = typeName;
    }

    public static OperationType forTypeName(final String typeName) {
        for (OperationType t : OperationType.values()) {
            if (t.getTypeName().equals(typeName)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown typename " + typeName + " for OperationType");
    }

    public String getTypeName() {
        return typeName;
    }
}
