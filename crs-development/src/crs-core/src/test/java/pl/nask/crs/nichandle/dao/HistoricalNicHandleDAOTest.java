package pl.nask.crs.nichandle.dao;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.jdbc.UncategorizedSQLException;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.County;
import pl.nask.crs.country.dao.CountryDAO;
import pl.nask.crs.country.dao.CountyDAO;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.AbstractContextAwareTest;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.NicHandleStatus;
import pl.nask.crs.nichandle.search.HistoricalNicHandleSearchCriteria;

import static org.testng.AssertJUnit.assertEquals;
import static pl.nask.crs.nichandle.testhelp.NicHandleTestHelp.compareHistoricalNHList;
import static pl.nask.crs.nichandle.testhelp.NicHandleTestHelp.compareHistoricalNicHandles;

public class HistoricalNicHandleDAOTest extends AbstractContextAwareTest {

    @Resource
    HistoricalNicHandleDAO historicalNicHandleDAO;
    @Resource
    NicHandleDAO nicHandleDAO;
    @Resource
    CountryDAO countryDAO;
    @Resource
    CountyDAO countyDAO;

    @Test
    public void findHistoricalNicHandlesByNicHandle() {
        HistoricalNicHandleSearchCriteria criteria = new HistoricalNicHandleSearchCriteria();
        criteria.setNicHandleId("ABD275-IEDR");
        SearchResult<HistoricalObject<NicHandle>> result = historicalNicHandleDAO.find(criteria);
        assertEquals(4, result.getResults().size());
        List<HistoricalObject<NicHandle>> actualNicHandles = result.getResults();
        List<HistoricalObject<NicHandle>> expectedNicHandles = createNHABD275();
        compareHistoricalNHList(expectedNicHandles, actualNicHandles);
    }

    @Test
    public void findHistoricalNicHandlesByNicHandleNotExists() {
        HistoricalNicHandleSearchCriteria criteria = new HistoricalNicHandleSearchCriteria();
        criteria.setNicHandleId("NOTEXISTS-IEDR");
        SearchResult<HistoricalObject<NicHandle>> result = historicalNicHandleDAO.find(criteria);
        assertEquals(0, result.getResults().size());
    }

    @Test
    public void createHistoricalNicHandle(){
        Date changeDate = new Date();
        NicHandle nicHandle = nicHandleDAO.get("AA11-IEDR");
        nicHandle.setChangeDate(changeDate);
        historicalNicHandleDAO.create(nicHandle, changeDate, "TEST-IEDR");
        HistoricalNicHandleSearchCriteria criteria = new HistoricalNicHandleSearchCriteria();
        criteria.setNicHandleId(nicHandle.getNicHandleId());
        SearchResult<HistoricalObject<NicHandle>> results = historicalNicHandleDAO.find(criteria);
        assertEquals(2, results.getResults().size());
        compareHistoricalNicHandles(results.getResults().get(0),
                createHistoricalNicHandleAA11(DateUtils.truncate(changeDate, Calendar.SECOND)));
    }

    @Test
    public void createHistoricalNicHandleWithTelecomAndPayment() {
        NicHandle nicHandle = nicHandleDAO.get("AAA906-IEDR");
        List<String> phones = Arrays.asList("11111", "22222");
        List<String> faxes = Arrays.asList("4444");
        nicHandle.setVatNo("ABCDEFGHI");
        nicHandle.setPhones(phones);
        nicHandle.setFaxes(faxes);
        Date date = DateUtils.setMilliseconds(new Date(), 999);
        historicalNicHandleDAO.create(nicHandle, date, "TEST-IEDR");
        HistoricalNicHandleSearchCriteria criteria = new HistoricalNicHandleSearchCriteria();
        criteria.setNicHandleId("AAA906-IEDR");
        SearchResult<HistoricalObject<NicHandle>> result = historicalNicHandleDAO.find(criteria);
        assertEquals(2, result.getResults().size());
        HistoricalObject<NicHandle> histNH = result.getResults().get(0);
        assertEquals("ABCDEFGHI", histNH.getObject().getVatNo());
        assertEquals(DateUtils.truncate(date, Calendar.SECOND), histNH.getChangeDate());
        assertEquals(2, histNH.getObject().getPhones().size());
        assertEquals(phones, histNH.getObject().getPhones());
        assertEquals(faxes, histNH.getObject().getFaxes());
    }

