package pl.nask.crs.domains.search;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import java.util.*;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.*;

public abstract class AbstractPlainDomainSearchCriteria<T extends PlainDomain> implements SearchCriteria<T> {

    private /*>>>@Nullable*/ String domainName;
    private /*>>>@Nullable*/ String domainHolder;
    private /*>>>@Nullable*/ Date registrationDate;
    private /*>>>@Nullable*/ Date registrationFrom;
    private /*>>>@Nullable*/ Date registrationTo;
    private /*>>>@Nullable*/ Date renewalDate;
    private /*>>>@Nullable*/ Date renewalFrom;
    private /*>>>@Nullable*/ Date renewalTo;
    private /*>>>@Nullable*/ Date transferDate;
    private /*>>>@Nullable*/ Date transferFrom;
    private /*>>>@Nullable*/ Date transferTo;
    private /*>>>@Nullable*/ Date suspensionFrom;
    private /*>>>@Nullable*/ Date suspensionTo;
    private /*>>>@Nullable*/ Date deletionFrom;
    private /*>>>@Nullable*/ Date deletionTo;
    private /*>>>@Nullable*/ Date authcExpFrom;
    private /*>>>@Nullable*/ Date authcExpTo;
    private /*>>>@Nullable*/ Date lockFrom;
    private /*>>>@Nullable*/ Date lockTo;
    private /*>>>@Nullable*/ Date lockRenewalFrom;
    private /*>>>@Nullable*/ Date lockRenewalTo;
    private /*>>>@Nullable*/ Long holderClassId;
    private /*>>>@Nullable*/ Long holderCategoryId;
    private /*>>>@Nullable*/ String holderClassName;
    private /*>>>@Nullable*/ String holderCategoryName;
    private /*>>>@Nullable*/ String billingNH;
    private /*>>>@Nullable*/ Long accountId;

    private /*>>>@Nullable*/ ShortNRPStatus shortNRPStatus;
    private /*>>>@Nullable*/ List<NRPStatus> nrpStatuses;
    private /*>>>@Nullable*/ List<RenewalMode> renewalModes;
    private /*>>>@Nullable*/ List<DomainHolderType> holderTypes;
    private /*>>>@Nullable*/ List<SecondaryMarketStatus> secondaryMarketStatuses;
    private /*>>>@Nullable*/ Boolean activeFlag;

    private /*>>>@Nullable*/ Boolean locked;
    private /*>>>@Nullable*/ Boolean lockingActive;


    public /*>>>@Nullable*/ String getDomainName() {
        return domainName;
    }

    public void setDomainName(/*>>>@Nullable*/ String domainName) {
        this.domainName = domainName;
    }

    public /*>>>@Nullable*/ String getDomainHolder() {
        return domainHolder;
    }

    public void setDomainHolder(/*>>>@Nullable*/ String domainHolder) {
        this.domainHolder = domainHolder;
    }

