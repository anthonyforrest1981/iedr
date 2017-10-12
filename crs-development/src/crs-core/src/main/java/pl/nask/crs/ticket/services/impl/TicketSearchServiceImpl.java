package pl.nask.crs.ticket.services.impl;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.List;

import pl.nask.crs.app.tickets.exceptions.DomainModificationPendingException;
import pl.nask.crs.app.tickets.exceptions.DomainTransferPendingException;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.TicketDAO;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.search.TicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketSearchService;

public class TicketSearchServiceImpl implements TicketSearchService {
    private TicketDAO ticketDao;

    public TicketSearchServiceImpl(TicketDAO ticketDao) {
        Validator.assertNotNull(ticketDao, "ticket dao");
        this.ticketDao = ticketDao;
    }

    @Override
    public Ticket getTicket(long id) throws TicketNotFoundException {
        Ticket ret = ticketDao.get(id);
        if (ret == null)
            throw new TicketNotFoundException(id);
        return ret;
    }

    @Override
    public LimitedSearchResult<Ticket> find(TicketSearchCriteria searchCriteria, long offset, long limit, /*>>>@Nullable*/
            List<SortCriterion> sortBy) {
        return ticketDao.find(searchCriteria, offset, limit, sortBy);
    }

    @Override
    public List<Ticket> findAll(TicketSearchCriteria criteria, /*>>>@Nullable*/ List<SortCriterion> sortBy) {
        return ticketDao.find(criteria, sortBy).getResults();
    }

    @Override
    public void validateTicketPending(String domainName)
            throws DomainModificationPendingException, DomainTransferPendingException, TooManyTicketsException {
        Ticket ticket = getTicketForDomain(domainName);
        if (ticket != null) {
            switch (ticket.getOperation().getType()) {
                case MOD:
                    throw new DomainModificationPendingException(domainName);
                case XFER:
                    throw new DomainTransferPendingException(domainName);
                default:
                    // only MOD and XFER tickets are considered. REG tickets are 'safe'
            }
        }
    }

    @Override
    public /*>>>@Nullable*/ Ticket getTicketForDomain(String domainName) throws TooManyTicketsException {
        TicketSearchCriteria crit = new TicketSearchCriteria();
        crit.setDomainName(domainName);
        // Correct state of database allows only one ticket per domain name at one time
        List<Ticket> s = ticketDao.find(crit).getResults();
        if (s.size() > 1) {
            throw new TooManyTicketsException("Too many tickets found for domain name = " + domainName);
        } else if (s.size() == 1) {
            long id = s.get(0).getId();
            return ticketDao.get(id);
        } else {
            return null;
        }
    }

    @Override
    public List<String> findPendingDomainsForHolder(String holder, int limit, String except) {
        TicketSearchCriteria cr = new TicketSearchCriteria();
        cr.setExactDomainHolder(holder);
        cr.setTicketType(DomainOperation.DomainOperationType.REG);
        List<String> newDomains = ticketDao.findDomainNames(cr, 0, limit);
        if (except != null) {
            newDomains.remove(except);
        }
        return newDomains;
    }

    @Override
    public boolean exists(TicketSearchCriteria criteria) {
        return ticketDao.exists(criteria);
    }

}
