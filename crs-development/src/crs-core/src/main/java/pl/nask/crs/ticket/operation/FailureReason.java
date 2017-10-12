package pl.nask.crs.ticket.operation;

public enum FailureReason {

    INCORRECT("Incorrect");

    private final String description;

    FailureReason(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static FailureReason forDescription(final String description) {
        if (description.equals(INCORRECT.description)) {
            return INCORRECT;
        } else {
            throw new IllegalArgumentException("Unsupported value for FailureReason: " + description);
        }
    }
}
