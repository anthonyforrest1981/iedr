package pl.nask.crs.api.vo.search;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.contacts.ContactType;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.search.AbstractDomainSearchCriteria;
import pl.nask.crs.domains.search.DomainSearchCriteria;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class DomainSearchCriteriaVO extends PlainDomainSearchCriteriaVO {

    private String nicHandle;
    private List<ContactType> contactType;

    protected void setDomainSearchCriteriaFields(AbstractDomainSearchCriteria<? extends Domain> criteria) {
        setPlainDomainSearchCriteriaFields(criteria);
        criteria.setNicHandle(nicHandle);
        criteria.setContactType(contactType);
    }

    public DomainSearchCriteria toDomainSearchCriteria() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        setDomainSearchCriteriaFields(criteria);
        return criteria;
    }

}
