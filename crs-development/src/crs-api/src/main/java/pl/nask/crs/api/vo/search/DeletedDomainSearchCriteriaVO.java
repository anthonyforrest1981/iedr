package pl.nask.crs.api.vo.search;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.domains.search.DeletedDomainSearchCriteria;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class DeletedDomainSearchCriteriaVO {

    private String domainName;

    public DeletedDomainSearchCriteria toSearchCriteria() {
        DeletedDomainSearchCriteria criteria = new DeletedDomainSearchCriteria();
        criteria.setDomainName(domainName);
        return criteria;
    }

}
