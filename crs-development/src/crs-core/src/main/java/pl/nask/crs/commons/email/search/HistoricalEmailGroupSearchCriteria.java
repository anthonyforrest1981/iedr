package pl.nask.crs.commons.email.search;

import pl.nask.crs.commons.email.EmailGroup;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.history.HistoricalObject;

public class HistoricalEmailGroupSearchCriteria implements SearchCriteria<HistoricalObject<EmailGroup>> {
    protected Long id;

    public HistoricalEmailGroupSearchCriteria() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
