package pl.nask.crs.web.secondarymarket;

import java.util.List;

import pl.nask.crs.app.commons.RelatedDomains;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.nichandles.wrappers.PhoneWrapper;
import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.documents.service.DocumentService;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.exceptions.BuyRequestNotFoundException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class BuyRequestViewAction extends BuyRequestAction {
    private Long changeId;
    private HistoricalObject<BuyRequest> historicalRequest;
    private BuyRequest current;

    private RelatedDomains relatedDomains;

    private DomainAppService domainAppService;

    public BuyRequestViewAction(SecondaryMarketAppService secondaryMarketAppService, DomainAppService domainAppService,
            DocumentService documentService, ApplicationConfig config) {
        super(secondaryMarketAppService, documentService, config);

        this.domainAppService = domainAppService;
    }

    private void refreshPhoneWrappers() {
        phonesWrapper = new PhoneWrapper(buyRequest.getPhones());
        faxesWrapper = new PhoneWrapper(buyRequest.getFaxes());
    }

    public String view() throws Exception {
        AuthenticatedUser user = getUser();
        buyRequest = secondaryMarketAppService.getBuyRequest(user, buyRequestId);
        refreshPhoneWrappers();
        return SUCCESS;
    }

    public HistoricalObject<BuyRequest> getHistoricalRequest() {
        return historicalRequest;
    }

    public void setHistoricalRequest(HistoricalObject<BuyRequest> historicalRequest) {
        this.historicalRequest = historicalRequest;
    }

    public long getChangeId() {
        return changeId;
    }

    public void setChangeId(long changeId) {
        this.changeId = changeId;
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

    public boolean isHistorical() {
        return changeId != null;
    }

    public boolean hasCurrent() {
        return current != null;
    }

    public String history() throws Exception {
        historicalRequest = secondaryMarketAppService.getHistoricalBuyRequest(getUser(), changeId);
        buyRequest = historicalRequest.getObject();
        try {
            current = secondaryMarketAppService.getBuyRequest(getUser(), buyRequest.getId());
        } catch (BuyRequestNotFoundException e) {
            current = null;
        }
        refreshPhoneWrappers();
        return SUCCESS;
    }
}
