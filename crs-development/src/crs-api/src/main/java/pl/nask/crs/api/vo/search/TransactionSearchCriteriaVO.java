package pl.nask.crs.api.vo.search;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.payment.TransactionSearchCriteria;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionSearchCriteriaVO {

    private String billingNH;
    private Date settledFrom;
    private Date settledTo;

    public TransactionSearchCriteria toSearchCriteria() {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setBillingNH(billingNH);
        criteria.setSettledFrom(settledFrom);
        criteria.setSettledTo(settledTo);
        return criteria;
    }

}
