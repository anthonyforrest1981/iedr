package pl.nask.crs.api.vo.search;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.CustomerStatus;
import pl.nask.crs.ticket.FinancialStatus;
import pl.nask.crs.ticket.TechStatus;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.search.TicketSearchCriteria;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketSearchCriteriaVO {

    private /*>>>@Nullable*/ Date from;
    private /*>>>@Nullable*/ Date to;
    private /*>>>@Nullable*/ AdminStatus adminStatus;
    private /*>>>@Nullable*/ TechStatus techStatus;
    private /*>>>@Nullable*/ CustomerStatus customerStatus;
    private /*>>>@Nullable*/ FinancialStatus financialStatus;
    private /*>>>@Nullable*/ String domainName;
    private /*>>>@Nullable*/ String domainHolder;
    private /*>>>@Nullable*/ Long accountId;
    private /*>>>@Nullable*/ List<DomainOperationType> type;

    public TicketSearchCriteria toSearchCriteria() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setFrom(from);
        criteria.setTo(to);
        criteria.setAdminStatus(adminStatus);
        criteria.setTechStatus(techStatus);
        criteria.setCustomerStatus(customerStatus);
        criteria.setFinancialStatus(financialStatus);
        criteria.setDomainName(domainName);
        criteria.setDomainHolder(domainHolder);
        criteria.setAccountId(accountId);
        criteria.setType(type);
        return criteria;
    }

    // required to be called after ws unpacks ticketsearchcriteria from soap
    @SuppressWarnings("nullness")
    public void filterValues() {
        if (type != null) {
            type.remove(null);
        }
    }

}
