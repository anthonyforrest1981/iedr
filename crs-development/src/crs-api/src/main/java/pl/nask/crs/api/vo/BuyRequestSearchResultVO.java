package pl.nask.crs.api.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.secondarymarket.BuyRequest;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BuyRequestSearchResultVO {

    private List<BuyRequestVO> results;
    private long totalResults;

    public BuyRequestSearchResultVO() {}

    public BuyRequestSearchResultVO(List<BuyRequest> list, long totalResults) {
        results = new ArrayList<>();
        for (BuyRequest buyRequest : list) {
            results.add(new BuyRequestVO(buyRequest));
        }
        this.totalResults = totalResults;
    }

    public List<BuyRequestVO> getResults() {
        return results;
    }

    public long getTotalResults() {
        return totalResults;
    }

}
