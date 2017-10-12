package pl.nask.crs.domains.services;

import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.*;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.nameservers.NsReport;
import pl.nask.crs.domains.search.*;

/**
 * @author Kasia Fulara
 */
public interface DomainSearchService {

    Domain getDomain(String name) throws DomainNotFoundException;

    boolean exists(String name);

    boolean exists(DomainSearchCriteria criteria);

    LimitedSearchResult<Domain> find(DomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy);

    LimitedSearchResult<PlainDomain> findPlain(PlainDomainSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> orderBy);

    List<Domain> findAll(DomainSearchCriteria criteria, List<SortCriterion> sortBy);

    LimitedSearchResult<Domain> fullFind(DomainSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> orderBy);

    LimitedSearchResult<PlainDomain> findTransferredInDomains(PlainDomainSearchCriteria searchCriteria, long offset,
            long limit, List<SortCriterion> sortBy);

    LimitedSearchResult<PlainDomain> findTransferredAwayDomains(PlainDomainSearchCriteria searchCriteria, long offset,
            long limit, List<SortCriterion> sortBy);

    LimitedSearchResult<DeletedDomain> findDeletedDomains(DeletedDomainSearchCriteria criteria, long offset,
            long limit, List<SortCriterion> orderBy);

    List<String> findDomainNames(DomainSearchCriteria criteria, int offset, int limit);

    LimitedSearchResult<ExtendedDomain> findExtended(ExtendedDomainSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy);

    List<ExtendedDomain> findExtended(ExtendedDomainSearchCriteria criteria, List<SortCriterion> sortBy);

    LimitedSearchResult<Domain> findDomainAutorenewals(DomainSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy);

    LimitedSearchResult<NsReport> getNsReports(String billingNH, NsReportSearchCriteria criteria, long offset,
            long limit, List<SortCriterion> sortBy);

    List<DomainCountForContact> findDomainCountForContact(DomainCountForContactSearchCriteria criteria);

    List<String> findDomainsForHolder(String holder, int limit, String except);
}
