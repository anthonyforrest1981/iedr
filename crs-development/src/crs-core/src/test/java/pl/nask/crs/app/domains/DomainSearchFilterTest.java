package pl.nask.crs.app.domains;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.contacts.ContactType;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainCountForContact;
import pl.nask.crs.domains.ExtendedDomain;
import pl.nask.crs.domains.RenewalDateType;
import pl.nask.crs.domains.nameservers.NsReport;
import pl.nask.crs.domains.search.DomainCountForContactSearchCriteria;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.search.ExtendedDomainSearchCriteria;
import pl.nask.crs.domains.search.NsReportSearchCriteria;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;

import static org.testng.AssertJUnit.assertEquals;

@ContextConfiguration(locations = {"/application-services-config.xml"})
public class DomainSearchFilterTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    DomainAppService domainAppService;

    @Autowired
    DomainSearchService domainSearchService;

    @Autowired
    AuthenticationService authenticationService;

    @Test
    public void testFindUsingContactFilter() throws Exception {
        AuthenticatedUser user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "127.0.0.1",
                false, null, false, "ws");
        DomainSearchCriteria criteria = new DomainSearchCriteria();

        //All contact types
        criteria.setNicHandle(user.getUsername());
        LimitedSearchResult<Domain> result = domainSearchService.find(criteria, 0, 10, null);
        assertEquals(18, result.getTotalResults());

        //Only billing, admin and tech contacts
        result = domainAppService.search(user, criteria, 0, 10, null);
        assertEquals(17, result.getTotalResults());

        result = domainAppService.searchFull(user, criteria, 0, 10, null);
        assertEquals(17, result.getTotalResults());

        criteria = new DomainSearchCriteria();
        result = domainAppService.findOwn(user, criteria, 0, 10, null);
        assertEquals(17, result.getTotalResults());

        criteria.setNicHandle("APIT2-IEDR");

        //Billing, admin and tech contacts
        result = domainAppService.search(user, criteria, 0, 10, null);
        assertEquals(49, result.getTotalResults());

        //Only billing contacts
        criteria.setContactType(Arrays.asList(ContactType.BILLING));
        result = domainAppService.search(user, criteria, 0, 10, null);
        assertEquals(17, result.getTotalResults());

        //Only billing contacts - creator contacts omitted
        criteria.setContactType(Arrays.asList(ContactType.BILLING, ContactType.CREATOR));
        result = domainAppService.search(user, criteria, 0, 10, null);
        assertEquals(17, result.getTotalResults());

        //Billing, admin and tech contacts - creator contacts omitted, empty list replaced
        criteria.setContactType(Arrays.asList(ContactType.CREATOR));
        result = domainAppService.search(user, criteria, 0, 10, null);
        assertEquals(49, result.getTotalResults());
    }

    @Test
    public void testFindDomainCountForContact() throws Exception {
        AuthenticatedUser user = authenticationService.authenticate("APIT2-IEDR", "Passw0rd!", false, "127.0.0.1",
                false, null, false, "ws");
        DomainCountForContactSearchCriteria criteria = new DomainCountForContactSearchCriteria();
        List<DomainCountForContact> result = domainAppService.findDomainCountForContact(user, criteria);
        assertEquals(3, result.size());
        assertEquals(ContactType.ADMIN, result.get(0).getContactType());
        assertEquals(4, result.get(0).getDomainCount().intValue());
        assertEquals(ContactType.BILLING, result.get(1).getContactType());
        assertEquals(17, result.get(1).getDomainCount().intValue());
        assertEquals(ContactType.TECH, result.get(2).getContactType());
        assertEquals(49, result.get(2).getDomainCount().intValue());

        //Only domains in NRP
        criteria.setActiveFlag(false);
        result = domainAppService.findDomainCountForContact(user, criteria);
        assertEquals(1, result.size());
        assertEquals(ContactType.TECH, result.get(0).getContactType());
        assertEquals(5, result.get(0).getDomainCount().intValue());
    }

    @Test
    public void testFindDomainForCurrentRenewal() throws Exception {
        AuthenticatedUser user = authenticationService.authenticate("APITEST-IEDR", "Passw0rd!", false, "127.0.0.1",
                false, null, false, "ws");
        ExtendedDomainSearchCriteria criteria = new ExtendedDomainSearchCriteria();
        LimitedSearchResult<ExtendedDomain> result = domainAppService.findDomainsForCurrentRenewal(user,
                RenewalDateType.RENEWAL_TODAY, criteria, 0, 5, null);
        for (ExtendedDomain domain : result.getResults()) {
            Assert.assertEquals(domain.getRenewalDate(), DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
        }
        Assert.assertEquals(result.getTotalResults(), 1);
        Assert.assertEquals(result.getResults().size(), 1);
    }

    @Test
    public void testFindDomainForFutureRenewal() throws Exception {
        AuthenticatedUser user = authenticationService.authenticate("APITEST-IEDR", "Passw0rd!", false, "127.0.0.1",
                false, null, false, "ws");
        ExtendedDomainSearchCriteria criteria = new ExtendedDomainSearchCriteria();
        int renewalMonth = 5;
        LimitedSearchResult<ExtendedDomain> result = domainAppService.findDomainsForFutureRenewal(user, renewalMonth,
                criteria, 0, 5, null);
        for (ExtendedDomain domain : result.getResults()) {
            checkDomainRenewalInFuture(domain, renewalMonth);
        }
        Assert.assertEquals(2, result.getTotalResults());
        Assert.assertEquals(2, result.getResults().size());
    }

    @Test
    public void testFutureRenewalsDoNotIncludeCurrent() throws Exception {
        AuthenticatedUser user = authenticationService.authenticate("APITEST-IEDR", "Passw0rd!", false, "127.0.0.1",
                false, null, false, "ws");
        ExtendedDomainSearchCriteria criteria = new ExtendedDomainSearchCriteria();
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        List<ExtendedDomain> current = domainAppService.findDomainsForCurrentRenewal(user,
                RenewalDateType.RENEWAL_TODAY, criteria, 0, 5, null).getResults();
        List<ExtendedDomain> future = domainAppService.findDomainsForFutureRenewal(user, currentMonth, criteria, 0, 5,
                null).getResults();
        Assert.assertFalse(current.isEmpty());
        for (ExtendedDomain futureDomain : future) {
            checkDomainRenewalInFuture(futureDomain, currentMonth);
            for (ExtendedDomain currentDomain : current) {
                Assert.assertNotEquals(currentDomain.getName(), futureDomain.getName());
            }
        }
    }

    private void checkDomainRenewalInFuture(ExtendedDomain domain, int renewalMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(domain.getRenewalDate());
        Assert.assertEquals(calendar.get(Calendar.MONTH) + 1, renewalMonth, domain.getRenewalDate().toString());
        Date nextMonthStart = DateUtils.addMonths(DateUtils.truncate(new Date(), Calendar.MONTH), 1);
        Assert.assertTrue(domain.getRenewalDate().compareTo(nextMonthStart) >= 0,
                domain.getRenewalDate().toString());
    }

    @Test
    public void testGetNsReportsl() throws Exception {
        AuthenticatedUser user = authenticationService.authenticate("APITEST-IEDR", "Passw0rd!", false, "127.0.0.1",
                false, null, false, "ws");
        NsReportSearchCriteria criteria = new NsReportSearchCriteria();
        criteria.setDnsName("dns1.myhostns.com");
        LimitedSearchResult<NsReport> reports = domainAppService.getNsReports(user, criteria, 0, 10, null);
        Assert.assertEquals(6, reports.getTotalResults());
        Assert.assertEquals(6, reports.getResults().size());
    }

    @Test
    public void testSearchFullWithLockingActive() throws Exception {
        AuthenticatedUser user = authenticationService.authenticate("IDL2-IEDR", "Passw0rd!", false, "127.0.0.1",
                false, null, false, "ws");
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        LimitedSearchResult<Domain> result = domainAppService.searchFullWithLockingActive(user, criteria, 0, 10, null);
        Assert.assertEquals(9, result.getTotalResults());
        criteria.setNicHandle("AAP368-IEDR");
        result = domainAppService.searchFullWithLockingActive(user, criteria, 0, 10, null);
        // Not includes a domain having AAP368-IEDR only as a creator
        Assert.assertEquals(1, result.getTotalResults());
    }

}
