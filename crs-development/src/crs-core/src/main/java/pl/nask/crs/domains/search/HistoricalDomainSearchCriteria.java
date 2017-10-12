package pl.nask.crs.domains.search;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.history.HistoricalObject;

public class HistoricalDomainSearchCriteria implements SearchCriteria<HistoricalObject<Domain>> {

    private String domainName;
    private String domainHolder;
    private String nicHandle;
    private Long accountId;
    private Long holderClassId;
    private Long holderCategoryId;

    public HistoricalDomainSearchCriteria() {}

    public HistoricalDomainSearchCriteria(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainName() {
        return ("".equals(domainName)) ? null : domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainHolder() {
        return ("".equals(domainHolder)) ? null : domainHolder;
    }

    public void setDomainHolder(String domainHolder) {
        this.domainHolder = domainHolder;
    }

    public String getNicHandle() {
        return ("".equals(nicHandle)) ? null : nicHandle;
    }

    public void setNicHandle(String nicHandle) {
        this.nicHandle = nicHandle;
    }

    public Long getAccountId() {
        return (accountId == null || accountId < 0) ? null : accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getHolderClassId() {
        return holderClassId;
    }

    public void setHolderClassId(Long holderClassId) {
        this.holderClassId = holderClassId;
    }

    public Long getHolderCategoryId() {
        return holderCategoryId;
    }

    public void setHolderCategoryId(Long holderCategoryId) {
        this.holderCategoryId = holderCategoryId;
    }
}
