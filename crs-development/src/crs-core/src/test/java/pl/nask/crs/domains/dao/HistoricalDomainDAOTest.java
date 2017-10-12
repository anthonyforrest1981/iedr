package pl.nask.crs.domains.dao;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.UncategorizedSQLException;
import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainsHelper;
import pl.nask.crs.domains.search.HistoricalDomainSearchCriteria;
import pl.nask.crs.history.HistoricalObject;

import static org.apache.commons.lang.time.DateUtils.truncate;

public class HistoricalDomainDAOTest extends AbstractContextAwareTest {

    @Resource
    HistoricalDomainDAO historicalDomainDAO;
    @Resource
    DomainDAO domainDAO;

    @Test
    public void testGetDomainHistoryForDomain() {
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setDomainName("castlebargolfclub.ie");
        SearchResult<HistoricalObject<Domain>> result = historicalDomainDAO.find(criteria);
        AssertJUnit.assertEquals(24, result.getResults().size());
    }

    @Test
    public void testCreate() {
        final String domainName = "castlebargolfclub.ie";
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setDomainName(domainName);
        SearchResult<HistoricalObject<Domain>> result = historicalDomainDAO.find(criteria);
        AssertJUnit.assertEquals(24, result.getResults().size());

        Domain domain = domainDAO.get(domainName);
        long changeId = historicalDomainDAO.create(domain, new Date(), "aa");
        result = historicalDomainDAO.find(criteria);
        AssertJUnit.assertEquals(25, result.getResults().size());
        HistoricalObject<Domain> histDomain = result.getResults().get(24);
        AssertJUnit.assertEquals(changeId, histDomain.getChangeId());
        DomainsHelper.compareDomains(histDomain.getObject(), domain);
    }

