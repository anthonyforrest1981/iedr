package pl.nask.crs.commons.email.search;

import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.history.HistoricalObject;

public class HistoricalEmailDisablerSearchCriteria implements SearchCriteria<HistoricalObject<EmailDisabler>> {
    protected Long emailId;
    protected String nicHandle;

    public HistoricalEmailDisablerSearchCriteria() {}

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    public String getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(String nicHandle) {
        this.nicHandle = nicHandle;
    }

}
