package pl.nask.crs.domains.dao;

import java.util.List;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.PlainDomain;
import pl.nask.crs.domains.search.PlainDomainSearchCriteria;

public interface PlainDomainDAO extends GenericDAO<PlainDomain, String> {

    LimitedSearchResult<PlainDomain> findTransferredInDomains(PlainDomainSearchCriteria searchCriteria, long offset,
            long limit, List<SortCriterion> sortBy);

    LimitedSearchResult<PlainDomain> findTransferredAwayDomains(PlainDomainSearchCriteria searchCriteria, long offset,
            long limit, List<SortCriterion> sortBy);

}
