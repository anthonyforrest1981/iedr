package pl.nask.crs.ticket.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.entities.dao.EntityCategoryDAO;
import pl.nask.crs.entities.dao.EntityClassDAO;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.reports.ReportTimeGranulation;
import pl.nask.crs.reports.TicketReport;
import pl.nask.crs.reports.search.ReportsSearchCriteria;
import pl.nask.crs.ticket.AbstractContextAwareTest;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.ibatis.InternalHistoricalTicketIBatisDAO;
import pl.nask.crs.ticket.dao.ibatis.InternalTicketIBatisDAO;
import pl.nask.crs.ticket.dao.ibatis.TicketReportsIBatisDAO;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalHistoricalTicket;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalTicket;
import pl.nask.crs.ticket.response.TicketResponse;
import pl.nask.crs.ticket.search.HistoricTicketSearchCriteria;
import pl.nask.crs.ticket.search.TicketSearchCriteria;

@SuppressWarnings("nullness")
public class InternalTicketUTF8DAOTest extends AbstractContextAwareTest {

    @Autowired
    InternalTicketIBatisDAO dao;

    @Autowired
    InternalHistoricalTicketIBatisDAO historicalDao;

    @Autowired
    TicketResponseDAO responseDao;

    @Autowired TicketReportsIBatisDAO reportDao;

    @Autowired
    HistoricalTicketDAO historicDao;

    @Autowired
    EntityClassDAO entityClassDAO;

    @Autowired
    EntityCategoryDAO entityCategoryDAO;

    @Test
    public void testGetUnnormalizedTicket() {
        InternalTicket t = dao.get(400000l);
        Assert.assertEquals(t.getDomainName(), "unnormalizeddomainname\u00E4.ie");
        Assert.assertEquals(t.getDomainHolder(), "\u00DCser");
        Assert.assertEquals(t.getDomainHolderClass().getName(), "Unnormalized Cl\u00E3ss");
        Assert.assertEquals(t.getDomainHolderCategory().getName(), "Unnormalized Cate\u01F5\u014Dry");
        Assert.assertEquals(t.getRequestersRemark(), "Sole p\u00E4le remark");
        Assert.assertEquals(t.getHostmastersRemark(), "Sole p\u00E4le h remark");
        Assert.assertEquals(t.getCharityCode(), "char\u1EC9t\u00FCcode");
        Assert.assertEquals(t.getCreatorNicHandle(), "IDL2-I\u00CBDP");
        Assert.assertEquals(t.getBillingContactNicHandle(), "IDL2-I\u00CBDP");
        Assert.assertEquals(t.getBillingContactName(), "NicHandl\u00EB name");
        Assert.assertEquals(t.getBillingContactCompanyName(), "NicHandl\u00EB company");
        Assert.assertEquals(t.getBillingContactCountry(), "Unnormalized côuṅtry");
        Assert.assertEquals(t.getBillingContactEmail(), "NicHandl\u00EB@email.ie");
        Assert.assertEquals(t.getTechContactNicHandle(), "IDL2-I\u00CBDP");
        Assert.assertEquals(t.getAdminContact1NicHandle(), "IDL2-I\u00CBDP");
        Assert.assertEquals(t.getCheckedOutToNicHandle(), "IDL2-I\u00CBDP");
        Assert.assertEquals(t.getNameservers().size(), 2);
        Assert.assertEquals(t.getNameservers().get(0).getName(), "ns1.domainname\u00E4.ie");
        Assert.assertEquals(t.getNameservers().get(1).getName(), "ns2.domainname\u00E4.ie");
    }

