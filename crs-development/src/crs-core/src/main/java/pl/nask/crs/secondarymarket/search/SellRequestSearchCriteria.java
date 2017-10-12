package pl.nask.crs.secondarymarket.search;

import java.util.Date;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.secondarymarket.SellRequest;

public class SellRequestSearchCriteria implements SearchCriteria<SellRequest> {

    private Long sellRequestId;
    private String domainName;
    private String creatorNH;
    private Date createdFrom;
    private Date createdTo;
    private Date completeFrom;
    private Date completeTo;
    private String domainHolder;
    private Long accountId;
    private Long buyRequestId;
    private String contactNH;

    public Long getSellRequestId() {
        return sellRequestId;
    }

    public void setSellRequestId(Long requestId) {
        this.sellRequestId = requestId;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getCreatorNH() {
        return creatorNH;
    }

    public void setCreatorNH(String creatorNH) {
        this.creatorNH = creatorNH;
    }

    public Date getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(Date createdFrom) {
        this.createdFrom = DateUtils.startOfDay(createdFrom);
    }

    public Date getCreatedTo() {
        return createdTo;
    }

    public void setCreatedTo(Date createdTo) {
        this.createdTo = DateUtils.endOfDay(createdTo);
    }

    public Date getCompleteFrom() {
        return completeFrom;
    }

    public void setCompleteFrom(Date completeFrom) {
        this.completeFrom = DateUtils.startOfDay(completeFrom);
    }

    public Date getCompleteTo() {
        return completeTo;
    }

    public void setCompleteTo(Date completeTo) {
        this.completeTo = DateUtils.endOfDay(completeTo);
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

    public Long getBuyRequestId() {
        return buyRequestId;
    }

    public void setBuyRequestId(Long buyRequestId) {
        this.buyRequestId = buyRequestId;
    }

    public String getContactNH() {
        return contactNH;
    }

    public void setContactNH(String contactNH) {
        this.contactNH = contactNH;
    }

    public void removeEmptyStrings() {
        domainName = Validator.isEmpty(domainName) ? null : domainName;
        creatorNH = Validator.isEmpty(creatorNH) ? null : creatorNH;
        domainHolder = Validator.isEmpty(domainHolder) ? null : domainHolder;
    }
}
