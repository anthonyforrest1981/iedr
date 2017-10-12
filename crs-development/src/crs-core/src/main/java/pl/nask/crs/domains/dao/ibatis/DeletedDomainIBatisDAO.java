package pl.nask.crs.domains.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.DeletedDomain;
import pl.nask.crs.domains.dao.DeletedDomainDAO;

import static pl.nask.crs.domains.dao.ibatis.DomainSortMapHelper.getDeletedDomainSortMap;

public class DeletedDomainIBatisDAO extends GenericIBatisDAO<DeletedDomain, String> implements DeletedDomainDAO {

    private ApplicationConfig config;

    public DeletedDomainIBatisDAO(ApplicationConfig config) {
        this.config = config;
        setSortMapping(getDeletedDomainSortMap());
    }

    public LimitedSearchResult<DeletedDomain> find(SearchCriteria<? super DeletedDomain> criteria, long offset,
            long limit, List<SortCriterion> sortBy) {
        long finalDsmState = config.getEligibleForDeletionDomainState();
        Map<String, Object> map = new HashMap<>();
        map.put("finalDsmState", finalDsmState);
        return performFindWithParameters("deleted-domain.findDeletedDomains", "deleted-domain.countFindDeleted",
                criteria, offset, limit, sortBy, map);
    }

}
