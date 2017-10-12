package pl.nask.crs.accounts.services;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.search.HistoricalAccountSearchCriteria;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.history.HistoricalObject;

/**
 * @author Marianna Mysiorska
 */
public interface HistoricalAccountSearchService {

    public SearchResult<HistoricalObject<Account>> findHistoricalNicHandle(HistoricalAccountSearchCriteria criteria);
}
