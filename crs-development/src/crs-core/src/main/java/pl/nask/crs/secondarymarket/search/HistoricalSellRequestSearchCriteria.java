package pl.nask.crs.secondarymarket.search;

import java.util.Arrays;
import java.util.List;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.secondarymarket.SellRequest;
import pl.nask.crs.secondarymarket.SellRequestStatus;

public class HistoricalSellRequestSearchCriteria implements SearchCriteria<HistoricalObject<SellRequest>> {

    private Long sellRequestId;
    private String domainName;
    private String domainHolder;
    private Long accountId;
    private List<SellRequestStatus> statuses;

    public Long getSellRequestId() {
        return sellRequestId;
    }

    public void setSellRequestId(Long sellRequestId) {
        this.sellRequestId = sellRequestId;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainHolder() {
        return domainHolder;
    }

    public void setDomainHolder(String domainHolder) {
        this.domainHolder = domainHolder;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public List<SellRequestStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<SellRequestStatus> statuses) {
        this.statuses = statuses;
    }

    public void setStatus(SellRequestStatus status) {
        if (status != null) {
            this.statuses = Arrays.asList(status);
        }
    }

    public void removeEmptyStrings() {
        domainName = Validator.isEmpty(domainName) ? null : domainName;
        domainHolder = Validator.isEmpty(domainHolder) ? null : domainHolder;
    }

}
