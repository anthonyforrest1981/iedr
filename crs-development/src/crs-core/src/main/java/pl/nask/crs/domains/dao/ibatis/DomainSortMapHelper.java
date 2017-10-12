package pl.nask.crs.domains.dao.ibatis;

import java.util.HashMap;
import java.util.Map;

public class DomainSortMapHelper {

    public static Map<String, String> getPlainDomainSortMap() {
        Map<String, String> plainSortMap = new HashMap<>();
        plainSortMap.put("name", "domainName");
        plainSortMap.put("holder", "domainHolder");
        plainSortMap.put("registrationDate", "registrationDate");
        plainSortMap.put("renewalDate", "renewalDate");
        plainSortMap.put("transferDate", "transferDate");
        plainSortMap.put("suspensionDate", "suspensionDate");
        plainSortMap.put("deletionDate", "deletionDate");
        plainSortMap.put("dsmStateId", "dsmStateId");
        plainSortMap.put("dsmHolderType", "dsmHolderType");
        plainSortMap.put("dsmRenewalMode", "dsmRenewalMode");
        plainSortMap.put("dsmLocked", "dsmLocked");
        plainSortMap.put("dsmCustomerType", "dsmCustomerType");
        plainSortMap.put("dsmNrpStatus", "dsmNrpStatus");
        plainSortMap.put("dsmPublished", "dsmPublished");
        plainSortMap.put("zonePublished", "zonePublished");
        plainSortMap.put("lockingDate", "lockingDate");
        plainSortMap.put("lockingRenewalDate", "lockingRenewalDate");
        plainSortMap.put("holderClass", "className");
        plainSortMap.put("holderCategory", "categoryName");
        return plainSortMap;
    }

    public static Map<String, String> getDomainSortMap() {
        Map<String, String> domainSortMap = new HashMap<>();
        domainSortMap.putAll(getPlainDomainSortMap());
        domainSortMap.put("resellerAccountId", "resellerAccountId");
        domainSortMap.put("resellerAccountName", "resellerAccountName");
        domainSortMap.put("changeDate", "changeDate");
        domainSortMap.put("transferDate", "transferDate");
        domainSortMap.put("billingNH", "C2.Contact_NH");
        domainSortMap.put("locked", "DSM.Locked");
        return domainSortMap;
    }

    public static Map<String, String> getDeletedDomainSortMap() {
        Map<String, String> deletedDomainSortMap = new HashMap<>();
        deletedDomainSortMap.putAll(getPlainDomainSortMap());
        deletedDomainSortMap.put("billingName", "billingName");
        deletedDomainSortMap.put("billingNic", "billingNic");
        deletedDomainSortMap.put("countryName", "countryName");
        deletedDomainSortMap.put("countyName", "countyName");
        return deletedDomainSortMap;
    }

    public static Map<String, String> getDnsDomainSortMap() {
        Map<String, String> dnsDomainSortMap = new HashMap<>();
        dnsDomainSortMap.put("domainName", "domainName");
        dnsDomainSortMap.put("holderName", "holderName");
        dnsDomainSortMap.put("registrationDate", "registrationDate");
        dnsDomainSortMap.put("renewalDate", "renewalDate");
        dnsDomainSortMap.put("dnsName", "dnsName");
        dnsDomainSortMap.put("dnsIpv4Address", "dnsIpv4Address");
        dnsDomainSortMap.put("dnsIpv6Address", "dnsIpv6Address");
        dnsDomainSortMap.put("dnsOrder", "dnsOrder");
        return dnsDomainSortMap;
    }

}
