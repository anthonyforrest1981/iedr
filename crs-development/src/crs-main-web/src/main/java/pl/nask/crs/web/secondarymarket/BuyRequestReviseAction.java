package pl.nask.crs.web.secondarymarket;

import com.opensymphony.xwork2.ActionContext;
import pl.nask.crs.app.commons.RelatedDomains;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.documents.service.DocumentService;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.BuyRequestStatus;
import pl.nask.crs.secondarymarket.exceptions.BuyRequestNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.operation.FailureReason;

import java.util.List;

public class BuyRequestReviseAction extends BuyRequestAction {

    private RelatedDomains relatedDomains;

    private boolean forceEdit;

    private String responseText;
    private BuyRequestStatus newStatus;

    DomainAppService domainAppService;

    public BuyRequestReviseAction(SecondaryMarketAppService secondaryMarketAppService, DomainAppService domainAppService,
            DocumentService documentService, ApplicationConfig config) {
        super(secondaryMarketAppService, documentService, config);

        this.domainAppService = domainAppService;
    }

    public String input() throws Exception {
        fetchBuyRequest();
        setResponseText("");
        return INPUT;
    }

    public String save() throws Exception {
        AuthenticatedUser user = getUser();
        if (!validateCurrentBuyRequest() || !validateHostmasterRemark())
            return INPUT;

        secondaryMarketAppService.saveBuyRequest(user, buyRequestId, responseText, failures.getDomainNameFR(),
                failures.getDomainHolderFR(), failures.getHolderCategoryFR(), failures.getHolderClassFR(),
                failures.getAdminNameFR(), failures.getAdminEmailFR(), failures.getAdminCompanyNameFR(),
                failures.getAdminAddressFR(), failures.getAdminCountryFR(), failures.getAdminCountyFR(),
                failures.getPhonesFR(), failures.getFaxesFR());
        // after successful save reset hostmaster remark in the form
        setResponseText("");
        fetchBuyRequest();
        return INPUT;
    }

    public String accept() throws Exception {
        AuthenticatedUser user = getUser();
        if (!validateCurrentBuyRequest() || !validateNoFailureReasons())
            return INPUT;
        secondaryMarketAppService.acceptBuyRequest(user, buyRequestId, getResponseText());
        return SUCCESS;
    }

    public String reject() throws Exception {
        AuthenticatedUser user = getUser();
        if (!validateCurrentBuyRequest() || !validateHostmasterRemark())
            return INPUT;
        secondaryMarketAppService
                .rejectBuyRequest(user, buyRequestId, newStatus, getResponseText(), failures.getDomainNameFR(),
                        failures.getDomainHolderFR(), failures.getHolderCategoryFR(), failures.getHolderClassFR(),
                        failures.getAdminNameFR(), failures.getAdminEmailFR(), failures.getAdminCompanyNameFR(),
                        failures.getAdminAddressFR(), failures.getAdminCountryFR(), failures.getAdminCountyFR(),
                        failures.getPhonesFR(), failures.getFaxesFR());
        return SUCCESS;
    }

    public String cancelEdit() {
        setForceEdit(false);
        return INPUT;
    }

    private boolean validateCurrentBuyRequest() throws AccessDeniedException, BuyRequestNotFoundException {
        BuyRequest dbRequest = secondaryMarketAppService.getBuyRequest(getUser(), buyRequestId);
        if (!dbRequest.getChangeDate().equals(buyRequest.getChangeDate())) {
            addActionError("BuyRequest was changed in the meantime, please recheck");
            fetchBuyRequest();
            return false;
        }
        return true;
    }

    private boolean validateHostmasterRemark() {
        String hostmasterRemark = ActionContext.getContext().getValueStack().findString("responseText");
        if (Validator.isEmpty(hostmasterRemark)) {
            addFieldError("responseText", "Hostmaster's remark is required");
            return false;
        }
        return true;
    }

    private boolean validateNoFailureReasons() {
        boolean result = true;
        result = validateFr("failures.domainNameFR", "domain name") && result;
        result = validateFr("failures.domainHolderFR", "domain holder") && result;
        result = validateFr("failures.holderClassFR", "class") && result;
        result = validateFr("failures.holderCategoryFR", "category") && result;
        result = validateFr("failures.adminNameFR", "admin name") && result;
        result = validateFr("failures.adminEmailFR", "email") && result;
        result = validateFr("failures.adminAddressFR", "address") && result;
        result = validateFr("failures.adminCompanyNameFR", "company name") && result;
        result = validateFr("failures.adminCountryFR", "country class") && result;
        result = validateFr("failures.adminCountyFR", "county") && result;
        result = validateFr("failures.phonesFR", "phones") && result;
        result = validateFr("failures.faxesFR", "faxes") && result;
        return result;
    }

    private boolean validateFr(String fieldName, String fieldLabel) {
        FailureReason fr = (FailureReason) ActionContext.getContext().getValueStack().findValue(fieldName, FailureReason.class);
        if (fr != null) {
            addFieldError(fieldName, "There should be no failure reason for " + fieldLabel);
            return false;
        }
        return true;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public boolean isForceEdit() {
        return forceEdit;
    }

    public void setForceEdit(boolean forceEdit) {
        this.forceEdit = forceEdit;
    }

    public BuyRequestStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(BuyRequestStatus newStatus) {
        this.newStatus = newStatus;
    }

    public List<BuyRequestStatus> getStatuses() {
        return BuyRequestStatus.valuesExcept(BuyRequestStatus.PASSED);
    }

    public BuyRequestStatus getCancellingStatus() {
        return BuyRequestStatus.CANCELLED;
    }

    public List<String> getPendingDomainNames() throws Exception {
        prepareRelatedDomains();
        return relatedDomains.getPendingDomains();
    }

    public List<String> getRelatedDomainNames() throws Exception {
        prepareRelatedDomains();
        return relatedDomains.getRelatedDomains();
    }

    private void prepareRelatedDomains() throws Exception {
        if (this.relatedDomains == null) {
            this.relatedDomains = domainAppService
                    .getRelatedDomains(getUser(), buyRequest.getDomainHolder(), 10, buyRequest.getDomainName());
        }
    }

}
