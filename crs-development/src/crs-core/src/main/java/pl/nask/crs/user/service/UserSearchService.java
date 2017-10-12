package pl.nask.crs.user.service;

import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.user.User;
import pl.nask.crs.user.search.UserSearchCriteria;

public interface UserSearchService {

    LimitedSearchResult<User> find(UserSearchCriteria searchCriteria, long offset, long limit);

    SearchResult<User> find(UserSearchCriteria searchCriteria);

    User get(String nicHandleId);

    List<String> findInternalUsers();
}
