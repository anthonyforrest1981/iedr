package pl.nask.crs.api.vo;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.domains.nameservers.NsReport;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NsReportVO {

    private String billingNH;
    private String domainName;
    private String holderName;
    private Date registrationDate;
    private Date renewalDate;
    private String dnsName;
    private Integer dnsOrder;
    private String dnsIpv4Address;
    private String dnsIpv6Address;

    public NsReportVO() {}

    public NsReportVO(NsReport nsReport) {
        this.billingNH = nsReport.getBillingNH();
        this.domainName = nsReport.getDomainName();
        this.holderName = nsReport.getHolderName();
        this.registrationDate = nsReport.getRegistrationDate();
        this.renewalDate = nsReport.getRenewalDate();
        this.dnsName = nsReport.getDnsName();
        this.dnsOrder = nsReport.getDnsOrder();
        this.dnsIpv4Address = nsReport.getDnsIpv4Address();
        this.dnsIpv6Address = nsReport.getDnsIpv6Address();
    }

}
