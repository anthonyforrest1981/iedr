package pl.nask.crs.ticket;

public enum FinancialStatus {
    NEW("New"), PASSED("Passed"), STALLED("Stalled");

    private final String description;

    FinancialStatus(String desc) {
        this.description = desc;
    }

    public String getDescription() {
        return description;
    }

    public static FinancialStatus forDescription(final String desc) {
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