    @Test
    public void testFindUtf8Unnormalized() {
        HistoricalNicHandleSearchCriteria criteria = new HistoricalNicHandleSearchCriteria();
        criteria.setNicHandleId("YYY9-IEDR");
        LimitedSearchResult<HistoricalObject<NicHandle>> result = historicalNicHandleDAO.find(criteria, 0, 10);
        AssertJUnit.assertEquals(2, result.getTotalResults());
        HistoricalObject<NicHandle> historicalNicHandle = result.getResults().get(1);
        Assert.assertEquals(historicalNicHandle.getChangedBy(), "XY\u01788-IEDR");
        NicHandle nicHandle = historicalNicHandle.getObject();
        Assert.assertEquals(nicHandle.getName(), "\u00cc\u1e43\u1e03\u00fa\u0205l");
        Assert.assertEquals(nicHandle.getCompanyName(), "\u1e0c\u011c\u1e42\u0158 Ltd.");
        Assert.assertEquals(nicHandle.getAddress(), "\u0150\u0207\u1ee5\u0135\u0215 Street");
        Assert.assertEquals(nicHandle.getCounty().getName(), "Unnormalized co\u00fan\u0163y");
        Assert.assertEquals(nicHandle.getCountry().getName(), "Unnormalized c\u00f4u\u1e45try");
        Assert.assertEquals(nicHandle.getEmail(), "\u1eb9\u1e3f\u0105\u1ec9\u1e3d@server.xxx");
        Assert.assertEquals(nicHandle.getNicHandleRemark(), "\u016a\u0123\u1e43\u1e96\u01e9");
        Assert.assertEquals(nicHandle.getCreator(), "XY\u01788-IEDR");
    }

    @Test
    public void testLimitedFindUtf8Normalized() {
        String nicHandle = "XX\u01787-IEDR";
        HistoricalNicHandleSearchCriteria criteria = new HistoricalNicHandleSearchCriteria();
        criteria.setNicHandleId(nicHandle);
        LimitedSearchResult<HistoricalObject<NicHandle>> result = historicalNicHandleDAO.find(criteria, 0, 10);
        Assert.assertEquals(result.getTotalResults(), 2);
    }

    @Test
    public void testLooseUtf8Validation() {
        HistoricalNicHandleSearchCriteria criteria = new HistoricalNicHandleSearchCriteria();
        criteria.setNicHandleId("XX7-IEDR\01");
        SearchResult<HistoricalObject<NicHandle>> result = historicalNicHandleDAO.find(criteria);
        Assert.assertEquals(result.getResults().size(), 1);
        HistoricalObject<NicHandle> histNH = result.getResults().get(0);
        Assert.assertEquals(histNH.getObject().getName(), "Bad utf8 NH name\01");
    }

