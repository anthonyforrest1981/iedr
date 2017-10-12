package pl.nask.crs.ticket;

/*>>>import org.checkerframework.checker.nullness.qual.EnsuresNonNull;*/
/*>>>import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;*/
/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.ticket.operation.DomainOperation;

/**
 * The <code>Ticket</code> class represents a request for an operation
 * to be performed against a given domain. An operation is one of: registration,
 * modification, or transfer of a domain.
 */
public class Ticket {

    /**
     * T_Number PK
     */
    private long id;

    private DomainOperation operation;

    /**
     * Admin_Status not null
     */
    private AdminStatus adminStatus;

    /**
     * Ad_StatusDt not null
     */
    private Date adminStatusChangeDate;

    /**
     * Tech_Status not null
     */
    private TechStatus techStatus;

    /**
     * Tc_StatusDt not null
     */
    private Date techStatusChangeDate;

    /**
     * T_Remark null
     */
    private /*>>>@Nullable*/ String requestersRemark;

    /**
     * H_Remark null
     */
    private /*>>>@Nullable*/ String hostmastersRemark;

    /**
     * Creator_NH
     */
    private Contact creator;

    /**
     * T_Created_TS not null
     */
    private Date creationDate;

    /**
     * T_TStamp not null
     */
    private Date changeDate;

    /**
     * CheckedOut not null, CheckedOutTo null
     */
    private /*>>>@Nullable*/ Contact checkedOutTo;

    /**
     * clikpaid null
     * <p/>
     */
    private boolean clikPaid;

    private boolean hasDocuments;

    /**
     * period
     */
    private /*>>>@Nullable*/ Period domainPeriod;

    private /*>>>@Nullable*/ String charityCode;

    private boolean autorenewMode;

    private FinancialStatus financialStatus;

    private Date financialStatusChangeDate;

    private CustomerStatus customerStatus;

    private Date customerStatusChangeDate;

    public Ticket(long id, DomainOperation operation, AdminStatus adminStatus, Date adminStatusChangeDate,
            TechStatus techStatus, Date techStatusChangeDate, /*>>>@Nullable*/ String requestersRemark, /*>>>@Nullable*/
            String hostmastersRemark, Contact creator, Date creationDate, Date changeDate, /*>>>@Nullable*/
            Contact checkedOutTo, boolean clikPaid, boolean hasDocuments, /*>>>@Nullable*/ Period domainPeriod, /*>>>@Nullable*/
            String charityCode, boolean autorenewMode, FinancialStatus financialStatus, Date financialStatusChangeDate,
            CustomerStatus customerStatus, Date customerStatusChangeDate) {
        Validator.assertNotNull(operation, "domain change operation");
        Validator.assertNotNull(adminStatus, "admin status");
        Validator.assertNotNull(techStatus, "tech status");
        Validator.assertNotNull(financialStatus, "financial status");
        Validator.assertNotNull(creator, "creator");
        Validator.assertNotNull(creationDate, "creation date");
        this.id = id;
        this.operation = operation;
        this.adminStatus = adminStatus;
        this.adminStatusChangeDate = adminStatusChangeDate;
        this.techStatus = techStatus;
        this.techStatusChangeDate = techStatusChangeDate;
        this.requestersRemark = requestersRemark;
        this.hostmastersRemark = hostmastersRemark;
        this.creator = creator;
        this.creationDate = creationDate;
        this.changeDate = changeDate;
        this.checkedOutTo = checkedOutTo;
        this.clikPaid = clikPaid;
        this.hasDocuments = hasDocuments;
        this.domainPeriod = domainPeriod;
        this.charityCode = charityCode;
        this.autorenewMode = autorenewMode;
        this.financialStatus = financialStatus;
        this.financialStatusChangeDate = financialStatusChangeDate;
        this.customerStatus = customerStatus;
        this.customerStatusChangeDate = customerStatusChangeDate;
    }

    public Ticket(DomainOperation operation, Date creationDate, Contact creator, String requestersRemark,
            AdminStatus adminStatus, /*>>>@Nullable*/ Period domainPeriod, /*>>>@Nullable*/ String charityCode,
            boolean autorenewMode) {
        this(-1, operation, adminStatus, creationDate, TechStatus.NEW, creationDate, requestersRemark, null,
                creator, creationDate, creationDate, null, false, false, domainPeriod, charityCode, autorenewMode,
                FinancialStatus.NEW, creationDate, CustomerStatus.NEW, creationDate);
    }

