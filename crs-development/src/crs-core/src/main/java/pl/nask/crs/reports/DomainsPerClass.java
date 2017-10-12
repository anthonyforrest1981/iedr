package pl.nask.crs.reports;

import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DomainsPerClass implements Report {

    private EntityClass domainClass;
    private EntityCategory domainCategory;
    private long domainCount;
    private String accountName;
    private String billNHId;

    public EntityClass getDomainClass() {
        return domainClass;
    }

    public void setDomainClass(EntityClass domainClass) {
        this.domainClass = domainClass;
    }

    public EntityCategory getDomainCategory() {
        return domainCategory;
    }

    public void setDomainCategory(EntityCategory domainCategory) {
        this.domainCategory = domainCategory;
    }

    public long getDomainCount() {
        return domainCount;
    }

    public void setDomainCount(long domainCount) {
        this.domainCount = domainCount;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBillNHId() {
        return billNHId;
    }

    public void setBillNHId(String billNHId) {
        this.billNHId = billNHId;
    }

    @Override
    public String toString() {
        return "DomainsPerClass{" + "domainClass=" + domainClass.getId() + ", domainCategory=" + domainCategory.getId()
                + ", domainCount=" + domainCount + ", accountName='" + accountName + '\'' + ", billNHId='" + billNHId
                + '\'' + '}';
    }
}
