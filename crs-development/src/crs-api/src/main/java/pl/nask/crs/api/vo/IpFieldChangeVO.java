package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.ticket.operation.IpFailureReason;
import pl.nask.crs.ticket.operation.IpFieldChange;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class IpFieldChangeVO {

    private String oldValue;
    private String newValue;
    private String failureReason;

    public IpFieldChangeVO() {}

    public IpFieldChangeVO(IpFieldChange dc) {
        this.oldValue = dc.getOldValue();
        this.newValue = dc.getNewValue();
        if (dc.getFailureReason() != null) {
            this.failureReason = dc.getFailureReason().getDescription();
        }
    }

}
