package pl.nask.crs.dnscheck.impl;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.ContactType;
import pl.nask.crs.defaults.ResellerDefaults;
import pl.nask.crs.defaults.ResellerDefaultsService;
import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;
import pl.nask.crs.dnscheck.DnsNotification;
import pl.nask.crs.dnscheck.DnsNotificationDate;
import pl.nask.crs.dnscheck.DnsNotificationService;
import pl.nask.crs.dnscheck.dao.DnsNotificationDAO;
import pl.nask.crs.dnscheck.email.DnsNotificationEmailParameters;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.services.TicketSearchService;

public class DnsNotificationServiceImpl implements DnsNotificationService {

    private final static Logger LOG = Logger.getLogger(DnsNotificationServiceImpl.class);
    public static final String TIME_FORMAT = "HH:mm";
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    private DnsNotificationDAO dnsNotificationDAO;

    private EmailTemplateSender emailTemplateSender;

    private ResellerDefaultsService defaultsService;

    private TicketSearchService ticketSearchService;

    private NicHandleSearchService nicHandleSearchService;

    private DomainSearchService domainSearchService;

    private Comparator<DnsNotification> dnsNotificationComparator;

    public DnsNotificationServiceImpl(DnsNotificationDAO dnsNotificationDAO, EmailTemplateSender emailTemplateSender,
            ResellerDefaultsService defaultsService, TicketSearchService ticketSearchService,
            NicHandleSearchService nicHandleSearchService, DomainSearchService domainSearchService) {
        Validator.assertNotNull(dnsNotificationDAO, "dnsNotificationDAO");
        Validator.assertNotNull(emailTemplateSender, "emailTemplateSender");
        Validator.assertNotNull(defaultsService, "defaultsService");
        Validator.assertNotNull(ticketSearchService, "ticketSearchService");
        Validator.assertNotNull(nicHandleSearchService, "nicHandleSearchService");
        Validator.assertNotNull(domainSearchService, "domainSearchService");
        this.dnsNotificationDAO = dnsNotificationDAO;
        this.emailTemplateSender = emailTemplateSender;
        this.defaultsService = defaultsService;
        this.ticketSearchService = ticketSearchService;
        this.nicHandleSearchService = nicHandleSearchService;
        this.domainSearchService = domainSearchService;
        this.dnsNotificationComparator = createDnsNotificationComparator();
    }

    @Override
    public void createNotification(DnsNotification dnsNotification) {
        dnsNotificationDAO.create(dnsNotification);
    }

    @Override
    public void sendNotifications() {
        List<DnsNotification> notifications = dnsNotificationDAO.getAll();
        if (notifications.size() == 0) {
            LOG.info("No notifications found.");
            return;
        }
        NotificationsBundler bundler = new NotificationsBundler();
        for (DnsNotification notification : notifications) {
            bundler.processNotification(notification);
        }
        for (NotificationBundle bundle : bundler.getNotificationBundles()) {
            sendNotificationBundle(bundle);
        }
    }

    @Override
    public void removeObsoleteNotifications(String domainName, Long ticketNumber,
            Set<String> currentFailedNameservers) {
        Ticket ticket = getTicketForDomain(domainName);
        if (ticketNumber != null && ticket == null) {
            LOG.error(String.format(
                    "Failed to remove obsolete notifications due to a missing ticket: %s", ticketNumber));
            throw new IllegalStateException();
        }
        boolean removeForTicket = ticketNumber != null;
        boolean removeForDomain = ticketNumber == null;
        if (ticket != null && ticket.getOperation().getType() == DomainOperation.DomainOperationType.MOD) {
            removeForTicket = true;
            removeForDomain = true;
        }
        if (removeForTicket) {
            dnsNotificationDAO.removeObsoleteNotificationsForTicket(ticket.getId(), currentFailedNameservers);
        }
        if (removeForDomain) {
            dnsNotificationDAO.removeObsoleteNotificationsForDomain(domainName, currentFailedNameservers);
        }
    }

    private Comparator<DnsNotification> createDnsNotificationComparator() {
        return new Comparator<DnsNotification>() {
            @Override
            public int compare(DnsNotification o1, DnsNotification o2) {
                Date t1 = o1.getTimeOfCheck();
                Date t2 = o2.getTimeOfCheck();
                if (t1 == t2)
                    return 0;
                else if (t1 == null)
                    return 1;
                else if (t2 == null)
                    return -1;
                else
                    return t1.compareTo(t2);
            }
        };
    }