    @Test
    public void testLimitedFindByDomainName() {
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setDomainName("theweb");
        criteria.setDomainHolder("");
        criteria.setNicHandle("");
        LimitedSearchResult<HistoricalObject<Domain>> found = historicalDomainDAO.find(criteria, 0, 10);
        AssertJUnit.assertEquals(10, found.getResults().size());
        AssertJUnit.assertEquals(17, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByAccount() {
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setAccountId(122L);
        LimitedSearchResult<HistoricalObject<Domain>> found = historicalDomainDAO.find(criteria, 0, 10);
        AssertJUnit.assertEquals(10, found.getResults().size());
        AssertJUnit.assertEquals(31, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByDomainHolder() {
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setDomainHolder("astleba");
        LimitedSearchResult<HistoricalObject<Domain>> found = historicalDomainDAO.find(criteria, 0, 10);
        AssertJUnit.assertEquals(10, found.getResults().size());
        AssertJUnit.assertEquals(28, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByNicHandle() {
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setNicHandle("AAH014-IEDR");
        LimitedSearchResult<HistoricalObject<Domain>> found = historicalDomainDAO.find(criteria, 0, 1);
        AssertJUnit.assertEquals(1, found.getResults().size());
        AssertJUnit.assertEquals(34, found.getTotalResults());
    }

    @Test
    public void testCount() {
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setNicHandle("AAH014-IEDR");
        AssertJUnit.assertEquals(34, historicalDomainDAO.count(criteria));
    }

    @Test
    public void testExists() {
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setNicHandle("AAH014-IEDR");
        AssertJUnit.assertTrue(historicalDomainDAO.exists(criteria));
    }

    @Test
    public void testNotExists() {
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setNicHandle("AHM415-IEDR");
        AssertJUnit.assertFalse(historicalDomainDAO.exists(criteria));
    }

    @Test
    public void testLimitedFindUtf8Unnormalized() {
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setDomainName("unnormalized-name");
        LimitedSearchResult<HistoricalObject<Domain>> result = historicalDomainDAO.find(criteria, 0, 10);
        AssertJUnit.assertEquals(2, result.getTotalResults());
        AssertJUnit.assertEquals(result.getResults().get(0).getObject().getName(), "unnormalized-name-\u00f5\u1ebb.ie");
        criteria.setDomainName("unnormalized-details.ie");
        result = historicalDomainDAO.find(criteria, 0, 10);
        AssertJUnit.assertEquals(2, result.getTotalResults());
        HistoricalObject<Domain> historicalDomain = result.getResults().get(1);
        Assert.assertEquals(historicalDomain.getChangedBy(), "XY\u01788-IEDR");
        Domain domain = historicalDomain.getObject();
        Assert.assertEquals(domain.getHolder(), "Unnormalized \u0124\u1ecdlder");
        Assert.assertEquals(domain.getHolderClass().getName(), "Unnormalized Cl\u00e3ss");
        Assert.assertEquals(domain.getHolderCategory().getName(), "Unnormalized Cate\u01f5\u014dry");
        Assert.assertEquals(domain.getRemark(), "Ȳģṃẖǩ");
    }

    @Test
    public void testLimitedFindUtf8Normalized() {
        String domainName = "normalized-correct-\u00f5\u1ebb.ie";
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setDomainName(domainName);
        LimitedSearchResult<HistoricalObject<Domain>> result = historicalDomainDAO.find(criteria, 0, 10);
        Assert.assertEquals(result.getTotalResults(), 2);
        criteria = new HistoricalDomainSearchCriteria();
        criteria.setDomainHolder("Previous normalized \u0124\u1ecdlder");
        result = historicalDomainDAO.find(criteria, 0, 10);
        Assert.assertEquals(result.getTotalResults(), 1);
        Assert.assertEquals(result.getResults().get(0).getObject().getName(), domainName);
        criteria = new HistoricalDomainSearchCriteria();
        criteria.setHolderClassId(98L);
        result = historicalDomainDAO.find(criteria, 0, 10);
        Assert.assertEquals(result.getTotalResults(), 2);
        Assert.assertEquals(result.getResults().get(0).getObject().getName(), domainName);
        criteria = new HistoricalDomainSearchCriteria();
        criteria.setHolderCategoryId(98L);
        result = historicalDomainDAO.find(criteria, 0, 10);
        Assert.assertEquals(result.getTotalResults(), 2);
        Assert.assertEquals(result.getResults().get(0).getObject().getName(), domainName);
        criteria = new HistoricalDomainSearchCriteria();
        criteria.setNicHandle("XX\u01787-IEDR");
        result = historicalDomainDAO.find(criteria, 0, 10);
        Assert.assertEquals(result.getTotalResults(), 8);
        Assert.assertEquals(result.getResults().get(0).getObject().getName(), domainName);
    }

    @Test
    public void testLockingDates() {
        final String domainName = "castlebargolfclub.ie";
        Domain d = domainDAO.get(domainName);
        Date lockingDate = new Date();
        d.setLockingDate(lockingDate);
        Date lockingRenewalDate = DateUtils.addMonths(lockingDate, 10);
        d.setLockingRenewalDate(lockingRenewalDate);
        long changeId = historicalDomainDAO.create(d, new Date(), "aa");
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setDomainName(domainName);
        SearchResult<HistoricalObject<Domain>> result = historicalDomainDAO.find(criteria);
        Assert.assertEquals(result.getResults().size(), 25);
        HistoricalObject<Domain> lastHistObj = result.getResults().get(24);
        Assert.assertEquals(lastHistObj.getChangeId(), changeId);
        Assert.assertEquals(lastHistObj.getObject().getLockingDate(), truncate(lockingDate, Calendar.SECOND));
        Assert.assertEquals(lastHistObj.getObject().getLockingRenewalDate(), truncate(lockingRenewalDate, Calendar.DATE));
    }

    @Test
    public void testLooseUtf8Validation() {
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria("badutf8");
        SearchResult<HistoricalObject<Domain>> histDomains = historicalDomainDAO.find(criteria);
        Assert.assertEquals(histDomains.getResults().size(), 5);
        HistoricalObject<Domain> histDomain = histDomains.getResults().get(0);
        Assert.assertEquals(histDomain.getObject().getName(), "badutf8\01.ie");
    }

    private String getForbiddenString() {
        // an exaple of 4-byte UTF-8
        String str = new String(Character.toChars(Integer.parseInt("10348", 16)));
        return str;
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testUpdateDomainWithForbiddenName() {
        Domain domain = domainDAO.get("unnormalized-details.ie");
        domain.setName(getForbiddenString());
        historicalDomainDAO.create(domain, new Date(), TestOpInfo.DEFAULT.getActorName());
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testUpdateDomainWithForbiddenHolder() {
        Domain domain = domainDAO.get("unnormalized-details.ie");
        domain.setHolder(getForbiddenString());
        historicalDomainDAO.create(domain, new Date(), TestOpInfo.DEFAULT.getActorName());
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testUpdateDomainWithForbiddenRemark() {
        Domain domain = domainDAO.get("unnormalized-details.ie");
        domain.setRemark(getForbiddenString());
        historicalDomainDAO.create(domain, new Date(), TestOpInfo.DEFAULT.getActorName());
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testUpdateDomainWithForbiddenAuthcode() {
        Domain domain = domainDAO.get("unnormalized-details.ie");
        domain.setAuthCode(getForbiddenString());
        historicalDomainDAO.create(domain, new Date(), TestOpInfo.DEFAULT.getActorName());
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testUpdateDomainWithForbiddenDnsName() {
        Domain domain = domainDAO.get("unnormalized-details.ie");
        domain.getNameservers().get(0).setName(getForbiddenString());
        historicalDomainDAO.create(domain, new Date(), TestOpInfo.DEFAULT.getActorName());
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testUpdateDomainWithForbiddenDnsIpv4() {
        Domain domain = domainDAO.get("unnormalized-details.ie");
        domain.getNameservers().get(0).setIpv4Address(getForbiddenString());
        historicalDomainDAO.create(domain, new Date(), TestOpInfo.DEFAULT.getActorName());
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testUpdateDomainWithForbiddenDnsIpv6() {
        Domain domain = domainDAO.get("unnormalized-details.ie");
        domain.getNameservers().get(0).setIpv6Address(getForbiddenString());
        historicalDomainDAO.create(domain, new Date(), TestOpInfo.DEFAULT.getActorName());
    }

}
