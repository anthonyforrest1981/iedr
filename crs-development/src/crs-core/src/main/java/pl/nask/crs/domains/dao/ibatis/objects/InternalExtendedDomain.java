package pl.nask.crs.domains.dao.ibatis.objects;

import pl.nask.crs.payment.PaymentMethod;

public class InternalExtendedDomain extends InternalDomain {

    private PaymentMethod pendingReservationPaymentMethod;

    public PaymentMethod getPendingReservationPaymentMethod() {
        return pendingReservationPaymentMethod;
    }

    public void setPendingReservationPaymentMethod(PaymentMethod pendingReservationPaymentMethod) {
        this.pendingReservationPaymentMethod = pendingReservationPaymentMethod;
    }

}
