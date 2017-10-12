package pl.nask.crs.secondarymarket.search;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.BuyRequestStatus;

public class BuyRequestSearchCriteria implements SearchCriteria<BuyRequest> {

    private Long buyRequestId;
    private Date creationDateFrom;
    private Date creationDateTo;
    private String domainName;
    private String creatorNH;
    private Long buyerAccountId;
    private Long sellerAccountId;
    private List<BuyRequestStatus> statuses;
    private String domainHolder;
    private String buyerName;
    private String checkedOutTo;
    private Date authCodeCreationDateFrom;
    private Date authCodeCreationDateTo;

    public Long getBuyRequestId() {
        return buyRequestId;
    }

    public void setBuyRequestId(Long buyRequestId) {
        this.buyRequestId = buyRequestId;
    }

    public Date getCreationDateFrom() {
        return creationDateFrom;
    }

    public void setCreationDateFrom(Date creationDateFrom) {
        this.creationDateFrom = DateUtils.startOfDay(creationDateFrom);
    }

    public Date getCreationDateTo() {
        return creationDateTo;
    }

    public void setCreationDateTo(Date creationDateTo) {
        this.creationDateTo = DateUtils.endOfDay(creationDateTo);
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

    public Long getBuyerAccountId() {
        return buyerAccountId;
    }

    public void setBuyerAccountId(Long buyerAccountId) {
        this.buyerAccountId = buyerAccountId;
    }

    public Long getSellerAccountId() {
        return sellerAccountId;
    }

    public void setSellerAccountId(Long sellerAccountId) {
        this.sellerAccountId = sellerAccountId;
    }

    public List<BuyRequestStatus> getStatuses() {
        return statuses;
    }

    public void setStatus(BuyRequestStatus status) {
        if (status == null) {
            this.statuses = null;
        } else {
            this.statuses = Collections.singletonList(status);
        }
    }

    public void setStatuses(List<BuyRequestStatus> statuses) {
        this.statuses = statuses;
    }

    public String getDomainHolder() {
        return domainHolder;
    }

    public void setDomainHolder(String domainHolder) {
        this.domainHolder = domainHolder;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getCheckedOutTo() {
        return checkedOutTo;
    }

    public void setCheckedOutTo(String checkedOutTo) {
        this.checkedOutTo = checkedOutTo;
    }

    public Date getAuthCodeCreationDateFrom() {
        return authCodeCreationDateFrom;
    }

    public void setAuthCodeCreationDateFrom(Date authCodeCreationDateFrom) {
        this.authCodeCreationDateFrom = DateUtils.startOfDay(authCodeCreationDateFrom);
    }

    public Date getAuthCodeCreationDateTo() {
        return authCodeCreationDateTo;
    }

    public void setAuthCodeCreationDateTo(Date authCodeCreationDateTo) {
        this.authCodeCreationDateTo = DateUtils.endOfDay(authCodeCreationDateTo);
    }

    public void removeEmptyStrings() {
        domainName = Validator.isEmpty(domainName) ? null : domainName;
        creatorNH = Validator.isEmpty(creatorNH) ? null : creatorNH;
        domainHolder = Validator.isEmpty(domainHolder) ? null : domainHolder;
        buyerName = Validator.isEmpty(buyerName) ? null : buyerName;
        checkedOutTo = Validator.isEmpty(checkedOutTo) ? null : checkedOutTo;
    }

}
