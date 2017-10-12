package pl.nask.crs.api.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.ExtendedDeposit;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DepositSearchResultVO {

    private long totalResults;
    private List<DepositVO> results = new ArrayList<DepositVO>();

    public DepositSearchResultVO() {}

    public DepositSearchResultVO(LimitedSearchResult<? extends Deposit> searchResult) {
        this.totalResults = searchResult.getTotalResults();
        for (Deposit deposit : searchResult.getResults()) {
            if (deposit instanceof ExtendedDeposit) {
                results.add(new DepositVO((ExtendedDeposit) deposit));
            } else {
                results.add(new DepositVO(deposit));
            }
        }
    }

    public long getTotalResults() {
        return totalResults;
    }

    public List<DepositVO> getResults() {
        return results;
    }
}
