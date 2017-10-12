package pl.nask.crs.secondarymarket.search;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.secondarymarket.BuyRequest;

public class HistoricalBuyRequestSearchCriteria implements SearchCriteria<HistoricalObject<BuyRequest>> {

    private Long buyRequestId;
    private String domainName;
    private String domainHolder;
    private Long accountId;

    public Long getBuyRequestId() {
        return buyRequestId;
    }

    public void setBuyRequestId(Long buyRequestId) {
        this.buyRequestId = buyRequestId;
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

    public void removeEmptyStrings() {
        domainName = Validator.isEmpty(domainName) ? null : domainName;
        domainHolder = Validator.isEmpty(domainHolder) ? null : domainHolder;
    }

}
