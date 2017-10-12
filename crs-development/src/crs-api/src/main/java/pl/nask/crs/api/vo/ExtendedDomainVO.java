package pl.nask.crs.api.vo;

import pl.nask.crs.domains.ExtendedDomain;
import pl.nask.crs.payment.PaymentMethod;

public class ExtendedDomainVO extends DomainVO {

    private PaymentMethod pendingReservationPaymentMethod;

    public ExtendedDomainVO() {}

    public ExtendedDomainVO(ExtendedDomain domain) {
        super(domain);
        this.pendingReservationPaymentMethod = domain.getPendingReservationPaymentMethod();
    }

}