    private String getForbiddenUtf8String() {
        // an exaple of 4-byte UTF-8
        String str = new String(Character.toChars(Integer.parseInt("1034A", 16)));
        return str;
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testUpdateNicHandleWithForbiddenName() {
        NicHandle nicHandle = nicHandleDAO.get("YYY9-IEDR");
        nicHandle.setName(getForbiddenUtf8String());
        historicalNicHandleDAO.create(nicHandle, new Date(), "IDL2-IEDR");
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testUpdateNicHandleWithForbiddenCompanyName() {
        NicHandle nicHandle = nicHandleDAO.get("YYY9-IEDR");
        nicHandle.setCompanyName(getForbiddenUtf8String());
        historicalNicHandleDAO.create(nicHandle, new Date(), "IDL2-IEDR");
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testUpdateNicHandleWithForbiddenAddress() {
        NicHandle nicHandle = nicHandleDAO.get("YYY9-IEDR");
        nicHandle.setAddress(getForbiddenUtf8String());
        historicalNicHandleDAO.create(nicHandle, new Date(), "IDL2-IEDR");
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testUpdateNicHandleWithForbiddenEmail() {
        NicHandle nicHandle = nicHandleDAO.get("YYY9-IEDR");
        nicHandle.setEmail(getForbiddenUtf8String());
        historicalNicHandleDAO.create(nicHandle, new Date(), "IDL2-IEDR");
    }

    private HistoricalObject<NicHandle> createHistoricalNicHandleAA11(Date changeDate) {
        List<String> phones = Arrays.asList("22222");
        List<String> faxes = Arrays.asList("33333");
        Country country = countryDAO.get(121);
        County county = countyDAO.get(11);
        NicHandle nh = new NicHandle("AA11-IEDR", "Aine Andrews", new Account(103, "Kerna Communications", "KCB1-IEDR"),
                "Art Teachers Association", "NHAddress000001", phones, faxes, country, county,
                "NHEmail000001@server.xxx", NicHandleStatus.Active, new Date(1038783600000L), new Date(1038783600000L),
                changeDate, true, "updated bill-c from waived-iedr ", "AA11-IEDR",
                "GB747832695", "A", false);
        return new HistoricalObject<>(nh, changeDate, "TEST-IEDR");
    }

    private List<HistoricalObject<NicHandle>> createNHABD275() {
        ArrayList<HistoricalObject<NicHandle>> results = new ArrayList<>();
        results.add(createNHABDCurrent());
        results.add(createNHABD0());
        results.add(createNHABD1());
        results.add(createNHABD2());
        return results;
    }

    private HistoricalObject<NicHandle> createNHABDCurrent() {
        Country country = countryDAO.get(121);
        County county = countyDAO.get(11);
        NicHandle nh = new NicHandle("ABD275-IEDR", "Colette Murray", new Account(101, "Irish Domains", "IDL2-IEDR"),
                "Airtec Compressors Ltd.", "address", null, null, country, county, "email2@aa.a",
                NicHandleStatus.Active, new Date(1196895600000L), new Date(1196463600000L),
                new Date(1196932798000L), false, "e-mail spelling by PAUL-IEDR on 06/12/2007 09:19:57",
                "IDL2-IEDR", null, "A", false);
        return new HistoricalObject<>(nh, new Date(1196932707000L), "Console");
    }

    private HistoricalObject<NicHandle> createNHABD0() {
        List<String> phones = Arrays.asList("+353868572770", "+353868572771");
        Country country = countryDAO.get(121);
        County county = countyDAO.get(11);
        NicHandle nh = new NicHandle("ABD275-IEDR", "Colette Murray", new Account(101, "Irish Domains", "IDL2-IEDR"),
                "Airtec Compressors Ltd.", "address2", phones, null, country, county, "email1@aa.a",
                NicHandleStatus.Active, new Date(1196895600000L), new Date(1196463600000L),
                new Date(1196932707000L), false, "Edit - Via Reseller Console", "IDL2-IEDR", null, null, false);
        return new HistoricalObject<>(nh, new Date(1196673511000L), "PHOENIX");
    }

    private HistoricalObject<NicHandle> createNHABD1() {
        List<String> phones = Arrays.asList("021 431 2222");
        Country country = countryDAO.get(121);
        County county = countyDAO.get(11);
        NicHandle nh = new NicHandle("ABD275-IEDR", "Colette Murray", new Account(101, "Irish Domains", "IDL2-IEDR"),
                "Airtec Compressors Ltd.", "address2", phones, null, country, county, "email2@aa.a",
                NicHandleStatus.Active, new Date(1196895600000L), new Date(1196463600000L),
                new Date(1196932707000L), false, "re-activated by PAUL-IEDR on 06/12/2007 09:18:31", null, null,
                null, false);
        return new HistoricalObject<>(nh, new Date(1196500711000L), "PHOENIX");
    }

    private HistoricalObject<NicHandle> createNHABD2() {
        List<String> phones = Arrays.asList("33", "34", "55");
        List<String> faxes = Arrays.asList("11", "12");
        Country country = countryDAO.get(121);
        County county = countyDAO.get(11);
        NicHandle nh = new NicHandle("ABD275-IEDR", "Colette Murray", new Account(101, "Irish Domains", "IDL2-IEDR"),
                "Airtec Compressors Ltd.", "address3", phones, faxes, country, county, "email2@aa.a",
                NicHandleStatus.Active, new Date(1196895600000L), new Date(1196463600000L),
                new Date(1196932707000L), false, "remark", null, null, null, false);
        return new HistoricalObject<>(nh, new Date(1196463600000L), "IH4-IEDR");
    }

}
