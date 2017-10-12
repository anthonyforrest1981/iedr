package pl.nask.crs.api.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.domains.DomainCountForContact;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DomainCountForContactSearchResultVO {

    private List<DomainCountForContactVO> results = new ArrayList<DomainCountForContactVO>();

    public DomainCountForContactSearchResultVO() {}

    public DomainCountForContactSearchResultVO(List<DomainCountForContact> results) {
        for (DomainCountForContact summary : results) {
            this.results.add(new DomainCountForContactVO(summary));
        }
    }

    public List<DomainCountForContactVO> getResults() {
        return results;
    }
}
