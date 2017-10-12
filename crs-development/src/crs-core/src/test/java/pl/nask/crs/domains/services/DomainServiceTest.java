package pl.nask.crs.domains.services;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import pl.nask.crs.app.tickets.exceptions.*;
import pl.nask.crs.commons.DateTestHelper;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.dns.exceptions.*;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.dao.ContactDAO;
import pl.nask.crs.domains.*;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dao.DomainDAOTest;
import pl.nask.crs.domains.dao.HistoricalDomainDAO;
import pl.nask.crs.domains.dao.PlainDomainDAO;
import pl.nask.crs.domains.exceptions.AuthcodeGenerationDomainStateException;
import pl.nask.crs.domains.exceptions.DomainLockedException;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.domains.search.HistoricalDomainSearchCriteria;
import pl.nask.crs.domains.search.PlainDomainSearchCriteria;
import pl.nask.crs.entities.exceptions.ClassDoesNotMatchCategoryException;
import pl.nask.crs.entities.service.EntityService;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.exception.NicHandleIncorrectForAccountException;
import pl.nask.crs.secondarymarket.dao.BuyRequestDAO;
import pl.nask.crs.secondarymarket.dao.SellRequestDAO;
import pl.nask.crs.secondarymarket.exceptions.SellRequestExistsException;
import pl.nask.crs.secondarymarket.search.BuyRequestSearchCriteria;
import pl.nask.crs.secondarymarket.search.SellRequestSearchCriteria;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import static org.testng.AssertJUnit.*;

public class DomainServiceTest extends AbstractContextAwareTest {

    @Resource
    private DomainService domainService;
    @Resource
    private DomainDAO domainDAO;
    @Resource
    private PlainDomainDAO plainDomainDAO;
    @Resource
    private ContactDAO contactDAO;
    @Resource
    private HistoricalDomainDAO historicalDomainDAO;
    @Resource
    private BuyRequestDAO buyRequestDAO;
    @Resource
    private SellRequestDAO sellRequestDAO;
    @Resource
    EntityService entityService;

    @Test
    public void testSave() throws Exception {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        assertNotNull(domain);

        domain.setRemark("test");
        domainService.save(domain, new TestOpInfo("nichandle"));

        domain = domainDAO.get("castlebargolfclub.ie");
        Assert.assertTrue(domain.getRemark().contains("nichandle"));
    }

    @Test
    public void testChangeNameservers() throws Exception {
        String domainName = "theweb.ie";
        Domain domain = domainDAO.get(domainName);
        assertNotNull(domain);
        Nameserver nameserver1 = new Nameserver("name1.theweb.ie", "1.1.1.1", "::1");
        Nameserver nameserver2 = new Nameserver("name2.theweb.ie", "1.1.1.1", "::1");
        List<Nameserver> nameservers = new ArrayList<Nameserver>();
        nameservers.add(nameserver1);
        nameservers.add(nameserver2);
        domain.setNameservers(nameservers);
        domain.setRemark("remark");
        domainService.save(domain, new TestOpInfo("GEORGE-IEDR"));
        Domain domain2 = domainDAO.get(domainName);
        assertEquals(domain2.getNameservers().size(), 2);
        assertEquals(domain2.getNameservers().get(0), nameserver1);
        assertEquals(domain2.getNameservers().get(1), nameserver2);
        SearchResult<HistoricalObject<Domain>> result = findHistory(domainName);
        assertEquals(result.getResults().size(), 16);

        Domain previousDomainHist = result.getResults().get(14).getObject();
        assertEquals(previousDomainHist.getNameservers().size(), 2);
        assertEquals(previousDomainHist.getNameservers().get(0).getName(), "CSF14.SSLSITE.COM");
        assertNull(previousDomainHist.getNameservers().get(0).getIpv4Address());
        assertNull(previousDomainHist.getNameservers().get(0).getIpv6Address());

        Domain currentDomainHist = result.getResults().get(15).getObject();
        assertEquals(currentDomainHist.getNameservers().size(), 2);
        assertEquals(currentDomainHist.getNameservers().get(0).getName(), nameserver1.getName());
        assertEquals(currentDomainHist.getNameservers().get(0).getIpv4Address(), nameserver1.getIpv4Address());
        assertEquals(currentDomainHist.getNameservers().get(0).getIpv6Address(), nameserver1.getIpv6Address());
        assertEquals(currentDomainHist.getNameservers().get(1).getName(), nameserver2.getName());
        assertEquals(currentDomainHist.getNameservers().get(1).getIpv4Address(), nameserver2.getIpv4Address());
        assertEquals(currentDomainHist.getNameservers().get(1).getIpv6Address(), nameserver2.getIpv6Address());
    }

