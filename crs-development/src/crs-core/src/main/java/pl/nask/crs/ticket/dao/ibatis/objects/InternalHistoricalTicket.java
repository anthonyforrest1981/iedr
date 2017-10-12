package pl.nask.crs.ticket.dao.ibatis.objects;

import java.util.Date;

public class InternalHistoricalTicket extends InternalTicket {

    @SuppressWarnings("nullness")
    public InternalHistoricalTicket() {}

    private long changeId;

    private Date histChangeDate;

    private String changedByNicHandle;

    public long getChangeId() {
        return changeId;
    }

    public void setChangeId(long changeId) {
        this.changeId = changeId;
    }

    public Date getHistChangeDate() {
        return histChangeDate;
    }

    public void setHistChangeDate(Date histChangeDate) {
        this.histChangeDate = histChangeDate;
    }

    public String getChangedByNicHandle() {
        return changedByNicHandle;
    }

    public void setChangedByNicHandle(String changedByNicHandle) {
        this.changedByNicHandle = changedByNicHandle;
    }

}
