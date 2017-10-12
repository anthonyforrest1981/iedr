package pl.nask.crs.nichandle.dao.ibatis.objects;

import java.util.Date;

/**
 * @author Marianna Mysiorska
 */
public class InternalHistoricalNicHandle extends InternalNicHandle {

    private long changeId;

    private Date histChangeDate;

    private String changedByNicHandle;

    public InternalHistoricalNicHandle() {
        super();
    }

    public InternalHistoricalNicHandle(InternalNicHandle inh, long changeId, Date histChangeDate,
            String changedByNicHandle) {
        super(inh.getNicHandleId(), inh.getName(), inh.getAccountNumber(), inh.getAccountName(), inh.getCompanyName(),
                inh.getAddress(), inh.getTelecoms(), inh.getCountry(), inh.getCounty(), inh.getEmail(), inh.getStatus(),
                inh.getStatusChangeDate(), inh.getRegistrationDate(), inh.getChangeDate(), inh.isBillCInd(),
                inh.getNicHandleRemark(), inh.getCreator(), inh.getVatNo(), inh.getVatCategory(), inh.isExported());
        this.changeId = changeId;
        this.histChangeDate = histChangeDate;
        this.changedByNicHandle = changedByNicHandle;
    }

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
