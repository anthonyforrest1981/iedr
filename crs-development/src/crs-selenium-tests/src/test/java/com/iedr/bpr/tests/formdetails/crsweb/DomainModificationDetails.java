package com.iedr.bpr.tests.formdetails.crsweb;

import com.iedr.bpr.tests.formdetails.DomainContactFormDetails;
import com.iedr.bpr.tests.formdetails.NameserverFormDetails;
import com.iedr.bpr.tests.utils.User;

public class DomainModificationDetails {

    private String domainHolder;
    private String domainClass;
    private String domainCategory;
    private String remark;
    private DomainContactFormDetails contactDetails;
    private NameserverFormDetails dnsDetails;

    public DomainModificationDetails(String remark) {
        setRemark(remark);
    }

    public DomainModificationDetails(String domainHolder, String domainClass, String domainCategory, String remark,
            DomainContactFormDetails contactDetails, NameserverFormDetails dnsDetails) {
        setDomainHolder(domainHolder);
        setDomainClass(domainClass);
        setDomainCategory(domainCategory);
        setRemark(remark);
        setContactDetails(contactDetails);
        setDnsDetails(dnsDetails);
    }

    public DomainModificationDetails(User user, String domainName, String domainHolder, String domainClass,
            String domainCategory, String remark) {
        this(domainHolder, domainClass, domainCategory, remark, new DomainContactFormDetails(user),
                new NameserverFormDetails(domainName));
    }

    public String getDomainHolder() {
        return domainHolder;
    }

    public void setDomainHolder(String domainHolder) {
        this.domainHolder = domainHolder;
    }

    public String getDomainClass() {
        return domainClass;
    }

    public void setDomainClass(String domainClass) {
        this.domainClass = domainClass;
    }

    public String getDomainCategory() {
        return domainCategory;
    }

    public void setDomainCategory(String domainCategory) {
        this.domainCategory = domainCategory;
    }

    public DomainContactFormDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(DomainContactFormDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public NameserverFormDetails getDnsDetails() {
        return dnsDetails;
    }

    public void setDnsDetails(NameserverFormDetails dnsDetails) {
        this.dnsDetails = dnsDetails;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
