package pl.nask.crs.ticket.search;

/*>>>import org.checkerframework.checker.nullness.qual.MonotonicNonNull;*/
/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.ticket.*;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;

public class TicketSearchCriteria implements SearchCriteria<Ticket> {

    private /*>>>@Nullable*/ Date from;
    private /*>>>@Nullable*/ Date to;
    private /*>>>@Nullable*/ AdminStatus adminStatus;
    private /*>>>@Nullable*/ TechStatus techStatus;
    private /*>>>@Nullable*/ CustomerStatus customerStatus;
    private /*>>>@Nullable*/ String nicHandle;
    private /*>>>@Nullable*/ String domainName;
    private /*>>>@Nullable*/ String domainHolder;
    private /*>>>@Nullable*/ Long accountId; // reseller account id
    private /*>>>@Nullable*/ Long categoryId;
    private /*>>>@Nullable*/ Long classId;
    private /*>>>@Nullable*/ String categoryName;
    private /*>>>@Nullable*/ String className;
    private /*>>>@Nullable*/ List<DomainOperationType> type;
    private /*>>>@Nullable*/ String billNicHandle;
    private /*>>>@Nullable*/ FinancialStatus financialStatus;
    private /*>>>@Nullable*/ String creatorNh;
    private /*>>>@Nullable*/ String exactDomainName;
    private /*>>>@Nullable*/ String exactDomainHolder;
    private /*>>>@Nullable*/ String anyContactNH;
    private /*>>>@Nullable*/ Long ticketId;

    public TicketSearchCriteria() {}

    public /*>>>@Nullable*/ Date getFrom() {
        return from;
    }

    public void setFrom(/*>>>@Nullable*/ Date from) {
        this.from = DateUtils.startOfDay(from);
    }

    public /*>>>@Nullable*/ Date getTo() {
        return to;
    }

    public void setTo(/*>>>@Nullable*/ Date to) {
        this.to = DateUtils.endOfDay(to);
    }

    public /*>>>@Nullable*/ AdminStatus getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(/*>>>@Nullable*/ AdminStatus adminStatus) {
        this.adminStatus = adminStatus;
    }

    public /*>>>@Nullable*/ TechStatus getTechStatus() {
        return techStatus;
    }

    public void setTechStatus(/*>>>@Nullable*/ TechStatus techStatus) {
        this.techStatus = techStatus;
    }

    public /*>>>@Nullable*/ CustomerStatus getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(/*>>>@Nullable*/ CustomerStatus customerStatus) {
        this.customerStatus = customerStatus;
    }

    public /*>>>@Nullable*/ String getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(/*>>>@Nullable*/ String nicHandle) {
        this.nicHandle = nicHandle;
    }

    public /*>>>@Nullable*/ String getDomainName() {
        return domainName;
    }

    public void setDomainName(/*>>>@Nullable*/ String domainName) {
        this.domainName = domainName;
    }

    public /*>>>@Nullable*/ String getDomainHolder() {
        return domainHolder;
    }

    public void setDomainHolder(/*>>>@Nullable*/ String domainHolder) {
        this.domainHolder = domainHolder;
    }

    public /*>>>@Nullable*/ Long getAccountId() {
        return accountId;
    }

    public void setAccountId(/*>>>@Nullable*/ Long accountId) {
        this.accountId = accountId;
    }

    public void setCategoryId(/*>>>@Nullable*/ Long categoryId) {
        this.categoryId = categoryId;
    }

    public /*>>>@Nullable*/ Long getCategoryId() {
        return categoryId;
    }

    public /*>>>@Nullable*/ Long getClassId() {
        return classId;
    }

    public void setClassId(/*>>>@Nullable*/ Long classId) {
        this.classId = classId;
    }

    public void setCategoryName(/*>>>@Nullable*/ String categoryName) {
        this.categoryName = categoryName;
    }

    public /*>>>@Nullable*/ String getCategoryName() {
        return categoryName;
    }

    public /*>>>@Nullable*/ String getClassName() {
        return className;
    }

    public void setClassName(/*>>>@Nullable*/ String className) {
        this.className = className;
    }

    public void setTicketType(DomainOperationType... type) {
        this.type = Arrays.asList(type);
    }

    //ws require simple getter
    public void setType(/*>>>@Nullable*/ List<DomainOperationType> type) {
        this.type = type;
    }

    public /*>>>@Nullable*/ List<DomainOperationType> getType() {
        return type;
    }

    public /*>>>@Nullable*/ String getBillNicHandle() {
        return billNicHandle;
    }

    public void setBillNicHandle(/*>>>@Nullable*/ String billNicHandle) {
        this.billNicHandle = billNicHandle;
    }

    public /*>>>@Nullable*/ FinancialStatus getFinancialStatus() {
        return financialStatus;
    }

    public void setFinancialStatus(/*>>>@Nullable*/ FinancialStatus financialStatus) {
        this.financialStatus = financialStatus;
    }

    public void setCreatorNh(/*>>>@Nullable*/ String creatorNh) {
        this.creatorNh = creatorNh;
    }

    public /*>>>@Nullable*/ String getCreatorNh() {
        return creatorNh;
    }

    public void setExactDomainName(/*>>>@Nullable*/ String exactDomainName) {
        this.exactDomainName = exactDomainName;
    }

    public /*>>>@Nullable*/ String getExactDomainName() {
        return exactDomainName;
    }

    public void setExactDomainHolder(/*>>>@Nullable*/ String exactDomainHolder) {
        this.exactDomainHolder = exactDomainHolder;
    }

    public /*>>>@Nullable*/ String getExactDomainHolder() {
        return exactDomainHolder;
    }

    public /*>>>@Nullable*/ Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(/*>>>@Nullable*/ Long ticketId) {
        this.ticketId = ticketId;
    }

    public /*>>>@Nullable*/ String getAnyContactNH() {
        return anyContactNH;
    }

    public void setAnyContactNH(String anyContactNH) {
        this.anyContactNH = anyContactNH;
    }

    // required to be called after ws unpacks ticketsearchcriteria from soap
    @SuppressWarnings("nullness")
    public void filterValues() {
        if (type != null) {
            type.remove(null);
        }
    }
}
