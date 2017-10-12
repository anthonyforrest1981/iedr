package pl.nask.crs.documents.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.documents.AbstractContextAwareTest;
import pl.nask.crs.reports.DocumentReport;
import pl.nask.crs.reports.ReportTimeGranulation;
import pl.nask.crs.reports.search.ReportsSearchCriteria;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class DocumentReportsDAOTest extends AbstractContextAwareTest {

    @Resource
    DocumentReportsDAO documentReportsDAO;

    @Test
    public void findAllReports() {
        ReportsSearchCriteria criteria = new ReportsSearchCriteria();
        LimitedSearchResult<DocumentReport> result = null;

        criteria.setReportTimeGranulation(ReportTimeGranulation.DAY);
        result = documentReportsDAO.findDocumentReports(criteria, 0, 10, null);
        assertEquals("reports for day", 10, result.getResults().size());
        assertEquals("total reports for day", 10, result.getTotalResults());
        Set<String> hostmasterNames = new HashSet<>();
        for (DocumentReport tr : result.getResults()) {
            hostmasterNames.add(tr.getHostmasterName());
        }
        assertTrue(hostmasterNames.contains("IDL2-IËDP"));
        assertTrue(hostmasterNames.contains("IDË2-IEDR"));
        hostmasterNames.clear();

        criteria.setReportTimeGranulation(ReportTimeGranulation.MONTH);
        result = documentReportsDAO.findDocumentReports(criteria, 0, 10, null);
        assertEquals("reports for month", 8, result.getResults().size());
        assertEquals("total reports for month", 8, result.getTotalResults());
        for (DocumentReport tr : result.getResults()) {
            hostmasterNames.add(tr.getHostmasterName());
        }
        assertTrue(hostmasterNames.contains("IDL2-IËDP"));
        assertTrue(hostmasterNames.contains("IDË2-IEDR"));
        hostmasterNames.clear();

        criteria.setReportTimeGranulation(ReportTimeGranulation.YEAR);
        result = documentReportsDAO.findDocumentReports(criteria, 0, 10, null);
        assertEquals("reports for year", 6, result.getResults().size());
        assertEquals("total reports for year", 6, result.getTotalResults());
        for (DocumentReport tr : result.getResults()) {
            hostmasterNames.add(tr.getHostmasterName());
        }
        assertTrue(hostmasterNames.contains("IDL2-IËDP"));
        assertTrue(hostmasterNames.contains("IDË2-IEDR"));
        hostmasterNames.clear();
    }

    @Test
    public void findAllUserReports() {
        ReportsSearchCriteria criteria = new ReportsSearchCriteria();
        criteria.setHostmasterName("CIARA-IEDR");
        LimitedSearchResult<DocumentReport> result = null;

        criteria.setReportTimeGranulation(ReportTimeGranulation.DAY);
        result = documentReportsDAO.findDocumentReports(criteria, 0, 10, null);
        assertEquals("reports for day", 5, result.getResults().size());
        assertEquals("total reports for day", 5, result.getTotalResults());

        criteria.setReportTimeGranulation(ReportTimeGranulation.MONTH);
        result = documentReportsDAO.findDocumentReports(criteria, 0, 10, null);
        assertEquals("reports for month", 3, result.getResults().size());
        assertEquals("total reports for month", 3, result.getTotalResults());

        criteria.setReportTimeGranulation(ReportTimeGranulation.YEAR);
        result = documentReportsDAO.findDocumentReports(criteria, 0, 10, null);
        assertEquals("reports for year", 2, result.getResults().size());
        assertEquals("total reports for year", 2, result.getTotalResults());
    }

    @Test
    public void findUserReportsWithDateLimit() {
        ReportsSearchCriteria criteria = new ReportsSearchCriteria();
        criteria.setHostmasterName("CIARA-IEDR");
        criteria.setFrom(new Date(1224672872796L));
        criteria.setTo(new Date());
        LimitedSearchResult<DocumentReport> result = null;

        criteria.setReportTimeGranulation(ReportTimeGranulation.DAY);
        result = documentReportsDAO.findDocumentReports(criteria, 0, 10, null);
        assertEquals("reports for day", 4, result.getResults().size());
        assertEquals("total reports for day", 4, result.getTotalResults());

        criteria.setReportTimeGranulation(ReportTimeGranulation.MONTH);
        result = documentReportsDAO.findDocumentReports(criteria, 0, 10, null);
        assertEquals("reports for month", 3, result.getResults().size());
        assertEquals("total reports for month", 3, result.getTotalResults());

        criteria.setReportTimeGranulation(ReportTimeGranulation.YEAR);
        result = documentReportsDAO.findDocumentReports(criteria, 0, 10, null);
        assertEquals("reports for year", 2, result.getResults().size());
        assertEquals("total reports for year", 2, result.getTotalResults());
    }

    @Test
    public void findUserReportsWithOffset() {
        ReportsSearchCriteria criteria = new ReportsSearchCriteria();
        criteria.setHostmasterName("CIARA-IEDR");
        LimitedSearchResult<DocumentReport> result = null;

        criteria.setReportTimeGranulation(ReportTimeGranulation.DAY);
        result = documentReportsDAO.findDocumentReports(criteria, 2, 5, null);
        assertEquals("reports for day with offset, limit", 3, result.getResults().size());
        assertEquals("total report for day", 5, result.getTotalResults());

        criteria.setReportTimeGranulation(ReportTimeGranulation.DAY);
        result = documentReportsDAO.findDocumentReports(criteria, 0, 3, null);
        assertEquals("reports for day with limit", 3, result.getResults().size());
        assertEquals("total report for day", 5, result.getTotalResults());
    }

}
