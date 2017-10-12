package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.ticket.Ticket;

public class TransferToDirect extends AbstractTransferEvent {
    public TransferToDirect(Ticket t) {
        super(DsmEventName.TransferToDirect, t);
    }
}
