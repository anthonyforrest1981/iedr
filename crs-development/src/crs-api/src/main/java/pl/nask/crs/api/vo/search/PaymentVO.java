package pl.nask.crs.api.vo.search;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.payment.Payment;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentVO {

    private BigDecimal fee;
    private BigDecimal vat;
    private BigDecimal total;

    public PaymentVO() {}

    public PaymentVO(Payment payment) {
        this.fee = payment.getFee();
        this.vat = payment.getVat();
        this.total = payment.getTotal();
    }

}
