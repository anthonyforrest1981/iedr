package pl.nask.crs.api.vo.search;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.domains.search.ExtendedDomainSearchCriteria;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class ExtendedDomainSearchCriteriaVO extends DomainSearchCriteriaVO {

    protected void setExtendedDomainSearchCriteriaFields(ExtendedDomainSearchCriteria criteria) {
        setDomainSearchCriteriaFields(criteria);
    }

    public ExtendedDomainSearchCriteria toExtendedDomainSearchCriteria() {
        ExtendedDomainSearchCriteria criteria = new ExtendedDomainSearchCriteria();
        setExtendedDomainSearchCriteriaFields(criteria);
        return criteria;
    }
}
