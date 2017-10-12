package pl.nask.crs.app;

import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * Application search service interface.
 */
public interface AppSearchService<T, SEARCH_CRITERIA_TYPE extends SearchCriteria<? super T>> {
    LimitedSearchResult<T> search(AuthenticatedUser user, SEARCH_CRITERIA_TYPE criteria, long offset,
            long limit, List<SortCriterion> orderBy) throws AccessDeniedException;
}
