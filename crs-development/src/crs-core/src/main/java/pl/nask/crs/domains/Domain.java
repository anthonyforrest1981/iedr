package pl.nask.crs.domains;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.EntitySubcategory;

public class Domain extends PlainDomain {

    private Account resellerAccount;

    private String remark;
    private boolean clikPaid;

    private Integer dateRoll = null;

    private Contact creator;

    private List<Contact> adminContacts = new ArrayList<>();
    private List<Contact> techContacts = new ArrayList<>();
    private List<Contact> billingContacts = new ArrayList<>();

    private List<Nameserver> nameservers = new ArrayList<>();

    public Domain(String name, String holder, EntityClass holderClass, EntityCategory holderCategory,
            EntitySubcategory holderSubcategory, Contact creator, Account resellerAccount, Date registrationDate,
            Date renewalDate, String remark, Date changeDate, boolean clikPaid, List<Contact> techContacts,
            List<Contact> billingContacts, List<Contact> adminContacts, List<Nameserver> nameservers, DsmState dsmState,
            boolean zonePublished, Date lockingDate, Date lockingRenewalDate) {
        super(name, holder, holderClass, holderCategory, holderSubcategory, registrationDate, renewalDate, changeDate,
                dsmState, zonePublished, lockingDate, lockingRenewalDate, getContactNic(billingContacts, 0),
                resellerAccount.getId());
        this.creator = creator;
        this.resellerAccount = resellerAccount;
        this.remark = remark;
        this.clikPaid = clikPaid;
        this.adminContacts = adminContacts;
        this.techContacts = techContacts;
        this.billingContacts = billingContacts;
        this.nameservers = nameservers;
        validate();
    }

    private void validate() {
        // Validator.assertNotEmpty(remark, "domain remark"); TODO: in database there are domains with empty remarks!!!
        Validator.assertNotNull(this.clikPaid, "clik paid");
        Validator.assertNotNull(this.adminContacts, "admin contacts");
        Validator.assertNotNull(this.techContacts, "tech contacts");
        Validator.assertNotNull(this.billingContacts, "billing contacts");
        Validator.assertNotNull(this.nameservers, "nameservers");
    }

    public Account getResellerAccount() {
        return resellerAccount;
    }

    public void setResellerAccount(Account resellerAccount) {
        this.resellerAccount = resellerAccount;
    }

    public Contact getCreator() {
        return creator;
    }

    public void setCreator(Contact creator) {
        this.creator = creator;
    }

    public List<Contact> getAdminContacts() {
        return adminContacts;
    }

    public List<Contact> getTechContacts() {
        return techContacts;
    }

    public List<Contact> getBillingContacts() {
        return billingContacts;
    }

    public void updateChangeDate() {
        setChangeDate(new Date());
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isClikPaid() {
        return clikPaid;
    }

    public void setClikPaid(boolean clikPaid) {
        this.clikPaid = clikPaid;
    }

    public List<Nameserver> getNameservers() {
        return nameservers;
    }

    public void updateRemark(String hostmasterHandle) {
        remark += " by " + hostmasterHandle + " on " + new Date();
    }

    public void setAdminContacts(List<Contact> adminContacts) {
        this.adminContacts = adminContacts;
    }

    public void setTechContacts(List<Contact> techContacts) {
        this.techContacts = techContacts;
    }

    public void setBillingContacts(List<Contact> billingContacts) {
        this.billingContacts = billingContacts;
    }

    public void setNameservers(List<Nameserver> nameservers) {
        this.nameservers = nameservers;
    }

    public Integer getDateRoll() {
        return dateRoll;
    }

    public void setDateRoll(Integer dateRoll) {
        this.dateRoll = dateRoll;
    }

    private static Contact getContact(List<Contact> list, int index) {
        if (list != null && list.size() > index) {
            return list.get(index);
        } else {
            return null;
        }
    }

    private static String getContactNic(List<Contact> list, int index) {
        if (list != null && list.size() > index) {
            Contact c = list.get(index);
            if (c == null) {
                return null;
            } else {
                return c.getNicHandle();
            }
        } else {
            return null;
        }
    }

    public Contact getBillingContact() {
        return getContact(billingContacts, 0);
    }

    public String getBillingContactNic() {
        return getContactNic(billingContacts, 0);
    }

    public String getFirstAdminContactNic() {
        return getContactNic(adminContacts, 0);
    }

    public String getSecondAdminContactNic() {
        return getContactNic(adminContacts, 1);
    }

    public Contact getFirstAdminContact() {
        return getContact(adminContacts, 0);
    }

    public Contact getSecondAdminContact() {
        return getContact(adminContacts, 1);
    }

    public String getTechContactNic() {
        return getContactNic(techContacts, 0);
    }

    public Contact getTechContact() {
        return getContact(techContacts, 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Domain domain = (Domain) o;

        if (name != null ? !name.equals(domain.name) : domain.name != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
