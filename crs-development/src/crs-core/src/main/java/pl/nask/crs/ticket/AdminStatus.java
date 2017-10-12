package pl.nask.crs.ticket;

public enum AdminStatus {
    NEW("New"),
    PASSED("Passed"),
    HOLD_UPDATE("Hold Update"),
    HOLD_PAPERWORK("Hold Paperwork"),
    STALLED("Stalled"),
    RENEW("Renew"),
    FINANCE_HOLDUP("Finance Holdup"),
    CANCELLED("Cancelled"),
    HOLD_REGISTRAR_APPROVAL("Hold Registrar Approval"),
    DOCUMENTS_SUBMITTED("Documents Submitted");

    private String description;

    AdminStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static AdminStatus forDescription(String desc) {
        if (NEW.description.equals(desc)) {
            return NEW;
        } else if (PASSED.description.equals(desc)) {
            return PASSED;
        } else if (HOLD_UPDATE.description.equals(desc)) {
            return HOLD_UPDATE;
        } else if (HOLD_PAPERWORK.description.equals(desc)) {
            return HOLD_PAPERWORK;
        } else if (STALLED.description.equals(desc)) {
            return STALLED;
        } else if (RENEW.description.equals(desc)) {
            return RENEW;
        } else if (FINANCE_HOLDUP.description.equals(desc)) {
            return FINANCE_HOLDUP;
        } else if (CANCELLED.description.equals(desc)) {
            return CANCELLED;
        } else if (HOLD_REGISTRAR_APPROVAL.description.equals(desc)) {
            return HOLD_REGISTRAR_APPROVAL;
        } else if (DOCUMENTS_SUBMITTED.description.equals(desc)) {
            return DOCUMENTS_SUBMITTED;
        } else {
            throw new IllegalArgumentException("Unsupported value for AdminStatus: " + desc);
        }
    }
}
