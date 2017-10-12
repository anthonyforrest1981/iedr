package com.iedr.bpr.tests.formdetails.console;

import java.util.List;

import com.iedr.bpr.tests.formdetails.DomainContactFormDetails;
import com.iedr.bpr.tests.formdetails.NameserverFormDetails;
import com.iedr.bpr.tests.utils.DomainNameServer;
import com.iedr.bpr.tests.utils.User;

public class DomainRegistrationDetails {

    private String domainHolder;
    private String ownerType;
    private boolean basedInIreland;
    private String remarks;
    private DomainContactFormDetails contactDetails;
    private NameserverFormDetails dnsDetails;
    private PaymentDetails paymentDetails;
    private int paymentPeriod;
    private boolean validatePrice;

    public DomainRegistrationDetails(String domainHolder, String ownerType, boolean basedInIreland, String remarks,
            DomainContactFormDetails contactDetails, NameserverFormDetails dnsDetails, PaymentDetails paymentDetails,
            int paymentPeriod, boolean validatePrice) {
        setDomainHolder(domainHolder);
        setOwnerType(ownerType);
        setBasedInIreland(basedInIreland);
        setRemarks(remarks);
        setContactDetails(contactDetails);
        setDnsDetails(dnsDetails);
        setPaymentDetails(paymentDetails);
        setPaymentPeriod(paymentPeriod);
        setValidatePrice(validatePrice);
        if ("Charity".equals(ownerType) && basedInIreland) {
            assert paymentDetails.getMethod() == PaymentDetails.PaymentMethod.CHARITY;
        } else {
            assert paymentDetails.getMethod() != PaymentDetails.PaymentMethod.CHARITY;
        }
    }

    public DomainRegistrationDetails(DomainContactFormDetails contactDetails, NameserverFormDetails dnsDetails,
            PaymentDetails paymentDetails, int paymentPeriod, boolean validatePrice) {
        this("Test Holder 0001",
                PaymentDetails.PaymentMethod.CHARITY == paymentDetails.getMethod() ? "Charity" : "Company", true,
                "Test remark", contactDetails, dnsDetails, paymentDetails, paymentPeriod, validatePrice);
    }

    public DomainRegistrationDetails(User user, NameserverFormDetails dnsDetails, PaymentDetails paymentDetails,
            int paymentPeriod, boolean validatePrice) {
        this(new DomainContactFormDetails(user), dnsDetails, paymentDetails, paymentPeriod, validatePrice);
    }

    public DomainRegistrationDetails(User user, List<DomainNameServer> dnsList, PaymentDetails paymentDetails,
            int paymentPeriod, boolean validatePrice) {
        this(user, new NameserverFormDetails(dnsList), paymentDetails, paymentPeriod, validatePrice);
    }

    public DomainRegistrationDetails(User user, String domainName, PaymentDetails paymentDetails, int paymentPeriod,
            boolean validatePrice) {
        this(user, new NameserverFormDetails(domainName), paymentDetails, paymentPeriod, validatePrice);
    }

    public DomainRegistrationDetails(User user, String domainName) {
        this(user, domainName, PredefinedPayments.VALID_CREDIT_CARD, 1, false);
    }

    public String getDomainHolder() {
        return domainHolder;
    }

    public void setDomainHolder(String domainHolder) {
        this.domainHolder = domainHolder;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public int getPaymentPeriod() {
        return paymentPeriod;
    }

    public void setPaymentPeriod(int paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    public boolean isValidatePrice() {
        return validatePrice;
    }

    public void setValidatePrice(boolean validatePrice) {
        this.validatePrice = validatePrice;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public boolean isBasedInIreland() {
        return basedInIreland;
    }

    public void setBasedInIreland(boolean basedInIreland) {
        this.basedInIreland = basedInIreland;
    }
}
