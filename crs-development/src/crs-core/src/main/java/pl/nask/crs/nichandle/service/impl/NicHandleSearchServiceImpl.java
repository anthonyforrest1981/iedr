package pl.nask.crs.nichandle.service.impl;

import java.util.List;

import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.NicHandleStatus;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.nichandle.exception.NicHandleIncorrectForAccountException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;
import pl.nask.crs.nichandle.service.NicHandleSearchService;

public class NicHandleSearchServiceImpl implements NicHandleSearchService {

    private NicHandleDAO nicHandleDAO;
    private ApplicationConfig applicationConfig;

    public NicHandleSearchServiceImpl(NicHandleDAO nicHandleDAO, ApplicationConfig applicationConfig) {
        Validator.assertNotNull(nicHandleDAO, "nic handle DAO");
        Validator.assertNotNull(applicationConfig, "applicationConfig");
        this.nicHandleDAO = nicHandleDAO;
        this.applicationConfig = applicationConfig;
    }

    public NicHandle getNicHandle(String nicHandleId) throws NicHandleNotFoundException {
        NicHandle ret = nicHandleDAO.get(nicHandleId);
        if (ret == null)
            throw new NicHandleNotFoundException(nicHandleId);
        return ret;
    }

    public SearchResult<NicHandle> findNicHandle(NicHandleSearchCriteria criteria) {
        return nicHandleDAO.find(criteria);
    }

    public LimitedSearchResult<NicHandle> findNicHandle(NicHandleSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> orderBy) {
        return nicHandleDAO.find(criteria, offset, limit, orderBy);
    }

    @Override
    public void confirmNicHandleActive(String nicHandleId)
            throws NicHandleNotFoundException, NicHandleNotActiveException {
        NicHandle nicHandle = nicHandleDAO.get(nicHandleId);
        if (nicHandle == null)
            throw new NicHandleNotFoundException(nicHandleId);
        if (nicHandle.getStatus() != NicHandleStatus.Active)
            throw new NicHandleNotActiveException(nicHandleId);
    }

    @Override
    public void validateNicHandleWithAccount(String nicHandleId, long accountId)
            throws NicHandleNotFoundException, NicHandleIncorrectForAccountException {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setNicHandleId(nicHandleId);
        criteria.setAccountNumber(accountId);
        SearchResult<NicHandle> searchResult = nicHandleDAO.find(criteria);
        if (searchResult.getResults().isEmpty()) {
            throw new NicHandleIncorrectForAccountException(nicHandleId, accountId);
        }
    }

    @Override
    public boolean isNicHandleDirect(String nicHandleId) throws NicHandleNotFoundException {
        NicHandle nicHandle = getNicHandle(nicHandleId);
        return isNicHandleDirect(nicHandle);
    }

    @Override
    public boolean isNicHandleDirect(NicHandle nicHandle) {
        return isAccountDirect(nicHandle.getAccount().getId());
    }

    @Override
    public boolean isAccountDirect(long accountId) {
        return accountId == applicationConfig.getGuestAccountId();
    }

}
