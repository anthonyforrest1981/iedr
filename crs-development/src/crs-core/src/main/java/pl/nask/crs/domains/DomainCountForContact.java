package pl.nask.crs.domains;

import pl.nask.crs.contacts.ContactType;

public class DomainCountForContact {
    private String nicHandle;
    private ContactType contactType;
    private Integer domainCount;

    public String getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(String nicHandle) {
        this.nicHandle = nicHandle;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public Integer getDomainCount() {
        return domainCount;
    }

    public void setDomainCount(Integer domainCount) {
        this.domainCount = domainCount;
    }
}
