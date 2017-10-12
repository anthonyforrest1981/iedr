package pl.nask.crs.nichandle.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.UncategorizedSQLException;
import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.contacts.ContactType;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.County;
import pl.nask.crs.country.dao.CountryDAO;
import pl.nask.crs.country.dao.CountyDAO;
import pl.nask.crs.nichandle.AbstractContextAwareTest;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.NicHandleStatus;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;

import static org.testng.AssertJUnit.*;

import static pl.nask.crs.nichandle.testhelp.NicHandleTestHelp.compareNicHandle;

public class NicHandleDAOTest extends AbstractContextAwareTest {

    @Resource
    NicHandleDAO nicHandleDAO;
    @Resource
    HistoricalNicHandleDAO historicalNicHandleDAO;

    @Resource
    CountryDAO countryDAO;

    @Resource
    CountyDAO countyDAO;

    //TODO: CRS-72
    //    @Test
    //    public void getNicHandle() {
    //        NicHandle actualNicHandle = nicHandleDAO.get("AA11-IEDR");
    //        NicHandle expectedNicHandle = createNHAA11();
    //        compareNicHandleNotExactDates(actualNicHandle, expectedNicHandle);
    //
    //        actualNicHandle = nicHandleDAO.get("AAE359-IEDR");
    //        expectedNicHandle = createNHAAE359();
    //        compareNicHandleNotExactDates(actualNicHandle, expectedNicHandle);
    //
    //    }

    @Test
    public void getNicHandleAccountFlags() {
        NicHandle actualNicHandle = nicHandleDAO.get("AAE359-IEDR");
        assertEquals("nic handle", "AAE359-IEDR", actualNicHandle.getNicHandleId());
        assertEquals("nicHandle.account.id", 1L, actualNicHandle.getAccount().getId());
        // Feature #2373
        assertTrue("agreement signed", actualNicHandle.getAccount().isAgreementSigned());
        assertTrue("ticket edit", actualNicHandle.getAccount().isTicketEdit());
    }

    @Test
    public void getNullNicHandle() {
        NicHandle actualNicHandle = nicHandleDAO.get("ABCD");
        NicHandle expectedNicHandle = null;
        compareNicHandle(actualNicHandle, expectedNicHandle);
    }

