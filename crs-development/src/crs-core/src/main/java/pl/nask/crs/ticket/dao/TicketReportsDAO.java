package pl.nask.crs.ticket.dao;

import java.util.List;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.reports.TicketReport;
import pl.nask.crs.reports.search.ReportsSearchCriteria;

public interface TicketReportsDAO {

    LimitedSearchResult<TicketReport> findTicketReports(ReportsSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy);
}