    /**
     * Returns the unique identifier of the ticket.
     *
     * @return the unique identifier of the ticket.
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the operation representing the change to the domain requested in this ticket.
     *
     * @return the domain change operation; never null
     */
    /*>>>@Pure*/
    public DomainOperation getOperation() {
        return operation;
    }

    public /*>>>@Nullable*/ String getRequestersRemark() {
        return requestersRemark;
    }

    public /*>>>@Nullable*/ String getHostmastersRemark() {
        return hostmastersRemark;
    }

    public void setHostmastersRemark(String remark) {
        hostmastersRemark = remark;
    }

    /**
     * Returns the status of the administrative processing of the ticket.
     *
     * @return the status of the administrative processing of the ticket; never null
     */
    public AdminStatus getAdminStatus() {
        return adminStatus;
    }

    /**
     * Sets the status of the administrative processing of the ticket to the given value. Updates
     * the change date of the admin status to a current date in case the change happens i.e. the new status differs from the old one.
     *
     * @param status the new status of the administrative processing
     * @return true if the status has been changed; false if no change was required i.e. the new status equals the old one
     * @throws IllegalArgumentException when the provided status is null
     */
    public boolean setAdminStatus(AdminStatus status) throws IllegalArgumentException {
        Validator.assertNotNull(status, "admin status");
        if (adminStatus != status) {
            adminStatus = status;
            adminStatusChangeDate = new Date();
            return true;
        }
        return false;
    }

    /**
     * Returns the date of the last change of the admin status.
     *
     * @return the date of the last change of the admin status; if there was no change
     * (i.e. the status has its initial value) null
     */
    public Date getAdminStatusChangeDate() {
        return adminStatusChangeDate;
    }

    /**
     * Returns the status of the technical processing of the ticket.
     *
     * @return the status of the technical processing of the ticket; never null
     */
    public TechStatus getTechStatus() {
        return techStatus;
    }

    /**
     * Sets the status of the technical processing of the ticket to the given value. Updates
     * the change date of the tech status to a current date  in case the change happens i.e. the new status differs from the old one.
     *
     * @param status the new status of the technical processing; can't be null
     * @return true if the status has been changed; false if no change was required i.e. the new status equals the old one
     * @throws IllegalArgumentException thrown when the status parameter is null
     */
    public boolean setTechStatus(TechStatus status) throws IllegalArgumentException {
        Validator.assertNotNull(status, "tech status");
        if (techStatus != status) {
            techStatus = status;
            techStatusChangeDate = new Date();
            return true;
        }
        return false;
    }

    /**
     * Returns the date of the last change of the tech status.
     *
     * @return the date of the last change of the tech status
     */
    public Date getTechStatusChangeDate() {
        return techStatusChangeDate;
    }

    /**
     * Returns the contact representing the nic handle that created this ticket.
     *
     * @return the nic handle that created this ticket; never null
     */
    public Contact getCreator() {
        return creator;
    }

    /**
     * Returns the date when the the ticket was created.
     *
     * @return the creation date of the ticket
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Return the date of the last change of the ticket.
     * <p/>
     * todo: when this should be set? what does it mean *change*?
     *
     * @return the date of the last change of the ticket.
     */
    public Date getChangeDate() {
        return changeDate;
    }

    /**
     * todo: should this be publicly available?
     *
     * @param newChangeDate the new change date
     * @throws IllegalArgumentException when the new change date is null or lower than the current change date
     */
    public void setChangeDate(Date newChangeDate) throws IllegalArgumentException {
        Validator.assertNotNull(newChangeDate, "ticket change date");
        // database currently has 1-second resolution anyway
        // we want to have control the data we sent precisely
        if (newChangeDate.before(changeDate)) {
            Logger.getLogger(Ticket.class).error(
                    "New change date [" + newChangeDate + "] is before old change date [" + changeDate + "]");
            throw new IllegalArgumentException("New change date [" + newChangeDate + "] before current change date ["
                    + changeDate + "]");
        }
        changeDate = newChangeDate;
    }

    public void updateChangeDate() {
        setChangeDate(new Date());
    }

