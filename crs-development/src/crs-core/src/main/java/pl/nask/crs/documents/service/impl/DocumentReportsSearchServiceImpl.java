package pl.nask.crs.documents.service.impl;

import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.documents.dao.DocumentReportsDAO;
import pl.nask.crs.documents.service.DocumentReportsSearchService;
import pl.nask.crs.reports.DocumentReport;
import pl.nask.crs.reports.search.ReportsSearchCriteria;

public class DocumentReportsSearchServiceImpl implements DocumentReportsSearchService {

    private DocumentReportsDAO documentReportsDAO;

    public DocumentReportsSearchServiceImpl(DocumentReportsDAO documentReportsDAO) {
        Validator.assertNotNull(documentReportsDAO, "document reports dao");
        this.documentReportsDAO = documentReportsDAO;
    }

    public LimitedSearchResult<DocumentReport> find(ReportsSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> orderBy) {
        return documentReportsDAO.findDocumentReports(criteria, offset, limit, orderBy);
    }
}
