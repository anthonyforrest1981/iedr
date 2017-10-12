package pl.nask.crs.dnscheck;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.google.common.collect.Sets;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.utils.CollectionUtils;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.dnscheck.dao.DnsNotificationDAO;

import static org.apache.commons.lang.time.DateUtils.truncate;
import static pl.nask.crs.commons.utils.CollectionUtils.exists;
import static pl.nask.crs.commons.utils.CollectionUtils.forAll;

public class DnsNotificationDAOTest extends AbstractTest {

    @Resource
    DnsNotificationDAO dnsNotificationDAO;

    @Test
    public void createTest() {
        Date now = new Date();
        DnsNotification dnsNotification = new DnsNotification("xdomain.ie", null, "ns1.ie", now, "error");
        dnsNotificationDAO.create(dnsNotification);

        List<DnsNotification> notifications = dnsNotificationDAO.getByDomainName("xdomain.ie");
        AssertJUnit.assertEquals(1, notifications.size());

        DnsNotification n = notifications.get(0);
        AssertJUnit.assertNotNull(n);
        AssertJUnit.assertEquals("xdomain.ie", n.getDomainName());
        AssertJUnit.assertNull(n.getTicketNumber());
        AssertJUnit.assertEquals("ns1.ie", n.getNsName());
        AssertJUnit.assertEquals("error", n.getErrorMessage());
        AssertJUnit.assertEquals(truncate(now, Calendar.SECOND), n.getTimeOfCheck());
    }

    @Test
    public void getAllTest() {
        List<DnsNotification> notifications = dnsNotificationDAO.getAll();
        AssertJUnit.assertEquals(5, notifications.size());
    }

    @Test
    public void deleteTest() {
        int initialSize = dnsNotificationDAO.getAll().size();
        DnsNotification notification = dnsNotificationDAO.getByDomainName("presb.ie").get(0);
        dnsNotificationDAO.deleteById(notification.getId());
        List<DnsNotification> notifications = dnsNotificationDAO.getAll();
        AssertJUnit.assertEquals(initialSize - 1, notifications.size());
    }

    @Test
    public void notificationDateTest() {
        DnsNotificationDate notificationDate = dnsNotificationDAO.getNotificationDate("NH2-IEDR");
        Date newDate = DateUtils.endOfDay(new Date());
        notificationDate.setNextNotificationDate(newDate);
        dnsNotificationDAO.update(notificationDate);
        DnsNotificationDate updatedConfig = dnsNotificationDAO.getNotificationDate("NH2-IEDR");
        AssertJUnit.assertEquals(truncate(newDate, Calendar.DATE), updatedConfig.getNextNotificationDate());

        notificationDate = dnsNotificationDAO.getNotificationDate("X-IEDR");
        AssertJUnit.assertNull(notificationDate);
        notificationDate = new DnsNotificationDate("X-IEDR", new Date());
        AssertJUnit.assertEquals("X-IEDR", notificationDate.getNicHandle());
        Assert.assertNotNull(notificationDate.getNextNotificationDate());
    }

    @Test
    public void createNotificationDateTest() throws Exception {
        DnsNotificationDate newNotificationDate = new DnsNotificationDate("TEST", new Date());
        dnsNotificationDAO.createNotificatioDate(newNotificationDate);
        DnsNotificationDate fromDB = dnsNotificationDAO.getNotificationDate("TEST");
        Assert.assertNotNull(fromDB);
    }

    @Test
    public void deleteAllForDomainWithEmptyNameserverListTest() {
        final String domainName = "xdomain.ie";
        Assert.assertTrue(dnsNotificationDAO.getByDomainName(domainName).isEmpty(),
                "No notification should be present at start of test");

        Date now = new Date();
        DnsNotification notif1 = new DnsNotification(domainName, null, "ns1.ie", now, "error");
        dnsNotificationDAO.create(notif1);
        DnsNotification notif2 = new DnsNotification(domainName, null, "ns2.ie", now, "error");
        dnsNotificationDAO.create(notif2);
        DnsNotification notif3 = new DnsNotification(domainName, null, "ns3.ie", now, "error");
        dnsNotificationDAO.create(notif3);

        Assert.assertEquals(dnsNotificationDAO.getByDomainName(domainName).size(), 3,
                "Should list all three notifications");

        dnsNotificationDAO.removeObsoleteNotificationsForDomain(domainName, Collections.<String>emptySet());

        Assert.assertTrue(dnsNotificationDAO.getByDomainName(domainName).isEmpty(),
                "Should be empty after all notifications were deleted");
    }

