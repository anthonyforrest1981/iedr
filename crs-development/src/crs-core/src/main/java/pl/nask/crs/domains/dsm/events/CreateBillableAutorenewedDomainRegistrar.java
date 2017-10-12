package pl.nask.crs.domains.dsm.events;

import java.util.Date;

import pl.nask.crs.domains.dsm.DsmEventName;

public class CreateBillableAutorenewedDomainRegistrar extends AbstractCreateDomainEvent {
    public CreateBillableAutorenewedDomainRegistrar(Date renewalDate) {
        super(DsmEventName.CreateBillableAutorenewedDomainRegistrar, renewalDate);
    }
}
