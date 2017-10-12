package pl.nask.crs.domains.search;

import java.util.List;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.contacts.ContactType;
import pl.nask.crs.domains.DomainCountForContact;
import pl.nask.crs.domains.NRPStatus;

public class DomainCountForContactSearchCriteria implements SearchCriteria<DomainCountForContact> {

    private String nicHandle;
    private List<ContactType> contactType;
    private Boolean activeFlag;
    private List<NRPStatus> nrpStatuses;

    public String getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(String nicHandle) {
        this.nicHandle = nicHandle;
    }

    public List<ContactType> getContactType() {
        return contactType;
    }

    public void setContactType(List<ContactType> contactType) {
        this.contactType = contactType;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public List<NRPStatus> getNrpStatuses() {
        return nrpStatuses;
    }

    public void setNrpStatuses(List<NRPStatus> nrpStatuses) {
        this.nrpStatuses = nrpStatuses;
    }

    public void filterNrpStatuses() {
        if (nrpStatuses != null)
            nrpStatuses.remove(null);
        if (nrpStatuses == null || nrpStatuses.size() == 0) {
            nrpStatuses = NRPStatus.getActiveList(activeFlag);
        }
    }

}
