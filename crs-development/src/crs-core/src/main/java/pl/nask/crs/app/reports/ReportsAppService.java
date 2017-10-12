package pl.nask.crs.app.reports;

import java.util.List;

import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.reports.*;
import pl.nask.crs.reports.search.DomainsPerClassSearchCriteria;
import pl.nask.crs.reports.search.ReportsSearchCriteria;
import pl.nask.crs.reports.search.TotalDomainsCriteria;
import pl.nask.crs.reports.search.TotalDomainsPerDateCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public interface ReportsAppService extends AppSearchService<AbstractTicketReport, ReportsSearchCriteria> {

    LimitedSearchResult<TotalDomains> findTotalDomains(AuthenticatedUser user, TotalDomainsCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException;

    LimitedSearchResult<TotalDomainsPerDate> findTotalDomainsPerDate(AuthenticatedUser user,
            TotalDomainsPerDateCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException;

    LimitedSearchResult<DomainsPerClass> findTotalDomainsPerClass(AuthenticatedUser user,
            DomainsPerClassSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException;

    // needed to redeclare it here to allow the authorization mechanism to check the permissions for this
    @Override
    LimitedSearchResult<AbstractTicketReport> search(AuthenticatedUser user, ReportsSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException;

}
