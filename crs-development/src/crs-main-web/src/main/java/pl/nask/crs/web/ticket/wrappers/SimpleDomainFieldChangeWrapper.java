package pl.nask.crs.web.ticket.wrappers;

import java.util.Arrays;
import java.util.List;

import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

public class SimpleDomainFieldChangeWrapper<T> {

    private SimpleDomainFieldChange<T> orig;
    private final DomainOperationType opType;

    public SimpleDomainFieldChangeWrapper(SimpleDomainFieldChange<T> orig, DomainOperationType opType) {
        this.orig = orig;
        this.opType = opType;
    }

    protected SimpleDomainFieldChange<T> getOrig() {
        return orig;
    }

    public List<FailureReason> getFailureReasonList() {
        return Arrays.asList(FailureReason.values());
    }

    public void setFailureReason(final String reasonName) {
        if (reasonName != null && !reasonName.isEmpty() && !reasonName.equalsIgnoreCase("null")) {
            orig.setFailureReason(FailureReason.valueOf(reasonName));
        } else {
            orig.setFailureReason(null);
        }
    }

    public void setFailuerReason(final FailureReason reason) {
        orig.setFailureReason(reason);
    }

    public FailureReason getFailureReason() {
        return orig.getFailureReason();
    }

    public T getNewValue() {
        return orig.getNewValue();
    }

    public void setNewValue(T value) {
        orig.setNewValue(value);
    }

    public boolean isModification() {
        return ((opType == DomainOperationType.MOD) || (opType == DomainOperationType.XFER)) && orig.isModification();
    }
}
