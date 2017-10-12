package pl.nask.crs.commons.email.search;

import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.history.HistoricalObject;

public class HistoricalEmailTemplateSearchCriteria implements SearchCriteria<HistoricalObject<EmailTemplate>> {
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