    @Test
    public void deleteAllForDomainWithNameserverListTest() {
        final String domainName = "xdomain.ie";
        Assert.assertTrue(dnsNotificationDAO.getByDomainName(domainName).isEmpty(),
                "No notification should be present at start of test");

        final String ns1Name = "ns1.ie";
        Date now = new Date();
        DnsNotification notif1 = new DnsNotification(domainName, null, ns1Name, now, "error");
        dnsNotificationDAO.create(notif1);
        final String ns2Name = "ns2.ie";
        DnsNotification notif2 = new DnsNotification(domainName, null, ns2Name, now, "error");
        dnsNotificationDAO.create(notif2);
        final String ns3Name = "ns3.ie";
        DnsNotification notif3 = new DnsNotification(domainName, null, ns3Name, now, "error");
        dnsNotificationDAO.create(notif3);

        Assert.assertEquals(dnsNotificationDAO.getByDomainName(domainName).size(), 3,
                "Should list all three notifications");

        dnsNotificationDAO.removeObsoleteNotificationsForDomain(domainName, Sets.newHashSet(ns1Name, ns2Name));

        final List<DnsNotification> notifications = dnsNotificationDAO.getByDomainName(domainName);
        Assert.assertEquals(notifications.size(), 2, "Should still contain notifications for ns1 and ns2");

        Assert.assertTrue(exists(notifications, NotificationPredicate.nsName(ns1Name)),
                "ns1 should still be present in result");
        Assert.assertTrue(exists(notifications, NotificationPredicate.nsName(ns2Name)),
                "ns2 should still be present in result");
        Assert.assertFalse(exists(notifications, NotificationPredicate.nsName(ns3Name)),
                "ns3 should no longer be present in result");
        Assert.assertTrue(forAll(notifications, NotificationPredicate.domainName(domainName)),
                "All should be about the same domain");
    }

    @Test
    public void deleteAllForDomainWithTicketPresent() {
        String domainName = "thedomain-668.ie";
        long ticketNumber = 259924L;
        DnsNotification notif1 = new DnsNotification(domainName, null, "n1.ie", new Date(), "error");
        DnsNotification notif2 = new DnsNotification(domainName, ticketNumber, "n2.ie", new Date(), "error");
        dnsNotificationDAO.create(notif1);
        dnsNotificationDAO.create(notif2);

        Assert.assertEquals(dnsNotificationDAO.getByDomainName(domainName).size(), 2);
        dnsNotificationDAO.removeObsoleteNotificationsForDomain(domainName, Collections.<String>emptySet());
        List<DnsNotification> notifications = dnsNotificationDAO.getByDomainName(domainName);
        Assert.assertEquals(notifications.size(), 1);
        Assert.assertEquals(notifications.get(0).getTicketNumber().longValue(), ticketNumber);
    }

    @Test
    public void deleteAllForTicketWithDomainPresent() {
        String domainName = "thedomain-668.ie";
        long ticketNumber = 259924L;
        DnsNotification notif1 = new DnsNotification(domainName, null, "n1.ie", new Date(), "error");
        DnsNotification notif2 = new DnsNotification(domainName, ticketNumber, "n2.ie", new Date(), "error");
        dnsNotificationDAO.create(notif1);
        dnsNotificationDAO.create(notif2);

        Assert.assertEquals(dnsNotificationDAO.getByDomainName(domainName).size(), 2);
        dnsNotificationDAO.removeObsoleteNotificationsForTicket(ticketNumber, Collections.<String>emptySet());
        List<DnsNotification> notifications = dnsNotificationDAO.getByDomainName(domainName);
        Assert.assertEquals(notifications.size(), 1);
        Assert.assertNull(notifications.get(0).getTicketNumber());
    }

    @Test
    public void deleteAllForTicketWithOtherTicketPresent() {
        String domainName = "thedomain-668.ie";
        long ticketNumber = 259924L;
        DnsNotification notif1 = new DnsNotification(domainName, 999999L, "n1.ie", new Date(), "error");
        DnsNotification notif2 = new DnsNotification(domainName, ticketNumber, "n2.ie", new Date(), "error");
        dnsNotificationDAO.create(notif1);
        dnsNotificationDAO.create(notif2);

        Assert.assertEquals(dnsNotificationDAO.getByDomainName(domainName).size(), 2);
        dnsNotificationDAO.removeObsoleteNotificationsForTicket(ticketNumber, Collections.<String>emptySet());
        List<DnsNotification> notifications = dnsNotificationDAO.getByDomainName(domainName);
        Assert.assertEquals(notifications.size(), 1);
        Assert.assertEquals(notifications.get(0).getTicketNumber().longValue(), 999999L);
    }