    private String prepareMessage(List<DnsNotification> domainNotifications) {
        StringBuilder message = new StringBuilder();
        Map<String, List<DnsNotification>> groupedNotifications = groupNotificationsByNameserver(domainNotifications);
        for (String nsName : groupedNotifications.keySet()) {
            List<DnsNotification> nsNotifications = groupedNotifications.get(nsName);
            SortedSet<DnsNotification> sortedUniqueNotifications = getSortedUniqueNotifications(nsNotifications);
            List<Date> failTimes = new ArrayList<>();
            for (DnsNotification notification : sortedUniqueNotifications) {
                final Date timeOfCheck = notification.getTimeOfCheck();
                if (timeOfCheck != null)
                    failTimes.add(timeOfCheck);
            }
            message.append("\nFor nameserver ").append(nsName);
            message.append(" ").append(formatNotificationDates(failTimes));
            message.append(". The most recent error messages are\n")
                    .append(sortedUniqueNotifications.last().getErrorMessage())
                    .append("\n");
        }
        return message.toString();
    }

    private String formatNotificationDates(List<Date> failTimes) {
        SortedMap<Date, SortedSet<Date>> dates = new TreeMap<>();
        final Date today = DateUtils.truncate(new Date(), Calendar.DATE);
        boolean allDatesToday = true;
        for (Date failTime : failTimes) {
            Date failDate = DateUtils.truncate(failTime, Calendar.DATE);
            allDatesToday = allDatesToday && failDate.equals(today);
            if (!dates.containsKey(failDate)) {
                dates.put(failDate, new TreeSet<Date>());
            }
            dates.get(failDate).add(failTime);
        }
        final Function<Date, String> dateToTimeString = new Function<Date, String>() {
            @Override
            public String apply(Date date) {
                return DateFormatUtils.format(date, TIME_FORMAT);
            }
        };

        if (allDatesToday) {
            List<String> failTimeStrings = Lists.transform(failTimes, dateToTimeString);
            return "at " + StringUtils.join(failTimeStrings, ", ");
        } else {
            List<String> allDatetimes = new ArrayList<>(dates.size());
            for (Date failDate : dates.keySet()) {
                String dateString = DateFormatUtils.format(failDate, DATE_FORMAT);
                String timesString = StringUtils.join(Iterables.transform(dates.get(failDate), dateToTimeString).iterator(), ", ");
                allDatetimes.add(String.format("on %s at %s", dateString, timesString));
            }
            return StringUtils.join(allDatetimes, ", ");
        }
    }

    private SortedSet<DnsNotification> getSortedUniqueNotifications(List<DnsNotification> notifications) {
        SortedSet<DnsNotification> sortedUniqueNotifications = new TreeSet<>(dnsNotificationComparator);
        sortedUniqueNotifications.addAll(notifications);
        return sortedUniqueNotifications;
    }

    private Map<String, List<DnsNotification>> groupNotificationsByNameserver(List<DnsNotification> allNotifications) {
        Map<String, List<DnsNotification>> groupedNotifications = new HashMap<>();
        for (DnsNotification notification : allNotifications) {
            final String nameserver = notification.getNsName();
            List<DnsNotification> nameserverNotifications = groupedNotifications.get(nameserver);
            if (nameserverNotifications == null) {
                nameserverNotifications = new ArrayList<>();
                groupedNotifications.put(nameserver, nameserverNotifications);
            }
            nameserverNotifications.add(notification);
        }

        return groupedNotifications;
    }

    private boolean isEligibleToSend(String nicHandleId) {
        ResellerDefaults resellerDefaults;
        try {
            resellerDefaults = defaultsService.get(nicHandleId);
        } catch (DefaultsNotFoundException e) {
            return true;
        }
        if (resellerDefaults.getDnsNotificationPeriod() == null) {
            return true;
        }
        DnsNotificationDate notificationDate = dnsNotificationDAO.getNotificationDate(nicHandleId);
        if (notificationDate == null) {
            createNextNotificationDate(nicHandleId, resellerDefaults.getDnsNotificationPeriod());
            return true;
        }
        if (notificationDate.getNextNotificationDate().before(new Date())) {
            updateNextNotificationDate(notificationDate, resellerDefaults.getDnsNotificationPeriod());
            return true;
        }
        return false;
    }

    private void createNextNotificationDate(String nicHandleId, int period) {
        DnsNotificationDate notificationDate = new DnsNotificationDate(nicHandleId, new Date());
        prepareNextNotificationDate(notificationDate, period);
        dnsNotificationDAO.createNotificatioDate(notificationDate);
    }

