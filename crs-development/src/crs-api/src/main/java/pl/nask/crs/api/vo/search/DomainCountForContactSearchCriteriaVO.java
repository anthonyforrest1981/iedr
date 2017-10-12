package pl.nask.crs.api.vo.search;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.domains.search.DomainCountForContactSearchCriteria;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class DomainCountForContactSearchCriteriaVO {

    private Boolean activeFlag;

    public DomainCountForContactSearchCriteria toSearchCriteria() {
        DomainCountForContactSearchCriteria criteria = new DomainCountForContactSearchCriteria();
        criteria.setActiveFlag(activeFlag);
        return criteria;
    }

}
