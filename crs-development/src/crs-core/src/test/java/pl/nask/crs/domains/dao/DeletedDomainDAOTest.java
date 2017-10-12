package pl.nask.crs.domains.dao;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.DeletedDomain;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.search.DeletedDomainSearchCriteria;
import pl.nask.crs.domains.search.HistoricalDomainSearchCriteria;
import pl.nask.crs.history.HistoricalObject;

public class DeletedDomainDAOTest extends AbstractContextAwareTest {

    @Autowired
    DeletedDomainDAO deletedDomainDAO;

    @Autowired
    HistoricalDomainDAO histDomainDAO;

    @Test
    public void findAllTest() {
        DeletedDomainSearchCriteria criteria = new DeletedDomainSearchCriteria();
        LimitedSearchResult<DeletedDomain> result = deletedDomainDAO.find(criteria, 0, 100, null);
        Assert.assertEquals(result.getTotalResults(), 10);
        Assert.assertEquals(result.getResults().size(), 10);
    }

    @Test
    public void findByDateFromTest() {
        DeletedDomainSearchCriteria criteria = new DeletedDomainSearchCriteria();
        criteria.setDeletionFrom(DateUtils.addYears(new Date(), -1));
        LimitedSearchResult<DeletedDomain> result = deletedDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(result.getTotalResults(), 8);
        Assert.assertEquals(result.getResults().size(), 8);
    }

    @Test
    public void findByDateToTest() {
        DeletedDomainSearchCriteria criteria = new DeletedDomainSearchCriteria();
        criteria.setDeletionTo(DateUtils.addYears(new Date(), -1));
        LimitedSearchResult<DeletedDomain> result = deletedDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(result.getTotalResults(), 1);
        Assert.assertEquals(result.getResults().size(), 1);
    }

    @Test
    public void findDomainWithTwoContactsTest() {
        DeletedDomainSearchCriteria criteria = new DeletedDomainSearchCriteria();
        criteria.setDomainName("deleteddomain2.ie");
        LimitedSearchResult<DeletedDomain> result = deletedDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(result.getTotalResults(), 1);
        Assert.assertEquals(result.getResults().size(), 1);
        HistoricalDomainSearchCriteria histCriteria = new HistoricalDomainSearchCriteria();
        histCriteria.setDomainName("deleteddomain2.ie");
        SearchResult<HistoricalObject<Domain>> histResult = histDomainDAO.find(histCriteria);
        Assert.assertEquals(1, histResult.getResults().size());
        Assert.assertEquals(2, histResult.getResults().get(0).getObject().getAdminContacts().size());
    }

    @Test
    public void findByAccountIdTest() {
        DeletedDomainSearchCriteria criteria = new DeletedDomainSearchCriteria();
        criteria.setAccountId(194L);
        LimitedSearchResult<DeletedDomain> result = deletedDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(1, result.getTotalResults());
        Assert.assertEquals(1, result.getResults().size());
        Assert.assertEquals(result.getResults().get(0).getCounty().getName(), "Co. Antrim");
        Assert.assertEquals(result.getResults().get(0).getCountry().getName(), "Northern Ireland");

        criteria = new DeletedDomainSearchCriteria();
        criteria.setAccountId(666L);
        result = deletedDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(3, result.getTotalResults());
        Assert.assertEquals(3, result.getResults().size());
    }

    @Test
    public void findByRenewalDateTest() {
        DeletedDomainSearchCriteria criteria = new DeletedDomainSearchCriteria();
        criteria.setRenewalFrom(new Date(110, 0, 1));
        LimitedSearchResult<DeletedDomain> result = deletedDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(6, result.getTotalResults());
        Assert.assertEquals(6, result.getResults().size());
    }

    @Test
    public void findByDateRangeTest() {
        DeletedDomainSearchCriteria criteria = new DeletedDomainSearchCriteria();
        criteria.setDeletionFrom(new Date(110, 0, 1));
        criteria.setDeletionTo(new Date(120, 0, 1));
        LimitedSearchResult<DeletedDomain> result = deletedDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(9, result.getTotalResults());
        Assert.assertEquals(9, result.getResults().size());
    }

    @Test
    public void testLooseUtf8ValidationOfDeletedDomains() {
        DeletedDomainSearchCriteria criteria = new DeletedDomainSearchCriteria();
        criteria.setDomainName("badutf8\01");
        LimitedSearchResult<DeletedDomain> result = deletedDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(result.getTotalResults(), 1);
        Assert.assertEquals(result.getResults().size(), 1);
    }

    @Test
    public void testFindDeletedUnnormalizedDomain() {
        DeletedDomainSearchCriteria criteria = new DeletedDomainSearchCriteria();
        criteria.setDomainName("unnormalized-deleted1");
        SearchResult<DeletedDomain> results = deletedDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(results.getResults().size(), 1);
        DeletedDomain domain = results.getResults().get(0);
        Assert.assertEquals(domain.getHolder(), "Unnormalized \u0124\u1ecdlder");
        Assert.assertEquals(domain.getHolderClass().getName(), "Unnormalized Cl\u00e3ss");
        Assert.assertEquals(domain.getHolderCategory().getName(), "Unnormalized Cate\u01f5\u014dry");
        Assert.assertEquals(domain.getBillingNH(), "XY\u01788-IEDR");
        criteria.setDomainName("unnormalized-deleted2");
        results = deletedDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(results.getResults().size(), 1);
        domain = results.getResults().get(0);
        Assert.assertEquals(domain.getBillingName(), "\u00cc\u1e43\u1e03\u00e1\u0205l");
        Assert.assertEquals(domain.getCounty().getName(), "Unnormalized co\u00fan\u0163y");
        Assert.assertEquals(domain.getCountry().getName(), "Unnormalized c\u00f4u\u1e45try");
    }

    @Test
    public void testFindUtf8DeletedDomainWithSearchCriteria() {
        String domainName = "normalized-deleted-\u00f5\u1ebb.ie";
        DeletedDomainSearchCriteria criteria = new DeletedDomainSearchCriteria();
        criteria.setDomainName(domainName);
        SearchResult<DeletedDomain> result = deletedDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(result.getResults().size(), 1);
        criteria = new DeletedDomainSearchCriteria();
        criteria.setBillingNH("XX\u1e8c7-IEDR");
        result = deletedDomainDAO.find(criteria, 0, 10, null);
        Assert.assertEquals(result.getResults().size(), 2);
        Assert.assertEquals(result.getResults().get(0).getName(), domainName);
        Assert.assertEquals(result.getResults().get(1).getName(), "badutf8\01.ie");
    }
}