    @Test
    public void testFindWithUnnormalizedCriteria() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setBillNicHandle("IDE\u03082-IEDR");
        criteria.setCategoryName("Normalized Cate\u01f5\u014dry");
        criteria.setClassName("Normalized Cl\u00e3ss");
        criteria.setCreatorNh("IDE\u03082-IEDR");
        criteria.setDomainHolder("U\u0308ser");
        criteria.setDomainName("normalizeddomainnamea\u0308.ie");
        criteria.setExactDomainName("normalizeddomainnamea\u0308.ie");
        criteria.setNicHandle("IDE\u03082-IEDR");
        LimitedSearchResult<InternalTicket> result = dao.find((SearchCriteria) criteria, 0, 10);
        Assert.assertEquals(result.getTotalResults(), 1);
    }

    @Test
    public void testCreateTicketWithUnnormalizedStrings() {
        // To ease setup I will reuse an existing ticket to have fields pre-filled
        InternalTicket t = dao.get(400000l);
        t.setDomainName("de\u0308mo.ie");
        t.setCreatorNicHandle("IDE\u03082-IEDR");
        t.setAdminContact1NicHandle("IDE\u03082-IEDR");
        t.setBillingContactNicHandle("IDE\u03082-IEDR");
        t.setTechContactNicHandle("IDE\u03082-IEDR");
        t.setCharityCode("chari\u0309te\u0308");
        t.setCheckedOut(true);
        t.setCheckedOutToNicHandle("IDE\u03082-IEDR");
        t.setDomainHolder("Holde\u0308r");
        t.setHostmastersRemark("Re\u0308mark H");
        t.setRequestersRemark("Re\u0308mark R");
        t.setCreationDate(new Date());
        t.getNameservers().get(0).setName("ns1.de\u0308mo.ie");
        t.getNameservers().get(1).setName("ns2.de\u0308mo.ie");
        long id = dao.createTicket(t);
        InternalTicket dbTicket = dao.get(id);
        Assert.assertEquals(dbTicket.getDomainName(), "dëmo.ie");
        Assert.assertEquals(dbTicket.getCreatorNicHandle(), "IDË2-IEDR");
        Assert.assertEquals(dbTicket.getAdminContact1NicHandle(), "IDË2-IEDR");
        Assert.assertEquals(dbTicket.getBillingContactNicHandle(), "IDË2-IEDR");
        Assert.assertEquals(dbTicket.getTechContactNicHandle(), "IDË2-IEDR");
        Assert.assertEquals(dbTicket.getCharityCode(), "charỉtë");
        Assert.assertEquals(dbTicket.getCheckedOutToNicHandle(), "IDË2-IEDR");
        Assert.assertEquals(dbTicket.getHostmastersRemark(), "Rëmark H");
        Assert.assertEquals(dbTicket.getRequestersRemark(), "Rëmark R");
        Assert.assertEquals(dbTicket.getNameservers().size(), 0);
    }

    @Test
    public void testCreateHistoricalTicketWithUnnormalizedStrings() {
        HistoricTicketSearchCriteria criteria = new HistoricTicketSearchCriteria();
        criteria.setTicketId(400000l);
        List<InternalHistoricalTicket> results = historicalDao.find((SearchCriteria) criteria).getResults();
        InternalHistoricalTicket t = results.get(0);
        t.setDomainName("de\u0308mo.ie");
        t.setCreatorNicHandle("IDE\u03082-IEDR");
        t.setAdminContact1NicHandle("IDE\u03082-IEDR");
        t.setBillingContactNicHandle("IDE\u03082-IEDR");
        t.setTechContactNicHandle("IDE\u03082-IEDR");
        t.setCharityCode("chari\u0309te\u0308");
        t.setCheckedOut(true);
        t.setCheckedOutToNicHandle("IDE\u03082-IEDR");
        t.setDomainHolder("Holde\u0308r");
        t.setDomainHolderClass(entityClassDAO.get(1l));
        t.setDomainHolderCategory(entityCategoryDAO.getForClass(1l).get(0));
        t.setHostmastersRemark("Re\u0308mark H");
        t.setRequestersRemark("Re\u0308mark R");
        t.setCreationDate(new Date());
        t.getNameservers().get(0).setName("ns1.de\u0308mo.ie");
        t.getNameservers().get(1).setName("ns2.de\u0308mo.ie");
        t.setChangeDate(new Date());
        t.setHistChangeDate(t.getChangeDate());
        t.setChangedByNicHandle(TestOpInfo.DEFAULT.getActorName());
        dao.createTicket(t);
        historicalDao.create(t);
        criteria.setTicketId(t.getId());
        results = historicalDao.find((SearchCriteria) criteria).getResults();
        InternalHistoricalTicket dbTicket = results.get(0);
        Assert.assertEquals(dbTicket.getDomainName(), "dëmo.ie");
        Assert.assertEquals(dbTicket.getCreatorNicHandle(), "IDË2-IEDR");
        Assert.assertEquals(dbTicket.getAdminContact1NicHandle(), "IDË2-IEDR");
        Assert.assertEquals(dbTicket.getBillingContactNicHandle(), "IDË2-IEDR");
        Assert.assertEquals(dbTicket.getTechContactNicHandle(), "IDË2-IEDR");
        Assert.assertEquals(dbTicket.getCharityCode(), "charỉtë");
        Assert.assertEquals(dbTicket.getCheckedOutToNicHandle(), "IDË2-IEDR");
        Assert.assertEquals(dbTicket.getDomainHolder(), "Holdër");
        Assert.assertEquals(dbTicket.getHostmastersRemark(), "Rëmark H");
        Assert.assertEquals(dbTicket.getRequestersRemark(), "Rëmark R");
        Assert.assertEquals(dbTicket.getNameservers().size(), 2);
        Assert.assertEquals(dbTicket.getNameservers().get(0).getName(), "ns1.dëmo.ie");
        Assert.assertEquals(dbTicket.getNameservers().get(1).getName(), "ns2.dëmo.ie");
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testFailureOn4ByteCharacter() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setCategoryName("\uD83D\uDE0A");
        LimitedSearchResult<InternalTicket> result = dao.find((SearchCriteria) criteria, 0, 10);
    }

    @Test
    public void testTicketResponse() {
        TicketResponse response = responseDao.get(100l);
        Assert.assertEquals(response.getTitle(), "Tëst unnormalized response");
        Assert.assertEquals(response.getText(), "Tëst unnormalized text");
    }

    @Test
    public void testFindTicketResponse() {
        TicketResponse response = responseDao.get("Te\u0308st normalized response");
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getTitle(), "Tëst normalized response");
        Assert.assertEquals(response.getText(), "Tëst normalized text");
    }

    @Test
    public void testCreateTicketResponse() {
        TicketResponse newResponse = new TicketResponse("New te\u0308st response title", "New te\u0308st response text");
        responseDao.create(newResponse);
        TicketResponse dbResponse = responseDao.get("New tëst response title");
        Assert.assertNotNull(dbResponse);
        Assert.assertEquals(dbResponse.getTitle(), "New tëst response title");
        Assert.assertEquals(dbResponse.getText(), "New tëst response text");
    }

    @Test
    public void testTicketReportUnnormalizedCriteria() {
        ReportsSearchCriteria criteria = new ReportsSearchCriteria();
        criteria.setReportTimeGranulation(ReportTimeGranulation.YEAR);
        criteria.setHostmasterName("IDE\u03082-IEDR");
        LimitedSearchResult<TicketReport> result = reportDao.findTicketReports(criteria, 0, 10, null);
        Assert.assertEquals(result.getTotalResults(), 1);
        TicketReport r = result.getResults().get(0);
        Assert.assertEquals(r.getHostmasterName(), "IDË2-IEDR");
    }

    @Test
    public void testTicketReportUnnormalizedAndBadUtf8Result() {
        // First check what is the start date for the report
        List<HistoricalObject<Ticket>> ht = historicDao.findAll(400000);
        Date fromDate = ht.get(0).getObject().getChangeDate();
        ReportsSearchCriteria criteria = new ReportsSearchCriteria();
        criteria.setReportTimeGranulation(ReportTimeGranulation.YEAR);
        criteria.setFrom(DateUtils.addMinutes(fromDate, -5));
        criteria.setTo(DateUtils.addMinutes(fromDate, 5));
        LimitedSearchResult<TicketReport> result = reportDao.findTicketReports(criteria, 0, 10,
                Arrays.asList(new SortCriterion("hostmasterName", true)));
        Assert.assertEquals(result.getTotalResults(), 3);
        Assert.assertEquals(result.getResults().get(0).getHostmasterName(), "IDË2-IEDR");
        Assert.assertEquals(result.getResults().get(1).getHostmasterName(), "IDL2-IËDP");
        Assert.assertEquals(result.getResults().get(2).getHostmasterName(), "IDL2-IEDR\01");
    }

    @Test
    public void testHistoricalObjects() {
        List<HistoricalObject<Ticket>> ht = historicDao.findAll(400000);
        Assert.assertEquals(ht.size(), 1);
        HistoricalObject<Ticket> t = ht.get(0);
        Assert.assertEquals(t.getChangedBy(), "IDL2-IËDP");
    }
}
