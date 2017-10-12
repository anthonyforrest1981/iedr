package pl.nask.crs.domains;

import java.util.ArrayList;
import java.util.List;

public enum NRPStatus {
    Active("A", "Active"),
    InvoluntaryMailed("IM", "Involuntary Mailed"),
    VoluntaryMailed("VM", "Voluntary Mailed"),
    InvoluntarySuspended("IS", "Involuntary Suspended"),
    VoluntarySuspended("VS", "Voluntary Suspended"),
    Deleted("D", "Deleted"),
    TransferPendingActive("XPA", "Transfer Pending - Active", "Transfer pending"),
    TransferPendingInvNRP("XPI", "Transfer Pending - Inv. NRP", "Transfer pending"),
    TransferPendingVolNRP("XPV", "Transfer Pending - Voluntary NRP", "Transfer pending"),
    InvoluntaryMailedPaymentPending("IMPP", "Involuntary Mailed Payment Pending"),
    InvoluntarySuspendedPaymentPending("ISPP", "Involuntary Suspended Payment Pending"),
    NA("N/A", "N/A");

    private final String description;
    private final String shortDescription;
    private final String code;

    NRPStatus(String code, String description) {
        this(code, description, description);
    }

    NRPStatus(String code, String description, String shortDescription) {
        this.code = code;
        this.description = description;
        this.shortDescription = shortDescription;
    }

    public static NRPStatus forCode(String code) {
        if (code == null) {
            return NA;
        }
        for (NRPStatus t : NRPStatus.values()) {
            if (t.getCode().equals(code)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unsupported code for NRPStatus: " + code);
    }

    public static List<NRPStatus> getActiveList(Boolean active) {
        List<NRPStatus> list = new ArrayList<NRPStatus>();
        if (active != null) {
            for (NRPStatus st : NRPStatus.values()) {
                if (active != st.isNRP()) {
                    list.add(st);
                }
            }
        }
        list.remove(NA);
        return list;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public boolean isNRP() {
        switch (this) {
            case Deleted:
            case InvoluntaryMailed:
            case InvoluntarySuspended:
            case VoluntaryMailed:
            case VoluntarySuspended:
            case TransferPendingVolNRP:
            case TransferPendingInvNRP:
            case InvoluntaryMailedPaymentPending:
            case InvoluntarySuspendedPaymentPending:
                return true;
            default:
                return false;
        }
    }

    public boolean isVoluntaryNRP() {
        return this == VoluntaryMailed || this == VoluntarySuspended || this == TransferPendingVolNRP;
    }

    public String shortDescription() {
        return shortDescription;
    }
}
