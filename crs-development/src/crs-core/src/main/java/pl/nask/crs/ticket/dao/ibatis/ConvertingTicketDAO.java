package pl.nask.crs.ticket.dao.ibatis;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.List;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.TicketNotification;
import pl.nask.crs.ticket.dao.TicketDAO;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalTicket;
import pl.nask.crs.ticket.search.TicketSearchCriteria;

public class ConvertingTicketDAO extends ConvertingGenericDAO<InternalTicket, Ticket, Long> implements TicketDAO {

    private final InternalTicketIBatisDAO dao;

    public ConvertingTicketDAO(InternalTicketIBatisDAO internalDao, Converter<InternalTicket, Ticket> internalConverter) {
        super(internalDao, internalConverter);
        dao = internalDao;
    }

    public List<String> findDomainNames(TicketSearchCriteria domainSearchCriteria, int offset, int limit) {
        return dao.findDomainNames(domainSearchCriteria, offset, limit);
    }

    public long createTicket(Ticket ticket) {
        InternalTicket internalTicket = getInternalConverter().from(ticket);
        assert internalTicket != null : "@AssumeAssertion(nullness)";
        long id = dao.createTicket(internalTicket);
        return id;
    }

    @Override
    public /*>>>@Nullable*/ TicketNotification getTicketNotification(long ticketId, int period) {
        return dao.getTicketNotification(ticketId, period);
    }

    @Override
    public void createTicketNotification(TicketNotification notification) {
        dao.createTicketNotification(notification);
    }

}