    /**
     * Returns the hostmaster this ticket is checked out to. If the ticket is not checked out, returns null.
     *
     * @return the hostmaster this ticket is checked out to; null if not checked out.
     */
    /*>>>@Pure*/
    public /*>>>@Nullable*/ Contact getCheckedOutTo() {
        return checkedOutTo;
    }

    /**
     * Checks out the ticket to the given hostmaster
     *
     * @param checkedOutTo the hostmaster to which this ticket is to be checked out
     * @throws IllegalArgumentException thrown when the checkedOutTo is null
     */
    @SuppressWarnings("nullness")
    /*>>>@EnsuresNonNull("getCheckedOutTo()")*/
    public void checkOut(Contact checkedOutTo) throws IllegalArgumentException {
        Validator.assertNotNull(checkedOutTo, "checked out to");
        this.checkedOutTo = checkedOutTo;
    }

    /**
     * Releases this ticket i.e. sets the checked out to null
     */
    public void checkIn() {
        this.checkedOutTo = null;
    }

    /**
     * Returns true if this is checked out to a hostmaster. To obtain the hostmaster this ticket is checked out to,
     * call getCheckedOutTo method.
     *
     * @return true if this ticket is checked out to a hostmaster; false otherwise
     */
    @SuppressWarnings("nullness")
    /*>>>@EnsuresNonNullIf(expression="getCheckedOutTo()", result=true)*/
    /*>>>@Pure*/
    public boolean isCheckedOut() {
        return checkedOutTo != null;
    }

    public boolean isClikPaid() {
        return clikPaid;
    }

    public void setClikPaid(boolean clikPaid) {
        this.clikPaid = clikPaid;
    }

    /**
     * @return List of all contacts (admin, tech, billing)
     */
    public List<Contact> getAllContactList() {
        return this.operation.getAllContactList();
    }

    public boolean isHasDocuments() {
        return hasDocuments;
    }

    @Override
    public String toString() {
        return "Ticket[id=" + id + "]";
    }

    public void setRequestersRemark(String requestersRemark) {
        this.requestersRemark = requestersRemark;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ Period getDomainPeriod() {
        return domainPeriod;
    }

    public /*>>>@Nullable*/ String getCharityCode() {
        return charityCode;
    }

    public void setCharityCode(String charityCode) {
        this.charityCode = charityCode;
    }

    public FinancialStatus getFinancialStatus() {
        return financialStatus;
    }

    public boolean setFinancialStatus(FinancialStatus status) {
        Validator.assertNotNull(status, "financial status");
        if (financialStatus != status) {
            financialStatus = status;
            financialStatusChangeDate = new Date();
            return true;
        }
        return false;
    }

    public Date getFinancialStatusChangeDate() {
        return financialStatusChangeDate;
    }

    public boolean isCharity() {
        return !Validator.isEmpty(charityCode);
    }

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }

    public boolean setCustomerStatus(CustomerStatus customerStatus) {
        Validator.assertNotNull(customerStatus, "financial status");
        if (this.customerStatus != customerStatus) {
            this.customerStatus = customerStatus;
            customerStatusChangeDate = new Date();
            return true;
        }
        return false;
    }

    public Date getCustomerStatusChangeDate() {
        return customerStatusChangeDate;
    }

    public void updateStatus(AdminStatus as, String remark) {
        setAdminStatus(as);
        this.hostmastersRemark = remark;
    }

    public void updateStatus(TechStatus ts, String remark) {
        setTechStatus(ts);
        this.hostmastersRemark = remark;
    }

    public void updateStatus(FinancialStatus s, String remark) {
        setFinancialStatus(s);
        this.hostmastersRemark = remark;
    }

    public boolean isCreatedByBillingContact() {
        Contact billingC = getOperation().getBillingContactsField().get(0).getNewValue();
        if (billingC == null)
            return false;

        return billingC.equals(getCreator());
    }

    public boolean isCanceled() {
        return adminStatus == AdminStatus.CANCELLED || customerStatus == CustomerStatus.CANCELLED;
    }

    public boolean isAutorenewMode() {
        return autorenewMode;
    }

    public void setAutorenewMode(boolean autorenewMode) {
        this.autorenewMode = autorenewMode;
    }
}
