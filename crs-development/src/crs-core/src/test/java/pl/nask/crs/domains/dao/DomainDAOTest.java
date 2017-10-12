package pl.nask.crs.domains.dao;

import java.util.*;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.ContactType;
import pl.nask.crs.domains.*;
import pl.nask.crs.domains.dao.ibatis.DomainCountForContactIBatisDAO;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.domains.nameservers.NsReport;
import pl.nask.crs.domains.search.DomainCountForContactSearchCriteria;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.search.NsReportSearchCriteria;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.EntitySubcategory;
import pl.nask.crs.entities.dao.EntityCategoryDAO;
import pl.nask.crs.entities.dao.EntityClassDAO;

import static org.testng.AssertJUnit.*;

public class DomainDAOTest extends AbstractContextAwareTest {

    @Autowired
    DomainDAO domainDAO;

    @Autowired
    HistoricalDomainDAO histDomainDAO;

    @Autowired
    DomainCountForContactIBatisDAO domainCountForContactDAO;

    @Autowired
    EntityClassDAO entityClassDAO;

    @Autowired
    EntityCategoryDAO entityCategoryDAO;

    @Test
    public void testGet() {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        assertNotNull(domain);
    }

    @Test
    public void testLock() {
        final String domainName = "castlebargolfclub.ie";
        assertTrue(domainDAO.lock(domainName));
        Domain actual = domainDAO.get(domainName);
        assertNotNull(actual);
        //EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testUpdate() {
        domainDAO.update(domainDAO.get("castlebargolfclub.ie"));
    }

    @Test
    public void testUpdateDomainAdminContacts() {
        final String domainName = "castlebargolfclub.ie";
        assertTrue(domainDAO.lock(domainName));
        Domain actual = domainDAO.get(domainName);
        assertNotNull(actual);

        List<Contact> contacts = actual.getAdminContacts();
        int size = contacts.size();
        contacts.add(new Contact("ABC718-IEDR"));
        domainDAO.updateUsingHistory(histDomainDAO.create(actual, new Date(), TestOpInfo.DEFAULT.getActorName()));

        actual = domainDAO.get(domainName);
        assertEquals(actual.getAdminContacts().size(), size + 1);
    }

    @Test
    public void testUpdateDomainHolder() {
        final String domainName = "castlebargolfclub.ie";
        assertTrue(domainDAO.lock(domainName));
        Domain actual = domainDAO.get(domainName);
        assertNotNull(actual);

        actual.setHolder("test holder");
        domainDAO.updateUsingHistory(histDomainDAO.create(actual, new Date(), TestOpInfo.DEFAULT.getActorName()));

        actual = domainDAO.get(domainName);
        assertEquals(actual.getHolder(), "test holder");
    }

    @Test
    public void testUpdateHolderClass() {
        final String domainName = "castlebargolfclub.ie";
        assertTrue(domainDAO.lock(domainName));
        Domain actual = domainDAO.get(domainName);
        assertNotNull(actual);

        actual.setHolderClass(entityClassDAO.getClassByName("Sole Trader"));
        domainDAO.updateUsingHistory(histDomainDAO.create(actual, new Date(), TestOpInfo.DEFAULT.getActorName()));

        actual = domainDAO.get(domainName);
        assertEquals(actual.getHolderClass().getName(), "Sole Trader");
    }

    @Test
    public void testUpdateHolderCategory() {
        final String domainName = "castlebargolfclub.ie";
        assertTrue(domainDAO.lock(domainName));
        Domain actual = domainDAO.get(domainName);
        assertNotNull(actual);

        actual.setHolderCategory(entityCategoryDAO.getCategoryByName("Personal Trading Name"));
        domainDAO.updateUsingHistory(histDomainDAO.create(actual, new Date(), TestOpInfo.DEFAULT.getActorName()));

        actual = domainDAO.get(domainName);
        assertEquals(actual.getHolderCategory().getName(), "Personal Trading Name");
    }

    @Test
    public void testUpdateDNS() {
        final String domainName = "castlebargolfclub.ie";
        assertTrue(domainDAO.lock(domainName));
        Domain actual = domainDAO.get(domainName);
        assertNotNull(actual);

        List<Nameserver> list = actual.getNameservers();
        list.set(0, new Nameserver("a.a.a", "1.2.3.4", "::1"));
        domainDAO.updateUsingHistory(histDomainDAO.create(actual, new Date(), TestOpInfo.DEFAULT.getActorName()));

        actual = domainDAO.get(domainName);
        assertEquals(actual.getNameservers().get(0), list.get(0));
    }

