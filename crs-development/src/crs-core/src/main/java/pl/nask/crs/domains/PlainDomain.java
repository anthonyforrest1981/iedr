package pl.nask.crs.domains;

import java.util.Date;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.EntitySubcategory;

public class PlainDomain {

    protected String name;
    protected String holder;
    protected EntityClass holderClass;
    protected EntityCategory holderCategory;
    protected EntitySubcategory holderSubcategory;
    protected Date registrationDate;
    protected Date renewalDate;
    protected Date changeDate;
    protected Date transferDate;
    protected Date suspensionDate;
    protected Date deletionDate;
    protected Date lockingDate;
    protected Date lockingRenewalDate;
    protected DsmState dsmState;
    protected boolean zonePublished;
    protected String authCode;
    protected Date authCodeExpirationDate;
    protected Integer authCodePortalCount;
    protected String billingNH;
    protected Long accountId;

    public PlainDomain() {}

    public PlainDomain(String name, String holder, EntityClass holderClass, EntityCategory holderCategory,
            EntitySubcategory holderSubcategory, Date registrationDate, Date renewalDate, Date changeDate,
            DsmState dsmState, boolean zonePublished, Date lockingDate, Date lockingRenewalDate, String billingNH,
            Long accountId) {
        this.name = name;
        this.holder = holder;
        this.holderClass = holderClass;
        this.holderCategory = holderCategory;
        this.holderSubcategory = holderSubcategory;
        this.registrationDate = registrationDate;
        this.renewalDate = renewalDate;
        this.changeDate = changeDate;
        this.dsmState = dsmState;
        this.zonePublished = zonePublished;
        this.lockingDate = lockingDate;
        this.lockingRenewalDate = lockingRenewalDate;
        this.billingNH = billingNH;
        this.accountId = accountId;
        validate();
    }

    private void validate() {
        Validator.assertNotEmpty(this.name, "domain name");
        Validator.assertNotEmpty(this.holder, "domain holder");
        Validator.assertNotNull(this.holderClass, "domain holder class");
        Validator.assertNotNull(this.holderCategory, "domain holder category");
        Validator.assertNotNull(this.registrationDate, "domain registration date");
        Validator.assertNotNull(this.renewalDate, "domain renewal date");
        Validator.assertNotNull(this.changeDate, "domain creation date");
    }

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

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public Date getSuspensionDate() {
        return suspensionDate;
    }

    public void setSuspensionDate(Date suspensionDate) {
        this.suspensionDate = suspensionDate;
    }

    public Date getDeletionDate() {
        return deletionDate;
    }

    public void setDeletionDate(Date deletionDate) {
        this.deletionDate = deletionDate;
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

    public DsmState getDsmState() {
        return dsmState;
    }

    public void setDsmState(DsmState dsmState) {
        this.dsmState = dsmState;
    }

    public boolean isNRP() {
        return dsmState.getNrpStatus().isNRP();
    }


    public boolean isZonePublished() {
        return zonePublished;
    }

    public void setZonePublished(boolean zonePublished) {
        this.zonePublished = zonePublished;
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

    public String getBillingNH() {
        return billingNH;
    }

    public void setBillingNH(String billingNH) {
        this.billingNH = billingNH;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return name;
    }
}
