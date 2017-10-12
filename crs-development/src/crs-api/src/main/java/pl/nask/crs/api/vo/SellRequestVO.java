package pl.nask.crs.api.vo;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.secondarymarket.SellRequest;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SellRequestVO {

    private long id;
    private Date creationDate;
    private BuyRequestVO buyRequest;

    public SellRequestVO() {}

    public SellRequestVO(SellRequest sellRequest) {
        this.id = sellRequest.getId();
        this.creationDate = sellRequest.getCreationDate();
        this.buyRequest = new BuyRequestVO(sellRequest.getBuyRequest());
    }

    public long getId() {
        return id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public BuyRequestVO getBuyRequest() {
        return buyRequest;
    }

}
