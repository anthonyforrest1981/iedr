package pl.nask.crs.ticket.services.impl;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.*;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.HistoricalTicketDAO;
import pl.nask.crs.ticket.search.HistoricTicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketHistorySearchService;

public class TicketHistorySearchServiceImpl implements TicketHistorySearchService {

    private static Set<String> sortingCriteria = new HashSet<String>(Arrays.asList("histChangeDate",
            "resellerAccountName", "domainName", "domainHolder", "changedByNicHandle", "type"));

    private HistoricalTicketDAO dao;

    public TicketHistorySearchServiceImpl(HistoricalTicketDAO dao) {
        this.dao = dao;
    }

    public List<HistoricalObject<Ticket>> getTicketHistory(long id) {
        return dao.findAll(id);
    }

    public LimitedSearchResult<HistoricalObject<Ticket>> findTicketHistory(HistoricTicketSearchCriteria criteria,
            long offset, long limit, /*>>>@Nullable*/ List<SortCriterion> sortBy) {
        return dao.find(criteria, offset, limit, sortingCriteria(sortBy));
    }

    private /*>>>@Nullable*/ List<SortCriterion> sortingCriteria(/*>>>@Nullable*/ List<SortCriterion> sortBy) {
        if (sortBy == null)
            return null;
        List<SortCriterion> l = new ArrayList<SortCriterion>();
        for (SortCriterion c : sortBy) {
            if (sortingCriteria.contains(c.getSortBy()))
                l.add(c);
        }
        return l;
    }

}
