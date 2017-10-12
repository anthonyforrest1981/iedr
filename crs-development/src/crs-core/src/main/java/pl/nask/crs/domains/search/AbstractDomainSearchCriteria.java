package pl.nask.crs.domains.search;

import java.util.List;

import pl.nask.crs.contacts.ContactType;
import pl.nask.crs.domains.CustomerType;
import pl.nask.crs.domains.Domain;

public abstract class AbstractDomainSearchCriteria<T extends Domain> extends AbstractPlainDomainSearchCriteria<T> {

    private String exactDomainHolder;
    private String nicHandle;
    // can be used only if nicHandle is set
    private List<ContactType> contactType;

    private String secondContact;
    private List<ContactType> secondContactType;

    private CustomerType type;

    private Integer renewalMonth;

    private Boolean authCodeFromPortal = false;

    public String getExactDomainHolder() {
        return exactDomainHolder;
    }

    public void setExactDomainHolder(String exactDomainHolder) {
        this.exactDomainHolder = getNotEmptyString(exactDomainHolder);
    }

    public String getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(String nicHandle) {
        this.nicHandle = getNotEmptyString(nicHandle);
    }

    public List<ContactType> getContactType() {
        return contactType;
    }

    public void setContactType(List<ContactType> contactType) {
        this.contactType = contactType;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    public Integer getRenewalMonth() {
        return renewalMonth;
    }

    public void setRenewalMonth(Integer renewalMonth) {
        this.renewalMonth = renewalMonth;
    }

    @Override
    public void removeNullValues() {
        super.removeNullValues();
        if (contactType != null) {
            contactType.remove(null);
        }
        if (secondContactType != null) {
            secondContactType.remove(null);
        }
    }

    public void setSecondContact(String nh) {
        this.secondContact = nh;
    }

    public void setSecondContactType(List<ContactType> secondContactType) {
        this.secondContactType = secondContactType;
    }

    public String getSecondContact() {
        return secondContact;
    }

    public List<ContactType> getSecondContactType() {
        return secondContactType;
    }

    public Boolean getAuthCodeFromPortal() {
        return authCodeFromPortal == null ? false : authCodeFromPortal;
    }

    public void setAuthCodeFromPortal(Boolean authCodeFromPortal) {
        this.authCodeFromPortal = authCodeFromPortal;
    }

}
