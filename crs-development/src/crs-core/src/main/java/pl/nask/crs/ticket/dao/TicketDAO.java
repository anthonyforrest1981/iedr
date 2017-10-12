package pl.nask.crs.ticket.dao;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.List;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.TicketNotification;
import pl.nask.crs.ticket.search.TicketSearchCriteria;

public interface TicketDAO extends GenericDAO<Ticket, Long> {

    List<String> findDomainNames(TicketSearchCriteria cr, int offset, int limit);

    long createTicket(Ticket ticket);

    /*>>>@Nullable*/ TicketNotification getTicketNotification(long ticketId, int period);

    void createTicketNotification(TicketNotification notification);
}
