package pl.nask.crs.secondarymarket;

import java.util.Date;

public class SellRequest {

    private long id = -1;
    private String creatorNH;
    private Date creationDate;
    private SellRequestStatus status;
    private BuyRequest buyRequest;

    public SellRequest() {}

    public SellRequest(String creatorNH, Date creationDate, BuyRequest buyRequest) {
        this.creatorNH = creatorNH;
        this.creationDate = creationDate;
        this.status = SellRequestStatus.PROCESSING;
        this.buyRequest = buyRequest;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDomainName() {
        return buyRequest.getDomainName();
    }

    public String getCreatorNH() {
        return creatorNH;
    }

    public void setCreatorNH(String creatorNH) {
        this.creatorNH = creatorNH;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public SellRequestStatus getStatus() {
        return status;
    }

    public void setStatus(SellRequestStatus status) {
        this.status = status;
    }

    public BuyRequest getBuyRequest() {
        return buyRequest;
    }

    public void setBuyRequest(BuyRequest buyRequest) {
        this.buyRequest = buyRequest;
    }

}
