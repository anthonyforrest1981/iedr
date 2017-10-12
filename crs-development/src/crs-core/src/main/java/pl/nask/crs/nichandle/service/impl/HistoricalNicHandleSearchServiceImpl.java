package pl.nask.crs.nichandle.service.impl;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.HistoricalNicHandleDAO;
import pl.nask.crs.nichandle.search.HistoricalNicHandleSearchCriteria;
import pl.nask.crs.nichandle.service.HistoricalNicHandleSearchService;

public class HistoricalNicHandleSearchServiceImpl implements HistoricalNicHandleSearchService {

    private HistoricalNicHandleDAO historicalNicHandleDAO;

    public HistoricalNicHandleSearchServiceImpl(HistoricalNicHandleDAO historicalNicHandleDAO) {
        Validator.assertNotNull(historicalNicHandleDAO, "historical nic handle dao");
        this.historicalNicHandleDAO = historicalNicHandleDAO;
    }

    public LimitedSearchResult<HistoricalObject<NicHandle>> findHistoricalNicHandle(
            HistoricalNicHandleSearchCriteria criteria, int offset, int limit) {
        return historicalNicHandleDAO.find(criteria, offset, limit);
    }

}
