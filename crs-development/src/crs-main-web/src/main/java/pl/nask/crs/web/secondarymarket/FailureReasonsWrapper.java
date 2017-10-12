package pl.nask.crs.web.secondarymarket;

import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.ticket.operation.FailureReason;

public class FailureReasonsWrapper {
    private FailureReason domainNameFR;
    private FailureReason domainHolderFR;
    private FailureReason holderClassFR;
    private FailureReason holderCategoryFR;
    private FailureReason adminNameFR;
    private FailureReason adminEmailFR;
    private FailureReason adminCompanyNameFR;
    private FailureReason adminAddressFR;
    private FailureReason adminCountryFR;
    private FailureReason adminCountyFR;
    private FailureReason phonesFR;
    private FailureReason faxesFR;

    public FailureReasonsWrapper() {
    }

    public FailureReasonsWrapper(BuyRequest buyRequest) {
        this.domainNameFR = buyRequest.getDomainNameFR();
        this.domainHolderFR = buyRequest.getDomainHolderFR();
        this.holderCategoryFR = buyRequest.getHolderCategoryFR();
        this.holderClassFR = buyRequest.getHolderClassFR();
        this.adminNameFR = buyRequest.getAdminNameFR();
        this.adminEmailFR = buyRequest.getAdminEmailFR();
        this.adminCompanyNameFR = buyRequest.getAdminCompanyNameFR();
        this.adminAddressFR = buyRequest.getAdminAddressFR();
        this.adminCountryFR = buyRequest.getAdminCountryFR();
        this.adminCountyFR = buyRequest.getAdminCountyFR();
        this.phonesFR = buyRequest.getPhonesFR();
        this.faxesFR = buyRequest.getFaxesFR();
    }

    public FailureReason getDomainNameFR() {
        return domainNameFR;
    }

    public void setDomainNameFR(FailureReason domainNameFR) {
        this.domainNameFR = domainNameFR;
    }

    public FailureReason getDomainHolderFR() {
        return domainHolderFR;
    }

    public void setDomainHolderFR(FailureReason domainHolderFR) {
        this.domainHolderFR = domainHolderFR;
    }

    public FailureReason getHolderClassFR() {
        return holderClassFR;
    }

    public void setHolderClassFR(FailureReason holderClassFR) {
        this.holderClassFR = holderClassFR;
    }

    public FailureReason getHolderCategoryFR() {
        return holderCategoryFR;
    }

    public void setHolderCategoryFR(FailureReason holderCategoryFR) {
        this.holderCategoryFR = holderCategoryFR;
    }

    public FailureReason getAdminNameFR() {
        return adminNameFR;
    }

    public void setAdminNameFR(FailureReason adminNameFR) {
        this.adminNameFR = adminNameFR;
    }

    public FailureReason getAdminEmailFR() {
        return adminEmailFR;
    }

    public void setAdminEmailFR(FailureReason adminEmailFR) {
        this.adminEmailFR = adminEmailFR;
    }

    public FailureReason getAdminCompanyNameFR() {
        return adminCompanyNameFR;
    }

    public void setAdminCompanyNameFR(FailureReason adminCompanyNameFR) {
        this.adminCompanyNameFR = adminCompanyNameFR;
    }

    public FailureReason getAdminAddressFR() {
        return adminAddressFR;
    }

    public void setAdminAddressFR(FailureReason adminAddressFR) {
        this.adminAddressFR = adminAddressFR;
    }

    public FailureReason getAdminCountryFR() {
        return adminCountryFR;
    }

    public void setAdminCountryFR(FailureReason adminCountryFR) {
        this.adminCountryFR = adminCountryFR;
    }

    public FailureReason getAdminCountyFR() {
        return adminCountyFR;
    }

    public void setAdminCountyFR(FailureReason adminCountyFR) {
        this.adminCountyFR = adminCountyFR;
    }

    public FailureReason getPhonesFR() {
        return phonesFR;
    }

    public void setPhonesFR(FailureReason phonesFR) {
        this.phonesFR = phonesFR;
    }

    public FailureReason getFaxesFR() {
        return faxesFR;
    }

    public void setFaxesFR(FailureReason faxesFR) {
        this.faxesFR = faxesFR;
    }
}
