package pl.nask.crs.ticket.operation;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import pl.nask.crs.commons.utils.Validator;

/**
 * This class represent a change to a single domain field. It is used
 * by a <code>DomainOperation</code> to model a change request.
 *
 * @author Patrycja Wegrzynowicz
 * @param <T> the type of a field being to change; must define a valid <code>equals</code> method
 */
public class SimpleDomainFieldChange<T> implements DomainFieldChange {

    private /*>>>@Nullable*/ T currentValue;

    private /*>>>@Nullable*/ T newValue;

    private /*>>>@Nullable*/ FailureReason failureReason;

    public SimpleDomainFieldChange(/*>>>@Nullable*/ T currentValue, /*>>>@Nullable*/ T newValue) {
        this(currentValue, newValue, null);
    }

    public SimpleDomainFieldChange(/*>>>@Nullable*/ T currentValue, /*>>>@Nullable*/ T newValue, /*>>>@Nullable*/
            FailureReason failureReason) {
        this.currentValue = currentValue;
        this.newValue = newValue;
        this.failureReason = failureReason;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ T getCurrentValue() {
        return currentValue;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ T getNewValue() {
        return newValue;
    }

    public void setNewValue(/*>>>@Nullable*/ T newValue) {
        this.newValue = newValue;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ FailureReason getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(/*>>>@Nullable*/ FailureReason failureReason) {
        this.failureReason = failureReason;
    }

    /**
     * Indicates whether the change represents a modification of the current value.
     *
     * @return true if the new value differs from the current one, false otherwise
     */
    public boolean isModification() {
        if (currentValue == null && newValue == null) {
            // both values are null, so there is no modification
            return false;
        }

        if (currentValue instanceof String || newValue instanceof String) {
            String cv = (String) currentValue;
            String nv = (String) newValue;
            if (Validator.isEmpty(cv) && Validator.isEmpty(nv))
                return false;
            // ugly hack to compare strings case-insensitive
            return !(cv != null ? cv.equalsIgnoreCase(nv) : false);
        }

        if (currentValue == null || newValue == null) {
            // one of the values is null, so there is a modification
            return true;
        }
        // both values are set, then equals tells us whether or not there is a modification
        return !currentValue.equals(newValue);
    }

    public void populateValue(SimpleDomainFieldChange<T> change) {
        setNewValue(change.getNewValue());
    }

    public void populateFailureReason(SimpleDomainFieldChange<?> change) {
        setFailureReason(change.getFailureReason());
    }

    public boolean isFailed() {
        return failureReason != null;
    }

    @Override
    public String toString() {
        return "FieldChange[curr=" + currentValue + ", new=" + newValue + "]";
    }
}
