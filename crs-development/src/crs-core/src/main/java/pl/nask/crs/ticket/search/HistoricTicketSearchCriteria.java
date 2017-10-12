package pl.nask.crs.ticket.search;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.ticket.Ticket;

public class HistoricTicketSearchCriteria implements SearchCriteria<HistoricalObject<Ticket>> {

    private /*>>>@Nullable*/ Long ticketId;

    private /*>>>@Nullable*/ String domainName;

    private /*>>>@Nullable*/ String domainHolder;

    private /*>>>@Nullable*/ Long accountId;

    private /*>>>@Nullable*/ Long categoryId;
    private /*>>>@Nullable*/ Long classId;

    public /*>>>@Nullable*/ Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public /*>>>@Nullable*/ String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public /*>>>@Nullable*/ String getDomainHolder() {
        return domainHolder;
    }

    public void setDomainHolder(String domainHolder) {
        this.domainHolder = domainHolder;
    }

    public /*>>>@Nullable*/ Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public /*>>>@Nullable*/ Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public /*>>>@Nullable*/ Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

}
