package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleDomainFieldChangeVO<T> {

    private T oldValue;
    private T newValue;
    private String failureReason;

    public SimpleDomainFieldChangeVO() {}

    public SimpleDomainFieldChangeVO(SimpleDomainFieldChange<T> dc) {
        this.newValue = dc.getNewValue();
        if (dc.getFailureReason() != null) {
            this.failureReason = dc.getFailureReason().getDescription();
        }
        this.oldValue = dc.getCurrentValue();
    }

    public SimpleDomainFieldChangeVO(T currentValue, T newValue, FailureReason fr) {
        this.newValue = newValue;
        this.oldValue = currentValue;
        if (fr != null) {
            this.failureReason = fr.getDescription();
        }
    }
}
