package pl.nask.crs.api.vo.search;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.PlainInvoiceSearchCriteria;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class PlainInvoiceSearchCriteriaVO {

    private String billingNH;
    private Date settledFrom;
    private Date settledTo;
    private PaymentMethod paymentMethod;
    private String invoiceNumber;

    private String settlementDateLike;
    private String invoiceDateLike;

    public PlainInvoiceSearchCriteria toSearchCriteria() {
        PlainInvoiceSearchCriteria criteria = new PlainInvoiceSearchCriteria();
        criteria.setBillingNH(billingNH);
        criteria.setSettledFrom(settledFrom);
        criteria.setSettledTo(settledTo);
        criteria.setPaymentMethod(paymentMethod);
        criteria.setInvoiceNumber(invoiceNumber);
        criteria.setSettlementDateLike(settlementDateLike);
        criteria.setInvoiceDateLike(invoiceDateLike);
        return criteria;
    }

}