    private SearchResult<HistoricalObject<Domain>> findHistory(String domainName) {
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setDomainName(domainName);
        SearchResult<HistoricalObject<Domain>> result = historicalDomainDAO.find(criteria);
        return result;
    }

    @Test
    public void createTransferRecord() throws Exception {
        PlainDomainSearchCriteria criteria = new PlainDomainSearchCriteria();
        criteria.setBillingNH("NTG1-IEDR");
        LimitedSearchResult<PlainDomain> transferredDomains = plainDomainDAO.findTransferredAwayDomains(criteria, 0,
                10, Arrays.asList(new SortCriterion("domainName", true)));
        long numberOfResults = transferredDomains.getTotalResults();

        final Date transferDate = DateUtils.setMilliseconds(new Date(), 999);
        domainService.createTransferRecord("castlebargolfclub.ie", transferDate, "NTG1-IEDR", "NH4-IEDR");

        transferredDomains = plainDomainDAO.findTransferredAwayDomains(criteria, 0, 10,
                Arrays.asList(new SortCriterion("domainName", true)));
        Assert.assertEquals(numberOfResults + 1, transferredDomains.getTotalResults());
    }

    @Test
    public void histRecordShouldBeCreatedWhenSavingDomainChanges() throws Exception {
        String domainName = "castlebargolfclub.ie";
        SearchResult<HistoricalObject<Domain>> history = findHistory(domainName);
        // just to be sure: historical chngDate should be not less than domain's change date
        Domain domain = domainDAO.get(domainName);
        assertNotNull(domain);
        domain.setRemark("test");
        domainService.save(domain, new TestOpInfo("nichandle"));
        SearchResult<HistoricalObject<Domain>> newHistory = findHistory(domainName);
        Assert.assertTrue(history.getResults().size() < newHistory.getResults().size(), "New record should be created");
    }

    @Test
    public void lockingAndUnlockingTest() throws Exception {
        AuthenticatedUser user = getAuthenticatedUser();
        String domainName = "directDomain.ie";
        Domain d = domainDAO.get(domainName);
        Assert.assertNull(d.getLockingDate(), "Domain should start with null locking date");
        Assert.assertNull(d.getLockingRenewalDate(), "Domain should start with null locking renewal date");
        Assert.assertFalse(d.getDsmState().getLocked(), "Domain should be unlocked");

        final Date today = new Date();
        domainService.lock(user, domainName, new TestOpInfo("IDL2-IEDR", "lock"));
        d = domainDAO.get(domainName);
        Assert.assertTrue(DateUtils.isSameDay(d.getLockingDate(), today), "Domain's locking date should be today");
        Assert.assertTrue(DateUtils.isSameDay(d.getLockingRenewalDate(), today),
                "Domain's locking renewal date should be today");
        Assert.assertTrue(d.getDsmState().getLocked(), "Domain should be locked");

        domainService.unlock(user, domainName, new TestOpInfo("IDL2-IEDR", "unlock"), false);
        d = domainDAO.get(domainName);
        Assert.assertTrue(DateUtils.isSameDay(d.getLockingDate(), today), "Domain's locking date should still be today");
        Assert.assertTrue(DateUtils.isSameDay(d.getLockingRenewalDate(), today),
                "Domain's locking renewal date should still be today");
        Assert.assertFalse(d.getDsmState().getLocked(), "Domain should be unlocked");

        domainService.lock(user, domainName, new TestOpInfo("IDL2-IEDR", "lock"));
        d = domainDAO.get(domainName);
        Assert.assertTrue(DateUtils.isSameDay(d.getLockingDate(), today), "Domain's locking date should be today");
        Assert.assertTrue(DateUtils.isSameDay(d.getLockingRenewalDate(), today),
                "Domain's locking renewal date should be today");
        Assert.assertTrue(d.getDsmState().getLocked(), "Domain should be locked");

        domainService.unlock(user, domainName, new TestOpInfo("IDL2-IEDR", "unlock"), true);
        d = domainDAO.get(domainName);
        Assert.assertNull(d.getLockingDate(), "Domain's locking date should be set to NULL");
        Assert.assertNull(d.getLockingRenewalDate(), "Domain's locking renewal date should be set to NULL");
        Assert.assertFalse(d.getDsmState().getLocked(), "Domain should be unlocked");
    }

