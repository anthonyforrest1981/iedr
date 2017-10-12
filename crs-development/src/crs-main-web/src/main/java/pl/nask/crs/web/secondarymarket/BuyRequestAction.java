package pl.nask.crs.web.secondarymarket;

import org.apache.commons.lang.time.DateUtils;
import pl.nask.crs.app.nichandles.wrappers.PhoneWrapper;
import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.documents.service.DocumentService;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.exceptions.BuyRequestNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

abstract class BuyRequestAction extends AuthenticatedUserAwareAction {
    private final ApplicationConfig config;

    private String previousAction;
    protected long buyRequestId;
    protected BuyRequest buyRequest;
    protected FailureReasonsWrapper failures;
    protected PhoneWrapper phonesWrapper;
    protected PhoneWrapper faxesWrapper;

    protected SecondaryMarketAppService secondaryMarketAppService;
    private DocumentService documentService;

    BuyRequestAction(SecondaryMarketAppService secondaryMarketAppService, DocumentService documentService,
                     ApplicationConfig config) {
        this.secondaryMarketAppService = secondaryMarketAppService;
        this.documentService = documentService;
        this.config = config;
        failures = new FailureReasonsWrapper();
    }

    public BuyRequest getBuyRequest() {
        return buyRequest;
    }

    public void setBuyRequest(BuyRequest buyRequest) {
        this.buyRequest = buyRequest;
    }

    public long getBuyRequestId() {
        return buyRequestId;
    }

    public void setBuyRequestId(long buyRequestId) {
        this.buyRequestId = buyRequestId;
    }

    public String getPreviousAction() {
        return previousAction;
    }

    public void setPreviousAction(String previousAction) {
        this.previousAction = previousAction;
    }

    public List<HistoricalObject<BuyRequest>> getHistory() throws Exception {
        return secondaryMarketAppService.getBuyRequestHistory(getUser(), buyRequestId);
    }

    public PhoneWrapper getPhonesWrapper() {
        return phonesWrapper;
    }

    public void setPhonesWrapper(PhoneWrapper phonesWrapper) {
        this.phonesWrapper = phonesWrapper;
    }

    public PhoneWrapper getFaxesWrapper() {
        return faxesWrapper;
    }

    public void setFaxesWrapper(PhoneWrapper faxesWrapper) {
        this.faxesWrapper = faxesWrapper;
    }

    public FailureReasonsWrapper getFailures() {
        return failures;
    }

    public void setFailures(FailureReasonsWrapper failures) {
        this.failures = failures;
    }

    public boolean getHasDocuments() {
        return documentService.hasDocumentsForBuyRequest(buyRequestId);
    }

    void fetchBuyRequest() throws AccessDeniedException, BuyRequestNotFoundException {
        AuthenticatedUser user = getUser();
        buyRequest = secondaryMarketAppService.getBuyRequest(user, buyRequestId);
        phonesWrapper = new PhoneWrapper(buyRequest.getPhones());
        faxesWrapper = new PhoneWrapper(buyRequest.getFaxes());
        failures = new FailureReasonsWrapper(buyRequest);
    }

    public Date getRequestExpirationDate() {
        return DateUtils.addDays(buyRequest.getCreationDate(), config.getTicketExpirationPeriod());
    }
}
