package pl.nask.crs.ticket.dao;

import java.util.*;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.EntitySubcategory;
import pl.nask.crs.entities.dao.EntityCategoryDAO;
import pl.nask.crs.entities.dao.EntityClassDAO;
import pl.nask.crs.entities.dao.EntitySubcategoryDAO;
import pl.nask.crs.ticket.*;
import pl.nask.crs.ticket.operation.*;
import pl.nask.crs.ticket.search.TicketSearchCriteria;

import static org.testng.AssertJUnit.*;
import static pl.nask.crs.commons.utils.DateUtils.endOfDay;

@SuppressWarnings("nullness")
public class TicketDAOTest extends AbstractContextAwareTest {

    @Autowired
    TicketDAO ticketDao;

    @Autowired
    HistoricalTicketDAO historicalTicketDao;

    @Autowired
    EntityClassDAO entityClassDao;

    @Autowired
    EntityCategoryDAO entityCategoryDao;

    @Autowired
    EntitySubcategoryDAO entitySubcategoryDao;

    @Test
    public void testGetNormal() {
        Ticket actual = ticketDao.get(258973L);
        assertNotNull(actual);
        assertNotNull(actual.getChangeDate());
        // EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    public void testGetWithAccountFlags() {
        Ticket actual = ticketDao.get(256744L);
        assertNotNull(actual);
        assertNotNull(actual.getChangeDate());
        assertNotNull("reseller", actual.getOperation().getResellerAccountField().getNewValue());
        assertEquals("reseller Id ", 1L, actual.getOperation().getResellerAccountField().getNewValue().getId());
        assertTrue("agreementSigned", actual.getOperation().getResellerAccountField().getNewValue().isAgreementSigned());
        assertTrue("ticketEdit", actual.getOperation().getResellerAccountField().getNewValue().isTicketEdit());
        // EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    public void testGetNonExisting() {
        Ticket actual = ticketDao.get(-1L);
        Assert.assertNull(actual, "Ticket");
    }

    @Test
    public void testLock() {
        assertTrue(ticketDao.lock(258973L));
        Ticket actual = ticketDao.get(258973L);
        assertNotNull(actual);
        // EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testUpdate() {
        ticketDao.update(ticketDao.get(258973L));
    }

    @Test
    public void testUpdateCheckOut() {
        Ticket existing = ticketDao.get(258973L);
        assertFalse(existing.isCheckedOut());
        assertNotNull(existing.getChangeDate());

        existing.checkOut(new Contact("IH4-IEDR"));
        ticketDao.updateUsingHistory(historicalTicketDao.create(existing, new Date(), TestOpInfo.DEFAULT.getActorName()));

        Ticket changed = ticketDao.get(258973L);
        assertTrue(changed.isCheckedOut());
        assertNotNull(changed.getChangeDate());
    }

    @Test
    public void testUpdateCheckIn() {
        Ticket existing = ticketDao.get(256744L);
        assertTrue(existing.isCheckedOut());

        existing.checkIn();
        ticketDao.updateUsingHistory(historicalTicketDao.create(existing, new Date(), TestOpInfo.DEFAULT.getActorName()));

        Ticket changed = ticketDao.get(256744L);
        assertFalse(changed.isCheckedOut());
    }

    @Test
    public void testUpdateHostmastersRemark() {
        String remark = "test-ticket-dao-remark";

        Ticket existing = ticketDao.get(257777L);
        assertNull(existing.getHostmastersRemark());

        existing.setHostmastersRemark(remark);
        ticketDao.updateUsingHistory(historicalTicketDao.create(existing, new Date(), TestOpInfo.DEFAULT.getActorName()));

        Ticket changed = ticketDao.get(257777L);
        assertEquals(remark, changed.getHostmastersRemark());
    }

    @Test
    public void testUpdateClikPaid() {
        Ticket existing = ticketDao.get(257777L);
        boolean clikPaid = existing.isClikPaid();

        existing.setClikPaid(!clikPaid);
        ticketDao.updateUsingHistory(historicalTicketDao.create(existing, new Date(), TestOpInfo.DEFAULT.getActorName()));

        Ticket changed = ticketDao.get(257777L);
        assertTrue(changed.isClikPaid() != clikPaid);
    }