    @Test(expectedExceptions = DomainLockedException.class)
    public void saveLockedDomain() throws Exception {
        Domain domain = domainDAO.get("payDomainLocked.ie");
        assertNotNull(domain);
        Assert.assertTrue(domain.getDsmState().getLocked());
        domainService.save(domain, new TestOpInfo("nichandle"));
    }

    @Test(expectedExceptions = SellRequestExistsException.class)
    public void saveDomainWithSellRequest() throws Exception {
        Domain domain = domainDAO.get("webwebweb.ie");
        assertNotNull(domain);
        Assert.assertEquals(domain.getDsmState().getSecondaryMarketStatus(), SecondaryMarketStatus.SellRequestRegistered);
        domainService.save(domain, new TestOpInfo("nichandle"));
    }

    @Test
    public void rollLockingServiceDates() throws Exception {
        AuthenticatedUser user = getAuthenticatedUser();
        String domainName = "directdomain.ie";
        Domain d = domainDAO.get(domainName);
        Assert.assertNull(d.getLockingDate(), "Domain should start with null locking date");
        Assert.assertNull(d.getLockingRenewalDate(), "Domain should start with null locking renewal date");
        Assert.assertFalse(d.getDsmState().getLocked(), "Domain should be unlocked");

        final Date today = new Date();
        domainService.lock(user, domainName, new TestOpInfo("IDL2-IEDR", "lock"));
        d = domainDAO.get(domainName);
        Assert.assertTrue(DateUtils.isSameDay(d.getLockingDate(), today), "Domain's locking date should be today");
        Assert.assertTrue(DateUtils.isSameDay(d.getLockingRenewalDate(), today),
                "Domain's locking renewal date should be today");
        Assert.assertTrue(d.getDsmState().getLocked(), "Domain should locked");

        Date newLockingRenewalDate = DateUtils.addYears(today, 1);
        domainService.rollLockRenewalDates(Collections.singletonMap(domainName, newLockingRenewalDate), new TestOpInfo(
                "IDL2-IEDR", "roll"));
        d = domainDAO.get(domainName);
        Assert.assertTrue(DateUtils.isSameDay(d.getLockingDate(), today), "Domain's locking date should stay at today");
        Assert.assertTrue(DateUtils.isSameDay(d.getLockingRenewalDate(), newLockingRenewalDate),
                "Domain's locking renewal date should be rolled by one year");
    }

    @Test(expectedExceptions = AuthcodeGenerationDomainStateException.class)
    public void requestAuthCodeOfLockedDomain() throws Exception {
        AuthenticatedUser user = getAuthenticatedUser();
        domainService.getOrCreateAuthCode("payDomainLocked.ie", new OpInfo(user));
    }

    @Test(expectedExceptions = AuthcodeGenerationDomainStateException.class)
    public void requestAuthCodeOfDomainWithSellRequest() throws Exception {
        AuthenticatedUser user = getAuthenticatedUser();
        domainService.getOrCreateAuthCode("webwebweb.ie", new OpInfo(user));
    }

    @Test
    public void domainLockingClearsAuthCode() throws Exception {
        AuthenticatedUser user = getAuthenticatedUser();
        String domainName = "directDomain.ie";
        String authcode = domainService.getOrCreateAuthCode(domainName, new OpInfo(user)).getAuthCode();
        Domain domain = domainDAO.get(domainName);
        assertNotNull(authcode);
        assertEquals(domain.getAuthCode(), authcode);
        domainService.lock(user, domainName, new TestOpInfo(user.getUsername(), "Remark"));
        domain = domainDAO.get(domainName);
        assertTrue(domain.getDsmState().getLocked());
        assertNull(domain.getAuthCode());
    }

