package pl.nask.crs.domains.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.*;
import pl.nask.crs.domains.dao.*;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.nameservers.NsReport;
import pl.nask.crs.domains.search.*;
import pl.nask.crs.domains.services.DomainSearchService;

public class DomainSearchServiceImpl implements DomainSearchService {

    private PlainDomainDAO plainDomainDAO;
    private DeletedDomainDAO deletedDomainDAO;
    private DomainDAO domainDAO;
    private ExtendedDomainDAO extendedDomainDAO;
    private DomainCountForContactDAO domainCountForContactDAO;

    public DomainSearchServiceImpl(PlainDomainDAO plainDomainDAO, DeletedDomainDAO deletedDomainDAO,
            DomainDAO domainDAO, ExtendedDomainDAO extendedDomainDAO,
            DomainCountForContactDAO domainCountForContactDAO) {
        this.plainDomainDAO = plainDomainDAO;
        this.deletedDomainDAO = deletedDomainDAO;
        this.domainDAO = domainDAO;
        this.extendedDomainDAO = extendedDomainDAO;
        this.domainCountForContactDAO = domainCountForContactDAO;
    }

    public Domain getDomain(String name) throws DomainNotFoundException {
        Domain ret = domainDAO.get(name);
        if (ret == null) {
            throw new DomainNotFoundException(name);
        }
        return ret;
    }

    public boolean exists(String name) {
        Domain ret = domainDAO.get(name);
        return ret != null;
    }

    public boolean exists(DomainSearchCriteria criteria) {
        return domainDAO.exists(criteria);
    }

    @Override
    public LimitedSearchResult<Domain> find(DomainSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> orderBy) {
        return domainDAO.find(criteria, offset, limit, orderBy);
    }

    @Override
    public LimitedSearchResult<PlainDomain> findPlain(PlainDomainSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> orderBy) {
        return plainDomainDAO.find(criteria, offset, limit, orderBy);
    }

    @Override
    public List<Domain> findAll(DomainSearchCriteria criteria, List<SortCriterion> sortBy) {
        return domainDAO.findAll(criteria, sortBy);
    }

    public LimitedSearchResult<Domain> fullFind(DomainSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> orderBy) {
        return domainDAO.fullDomainFind(criteria, offset, limit, orderBy);
    }

    @Override
    public LimitedSearchResult<PlainDomain> findTransferredInDomains(PlainDomainSearchCriteria searchCriteria,
            long offset, long limit, List<SortCriterion> sortBy) {
        return plainDomainDAO.findTransferredInDomains(searchCriteria, offset, limit, sortBy);
    }

    @Override
    public LimitedSearchResult<PlainDomain> findTransferredAwayDomains(PlainDomainSearchCriteria searchCriteria,
            long offset, long limit, List<SortCriterion> sortBy) {
        return plainDomainDAO.findTransferredAwayDomains(searchCriteria, offset, limit, sortBy);
    }

    @Override
    public LimitedSearchResult<DeletedDomain> findDeletedDomains(DeletedDomainSearchCriteria criteria, long offset,
            long limit, List<SortCriterion> orderBy) {
        if (criteria == null) {
            criteria = new DeletedDomainSearchCriteria();
        }
        if (criteria.getDeletionFrom() == null && criteria.getDeletionTo() == null) {
            criteria.setDeletionFrom(DateUtils.addYears(new Date(), -1));
        }

        return deletedDomainDAO.find(criteria, offset, limit, orderBy);
    }

    @Override
    public List<String> findDomainNames(DomainSearchCriteria criteria, int offset, int limit) {
        return domainDAO.findDomainNames(criteria, offset, limit);
    }

    @Override
    public LimitedSearchResult<ExtendedDomain> findExtended(ExtendedDomainSearchCriteria criteria, long offset,
            long limit, List<SortCriterion> sortBy) {
        return extendedDomainDAO.find(criteria, offset, limit, sortBy);
    }

    @Override
    public List<ExtendedDomain> findExtended(ExtendedDomainSearchCriteria criteria, List<SortCriterion> sortBy) {
        return extendedDomainDAO.find(criteria, sortBy).getResults();
    }

    @Override
    public LimitedSearchResult<Domain> findDomainAutorenewals(DomainSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy) {
        List<RenewalMode> correctModes = Arrays.asList(RenewalMode.RenewOnce, RenewalMode.Autorenew);
        if (criteria.getRenewalModes() == null) {
            criteria.setRenewalModes(correctModes);
        } else {
            List<RenewalMode> modes = new ArrayList<RenewalMode>(criteria.getRenewalModes());
            modes.retainAll(correctModes);
            if (modes.isEmpty()) {
                modes = correctModes;
            }
            criteria.setRenewalModes(modes);
        }
        return domainDAO.fullDomainFind(criteria, offset, limit, sortBy);
    }

    @Override
    public LimitedSearchResult<NsReport> getNsReports(String billingNH, NsReportSearchCriteria criteria, long offset,
            long limit, List<SortCriterion> sortBy) {
        return domainDAO.getNsReport(billingNH, criteria, offset, limit, sortBy);
    }

    @Override
    public List<DomainCountForContact> findDomainCountForContact(DomainCountForContactSearchCriteria criteria) {
        return domainCountForContactDAO.findDomainCountForContact(criteria);
    }

    @Override
    public List<String> findDomainsForHolder(String holder, int limit, String except) {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setExactDomainHolder(holder);
        List<String> domains = domainDAO.findDomainNames(criteria, 0, limit);
        if (except != null) {
            domains.remove(except);
        }
        return domains;
    }
}