    @Test
    public void testGetPreviousHolder() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDomainName("Hilfiger.ie");
        criteria.setDomainHolder("Tommy Hilfiger Licensing, Inc.");
        String holder = domainDAO.getPreviousHolder(criteria);
        assertNotNull(holder);
    }

    @Test
    public void testFindDomainNamesWithDomainHolder() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setExactDomainHolder("Brian Prendergast");
        List<String> domains = domainDAO.findDomainNames(criteria, 0, 100);
        assertEquals("number of domain names", 3, domains.size());
    }

    @Test
    public void testFindDomainNamesNoAutorenew() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDomainRenewalModes(RenewalMode.NoAutorenew);
        List<String> domains = domainDAO.findDomainNames(criteria, 0, 100);
        assertEquals("number of domain names", 82, domains.size());
    }

    @Test
    public void testFindDomainNamesAutorenew() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDomainRenewalModes(RenewalMode.Autorenew);
        List<String> domains = domainDAO.findDomainNames(criteria, 0, 100);
        assertEquals("number of domain names", 5, domains.size());
    }

    @Test
    public void testFindDomainNamesAutorenewOrNoAutorenew() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDomainRenewalModes(RenewalMode.NoAutorenew, RenewalMode.Autorenew);
        List<String> domains = domainDAO.findDomainNames(criteria, 0, 100);
        assertEquals("number of domain names", 87, domains.size());
    }

    @Test
    public void testFindDomainNamesWithWrongDomainHolder() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setExactDomainHolder("Brian Prendergas");
        List<String> domains = domainDAO.findDomainNames(criteria, 0, 100);
        assertEquals("number of domain names", 0, domains.size());
    }

    @Test
    public void testLimitedFindByEmptyCriteria() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        LimitedSearchResult<Domain> found = domainDAO.find(criteria, 0, 1);
        assertEquals(1, found.getResults().size());
    }

    @Test
    public void testLimitedFindByDomainName() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDomainName("castlebargolfclub.ie");
        LimitedSearchResult<Domain> found = domainDAO.find(criteria, 0, 1);
        assertEquals(1, found.getResults().size());
        assertEquals(1, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByAccount() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setAccountId(122L);
        LimitedSearchResult<Domain> found = domainDAO.find(criteria, 0, 1);
        assertEquals(1, found.getResults().size());
        assertEquals(4, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByDomainHolder() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDomainHolder("astleba");
        LimitedSearchResult<Domain> found = domainDAO.find(criteria, 0, 1);
        assertEquals(1, found.getResults().size());
        assertEquals(5, found.getTotalResults());
    }

    @Test
    public void testLimitedFindNicHandle() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setNicHandle("AA");
        LimitedSearchResult<Domain> found = domainDAO.find(criteria, 0, 5);
        assertEquals(5, found.getResults().size());
        assertEquals(31, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByLockingDate() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setLockFrom(DateUtils.addMonths(new Date(), -10));
        criteria.setLockTo(DateUtils.addMonths(new Date(), -4));
        LimitedSearchResult<Domain> found = domainDAO.find(criteria, 0, 5);
        assertEquals(4, found.getResults().size());
        assertEquals(4, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByLockingRenewalDate() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setLockRenewalFrom(new Date());
        criteria.setLockRenewalTo(DateUtils.addMonths(new Date(), 6));
        LimitedSearchResult<Domain> found = domainDAO.find(criteria, 0, 5);
        assertEquals(5, found.getResults().size());
        assertEquals(5, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByLockedStatus() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setLocked(true);
        LimitedSearchResult<Domain> found = domainDAO.find(criteria, 0, 5);
        assertEquals(5, found.getResults().size());
        assertEquals(8, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByLockingActive() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setLockingActive(true);
        LimitedSearchResult<Domain> found = domainDAO.find(criteria, 0, 5);
        assertEquals(5, found.getResults().size());
        assertEquals(9, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByMultipleContactType() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setNicHandle("APIT1-IEDR");
        List<ContactType> contactTypeList = new ArrayList<ContactType>();
        contactTypeList.add(ContactType.ADMIN);
        contactTypeList.add(ContactType.TECH);
        criteria.setContactType(contactTypeList);
        LimitedSearchResult<Domain> found = domainDAO.find(criteria, 0, 5);
        assertEquals(5, found.getResults().size());
        assertEquals(64, found.getTotalResults());
    }

    private DomainSearchCriteria getAllCriteria() {
        DomainSearchCriteria allCriteria = new DomainSearchCriteria();
        List<String> billingStatusList = new ArrayList<String>();
        billingStatusList.add("Y");
        allCriteria.setAccountId(1L);
        allCriteria.setDomainHolder("T");
        allCriteria.setDomainName("T");
        allCriteria.setNicHandle("A");
        allCriteria.setRegistrationFrom(new Date(0L));
        allCriteria.setRegistrationTo(new Date());
        allCriteria.setRenewalFrom(new Date(0L));
        allCriteria.setRenewalTo(new Date(9999999999999L));
        return allCriteria;
    }

    @Test
    public void testFindLimitedByAllCriteria() {
        LimitedSearchResult<Domain> found = domainDAO.find(getAllCriteria(), 1, 3);
        assertEquals(3, found.getResults().size());
        assertEquals(7, found.getTotalResults());
    }

    @Test
    public void testCount() {
        assertEquals(7, domainDAO.count(getAllCriteria()));
    }

    @Test
    public void testExists() {
        assertTrue(domainDAO.exists(getAllCriteria()));
    }

    @Test
    public void existsByContact() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setNicHandle("AAB069-IEDR");
        assertTrue(domainDAO.exists(criteria));
    }

    @Test
    public void isNotContact() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setNicHandle("AA11-IEDR");
        assertFalse(domainDAO.exists(criteria));
    }

    @Test
    public void testNotExists() {
        DomainSearchCriteria criteria = getAllCriteria();
        criteria.setNicHandle("AA");
        assertFalse(domainDAO.exists(criteria));
    }

    public static Domain getDomainToCreateDetails(Date date) {
        List<Contact> adminContacts = new ArrayList<>();
        adminContacts.add(new Contact("APIT1-IEDR"));

        List<Contact> techContacts = new ArrayList<>();
        techContacts.add(new Contact("APIT1-IEDR"));

        List<Contact> billingContacts = new ArrayList<>();
        billingContacts.add(new Contact("IH4-IEDR"));

        List<Nameserver> nsList = new ArrayList<>();
        nsList.add(new Nameserver("dns.testowy.ie", "1.2.3.5", "::2"));

        Domain domain = new Domain("createdDomain.ie", "Test Holder", getEntityClass(), getEntityCategory(),
                getEntitySubcategory(), new Contact("APIT1-IEDR"), new Account(666), date, date, "Domain create", date,
                false, techContacts, billingContacts, adminContacts, nsList, DsmState.initialState(), true, date,
                DateUtils.addMonths(date, 10));
        domain.setAuthCodePortalCount(0);
        return domain;
    }

    private static EntityClass getEntityClass() {
        EntityClass domainClass = new EntityClass();
        domainClass.setId(3);
        domainClass.setName("Sole Trader");
        return domainClass;
    }

    private static EntityCategory getEntityCategory() {
        EntityCategory domainCategory = new EntityCategory();
        domainCategory.setId(11);
        domainCategory.setName("Discretionary Name");
        return domainCategory;
    }

    private static EntitySubcategory getEntitySubcategory() {
        EntitySubcategory domainSubcategory = new EntitySubcategory();
        domainSubcategory.setId(2);
        domainSubcategory.setName("Blog / Informative website");
        return domainSubcategory;
    }

    public static Domain getExpectedCreatedDomainDetails(Date date) {
        Contact creator = new Contact("APIT1-IEDR", "API Tester", "NHEmail000877@server.xxx", "API Tests", "Ireland",
                "Co. Dublin");

        List<Contact> adminContacts = new ArrayList<>();
        adminContacts.add(creator);

        List<Contact> techContacts = new ArrayList<>();
        techContacts.add(creator);

        List<Contact> billingContacts = new ArrayList<>();
        billingContacts.add(new Contact("IH4-IEDR", "IEDR Hostmaster", "NHEmail000903@server.xxx",
                "IE Domain Registry Limited", "Ireland", "Co. Dublin"));

        List<Nameserver> nsList = new ArrayList<>();
        nsList.add(new Nameserver("dns.testowy.ie", "1.2.3.5", "::2"));

        Domain domain = new Domain("createdDomain.ie", "Test Holder", getEntityClass(), getEntityCategory(),
                getEntitySubcategory(), creator, new Account(666), date, date, "Domain create", date, false,
                techContacts, billingContacts, adminContacts, nsList, DsmState.initialState(), true, date,
                DateUtils.addMonths(date, 10));
        domain.setAuthCodePortalCount(0);
        return domain;
    }

    @Test
    public void createDomainTest() {
        Date date = DateUtils.setMilliseconds(new Date(), 999);
        Domain expectedDomain = getExpectedCreatedDomainDetails(date);
        domainDAO.create(getDomainToCreateDetails(date));
        Domain dbDomain = domainDAO.get("createdDomain.ie");

        DomainsHelper.compareBasics(dbDomain, expectedDomain);
        DomainsHelper.compareDates(dbDomain, expectedDomain, false);
    }

    @Test
    public void createDomainAndUpdateUsingHistory() {
        Date date = DateUtils.setMilliseconds(new Date(), 999);
        Domain domain = getDomainToCreateDetails(date);
        Domain expectedDomain = getExpectedCreatedDomainDetails(date);
        domainDAO.create(domain);
        domainDAO.updateUsingHistory(histDomainDAO.create(domain, domain.getChangeDate(),
                TestOpInfo.DEFAULT.getActorName()));
        Domain dbDomain = domainDAO.get("createdDomain.ie");

        DomainsHelper.compareDomains(dbDomain, expectedDomain);
    }

    @Test
    public void saveChangeDateTest() {
        Domain d = domainDAO.get("castlebargolfclub.ie");
        Assert.assertNull(d.getSuspensionDate());
        Date crDate = DateUtils.setMilliseconds(new Date(), 999);
        d.setChangeDate(crDate);
        domainDAO.updateUsingHistory(histDomainDAO.create(d, new Date(), TestOpInfo.DEFAULT.getActorName()));

        d = domainDAO.get("castlebargolfclub.ie");
        Assert.assertNotNull(d.getChangeDate());
        Assert.assertEquals(d.getChangeDate(), DateUtils.truncate(crDate, Calendar.SECOND));
    }

    @Test
    public void saveRenewalDateTest() {
        Domain d = domainDAO.get("castlebargolfclub.ie");
        Date crDate = DateUtils.setMilliseconds(new Date(), 999);
        d.setRenewalDate(crDate);
        domainDAO.updateUsingHistory(histDomainDAO.create(d, new Date(), TestOpInfo.DEFAULT.getActorName()));

        d = domainDAO.get("castlebargolfclub.ie");
        Assert.assertNotNull(d.getRenewalDate());
        Assert.assertEquals(d.getRenewalDate(), DateUtils.truncate(crDate, Calendar.DATE));
    }

    @Test
    public void saveSuspensionDateTest() {
        Domain d = domainDAO.get("castlebargolfclub.ie");
        Assert.assertNull(d.getSuspensionDate());
        Date crDate = DateUtils.setMilliseconds(new Date(), 999);
        d.setSuspensionDate(crDate);
        domainDAO.updateUsingHistory(histDomainDAO.create(d, new Date(), TestOpInfo.DEFAULT.getActorName()));

        d = domainDAO.get("castlebargolfclub.ie");
        Assert.assertNotNull(d.getSuspensionDate());
        Assert.assertEquals(d.getSuspensionDate(), DateUtils.truncate(crDate, Calendar.DATE));
    }

    @Test
    public void saveDeletionDateTest() {
        Domain d = domainDAO.get("castlebargolfclub.ie");
        Assert.assertNull(d.getDeletionDate());
        Date crDate = DateUtils.setMilliseconds(new Date(), 999);
        d.setDeletionDate(crDate);
        domainDAO.updateUsingHistory(histDomainDAO.create(d, new Date(), TestOpInfo.DEFAULT.getActorName()));

        d = domainDAO.get("castlebargolfclub.ie");
        Assert.assertNotNull(d.getDeletionDate());
        Assert.assertEquals(d.getDeletionDate(), DateUtils.truncate(crDate, Calendar.DATE));
    }

    @Test
    public void saveChangeAuthCodeExpirationDate() {
        Domain d = domainDAO.get("castlebargolfclub.ie");
        Assert.assertNull(d.getAuthCodeExpirationDate());
        Date crDate = DateUtils.setMilliseconds(new Date(), 999);
        d.setAuthCodeExpirationDate(crDate);
        domainDAO.updateUsingHistory(histDomainDAO.create(d, new Date(), TestOpInfo.DEFAULT.getActorName()));

        d = domainDAO.get("castlebargolfclub.ie");
        Assert.assertNotNull(d.getAuthCodeExpirationDate());
        Assert.assertEquals(d.getAuthCodeExpirationDate(), DateUtils.truncate(crDate, Calendar.DATE));
    }

    @Test
    public void findDomainWithNrpStatusActive() {
        DomainSearchCriteria crit = new DomainSearchCriteria();
        crit.setActive(true);
        SearchResult<Domain> res = domainDAO.find(crit);
        AssertJUnit.assertFalse("Empty result", res.getResults().isEmpty());
        for (Domain d : res.getResults()) {
            AssertJUnit.assertTrue(crit.getNrpStatuses().contains(d.getDsmState().getNrpStatus()));
        }
    }

    @Test
    public void findDomainWithNrpStatusNRP() {
        DomainSearchCriteria crit = new DomainSearchCriteria();
        crit.setActive(false);
        SearchResult<Domain> res = domainDAO.find(crit);
        AssertJUnit.assertFalse("Empty result", res.getResults().isEmpty());
        for (Domain d : res.getResults()) {
            AssertJUnit.assertTrue(crit.getNrpStatuses().contains(d.getDsmState().getNrpStatus()));
        }
    }

    @Test
    public void testZoneDoublePublish() {
        domainDAO.zonePublish("castlebargolfclub.ie");
        domainDAO.zonePublish("castlebargolfclub.ie");
        Domain d = domainDAO.get("castlebargolfclub.ie");
        AssertJUnit.assertFalse(d.isZonePublished());
    }

    @Test
    public void testZonePublishCommit() {
        domainDAO.zonePublish("castlebargolfclub.ie");
        domainDAO.zonePublish("castlebargolfclub.ie");
        domainDAO.zoneCommit();
        Domain d = domainDAO.get("castlebargolfclub.ie");
        AssertJUnit.assertTrue(d.isZonePublished());
    }

    @Test
    public void testZoneUnpublish() {
        domainDAO.zonePublish("castlebargolfclub.ie");
        domainDAO.zoneCommit();
        domainDAO.zoneUnpublish("castlebargolfclub.ie");
        Domain d = domainDAO.get("castlebargolfclub.ie");
        AssertJUnit.assertFalse(d.isZonePublished());
    }

    @Test
    public void findBySuspendedDateTest() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setSuspensionFrom(new Date(105, 4, 6));//2005-05-06
        criteria.setSuspensionTo(new Date(108, 4, 6));
        LimitedSearchResult<Domain> result = domainDAO.find(criteria, 0, 10);
        Assert.assertEquals(2, result.getTotalResults());

        criteria = new DomainSearchCriteria();
        criteria.setSuspensionFrom(new Date(108, 4, 6));
        result = domainDAO.find(criteria, 0, 10);
        Assert.assertEquals(2, result.getTotalResults());

        criteria = new DomainSearchCriteria();
        criteria.setSuspensionTo(new Date(105, 4, 6));//2005-05-06
        result = domainDAO.find(criteria, 0, 10);
        Assert.assertEquals(0, result.getTotalResults());
    }

    @Test
    public void findByDeletedDateTest() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDeletionFrom(new Date(105, 4, 6));//2005-05-06
        criteria.setDeletionTo(new Date(108, 4, 6));
        LimitedSearchResult<Domain> result = domainDAO.find(criteria, 0, 10);
        Assert.assertEquals(4, result.getTotalResults());

        criteria = new DomainSearchCriteria();
        criteria.setDeletionFrom(new Date(108, 4, 6));
        result = domainDAO.find(criteria, 0, 10);
        Assert.assertEquals(1, result.getTotalResults());

        criteria = new DomainSearchCriteria();
        criteria.setDeletionTo(new Date(105, 4, 6));//2005-05-06
        result = domainDAO.find(criteria, 0, 10);
        Assert.assertEquals(0, result.getTotalResults());
    }

    @Test
    public void findByTransferDateTest() {
        Date now = new Date();
        Date futureDate = org.apache.commons.lang.time.DateUtils.addYears(now, 2);
        Date passedDate = org.apache.commons.lang.time.DateUtils.addYears(now, -2);
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setTransferFrom(passedDate);
        criteria.setTransferTo(futureDate);
        LimitedSearchResult<Domain> result = domainDAO.find(criteria, 0, 10);
        Assert.assertEquals(result.getTotalResults(), 3);

        criteria = new DomainSearchCriteria();
        criteria.setTransferFrom(futureDate);
        result = domainDAO.find(criteria, 0, 10);
        Assert.assertEquals(result.getTotalResults(), 0);

        criteria = new DomainSearchCriteria();
        criteria.setTransferTo(passedDate);
        result = domainDAO.find(criteria, 0, 10);
        Assert.assertEquals(result.getTotalResults(), 1);
    }

    @Test
    public void findAllTest() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setAccountId(122L);
        List<Domain> found = domainDAO.findAll(criteria, null);
        assertEquals(4, found.size());
    }

    // TODO: CRS-72
    //    @Test
    //    public void getDsmStatesTest() {
    //        List<Integer> states = domainDAO.getDsmStates();
    //        Assert.assertEquals(115, states.size());
    //    }

    @Test
    public void deleteDomainTest() {
        Domain domain = domainDAO.get("autocreated.ie");
        assertNotNull(domain);
        domainDAO.deleteById(domain.getName());
        domain = domainDAO.get("autocreated.ie");
        assertNull(domain);
    }

    @Test
    public void testDomainExists() {
        AssertJUnit.assertTrue(domainDAO.exists("suka.ie"));
        AssertJUnit.assertFalse(domainDAO.exists("someFakedDomainName.ie"));
    }

    @Test
    public void notificationTest() {
        final Date currentDate = DateUtils.setMilliseconds(new Date(), 999);
        DomainNotification notification = new DomainNotification("domain.ie", 30, DateUtils.addDays(currentDate, 1));
        DomainNotification expiredNotification = new DomainNotification("domain2.ie", 60, DateUtils.addDays(
                currentDate, -1));
        domainDAO.createNotification(notification);
        domainDAO.createNotification(expiredNotification);
        List<DomainNotification> all = domainDAO.getAllNotifications();
        Assert.assertEquals(2, all.size());

        DomainNotification dbNotification = domainDAO.getDomainNotification("domain.ie", 30);
        Assert.assertNotNull(dbNotification);
        Assert.assertEquals(dbNotification.getExpirationDate(),
                DateUtils.truncate(DateUtils.addDays(currentDate, 1), Calendar.DATE));
        DomainNotification dbExpiredNotification = domainDAO.getDomainNotification("domain2.ie", 60);
        Assert.assertNull(dbExpiredNotification);
    }

    @Test
    public void findByHolderTypeCharity() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDomainHolderTypes(DomainHolderType.Charity);
        SearchResult<Domain> result = domainDAO.find(criteria);
        Assert.assertEquals(3, result.getResults().size());
        for (Domain domain : result.getResults()) {
            Assert.assertEquals(DomainHolderType.Charity, domain.getDsmState().getDomainHolderType());
        }
    }

    @Test
    public void findByHolderTypeBillable() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDomainHolderTypes(DomainHolderType.Billable);
        SearchResult<Domain> result = domainDAO.find(criteria);
        assertEquals(85, result.getResults().size());
        for (Domain domain : result.getResults()) {
            Assert.assertEquals(DomainHolderType.Billable, domain.getDsmState().getDomainHolderType());
        }
    }

    @Test
    public void findByHolderTypeCharityOrBillable() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();

        criteria.setDomainHolderTypes(DomainHolderType.Charity, DomainHolderType.Billable);
        SearchResult<Domain> result = domainDAO.find(criteria);
        assertEquals(88, result.getResults().size());
    }

    @Test
    public void fullFindTest() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        LimitedSearchResult<Domain> result = domainDAO.fullDomainFind(criteria, 0, 200, null);
        assertEquals(145, result.getTotalResults());
        assertEquals(145, result.getResults().size());
    }

    @Test
    public void findByCustomerTypeTest() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setType(CustomerType.Direct);
        LimitedSearchResult<Domain> result = domainDAO.fullDomainFind(criteria, 0, 200, null);
        assertEquals(24, result.getTotalResults());
        assertEquals(24, result.getResults().size());
    }

    @Test
    public void findByBillingNHTest() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setBillingNH("AAE553-IEDR");
        LimitedSearchResult<Domain> result = domainDAO.fullDomainFind(criteria, 0, 200, null);
        assertEquals(9, result.getTotalResults());
        assertEquals(9, result.getResults().size());
    }

    @Test
    public void getNsReportTest() {
        LimitedSearchResult<NsReport> reports = domainDAO.getNsReport("APITEST-IEDR", null, 0, 10, null);
        Assert.assertEquals(45, reports.getTotalResults());
        Assert.assertEquals(10, reports.getResults().size());

        NsReportSearchCriteria criteria = new NsReportSearchCriteria();
        criteria.setDnsName("dns1.myhostns.com");
        reports = domainDAO.getNsReport("APITEST-IEDR", criteria, 0, 10, null);
        Assert.assertEquals(6, reports.getTotalResults());
        Assert.assertEquals(6, reports.getResults().size());

        criteria = new NsReportSearchCriteria();
        reports = domainDAO.getNsReport("AAU809-IEDR", criteria, 0, 10, null);
        Assert.assertEquals(12, reports.getTotalResults());
        Assert.assertEquals(10, reports.getResults().size());

        criteria = new NsReportSearchCriteria();
        criteria.setDomainName("rough-dates.ie");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2002, Calendar.JULY, 30);
        Date registrationDate = calendar.getTime();
        criteria.setRegistrationFrom(registrationDate);
        criteria.setRegistrationTo(registrationDate);
        reports = domainDAO.getNsReport("APIT1-IEDR", criteria, 0, 10, null);
        Assert.assertEquals(2, reports.getTotalResults());
        Assert.assertEquals(2, reports.getResults().size());
    }

    @Test
    public void findDomainByRenewalDate() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setRenewalDate(new Date(108, 7, 5));//2008-08-05
        List<Domain> domains = domainDAO.findAll(criteria, null);
        Assert.assertEquals(1, domains.size());
    }

    @Test
    public void createTransferRecordShouldNotFail() {
        domainDAO.createTransferRecord("castlbargolfclub.ie", new Date(), "NTG1-IEDR", "NH4-IEDR");
    }

    /*
     * this checks, if one can search for a domain which has different billing and (admin/tech) contact.
     * ex: makingadifference.ie
     * billingC: AAG061-IEDR
     * adminC: ADM152-IEDR
     */
    @Test
    public void testFindDomainNamesForBillingContactWithOtherContactRequired() {
        DomainSearchCriteria crit = new DomainSearchCriteria();
        crit.setNicHandle("ADM152-IEDR");
        crit.setBillingNH("AAG061-IEDR");
        List<String> res = domainDAO.findDomainNames(crit, 0, 10);
        assertResultContainsDomain(res, "makingadifference.ie");
    }

    /*
     * this checks, if one can search for a domain which has different contacts
     * ex: makingadifference.ie
     * billingC: AAG061-IEDR
     * adminC: ADM152-IEDR
     */
    @Test
    public void testFindDomainNamesSharing2Contacts() {
        DomainSearchCriteria crit = new DomainSearchCriteria();
        crit.setNicHandle("ADM152-IEDR");
        crit.setSecondContact("AAG061-IEDR");

        List<String> res = domainDAO.findDomainNames(crit, 0, 10);

        String domainName = "makingadifference.ie";
        assertResultContainsDomain(res, domainName);

        // this must also work with the contact types set
        crit.setContactType(Arrays.asList(ContactType.ADMIN));
        crit.setSecondContactType(Arrays.asList(ContactType.BILLING));
        res = domainDAO.findDomainNames(crit, 0, 10);
        assertResultContainsDomain(res, domainName);

        crit.setSecondContactType(Arrays.asList(ContactType.ADMIN));
        res = domainDAO.findDomainNames(crit, 0, 10);
        Assert.assertEquals(0, res.size());
    }

    // this is a regression test, see https://drotest4.nask.net.pl:3000/issues/11093
    @Test
    public void nullCollectionInSecondContactTypeShouldBeIgnored() {
        DomainSearchCriteria crit = new DomainSearchCriteria();
        // a mutable list is needed
        crit.setSecondContactType(new ArrayList(Arrays.asList((ContactType) null)));
        // no exception should be thrown here
        domainDAO.findDomainNames(crit, 0, 1);
    }

    private void assertResultContainsDomain(List<String> res, String domainName) {
        Assert.assertNotNull(res);
        AssertJUnit.assertFalse("should get at least one result", res.isEmpty());
        AssertJUnit.assertTrue("result should contain '" + domainName + "'", res.contains(domainName));
    }

    @Test
    public void testFindDomainWithUnnormalizedName() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDomainName("unnormalized-name");
        List<String> domains = domainDAO.findDomainNames(criteria, 0, 10);
        Assert.assertEquals(domains.size(), 1);
        Assert.assertEquals(domains.get(0), "unnormalized-name-\u00f5\u1ebb.ie");
    }

    @Test
    public void testGetDomainWithUnnormalizedDetails() {
        Domain domain = domainDAO.get("unnormalized-details.ie");
        Assert.assertEquals(domain.getHolder(), "Unnormalized \u0124\u1ecdlder");
        Assert.assertEquals(domain.getHolderClass().getName(), "Unnormalized Cl\u00e3ss");
        Assert.assertEquals(domain.getHolderCategory().getName(), "Unnormalized Cate\u01f5\u014dry");
        Assert.assertEquals(domain.getRemark(), "\u0232\u0123\u1e43\u1e96\u01e9");
        Assert.assertEquals(domain.getAuthCode(), "\u1e1e9\u1e5c9VO");
        Assert.assertEquals(domain.getNameservers().size(), 2);
        Assert.assertEquals(domain.getNameservers().get(0).getName(), "\u0144\u1e611.unn\u020drmali\u1e91ed.ie");
        Assert.assertEquals(domain.getNameservers().get(1).getName(), "\u0148\u015f2.unn\u020frmali\u017ced.ie");
        Assert.assertEquals(domain.getAdminContacts().size(), 1);
        Assert.assertEquals(domain.getAdminContacts().get(0).getNicHandle(), "XY\u01788-IEDR");
        List<Contact> contacts = new ArrayList<Contact>();
        contacts.add(domain.getCreator());
        contacts.add(domain.getBillingContact());
        contacts.addAll(domain.getTechContacts());
        Assert.assertEquals(contacts.size(), 3);
        for (Contact contact : contacts) {
            Assert.assertEquals(contact.getNicHandle(), "YYY9-IEDR");
            Assert.assertEquals(contact.getName(), "\u00cc\u1e43\u1e03\u00fa\u0205l");
            Assert.assertEquals(contact.getCompanyName(), "\u1e0c\u011c\u1e42\u0158 Ltd.");
            Assert.assertEquals(contact.getCountyName(), "Unnormalized co\u00fan\u0163y");
            Assert.assertEquals(contact.getCountryName(), "Unnormalized c\u00f4u\u1e45try");
            Assert.assertEquals(contact.getEmail(), "\u1eb9\u1e3f\u0105\u1ec9\u1e3d@server.xxx");
        }
    }

    @Test
    public void testFindUtf8DomainWithSearchCriteria() {
        String domainName = "normalized-correct-\u00f5\u1ebb.ie";
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDomainName(domainName);
        SearchResult<Domain> result = domainDAO.find(criteria);
        Assert.assertEquals(result.getResults().size(), 1);
        criteria = new DomainSearchCriteria();
        criteria.setDomainHolder("Normalized \u0124\u1ecdlder");
        result = domainDAO.find(criteria);
        Assert.assertEquals(result.getResults().size(), 1);
        Assert.assertEquals(result.getResults().get(0).getName(), domainName);
        criteria = new DomainSearchCriteria();
        criteria.setHolderClassName("Normalized Cl\u00e3ss");
        result = domainDAO.find(criteria);
        Assert.assertEquals(result.getResults().size(), 1);
        Assert.assertEquals(result.getResults().get(0).getName(), domainName);
        criteria = new DomainSearchCriteria();
        criteria.setHolderCategoryName("Normalized Cate\u01f5\u014dry");
        result = domainDAO.find(criteria);
        Assert.assertEquals(result.getResults().size(), 1);
        Assert.assertEquals(result.getResults().get(0).getName(), domainName);
        criteria = new DomainSearchCriteria();
        criteria.setNicHandle("XX\u01787-IEDR");
        result = domainDAO.find(criteria);
        Assert.assertEquals(result.getResults().size(), 1);
        Assert.assertEquals(result.getResults().get(0).getName(), domainName);
    }

    private String getForbiddenString() {
        // an exaple of 4-byte UTF-8
        String str = new String(Character.toChars(Integer.parseInt("10348", 16)));
        return str;
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testCreateDomainWithForbiddenName() {
        Domain domain = getDomainToCreateDetails(new Date());
        domain.setName(getForbiddenString());
        domainDAO.create(domain);
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testCreateDomainWithForbiddenHolder() {
        Domain domain = getDomainToCreateDetails(new Date());
        domain.setHolder(getForbiddenString());
        domainDAO.create(domain);
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testCreateDomainWithForbiddenRemark() {
        Domain domain = getDomainToCreateDetails(new Date());
        domain.setRemark(getForbiddenString());
        domainDAO.create(domain);
    }

    @Test
    public void testDomainCountForUtf8Contact() {
        DomainCountForContactSearchCriteria criteria = new DomainCountForContactSearchCriteria();
        criteria.setNicHandle("XX\u01787-IEDR");
        List<DomainCountForContact> result = domainCountForContactDAO.findDomainCountForContact(criteria);
        Assert.assertEquals(result.size(), 4);
        Assert.assertEquals(result.get(0).getContactType(), ContactType.ADMIN);
        Assert.assertEquals(result.get(0).getDomainCount().intValue(), 1);
    }

    @Test
    public void testLockingAndLockingRenewalDates() {
        Domain d = domainDAO.get("pizzaonline.ie");
        Assert.assertNull(d.getLockingDate());
        Assert.assertNull(d.getLockingRenewalDate());
        final Date today = new Date();
        final Date in10months = DateUtils.addMonths(today, 10);
        d.setLockingDate(today);
        d.setLockingRenewalDate(in10months);
        domainDAO.updateUsingHistory(histDomainDAO.create(d, new Date(), TestOpInfo.DEFAULT.getActorName()));
        d = domainDAO.get("pizzaonline.ie");
        Assert.assertEquals(d.getLockingDate(), DateUtils.truncate(today, Calendar.SECOND));
        Assert.assertEquals(d.getLockingRenewalDate(), DateUtils.truncate(in10months, Calendar.DATE));
    }

    @Test
    public void testFindDomainBySecondaryMarketStatus() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess);
        assertEquals(72, domainDAO.findDomainNames(criteria, 0, 100).size());
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NA);
        assertEquals(57, domainDAO.findDomainNames(criteria, 0, 100).size());
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.BuyRequestRegistered);
        assertEquals(7, domainDAO.findDomainNames(criteria, 0, 100).size());
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.SellRequestRegistered);
        assertEquals(9, domainDAO.findDomainNames(criteria, 0, 100).size());
        criteria.setSecondaryMarketStatuses(SecondaryMarketStatus.NoProcess, SecondaryMarketStatus.NA,
                SecondaryMarketStatus.BuyRequestRegistered, SecondaryMarketStatus.SellRequestRegistered);
        assertEquals(145, domainDAO.findDomainNames(criteria, 0, 200).size());
    }

}