    @Test(expectedExceptions = DomainModificationPendingException.class)
    public void domainLockingWithModificationTicket() throws Exception {
        AuthenticatedUser user = getAuthenticatedUser();
        domainService.lock(user, "castlebargolfclub.ie", new TestOpInfo(user.getUsername(), "Remark"));
    }

    @Test(expectedExceptions = DomainTransferPendingException.class)
    public void domainLockingWithTransferTicket() throws Exception {
        AuthenticatedUser user = getAuthenticatedUser();
        domainService.lock(user, "thedomain-668.ie", new TestOpInfo(user.getUsername(), "Remark"));
    }

    @Test
    public void getAuthCodeFromPortalCheckChangeNh() throws Exception {
        String domainName = "directDomain.ie";
        String emailAddress = "someEmailAddress@iedr.ie";
        List<HistoricalObject<Domain>> initialResults = findHistory(domainName).getResults();
        assertEquals(initialResults.size(), 1);
        OpInfo opInfo = TestOpInfo.DEFAULT;

        domainService.sendAuthCodeFromPortal(domainName, emailAddress, opInfo);

        List<HistoricalObject<Domain>> results = findHistory(domainName).getResults();
        assertEquals(results.size(), 2);
        HistoricalObject<Domain> histDomain = results.get(1);
        assertFalse(Validator.isEmpty(histDomain.getChangedBy()));
        assertEquals(histDomain.getChangedBy(), opInfo.getActorName());
    }

    @Test(expectedExceptions = DomainHolderTooLongException.class)
    public void updateDomainHolderTooLong() throws Exception {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        char[] charArray = new char[256];
        Arrays.fill(charArray, 'A');
        domain.setHolder(new String(charArray));
        domainService.save(domain, new TestOpInfo("opinfo"));
    }

    @Test(expectedExceptions = ClassDoesNotMatchCategoryException.class)
    public void updateDomainClassDontMatchCategory() throws Exception {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        domain.setHolderClass(entityService.getClass(2L));
        domain.setHolderCategory(entityService.getCategory(1L));
        domainService.save(domain, new TestOpInfo("opinfo"));
    }

    @Test(expectedExceptions = TooFewContactsException.class)
    public void updateDomainNoAdminContacts() throws Exception {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        domain.setAdminContacts(Collections.EMPTY_LIST);
        domainService.save(domain, new TestOpInfo("opinfo"));
    }

    @Test(expectedExceptions = TooFewContactsException.class)
    public void updateDomainNoTechContacts() throws Exception {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        domain.setTechContacts(Collections.EMPTY_LIST);
        domainService.save(domain, new TestOpInfo("opinfo"));
    }

    @Test(expectedExceptions = TooFewContactsException.class)
    public void updateDomainNoBillingContacts() throws Exception {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        domain.setBillingContacts(Collections.EMPTY_LIST);
        domainService.save(domain, new TestOpInfo("opinfo"));
    }

    @Test(expectedExceptions = TooManyContactsException.class)
    public void updateDomainThreeAdminContacts() throws Exception {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        Contact contact1 = contactDAO.get("ABC718-IEDR");
        Contact contact2 = contactDAO.get("AHD731-IEDR");
        Contact contact3 = contactDAO.get("AHF747-IEDR");
        domain.setAdminContacts(Arrays.asList(contact1, contact2, contact3));
        domainService.save(domain, new TestOpInfo("opinfo"));
    }

    @Test(expectedExceptions = DuplicatedContactException.class)
    public void updateDomainDuplicateAdminContact() throws Exception {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        Contact contact = contactDAO.get("ABC718-IEDR");
        domain.setAdminContacts(Arrays.asList(contact, contact));
        domainService.save(domain, new TestOpInfo("opinfo"));
    }

    @Test(expectedExceptions = NicHandleIncorrectForAccountException.class)
    public void updateDomainAdminContactOfAnotherAccount() throws Exception {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        Contact contact = contactDAO.get("AA11-IEDR");
        domain.setAdminContacts(Arrays.asList(contact, contact));
        domainService.save(domain, new TestOpInfo("opinfo"));
    }

    @Test
    public void updateDomainSecondAdminContactNull() throws Exception {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        Contact contact = contactDAO.get("ABC718-IEDR");
        domain.setAdminContacts(Arrays.asList(contact, null));
        domainService.save(domain, new TestOpInfo("opinfo"));
        assertEquals(contact.getNicHandle(), domain.getFirstAdminContactNic());
        assertNull(domain.getSecondAdminContactNic());
    }

