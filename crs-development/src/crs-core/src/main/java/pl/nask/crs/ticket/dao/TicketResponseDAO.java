package pl.nask.crs.ticket.dao;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.ticket.response.TicketResponse;

public interface TicketResponseDAO extends GenericDAO<TicketResponse, Long> {

    public /*>>>@Nullable*/ TicketResponse get(String title);
}
