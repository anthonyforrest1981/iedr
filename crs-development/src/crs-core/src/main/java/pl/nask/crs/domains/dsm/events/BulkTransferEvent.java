package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.domains.dsm.DsmEventName;

public class BulkTransferEvent extends AbstractEvent {

    public BulkTransferEvent() {
        super(DsmEventName.BulkTransfer);
    }

}
