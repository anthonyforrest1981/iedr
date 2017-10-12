package pl.nask.crs.reports.service;

import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.reports.DomainsPerClass;
import pl.nask.crs.reports.TotalDomains;
import pl.nask.crs.reports.TotalDomainsPerDate;
import pl.nask.crs.reports.search.DomainsPerClassSearchCriteria;
import pl.nask.crs.reports.search.TotalDomainsCriteria;
import pl.nask.crs.reports.search.TotalDomainsPerDateCriteria;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface ReportsService {

    LimitedSearchResult<TotalDomains> findTotalDomains(TotalDomainsCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy);

    LimitedSearchResult<TotalDomainsPerDate> findTotalDomainsPerDate(TotalDomainsPerDateCriteria criteria, long offset,
            long limit, List<SortCriterion> sortBy);

    LimitedSearchResult<DomainsPerClass> findTotalDomainsPerClass(DomainsPerClassSearchCriteria criteria, long offset,
            long limit, List<SortCriterion> sortBy);
}
