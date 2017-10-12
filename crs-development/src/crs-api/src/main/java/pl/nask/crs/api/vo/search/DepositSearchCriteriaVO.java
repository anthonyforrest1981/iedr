package pl.nask.crs.api.vo.search;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.payment.DepositSearchCriteria;
import pl.nask.crs.payment.DepositTransactionType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class DepositSearchCriteriaVO {

    private String nicHandleId;
    private Date transactionDateFrom;
    private Date transactionDateTo;
    private DepositTransactionType transactionType;

    public DepositSearchCriteria toSearchCriteria() {
        DepositSearchCriteria criteria = new DepositSearchCriteria();
        criteria.setNicHandleId(nicHandleId);
        criteria.setTransactionDateFrom(transactionDateFrom);
        criteria.setTransactionDateTo(transactionDateTo);
        criteria.setTransactionType(transactionType);
        return criteria;
    }

}
