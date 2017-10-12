package pl.nask.crs.ticket;

public enum CustomerStatus {
    NEW("New"), CANCELLED("Cancelled");

    private final String description;

    CustomerStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static CustomerStatus forDescription(final String desc) {
        if (NEW.description.equals(desc)) {
            return NEW;
        } else if (CANCELLED.description.equals(desc)) {
            return CANCELLED;
        } else {
            throw new IllegalArgumentException("Unsupported value for CustomerStatus: " + desc);
        }
    }
}
