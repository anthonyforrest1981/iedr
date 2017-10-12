package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.contacts.Contact;
import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleDomainContactChangeVO {

    private ContactVO newValue;
    private ContactVO oldValue;
    private String failureReason;

    public SimpleDomainContactChangeVO() {}

    public SimpleDomainContactChangeVO(SimpleDomainFieldChange<Contact> c) {
        this.newValue = c.getNewValue() == null ? null : new ContactVO(c.getNewValue().getNicHandle(), c.getNewValue()
                .getName());
        this.oldValue = c.getCurrentValue() == null ? null : new ContactVO(c.getCurrentValue().getNicHandle(), c
                .getCurrentValue().getName());
        if (c.getFailureReason() != null) {
            this.failureReason = c.getFailureReason().getDescription();
        }
    }
}
