package pl.nask.crs.domains.dao.ibatis.objects;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.contacts.ContactType;
import pl.nask.crs.contacts.dao.ibatis.objects.InternalContact;
import pl.nask.crs.domains.DsmState;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.EntitySubcategory;

public class InternalDomain {

    private String name;

    private String holder;
    private EntityClass holderClass;
    private EntityCategory holderCategory;
    private EntitySubcategory holderSubcategory;

    private Long resellerAccountId;
    private String resellerAccountName;
    private String resellerAccountBillingContact;

    private Date registrationDate;
    private Date renewalDate;
    private Date changeDate;

    private String remark;

    private List<InternalContact> contacts;

    private boolean clikPaid;

    private List<Nameserver> nameservers;

    private Integer dateRoll;

    private DsmState dsmState;

    private Date suspensionDate;

    private Date deletionDate;

    private boolean zonePublished;

    private Date transferDate;

    private String authCode;
    private Date authCodeExpirationDate;
    private Integer authCodePortalCount;

    private Date lockingDate;
    private Date lockingRenewalDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public EntityClass getHolderClass() {
        return holderClass;
    }

    public void setHolderClass(EntityClass holderClass) {
        this.holderClass = holderClass;
    }

    public EntityCategory getHolderCategory() {
        return holderCategory;
    }

    public void setHolderCategory(EntityCategory holderCategory) {
        this.holderCategory = holderCategory;
    }

    public EntitySubcategory getHolderSubcategory() {
        return holderSubcategory;
    }

    public void setHolderSubcategory(EntitySubcategory holderSubcategory) {
        this.holderSubcategory = holderSubcategory;
    }

    public Long getResellerAccountId() {
        return resellerAccountId;
    }

    public void setResellerAccountId(Long resellerAccountId) {
        this.resellerAccountId = resellerAccountId;
    }

    public String getResellerAccountName() {
        return resellerAccountName;
    }

    public void setResellerAccountName(String resellerAccountName) {
        this.resellerAccountName = resellerAccountName;
    }

    public String getResellerAccountBillingContact() {
        return resellerAccountBillingContact;
    }

    public void setResellerAccountBillingContact(String resellerAccountBillingContact) {
        this.resellerAccountBillingContact = resellerAccountBillingContact;
    }

    public List<Nameserver> getNameservers() {
        return nameservers == null ? Collections.EMPTY_LIST : nameservers;
    }

    public void setNameservers(List<Nameserver> nameservers) {
        this.nameservers = nameservers;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Date getAuthCodeExpirationDate() {
        return authCodeExpirationDate;
    }

    public void setAuthCodeExpirationDate(Date authCodeExpirationDate) {
        this.authCodeExpirationDate = authCodeExpirationDate;
    }

    public Integer getAuthCodePortalCount() {
        return authCodePortalCount;
    }

    public void setAuthCodePortalCount(Integer authCodePortalCount) {
        this.authCodePortalCount = authCodePortalCount;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public boolean isClikPaid() {
        return clikPaid;
    }

    public void setClikPaid(boolean clikPaid) {
        this.clikPaid = clikPaid;
    }

    public List<InternalContact> getContacts() {
        return contacts == null ? Collections.EMPTY_LIST : contacts;
    }

    public void setContacts(List<InternalContact> contacts) {
        this.contacts = contacts;
    }

    public List<InternalContact> getAdminContacts() {
        return getContactsOfType(ContactType.ADMIN);
    }

    public List<InternalContact> getTechContacts() {
        return getContactsOfType(ContactType.TECH);
    }

    public List<InternalContact> getBillingContacts() {
        return getContactsOfType(ContactType.BILLING);
    }

    public InternalContact getCreator() {
        final List<InternalContact> creatorContacts = getContactsOfType(ContactType.CREATOR);
        if (creatorContacts.isEmpty())
            return null;
        else
            return creatorContacts.get(0);
    }

    private List<InternalContact> getContactsOfType(ContactType type) {
        List<InternalContact> ret = new ArrayList<InternalContact>();
        for (InternalContact contact : getContacts()) {
            if (!contact.isEmpty() && type == contact.getType()) {
                ret.add(contact);
            }
        }
        return ret;
    }

    public /*>>>@Nullable*/ Nameserver getNameserver(String name) {
        for (Nameserver ns : getNameservers()) {
            if (name.equalsIgnoreCase(ns.getName()))
                return ns;
        }
        return null;
    }

    public Account getResellerAccount() {
        return resellerAccountId == null ? null : new Account(resellerAccountId, resellerAccountName,
                resellerAccountBillingContact);
    }

    public Integer getDateRoll() {
        return dateRoll;
    }

    public void setDateRoll(Integer dateRoll) {
        this.dateRoll = dateRoll;
    }

    public DsmState getDsmState() {
        return dsmState;
    }

    public void setDsmState(DsmState dsmState) {
        this.dsmState = dsmState;
    }

    public void setSuspensionDate(Date suspensionDate) {
        this.suspensionDate = suspensionDate;
    }

    public Date getSuspensionDate() {
        return suspensionDate;
    }

    public void setDeletionDate(Date deletionDate) {
        this.deletionDate = deletionDate;
    }

    public Date getDeletionDate() {
        return deletionDate;
    }

    public boolean isZonePublished() {
        return zonePublished;
    }

    public void setZonePublished(boolean zonePublished) {
        this.zonePublished = zonePublished;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public Date getLockingDate() {
        return lockingDate;
    }

    public void setLockingDate(Date lockingDate) {
        this.lockingDate = lockingDate;
    }

    public Date getLockingRenewalDate() {
        return lockingRenewalDate;
    }

    public void setLockingRenewalDate(Date lockingRenewalDate) {
        this.lockingRenewalDate = lockingRenewalDate;
    }
}
