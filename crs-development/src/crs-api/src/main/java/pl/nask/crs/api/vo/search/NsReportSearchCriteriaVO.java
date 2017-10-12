package pl.nask.crs.api.vo.search;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.domains.search.NsReportSearchCriteria;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class NsReportSearchCriteriaVO {

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

    public NsReportSearchCriteria toSearchCriteria() {
        NsReportSearchCriteria criteria = new NsReportSearchCriteria();
        criteria.setDomainName(domainName);
        criteria.setHolderName(holderName);
        criteria.setRegistrationFrom(registrationFrom);
        criteria.setRegistrationTo(registrationTo);
        criteria.setRenewalFrom(renewalFrom);
        criteria.setRenewalTo(renewalTo);
        criteria.setDnsName(dnsName);
        criteria.setDnsOrder(dnsOrder);
        criteria.setDnsIpv4Address(dnsIpv4Address);
        criteria.setDnsIpv6Address(dnsIpv6Address);
        return criteria;
    }

}
