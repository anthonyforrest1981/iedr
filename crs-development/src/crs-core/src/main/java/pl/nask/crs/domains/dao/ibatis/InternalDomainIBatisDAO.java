package pl.nask.crs.domains.dao.ibatis;

import java.util.*;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainNotification;
import pl.nask.crs.domains.PlainDomain;
import pl.nask.crs.domains.dao.ibatis.objects.InternalDomain;
import pl.nask.crs.domains.nameservers.NsReport;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.search.NsReportSearchCriteria;

import static pl.nask.crs.domains.dao.ibatis.DomainSortMapHelper.getDnsDomainSortMap;
import static pl.nask.crs.domains.dao.ibatis.DomainSortMapHelper.getDomainSortMap;

public class InternalDomainIBatisDAO extends GenericIBatisDAO<InternalDomain, String> {

    public InternalDomainIBatisDAO() {
        setGetQueryId("domain.getDomainByName");
        setCreateQueryId("domain.createDomain");
        setFindQueryId("domain.findDomain");
        setLimitedFindQueryId("domain.findDomain");
        setCountFindQueryId("domain.countFindDomain");
        setLockQueryId("domain.getLockedDomainByName");
        setSortMapping(getDomainSortMap());
    }

    public void updateUsingHistory(long changeId) {
        performUpdate("domain.updateUsingHistory", changeId);
        performDelete("domain.deleteNameserversByChangeId", changeId);
        performInsert("domain.createNameserversUsingHistory", changeId);
        performDelete("domain.deleteContactsByChangeId", changeId);
        performInsert("domain.createContactsUsingHistory", changeId);
    }

    public void createTransferRecord(String domainName, Date transferDate, String oldBillC, String newBillC) {
        Map<String, Object> params = new HashMap<>();
        params.put("domainName", domainName);
        params.put("transferDate", transferDate);
        params.put("oldBillC", oldBillC);
        params.put("newBillC", newBillC);
        performInsert("domain.createTransferHistRecord", params);
    }

    public void deleteDomain(String domainName) {
        performDelete("domain.deleteAssociatedDNS", domainName);
        performDelete("domain.deleteAssociatedContact", domainName);
        performDelete("domain.deleteDomain", domainName);
    }

    public String getPreviousHolder(SearchCriteria<Domain> domain) {
        return (String) performQueryForObject("domain.getPreviousHolder", domain);
    }

    public List<String> findDomainNames(SearchCriteria<Domain> criteria, int offset, int limit) {
        // ugly workaround to make sure, that the criteria will not contain null values in the collections
        removeNullCriteria(criteria);
        FindParameters params = new FindParameters(criteria).setLimit(offset, limit);
        List<String> res = performQueryForList("domain.findDomainNames", params);
        return res;
    }

    public LimitedSearchResult<InternalDomain> fullDomainFind(SearchCriteria<InternalDomain> criteria, long offset,
            long limit, List<SortCriterion> sortBy) {
        return performFind("domain.findFullDomain", "domain.countFullFindDomain", criteria, offset, limit, sortBy);
    }

    public void zoneUnpublish(String domainName) {
        performDelete("domain.domainUnpublished", domainName);
    }

    public void zonePublish(String domainName) {
        performInsert("domain.domainPublished", domainName);
    }

    public void zoneCommit() {
        performUpdate("domain.zoneCommit", null);
    }

    List<InternalDomain> findAll(DomainSearchCriteria criteria, List<SortCriterion> sortBy) {
        removeNullCriteria(criteria);
        FindParameters findParameters = new FindParameters(criteria).setOrderBy(sortBy);
        return performQueryForList("domain.findFullDomain", findParameters);
    }

    public List<Integer> getDsmStates() {
        return performQueryForList("domain.getDsmStates");
    }

    public DomainNotification getNotification(String domainName, int period) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("domainName", domainName);
        param.put("notificationPeriod", period);
        return performQueryForObject("domain.getNotification", param);
    }

    public void createNotification(DomainNotification notification) {
        performInsert("domain.createNotification", notification);
    }

    public List<DomainNotification> getAllNotifications() {
        return performQueryForList("domain.getAllNotifications");
    }

    public boolean exists(String domainName) {
        return performQueryForObject("domain.exists", domainName) != null;
    }

    public LimitedSearchResult<NsReport> getNsReports(String billingNH, NsReportSearchCriteria criteria, long offset,
            long limit, List<SortCriterion> sortBy) {
        FindParameters parameters = new FindParameters(criteria).setLimit(offset, limit).setOrderBy(sortBy,
                getDnsDomainSortMap());
        parameters.getMap().put("billingNH", billingNH);
        Integer total = performQueryForObject("domain.getNsReportsCount", parameters);
        List<NsReport> reports = null;
        if (total > 0) {
            reports = performQueryForList("domain.getNsReports", parameters);
        } else {
            reports = Collections.emptyList();
        }
        return new LimitedSearchResult<>(criteria, limit, offset, reports, total);
    }

    private void removeNullCriteria(SearchCriteria<? extends PlainDomain> criteria) {
        if (criteria instanceof DomainSearchCriteria) {
            ((DomainSearchCriteria) criteria).removeNullValues();
        }
    }

}
