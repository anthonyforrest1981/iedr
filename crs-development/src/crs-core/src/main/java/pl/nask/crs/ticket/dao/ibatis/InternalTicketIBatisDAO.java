package pl.nask.crs.ticket.dao.ibatis;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.SequentialNumberGenerator;
import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.TicketNotification;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalTicket;

public class InternalTicketIBatisDAO extends GenericIBatisDAO<InternalTicket, Long> {
    private /*>>>@Nullable*/ SequentialNumberGenerator idGenerator;

    private static final Map<String, String> sortMap = new HashMap<>();
    {
        sortMap.put("ticketId", "ticketId");
        sortMap.put("type", "type");
        sortMap.put("domainName", "domainName");
        sortMap.put("domainNameFR", "domainNameFR");
        sortMap.put("domainHolder", "domainHolder");
        sortMap.put("domainHolderFR", "domainHolderFR");
        sortMap.put("resellerAccountId", "resellerAccountId");
        sortMap.put("resellerAccountName", "resellerAccountName");
        sortMap.put("resellerAccountBillingContact", "resellerAccountBillingContact");
        sortMap.put("agreementSigned", "agreementSigned");
        sortMap.put("ticketEdit", "ticketEdit");
        sortMap.put("resellerAccountFR", "resellerAccountFR");
        sortMap.put("classId", "classId");
        sortMap.put("className", "className");
        sortMap.put("domainHolderClassFR", "domainHolderClassFR");
        sortMap.put("categoryId", "categoryId");
        sortMap.put("categoryName", "categoryName");
        sortMap.put("domainHolderCatFR", "domainHolderCatFR");
        sortMap.put("adminContact1NH", "adminContact1NH");
        sortMap.put("adminContact1Name", "adminContact1Name");
        sortMap.put("adminContact1Email", "adminContact1Email");
        sortMap.put("adminContact1CompanyName", "adminContact1CompanyName");
        sortMap.put("adminContact1Country", "adminContact1Country");
        sortMap.put("adminContact1FR", "adminContact1FR");
        sortMap.put("adminContact2NH", "adminContact2NH");
        sortMap.put("adminContact2Name", "adminContact2Name");
        sortMap.put("adminContact2Email", "adminContact2Email");
        sortMap.put("adminContact2CompanyName", "adminContact2CompanyName");
        sortMap.put("adminContact2Country", "adminContact2Country");
        sortMap.put("adminContact2FR", "adminContact2FR");
        sortMap.put("techContactNH", "techContactNH");
        sortMap.put("techContactName", "techContactName");
        sortMap.put("techContactEmail", "techContactEmail");
        sortMap.put("techContactCompanyName", "techContactCompanyName");
        sortMap.put("techContactCountry", "techContactCountry");
        sortMap.put("techContactFR", "techContactFR");
        sortMap.put("billingContactNH", "billingContactNH");
        sortMap.put("billingContactName", "billingContactName");
        sortMap.put("billingContactEmail", "billingContactEmail");
        sortMap.put("billingContactCompanyName", "billingContactCompanyName");
        sortMap.put("billingContactCountry", "billingContactCountry");
        sortMap.put("billingContactFR", "billingContactFR");
        sortMap.put("creatorNH", "creatorNH");
        sortMap.put("creatorName", "creatorName");
        sortMap.put("creatorEmail", "creatorEmail");
        sortMap.put("creatorCompanyName", "creatorCompanyName");
        sortMap.put("creatorCountry", "creatorCountry");
        sortMap.put("adminStatus", "adminStatus");
        sortMap.put("adminStatusChangeDate", "adminStatusChangeDate");
        sortMap.put("techStatus", "techStatus");
        sortMap.put("techStatusChangeDate", "techStatusChangeDate");
        sortMap.put("financialStatus", "financialStatus");
        sortMap.put("financialStatusChangeDate", "financialStatusChangeDate");
        sortMap.put("customerStatus", "customerStatus");
        sortMap.put("customerStatusChangeDate", "customerStatusChangeDate");
        sortMap.put("checkedOut", "checkedOut");
        sortMap.put("checkedOutToNH", "checkedOutToNH");
        sortMap.put("checkedOutToName", "checkedOutToName");
        sortMap.put("renewalDate", "renewalDate");
        sortMap.put("changeDate", "changeDate");
        sortMap.put("creationDate", "creationDate");
        sortMap.put("clikPaid", "clikPaid");
        sortMap.put("domainPeriod", "domainPeriod");
        sortMap.put("autorenewMode", "autorenewMode");
        sortMap.put("docsCNT", "docsCNT");
    }

    public InternalTicketIBatisDAO() {
        setGetQueryId("ticket.geTicketById");
        setLockQueryId("ticket.getLockedTicketById");
        setFindQueryId("ticket.findTicket");
        setCountFindQueryId("ticket.countTotalSearchResult");
        setDeleteQueryId("ticket.deleteTicket");

        setSortMapping(sortMap);
    }

    public List<String> findDomainNames(SearchCriteria<Ticket> criteria, int offset, int limit) {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("criteria", criteria);
        parameterMap.put("limit", limit);
        parameterMap.put("offset", offset);
        List<String> res = performQueryForList("ticket.findDomainNames", parameterMap);
        return res;
    }

    public long createTicket(InternalTicket internalTicket) {
        if (idGenerator == null) {
            throw new IllegalStateException("idGenerator not initialized");
        }
        long id = idGenerator.getNextId();
        internalTicket.setId(id);
        performInsert("ticket.insertTicket", Collections.singletonMap("internalTicket", internalTicket));
        return id;
    }

    public void updateUsingHistory(long changeId) {
        performUpdate("ticket.updateUsingHistory", changeId);
        performDelete("ticket.deleteNameserversByChangeId", changeId);
        performInsert("ticket.createNameserversUsingHistory", changeId);
    }

    public /*>>>@Nullable*/ TicketNotification getTicketNotification(long ticketId, int period) {
        Map<String, Object> param = new HashMap<>();
        param.put("ticketId", ticketId);
        param.put("notificationPeriod", period);
        return performQueryForObject("ticket.getTicketNotification", param);
    }

    public void createTicketNotification(TicketNotification notification) {
        performInsert("ticket.createTicketNotification", notification);
    }

    public void setIdGenerator(SequentialNumberGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

}
