package pl.nask.crs.accounts;

import java.util.Date;

import pl.nask.crs.commons.utils.Validator;

/**
 * Plain class, useful when different object (e.g. Domain or BuyRequest)
 * needs to have Account data, but not necessarily all of them and would prefer not to use
 * Account (as Account joins to NicHandle and also requires converter from InternalAccount
 * to Account)
 */
public class PlainAccount {

    protected long id;
    protected String name;
    protected AccountStatus status;
    protected String webAddress;
    protected String remark;
    protected String billingNH;
    protected Date creationDate;
    protected Date statusChangeDate;
    protected Date changeDate;
    // Not used by the application, stored here only to keep the process of writing historical records correct
    // id instead of a full object because of efficiency reasons
    protected Long segmentId;
    protected boolean agreementSigned;
    protected boolean ticketEdit;

    public PlainAccount() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBillingNH() {
        return billingNH;
    }

    public void setBillingNH(String billingNH) {
        this.billingNH = billingNH;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getStatusChangeDate() {
        return statusChangeDate;
    }

    public void setStatusChangeDate(Date statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public Long getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(Long segmentId) {
        this.segmentId = segmentId;
    }

    public boolean isAgreementSigned() {
        return agreementSigned;
    }

    public void setAgreementSigned(boolean agreementSigned) {
        this.agreementSigned = agreementSigned;
    }

    public boolean isTicketEdit() {
        return ticketEdit;
    }

    public void setTicketEdit(boolean ticketEdit) {
        this.ticketEdit = ticketEdit;
    }

    public boolean isActive() {
        return AccountStatus.Active.equals(status);
    }

    public boolean isSuspended() {
        return AccountStatus.Suspended.equals(status);
    }

    public boolean isDeleted() {
        return AccountStatus.Deleted.equals(status);
    }

    /**
     * If new status is different from current one, sets it <em>and updates
     * status change date</em>
     * @param status
     * @return
     */
    public boolean updateStatus(AccountStatus status) {
        Validator.assertNotNull(status, "account status");
        if (!status.equals(this.status)) {
            this.status = status;
            this.statusChangeDate = new Date();
            return true;
        }
        return false;
    }

    public void updateRemark(String newRemark, String hostmasterHandle) {
        this.remark = newRemark + " by " + hostmasterHandle + " on " + new Date();
    }

    public void validateFlags() {
        if (!agreementSigned && ticketEdit)
            throw new IllegalStateException("ticketEdit cannot be set to true if agreementSigned is not set to true");
    }

    @Override
    public String toString() {
        return String.format("%s[id=%s, name='%s', billingNH='%s']", this.getClass().getSimpleName(), id, name, billingNH);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlainAccount)) return false;

        PlainAccount that = (PlainAccount) o;

        return getId() == that.getId();

    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }
}