    public /*>>>@Nullable*/ Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(/*>>>@Nullable*/ Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public /*>>>@Nullable*/ Date getRegistrationFrom() {
        return registrationFrom;
    }

    public void setRegistrationFrom(/*>>>@Nullable*/ Date registrationFrom) {
        this.registrationFrom = DateUtils.startOfDay(registrationFrom);
    }

    public /*>>>@Nullable*/ Date getRegistrationTo() {
        return registrationTo;
    }

    public void setRegistrationTo(/*>>>@Nullable*/ Date registrationTo) {
        this.registrationTo = DateUtils.endOfDay(registrationTo);
    }

    public /*>>>@Nullable*/ Date getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(/*>>>@Nullable*/ Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    public /*>>>@Nullable*/ Date getRenewalFrom() {
        return renewalFrom;
    }

    public void setRenewalFrom(/*>>>@Nullable*/ Date renewalFrom) {
        this.renewalFrom = DateUtils.startOfDay(renewalFrom);
    }

    public /*>>>@Nullable*/ Date getRenewalTo() {
        return renewalTo;
    }

    public void setRenewalTo(/*>>>@Nullable*/ Date renewalTo) {
        this.renewalTo = DateUtils.endOfDay(renewalTo);
    }

    public /*>>>@Nullable*/ Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(/*>>>@Nullable*/ Date transferDate) {
        this.transferDate = transferDate;
    }

    public /*>>>@Nullable*/ Date getTransferFrom() {
        return transferFrom;
    }

    public void setTransferFrom(/*>>>@Nullable*/ Date transferFrom) {
        this.transferFrom = DateUtils.startOfDay(transferFrom);
    }

    public /*>>>@Nullable*/ Date getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(/*>>>@Nullable*/ Date transferTo) {
        this.transferTo = DateUtils.endOfDay(transferTo);
    }

    public /*>>>@Nullable*/ Date getSuspensionFrom() {
        return suspensionFrom;
    }

    public void setSuspensionFrom(/*>>>@Nullable*/ Date suspensionFrom) {
        this.suspensionFrom = DateUtils.startOfDay(suspensionFrom);
    }

    public /*>>>@Nullable*/ Date getSuspensionTo() {
        return suspensionTo;
    }

    public void setSuspensionTo(/*>>>@Nullable*/ Date suspensionTo) {
        this.suspensionTo = DateUtils.endOfDay(suspensionTo);
    }

    public /*>>>@Nullable*/ Date getDeletionFrom() {
        return deletionFrom;
    }

    public void setDeletionFrom(/*>>>@Nullable*/ Date deletionFrom) {
        this.deletionFrom = DateUtils.startOfDay(deletionFrom);
    }

    public /*>>>@Nullable*/ Date getDeletionTo() {
        return deletionTo;
    }

    public void setDeletionTo(/*>>>@Nullable*/ Date deletionTo) {
        this.deletionTo = DateUtils.endOfDay(deletionTo);
    }

    public /*>>>@Nullable*/ Date getAuthcExpFrom() {
        return authcExpFrom;
    }

    public void setAuthcExpFrom(/*>>>@Nullable*/ Date authcExpFrom) {
        this.authcExpFrom = DateUtils.startOfDay(authcExpFrom);
    }

    public /*>>>@Nullable*/ Date getAuthcExpTo() {
        return authcExpTo;
    }

    public void setAuthcExpTo(/*>>>@Nullable*/ Date authcExpTo) {
        this.authcExpTo = DateUtils.endOfDay(authcExpTo);
    }

    public /*>>>@Nullable*/ Date getLockFrom() {
        return lockFrom;
    }

    public void setLockFrom(/*>>>@Nullable*/ Date lockFrom) {
        this.lockFrom = DateUtils.startOfDay(lockFrom);
    }

    public /*>>>@Nullable*/ Date getLockTo() {
        return lockTo;
    }

    public void setLockTo(/*>>>@Nullable*/ Date lockTo) {
        this.lockTo = DateUtils.endOfDay(lockTo);
    }

    public /*>>>@Nullable*/ Date getLockRenewalFrom() {
        return lockRenewalFrom;
    }

    public void setLockRenewalFrom(/*>>>@Nullable*/ Date lockRenewalFrom) {
        this.lockRenewalFrom = DateUtils.startOfDay(lockRenewalFrom);
    }

    public /*>>>@Nullable*/ Date getLockRenewalTo() {
        return lockRenewalTo;
    }

    public void setLockRenewalTo(/*>>>@Nullable*/ Date lockRenewalTo) {
        this.lockRenewalTo = DateUtils.endOfDay(lockRenewalTo);
    }

    public /*>>>@Nullable*/ String getHolderClassName() {
        return holderClassName;
    }

    public void setHolderClassName(/*>>>@Nullable*/ String holderClassName) {
        this.holderClassName = getNotEmptyString(holderClassName);
    }

    public /*>>>@Nullable*/ String getHolderCategoryName() {
        return holderCategoryName;
    }

    public void setHolderCategoryName(/*>>>@Nullable*/ String holderCategoryName) {
        this.holderCategoryName = getNotEmptyString(holderCategoryName);
    }

    public /*>>>@Nullable*/ Long getHolderClassId() {
        return holderClassId;
    }

    public void setHolderClassId(/*>>>@Nullable*/ Long holderClassId) {
        this.holderClassId = holderClassId;
    }

    public /*>>>@Nullable*/ Long getHolderCategoryId() {
        return holderCategoryId;
    }

    public void setHolderCategoryId(/*>>>@Nullable*/ Long holderCategoryId) {
        this.holderCategoryId = holderCategoryId;
    }

    public /*>>>@Nullable*/ String getBillingNH() {
        return billingNH;
    }

    public void setBillingNH(/*>>>@Nullable*/ String billingNH) {
        this.billingNH = billingNH;
    }

    public /*>>>@Nullable*/ Long getAccountId() {
        return accountId;
    }

    public void setAccountId(/*>>>@Nullable*/ Long accountId) {
        this.accountId = accountId;
    }

    public /*>>>@Nullable*/ ShortNRPStatus getShortNRPStatus() {
        return shortNRPStatus;
    }

    public void setShortNRPStatus(/*>>>@Nullable*/ ShortNRPStatus shortNRPStatus) {
        this.shortNRPStatus = shortNRPStatus;
    }

    public void setActive(/*>>>@Nullable*/ Boolean active) {
        this.nrpStatuses = NRPStatus.getActiveList(active);
    }

    public /*>>>@Nullable*/ List<NRPStatus> getNrpStatuses() {
        return nrpStatuses;
    }

    public void setNrpStatuses(NRPStatus... nrpStatuses) {
        this.nrpStatuses = new ArrayList<>(Arrays.asList(nrpStatuses));
    }

    public void setNrpStatus(/*>>>@Nullable*/ String statusCode) {
        if (!Validator.isEmpty(statusCode)) {
            this.setNrpStatuses(NRPStatus.forCode(statusCode));
        }
    }

    public void setActiveFlag(/*>>>@Nullable*/ Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public /*>>>@Nullable*/ Boolean getActiveFlag() {
        return activeFlag;
    }

    public void filterValues() {
        removeNullValues();
        if (nrpStatuses == null || nrpStatuses.size() == 0) {
            setActive(activeFlag);
        }
        if (shortNRPStatus != null) {
            nrpStatuses = shortNRPStatus.toNRPStatuses();
        }
    }

    public void removeNRPStatus(NRPStatus... nrpStatuses) {
        if (this.nrpStatuses != null && nrpStatuses.length != 0) {
            for (NRPStatus toRemove : nrpStatuses) {
                this.nrpStatuses.remove(toRemove);
            }
        }
    }

    public void setDomainRenewalModes(RenewalMode... renewalModes) {
        if (renewalModes == null) {
            this.renewalModes = null;
        } else {
            this.renewalModes = Arrays.asList(renewalModes);
        }
    }

    public void setRenewalModes(/*>>>@Nullable*/ List<RenewalMode> modes) {
        this.renewalModes = modes;
    }

    public void setRenewalMode(/*>>>@Nullable*/ String renewalCode) {
        if (!Validator.isEmpty(renewalCode)) {
            this.renewalModes = Collections.singletonList(RenewalMode.forCode(renewalCode));
        }
    }

    public /*>>>@Nullable*/ List<RenewalMode> getRenewalModes() {
        return renewalModes;
    }

    public void setSecondaryMarketStatuses(SecondaryMarketStatus... secondaryMarketStatuses) {
        this.secondaryMarketStatuses = new ArrayList<>(Arrays.asList(secondaryMarketStatuses));
    }

    public /*>>>@Nullable*/ List<SecondaryMarketStatus> getSecondaryMarketStatuses() {
        return secondaryMarketStatuses;
    }

    public void setHolderTypes(/*>>>@Nullable*/ List<DomainHolderType> holderTypes) {
        this.holderTypes = holderTypes;
    }

    public void setDomainHolderTypes(DomainHolderType... holderType) {
        if (holderType == null) {
            this.holderTypes = null;
        } else {
            this.holderTypes = Arrays.asList(holderType);
        }
    }

    public void setHolderType(/*>>>@Nullable*/ String holderCode) {
        if (!Validator.isEmpty(holderCode)) {
            this.holderTypes = Collections.singletonList(DomainHolderType.forCode(holderCode));
        }
    }

    public /*>>>@Nullable*/ List<DomainHolderType> getHolderTypes() {
        return holderTypes;
    }

    public /*>>>@Nullable*/ Boolean isLocked() {
        return locked;
    }

    public void setLocked(/*>>>@Nullable*/ Boolean locked) {
        this.locked = locked;
    }

    public /*>>>@Nullable*/ Boolean isLockingActive() {
        return lockingActive;
    }

    public void setLockingActive(/*>>>@Nullable*/ Boolean lockingActive) {
        this.lockingActive = lockingActive;
    }

    public void removeNullValues() {
        if (nrpStatuses != null) {
            nrpStatuses.remove(null);
        }
        if (renewalModes != null) {
            renewalModes.remove(null);
        }
        if (holderTypes != null) {
            holderTypes.remove(null);
        }
        if (secondaryMarketStatuses != null) {
            secondaryMarketStatuses.remove(null);
        }
    }

    /*>>>@Pure*/
    protected /*>>>@Nullable*/ String getNotEmptyString(/*>>>@Nullable*/ String val) {
        if (Validator.isEmpty(val)) {
            return null;
        } else {
            return val;
        }
    }

}
