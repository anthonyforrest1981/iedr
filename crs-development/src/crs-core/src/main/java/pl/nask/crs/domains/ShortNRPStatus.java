package pl.nask.crs.domains;

import java.util.Arrays;
import java.util.List;

public enum ShortNRPStatus {

    Active("Active"),
    InvoluntaryMailed("Involuntary Mailed"),
    InvoluntarySuspended("Involuntary Suspended"),
    VoluntaryMailed("Voluntary Mailed"),
    VoluntarySuspended("Voluntary Suspended"),
    NA("N/A");

    private final String value;

    ShortNRPStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ShortNRPStatus fromNRPStatus(NRPStatus nrpStatus) {
        switch (nrpStatus) {
            case Active:
            case TransferPendingActive:
            case InvoluntaryMailedPaymentPending:
            case InvoluntarySuspendedPaymentPending:
            case TransferPendingInvNRP:
            case TransferPendingVolNRP:
                return ShortNRPStatus.Active;
            case InvoluntaryMailed:
                return ShortNRPStatus.InvoluntaryMailed;
            case VoluntaryMailed:
                return ShortNRPStatus.VoluntaryMailed;
            case InvoluntarySuspended:
                return ShortNRPStatus.InvoluntarySuspended;
            case VoluntarySuspended:
                return ShortNRPStatus.VoluntarySuspended;
            case Deleted:
            case NA:
                return ShortNRPStatus.NA;
            default:
                throw new IllegalArgumentException("Cannot map " + nrpStatus + " onto ShortNRPStatus type");
        }
    }

    public List<NRPStatus> toNRPStatuses() {
        switch (this) {
            case Active:
                return Arrays.asList(NRPStatus.Active, NRPStatus.TransferPendingActive,
                        NRPStatus.InvoluntaryMailedPaymentPending, NRPStatus.InvoluntarySuspendedPaymentPending,
                        NRPStatus.TransferPendingInvNRP, NRPStatus.TransferPendingVolNRP);
            case InvoluntaryMailed:
                return Arrays.asList(NRPStatus.InvoluntaryMailed);
            case InvoluntarySuspended:
                return Arrays.asList(NRPStatus.InvoluntarySuspended);
            case VoluntaryMailed:
                return Arrays.asList(NRPStatus.VoluntaryMailed);
            case VoluntarySuspended:
                return Arrays.asList(NRPStatus.VoluntarySuspended);
            case NA:
                return Arrays.asList(NRPStatus.Deleted, NRPStatus.NA);
            default:
                throw new IllegalArgumentException("Cannot map " + this + " onto NRPStatus list");
        }
    }
}
