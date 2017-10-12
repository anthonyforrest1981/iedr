package pl.nask.crs.domains.dao.ibatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.contacts.dao.ibatis.objects.InternalContact;
import pl.nask.crs.domains.dao.ibatis.objects.InternalHistoricalDomain;
import pl.nask.crs.domains.nameservers.Nameserver;

public class InternalHistoricalDomainIbatisDAO extends GenericIBatisDAO<InternalHistoricalDomain, Long> {

    private static Map<String, String> sortMap = new HashMap<>();
    {
        sortMap.put("changeId", "changeId");
        sortMap.put("name", "name");
        sortMap.put("holder", "holder");
        sortMap.put("className", "className");
        sortMap.put("categoryName", "categoryName");
        sortMap.put("resellerAccountId", "resellerAccountId");
        sortMap.put("resellerAccountName", "resellerAccountName");
        sortMap.put("registrationDate", "registrationDate");
        sortMap.put("renewalDate", "renewalDate");
        sortMap.put("suspensionDate", "suspensionDate");
        sortMap.put("deletionDate", "deletionDate");
        sortMap.put("transferDate", "transferDate");
        sortMap.put("changeDate", "changeDate");
        sortMap.put("authCodeExpirationDate", "authCodeExpirationDate");
        sortMap.put("changedByNicHandle", "changedByNicHandle");
        sortMap.put("histChangeDate", "histChangeDate");
        sortMap.put("dsmStateId", "dsmStateId");
        sortMap.put("dsmHolderType", "dsmHolderType");
        sortMap.put("dsmRenewalMode", "dsmRenewalMode");
        sortMap.put("dsmLocked", "dsmLocked");
        sortMap.put("dsmCustomerType", "dsmCustomerType");
        sortMap.put("dsmNrpStatus", "dsmNrpStatus");
        sortMap.put("dsmPublished", "dsmPublished");
        sortMap.put("lockingDate", "lockingDate");
        sortMap.put("lockingRenewalDate", "lockingRenewalDate");
    }

    public InternalHistoricalDomainIbatisDAO() {
        setFindQueryId("historical-domain.findDomainHistory");
        setCountFindQueryId("historical-domain.countDomainHistory");

        setSortMapping(sortMap);
    }

    @Override
    public void create(InternalHistoricalDomain internalHistoricalDomain) {
        performInsert("historical-domain.createHistoricalDomain", internalHistoricalDomain);
        if (!internalHistoricalDomain.getNameservers().isEmpty()) {
            performInsert("historical-domain.createHistoricalNameservers",
                    getNameserversParams(internalHistoricalDomain));
        }
        for (InternalContact internalContact : internalHistoricalDomain.getContacts()) {
            performInsert("historical-domain.createHistoricalContact",
                    getContactParams(internalHistoricalDomain, internalContact));
        }
    }

    private Map<String, Object> getNameserversParams(InternalHistoricalDomain internalHistoricalDomain) {
        List<Map<String, Object>> nsParamsList = new ArrayList<>();
        List<Nameserver> nameservers = internalHistoricalDomain.getNameservers();
        for (int i = 0; i < nameservers.size(); i++) {
            Map<String, Object> nsParams = new HashMap<>();
            nsParams.put("nameserver", nameservers.get(i));
            nsParams.put("order", i);
            nsParamsList.add(nsParams);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("domain", internalHistoricalDomain);
        params.put("nameservers", nsParamsList);
        return params;
    }

    private Map<String, Object> getContactParams(InternalHistoricalDomain internalHistoricalDomain,
            InternalContact internalContact) {
        Map<String, Object> params = new HashMap<>();
        params.put("domain", internalHistoricalDomain);
        params.put("contact", internalContact);
        return params;
    }

}
