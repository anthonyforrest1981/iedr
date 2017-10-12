package pl.nask.crs.ticket.dao;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.List;

import pl.nask.crs.commons.dao.HistoricalDAO;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.ticket.Ticket;

public interface HistoricalTicketDAO extends HistoricalDAO<Ticket, Long> {

    List<HistoricalObject<Ticket>> findAll(long id);

}