    private void prepareNextNotificationDate(DnsNotificationDate notificationDate, int period) {
        Date nextNotificationDate = DateUtils.addDays(notificationDate.getNextNotificationDate(), period);
        notificationDate.setNextNotificationDate(nextNotificationDate);
    }

    private void updateNextNotificationDate(DnsNotificationDate notificationDate, int period) {
        prepareNextNotificationDate(notificationDate, period);
        dnsNotificationDAO.update(notificationDate);
    }

    private void sendNotificationBundle(NotificationBundle bundle) {
        try {
            NicHandle techContact = bundle.getTechC();
            if (techContact != null && isEligibleToSend(techContact.getNicHandleId())) {
                String nicHandleId = techContact.getNicHandleId();
                String registrarNicHandleId = bundle.getBillCNic();
                String email = techContact.getEmail();
                String message = prepareMessage(bundle.getNotifications());
                DnsNotificationEmailParameters parameters = new DnsNotificationEmailParameters(nicHandleId,
                        registrarNicHandleId, email, bundle.getDomainName(), message);
                emailTemplateSender.sendEmail(EmailTemplateNamesEnum.DNS_NOTIFICATION.getId(), parameters);
            }
            removeNotifications(bundle.getNotifications());
        } catch (Exception e) {
            LOG.error("Problem with sending notification", e);
        }
    }

    private Ticket getTicketForDomain(String domainName) {
        try {
            return ticketSearchService.getTicketForDomain(domainName);
        } catch (TooManyTicketsException e) {
            LOG.error(String.format("Failed to update notifications for domain: %s", domainName), e);
            throw new IllegalStateException();
        }
    }

    private void removeNotifications(List<DnsNotification> notifications) {
        for (DnsNotification notification: notifications) {
            dnsNotificationDAO.deleteById(notification.getId());
        }
    }

    private class NotificationsBundler {
        private Map<Long, Ticket> ticketMap = new HashMap<>();
        private Map<String, Domain> domainMap = new HashMap<>();
        private Map<String, List<DnsNotification>> domainNotifications = new HashMap<>();
        private Map<Long, List<DnsNotification>> ticketNotifications = new HashMap<>();

        void processNotification(DnsNotification notification) {
            try {
                if (shouldSendToDomainContacts(notification)) {
                    processAsDomainNotification(notification);
                } else {
                    processAsTicketNotification(notification);
                }
            } catch (TicketNotFoundException e) {
                LOG.info(String.format("Skipping and removing DNS check notifications due to missing ticket (%s)",
                        notification.getTicketNumber()));
                dnsNotificationDAO.deleteById(notification.getId());
            }
        }

        List<NotificationBundle> getNotificationBundles() {
            List<NotificationBundle> bundles = new ArrayList<>();
            bundles.addAll(getDomainBundles());
            bundles.addAll(getTicketBundles());
            return bundles;
        }

        private boolean shouldSendToDomainContacts(DnsNotification notification) throws TicketNotFoundException {
            if (notification.getTicketNumber() == null) {
                return true;
            } else {
                Ticket ticket = getTicket(notification.getTicketNumber());
                return ticket.getOperation().getType() == DomainOperation.DomainOperationType.MOD;
            }
        }

        private void processAsDomainNotification(DnsNotification notification) {
            List<DnsNotification> notifications = domainNotifications.get(notification.getDomainName());
            if (notifications == null) {
                notifications = new ArrayList<>();
                domainNotifications.put(notification.getDomainName(), notifications);
            }
            notifications.add(notification);
        }

        private void processAsTicketNotification(DnsNotification notification) {
            List<DnsNotification> notifications = ticketNotifications.get(notification.getTicketNumber());
            if (notifications == null) {
                notifications = new ArrayList<>();
                ticketNotifications.put(notification.getTicketNumber(), notifications);
            }
            notifications.add(notification);
        }

        private List<NotificationBundle> getDomainBundles() {
            List<NotificationBundle> bundles = new ArrayList<>();
            for (Map.Entry<String, List<DnsNotification>> entry : domainNotifications.entrySet()) {
                NotificationBundle bundle = getDomainBundle(entry.getKey(), entry.getValue());
                if (bundle != null) {
                    bundles.add(bundle);
                }
            }
            return bundles;
        }

        private List<NotificationBundle> getTicketBundles() {
            List<NotificationBundle> bundles = new ArrayList<>();
            for (Map.Entry<Long, List<DnsNotification>> entry : ticketNotifications.entrySet()) {
                NotificationBundle bundle = getTicketBundle(entry.getKey(), entry.getValue());
                if (bundle != null) {
                    bundles.add(bundle);
                }
            }
            return bundles;
        }

