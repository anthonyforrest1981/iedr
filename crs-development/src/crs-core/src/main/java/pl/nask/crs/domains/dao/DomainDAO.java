package pl.nask.crs.domains.dao;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.DeletedDomain;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainNotification;
import pl.nask.crs.domains.PlainDomain;
import pl.nask.crs.domains.nameservers.NsReport;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.search.NsReportSearchCriteria;
import pl.nask.crs.domains.search.PlainDomainSearchCriteria;

public interface DomainDAO extends GenericDAO<Domain, String> {

    /*>>>@Nullable*/String getPreviousHolder(SearchCriteria<Domain> domain);

    /**
     * Find all domain names matching the search criteria
     *
     * @param domainSearchCriteria
     * @param offset
     * @param limit
     * @return search result containing list of found domain names, never null.
     */
    List<String> findDomainNames(SearchCriteria<Domain> domainSearchCriteria, int offset, int limit);

    List<Domain> findAll(DomainSearchCriteria domainSearchCriteria, List<SortCriterion> sortBy);

    LimitedSearchResult<Domain> fullDomainFind(SearchCriteria<Domain> criteria, long offset, long limit,
            List<SortCriterion> sortBy);

    void zonePublish(String domainName);

    void zoneUnpublish(String domainName);

    void zoneCommit();

    List<Integer> getDsmStates();

    DomainNotification getDomainNotification(String domainName, int period);

    void createNotification(DomainNotification notification);

    List<DomainNotification> getAllNotifications();

    boolean exists(String domainName);

    LimitedSearchResult<NsReport> getNsReport(String billingNH, NsReportSearchCriteria criteria, long offset,
            long limit, List<SortCriterion> sortBy);

    void createTransferRecord(String domainName, Date transferDate, String oldBillC, String newBillC);

    void deleteById(String domainName);
}
