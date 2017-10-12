package pl.nask.crs.domains.dsm;

import pl.nask.crs.domains.dsm.events.AbstractEvent;

public class PaymentCancelledEvent extends AbstractEvent {
    public PaymentCancelledEvent() {
        super(DsmEventName.PaymentCancelled);
    }
}
