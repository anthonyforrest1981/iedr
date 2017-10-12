package pl.nask.crs.api.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.secondarymarket.SellRequest;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SellRequestSearchResultVO {

    private List<SellRequestVO> results;
    private long totalResults;

    public SellRequestSearchResultVO() {

    }

    public SellRequestSearchResultVO(List<SellRequest> list, long totalResults) {
        results = new ArrayList<>();
        for (SellRequest sellRequest : list) {
            results.add(new SellRequestVO(sellRequest));
        }
        this.totalResults = totalResults;
    }

    public List<SellRequestVO> getResults() {
        return results;
    }

    public long getTotalResults() {
        return totalResults;
    }

}
