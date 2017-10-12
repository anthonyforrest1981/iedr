package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.contacts.ContactType;
import pl.nask.crs.domains.DomainCountForContact;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DomainCountForContactVO {
    private String nicHandle;
    private ContactType contactType;
    private Integer domainCount;

    public DomainCountForContactVO() {}

    public DomainCountForContactVO(DomainCountForContact summary) {
        this.nicHandle = summary.getNicHandle();
        this.contactType = summary.getContactType();
        this.domainCount = summary.getDomainCount();
    }
}
