package pl.nask.crs.secondarymarket;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public enum BuyRequestStatus {
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

    BuyRequestStatus(String description) {
        this.description = description;
    }

    public static BuyRequestStatus forDescription(final String description) {
        if (NEW.description.equals(description)) {
            return NEW;
        } else if (PASSED.description.equals(description)) {
            return PASSED;
        } else if (HOLD_UPDATE.description.equals(description)) {
            return HOLD_UPDATE;
        } else if (HOLD_PAPERWORK.description.equals(description)) {
            return HOLD_PAPERWORK;
        } else if (STALLED.description.equals(description)) {
            return STALLED;
        } else if (RENEW.description.equals(description)) {
            return RENEW;
        } else if (FINANCE_HOLDUP.description.equals(description)) {
            return FINANCE_HOLDUP;
        } else if (CANCELLED.description.equals(description)) {
            return CANCELLED;
        } else if (HOLD_REGISTRAR_APPROVAL.description.equals(description)) {
            return HOLD_REGISTRAR_APPROVAL;
        } else if (DOCUMENTS_SUBMITTED.description.equals(description)) {
            return DOCUMENTS_SUBMITTED;
        } else {
            throw new IllegalArgumentException("Unsupported value for Buy Request status: " + description);
        }
    }

    public String getDescription() {
        return description;
    }

    public static List<BuyRequestStatus> valuesExcept(BuyRequestStatus... except) {
        List<BuyRequestStatus> values = new LinkedList<>(Arrays.asList(values()));
        for (BuyRequestStatus status : except) {
            values.remove(status);
        }
        return values;
    }

}
