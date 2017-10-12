package pl.nask.crs.domains.search;

import pl.nask.crs.domains.CustomerType;
import pl.nask.crs.domains.DeletedDomain;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class DeletedDomainSearchCriteria extends AbstractPlainDomainSearchCriteria<DeletedDomain> {

    private String billingNH;
    private CustomerType type;
    private Long accountId;

    public DeletedDomainSearchCriteria() {}

    public String getBillingNH() {
        return billingNH;
    }

    public void setBillingNH(String billingNH) {
        this.billingNH = billingNH;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
