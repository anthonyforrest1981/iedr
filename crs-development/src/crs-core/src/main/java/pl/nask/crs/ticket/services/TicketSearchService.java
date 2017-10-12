package pl.nask.crs.ticket.services;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.List;

import pl.nask.crs.app.tickets.exceptions.DomainModificationPendingException;
import pl.nask.crs.app.tickets.exceptions.DomainTransferPendingException;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.search.TicketSearchCriteria;

public interface TicketSearchService {

    Ticket getTicket(long id) throws TicketNotFoundException;

    boolean exists(TicketSearchCriteria criteria);

    LimitedSearchResult<Ticket> find(TicketSearchCriteria criteria,
            long offset, long limit, /*>>>@Nullable*/ List<SortCriterion> orderBy);

    List<Ticket> findAll(TicketSearchCriteria criteria, /*>>>@Nullable*/ List<SortCriterion> sortBy);

    void validateTicketPending(String domainName)
            throws DomainModificationPendingException,
            DomainTransferPendingException, TooManyTicketsException;

    /*>>>@Nullable*/ Ticket getTicketForDomain(String domainName) throws TooManyTicketsException;

    List<String> findPendingDomainsForHolder(String holder, int limit, String except);

}