    @Test
    public void findAllNicHandles() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        SearchResult<NicHandle> result = nicHandleDAO.find(criteria);
        assertEquals(964, result.getResults().size());
    }

    //TODO: CRS-72
    //    @Test
    //    public void findNicHandlesByNicHandle() {
    //        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
    //        criteria.setNicHandleId("AAE");
    //        SearchResult<NicHandle> result = nicHandleDAO.find(criteria);
    //        List<NicHandle> actualNicHandles = result.getResults();
    //        List<NicHandle> expectedNicHandles = createNHAAE();
    //        compareNicHandleList(actualNicHandles, expectedNicHandles);
    //    }
    //
    //    @Test
    //    public void findNicHandlesByNicHandleWithLimit() {
    //        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
    //        criteria.setNicHandleId("AAE");
    //        LimitedSearchResult<NicHandle> result = nicHandleDAO.find(criteria, 0, 1);
    //        List<NicHandle> expectedNicHandles = createNHAAELimited();
    //        compareNicHandleList(result.getResults(), expectedNicHandles);
    //    }

    @Test
    public void findNicHandlesByName() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setName("Ad");
        SearchResult<NicHandle> result = nicHandleDAO.find(criteria);
        List<NicHandle> actualNicHandles = result.getResults();
        assertEquals(5, actualNicHandles.size());
    }

    @Test
    public void findNicHandlesByCompanyName() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setCompanyName("Ne");
        SearchResult<NicHandle> result = nicHandleDAO.find(criteria);
        List<NicHandle> actualNicHandles = result.getResults();
        assertEquals(6, actualNicHandles.size());
    }

    @Test
    public void findNicHandlesByEmail() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setEmail("NHEmail0008");
        SearchResult<NicHandle> result = nicHandleDAO.find(criteria);
        List<NicHandle> actualNicHandles = result.getResults();
        assertEquals(100, actualNicHandles.size());
    }

    @Test
    public void findNicHandlesByAccountNumber() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setAccountNumber(1l);
        SearchResult<NicHandle> result = nicHandleDAO.find(criteria);
        List<NicHandle> actualNicHandles = result.getResults();
        assertEquals(63, actualNicHandles.size());
    }

    @Test
    public void findNicHandlesByDomainName() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setDomainName("c");
        SearchResult<NicHandle> result = nicHandleDAO.find(criteria);
        List<NicHandle> actualNicHandles = result.getResults();
        assertEquals(6, actualNicHandles.size());
    }

    @Test
    public void findNicHandlesByContactType() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setContactType(ContactType.ADMIN);
        SearchResult<NicHandle> result = nicHandleDAO.find(criteria);
        List<NicHandle> actualNicHandles = result.getResults();
        assertEquals(37, actualNicHandles.size());
    }

    @Test
    public void findNicHandlesByAllCriteria() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setNicHandleId("A");
        criteria.setName("T");
        criteria.setCompanyName("I");
        criteria.setEmail("NHEmail");
        criteria.setAccountNumber(1l);
        criteria.setDomainName("a");
        criteria.setContactType(ContactType.ADMIN);
        SearchResult<NicHandle> result = nicHandleDAO.find(criteria);
        List<NicHandle> actualNicHandles = result.getResults();
        assertEquals(0, actualNicHandles.size());
    }

    private NicHandleSearchCriteria getAllCriteria() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setNicHandleId("A");
        criteria.setName("T");
        criteria.setCompanyName("I");
        criteria.setEmail("NHEmail");
        criteria.setAccountNumber(1l);
        return criteria;
    }

    @Test
    public void findNicHandlesByAllCriteriaWithLimit() {
        SearchResult<NicHandle> result = nicHandleDAO.find(getAllCriteria(), 0, 1);
        List<NicHandle> actualNicHandles = result.getResults();
        assertEquals(1, actualNicHandles.size());
    }

    @Test
    public void countNicHandlesByAllCriteria() {
        assertEquals(2, nicHandleDAO.count(getAllCriteria()));
    }

    @Test
    public void nicHandleExists() {
        assertTrue(nicHandleDAO.exists(getAllCriteria()));
    }

    @Test
    public void findNicHandleNotExists() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setNicHandleId("NOT-EXISTS-IEDR");
        SearchResult<NicHandle> result = nicHandleDAO.find(criteria);
        assertEquals(0, result.getResults().size());
    }

    @Test
    public void nicHandleNotExists() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setNicHandleId("NOT-EXISTS-IEDR");
        assertFalse(nicHandleDAO.exists(criteria));
    }

    @Test
    public void findByAddressTest() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setAddress("address");
        LimitedSearchResult<NicHandle> result = nicHandleDAO.find(criteria, 0, 100, null);
        assertEquals(4, result.getTotalResults());
        assertEquals(4, result.getResults().size());
    }

    @Test
    public void findByCountryTest() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setCountryName("USA");
        LimitedSearchResult<NicHandle> result = nicHandleDAO.find(criteria, 0, 100, null);
        assertEquals(18, result.getTotalResults());
        assertEquals(18, result.getResults().size());
    }

    @Test
    public void findByCountyTest() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setCountyName("Texas");
        LimitedSearchResult<NicHandle> result = nicHandleDAO.find(criteria, 0, 100, null);
        assertEquals(2, result.getTotalResults());
        assertEquals(2, result.getResults().size());
    }

    @Test
    public void findPhoneTest() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setPhone("333");
        LimitedSearchResult<NicHandle> result = nicHandleDAO.find(criteria, 0, 100, null);
        assertEquals(1, result.getTotalResults());
        assertEquals(1, result.getResults().size());
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testUpdate() {
        nicHandleDAO.update(createNHAA11());
    }

    @Test
    public void updateNicHandleUsingHistory() {
        List<String> faxes = Arrays.asList("fax 1");
        List<String> phones = Arrays.asList("phone 1");
        Country country = countryDAO.get(119);
        County county = countyDAO.get(10);
        NicHandle nicHandle = nicHandleDAO.get("AA11-IEDR");
        nicHandle.setAddress("address 1");
        nicHandle.setCompanyName("company name 1");
        nicHandle.setCountry(country);
        nicHandle.setCounty(county);
        nicHandle.setEmail("email 1");
        nicHandle.setFaxes(faxes);
        nicHandle.setName("name 1");
        nicHandle.setPhones(phones);
        nicHandle.setVatNo("new number");
        nicHandle.setVatCategory("B");
        nicHandleDAO.updateUsingHistory(historicalNicHandleDAO.create(nicHandle, new Date(), "IDL2-IEDR"));
        NicHandle actualNicHandle = nicHandleDAO.get("AA11-IEDR");
        compareNicHandle(actualNicHandle, nicHandle);
    }

    @Test
    public void updateNicHandleUsingHistory2() {
        List<String> faxes = Arrays.asList("fax 1", "fax 2", "fax 3");
        List<String> phones = Arrays.asList("phone 1", "phone 2", "phone 3");
        Country country = countryDAO.get(119);
        County county = countyDAO.get(10);
        NicHandle nicHandle = nicHandleDAO.get("AA11-IEDR");
        nicHandle.setAccount(new Account(104L));
        nicHandle.setAddress("address 1");
        nicHandle.setCompanyName("company name 1");
        nicHandle.setCountry(country);
        nicHandle.setCounty(county);
        nicHandle.setEmail("email 1");
        nicHandle.setFaxes(faxes);
        nicHandle.setName("name 1");
        nicHandle.setPhones(phones);
        nicHandle.setVatNo(null);
        nicHandleDAO.updateUsingHistory(historicalNicHandleDAO.create(nicHandle, new Date(), "IDL2-IEDR"));
        NicHandle actualNicHandle = nicHandleDAO.get("AA11-IEDR");
        compareNicHandle(actualNicHandle, nicHandle);
    }

    @Test
    public void insertOrUpdatePayment() {
        String NHwithoutVatData = "AHM506-IEDR";
        String NHwithVatData = "AA11-IEDR";
        NicHandle nhWithoutVat = nicHandleDAO.get(NHwithoutVatData);
        assertNull(nhWithoutVat.getVatNo());
        nhWithoutVat.setVatNo("new vat");
        nicHandleDAO.updateUsingHistory(historicalNicHandleDAO.create(nhWithoutVat, new Date(), "IDL2-IEDR"));
        NicHandle nhWithoutVatAfterUpdate = nicHandleDAO.get(NHwithoutVatData);
        assertEquals("new vat", nhWithoutVatAfterUpdate.getVatNo());

        NicHandle nhWithVat = nicHandleDAO.get(NHwithVatData);
        assertNotNull(nhWithVat.getVatNo());
        nhWithVat.setVatNo("new vat 2");
        nicHandleDAO.updateUsingHistory(historicalNicHandleDAO.create(nhWithVat, new Date(), "IDL2-IEDR"));
        NicHandle nhWithVatAfterUpdate = nicHandleDAO.get(NHwithVatData);
        assertEquals("new vat 2", nhWithVatAfterUpdate.getVatNo());

    }

    @Test
    public void insertNicHandle() {
        NicHandle nicHandle = createNHnew();
        nicHandleDAO.create(nicHandle);
        NicHandle dbNicHandle = nicHandleDAO.get("MYMY-IEDR");
        compareNicHandle(dbNicHandle, nicHandle);
    }

    //TODO: CRS-72
    //    @Test
    //    public void lockNicHandle() {
    //        NicHandle nicHandle = nicHandleDAO.lock("AA11-IEDR");
    //        compareNicHandleNotExactDates(nicHandle, createNHAA11());
    //    }

    @Test
    public void lockNicHandleNotExists() {
        assertFalse(nicHandleDAO.lock("NOT-EXISTS-IEDR"));
    }

    @Test
    public void testRemoveNicHandle() {
        String nh = "APIT1-IEDR";
        assertNotNull(nicHandleDAO.get(nh));
        nicHandleDAO.deleteById(nh);
        assertNull(nicHandleDAO.get(nh));
    }

    @Test
    public void testRemoveDeletedNicHandleShouldNotAffectOtherNichandles() {
        String nh = "APIT1-IEDR";
        assertNotNull(nicHandleDAO.get(nh));
        nicHandleDAO.deleteMarkedNichandles();
        assertNotNull(nicHandleDAO.get(nh));
    }

    @Test
    public void testRemoveDeletedNicHandle() {
        String nh = "APIT1-IEDR";
        NicHandle dto = nicHandleDAO.get(nh);
        dto.setStatus(NicHandleStatus.Deleted);
        nicHandleDAO.updateUsingHistory(historicalNicHandleDAO.create(dto, new Date(), "IDL2-IEDR"));
        nicHandleDAO.deleteMarkedNichandles();
        assertNull(nicHandleDAO.get(nh));
    }

    @Test
    public void testFindByCreator() throws Exception {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setCreator("AAU809-IEDR");
        SearchResult<NicHandle> searchResult = nicHandleDAO.find(criteria);
        assertEquals(2, searchResult.getResults().size());
    }

    @Test
    public void testFindNicHandleWithUnnormalizedName() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setNicHandleId("XY");
        SearchResult<NicHandle> nicHandles = nicHandleDAO.find(criteria);
        Assert.assertEquals(nicHandles.getResults().size(), 1);
        Assert.assertEquals(nicHandles.getResults().get(0).getNicHandleId(), "XY\u01788-IEDR");
    }

    @Test
    public void testGetNicHandleWithUnnormalizedDetails() {
        NicHandle nicHandle = nicHandleDAO.get("YYY9-IEDR");
        Assert.assertEquals(nicHandle.getName(), "\u00cc\u1e43\u1e03\u00fa\u0205l");
        Assert.assertEquals(nicHandle.getCompanyName(), "\u1e0c\u011c\u1e42\u0158 Ltd.");
        Assert.assertEquals(nicHandle.getAddress(), "\u0150\u0207\u1ee5\u0135\u0215 Street");
        Assert.assertEquals(nicHandle.getEmail(), "\u1eb9\u1e3f\u0105\u1ec9\u1e3d@server.xxx");
        Assert.assertEquals(nicHandle.getNicHandleRemark(), "\u1e99\u011f\u1e41\u021f\u1e35");
        Assert.assertEquals(nicHandle.getCreator(), "XY\u01788-IEDR");
        Assert.assertEquals(nicHandle.getPhonesAsString(), "Tele\u1e55h\u01a1ne");
        Assert.assertEquals(nicHandle.getFaxesAsString(), "Tele\u1e1fa\u1e8d");
    }

    @Test
    public void testFindUtf8NicHandleWithSearchCriteria() {
        String nicHandle = "XX\u01787-IEDR";
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setNicHandleId(nicHandle);
        SearchResult<NicHandle> result = nicHandleDAO.find(criteria);
        Assert.assertEquals(result.getResults().size(), 1);
        criteria = new NicHandleSearchCriteria();
        criteria.setName("\u00cc\u1e43\u1e03\u00fa\u0205l");
        result = nicHandleDAO.find(criteria);
        Assert.assertEquals(result.getResults().size(), 1);
        Assert.assertEquals(result.getResults().get(0).getNicHandleId(), nicHandle);
        criteria = new NicHandleSearchCriteria();
        criteria.setCompanyName("\u1e0c\u011c\u1e42\u0158 Ltd.");
        result = nicHandleDAO.find(criteria);
        Assert.assertEquals(result.getResults().size(), 1);
        Assert.assertEquals(result.getResults().get(0).getNicHandleId(), nicHandle);
        criteria = new NicHandleSearchCriteria();
        criteria.setEmail("f\u1e3f\u0105\u1ec9\u1e3d@server.xxx"); // originally: "\u1eb9\u1e3f\u0105\u1ec9\u1e3d@server.xxx", but it is considered equal to "email@server.xxx" while searching - should be restored after solving the problem
        result = nicHandleDAO.find(criteria);
        Assert.assertEquals(result.getResults().size(), 1);
        Assert.assertEquals(result.getResults().get(0).getNicHandleId(), nicHandle);
    }

    private String getForbiddenUtf8String() {
        // an exaple of 4-byte UTF-8
        String str = new String(Character.toChars(Integer.parseInt("1034A", 16)));
        return str;
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testCreateNicHandleWithForbiddenName() {
        NicHandle nicHandle = createNHAA11();
        nicHandle.setName(getForbiddenUtf8String());
        nicHandleDAO.create(nicHandle);
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testCreateNicHandleWithForbiddenCompanyName() {
        NicHandle nicHandle = createNHAA11();
        nicHandle.setCompanyName(getForbiddenUtf8String());
        nicHandleDAO.create(nicHandle);
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testCreateNicHandleWithForbiddenAddress() {
        NicHandle nicHandle = createNHAA11();
        nicHandle.setAddress(getForbiddenUtf8String());
        nicHandleDAO.create(nicHandle);
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testCreateNicHandleWithForbiddenEmail() {
        NicHandle nicHandle = createNHAA11();
        nicHandle.setEmail(getForbiddenUtf8String());
        nicHandleDAO.create(nicHandle);
    }

    private NicHandle createNHAA11() {
        List<String> faxes = Arrays.asList("33333");
        List<String> phones = Arrays.asList("22222");
        Country country = countryDAO.get(121);
        County county = countyDAO.get(11);
        return new NicHandle("AA11-IEDR", "Aine Andrews", new Account(103, "Kerna Communications", "KCB1-IEDR"),
                "Art Teachers Association", "NHAddress000001", phones, faxes, country, county,
                "NHEmail000001@server.xxx", NicHandleStatus.Active, new Date(1038783600000L), new Date(1038783600000L),
                new Date(1195038000000L), true, "updated bill-c from waived-iedr ", "AA11-IEDR", "GB747832695",
                "A", false);
    }

    private NicHandle createNHnew() {
        Country country = countryDAO.get(121);
        County county = countyDAO.get(8);
        return new NicHandle("MYMY-IEDR", "Blacknight.ie", new Account(237, "Blacknight.ie", "AAE553-IEDR"),
                "Blacknight Internet Solutions Ltd", "NHAddress000008", null, null, country, county,
                "NHEmail000008@server.xxx", NicHandleStatus.Active, new Date(1189116000000L), new Date(1166569200000L),
                new Date(1200650008000L), true, "AAE553-IEDR by CIARA-IEDR on 18/01/2008 09:53:28", "EMAIL-IEDR",
                null, null, false);
    }
}
