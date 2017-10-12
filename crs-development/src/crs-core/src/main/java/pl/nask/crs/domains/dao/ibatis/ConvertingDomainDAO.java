package pl.nask.crs.domains.dao.ibatis;

import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.DomainNotification;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dao.ibatis.objects.InternalDomain;
import pl.nask.crs.domains.nameservers.NsReport;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.search.NsReportSearchCriteria;

public class ConvertingDomainDAO extends ConvertingGenericDAO<InternalDomain, Domain, String> implements DomainDAO {

    private InternalDomainIBatisDAO dao;

    public ConvertingDomainDAO(InternalDomainIBatisDAO internalDao,
            Converter<InternalDomain, Domain> internalConverter) {
        super(internalDao, internalConverter);
        Validator.assertNotNull(internalDao, "internal dao");
        this.dao = internalDao;
    }

    @Override
    public String getPreviousHolder(SearchCriteria<Domain> domain) {
        return dao.getPreviousHolder(domain);
    }

    @Override
    public List<String> findDomainNames(SearchCriteria<Domain> domainSearchCriteria, int offset, int limit) {
        return dao.findDomainNames(domainSearchCriteria, offset, limit);
    }

    @Override
    public List<Domain> findAll(DomainSearchCriteria domainSearchCriteria, List<SortCriterion> sortBy) {
        return getInternalConverter().to(dao.findAll(domainSearchCriteria, sortBy));
    }

    @Override
    public LimitedSearchResult<Domain> fullDomainFind(SearchCriteria<Domain> criteria, long offset, long limit,
            List<SortCriterion> sortBy) {
        LimitedSearchResult<InternalDomain> res = dao.fullDomainFind((SearchCriteria) criteria, offset, limit, sortBy);
        List<Domain> ret = getInternalConverter().to(res.getResults());
        return new LimitedSearchResult<>(criteria, res.getLimit(), res.getOffset(), ret, res.getTotalResults());
    }

    @Override
    public void zonePublish(String domainName) {
        dao.zonePublish(domainName);
    }

    @Override
    public void zoneUnpublish(String domainName) {
        dao.zoneUnpublish(domainName);
    }

    @Override
    public void zoneCommit() {
        dao.zoneCommit();
    }

    @Override
    public List<Integer> getDsmStates() {
        return dao.getDsmStates();
    }

    public void deleteById(String domainName) {
        dao.deleteDomain(domainName);
    }

    @Override
    public boolean exists(String domainName) {
        return dao.exists(domainName);
    }

    @Override
    public DomainNotification getDomainNotification(String domainName, int period) {
        return dao.getNotification(domainName, period);
    }

    @Override
    public void createNotification(DomainNotification notification) {
        dao.createNotification(notification);
    }

    @Override
    public List<DomainNotification> getAllNotifications() {
        return dao.getAllNotifications();
    }

    @Override
    public LimitedSearchResult<NsReport> getNsReport(String billingNH, NsReportSearchCriteria criteria, long offset,
            long limit, List<SortCriterion> sortBy) {
        return dao.getNsReports(billingNH, criteria, offset, limit, sortBy);
    }

    @Override
    public void createTransferRecord(String domainName, Date transferDate, String oldBillC, String newBillC) {
        dao.createTransferRecord(domainName, transferDate, oldBillC, newBillC);
    }
}
