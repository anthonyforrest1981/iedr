package pl.nask.crs.web.secondarymarket;

import java.util.Date;

import org.apache.commons.lang.xwork.time.DateUtils;

import pl.nask.crs.app.nichandles.wrappers.PhoneWrapper;
import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.secondarymarket.SellRequest;
import pl.nask.crs.secondarymarket.SellRequestStatus;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public class SellRequestViewAction extends AuthenticatedUserAwareAction {

    private final SecondaryMarketAppService secondaryMarketAppService;
    private final ApplicationConfig applicationConfig;

    private SellRequest sellRequest;
    private Long requestId;

    private HistoricalObject<SellRequest> historicalRequest;
    private Long changeId;

    private PhoneWrapper phonesWrapper;
    private PhoneWrapper faxesWrapper;

    private String previousAction;

    public SellRequestViewAction(SecondaryMarketAppService secondaryMarketAppService,
            ApplicationConfig applicationConfig) {
        this.secondaryMarketAppService = secondaryMarketAppService;
        this.applicationConfig = applicationConfig;
    }

    public SellRequest getSellRequest() {
        return sellRequest;
    }

    public void setSellRequest(SellRequest sellRequest) {
        this.sellRequest = sellRequest;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getChangeId() {
        return changeId;
    }

    public void setChangeId(Long changeId) {
        this.changeId = changeId;
    }

    public String getPreviousAction() {
        return previousAction;
    }

    public void setPreviousAction(String previousAction) {
        this.previousAction = previousAction;
    }

    public PhoneWrapper getPhonesWrapper() {
        return phonesWrapper;
    }

    public PhoneWrapper getFaxesWrapper() {
        return faxesWrapper;
    }

    private void refreshTelecomWrappers() {
        phonesWrapper = new PhoneWrapper(sellRequest.getBuyRequest().getPhones());
        faxesWrapper = new PhoneWrapper(sellRequest.getBuyRequest().getFaxes());
    }

    public String view() throws Exception {
        sellRequest = secondaryMarketAppService.getSellRequest(getUser(), requestId);
        refreshTelecomWrappers();
        return INPUT;
    }

    public Date getCompletionDate() {
        if (isHistory()) {
            if (sellRequest.getStatus() == SellRequestStatus.COMPLETED) {
                return historicalRequest.getChangeDate();
            } else {
                return null;
            }
        }
        return DateUtils.addDays(sellRequest.getCreationDate(), applicationConfig.getSecondaryMarketCountdownPeriod());
    }

    public Date getExpirationDate() {
        if (isHistory()) {
            if (sellRequest.getStatus() == SellRequestStatus.CANCELLED) {
                return historicalRequest.getChangeDate();
            } else {
                return null;
            }
        }
        return DateUtils.addDays(sellRequest.getCreationDate(), applicationConfig.getTicketExpirationPeriod());
    }

    public String cancelProcess() throws Exception {
        secondaryMarketAppService.cancelSellRequest(getUser(), requestId);
        return SUCCESS;
    }

    public boolean isHistory() {
        return changeId != null;
    }

    public String history() throws Exception {
        historicalRequest = secondaryMarketAppService.getHistoricalSellRequest(getUser(), changeId);
        sellRequest = historicalRequest.getObject();
        refreshTelecomWrappers();
        return INPUT;
    }

}
