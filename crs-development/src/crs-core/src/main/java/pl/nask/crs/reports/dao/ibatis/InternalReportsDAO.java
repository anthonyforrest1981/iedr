package pl.nask.crs.reports.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.reports.DomainsPerClass;
import pl.nask.crs.reports.TotalDomains;
import pl.nask.crs.reports.TotalDomainsPerDate;
import pl.nask.crs.reports.dao.ReportsDAO;
import pl.nask.crs.reports.search.DomainsPerClassSearchCriteria;
import pl.nask.crs.reports.search.TotalDomainsCriteria;
import pl.nask.crs.reports.search.TotalDomainsPerDateCriteria;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InternalReportsDAO extends GenericIBatisDAO implements ReportsDAO {

    private static final Map<String, String> totalDomainsSortMap = new HashMap<>();
    {
        totalDomainsSortMap.put("domainCount", "domainCount");
        totalDomainsSortMap.put("billingNH", "billingNH");
        totalDomainsSortMap.put("accountName", "accountName");
    }
    private static final Map<String, String> totalDomainsPerDateSortMap = new HashMap<>();
    {
        totalDomainsPerDateSortMap.put("domainCount", "domainCount");
        totalDomainsPerDateSortMap.put("billingNH", "billingNH");
        totalDomainsPerDateSortMap.put("accountName", "accountName");
        totalDomainsPerDateSortMap.put("date", "date");
        totalDomainsPerDateSortMap.put("className", "className");
        totalDomainsPerDateSortMap.put("categoryName", "categoryName");
    }
    private static final Map<String, String> domainsPerClassSortMap = new HashMap<>();
    {
        domainsPerClassSortMap.put("className", "className");
        domainsPerClassSortMap.put("categoryName", "categoryName");
        domainsPerClassSortMap.put("domainCount", "domainCount");
        domainsPerClassSortMap.put("accountName", "accountName");
        domainsPerClassSortMap.put("billNHId", "billNHId");
    }

    @Override
    public LimitedSearchResult<TotalDomains> findTotalDomains(TotalDomainsCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy) {
        setSortMapping(totalDomainsSortMap);
        return performFind("reports.getTotalDomains", "reports.getTotalDomainsCount", criteria, offset, limit, sortBy);
    }

    @Override
    public LimitedSearchResult<TotalDomainsPerDate> findTotalDomainsPerDate(TotalDomainsPerDateCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy) {
        setSortMapping(totalDomainsPerDateSortMap);
        switch (criteria.getReportTypeGranulation()) {
            case REGISTRAR:
                switch (criteria.getReportTimeGranulation()) {
                    case MONTH:
                        return performFind("reports.getDomainsPerMonth", "reports.getDomainsPerMonthCount", criteria,
                                offset, limit, sortBy);
                    case YEAR:
                        return performFind("reports.getDomainsPerYear", "reports.getDomainsPerYearCount", criteria,
                                offset, limit, sortBy);
                    default:
                        throw new IllegalArgumentException("Illegal time granulation: "
                                + criteria.getReportTimeGranulation());
                }
            case ALLREGISTRARS:
                switch (criteria.getReportTimeGranulation()) {
                    case MONTH:
                        return performFind("reports.getTotalDomainsPerMonth", "reports.getTotalDomainsPerMonthCount",
                                criteria, offset, limit, sortBy);
                    case YEAR:
                        return performFind("reports.getTotalDomainsPerYear", "reports.getTotalDomainsPerYearCount",
                                criteria, offset, limit, sortBy);
                    default:
                        throw new IllegalArgumentException("Illegal time granulation: "
                                + criteria.getReportTimeGranulation());
                }
            default:
                throw new IllegalArgumentException("Illegal type granulation: " + criteria.getReportTimeGranulation());
        }
    }

    @Override
    public LimitedSearchResult<DomainsPerClass> findTotalDomainsPerClass(DomainsPerClassSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy) {
        setSortMapping(domainsPerClassSortMap);
        return performFind("reports.getDomainsPerClass", "reports.getDomainsPerClassCount", criteria, offset, limit,
                sortBy);
    }
}
