package pl.nask.crs.api.vo.search;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.secondarymarket.search.SellRequestSearchCriteria;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class SellRequestSearchCriteriaVO {

    private String domainName;
    private String domainHolder;

    public SellRequestSearchCriteria toSearchCriteria() {
        SellRequestSearchCriteria criteria = new SellRequestSearchCriteria();
        criteria.setDomainName(domainName);
        criteria.setDomainHolder(domainHolder);
        criteria.removeEmptyStrings();
        return criteria;
    }

}
