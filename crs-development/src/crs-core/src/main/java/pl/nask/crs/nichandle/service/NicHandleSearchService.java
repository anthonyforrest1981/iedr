package pl.nask.crs.nichandle.service;

import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleIncorrectForAccountException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;

public interface NicHandleSearchService {

    NicHandle getNicHandle(String nicHandleId) throws NicHandleNotFoundException;

    SearchResult<NicHandle> findNicHandle(NicHandleSearchCriteria criteria);

    LimitedSearchResult<NicHandle> findNicHandle(NicHandleSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> orderBy);

    void confirmNicHandleActive(String nicHandleId) throws NicHandleNotFoundException, NicHandleNotActiveException;

    void validateNicHandleWithAccount(String nicHandleId, long accountId)
            throws NicHandleNotFoundException, NicHandleIncorrectForAccountException;

    boolean isNicHandleDirect(String nicHandleId) throws NicHandleNotFoundException;

    boolean isNicHandleDirect(NicHandle nicHandle);

    boolean isAccountDirect(long accountId);

}
