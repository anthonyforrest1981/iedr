package pl.nask.crs.api.vo.search;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.ReservationSearchCriteria;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class ReservationSearchCriteriaVO {

    private String billingNH;

    private PaymentMethod paymentMethod;
    private String domainName;
    private OperationType operationType;

    public ReservationSearchCriteria toSearchCriteria() {
        ReservationSearchCriteria criteria = new ReservationSearchCriteria();
        criteria.setBillingNH(billingNH);
        criteria.setPaymentMethod(paymentMethod);
        criteria.setDomainName(domainName);
        criteria.setOperationType(operationType);
        return criteria;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

}
