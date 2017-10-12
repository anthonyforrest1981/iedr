package pl.nask.crs.secondarymarket;

public enum BuyRequestNotificationType {
    REQUEST("request"),
    AUTHCODE("authcode");

    private String description;

    BuyRequestNotificationType(String description) {
        this.description = description;
    }

    public static BuyRequestNotificationType forDescription(final String description) {
        if (REQUEST.description.equals(description)) {
            return REQUEST;
        } else if (AUTHCODE.description.equals(description)) {
            return AUTHCODE;
        } else {
            throw new IllegalArgumentException("Unsupported value for Buy Request notification type: " + description);
        }
    }

    public String getDescription() {
        return description;
    }
}
