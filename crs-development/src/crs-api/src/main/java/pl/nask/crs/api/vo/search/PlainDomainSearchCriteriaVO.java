package pl.nask.crs.api.vo.search;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.PlainDomain;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.ShortNRPStatus;
import pl.nask.crs.domains.search.AbstractPlainDomainSearchCriteria;
import pl.nask.crs.domains.search.PlainDomainSearchCriteria;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class PlainDomainSearchCriteriaVO {

    private String domainName;
    private String domainHolder;
    private Date transferDate;
    private Date transferFrom;
    private Date transferTo;
    private Date registrationDate;
    private Date registrationFrom;
    private Date registrationTo;
    private Date renewalDate;
    private Date renewalFrom;
    private Date renewalTo;
    private Date lockFrom;
    private Date lockTo;
    private Date lockRenewalFrom;
    private Date lockRenewalTo;
    private String billingNH;
    private Long accountId;
    private List<RenewalMode> renewalModes;
    private List<DomainHolderType> holderTypes;

    private ShortNRPStatus shortNRPStatus;
    private Boolean activeFlag;
    private Boolean lockingActive;

    protected void setPlainDomainSearchCriteriaFields(AbstractPlainDomainSearchCriteria<? extends PlainDomain> criteria) {
        criteria.setDomainName(domainName);
        criteria.setDomainHolder(domainHolder);
        criteria.setTransferDate(transferDate);
        criteria.setTransferFrom(transferFrom);
        criteria.setTransferTo(transferTo);
        criteria.setRegistrationDate(registrationDate);
        criteria.setRegistrationFrom(registrationFrom);
        criteria.setRegistrationTo(registrationTo);
        criteria.setRenewalDate(renewalDate);
        criteria.setRenewalFrom(renewalFrom);
        criteria.setRenewalTo(renewalTo);
        criteria.setShortNRPStatus(shortNRPStatus);
        criteria.setLockFrom(lockFrom);
        criteria.setLockTo(lockTo);
        criteria.setLockRenewalFrom(lockRenewalFrom);
        criteria.setLockRenewalTo(lockRenewalTo);
        criteria.setBillingNH(billingNH);
        criteria.setAccountId(accountId);
        criteria.setRenewalModes(renewalModes);
        criteria.setHolderTypes(holderTypes);
        criteria.setShortNRPStatus(shortNRPStatus);
        criteria.setActiveFlag(activeFlag);
        criteria.setLockingActive(lockingActive);
    }

    public PlainDomainSearchCriteria toPlainDomainSearchCriteria() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        setPlainDomainSearchCriteriaFields(criteria);
        return criteria;
    }

}