    @Test
    public void testLimitedFindByBillingNH() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setBillNicHandle("APITEST-IEDR");
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 10);
        assertEquals(10, found.getResults().size());
        assertEquals(11, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByDomainName() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setDomainName("neway");
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 10);
        assertEquals(1, found.getResults().size());
        assertEquals(1, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByDomainHolder() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setDomainHolder("Bill");
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 2);
        assertEquals(2, found.getResults().size());
        assertEquals(4, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByAccountName() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setAccountId(122L);
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 10);
        assertEquals(10, found.getResults().size());
        assertEquals(108, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByAdminStatus() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setAdminStatus(AdminStatus.NEW);
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 1);
        assertEquals(1, found.getResults().size());
        assertEquals(8, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByTechStatus() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setTechStatus(TechStatus.PASSED);
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 10);
        assertEquals(10, found.getResults().size());
        assertEquals(33, found.getTotalResults());
    }

    @Test
    public void testLimitedFindFromDate() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setFrom(new Date("8/10/2008"));
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 10);
        assertEquals(10, found.getResults().size());
        assertEquals(293, found.getTotalResults());
    }

    @Test
    public void testLimitedFindToDate() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setTo(new Date("8/10/2008"));
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 10);
        assertEquals(10, found.getResults().size());
        assertEquals(644, found.getTotalResults());
    }

    private TicketSearchCriteria getAllCriteria() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setBillNicHandle("HA18-IEDR");
        criteria.setDomainName("b");
        criteria.setDomainHolder("war");
        criteria.setAccountId(114L);
        criteria.setAdminStatus(AdminStatus.HOLD_PAPERWORK);
        criteria.setTechStatus(TechStatus.NEW);
        criteria.setFrom(new Date("8/10/2008"));
        criteria.setTo(new Date("8/10/2008"));
        return criteria;
    }

    @Test
    public void testLimitedFindByTicketNumber() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setTicketId(7l);
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 10);
        assertEquals(1, found.getResults().size());
        assertEquals(1, found.getTotalResults());
        Ticket t = found.getResults().get(0);
        assertEquals(7l, t.getId());
        assertEquals("billiam.ie", t.getOperation().getDomainNameField().getNewValue());
        assertEquals("Bill Fogarty", t.getOperation().getDomainHolderField().getNewValue());
    }

    @Test
    public void testLimitedFindByTicketNumberAndDomainHolderConflicting() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setTicketId(7l);
        criteria.setDomainHolder("Billy Fogarty");
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 10);
        assertEquals(0, found.getResults().size());
        assertEquals(0, found.getTotalResults());
    }

    @Test
    public void testLimitedFindByTicketNumberAndDomainNameMatching() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setTicketId(7l);
        criteria.setDomainName("billiam.ie");
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 10);
        assertEquals(1, found.getResults().size());
        assertEquals(1, found.getTotalResults());
        Ticket t = found.getResults().get(0);
        assertEquals(7l, t.getId());
        assertEquals("billiam.ie", t.getOperation().getDomainNameField().getNewValue());
        assertEquals("Bill Fogarty", t.getOperation().getDomainHolderField().getNewValue());
    }

    @Test
    public void testLimitedFindByAllCriteria() {
        LimitedSearchResult<Ticket> found = ticketDao.find(getAllCriteria(), 0, 10);
        assertEquals(2, found.getResults().size());
        assertEquals(2, found.getTotalResults());
    }

    @Test
    public void testCount() {
        assertEquals(2, ticketDao.count(getAllCriteria()));
    }

    @Test
    public void testExists() {
        assertTrue(ticketDao.exists(getAllCriteria()));
    }

    @Test
    public void testNotExists() {
        TicketSearchCriteria criteria = getAllCriteria();
        criteria.setDomainName("e");
        assertFalse(ticketDao.exists(criteria));
    }

    @Test
    public void testLimitedFindByCheckedOutTo() {
        Ticket existing = ticketDao.get(258973L);
        assertFalse(existing.isCheckedOut());
        assertNotNull(existing.getChangeDate());

        existing.checkOut(new Contact("IH4-IEDR"));
        ticketDao.updateUsingHistory(historicalTicketDao.create(existing, new Date(), TestOpInfo.DEFAULT.getActorName()));

        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setNicHandle("IH4-IEDR");
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 1);
        assertEquals(1, found.getResults().size());
        assertEquals(1, found.getTotalResults());
    }

    @Test
    public void testGetModTicketNoDomain() {
        long ticketId = 300638L;
        Ticket t = ticketDao.get(ticketId);
        assertTrue("Hostmaster's remark starts with 'Domain name does not exist'",
                t.getHostmastersRemark().startsWith("Domain name does not exist"));
        assertTrue("Original domain name starts with 'Domain name does not exist'", t.getOperation()
                .getDomainNameField().getCurrentValue().startsWith("Domain name does not exist"));
    }

    private Ticket getNewTicket(long id, Date date) {
        Contact idl2Contact = new Contact("IDL2-IEDR", "IRISH DOMAINS LTD", "NHEmail000901@server.xxx", "ACCOUNTS",
                "Ireland", null);
        EntityClass domainClass = entityClassDao.get(1L);
        EntityCategory domainCategory = entityCategoryDao.get(11L);
        EntitySubcategory domainSubcategory = entitySubcategoryDao.get(1L);
        Account guestAccount = new Account(1, "GUEST ACCOUNT", "IH4-IEDR");
        guestAccount.setAgreementSigned(true);
        guestAccount.setTicketEdit(true);
        DomainOperation operation = new DomainOperation(DomainOperation.DomainOperationType.REG, date,
                new SimpleDomainFieldChange<>(null, "domainName"),
                new SimpleDomainFieldChange<>(null, "holderName"),
                new SimpleDomainFieldChange<>(null, domainClass),
                new SimpleDomainFieldChange<>(null, domainCategory),
                new SimpleDomainFieldChange<>(null, domainSubcategory),
                new SimpleDomainFieldChange<>(null, guestAccount),
                Arrays.asList(new SimpleDomainFieldChange<>(null, idl2Contact)),
                Arrays.asList(new SimpleDomainFieldChange<>(null, idl2Contact)),
                Arrays.asList(new SimpleDomainFieldChange<>(null, idl2Contact)),
                Arrays.asList(
                        new NameserverChange(
                                new SimpleDomainFieldChange<>(null, "ns1.ie"),
                                new IpFieldChange(null, "1.1.1.1"),
                                new IpFieldChange(null, "::1")),
                        new NameserverChange(
                                new SimpleDomainFieldChange<>(null, "ns2.ie"),
                                new IpFieldChange(null, "2.2.2.2"),
                                new IpFieldChange(null, "::2"))
                )
        );

        Ticket ticket = new Ticket(id, operation, AdminStatus.PASSED, date, TechStatus.PASSED, date,
                "reqRemark", "hostRemark", idl2Contact, date, date, null, false, false, Period.fromYears(2), "CHY1",
                false, FinancialStatus.NEW, date, CustomerStatus.NEW, date);
        return ticket;
    }

    @Test
    public void ticketCreateTest() {
        final Date aDate = endOfDay(new Date());
        Ticket ticket = getNewTicket(-1, aDate);
        long newTicketId = ticketDao.createTicket(ticket);
        assertTrue("newTicketId should be a positive integer if read from database", newTicketId > 0);

        Ticket ticketFromDB = ticketDao.get(newTicketId);
        TicketHelper.compareNewTickets(ticketFromDB, ticket, false);
        assertTrue(ticketFromDB.getOperation().getNameserversField().isEmpty());

        assertEquals(DateUtils.truncate(aDate, Calendar.SECOND), ticketFromDB.getCreationDate());
        assertEquals(DateUtils.truncate(aDate, Calendar.SECOND), ticketFromDB.getChangeDate());
        assertEquals(DateUtils.truncate(aDate, Calendar.SECOND), ticketFromDB.getAdminStatusChangeDate());
        assertEquals(DateUtils.truncate(aDate, Calendar.SECOND), ticketFromDB.getTechStatusChangeDate());
        assertEquals(DateUtils.truncate(aDate, Calendar.SECOND), ticketFromDB.getFinancialStatusChangeDate());
        assertEquals(DateUtils.truncate(aDate, Calendar.SECOND), ticketFromDB.getCustomerStatusChangeDate());
    }

    @Test
    public void testCreateAndCopyNameserversFromHistory() {
        Ticket ticket = getNewTicket(-1, new Date());
        long newTicketId = ticketDao.createTicket(ticket);
        Ticket ticketFromDB = ticketDao.get(newTicketId);
        TicketHelper.compareNewTickets(ticketFromDB, ticket, false);
        assertTrue(ticketFromDB.getOperation().getNameserversField().isEmpty());

        ticketFromDB.getOperation().setNameserversField(ticket.getOperation().getNameserversField());
        ticketDao.updateUsingHistory(historicalTicketDao.create(ticketFromDB, new Date(),
                TestOpInfo.DEFAULT.getActorName()));
        ticketFromDB = ticketDao.get(newTicketId);
        TicketHelper.compareNameserverChanges(ticketFromDB.getOperation().getNameserversField(),
                ticket.getOperation().getNameserversField());
    }

    @Test
    public void testUpdateUsingHistory() {
        long id = 256745L;
        Ticket ticket = getNewTicket(id, new Date());
        long changeId = historicalTicketDao.create(ticket, new Date(), TestOpInfo.DEFAULT.getActorName());
        ticketDao.updateUsingHistory(changeId);
        Ticket ticketFromDB = ticketDao.get(id);
        TicketHelper.compareTickets(ticketFromDB, ticket);
    }

    @Test
    public void testDateFieldsTruncateOnUpdate() {
        Date aDate = endOfDay(new Date());
        final long ticketId = 258973L;
        Ticket actual = ticketDao.get(ticketId);
        // instead of setting change dates (most of which are private and changed only
        // when some other field is touched) I'm using the fact that Ticket object
        // returns it's actual date field, not a copy of it.
        actual.getChangeDate().setTime(aDate.getTime());
        actual.getAdminStatusChangeDate().setTime(aDate.getTime());
        actual.getTechStatusChangeDate().setTime(aDate.getTime());
        actual.setFinancialStatus(FinancialStatus.STALLED);
        actual.getFinancialStatusChangeDate().setTime(aDate.getTime());
        actual.setCustomerStatus(CustomerStatus.CANCELLED);
        actual.getCustomerStatusChangeDate().setTime(aDate.getTime());
        ticketDao.updateUsingHistory(historicalTicketDao.create(actual, actual.getChangeDate(),
                TestOpInfo.DEFAULT.getActorName()));
        Ticket ticketFromDB = ticketDao.get(ticketId);
        assertEquals(DateUtils.truncate(aDate, Calendar.SECOND), ticketFromDB.getChangeDate());
        assertEquals(DateUtils.truncate(aDate, Calendar.SECOND), ticketFromDB.getAdminStatusChangeDate());
        assertEquals(DateUtils.truncate(aDate, Calendar.SECOND), ticketFromDB.getTechStatusChangeDate());
        assertEquals(DateUtils.truncate(aDate, Calendar.SECOND), ticketFromDB.getFinancialStatusChangeDate());
        assertEquals(DateUtils.truncate(aDate, Calendar.SECOND), ticketFromDB.getCustomerStatusChangeDate());
    }

    @Test
    public void testUpdateFinancialStatus() {
        Ticket actual = ticketDao.get(258973L); // some existing ticket
        // check the financial status first:
        assertEquals(FinancialStatus.NEW, actual.getFinancialStatus());
        actual.setFinancialStatus(FinancialStatus.PASSED);
        Date dt = actual.getFinancialStatusChangeDate();

        ticketDao.updateUsingHistory(historicalTicketDao.create(actual, new Date(), TestOpInfo.DEFAULT.getActorName()));
        actual = ticketDao.get(258973L);

        assertEquals(FinancialStatus.PASSED, actual.getFinancialStatus());
        assertTrue(DateUtils.isSameDay(dt, actual.getFinancialStatusChangeDate()));

    }

    @Test
    public void updateTechStatusTest() {
        Ticket actual = ticketDao.get(259926L);
        assertEquals(TechStatus.PASSED, actual.getTechStatus());
        actual.setTechStatus(TechStatus.STALLED);
        Date date = actual.getTechStatusChangeDate();

        ticketDao.updateUsingHistory(historicalTicketDao.create(actual, new Date(), TestOpInfo.DEFAULT.getActorName()));
        actual = ticketDao.get(259926L);

        assertEquals(TechStatus.STALLED, actual.getTechStatus());
        assertTrue(DateUtils.isSameDay(date, actual.getTechStatusChangeDate()));
    }

    @Test
    public void findByCustomerStatusTest() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setCustomerStatus(CustomerStatus.CANCELLED);
        LimitedSearchResult<Ticket> result = ticketDao.find(criteria, 0, 10);
        assertEquals(0, result.getTotalResults());

        criteria.setCustomerStatus(CustomerStatus.NEW);
        result = ticketDao.find(criteria, 0, 10);
        assertEquals(915, result.getTotalResults());
    }

    @Test
    public void findAllTest() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        SearchResult<Ticket> result = ticketDao.find(criteria);
        assertEquals(915, result.getResults().size());
    }

    @Test
    public void findByDomainName() {
        // domain name is a fuzzy search, looks for tickets where domain starts like passed string
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setDomainName("");
        SearchResult<Ticket> result = ticketDao.find(criteria);
        assertEquals(915, result.getResults().size());

        criteria.setDomainName("d");
        result = ticketDao.find(criteria);
        assertEquals(71, result.getResults().size());
        assertTrue(Iterables.all(result.getResults(), domainNameStartsWith("d")));

        criteria.setDomainName("da");
        result = ticketDao.find(criteria);
        assertEquals(10, result.getResults().size());
        assertTrue(Iterables.all(result.getResults(), domainNameStartsWith("da")));
    }

    private Predicate<? super Ticket> domainNameStartsWith(final String d) {
        return new Predicate<Ticket>() {
            @Override
            public boolean apply(Ticket ticket) {
                return ticket.getOperation().getDomainNameField().getNewValue().startsWith(d);
            }
        };
    }

    @Test
    public void findByExactDomainName() {
        // exact domain name should only match exact domain name
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setExactDomainName("");
        SearchResult<Ticket> result = ticketDao.find(criteria);
        assertEquals(0, result.getResults().size());

        criteria.setExactDomainName("d");
        result = ticketDao.find(criteria);
        assertEquals(0, result.getResults().size());

        final String domainName = "daosin.ie";
        criteria.setExactDomainName(domainName);
        result = ticketDao.find(criteria);
        assertEquals(1, result.getResults().size());
        final Ticket t = result.getResults().get(0);
        assertEquals(t.getOperation().getDomainNameField().getNewValue(), domainName);
    }

    @Test
    public void findByFinancialStatusTest() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setFinancialStatus(FinancialStatus.STALLED);
        LimitedSearchResult<Ticket> result = ticketDao.find(criteria, 0, 10);
        assertEquals(4, result.getTotalResults());

        criteria.setFinancialStatus(FinancialStatus.NEW);
        result = ticketDao.find(criteria, 0, 10);
        assertEquals(911, result.getTotalResults());

        criteria.setFinancialStatus(FinancialStatus.PASSED);
        result = ticketDao.find(criteria, 0, 10);
        assertEquals(0, result.getTotalResults());
    }

    @Test
    public void findByTicketType() throws Exception {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setTicketType(DomainOperation.DomainOperationType.MOD);
        LimitedSearchResult<Ticket> result = ticketDao.find(criteria, 0, 10);
        assertEquals(22, result.getTotalResults());

        criteria = new TicketSearchCriteria();
        criteria.setTicketType(DomainOperation.DomainOperationType.XFER);
        result = ticketDao.find(criteria, 0, 10);
        assertEquals(2, result.getTotalResults());

        criteria = new TicketSearchCriteria();
        criteria.setTicketType(DomainOperation.DomainOperationType.REG);
        result = ticketDao.find(criteria, 0, 10);
        assertEquals(888, result.getTotalResults());

        criteria = new TicketSearchCriteria();
        criteria.setTicketType(DomainOperation.DomainOperationType.MOD, DomainOperation.DomainOperationType.XFER,
                DomainOperation.DomainOperationType.REG);
        result = ticketDao.find(criteria, 0, 10);
        assertEquals(912, result.getTotalResults());
    }

    @Test
    public void updateNameserversWithFailureReasons() {
        final long TICKET_ID = 256745;
        Ticket actual = ticketDao.get(TICKET_ID);
        List<NameserverChange> actualNameservers = actual.getOperation().getNameserversField();
        Assert.assertEquals(actualNameservers.size(), 3);

        final NameserverChange change1 = nsChange(0);
        final NameserverChange change2 = nsChange(1);
        change1.getName().setFailureReason(FailureReason.INCORRECT);
        change1.getIpv4Address().setFailureReason(IpFailureReason.IP_DEST_HOST_UNREACHABLE);
        change1.getIpv6Address().setFailureReason(IpFailureReason.IP_DESTINATION_NET_UNREACHABLE);
        change2.getName().setFailureReason(FailureReason.INCORRECT);
        change2.getIpv4Address().setFailureReason(IpFailureReason.IP_DEST_HOST_UNREACHABLE);
        change2.getIpv6Address().setFailureReason(IpFailureReason.IP_DESTINATION_NET_UNREACHABLE);

        actual.getOperation().setNameserversField(Arrays.asList(change1, change2));
        ticketDao.updateUsingHistory(historicalTicketDao.create(actual, new Date(), TestOpInfo.DEFAULT.getActorName()));

        Ticket dbTicket = ticketDao.get(TICKET_ID);
        TicketHelper.compareNameserverChanges(dbTicket.getOperation().getNameserversField(),
                Arrays.asList(change1, change2));
    }

    @Test
    public void updateWithTwoNameservers() {
        final long TICKET_ID = 256745;
        Ticket actual = ticketDao.get(TICKET_ID);
        List<NameserverChange> actualNameservers = actual.getOperation().getNameserversField();
        Assert.assertEquals(actualNameservers.size(), 3);
        List<NameserverChange> nameservers = Arrays.asList(nsChange(0), nsChange(1));
        actual.getOperation().setNameserversField(nameservers);
        ticketDao.updateUsingHistory(historicalTicketDao.create(actual, new Date(), TestOpInfo.DEFAULT.getActorName()));

        Ticket dbTicket = ticketDao.get(TICKET_ID);
        TicketHelper.compareNameserverChanges(dbTicket.getOperation().getNameserversField(), nameservers);
    }

    @Test
    public void updateWithSevenNameservers() {
        final long TICKET_ID = 259926L;
        Ticket actual = ticketDao.get(TICKET_ID);
        List<NameserverChange> actualNameservers = actual.getOperation().getNameserversField();
        Assert.assertEquals(actualNameservers.size(), 2);

        List<NameserverChange> nameservers = Arrays.asList(nsChange(0, null), nsChange(1), nsChange(2, null),
                nsChange(3), nsChange(4, null), nsChange(5), nsChange(6, null));
        actual.getOperation().setNameserversField(nameservers);
        ticketDao.updateUsingHistory(historicalTicketDao.create(actual, new Date(), TestOpInfo.DEFAULT.getActorName()));

        Ticket dbTicket = ticketDao.get(TICKET_ID);
        TicketHelper.compareNameserverChanges(dbTicket.getOperation().getNameserversField(), nameservers);
    }

    @Test
    public void testFindDoesNotReturnNameservers() {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setBillNicHandle("APITEST-IEDR");
        LimitedSearchResult<Ticket> found = ticketDao.find(criteria, 0, 10);
        assertEquals(10, found.getResults().size());
        assertEquals(11, found.getTotalResults());
        for (Ticket t : found.getResults()) {
            Assert.assertEquals(t.getOperation().getNameserversField(), Collections.emptyList());
        }
    }

    @Test
    public void testTicketNotifications() {
        long ticketId = 259926L;
        int period = 30;
        assertNull(ticketDao.getTicketNotification(ticketId, period));
        TicketNotification notification = new TicketNotification(ticketId, period);
        ticketDao.createTicketNotification(notification);
        assertNotNull(ticketDao.getTicketNotification(ticketId, period));
        assertNull(ticketDao.getTicketNotification(ticketId, 15));
    }

    private NameserverChange nsChange(int i) {
        return nsChange(i, i);
    }

    private NameserverChange nsChange(int i, Integer j) {
        return new NameserverChange(
            new SimpleDomainFieldChange<>(null, nameserverName(i)),
            new IpFieldChange(null, nameserverIpv4(j)),
            new IpFieldChange(null, nameserverIpv6(j)));
    }

    private String nameserverName(int i) {
        return "n" + Integer.toString(i) + ".ie";
    }

    private String nameserverIpv4(Integer i) {
        if (i == null)
            return null;
        return "192.168.0." + Integer.toString(i);
    }

    private String nameserverIpv6(Integer i) {
        if (i == null)
            return null;
        return "::" + Integer.toString(i);
    }
}
