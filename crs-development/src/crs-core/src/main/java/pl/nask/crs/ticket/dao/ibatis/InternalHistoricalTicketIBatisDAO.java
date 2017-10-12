package pl.nask.crs.ticket.dao.ibatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalHistoricalTicket;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalTicketNameserver;

public class InternalHistoricalTicketIBatisDAO extends GenericIBatisDAO<InternalHistoricalTicket, Long> {

    private static final Map<String, String> sortMap = new HashMap<>();
    {
        sortMap.put("id", "id");
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
        sortMap.put("histChangeDate", "histChangeDate");
        sortMap.put("changeId", "changeId");
        sortMap.put("changedByNicHandle", "changedByNicHandle");
    }

    public InternalHistoricalTicketIBatisDAO() {
        setCreateQueryId("historical-ticket.createHistoricalTicket");
        setFindQueryId("historical-ticket.findTicketHistory");
        setLimitedFindQueryId("historical-ticket.findTicketHistorySimple");
        setCountFindQueryId("historical-ticket.countTicketHistory");

        setSortMapping(sortMap);
    }

    @Override
    public void create(InternalHistoricalTicket internalHistoricalTicket) {
        super.create(internalHistoricalTicket);
        if (!internalHistoricalTicket.getNameservers().isEmpty()) {
            performInsert("historical-ticket.createHistoricalNameservers",
                    getNameserversParams(internalHistoricalTicket));
        }
    }

    private Map<String, Object> getNameserversParams(InternalHistoricalTicket internalHistoricalTicket) {
        List<Map<String, Object>> nsParamsList = new ArrayList<>();
        List<InternalTicketNameserver> nameservers = internalHistoricalTicket.getNameservers();
        for (int i = 0; i < nameservers.size(); i++) {
            Map<String, Object> nsParams = new HashMap<>();
            nsParams.put("nameserver", nameservers.get(i));
            nsParams.put("order", i);
            nsParamsList.add(nsParams);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("ticket", internalHistoricalTicket);
        params.put("nameservers", nsParamsList);
        return params;
    }
}
