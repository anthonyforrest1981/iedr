package pl.nask.crs.accounts.search;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.history.HistoricalObject;

public class HistoricalAccountSearchCriteria implements SearchCriteria<HistoricalObject<Account>> {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