    @Test
    public void testCreateUnnormalizedUtf8() {
        DnsNotification newNotif = new DnsNotification();
        newNotif.setDomainName("newno\u0308tifdomain.ie");
        newNotif.setNsName("ns.newno\u0308tifdomain.ie");
        newNotif.setErrorMessage("new erro\u0308r msg");
        dnsNotificationDAO.create(newNotif);
        DnsNotification dbNotif = dnsNotificationDAO.get(newNotif.getId());
        Assert.assertEquals(dbNotif.getDomainName(), "newnötifdomain.ie");
        Assert.assertEquals(dbNotif.getNsName(), "ns.newnötifdomain.ie");
        Assert.assertEquals(dbNotif.getErrorMessage(), "new errör msg");
    }

    @Test
    public void testDeleteUnnormalizedUtf8() {
        dnsNotificationDAO.removeObsoleteNotificationsForDomain("normno\u0308tifdomaine.ie",
                Collections.<String>emptySet());
        List<DnsNotification> notifs = dnsNotificationDAO.getByDomainName("normno\u0308tifdomaine.ie");
        Assert.assertTrue(notifs.isEmpty());
    }

    @Test
    public void testDeleteAllButUnnormalizedUtf8() {
        dnsNotificationDAO.removeObsoleteNotificationsForDomain("normalized-correct-o\u0303e\u0309.ie",
                Sets.newHashSet("ns.normalized-correct-o\u0303e\u0309.ie"));
        List<DnsNotification> notifs = dnsNotificationDAO.getByDomainName("normalized-correct-o\u0303e\u0309.ie");
        Assert.assertEquals(notifs.size(), 1);
    }

    @Test
    public void testGetUnnormalizedUtf8() {
        DnsNotification notif = dnsNotificationDAO.get(10);
        Assert.assertEquals(notif.getDomainName(), "unnormalized-name-õẻ.ie");
        Assert.assertEquals(notif.getNsName(), "ns.unnormalized-name-õẻ.ie");
        Assert.assertEquals(notif.getErrorMessage(), "unorm nötif error");
    }

    @Test
    public void testGetByDomainNameUnnormalizedUtf8() {
        List<DnsNotification> notifs = dnsNotificationDAO.getByDomainName("normalized-correct-o\u0303e\u0309.ie");
        Assert.assertEquals(notifs.size(), 1);
        DnsNotification notif = notifs.get(0);
        Assert.assertEquals(notif.getDomainName(), "normalized-correct-õẻ.ie");
        Assert.assertEquals(notif.getNsName(), "ns.normalized-correct-õẻ.ie");
        Assert.assertEquals(notif.getErrorMessage(), "norm nötif error");
    }

    @Test
    public void testGetNextNotificationDateUnnormalizedUtf8() {
        DnsNotificationDate date = dnsNotificationDAO.getNotificationDate("IDE\u03082-IEDR");
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        Assert.assertEquals(dt.format(date.getNextNotificationDate()), "2015-01-01");
    }

    @Test
    public void testUpdateNexNotificationDateUnnormalizedUtf8() {
        DnsNotificationDate notifDate = dnsNotificationDAO.getNotificationDate("IDE\u03082-IEDR");
        Date newDate = new Date();
        notifDate.setNextNotificationDate(newDate);
        dnsNotificationDAO.update(notifDate);
        DnsNotificationDate dbDate = dnsNotificationDAO.getNotificationDate("IDE\u03082-IEDR");
        Assert.assertEquals(dbDate.getNextNotificationDate(), truncate(newDate, Calendar.DATE));
    }

    private static class NotificationPredicate {
        static CollectionUtils.Predicate<DnsNotification> nsName(final String ns) {
            return new CollectionUtils.Predicate<DnsNotification>() {
                @Override
                public boolean test(DnsNotification elem) {
                    return elem.getNsName().equals(ns);
                }
            };
        }

        static CollectionUtils.Predicate<DnsNotification> domainName(final String domainName) {
            return new CollectionUtils.Predicate<DnsNotification>() {
                @Override
                public boolean test(DnsNotification elem) {
                    return elem.getDomainName().equals(domainName);
                }
            };
        }
    }
}
