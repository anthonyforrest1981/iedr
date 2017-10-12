package pl.nask.crs.commons.email.search;

import pl.nask.crs.commons.email.EmailDisabler;
import pl.nask.crs.commons.search.SearchCriteria;

public class EmailDisablerSearchCriteria implements SearchCriteria<EmailDisabler> {
    protected Integer emailId;
    protected String nicHandle;

    public EmailDisablerSearchCriteria() {}

    public EmailDisablerSearchCriteria(Integer emailId, String nicHandle) {
        this.emailId = emailId;
        this.nicHandle = nicHandle;
    }

    public Integer getEmailId() {
        return emailId;
    }

    public void setEmailId(Integer emailId) {
        this.emailId = emailId;
    }

    public String getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(String nicHandle) {
        this.nicHandle = nicHandle;
    }
}