        private NotificationBundle getDomainBundle(String domainName, List<DnsNotification> notifications) {
            try {
                NicHandle techC = getTechContactForDomain(domainName);
                String billCNic = getContactNicForDomain(domainName, ContactType.BILLING);
                return new NotificationBundle(domainName, techC, billCNic, notifications);
            } catch (DomainNotFoundException e) {
                LOG.info(String.format(
                        "Skipping and removing DNS check notifications due to missing domain %s", domainName));
            } catch (NicHandleNotFoundException e) {
                LOG.warn(String.format("Skipping and removing DNS check notifications due to missing tech contact "
                        + "for domain %s", domainName));
            }
            removeNotifications(notifications);
            return null;
        }

        private NotificationBundle getTicketBundle(long ticketNumber, List<DnsNotification> notifications) {
            try {
                String domainName = getDomainNameForTicket(ticketNumber);
                NicHandle techC = getTechContactForTicket(ticketNumber);
                String billCNic = getContactNicForTicket(ticketNumber, ContactType.BILLING);
                return new NotificationBundle(domainName, techC, billCNic, notifications);
            } catch (TicketNotFoundException e) {
                LOG.info(String.format(
                        "Skipping and removing DNS check notifications due to missing ticket (%s)", ticketNumber));
            } catch (NicHandleNotFoundException e) {
                LOG.warn(String.format("Skipping and removing DNS check notifications due to missing tech contact "
                        + "for ticket %s", ticketNumber));
            }
            removeNotifications(notifications);
            return null;
        }

        private NicHandle getTechContactForDomain(String domainName)
                throws NicHandleNotFoundException, DomainNotFoundException {
            String techCNic = getContactNicForDomain(domainName, ContactType.TECH);
            return nicHandleSearchService.getNicHandle(techCNic);
        }

        private NicHandle getTechContactForTicket(long ticketNumber)
                throws NicHandleNotFoundException, TicketNotFoundException {
            String techCNic = getContactNicForTicket(ticketNumber, ContactType.TECH);
            return nicHandleSearchService.getNicHandle(techCNic);
        }

        private String getContactNicForDomain(String domainName, ContactType contactType)
                throws DomainNotFoundException {
            Contact contact;
            Domain domain = getDomain(domainName);
            switch (contactType) {
                case BILLING:
                    contact = domain.getBillingContact();
                    break;
                case TECH:
                    contact = domain.getTechContact();
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            return contact.getNicHandle();
        }

        private String getContactNicForTicket(long ticketNumber, ContactType contactType)
                throws TicketNotFoundException {
            Contact contact;
            Ticket ticket = getTicket(ticketNumber);
            switch (contactType) {
                case BILLING:
                    contact = ticket.getOperation().getBillingContactsField().get(0).getNewValue();
                    break;
                case TECH:
                    contact = ticket.getOperation().getTechContactsField().get(0).getNewValue();
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            return contact.getNicHandle();
        }

        private String getDomainNameForTicket(long ticketNumber) throws TicketNotFoundException {
            return getTicket(ticketNumber).getOperation().getDomainNameField().getNewValue();
        }

        private Domain getDomain(String domainName) throws DomainNotFoundException {
            if (domainMap.containsKey(domainName)) {
                return domainMap.get(domainName);
            } else {
                Domain domain = domainSearchService.getDomain(domainName);
                domainMap.put(domainName, domain);
                return domain;
            }
        }

        private Ticket getTicket(long ticketNumber) throws TicketNotFoundException {
            if (ticketMap.containsKey(ticketNumber)) {
                return ticketMap.get(ticketNumber);
            } else {
                Ticket ticket = ticketSearchService.getTicket(ticketNumber);
                ticketMap.put(ticketNumber, ticket);
                return ticket;
            }
        }
    }

    private static class NotificationBundle {
        private String domainName;
        private NicHandle techC;
        private String billCNic;
        private List<DnsNotification> notifications;

        NotificationBundle(String domainName, NicHandle techC, String billCNic, List<DnsNotification> notifications) {
            this.domainName = domainName;
            this.techC = techC;
            this.billCNic = billCNic;
            this.notifications = notifications;
        }

        String getDomainName() {
            return domainName;
        }

        NicHandle getTechC() {
            return techC;
        }

        String getBillCNic() {
            return billCNic;
        }

        List<DnsNotification> getNotifications() {
            return notifications;
        }
    }

}
