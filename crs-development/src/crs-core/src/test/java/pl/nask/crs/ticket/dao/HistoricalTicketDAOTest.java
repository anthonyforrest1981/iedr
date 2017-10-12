package pl.nask.crs.ticket.dao;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.ticket.AbstractContextAwareTest;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.TicketHelper;
import pl.nask.crs.ticket.operation.IpFieldChange;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;
import pl.nask.crs.ticket.search.HistoricTicketSearchCriteria;

import static org.testng.Assert.assertEquals;
import static pl.nask.crs.ticket.TicketHelper.compareHistoricalTickets;

@SuppressWarnings("nullness")
public class HistoricalTicketDAOTest extends AbstractContextAwareTest {

    @Resource
    private HistoricalTicketDAO historicalTicketDAO;

    @Resource
    private TicketDAO ticketDAO;

    @Test
    public void testGetHistoryPresent() {
        List<HistoricalObject<Ticket>> history = historicalTicketDAO.findAll(259573L);
        assertEquals(history.size(), 3);
    }

    @Test
    public void testGetHistoryEmpty() {
        List<HistoricalObject<Ticket>> history = historicalTicketDAO.findAll(999999L);
        assertEquals(history.size(), 0);
    }

    @Test
    public void testCreate() {
        final long ID = 256745;
        Date changeDate = new Date();
        final String nicHandle = "NicHandle";
        Ticket origTicket = ticketDAO.get(ID);
        long changeId = historicalTicketDAO.create(origTicket, changeDate, nicHandle);
        List<HistoricalObject<Ticket>> history = historicalTicketDAO.findAll(ID);
        assertEquals(history.size(), 2);

        final HistoricalObject<Ticket> ticketHistoricalObject = history.get(0);
        assertEquals(ticketHistoricalObject.getChangeId(), changeId);
        assertEquals(ticketHistoricalObject.getChangeDate(), DateUtils.truncate(changeDate, Calendar.SECOND),
                "Historical change date should be the one passed to DAO");
        assertEquals(ticketHistoricalObject.getChangedBy(), nicHandle, "ChangeBy should be correctly set");

        final Ticket ticket = ticketHistoricalObject.getObject();
        compareHistoricalTickets(ticket, origTicket);
    }

    @Test
    public void testCreateCheckNameservers() {
        long id = 256745;
        Ticket origTicket = ticketDAO.get(id);
        String domainName = origTicket.getOperation().getDomainNameField().getNewValue();
        List<NameserverChange> nameserversList = Arrays.asList(nsChange("ns.dns.ie", null, null),
                nsChange("ns." + domainName, "10.10.1.1", "fd62:3715:3fba::/48"));
        origTicket.getOperation().setNameserversField(nameserversList);

        long changeId = historicalTicketDAO.create(origTicket, new Date(), TestOpInfo.DEFAULT.getActorName());
        List<HistoricalObject<Ticket>> history = historicalTicketDAO.findAll(id);
        final HistoricalObject<Ticket> ticketHistoricalObject = history.get(0);
        assertEquals(ticketHistoricalObject.getChangeId(), changeId);

        TicketHelper.compareNameserverChanges(ticketHistoricalObject.getObject().getOperation().getNameserversField(),
                nameserversList);
    }

    @Test
    public void testLimitedFindByDomainName() {
        HistoricTicketSearchCriteria criteria = new HistoricTicketSearchCriteria();
        criteria.setDomainName("neway");
        LimitedSearchResult<HistoricalObject<Ticket>> found = historicalTicketDAO.find(criteria, 0, 10, null);
        assertEquals(found.getResults().size(), 10);
        assertEquals(found.getTotalResults(), 16);
    }

    @Test
    public void testLimitedFindByAccount() {
        HistoricTicketSearchCriteria criteria = new HistoricTicketSearchCriteria();
        criteria.setAccountId(122L);
        LimitedSearchResult<HistoricalObject<Ticket>> found = historicalTicketDAO.find(criteria, 0, 10, null);
        assertEquals(found.getResults().size(), 10);
        assertEquals(found.getTotalResults(), 193);
    }

    @Test
    public void testLimitedFindByDomainHolder() {
        HistoricTicketSearchCriteria criteria = new HistoricTicketSearchCriteria();
        criteria.setDomainHolder("Castlebar");
        LimitedSearchResult<HistoricalObject<Ticket>> found = historicalTicketDAO.find(criteria, 0, 10, null);
        assertEquals(found.getResults().size(), 10);
        assertEquals(found.getTotalResults(), 49);
    }

    @Test
    public void testLimitedFindAll() {
        HistoricTicketSearchCriteria criteria = new HistoricTicketSearchCriteria();
        LimitedSearchResult<HistoricalObject<Ticket>> found = historicalTicketDAO.find(criteria, 0, 10, null);
        assertEquals(found.getResults().size(), 10);
        assertEquals(found.getTotalResults(), 1403);
    }

    @Test
    public void testFindAll() {
        HistoricTicketSearchCriteria criteria = new HistoricTicketSearchCriteria();
        SearchResult<HistoricalObject<Ticket>> found = historicalTicketDAO.find(criteria);
        assertEquals(found.getResults().size(), 1403);
    }

    @Test
    public void testLooseUtf8Validation() {
        HistoricTicketSearchCriteria criteria = new HistoricTicketSearchCriteria();
        criteria.setTicketId(500000l);
        SearchResult<HistoricalObject<Ticket>> results = historicalTicketDAO.find(criteria);
        assertEquals(results.getResults().size(), 1);
        HistoricalObject<Ticket> histTicket = results.getResults().get(0);
        assertEquals(histTicket.getObject().getOperation().getDomainNameField().getNewValue(),
                "badutf8domainname\u0001.ie");
    }

    @Test
    public void testCount() {
        HistoricTicketSearchCriteria criteria = new HistoricTicketSearchCriteria();
        criteria.setDomainHolder("Castlebar");
        assertEquals(49, historicalTicketDAO.count(criteria));
    }

    @Test
    public void testExists() {
        HistoricTicketSearchCriteria criteria = new HistoricTicketSearchCriteria();
        criteria.setDomainHolder("Castlebar");
        Assert.assertTrue(historicalTicketDAO.exists(criteria));
    }

    @Test
    public void testNotExists() {
        HistoricTicketSearchCriteria criteria = new HistoricTicketSearchCriteria();
        criteria.setDomainHolder("Hat");
        Assert.assertFalse(historicalTicketDAO.exists(criteria));
    }

    private NameserverChange nsChange(String name, String ipv4, String ipv6) {
        return new NameserverChange(new SimpleDomainFieldChange<>(null, name),
                new IpFieldChange(null, ipv4), new IpFieldChange(null, ipv6));
    }

}
