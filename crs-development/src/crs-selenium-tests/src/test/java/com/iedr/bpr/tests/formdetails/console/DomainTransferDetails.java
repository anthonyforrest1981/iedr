package com.iedr.bpr.tests.formdetails.console;

import com.iedr.bpr.tests.formdetails.DomainContactFormDetails;
import com.iedr.bpr.tests.formdetails.NameserverFormDetails;
import com.iedr.bpr.tests.utils.User;

public class DomainTransferDetails {

    private String authcode;
    private DomainContactFormDetails contactDetails;
    private NameserverFormDetails dnsDetails;
    private PaymentDetails paymentMethod;
    private int paymentPeriod;

    public DomainTransferDetails(String authcode, DomainContactFormDetails contactDetails,
            NameserverFormDetails dnsDetails, PaymentDetails paymentMethod, int paymentPeriod) {
        setAuthcode(authcode);
        setContactDetails(contactDetails);
        setDnsDetails(dnsDetails);
        setPaymentMethod(paymentMethod);
        setPaymentPeriod(paymentPeriod);
    }

    public DomainTransferDetails(String domainName, User user, String authcode, PaymentDetails paymentMethod,
            int paymentPeriod) {
        this(authcode, new DomainContactFormDetails(user), new NameserverFormDetails(domainName), paymentMethod,
                paymentPeriod);
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
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

    public PaymentDetails getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentDetails paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getPaymentPeriod() {
        return paymentPeriod;
    }

    public void setPaymentPeriod(int paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

}
