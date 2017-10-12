package pl.nask.crs.dnscheck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.DateTestHelper;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.email.service.impl.EmailTemplateSenderImpl;
import pl.nask.crs.dnscheck.dao.DnsNotificationDAO;
import pl.nask.crs.dnscheck.email.DnsNotificationEmailParameters;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.services.TicketSearchService;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

public class DnsNotificationServiceTest extends AbstractTest {

    private static final String TIME_FORMAT = "HH:mm";

    @Autowired
    DnsNotificationService dnsNotificationService;

    @Autowired
    DomainSearchService domainSearchService;

    @Autowired
    TicketSearchService ticketSearchService;

    @Autowired
    DnsNotificationDAO dnsNotificationDAO;

    @Mocked
    private EmailTemplateSenderImpl sender;

    @Test
    public void test() throws Exception {
        List<DnsNotification> notifications = dnsNotificationDAO.getAll();
        AssertJUnit.assertEquals("Pending notifications before test", 5, notifications.size());

        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.DNS_NOTIFICATION.getId(),
                        withInstanceOf(DnsNotificationEmailParameters.class));
                // grouped by a domain name
                times = 4;
            }
        };

        dnsNotificationService.sendNotifications();

        notifications = dnsNotificationDAO.getAll();
        assertEquals(notifications.size(), 0, "Pending notifications after sending");
    }

    @Test
    public void skipDomainNotificationsWithoutDomain() throws Exception {
        dnsNotificationService.sendNotifications();
        assertEquals(dnsNotificationDAO.getAll().size(), 0);

        DnsNotification notification = new DnsNotification("nonexistent.ie", null, "dns1.myhostns.com", new Date(),
                "Failure");
        dnsNotificationDAO.create(notification);
        assertEquals(dnsNotificationDAO.getAll().size(), 1);

        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.DNS_NOTIFICATION.getId(),
                        withInstanceOf(EmailParameters.class));
                times = 0;
            }
        };
        dnsNotificationService.sendNotifications();
        assertEquals(dnsNotificationDAO.getAll().size(), 0);
    }

    @Test
    public void skipTicketNotificationsWithoutTicket() throws Exception {
        dnsNotificationService.sendNotifications();
        assertEquals(dnsNotificationDAO.getAll().size(), 0);

        DnsNotification notification = new DnsNotification("nonexistent.ie", 99999L, "dns1.myhostns.com", new Date(),
                "Failure");
        dnsNotificationDAO.create(notification);
        assertEquals(dnsNotificationDAO.getAll().size(), 1);

        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.DNS_NOTIFICATION.getId(),
                        withInstanceOf(EmailParameters.class));
                times = 0;
            }
        };
        dnsNotificationService.sendNotifications();
        assertEquals(dnsNotificationDAO.getAll().size(), 0);
    }

    @Test
    public void testMultipleNotificationsClearedAfterSend() throws Exception {
        dnsNotificationService.sendNotifications();
        assertEquals(dnsNotificationDAO.getAll().size(), 0);

        String domainName = "thedomain.ie";
        String nsName = "dns1.myhostns.com";
        DnsNotification notification = new DnsNotification(domainName, null, nsName, new Date(), "Failure");
        dnsNotificationDAO.create(notification);
        dnsNotificationDAO.create(notification);
        assertEquals(dnsNotificationDAO.getAll().size(), 2);

        dnsNotificationService.sendNotifications();
        assertEquals(dnsNotificationDAO.getAll().size(), 0);
    }

    @Test
    public void testMultipleNotificationsSameDay() throws Exception {
        Date checkTime1 = DateTestHelper.makeDate(2016, 11, 5);
        checkTime1 = DateTestHelper.setHour(checkTime1, 14, 43, 13, 0);
        final Date checkTime2 = DateUtils.addMinutes(checkTime1, -39);

        testMultipleNotifications(checkTime2, checkTime1, "on 2016-11-05 at 14:04, 14:43");
    }

    @Test
    public void testMultipleNotificationsDifferentDaysTime() throws Exception {
        Date checkTime1 = DateTestHelper.makeDate(2016, 11, 5);
        checkTime1 = DateTestHelper.setHour(checkTime1, 14, 43, 13, 0);
        final Date checkTime2 = DateUtils.addMinutes(checkTime1, 24 * 60 - 39);

        testMultipleNotifications(checkTime1, checkTime2, "on 2016-11-05 at 14:43, on 2016-11-06 at 14:04");
    }

    @Test
    public void testMultipleNotificationsFromToday() throws Exception {
        final Date checkTime1 = DateUtils.addMinutes(new Date(), -20);
        final Date checkTime2 = DateUtils.addMinutes(new Date(), -10);
        String timeString = String.format("at %s, %s",
                DateFormatUtils.format(checkTime1, TIME_FORMAT),
                DateFormatUtils.format(checkTime2, TIME_FORMAT));
        testMultipleNotifications(checkTime1, checkTime2, timeString);
    }

    private void testMultipleNotifications(Date firstNotificationCheckTime, Date secondNotificationCheckTime,
            String expectedTimeString) throws Exception {
        dnsNotificationService.sendNotifications();
        assertEquals(dnsNotificationDAO.getAll().size(), 0);

        String domainName = "thedomain.ie";
        String nsName = "dns1.myhostns.com";
        String failureReason1 = "Failure 1";
        String failureReason2 = "Failure 2";
        DnsNotification notification1 = new DnsNotification(domainName, null, nsName, firstNotificationCheckTime,
                failureReason1);
        DnsNotification notification2 = new DnsNotification(domainName, null, nsName, secondNotificationCheckTime,
                failureReason2);
        dnsNotificationDAO.create(notification1);
        dnsNotificationDAO.create(notification2);
        assertEquals(dnsNotificationDAO.getAll().size(), 2);

        List<DnsNotificationEmailParameters> paramsList = new ArrayList<>();
        captureNotificationParams(paramsList);

        dnsNotificationService.sendNotifications();
        assertEquals(dnsNotificationDAO.getAll().size(), 0);
        DnsNotificationEmailParameters params = paramsList.get(0);
        String message = params.getParameterValue(ParameterNameEnum.DNS_FAILURES.getName(), false);
        assertEquals(StringUtils.countOccurrencesOf(message, expectedTimeString), 1, message);
        assertEquals(StringUtils.countOccurrencesOf(message, failureReason1), 0, message);
        assertEquals(StringUtils.countOccurrencesOf(message, failureReason2), 1, message);
    }

    @Test
    public void testNotificationAddresseeForRegistration() throws Exception {
        String domainName = "irishenergyraters.ie";
        Ticket ticket = ticketSearchService.getTicketForDomain(domainName);
        testNotificationAddressee(domainName, ticket.getId(), true, false);
    }

    @Test
    public void testNotificationAddresseeForModificationAndTicketOrigin() throws Exception {
        String domainName = "tommy.ie";
        Ticket ticket = ticketSearchService.getTicketForDomain(domainName);
        testNotificationAddressee(domainName, ticket.getId(), false, true);
    }

    @Test
    public void testNotificationAddresseeForModificationAndDomainOrigin() throws Exception {
        String domainName = "tommy.ie";
        testNotificationAddressee(domainName, null, false, true);
    }

    @Test
    public void testNotificationAddresseeForTransferAndTicketOrigin() throws Exception {
        String domainName = "thedomain-668.ie";
        Ticket ticket = ticketSearchService.getTicketForDomain(domainName);
        testNotificationAddressee(domainName, ticket.getId(), true, true);
    }

    @Test
    public void testNotificationAddresseeTransferAndDomainOrigin() throws Exception {
        String domainName = "thedomain-668.ie";
        testNotificationAddressee(domainName, null, false, true);
    }

    private void testNotificationAddressee(String domainName, Long ticketId, boolean techCFromTicketExpected,
            boolean domainExists) throws Exception {
        assert techCFromTicketExpected || domainExists;
        Ticket ticket = ticketSearchService.getTicketForDomain(domainName);
        String ticketTechC = ticket.getOperation().getTechContactsField().get(0).getNewValue().getNicHandle();
        String domainTechC = null;
        if (domainExists) {
            Domain domain = domainSearchService.getDomain(domainName);
            domainTechC = domain.getTechContactNic();
            assertNotEquals(domainTechC, ticketTechC);
        }

        dnsNotificationService.sendNotifications();
        assertEquals(dnsNotificationDAO.getAll().size(), 0);

        DnsNotification notification = new DnsNotification(domainName, ticketId, "ns1.dns.ie", new Date(), "Reason");
        dnsNotificationDAO.create(notification);
        List<DnsNotificationEmailParameters> paramsList = new ArrayList<>();
        captureNotificationParams(paramsList);

        dnsNotificationService.sendNotifications();
        assertEquals(dnsNotificationDAO.getAll().size(), 0);

        DnsNotificationEmailParameters params = paramsList.get(0);
        String notificationTechC = params.getParameterValue(ParameterNameEnum.TECH_C_NIC.getName(), false);
        assertNotNull(notificationTechC);
        assertEquals(notificationTechC, techCFromTicketExpected ? ticketTechC : domainTechC);
    }

    @Test
    public void testNotificationsWithBothOriginsForModification() throws Exception {
        String domainName = "tommy.ie";
        testNotificationsWithBothOrigins(domainName, false);
    }

    @Test
    public void testNotificationsWithBothOriginsForTransfer() throws Exception {
        String domainName = "thedomain-668.ie";
        testNotificationsWithBothOrigins(domainName, true);
    }

    private void testNotificationsWithBothOrigins(String domainName, final boolean originsAreSeparate)
            throws Exception {
        dnsNotificationService.sendNotifications();
        assertEquals(dnsNotificationDAO.getAll().size(), 0);
        Ticket ticket = ticketSearchService.getTicketForDomain(domainName);

        DnsNotification notification1 = new DnsNotification(domainName, null, "ns1.dns.ie", new Date(), "E");
        DnsNotification notification2 = new DnsNotification(domainName, ticket.getId(), "ns1.dns.ie", new Date(), "E");
        dnsNotificationDAO.create(notification1);
        dnsNotificationDAO.create(notification2);
        assertEquals(dnsNotificationDAO.getAll().size(), 2);

        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.DNS_NOTIFICATION.getId(),
                        withInstanceOf(EmailParameters.class));
                times = originsAreSeparate ? 2 : 1;
            }
        };
        dnsNotificationService.sendNotifications();
    }

    @Test
    public void testNotificationRemovalForRegistration() throws Exception {
        String domainName = "irishenergyraters.ie";
        testNotificationRemoval(domainName, true);
    }

    @Test
    public void testNotificationRemovalForModification() throws Exception {
        String domainName = "tommy.ie";
        testNotificationRemoval(domainName, false);
    }

    @Test
    public void testNotificationRemovalForTransfer() throws Exception {
        String domainName = "thedomain-668.ie";
        testNotificationRemoval(domainName, true);
    }

    private void testNotificationRemoval(String domainName, boolean originsAreSeparate) throws Exception {
        testNotificationRemoval(domainName, true, originsAreSeparate);
        testNotificationRemoval(domainName, false, originsAreSeparate);
    }

    private void testNotificationRemoval(String domainName, boolean ticketOrigin, boolean originsAreSeparate)
            throws Exception {
        dnsNotificationService.sendNotifications();
        assertEquals(dnsNotificationDAO.getAll().size(), 0);

        Ticket ticket = ticketSearchService.getTicketForDomain(domainName);
        DnsNotification notification1 = new DnsNotification(domainName, null, "ns1.dns.ie", new Date(), "E");
        DnsNotification notification2 = new DnsNotification(domainName, ticket.getId(), "ns2.dns.ie", new Date(), "E");
        dnsNotificationDAO.create(notification1);
        dnsNotificationDAO.create(notification2);
        assertEquals(dnsNotificationDAO.getAll().size(), 2);

        Long ticketId = ticketOrigin ? ticket.getId() : null;
        Long expectedTicketId = ticketOrigin ? null : ticket.getId();
        dnsNotificationService.removeObsoleteNotifications(domainName, ticketId, Collections.<String>emptySet());
        List<DnsNotification> notifications = dnsNotificationDAO.getAll();
        if (originsAreSeparate) {
            assertEquals(notifications.size(), 1);
            assertEquals(notifications.get(0).getTicketNumber(), expectedTicketId);
        } else {
            assertEquals(notifications.size(), 0);
        }
    }

    private void captureNotificationParams(final List<DnsNotificationEmailParameters> paramsList) throws Exception {
        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.DNS_NOTIFICATION.getId(), withCapture(paramsList));
            }
        };
    }

}
