package pl.nask.crs.api.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.domains.ExtendedDomain;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ExtendedDomainSearchResultVO {

    private long totalResults;
    private List<ExtendedDomainVO> results = new ArrayList<ExtendedDomainVO>();

    public ExtendedDomainSearchResultVO() {}

    public ExtendedDomainSearchResultVO(LimitedSearchResult<ExtendedDomain> searchRes) {
        this.totalResults = searchRes.getTotalResults();
        for (ExtendedDomain d : searchRes.getResults()) {
            results.add(new ExtendedDomainVO(d));
        }
    }

}
