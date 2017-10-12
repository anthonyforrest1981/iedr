package pl.nask.crs.nichandle.search;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle;

public class HistoricalNicHandleSearchCriteria implements SearchCriteria<HistoricalObject<NicHandle>> {

    private String nicHandleId;

    public String getNicHandleId() {
        return ("".equals(nicHandleId)) ? null : nicHandleId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }
}
