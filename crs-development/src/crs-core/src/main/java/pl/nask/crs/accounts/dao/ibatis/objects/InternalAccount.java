package pl.nask.crs.accounts.dao.ibatis.objects;

import java.util.Date;

import pl.nask.crs.accounts.AccountStatus;

/**
 * @author Patrycja Wegrzynowicz
 */
public class InternalAccount {

    private long id;
    private String name;
    private AccountStatus status;
    private String webAddress;
    private String remark;
    private String billingContactId;
    private String billingContactName;
    private String billingContactEmail;
    private String billingContactCompanyName;
    private String billingContactCountry;
    private Date creationDate;
    private Date statusChangeDate;
    private Date changeDate;
    private Long segmentId;
    // Feature #2373 - flags for "signed agreement" and "edit ticket"
    protected boolean agreementSigned;
    protected boolean ticketEdit;

    public InternalAccount() {
        this.id = -1;
    }

    public InternalAccount(long id, String name, AccountStatus status, String webAddress, String remark,
            String billingContactId, String billingContactName, String billingContactEmail,
            String billingContactCompanyName, String billingContactCountry, Date creationDate, Date statusChangeDate,
            Date changeDate, boolean agreementSigned, boolean ticketEdit, Long segmentId) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.webAddress = webAddress;
        this.remark = remark;
        this.billingContactId = billingContactId;
        this.billingContactName = billingContactName;
        this.billingContactEmail = billingContactEmail;
        this.billingContactCompanyName = billingContactCompanyName;
        this.billingContactCountry = billingContactCountry;
        this.creationDate = creationDate;
        this.statusChangeDate = statusChangeDate;
        this.changeDate = changeDate;
        this.agreementSigned = agreementSigned;
        this.ticketEdit = ticketEdit;
        this.segmentId = segmentId;
    }

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

    public String getBillingContactId() {
        return billingContactId;
    }

    public void setBillingContactId(String billingContactId) {
        this.billingContactId = billingContactId;
    }

    public String getBillingContactName() {
        return billingContactName;
    }

    public void setBillingContactName(String billingContactName) {
        this.billingContactName = billingContactName;
    }

    public String getBillingContactEmail() {
        return billingContactEmail;
    }

    public void setBillingContactEmail(String billingContactEmail) {
        this.billingContactEmail = billingContactEmail;
    }

    public String getBillingContactCompanyName() {
        return billingContactCompanyName;
    }

    public void setBillingContactCompanyName(String billingContactCompanyName) {
        this.billingContactCompanyName = billingContactCompanyName;
    }

    public String getBillingContactCountry() {
        return billingContactCountry;
    }

    public void setBillingContactCountry(String billingContactCountry) {
        this.billingContactCountry = billingContactCountry;
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

    public Long getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(Long segmentId) {
        this.segmentId = segmentId;
    }
}
