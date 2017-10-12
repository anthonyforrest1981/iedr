package pl.nask.crs.documents.dao.ibatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.documents.dao.DocumentReportsDAO;
import pl.nask.crs.reports.DocumentReport;
import pl.nask.crs.reports.search.ReportsSearchCriteria;

public class DocumentReportsIBatisDAO extends GenericIBatisDAO<DocumentReport, String> implements DocumentReportsDAO {

    private static final Map<String, String> sortingMap = new HashMap<>();
    {
        sortingMap.put("hostmasterName", "hostmasterName");
        sortingMap.put("reportForDate", "reportForDate");
    }

    public LimitedSearchResult<DocumentReport> findDocumentReports(ReportsSearchCriteria criteria, long offset,
            long limit, List<SortCriterion> sortBy) {
        String findQuery = null;
        String countFindQuery = null;

        switch (criteria.getReportTimeGranulation()) {
            case DAY:
                findQuery = "document-reports.getDocumentReportsDayGranulation";
                countFindQuery = "document-reports.getDocumentReportsCountDayGranulation";
                break;
            case MONTH:
                findQuery = "document-reports.getDocumentReportsMonthGranulation";
                countFindQuery = "document-reports.getDocumentReportsCountMonthGranulation";
                break;
            case YEAR:
                findQuery = "document-reports.getDocumentReportsYearGranulation";
                countFindQuery = "document-reports.getDocumentReportsCountYearGranulation";
                break;
            default:
                throw new IllegalArgumentException(criteria.getReportTimeGranulation().name());
        }

        FindParameters params = new FindParameters(criteria).setLimit(offset, limit).setOrderBy(sortBy);

        Integer total = performQueryForObject(countFindQuery, params);

        List<DocumentReport> list;
        if (total == 0) {
            list = new ArrayList<>();
        } else {
            list = performQueryForList(findQuery, params);
        }

        return new LimitedSearchResult<>(criteria, limit, offset, list, total);
    }
}
