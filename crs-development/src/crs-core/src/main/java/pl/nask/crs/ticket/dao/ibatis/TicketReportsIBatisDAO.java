package pl.nask.crs.ticket.dao.ibatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.reports.TicketReport;
import pl.nask.crs.reports.search.ReportsSearchCriteria;
import pl.nask.crs.ticket.dao.TicketReportsDAO;

public class TicketReportsIBatisDAO extends GenericIBatisDAO<TicketReport, String> implements TicketReportsDAO {

    private static final Map<String, String> sortMap = new HashMap<>();
    {
        sortMap.put("ticketRevisionsCount", "ticketRevisionsCount");
        sortMap.put("hostmasterName", "hostmasterName");
        sortMap.put("ticketType", "ticketType");
        sortMap.put("reportForDate", "reportForDate");
    }

    public TicketReportsIBatisDAO() {
        setSortMapping(sortMap);
    }

    @Override
    public LimitedSearchResult<TicketReport> findTicketReports(ReportsSearchCriteria criteria, long offset,
            long limit, List<SortCriterion> sortBy) {
        String findQuery = null;
        String countFindQuery = null;

        switch (criteria.getReportTimeGranulation()) {
            case DAY:
                findQuery = "ticket-reports.getTicketReportsDayGranulation";
                countFindQuery = "ticket-reports.getTicketReportsCountDayGranulation";
                break;
            case MONTH:
                findQuery = "ticket-reports.getTicketReportsMonthGranulation";
                countFindQuery = "ticket-reports.getTicketReportsCountMonthGranulation";
                break;
            case YEAR:
                findQuery = "ticket-reports.getTicketReportsYearGranulation";
                countFindQuery = "ticket-reports.getTicketReportsCountYearGranulation";
                break;
            default:
                throw new IllegalArgumentException(criteria.getReportTimeGranulation().name());
        }

        FindParameters params = new FindParameters(criteria).setLimit(offset, limit).setOrderBy(sortBy);

        Integer total = performQueryForObject(countFindQuery, params);
        assert total != null : "@AssumeAssertion(nullness)";
        List<TicketReport> list;
        if (total == 0) {
            list = new ArrayList<>();
        } else {
            list = performQueryForList(findQuery, params);
        }

        return new LimitedSearchResult<>(criteria, limit, offset, list, total);
    }
}
