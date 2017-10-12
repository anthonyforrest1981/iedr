package pl.nask.crs.ticket;

public enum TechStatus {
    NEW("New"), PASSED("Passed"), STALLED("Stalled");

    private final String description;

    TechStatus(String desc) {
        this.description = desc;
    }

    public String getDescription() {
        return description;
    }

    public static TechStatus forDescription(final String desc) {
        if (NEW.description.equals(desc)) {
            return NEW;
        } else if (PASSED.description.equals(desc)) {
            return PASSED;
        } else if (STALLED.description.equals(desc)) {
            return STALLED;
        } else {
            throw new IllegalArgumentException("Unsupported value for FinancialStatus: " + desc);
        }
    }
}
