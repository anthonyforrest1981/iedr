package pl.nask.crs.ticket.dao.ibatis.objects;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.EntitySubcategory;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.CustomerStatus;
import pl.nask.crs.ticket.FinancialStatus;
import pl.nask.crs.ticket.TechStatus;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.operation.FailureReason;

public class InternalTicket implements Serializable {

    public enum DataSet {
        /**
         * InternalTicket is missing DNS data
         */
        SIMPLE("simple"),
        /**
         * InternalTicket contains all data, including DNS
         */
        FULL("full");

        private String name;

        private DataSet(String name) {
            this.name = name;
        }

        static public DataSet fromName(String name) {
            if (name.equals(SIMPLE.name))
                return SIMPLE;
            else if (name.equals(FULL.name))
                return FULL;
            else
                throw new IllegalArgumentException("Unknown type of InternalTicket: " + name);
        }
    }

    /**
     * Describes type of result from DB.
     * <ul>
     * <li><code>simple</code> is missing DNS entries</li>
     * <li><code>full</code> includes DNS servers</li>
     * </ul>
     * It's illegal to try to read DNS servers from a <code>simple</code> result type. First set of nameservers automatically sets
     * type of dataSetType to extended.
     */
    private /*>>>@Nullable*/ DataSet dataSetType = DataSet.SIMPLE;

    private long id;

    private DomainOperationType type;

    private String domainName;
    private /*>>>@Nullable*/ FailureReason domainNameFailureReason;

    private String domainHolder;
    private /*>>>@Nullable*/ FailureReason domainHolderFailureReason;

    private EntityClass domainHolderClass;
    private /*>>>@Nullable*/ FailureReason domainHolderClassFailureReason;

    private EntityCategory domainHolderCategory;
    private /*>>>@Nullable*/ FailureReason domainHolderCategoryFailureReason;

    private /*>>>@Nullable*/ EntitySubcategory domainHolderSubcategory;

    private long resellerAccountId;

    private /*>>>@Nullable*/ String resellerAccountName;
    private /*>>>@Nullable*/ String resellerAccountBillingContact;
    private /*>>>@Nullable*/ FailureReason resellerAccountFailureReason;
    private boolean resellerAccountAgreementSigned;
    private boolean resellerAccountTicketEdit;
    private /*>>>@Nullable*/ String adminContact1NicHandle;

    private String adminContact1Name;
    private /*>>>@Nullable*/ String adminContact1Email;
    private /*>>>@Nullable*/ String adminContact1CompanyName;
    private /*>>>@Nullable*/ String adminContact1Country;
    private /*>>>@Nullable*/ FailureReason adminContact1FailureReason;
    private /*>>>@Nullable*/ String adminContact2NicHandle;

    private /*>>>@Nullable*/ String adminContact2Name;
    private /*>>>@Nullable*/ String adminContact2Email;
    private /*>>>@Nullable*/ String adminContact2CompanyName;
    private /*>>>@Nullable*/ String adminContact2Country;
    private /*>>>@Nullable*/ FailureReason adminContact2FailureReason;
    private /*>>>@Nullable*/ String techContactNicHandle;

    private String techContactName;
    private /*>>>@Nullable*/ String techContactEmail;
    private /*>>>@Nullable*/ String techContactCompanyName;
    private /*>>>@Nullable*/ String techContactCountry;
    private /*>>>@Nullable*/ FailureReason techContactFailureReason;
    private /*>>>@Nullable*/ String billingContactNicHandle;

    private String billingContactName;
    private /*>>>@Nullable*/ String billingContactEmail;
    private /*>>>@Nullable*/ String billingContactCompanyName;
    private /*>>>@Nullable*/ String billingContactCountry;
    private /*>>>@Nullable*/ FailureReason billingContactFailureReason;
    private List<InternalTicketNameserver> nameservers;

    private /*>>>@Nullable*/ String requestersRemark;

    private /*>>>@Nullable*/ String hostmastersRemark;

    private AdminStatus adminStatus;
    private Date adminStatusChangeDate;

