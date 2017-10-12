package pl.nask.crs.domains.search;

import java.util.Date;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.domains.nameservers.NsReport;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class NsReportSearchCriteria implements SearchCriteria<NsReport> {

    private String domainName;
    private String holderName;
    private Date registrationFrom;
    private Date registrationTo;
    private Date renewalFrom;
    private Date renewalTo;
    private String dnsName;
    private Integer dnsOrder;
    private String dnsIpv4Address;
    private String dnsIpv6Address;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

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

    public Date getRenewalFrom() {
        return renewalFrom;
    }

    public void setRenewalFrom(Date renewalFrom) {
        this.renewalFrom = DateUtils.startOfDay(renewalFrom);
    }

    public Date getRenewalTo() {
        return renewalTo;
    }

    public void setRenewalTo(Date renewalTo) {
        this.renewalTo = DateUtils.endOfDay(renewalTo);
    }

    public String getDnsName() {
        return dnsName;
    }

    public void setDnsName(String dnsName) {
        this.dnsName = dnsName;
    }

    public Integer getDnsOrder() {
        return dnsOrder;
    }

    public void setDnsOrder(Integer dnsOrder) {
        this.dnsOrder = dnsOrder;
    }

    public String getDnsIpv4Address() {
        return dnsIpv4Address;
    }

    public void setDnsIpv4Address(String dnsIpv4Address) {
        this.dnsIpv4Address = dnsIpv4Address;
    }

    public String getDnsIpv6Address() {
        return dnsIpv6Address;
    }

    public void setDnsIpv6Address(String dnsIpv6Address) {
        this.dnsIpv6Address = dnsIpv6Address;
    }
}
