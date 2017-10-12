package pl.nask.crs.accounts.dao.ibatis.objects;

import java.util.Date;

public class InternalHistoricalAccount extends InternalAccount {

    private Long changeId;
    private Date histChangeDate;
    private String changedByNicHandle;

    public InternalHistoricalAccount() {
        super();
    }

    public InternalHistoricalAccount(InternalAccount ia, long changeId, Date histChangeDate,
            String changedByNicHandle) {
        super(ia.getId(), ia.getName(), ia.getStatus(), ia.getWebAddress(), ia.getRemark(), ia.getBillingContactId(),
                ia.getBillingContactName(), ia.getBillingContactEmail(), ia.getBillingContactCompanyName(),
                ia.getBillingContactCountry(), ia.getCreationDate(), ia.getStatusChangeDate(), ia.getChangeDate(),
                ia.isAgreementSigned(), ia.isTicketEdit(), ia.getSegmentId());
        this.changeId = changeId;
        this.histChangeDate = histChangeDate;
        this.changedByNicHandle = changedByNicHandle;
    }

    public Long getChangeId() {
        return changeId;
    }

    public void setChangeId(Long changeId) {
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
