package pl.nask.crs.domains.services;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.httpclient.util.DateParseException;
import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.domains.*;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dao.HistoricalDomainDAO;
import pl.nask.crs.domains.search.*;

import static org.testng.AssertJUnit.assertEquals;

public class DomainSearchServiceTest extends AbstractContextAwareTest {

    @Resource
    DomainSearchService domainSearchService;
    @Resource
    DomainDAO domainDao;
    @Resource
    HistoricalDomainDAO histDomainDao;

    @Test
    public void testFindForNH() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();

        criteria.setNicHandle("AAI538-IEDR");
        LimitedSearchResult<Domain> result = domainSearchService.find(criteria, 0, 10, null);
        assertEquals(result.getResults().size(), 1);
    }

    @Test
    public void testFindActive() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setActive(true);

        LimitedSearchResult<Domain> result = domainSearchService.find(criteria, 0, 10, null);
        assertEquals(10, result.getResults().size());
        assertEquals(72, result.getTotalResults());
    }

    @Test
    public void testFindDomainAutorenewals() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        LimitedSearchResult<Domain> result = domainSearchService.findDomainAutorenewals(criteria, 0, 10, null);
        Assert.assertEquals(6, result.getTotalResults());
        Assert.assertEquals(6, result.getResults().size());
        for (int i = 0; i < result.getResults().size(); i++) {
            RenewalMode renewalMode = result.getResults().get(i).getDsmState().getRenewalMode();
            Assert.assertTrue(renewalMode == RenewalMode.Autorenew || renewalMode == RenewalMode.RenewOnce);
        }

        criteria.setRenewalModes(Arrays.asList(RenewalMode.RenewOnce));
        result = domainSearchService.findDomainAutorenewals(criteria, 0, 10, null);
        Assert.assertEquals(1, result.getTotalResults());
        Assert.assertEquals(1, result.getResults().size());
        for (int i = 0; i < result.getResults().size(); i++) {
            RenewalMode renewalMode = result.getResults().get(i).getDsmState().getRenewalMode();
            Assert.assertTrue(renewalMode == RenewalMode.RenewOnce);
        }

        // "No autorenew" option omitted in criteria
        criteria.setRenewalModes(Arrays.asList(RenewalMode.RenewOnce, RenewalMode.NoAutorenew));
        result = domainSearchService.findDomainAutorenewals(criteria, 0, 10, null);
        Assert.assertEquals(1, result.getTotalResults());
        Assert.assertEquals(1, result.getResults().size());
        for (int i = 0; i < result.getResults().size(); i++) {
            RenewalMode renewalMode = result.getResults().get(i).getDsmState().getRenewalMode();
            Assert.assertTrue(renewalMode == RenewalMode.RenewOnce);
        }
    }

    @Test
    public void testFindTransferDataWithExistingHistoricalData() throws DateParseException {
        final String domainName = "transferred2.ie";
        final Date currentDate = new Date();
        Domain currentDomain = domainDao.get(domainName);
        histDomainDao.create(currentDomain, currentDate, "test");
        currentDomain.setRenewalDate(currentDate);
        domainDao.updateUsingHistory(histDomainDao.create(currentDomain, new Date(), "test"));
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setBillingNH("ACB865-IEDR");
        LimitedSearchResult<PlainDomain> result = domainSearchService.findTransferredAwayDomains(criteria, 0, 10, null);
        assertEquals(3, result.getTotalResults());
        PlainDomain d = result.getResults().get(0);
        assertEquals(domainName, d.getName());
        Calendar cal = Calendar.getInstance();
        cal.setTime(d.getRenewalDate());
        assertEquals(2010, cal.get(Calendar.YEAR));
        assertEquals(7, cal.get(Calendar.MONTH));
        assertEquals(29, cal.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testFindTransferIn() throws DateParseException {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setBillingNH("ACB865-IEDR");
        LimitedSearchResult<PlainDomain> result = domainSearchService.findTransferredInDomains(criteria, 0, 10, null);
        assertEquals(2, result.getTotalResults());
        PlainDomain d = result.getResults().get(0);
        assertEquals("transferred.ie", d.getName());
        Calendar cal = Calendar.getInstance();
        cal.setTime(d.getRenewalDate());
        assertEquals(2010, cal.get(Calendar.YEAR));
        assertEquals(7, cal.get(Calendar.MONTH));
        assertEquals(29, cal.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testLooseUtf8ValidationInTransfersHist() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setBillingNH("IDL2-IEDR\01");
        LimitedSearchResult<PlainDomain> result = domainSearchService.findTransferredAwayDomains(criteria, 0, 10, null);
        assertEquals(1, result.getTotalResults());
        PlainDomain d = result.getResults().get(0);
        assertEquals("badutf8\01.ie", d.getName());
    }

    @Test
    public void testFindWithDateRanges() throws DateParseException {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        Calendar calendar = Calendar.getInstance();
        criteria.setDomainName("rough-dates.ie");
        calendar.set(2002, Calendar.JULY, 30);
        Date registrationDate = calendar.getTime();
        criteria.setRegistrationFrom(registrationDate);
        criteria.setRegistrationTo(registrationDate);
        calendar.set(2004, Calendar.AUGUST, 29);
        Date transferDate = calendar.getTime();
        criteria.setTransferFrom(transferDate);
        criteria.setTransferTo(transferDate);
        calendar.set(2010, Calendar.SEPTEMBER, 28);
        Date renewalDate = calendar.getTime();
        criteria.setRenewalFrom(renewalDate);
        criteria.setRenewalTo(renewalDate);
        calendar.set(2012, Calendar.OCTOBER, 27);
        Date suspensionDate = calendar.getTime();
        criteria.setSuspensionFrom(suspensionDate);
        criteria.setSuspensionTo(suspensionDate);
        calendar.set(2013, Calendar.NOVEMBER, 26);
        Date deletionDate = calendar.getTime();
        criteria.setDeletionFrom(deletionDate);
        criteria.setDeletionTo(deletionDate);
        calendar.set(2020, Calendar.DECEMBER, 25);
        Date authcodeExpirationDate = calendar.getTime();
        criteria.setAuthcExpFrom(authcodeExpirationDate);
        criteria.setAuthcExpTo(authcodeExpirationDate);
        LimitedSearchResult<Domain> result = domainSearchService.find(criteria, 0, 10, null);
        assertEquals(1, result.getResults().size());
        assertEquals(1, result.getTotalResults());
    }

    @Test
    public void testFindDeletedWithDateRanges() throws DateParseException {
        DeletedDomainSearchCriteria criteria = new DeletedDomainSearchCriteria();
        Calendar calendar = Calendar.getInstance();
        criteria.setDomainName("deleted-rough-dates.ie");
        calendar.set(2002, Calendar.FEBRUARY, 25);
        Date registrationDate = calendar.getTime();
        criteria.setRegistrationFrom(registrationDate);
        criteria.setRegistrationTo(registrationDate);
        calendar.set(2009, Calendar.MARCH, 24);
        Date renewalDate = calendar.getTime();
        criteria.setRenewalFrom(renewalDate);
        criteria.setRenewalTo(renewalDate);
        // 100 days before current date
        Date deletionDate = new Date((new Date()).getTime() - 8640000000L);
        criteria.setDeletionFrom(deletionDate);
        criteria.setDeletionTo(deletionDate);
        LimitedSearchResult<DeletedDomain> result = domainSearchService.findDeletedDomains(criteria, 0, 10, null);
        assertEquals(1, result.getResults().size());
        assertEquals(1, result.getTotalResults());
    }

    @Test
    public void testFindDeletedDomainsWithDeletionDates() {
        // by default limit to last year
        DeletedDomainSearchCriteria criteria = new DeletedDomainSearchCriteria();
        LimitedSearchResult<DeletedDomain> result = domainSearchService.findDeletedDomains(criteria, 0, 10, null);
        assertEquals(8, result.getTotalResults());
        assertEquals(8, result.getResults().size());

        // explicit deletionFrom overrides default date range
        criteria.setDeletionFrom(DateUtils.addYears(new Date(), -2));
        result = domainSearchService.findDeletedDomains(criteria, 0, 10, null);
        assertEquals(9, result.getTotalResults());
        assertEquals(9, result.getResults().size());

        // also explicit deletionTo overrides default range
        criteria = new DeletedDomainSearchCriteria();
        criteria.setDeletionTo(DateUtils.addYears(new Date(), -1));
        result = domainSearchService.findDeletedDomains(criteria, 0, 10, null);
        assertEquals(1, result.getTotalResults());
        assertEquals(1, result.getResults().size());
    }

    @Test
    public void testFindDeletedDomainsCustomerTypeFilter() {
        // by default limit to last year
        DeletedDomainSearchCriteria criteria = new DeletedDomainSearchCriteria();
        LimitedSearchResult<DeletedDomain> result = domainSearchService.findDeletedDomains(criteria, 0, 10, null);
        assertEquals(8, result.getTotalResults());
        assertEquals(8, result.getResults().size());

        criteria.setType(CustomerType.Registrar);
        result = domainSearchService.findDeletedDomains(criteria, 0, 10, null);
        assertEquals(4, result.getTotalResults());
        assertEquals(4, result.getResults().size());
        assertEquals("deleteddomain2.ie", result.getResults().get(0).getName());

        criteria.setType(CustomerType.Direct);
        result = domainSearchService.findDeletedDomains(criteria, 0, 10, null);
        assertEquals(4, result.getTotalResults());
        assertEquals(4, result.getResults().size());
        assertEquals("deleted-rough-dates.ie", result.getResults().get(0).getName());
    }

    @Test
    public void testNoDataLeakForTransferInReport() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setBillingNH("ACB865-IEDR");
        LimitedSearchResult<PlainDomain> result = domainSearchService.findTransferredInDomains(criteria, 0, 10, null);
        assertEquals(2, result.getTotalResults());
        PlainDomain d = result.getResults().get(0);
        assertEquals("transferred.ie", d.getName());
        assertEquals("Tommy Hilfiger Licensing, Inc.", d.getHolder());
        d = result.getResults().get(1);
        assertEquals("transferred3.ie", d.getName());
        assertEquals("Holder transfer 7", d.getHolder());
    }


    @Test
    public void testTransferInWorksWithDeletedDomains() {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setBillingNH("IDL2-IEDR");
        LimitedSearchResult<PlainDomain> result = domainSearchService.findTransferredInDomains(criteria, 0, 10, null);
        assertEquals(2, result.getTotalResults());
        PlainDomain d = result.getResults().get(0);
        assertEquals("transferred3.ie", d.getName());
        assertEquals("Holder transfer 9", d.getHolder());
        d = result.getResults().get(1);
        assertEquals("transferred4.ie", d.getName());
        assertEquals("Holder transfer 5", d.getHolder());
    }

}
