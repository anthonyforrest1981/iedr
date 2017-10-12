package pl.nask.crs.domains.dao.ibatis;

import java.util.List;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.PlainDomain;
import pl.nask.crs.domains.dao.PlainDomainDAO;
import pl.nask.crs.domains.search.PlainDomainSearchCriteria;

import static pl.nask.crs.domains.dao.ibatis.DomainSortMapHelper.getPlainDomainSortMap;

public class PlainDomainIBatisDAO extends GenericIBatisDAO<PlainDomain, String> implements PlainDomainDAO {

    public PlainDomainIBatisDAO() {
        setFindQueryId("plain-domain.findPlainDomain");
        setCountFindQueryId("plain-domain.findPlainDomainCount");
        setSortMapping(getPlainDomainSortMap());
    }

    @Override
    public LimitedSearchResult<PlainDomain> findTransferredInDomains(PlainDomainSearchCriteria searchCriteria,
            long offset, long limit, List<SortCriterion> sortBy) {
        String transferCountQueryId = "plain-domain.findTransferredInDomainsCount";
        String transferQueryId = "plain-domain.findTransferredInDomains";
        return performFind(transferQueryId, transferCountQueryId, searchCriteria, offset, limit, sortBy);
    }

    @Override
    public LimitedSearchResult<PlainDomain> findTransferredAwayDomains(PlainDomainSearchCriteria searchCriteria,
            long offset, long limit, List<SortCriterion> sortBy) {
        String transferCountQueryId = "plain-domain.findTransferredAwayDomainsCount";
        String transferQueryId = "plain-domain.findTransferredAwayDomains";
        return performFind(transferQueryId, transferCountQueryId, searchCriteria, offset, limit, sortBy);
    }

}
