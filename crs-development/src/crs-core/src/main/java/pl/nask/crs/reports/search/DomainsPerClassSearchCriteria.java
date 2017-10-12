package pl.nask.crs.reports.search;

import java.util.Date;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.reports.DomainsPerClass;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DomainsPerClassSearchCriteria implements SearchCriteria<DomainsPerClass> {

    private Long holderClassId;
    private Long holderCategoryId;
    private Date from;
    private Date to;
    private Long accountId;
    private String billingNH;

    public Long getHolderClassId() {
        return holderClassId;
    }

    public void setHolderClassId(Long holderClassId) {
        this.holderClassId = holderClassId;
    }

    public Long getHolderCategoryId() {
        return holderCategoryId;
    }

    public void setHolderCategoryId(Long holderCategoryId) {
        this.holderCategoryId = holderCategoryId;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = DateUtils.startOfDay(from);
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = DateUtils.endOfDay(to);
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getBillingNH() {
        return billingNH;
    }

    public void setBillingNH(String billingNH) {
        this.billingNH = billingNH;
    }
}
