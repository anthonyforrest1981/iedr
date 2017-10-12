package pl.nask.crs.api.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.domains.PlainDomain;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PlainDomainSearchResultVO {

    private long totalResults;
    private List<PlainDomainVO> results = new ArrayList<PlainDomainVO>();

    public PlainDomainSearchResultVO() {}

    public PlainDomainSearchResultVO(LimitedSearchResult<? extends PlainDomain> searchRes) {
        this.totalResults = searchRes.getTotalResults();
        for (PlainDomain d : searchRes.getResults()) {
            results.add(new PlainDomainVO(d));
        }
    }

}
