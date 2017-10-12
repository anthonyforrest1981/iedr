package pl.nask.crs.reports.search;

import java.util.Date;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.domains.CustomerType;
import pl.nask.crs.reports.ReportTimeGranulation;
import pl.nask.crs.reports.ReportTypeGranulation;
import pl.nask.crs.reports.TotalDomainsPerDate;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TotalDomainsPerDateCriteria implements SearchCriteria<TotalDomainsPerDate> {

    private Date registrationFrom;
    private Date registrationTo;

    //report granulation is required
    private ReportTimeGranulation reportTimeGranulation;
    private ReportTypeGranulation reportTypeGranulation;

    private CustomerType customerType;

    private Long holderClassId;
    private Long holderCategoryId;
    private Long accountId;

    public Date getRegistrationFrom() {
        return registrationFrom;
    }

    public void setRegistrationFrom(Date registrationFrom) {
        this.registrationFrom = DateUtils.startOfDay(registrationFrom);
    }

    public Date getRegistrationTo() {
        return registrationTo;
    }

    public void setRegistrationTo(Date registrationTo) {
        this.registrationTo = DateUtils.endOfDay(registrationTo);
    }

    public ReportTimeGranulation getReportTimeGranulation() {
        return reportTimeGranulation;
    }

    public void setReportTimeGranulation(ReportTimeGranulation reportTimeGranulation) {
        this.reportTimeGranulation = reportTimeGranulation;
    }

    public ReportTypeGranulation getReportTypeGranulation() {
        return reportTypeGranulation;
    }

    public void setReportTypeGranulation(ReportTypeGranulation reportTypeGranulation) {
        this.reportTypeGranulation = reportTypeGranulation;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
