package pl.nask.crs.documents.dao;

import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.reports.DocumentReport;
import pl.nask.crs.reports.search.ReportsSearchCriteria;

public interface DocumentReportsDAO {

    LimitedSearchResult<DocumentReport> findDocumentReports(ReportsSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy);

}
