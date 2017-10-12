package pl.nask.crs.commons.email.service;

import java.util.List;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;

public interface EmailGroupService {

    LimitedSearchResult<EmailGroup> findEmailGroups(SearchCriteria<EmailGroup> criteria, long offset, long limit,
            List<SortCriterion> orderBy);

    SearchResult<EmailGroup> findEmailGroups(SearchCriteria<EmailGroup> criteria, List<SortCriterion> orderBy);

    EmailGroup get(long id);

    void create(EmailGroup group, OpInfo op);

    void update(EmailGroup group, OpInfo op);

    void delete(EmailGroup group, OpInfo opInfo);
}