    @Test(expectedExceptions = TooFewNameserversException.class)
    public void updateDomainOneNameserver() throws Exception {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        domain.setNameservers(Arrays.asList(new Nameserver("ns1.ie", null, null)));
        domainService.save(domain, new TestOpInfo("opinfo"));
    }

    @Test(expectedExceptions = TooManyNameserversException.class)
    public void updateDomainTooManyNameservers() throws Exception {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        domain.setNameservers(Arrays.asList(new Nameserver("ns1.ie", null, null), new Nameserver("ns2.ie", null, null),
            new Nameserver("ns3.ie", null, null), new Nameserver("ns4.ie", null, null), new Nameserver("ns5.ie", null, null),
            new Nameserver("ns6.ie", null, null), new Nameserver("ns7.ie", null, null), new Nameserver("ns8.ie", null, null)));
        domainService.save(domain, new TestOpInfo("opinfo"));
    }

    @Test(expectedExceptions = DuplicatedNameserverException.class)
    public void updateDomainDuplicateNameservers() throws Exception {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        domain.setNameservers(Arrays.asList(new Nameserver("ns1.ie", null, null), new Nameserver("ns1.ie", null, null)));
        domainService.save(domain, new TestOpInfo("opinfo"));
    }

    @Test(expectedExceptions = NameserverNameSyntaxException.class)
    public void updateDomainIncorrectNameserverName() throws Exception {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        domain.setNameservers(Arrays.asList(new Nameserver("incorrect$ns1.ie", null, null),
                new Nameserver("incorrect$ns2.ie", null, null)));
        domainService.save(domain, new TestOpInfo("opinfo"));
    }

    @Test(expectedExceptions = InvalidIPv4AddressException.class)
    public void updateDomainIncorrectNameserverIPv4() throws Exception {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        domain.setNameservers(Arrays.asList(new Nameserver("ns1.castlebargolfclub.ie", "10.10.1.256", null),
                new Nameserver("ns2.ie", null, null)));
        domainService.save(domain, new TestOpInfo("opinfo"));
    }

    @Test(expectedExceptions = InvalidIPv6AddressException.class)
    public void updateDomainIncorrectNameserverIPv6() throws Exception {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        domain.setNameservers(Arrays.asList(new Nameserver("ns1.castlebargolfclub.ie", null, ":"),
                new Nameserver("ns2.ie", null, null)));
        domainService.save(domain, new TestOpInfo("opinfo"));
    }

    @Test(expectedExceptions = GlueNotAllowedException.class)
    public void updateDomainNameserverGlueIpv4Redundant() throws Exception {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        domain.setNameservers(Arrays.asList(new Nameserver("ns1.ie", "10.10.1.1", null),
                new Nameserver("ns2.ie", null, null)));
        domainService.save(domain, new TestOpInfo("opinfo"));
    }

    @Test(expectedExceptions = GlueNotAllowedException.class)
    public void updateDomainNameserverGlueIpv6Redundant() throws Exception {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        domain.setNameservers(Arrays.asList(new Nameserver("ns1.ie", null, "::1"),
                new Nameserver("ns2.ie", null, null)));
        domainService.save(domain, new TestOpInfo("opinfo"));
    }

    @Test(expectedExceptions = GlueRequiredException.class)
    public void updateDomainNameserverGlueMissing() throws Exception {
        Domain domain = domainDAO.get("castlebargolfclub.ie");
        domain.setNameservers(Arrays.asList(new Nameserver("ns1.castlebargolfclub.ie", null, null),
                new Nameserver("ns2.ie", null, null)));
        domainService.save(domain, new TestOpInfo("opinfo"));
    }

    @Test
    public void createNewDomain() {
        Date date = DateUtils.setMilliseconds(new Date(), 999);
        NewDomain domain = getNewDomainDetails(date);
        Domain expectedDomain = DomainDAOTest.getExpectedCreatedDomainDetails(date);
        expectedDomain.setLockingDate(null);
        expectedDomain.setLockingRenewalDate(null);
        domainService.create(domain, TestOpInfo.DEFAULT);
        Domain dbDomain = domainDAO.get("createdDomain.ie");

        DomainsHelper.compareNewDomains(dbDomain, expectedDomain);
    }

