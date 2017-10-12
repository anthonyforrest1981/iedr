package pl.nask.crs.secondarymarket;

public enum SellRequestStatus {
    PROCESSING("Processing"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private String description;

    SellRequestStatus(String description) {
        this.description = description;
    }

    public static SellRequestStatus forDescription(final String description) {
        if (PROCESSING.description.equals(description)) {
            return PROCESSING;
        } else if (COMPLETED.description.equals(description)) {
            return COMPLETED;
        } else if (CANCELLED.description.equals(description)) {
            return CANCELLED;
        } else {
            throw new IllegalArgumentException("Unsupported value for Sell Request status: " + description);
        }
    }

    public String getDescription() {
        return description;
    }

}
