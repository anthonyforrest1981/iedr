package pl.nask.crs.api.vo.search;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.secondarymarket.BuyRequestStatus;
import pl.nask.crs.secondarymarket.search.BuyRequestSearchCriteria;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class BuyRequestSearchCriteriaVO {

    private Date creationDateFrom;
    private String domainName;
    private BuyRequestStatus status;
    private String domainHolder;

    public BuyRequestSearchCriteria toSearchCriteria() {
        BuyRequestSearchCriteria criteria = new BuyRequestSearchCriteria();
        criteria.setCreationDateFrom(creationDateFrom);
        criteria.setDomainName(domainName);
        criteria.setStatus(status);
        criteria.setDomainHolder(domainHolder);
        criteria.removeEmptyStrings();
        return criteria;
    }

}
