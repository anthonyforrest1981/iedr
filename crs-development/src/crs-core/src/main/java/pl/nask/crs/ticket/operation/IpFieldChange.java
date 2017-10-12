package pl.nask.crs.ticket.operation;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import java.util.Objects;

import org.apache.commons.lang.ObjectUtils;

public class IpFieldChange implements DomainFieldChange {

    private /*>>>@Nullable*/ String oldValue;
    private /*>>>@Nullable*/ String newValue;
    private /*>>>@Nullable*/ IpFailureReason failureReason;

    public IpFieldChange(/*>>>@Nullable*/ String oldValue, /*>>>@Nullable*/ String newValue) {
        this(oldValue, newValue, null);
    }

    public IpFieldChange(/*>>>@Nullable*/ String oldValue, /*>>>@Nullable*/ String newValue,
            /*>>>@Nullable*/ IpFailureReason failureReason) {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.failureReason = failureReason;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ String getOldValue() {
        return oldValue;
    }

    public void setOldValue(/*>>>@Nullable*/ String oldValue) {
        this.oldValue = oldValue;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ String getNewValue() {
        return newValue;
    }

    public void setNewValue(/*>>>@Nullable*/ String newValue) {
        this.newValue = newValue;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ IpFailureReason getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(/*>>>@Nullable*/ IpFailureReason failureReason) {
        this.failureReason = failureReason;
    }

    /*>>>@Pure*/
    @Override
    public boolean isModification() {
        return !Objects.equals(oldValue, newValue);
    }

    /*>>>@Pure*/
    @Override
    public boolean isFailed() {
        return failureReason != null;
    }
}
