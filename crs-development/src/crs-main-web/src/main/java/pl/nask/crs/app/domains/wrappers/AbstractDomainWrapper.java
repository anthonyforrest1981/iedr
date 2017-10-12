package pl.nask.crs.app.domains.wrappers;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DsmState;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.EntitySubcategory;

public abstract class AbstractDomainWrapper {

    private Domain domain;
    private DnsWrapper dnsWrapper;

    public AbstractDomainWrapper(Domain hDomain) {
        Validator.assertNotNull(hDomain, "historical domain");
        this.domain = hDomain;
    }

    protected Domain getWrappedDomain() {
        return domain;
    }

    /**
     * returns updated (if needed) domain object. use
     * {@link #getWrappedDomain()} to access plain domain object
     */
    public Domain getDomain() {
        // refresh dns-list...
        domain.setNameservers(getDnsWrapper().getNameservers());
        return domain;
    }

    public String getDomainHolder() {
        return StringEscapeUtils.escapeHtml(domain.getHolder());
    }

    public String getName() {
        return domain.getName();
    }

    public String getRemark() {
        String remark = domain.getRemark();
        return (remark == null || remark.trim().length() == 0) ? "(none)" : StringEscapeUtils.escapeHtml(remark);
    }

    public boolean isClikPaid() {
        return domain.isClikPaid();
    }

    public boolean getClikPaid() {
        return domain.isClikPaid();
    }

    public DnsWrapper getDnsWrapper() {
        if (domain == null)
            return null;
        if (dnsWrapper == null)
            dnsWrapper = new DnsWrapper(domain.getNameservers(), domain.getName());

        return dnsWrapper;
    }

    public String getHolder() {
        return domain.getHolder();
    }

    public EntityClass getHolderClass() {
        return domain.getHolderClass();
    }

    public EntityCategory getHolderCategory() {
        return domain.getHolderCategory();
    }

    public EntitySubcategory getHolderSubcategory() {
        EntitySubcategory subcategory = domain.getHolderSubcategory();
        return subcategory == null ? new EntitySubcategory() : subcategory;
    }

    public Account getResellerAccount() {
        return domain.getResellerAccount();
    }

    public List<Contact> getAdminContacts() {
        return domain.getAdminContacts();
    }

    public List<Contact> getTechContacts() {
        return domain.getTechContacts();
    }

    public List<Contact> getBillingContacts() {
        return domain.getBillingContacts();
    }

    public Date getRegistrationDate() {
        return domain.getRegistrationDate();
    }

    public Date getRenewalDate() {
        return domain.getRenewalDate();
    }

    public Date getChangeDate() {
        return domain.getChangeDate();
    }

    public Date getSuspensionDate() {
        return domain.getSuspensionDate();
    }

    public Date getDeletionDate() {
        return domain.getDeletionDate();
    }

    public DsmState getDsmState() {
        return domain.getDsmState();
    }

    public boolean isNrp() {
        return domain.getDsmState().getNrpStatus().isNRP();
    }

    public Date getLockingDate() {
        return domain.getLockingDate();
    }

    public Date getLockingRenewalDate() {
        return domain.getLockingRenewalDate();
    }
}
