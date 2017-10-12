package pl.nask.crs.app.reports.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import pl.nask.crs.app.reports.ReportsAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.documents.service.DocumentReportsSearchService;
import pl.nask.crs.reports.*;
import pl.nask.crs.reports.search.DomainsPerClassSearchCriteria;
import pl.nask.crs.reports.search.ReportsSearchCriteria;
import pl.nask.crs.reports.search.TotalDomainsCriteria;
import pl.nask.crs.reports.search.TotalDomainsPerDateCriteria;
import pl.nask.crs.reports.service.ReportsService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.ticket.services.TicketReportsSearchService;

import static pl.nask.crs.app.utils.UserValidator.validateLoggedIn;

public class ReportsAppServiceImpl implements ReportsAppService {

    private TicketReportsSearchService ticketReportsSearchService;
    private DocumentReportsSearchService documentReportsSearchService;
    private ReportsService reportsService;

    public ReportsAppServiceImpl(TicketReportsSearchService ticketReportsSearchService,
            DocumentReportsSearchService documentReportsSearchService, ReportsService reportsService) {
        Validator.assertNotNull(ticketReportsSearchService, "ticket reports service");
        Validator.assertNotNull(documentReportsSearchService, "document reports service");
        this.ticketReportsSearchService = ticketReportsSearchService;
        this.documentReportsSearchService = documentReportsSearchService;
        this.reportsService = reportsService;
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<AbstractTicketReport> search(AuthenticatedUser user,
            ReportsSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy)
            throws AccessDeniedException {
        validateLoggedIn(user);
        switch (criteria.getReportType()) {
            case TICKET_REVISIONS:
                return castResult(ticketReportsSearchService.find(criteria, offset, limit, orderBy));
            case DOCUMENTS_LOGGED:
                return castResult(documentReportsSearchService.find(criteria, offset, limit, orderBy));
            case ALL:
                // we cannot sort it, that is why orderBy is not passed
                return findAllReports(criteria, offset, limit);
            default:
                throw new IllegalArgumentException("Invalid report type: " + criteria.getReportType().name());
        }
    }

    private LimitedSearchResult<AbstractTicketReport> findAllReports(ReportsSearchCriteria criteria, long offset,
            long limit) {
        LimitedSearchResult<TicketReport> ticketReports =
                ticketReportsSearchService.find(criteria, offset, limit, null);
        long totalTicketReports = ticketReports.getTotalResults();
        long subOffset = 0;
        long subLimit = 0;
        if (totalTicketReports < offset + limit) {
            subOffset = Math.max(offset - totalTicketReports, 0);
            subLimit = Math.min(limit, offset + limit - totalTicketReports);
        }
        LimitedSearchResult<DocumentReport> documentReports =
                documentReportsSearchService.find(criteria, subOffset, subLimit, null);
        return joinResults(ticketReports, documentReports);
    }

    private LimitedSearchResult<AbstractTicketReport> joinResults(
            LimitedSearchResult<? extends AbstractTicketReport> firstResult,
            LimitedSearchResult<? extends AbstractTicketReport> secondResult) {
        List<AbstractTicketReport> retReslts = new ArrayList<>();
        retReslts.addAll(firstResult.getResults());
        retReslts.addAll(secondResult.getResults());
        LimitedSearchResult<AbstractTicketReport> ret =
                new LimitedSearchResult<>(firstResult.getCriteria(), firstResult.getLimit(), firstResult.getOffset(),
                        retReslts, firstResult.getTotalResults() + secondResult.getTotalResults());
        return ret;
    }

    private LimitedSearchResult<AbstractTicketReport> castResult(LimitedSearchResult<? extends AbstractTicketReport> result) {
        return new LimitedSearchResult<>(result.getCriteria(), result.getLimit(), result.getOffset(),
                new ArrayList<>(result.getResults()), result.getTotalResults());
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<TotalDomains> findTotalDomains(AuthenticatedUser user, TotalDomainsCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy) throws UserNotAuthenticatedException {
        validateLoggedIn(user);
        return reportsService.findTotalDomains(criteria, offset, limit, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<TotalDomainsPerDate> findTotalDomainsPerDate(AuthenticatedUser user,
            TotalDomainsPerDateCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws UserNotAuthenticatedException {
        validateLoggedIn(user);
        return reportsService.findTotalDomainsPerDate(criteria, offset, limit, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<DomainsPerClass> findTotalDomainsPerClass(AuthenticatedUser user,
            DomainsPerClassSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws UserNotAuthenticatedException {
        validateLoggedIn(user);
        return reportsService.findTotalDomainsPerClass(criteria, offset, limit, sortBy);
    }
}
