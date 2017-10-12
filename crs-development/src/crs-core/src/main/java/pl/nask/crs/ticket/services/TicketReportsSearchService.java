package pl.nask.crs.ticket.services;

import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.reports.TicketReport;
import pl.nask.crs.reports.search.ReportsSearchCriteria;

public interface TicketReportsSearchService {

    LimitedSearchResult<TicketReport> find(ReportsSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> orderBy);

}
