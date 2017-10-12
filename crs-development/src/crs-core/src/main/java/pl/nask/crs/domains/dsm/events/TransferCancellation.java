package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.ticket.Ticket;

public class TransferCancellation extends AbstractTransferEvent {
    public TransferCancellation(Ticket t) {
        super(DsmEventName.TransferCancellation, t);
    }
}
