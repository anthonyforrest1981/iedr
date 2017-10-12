package pl.nask.crs.ticket.dao.ibatis.converters;

import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalHistoricalTicket;

public class HistoricalTicketConverter extends AbstractConverter<InternalHistoricalTicket, HistoricalObject<Ticket>> {
    private TicketConverter ticketConverter;

    public HistoricalTicketConverter(TicketConverter ticketConverter) {
        Validator.assertNotNull(ticketConverter, "ticket converter");
        this.ticketConverter = ticketConverter;
    }

    protected HistoricalObject<Ticket> _to(InternalHistoricalTicket src) {
        final Ticket ticket = ticketConverter.to(src);
        assert ticket != null : "@AssumeAssertion(nullness)";
        return new HistoricalObject<>(src.getChangeId(), ticket, src.getHistChangeDate(), src.getChangedByNicHandle());
    }

    protected InternalHistoricalTicket _from(HistoricalObject<Ticket> ticketHistoricalObject) {
        InternalHistoricalTicket iht = new InternalHistoricalTicket();
        TicketConverter.copy(ticketHistoricalObject.getObject(), iht);
        iht.setHistChangeDate(ticketHistoricalObject.getChangeDate());
        iht.setChangedByNicHandle(ticketHistoricalObject.getChangedBy());
        return iht;
    }

}