    @Test
    public void updateDomainAndHistory() {
        Date now = new Date();
        String domainName = "castlebargolfclub.ie";
        Domain domain = domainDAO.get(domainName);
        domain.setRenewalDate(now);
        domainService.updateDomainAndHistory(domain, TestOpInfo.DEFAULT);
        Domain dbDomain = domainDAO.get(domainName);
        assertEquals(DateUtils.truncate(now, Calendar.DATE), dbDomain.getRenewalDate());
        HistoricalObject<Domain> histDomain = findLastHistoricalEntry(domainName);
        DateTestHelper.assertEqualDates(histDomain.getChangeDate(), domain.getChangeDate(), Calendar.SECOND);
        DomainsHelper.compareDomains(dbDomain, histDomain.getObject());
    }

    @Test
    public void updateDomainAndHistoryWithDsmState() {
        int dsmState = 18;
        String domainName = "castlebargolfclub.ie";
        Domain domain = domainDAO.get(domainName);
        domainService.updateDomainAndHistory(domain, dsmState, TestOpInfo.DEFAULT);
        Domain dbDomain = domainDAO.get(domainName);
        assertEquals(dsmState, dbDomain.getDsmState().getId());
        HistoricalObject<Domain> histDomain = findLastHistoricalEntry(domainName);
        DateTestHelper.assertEqualDates(histDomain.getChangeDate(), domain.getChangeDate(), Calendar.SECOND);
        DomainsHelper.compareDomains(dbDomain, histDomain.getObject());
    }

    @Test
    public void deleteDomain() {
        String domainName = "sec-mrkt-domain-1.ie";
        BuyRequestSearchCriteria buyRequestCriteria = new BuyRequestSearchCriteria();
        buyRequestCriteria.setDomainName(domainName);
        SellRequestSearchCriteria sellRequestCriteria = new SellRequestSearchCriteria();
        sellRequestCriteria.setDomainName(domainName);
        assertNotNull(domainDAO.get(domainName));
        assertTrue(buyRequestDAO.exists(buyRequestCriteria));
        assertTrue(sellRequestDAO.exists(sellRequestCriteria));
        domainService.delete(getAuthenticatedUser(), domainDAO.get(domainName), TestOpInfo.DEFAULT);
        assertNull(domainDAO.get(domainName));
        assertFalse(buyRequestDAO.exists(buyRequestCriteria));
        assertFalse(sellRequestDAO.exists(sellRequestCriteria));
    }

    private NewDomain getNewDomainDetails(Date date) {
        Domain domain = DomainDAOTest.getDomainToCreateDetails(date);
        List<String> adminContacts = contactsToStrings(domain.getAdminContacts());
        List<String> techContacts = contactsToStrings(domain.getTechContacts());
        List<String> billingContacts = contactsToStrings(domain.getBillingContacts());
        NewDomain newDomain = new NewDomain(domain.getName(), domain.getHolder(), domain.getHolderClass(),
                domain.getHolderCategory(), domain.getHolderSubcategory(), domain.getCreator().getNicHandle(),
                domain.getResellerAccount().getId(), domain.getRemark(), techContacts, billingContacts, adminContacts,
                domain.getNameservers(), Period.fromYears(1));
        return newDomain;
    }

    private List<String> contactsToStrings(List<Contact> contacts) {
        return Lists.transform(contacts, new Function<Contact, String>() {
            @Override
            public String apply(Contact contact) {
                return contact.getNicHandle();
            }
        });
    }

    private HistoricalObject<Domain> findLastHistoricalEntry(String domainName) {
        HistoricalDomainSearchCriteria criteria = new HistoricalDomainSearchCriteria();
        criteria.setDomainName(domainName);
        List<HistoricalObject<Domain>> results = historicalDomainDAO.find(criteria).getResults();
        return results.get(results.size() - 1);
    }

    private AuthenticatedUser getAuthenticatedUser() {
        return new AuthenticatedUser() {
            @Override
            public String getUsername() {
                return "IDL2-IEDR";
            }

            @Override
            public String getSuperUserName() {
                return null;
            }

            @Override
            public String getAuthenticationToken() {
                return null;
            }
        };
    }

}