    private TechStatus techStatus;
    private Date techStatusChangeDate;

    private FinancialStatus financialStatus;
    private Date financialStatusChangeDate;

    private CustomerStatus customerStatus;
    private Date customerStatusChangeDate;

    private boolean checkedOut;

    private boolean autorenewMode;

    private /*>>>@Nullable*/ String checkedOutToNicHandle;

    private /*>>>@Nullable*/ String checkedOutToName;

    private String creatorNicHandle;
    private /*>>>@Nullable*/ String creatorName;
    private /*>>>@Nullable*/ String creatorEmail;
    private /*>>>@Nullable*/ String creatorCompanyName;
    private /*>>>@Nullable*/ String creatorCountry;

    private Date creationDate;

    private Date changeDate;

    private /*>>>@Nullable*/ Date renewalDate;

    private boolean clikPaid;

    private long documentsCount;

    private /*>>>@Nullable*/ Integer domainPeriod;

    private /*>>>@Nullable*/ String charityCode;

    @SuppressWarnings("nullness")
    public InternalTicket() {}

    public /*>>>@Nullable*/ DataSet getDataSetType() {
        return dataSetType;
    }

    public void setDataSetType(String dataSetType) {
        this.dataSetType = DataSet.fromName(dataSetType);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DomainOperationType getType() {
        return type;
    }

    public void setType(DomainOperationType type) {
        this.type = type;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public /*>>>@Nullable*/ FailureReason getDomainNameFailureReason() {
        return domainNameFailureReason;
    }

    public void setDomainNameFailureReason(/*>>>@Nullable*/ FailureReason domainNameFailureReason) {
        this.domainNameFailureReason = domainNameFailureReason;
    }

    public String getDomainHolder() {
        return domainHolder;
    }

    public void setDomainHolder(String domainHolder) {
        this.domainHolder = domainHolder;
    }

    public /*>>>@Nullable*/ FailureReason getDomainHolderFailureReason() {
        return domainHolderFailureReason;
    }

    public void setDomainHolderFailureReason(/*>>>@Nullable*/ FailureReason domainHolderFailureReason) {
        this.domainHolderFailureReason = domainHolderFailureReason;
    }

    public EntityClass getDomainHolderClass() {
        return domainHolderClass;
    }

    public void setDomainHolderClass(EntityClass domainHolderClass) {
        this.domainHolderClass = domainHolderClass;
    }

    public /*>>>@Nullable*/ FailureReason getDomainHolderClassFailureReason() {
        return domainHolderClassFailureReason;
    }

    public void setDomainHolderClassFailureReason(/*>>>@Nullable*/ FailureReason domainHolderClassFailureReason) {
        this.domainHolderClassFailureReason = domainHolderClassFailureReason;
    }

    public EntityCategory getDomainHolderCategory() {
        return domainHolderCategory;
    }

    public void setDomainHolderCategory(EntityCategory domainHolderCategory) {
        this.domainHolderCategory = domainHolderCategory;
    }

    public /*>>>@Nullable*/ FailureReason getDomainHolderCategoryFailureReason() {
        return domainHolderCategoryFailureReason;
    }

    public void setDomainHolderCategoryFailureReason(/*>>>@Nullable*/ FailureReason domainHolderCategoryFailureReason) {
        this.domainHolderCategoryFailureReason = domainHolderCategoryFailureReason;
    }

    public /*>>>@Nullable*/ EntitySubcategory getDomainHolderSubcategory() {
        return domainHolderSubcategory;
    }

    public void setDomainHolderSubcategory(/*>>>@Nullable*/ EntitySubcategory domainHolderSubcategory) {
        this.domainHolderSubcategory = domainHolderSubcategory;
    }

    public long getResellerAccountId() {
        return resellerAccountId;
    }

    public void setResellerAccountId(long resellerAccountId) {
        this.resellerAccountId = resellerAccountId;
    }

    public /*>>>@Nullable*/ String getResellerAccountName() {
        return resellerAccountName;
    }

    public void setResellerAccountName(String resellerAccountName) {
        this.resellerAccountName = resellerAccountName;
    }

    public /*>>>@Nullable*/ String getResellerAccountBillingContact() {
        return resellerAccountBillingContact;
    }

    public void setResellerAccountBillingContact(String resellerAccountBillingContact) {
        this.resellerAccountBillingContact = resellerAccountBillingContact;
    }

    public /*>>>@Nullable*/ FailureReason getResellerAccountFailureReason() {
        return resellerAccountFailureReason;
    }

    public void setResellerAccountFailureReason(/*>>>@Nullable*/ FailureReason resellerAccountFailureReason) {
        this.resellerAccountFailureReason = resellerAccountFailureReason;
    }

    public /*>>>@Nullable*/ String getAdminContact1NicHandle() {
        return adminContact1NicHandle;
    }

    public void setAdminContact1NicHandle(/*>>>@Nullable*/ String adminContact1NicHandle) {
        this.adminContact1NicHandle = adminContact1NicHandle;
    }

    public String getAdminContact1Name() {
        return adminContact1Name;
    }

    public void setAdminContact1Name(String adminContact1Name) {
        this.adminContact1Name = adminContact1Name;
    }

    public /*>>>@Nullable*/ FailureReason getAdminContact1FailureReason() {
        return adminContact1FailureReason;
    }

    public void setAdminContact1FailureReason(/*>>>@Nullable*/ FailureReason adminContact1FailureReason) {
        this.adminContact1FailureReason = adminContact1FailureReason;
    }

    public /*>>>@Nullable*/ String getAdminContact2NicHandle() {
        return adminContact2NicHandle;
    }

    public void setAdminContact2NicHandle(/*>>>@Nullable*/ String adminContact2NicHandle) {
        this.adminContact2NicHandle = adminContact2NicHandle;
    }

    public /*>>>@Nullable*/ String getAdminContact2Name() {
        return adminContact2Name;
    }

    public void setAdminContact2Name(/*>>>@Nullable*/ String adminContact2Name) {
        this.adminContact2Name = adminContact2Name;
    }

    public /*>>>@Nullable*/ FailureReason getAdminContact2FailureReason() {
        return adminContact2FailureReason;
    }

    public void setAdminContact2FailureReason(/*>>>@Nullable*/ FailureReason adminContact2FailureReason) {
        this.adminContact2FailureReason = adminContact2FailureReason;
    }

    public /*>>>@Nullable*/ String getTechContactNicHandle() {
        return techContactNicHandle;
    }

    public void setTechContactNicHandle(/*>>>@Nullable*/ String techContactNicHandle) {
        this.techContactNicHandle = techContactNicHandle;
    }

    public String getTechContactName() {
        return techContactName;
    }

    public void setTechContactName(String techContactName) {
        this.techContactName = techContactName;
    }

    public /*>>>@Nullable*/ FailureReason getTechContactFailureReason() {
        return techContactFailureReason;
    }

    public void setTechContactFailureReason(/*>>>@Nullable*/ FailureReason techContactFailureReason) {
        this.techContactFailureReason = techContactFailureReason;
    }

    public /*>>>@Nullable*/ String getBillingContactNicHandle() {
        return billingContactNicHandle;
    }

    public void setBillingContactNicHandle(/*>>>@Nullable*/ String billingContactNicHandle) {
        this.billingContactNicHandle = billingContactNicHandle;
    }

    public String getBillingContactName() {
        return billingContactName;
    }

    public void setBillingContactName(String billingContactName) {
        this.billingContactName = billingContactName;
    }

    public /*>>>@Nullable*/ FailureReason getBillingContactFailureReason() {
        return billingContactFailureReason;
    }

    public void setBillingContactFailureReason(/*>>>@Nullable*/ FailureReason billingContactFailureReason) {
        this.billingContactFailureReason = billingContactFailureReason;
    }

    public /*>>>@Nullable*/ String getRequestersRemark() {
        return requestersRemark;
    }

    public void setRequestersRemark(/*>>>@Nullable*/ String requestersRemark) {
        this.requestersRemark = requestersRemark;
    }

    public /*>>>@Nullable*/ String getHostmastersRemark() {
        return hostmastersRemark;
    }

    public void setHostmastersRemark(/*>>>@Nullable*/ String hostmastersRemark) {
        this.hostmastersRemark = hostmastersRemark;
    }

    /*>>>@Pure*/
    public AdminStatus getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(AdminStatus adminStatus) {
        this.adminStatus = adminStatus;
    }

    public Date getAdminStatusChangeDate() {
        return adminStatusChangeDate;
    }

    public void setAdminStatusChangeDate(Date adminStatusChangeDate) {
        this.adminStatusChangeDate = adminStatusChangeDate;
    }

    /*>>>@Pure*/
    public TechStatus getTechStatus() {
        return techStatus;
    }

    public void setTechStatus(TechStatus techStatus) {
        this.techStatus = techStatus;
    }

    public Date getTechStatusChangeDate() {
        return techStatusChangeDate;
    }

    public void setTechStatusChangeDate(Date techStatusChangeDate) {
        this.techStatusChangeDate = techStatusChangeDate;
    }

    public boolean getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    public /*>>>@Nullable*/ String getCheckedOutToNicHandle() {
        return checkedOutToNicHandle;
    }

    public void setCheckedOutToNicHandle(/*>>>@Nullable*/ String checkedOutToNicHandle) {
        this.checkedOutToNicHandle = checkedOutToNicHandle;
    }

    public /*>>>@Nullable*/ String getCheckedOutToName() {
        return checkedOutToName;
    }

    public void setCheckedOutToName(String checkedOutToName) {
        this.checkedOutToName = checkedOutToName;
    }

    public String getCreatorNicHandle() {
        return creatorNicHandle;
    }

    public void setCreatorNicHandle(String creatorNicHandle) {
        this.creatorNicHandle = creatorNicHandle;
    }

    public /*>>>@Nullable*/ String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public boolean isClikPaid() {
        return clikPaid;
    }

    public void setClikPaid(boolean clikPaid) {
        this.clikPaid = clikPaid;
    }

    public /*>>>@Nullable*/ String getAdminContact1Email() {
        return adminContact1Email;
    }

    public void setAdminContact1Email(String adminContact1Email) {
        this.adminContact1Email = adminContact1Email;
    }

    public /*>>>@Nullable*/ String getAdminContact2Email() {
        return adminContact2Email;
    }

    public void setAdminContact2Email(String adminContact2Email) {
        this.adminContact2Email = adminContact2Email;
    }

    public /*>>>@Nullable*/ String getTechContactEmail() {
        return techContactEmail;
    }

    public void setTechContactEmail(String techContactEmail) {
        this.techContactEmail = techContactEmail;
    }

    public /*>>>@Nullable*/ String getBillingContactEmail() {
        return billingContactEmail;
    }

    public void setBillingContactEmail(String billingContactEmail) {
        this.billingContactEmail = billingContactEmail;
    }

    public /*>>>@Nullable*/ String getAdminContact1CompanyName() {
        return adminContact1CompanyName;
    }

    public void setAdminContact1CompanyName(String adminContact1CompanyName) {
        this.adminContact1CompanyName = adminContact1CompanyName;
    }

    public /*>>>@Nullable*/ String getAdminContact2CompanyName() {
        return adminContact2CompanyName;
    }

    public void setAdminContact2CompanyName(String adminContact2CompanyName) {
        this.adminContact2CompanyName = adminContact2CompanyName;
    }

    public /*>>>@Nullable*/ String getTechContactCompanyName() {
        return techContactCompanyName;
    }

    public void setTechContactCompanyName(String techContactCompanyName) {
        this.techContactCompanyName = techContactCompanyName;
    }

    public /*>>>@Nullable*/ String getBillingContactCompanyName() {
        return billingContactCompanyName;
    }

    public void setBillingContactCompanyName(String billingContactCompanyName) {
        this.billingContactCompanyName = billingContactCompanyName;
    }

    public /*>>>@Nullable*/ String getAdminContact1Country() {
        return adminContact1Country;
    }

    public void setAdminContact1Country(String adminContact1Country) {
        this.adminContact1Country = adminContact1Country;
    }

    public /*>>>@Nullable*/ String getAdminContact2Country() {
        return adminContact2Country;
    }

    public void setAdminContact2Country(String adminContact2Country) {
        this.adminContact2Country = adminContact2Country;
    }

    public /*>>>@Nullable*/ String getTechContactCountry() {
        return techContactCountry;
    }

    public void setTechContactCountry(String techContactCountry) {
        this.techContactCountry = techContactCountry;
    }

    public /*>>>@Nullable*/ String getBillingContactCountry() {
        return billingContactCountry;
    }

    public void setBillingContactCountry(String billingContactCountry) {
        this.billingContactCountry = billingContactCountry;
    }

    public /*>>>@Nullable*/ String getCreatorCountry() {
        return creatorCountry;
    }

    public void setCreatorCountry(String creatorCountry) {
        this.creatorCountry = creatorCountry;
    }

    public /*>>>@Nullable*/ String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public /*>>>@Nullable*/ String getCreatorCompanyName() {
        return creatorCompanyName;
    }

    public void setCreatorCompanyName(String creatorCompanyName) {
        this.creatorCompanyName = creatorCompanyName;
    }

    public void setRenewalDate(/*>>>@Nullable*/ Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    public /*>>>@Nullable*/ Date getRenewalDate() {
        return renewalDate;
    }

    public void setDocumentsCount(long documentsCount) {
        this.documentsCount = documentsCount;
    }

    public long getDocumentsCount() {
        return documentsCount;
    }

    public List<InternalTicketNameserver> getNameservers() {
        if (dataSetType == DataSet.SIMPLE)
            throw new IllegalStateException("Simple internal ticket does not hold any information about nameservers");
        return nameservers;
    }

    public void setNameservers(List<InternalTicketNameserver> nameservers) {
        this.nameservers = nameservers;
        this.dataSetType = DataSet.FULL;
    }

    public boolean isResellerAccountAgreementSigned() {
        return resellerAccountAgreementSigned;
    }

    public void setResellerAccountAgreementSigned(boolean resellerAccountAgreementSigned) {
        this.resellerAccountAgreementSigned = resellerAccountAgreementSigned;
    }

    public boolean isResellerAccountTicketEdit() {
        return resellerAccountTicketEdit;
    }

    public void setResellerAccountTicketEdit(boolean resellerAccountTicketEdit) {
        this.resellerAccountTicketEdit = resellerAccountTicketEdit;
    }

    public /*>>>@Nullable*/ Integer getDomainPeriod() {
        return domainPeriod;
    }

    public void setDomainPeriod(/*>>>@Nullable*/ Integer domainPeriod) {
        this.domainPeriod = domainPeriod;
    }

    public /*>>>@Nullable*/ String getCharityCode() {
        return charityCode;
    }

    public void setCharityCode(/*>>>@Nullable*/ String charityCode) {
        this.charityCode = charityCode;
    }

    public FinancialStatus getFinancialStatus() {
        return financialStatus;
    }

    public void setFinancialStatus(FinancialStatus financialStatus) {
        this.financialStatus = financialStatus;
    }

    public Date getFinancialStatusChangeDate() {
        return financialStatusChangeDate;
    }

    public void setFinancialStatusChangeDate(Date financialStatusChangeDate) {
        this.financialStatusChangeDate = financialStatusChangeDate;
    }

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(CustomerStatus customerStatus) {
        this.customerStatus = customerStatus;
    }

    public Date getCustomerStatusChangeDate() {
        return customerStatusChangeDate;
    }

    public void setCustomerStatusChangeDate(Date customerStatusChangeDate) {
        this.customerStatusChangeDate = customerStatusChangeDate;
    }

    public boolean isAutorenewMode() {
        return autorenewMode;
    }

    public void setAutorenewMode(boolean autorenewMode) {
        this.autorenewMode = autorenewMode;
    }
}
