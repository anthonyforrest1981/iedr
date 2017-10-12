package pl.nask.crs.domains.services.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.ValidationHelper;
import pl.nask.crs.app.commons.impl.TacDnsEmailParameters;
import pl.nask.crs.app.tickets.exceptions.DomainModificationPendingException;
import pl.nask.crs.app.tickets.exceptions.DomainTransferPendingException;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.AuthcodeGenerator;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.dns.NameserverValidator;
import pl.nask.crs.commons.email.service.*;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.commons.exceptions.WhoisProcessingException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.ContactValidator;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.CountryFactory;
import pl.nask.crs.domains.*;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dao.ExtendedDomainDAO;
import pl.nask.crs.domains.dao.HistoricalDomainDAO;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.dsm.DomainStateMachine;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.dsm.events.*;
import pl.nask.crs.domains.email.*;
import pl.nask.crs.domains.exceptions.*;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.domains.services.WhoisService;
import pl.nask.crs.entities.exceptions.CategoryDoesNotMatchSubcategoryException;
import pl.nask.crs.entities.exceptions.ClassDoesNotMatchCategoryException;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.secondarymarket.exceptions.SellRequestExistsException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.services.TicketSearchService;

public class DomainServiceImpl implements DomainService {

    private static final Logger LOG = Logger.getLogger(DomainSearchServiceImpl.class);
    public static final int AUTHCODE_LENGTH = 12;

    private DomainDAO domainDAO;
    private ExtendedDomainDAO extendedDomainDAO;
    private HistoricalDomainDAO historicalDomainDAO;
    private NicHandleSearchService nicHandleSearchService;
    private TicketSearchService ticketSearchService;
    private ApplicationConfig applicationConfig;

    private DomainStateMachine domainStateMachine;
    private EmailTemplateSender emailTemplateSender;
    private CountryFactory countryFactory;
    private WhoisService whoisService;

    private ServicesRegistry servicesRegistry;

    public DomainServiceImpl(DomainDAO domainDAO, ExtendedDomainDAO extendedDomainDAO,
            HistoricalDomainDAO historicalDomainDAO, NicHandleSearchService nicHandleSearchService,
            TicketSearchService ticketSearchService, ApplicationConfig applicationConfig,
            DomainStateMachine domainStateMachine, EmailTemplateSender emailTemplateSender,
            CountryFactory countryFactory, WhoisService whoisService) {
        Validator.assertNotNull(domainDAO, "domain dao");
        Validator.assertNotNull(extendedDomainDAO, "extended domain dao");
        Validator.assertNotNull(historicalDomainDAO, "historical domain dao");
        Validator.assertNotNull(ticketSearchService, "ticket search service");
        Validator.assertNotNull(nicHandleSearchService, "nichandle search service");
        Validator.assertNotNull(applicationConfig, "application config");
        Validator.assertNotNull(domainStateMachine, "domain state machine");
        Validator.assertNotNull(emailTemplateSender, "emailTemplateSender");
        Validator.assertNotNull(countryFactory, "countryFactory");
        Validator.assertNotNull(whoisService, "whoisService");
        this.domainDAO = domainDAO;
        this.extendedDomainDAO = extendedDomainDAO;
        this.historicalDomainDAO = historicalDomainDAO;
        this.nicHandleSearchService = nicHandleSearchService;
        this.ticketSearchService = ticketSearchService;
        this.applicationConfig = applicationConfig;
        this.domainStateMachine = domainStateMachine;
        this.emailTemplateSender = emailTemplateSender;
        this.countryFactory = countryFactory;
        this.whoisService = whoisService;
    }

    @Override
    public Domain lock(String domainName) throws DomainNotFoundException {
        if (domainDAO.lock(domainName)) {
            return domainDAO.get(domainName);
        } else {
            throw new DomainNotFoundException(domainName);
        }
    }

    @Override
    public void save(Domain domain, OpInfo opInfo)
            throws DomainNotFoundException, NicHandleException, ValidationException, EmptyRemarkException,
            ClassDoesNotMatchCategoryException, CategoryDoesNotMatchSubcategoryException {
        Validator.assertNotNull(domain, "domain");
        Validator.assertNotNull(opInfo, "opInfo");
        Domain currentDomainInDb = lock(domain.getName());
        ValidationHelper.checkIsEditable(currentDomainInDb);
        validateDomain(domain);
        updateDomainAndHistory(domain, opInfo);
    }

    @Override
    public void updateDomainAndHistory(Domain domain, OpInfo opInfo) {
        domain.updateChangeDate();
        domain.updateRemark(opInfo.getActorNameForRemark());
        long changeId = historicalDomainDAO.create(domain, domain.getChangeDate(), opInfo.getActorName());
        domainDAO.updateUsingHistory(changeId);
    }

    @Override
    public void updateDomainAndHistory(Domain domain, int targetState, OpInfo opInfo) {
        domain.updateChangeDate();
        domain.updateRemark(opInfo.getActorNameForRemark());
        long changeId = historicalDomainDAO.create(domain, targetState, domain.getChangeDate(), opInfo.getActorName());
        domainDAO.updateUsingHistory(changeId);
    }

    private void validateDomain(Domain domain)
            throws ValidationException, EmptyRemarkException, NicHandleException, ClassDoesNotMatchCategoryException,
            CategoryDoesNotMatchSubcategoryException {
        Validator.assertNotNull(domain.getName(), "domain name");
        ValidationHelper.validateHolder(domain.getHolder());
        validateRemark(domain.getRemark());
        Validator.assertNotNull(domain.getRegistrationDate(), "domain registration date");
        Validator.assertNotNull(domain.getRenewalDate(), "domain renewal date");
        Validator.assertNotNull(domain.getChangeDate(), "domain creation date");
        servicesRegistry.getEntityService().validateHolderEntities(domain.getHolderClass(), domain.getHolderCategory(),
                domain.getHolderSubcategory());
        ContactValidator.checkDomainContacts(domain, domain.getResellerAccount().getId(), servicesRegistry);
        NameserverValidator.checkNameservers(domain.getName(), domain.getNameservers(), servicesRegistry);
    }

    private void validateRemark(String remark) throws EmptyRemarkException {
        if (Validator.isEmpty(remark)) {
            throw new EmptyRemarkException();
        }
    }

    private void validateRemark(OpInfo opInfo) throws EmptyRemarkException {
        if (Validator.isEmpty(opInfo.getRemark())) {
            throw new EmptyRemarkException();
        }
    }

    private void validateTicketPending(String domainName)
            throws DomainModificationPendingException, DomainTransferPendingException, TooManyTicketsException {
        ticketSearchService.validateTicketPending(domainName);
    }

    private void runDsmEvents(AuthenticatedUser user, OpInfo opInfo, DsmEvent e, String... domainNames)
            throws DomainNotFoundException, DomainIllegalStateException {
        for (String d : domainNames) {
            domainStateMachine.handleEvent(user, d, e, opInfo);
        }
    }

    @Override
    public void enterVoluntaryNRP(AuthenticatedUser user, OpInfo opInfo, String... domainNames)
            throws DomainNotFoundException, DomainIllegalStateException {
        runDsmEvents(user, opInfo, EnterVoluntaryNRP.getInstance(), domainNames);
    }

    @Override
    public void removeFromVoluntaryNRP(AuthenticatedUser user, OpInfo opInfo, String... domainNames)
            throws DomainNotFoundException, DomainIllegalStateException {
        // validate domain's renew date: uc011sc01
        for (String domainName : domainNames) {
            Domain d = domainDAO.get(domainName);
            if (d.getDsmState().getNrpStatus().isVoluntaryNRP()
                    && d.getRenewalDate().before(pl.nask.crs.commons.utils.DateUtils.startOfDay(new Date()))
                    && d.getDsmState().getDomainHolderType() != DomainHolderType.Charity
                    && d.getDsmState().getDomainHolderType() != DomainHolderType.NonBillable) {
                throw new DomainIllegalStateException("Payment required for " + domainName, d.getName());
            }
        }
        runDsmEvents(user, opInfo, RemoveFromVoluntaryNRP.getInstance(), domainNames);
    }

    @Override
    public boolean isEventValid(String domainName, DsmEventName eventName) {
        return domainStateMachine.validateEvent(domainName, eventName);
    }

    @Override
    public void zonePublished(List<String> domainNames) {
        for (String d : domainNames) {
            domainDAO.zonePublish(d);
        }
    }

    @Override
    public void zoneCommit() {
        domainDAO.zoneCommit();
    }

    @Override
    public void zoneUnpublished(List<String> domainNames) {
        for (String d : domainNames) {
            domainDAO.zoneUnpublish(d);
        }
    }

    private void runDsmEvent(AuthenticatedUser user, String domainName, DsmEvent event, OpInfo opInfo)
            throws DomainNotFoundException, DomainIllegalStateException {
        if (domainStateMachine.validateEvent(domainName, event)) {
            domainStateMachine.handleEvent(user, domainName, event, opInfo);
        } else {
            LOG.warn(event.getName() + " is not valid event for domain: " + domainName);
        }
    }

    @Override
    public void runRenewalDatePasses(AuthenticatedUser user, String domainName, OpInfo opInfo)
            throws DomainNotFoundException, DomainIllegalStateException {
        runDsmEvent(user, domainName, new RenewalDatePasses(), opInfo);
    }

    @Override
    public void runSuspensionDatePasses(AuthenticatedUser user, String domainName, OpInfo opInfo)
            throws DomainNotFoundException, DomainIllegalStateException {
        runDsmEvent(user, domainName, new SuspensionDatePasses(), opInfo);
    }

    @Override
    public void runDeletionDatePasses(AuthenticatedUser user, String domainName, OpInfo opInfo)
            throws DomainNotFoundException, DomainIllegalStateException {
        runDsmEvent(user, domainName, new DeletionDatePasses(), opInfo);
    }

    @Override
    public void runDeletedDomainRemoval(AuthenticatedUser user, String domainName, OpInfo opInfo)
            throws DomainNotFoundException, DomainIllegalStateException {
        runDsmEvent(user, domainName, new DeletedDomainRemoval(), opInfo);
    }

    @Override
    public void forceDSMEvent(AuthenticatedUser user, List<String> domainNames, DsmEventName eventName, OpInfo opInfo)
            throws DomainNotFoundException, EmptyRemarkException, DomainIllegalStateException {
        validateRemark(opInfo);
        for (String domainName : domainNames) {
            domainStateMachine.handleEvent(user, domainName, eventName, opInfo);
            String nicHandle = domainDAO.get(domainName).getBillingContactNic();
            sendDSMEventForcedEmail(new DSMForceEmailParams(domainName, eventName, opInfo, nicHandle,
                    user.getUsername()));
        }
    }

    private void sendDSMEventForcedEmail(DSMForceEmailParams params) {
        try {
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.FORCE_DSM_EVENT.getId(), params);
        } catch (Exception e) {
            LOG.error("Error while sending force dsm event email", e);
        }
    }

    @Override
    public void forceDSMState(List<String> domainNames, int state, OpInfo opInfo)
            throws DomainNotFoundException, EmptyRemarkException {
        validateRemark(opInfo);
        for (String domainName : domainNames) {
            Domain domain = lock(domainName);
            domain.setRemark(opInfo.getRemark());
            updateDomainAndHistory(domain, state, opInfo);
        }
    }

    @Override
    public List<Integer> getDsmStates() {
        return domainDAO.getDsmStates();
    }

    @Override
    public void updateHolderType(AuthenticatedUser user, String domainName, DomainHolderType newHolderType,
            OpInfo opInfo) throws EmptyRemarkException, DomainIllegalStateException, ReservationPendingException,
            DomainNotFoundException {
        validateRemark(opInfo);
        DsmEventName eventName;
        switch (newHolderType) {
            case Billable:
                eventName = DsmEventName.SetBillable;
                break;
            case Charity:
                eventName = DsmEventName.SetCharity;
                break;
            case IEDRPublished:
                eventName = DsmEventName.SetIEDRPublished;
                break;
            case IEDRUnpublished:
                eventName = DsmEventName.SetIEDRUnpublished;
                break;
            case NonBillable:
                eventName = DsmEventName.SetNonBillable;
                break;
            case NA:
            default:
                throw new IllegalArgumentException("Holder type not supported: " + newHolderType);
        }
        /*
         * bug #11847 - the event may be performed only if there are no pending reservations!
         */
        assertNoPendingReservations(domainName,
                "Cannot change holder type - there are reservations waiting for settlement (payment is already initiated)");
        domainStateMachine.handleEvent(user, domainName, eventName, opInfo);
    }

    @Override
    public void assertNoPendingReservations(String domainName, String errorMsg) throws ReservationPendingException {
        ExtendedDomain domain = extendedDomainDAO.get(domainName);
        if (domain.hasPendingReservations()) {
            throw new ReservationPendingException(errorMsg, domainName);
        }
    }

    @Override
    public void lock(AuthenticatedUser user, String domainName, OpInfo opInfo)
            throws EmptyRemarkException, DomainIllegalStateException, DomainNotFoundException, EmailSendingException,
            TemplateNotFoundException, TemplateInstantiatingException, DomainModificationPendingException,
            DomainTransferPendingException, TooManyTicketsException {
        validateRemark(opInfo);
        Domain domain = lock(domainName);
        validateTicketPending(domainName);
        boolean enableLockingService = domain.getLockingDate() == null;
        if (enableLockingService) {
            Date lockingDate = new Date();
            domain.setLockingDate(lockingDate);
            domain.setLockingRenewalDate(lockingDate);
            domain.setRemark(opInfo.getRemark() + " (Enabling locking service)");
            updateDomainAndHistory(domain, opInfo);
        }
        domainStateMachine.handleEvent(user, domainName, DsmEventName.Lock, opInfo);
        if (enableLockingService) {
            // We're sending this email after triggering the Lock event to make sure the event has been handled
            // correctly and hasn't caused a rollback of the transaction.
            // We have to refetch the domain, because handleEvent doesn't update DsmState in domain object.
            domain = domainDAO.get(domainName);
            DomainDetailsEmailParams params = new DomainDetailsEmailParams(user.getUsername(), domain);
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.LOCKING_SERVICE_ENABLED.getId(), params);
        }
    }

    @Override
    public void rollLockRenewalDates(Map<String, Date> domainsNewLockingRenewalDates, OpInfo opInfo)
            throws EmailSendingException, TemplateNotFoundException, TemplateInstantiatingException,
            DomainIllegalStateException, DomainLockingRenewalDateOutOfBoundsException {
        final Map<String, List<Domain>> billingNhToDomains = new HashMap<>();
        final List<Domain> allDomains = new ArrayList<>();
        for (String domainName : domainsNewLockingRenewalDates.keySet()) {
            final Domain domain = domainDAO.get(domainName);
            String billingContactNic = domain.getBillingContactNic();
            if (!billingNhToDomains.containsKey(billingContactNic)) {
                billingNhToDomains.put(billingContactNic, new ArrayList<Domain>());
            }
            billingNhToDomains.get(billingContactNic).add(domain);
            allDomains.add(domain);
        }
        for (Domain domain : allDomains) {
            if (domain.getLockingRenewalDate() == null) {
                throw new DomainIllegalStateException(
                        "Cannot prolong locking service for the domain because it doesn't use the service",
                        domain.getName());
            }
            Date newRenewalDate = domainsNewLockingRenewalDates.get(domain.getName());
            if (domain.getLockingDate().after(newRenewalDate)) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
                throw new DomainLockingRenewalDateOutOfBoundsException("Domain's locking date "
                        + format.format(domain.getLockingDate())
                        + " is further in the future than proposed new locking renewal date "
                        + format.format(newRenewalDate), domain.getName());
            }
            domain.setRemark("Roll locking service dates");
            domain.setLockingRenewalDate(newRenewalDate);
            updateDomainAndHistory(domain, opInfo);
        }
        for (List<Domain> domainsOfOneBillNH : billingNhToDomains.values()) {
            DomainsLockingServiceSummaryEmailParameters params = new DomainsLockingServiceSummaryEmailParameters(
                    opInfo.getActorName(), domainsOfOneBillNH);
            emailTemplateSender
                    .sendEmail(EmailTemplateNamesEnum.LOCKING_SERVICE_PROLONGED_CONFIRMATION.getId(), params);
        }
    }

    @Override
    public void unlock(AuthenticatedUser user, String domainName, OpInfo opInfo, boolean disableLockingService)
            throws EmptyRemarkException, DomainIllegalStateException, DomainNotFoundException, EmailSendingException,
            TemplateNotFoundException, TemplateInstantiatingException {
        validateRemark(opInfo);
        domainStateMachine.handleEvent(user, domainName, DsmEventName.Unlock, opInfo);
        Domain domain = lock(domainName);
        if (disableLockingService) {
            domain.setLockingDate(null);
            domain.setLockingRenewalDate(null);
            domain.setRemark(opInfo.getRemark() + " (Disabling locking service)");
            updateDomainAndHistory(domain, opInfo);
            DomainDetailsEmailParams params = new DomainDetailsEmailParams(user.getUsername(), domain);
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.LOCKING_SERVICE_DISABLED.getId(), params);
        }
    }

    @Override
    public void revertToBillable(AuthenticatedUser user, List<String> domainNames, OpInfo opInfo)
            throws DomainNotFoundException, DomainIllegalStateException {
        for (String domainName : domainNames) {
            domainStateMachine.handleEvent(user, domainName, DsmEventName.SetBillable, opInfo);
        }
    }

    @Override
    public void sendNotification(Domain domain, AuthenticatedUser user, int period) throws DomainEmailException {
        DomainNotification notification = domainDAO.getDomainNotification(domain.getName(), period);
        if (notification == null) {
            sendNotificationEmails(domain, user, period);
            Date expirationDate = DateUtils.addDays(new Date(), period);
            notification = new DomainNotification(domain.getName(), period, expirationDate);
            domainDAO.createNotification(notification);
        } else {
            LOG.info("Notification: " + notification.toString() + " already sent.");
        }
    }

    @Override
    public void sendNotification(Domain domain, AuthenticatedUser user) throws DomainEmailException {
        sendNotificationEmails(domain, user, null);
    }

    private void sendNotificationEmails(Domain domain, AuthenticatedUser user, Integer period)
            throws DomainEmailException {
        try {
            sendEmailToContact(domain, user, period);
        } catch (Exception e) {
            throw new DomainEmailException("Error while sending notification email for domain: " + domain.getName(), e);
        }
    }

    private void sendEmailToContact(Domain domain, AuthenticatedUser user, Integer period)
            throws EmailSendingException, TemplateNotFoundException, TemplateInstantiatingException,
            IllegalArgumentException {
        DomainDetailsEmailParams params = new DomainDetailsEmailParams(user.getUsername(), domain);
        if (period == null) {
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.DOMAIN_EXPIRED.getId(), params);
        } else {
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.RENEWAL_NOTIFICATION.getId(), params);
        }
    }

    @Override
    public List<String> checkEventAvailable(List<String> domainNames, DsmEventName eventName) {
        List<String> invalidDomains = new ArrayList<String>();
        for (String domainName : domainNames) {
            if (!domainStateMachine.validateEvent(domainName, eventName)) {
                invalidDomains.add(domainName + " ("
                        + domainDAO.get(domainName).getDsmState().getNrpStatus().shortDescription() + ")");
            }
        }
        return invalidDomains;
    }

    @Override
    public void modifyRenewalMode(AuthenticatedUser user, List<String> domainNames, RenewalMode renewalMode,
            OpInfo opInfo) throws DomainNotFoundException, DomainIllegalStateException {
        for (String domainName : domainNames) {
            Domain domain = domainDAO.get(domainName);
            if (renewalMode != null && !renewalMode.equals(domain.getDsmState().getRenewalMode())) {
                switch (renewalMode) {
                    case NoAutorenew:
                        domainStateMachine.handleEvent(user, domainName, new SetNoAutoRenew(), opInfo);
                        break;
                    case RenewOnce:
                        domainStateMachine.handleEvent(user, domainName, new SetOnceAutoRenew(), opInfo);
                        break;
                    case Autorenew:
                        domainStateMachine.handleEvent(user, domainName, new SetAutoRenew(), opInfo);
                        break;
                    case NA:
                    default:
                        throw new IllegalStateException("Illegal renewal mode: " + renewalMode);
                }
            }
        }
    }

    @Override
    public List<Country> getCountries() {
        return countryFactory.getCountries();
    }

    @Override
    public void modifyRemark(AuthenticatedUser user, String domainName, String remark)
            throws DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException,
            EmptyRemarkException, DomainLockedException {
        Domain domain = lock(domainName);
        domain.setRemark(remark);
        updateDomainAndHistory(domain, new OpInfo(user));
    }

    @Override
    public void verifyAuthCode(AuthenticatedUser user, String domainName, String authCode)
            throws InvalidAuthCodeException, IllegalArgumentException, TemplateNotFoundException,
            TemplateInstantiatingException, EmailSendingException {
        verifyAuthCode(user, domainName, authCode, 0);
    }

    @Override
    public void verifyAuthCode(AuthenticatedUser user, String domainName, String authCode, int failureCount)
            throws InvalidAuthCodeException, IllegalArgumentException, TemplateNotFoundException,
            TemplateInstantiatingException, EmailSendingException {
        String domainAuthCode = domainDAO.get(domainName).getAuthCode();
        if (authCode == null || domainAuthCode == null || !authCode.equals(domainAuthCode)) {
            int failureLimit = applicationConfig.getAuthCodeFailureLimit();
            if (failureCount >= failureLimit) {
                sendViolationEmail(user, domainName);
            }
            LOG.warn("Incorrect authcode: " + authCode + " entered for the domain: " + domainName);
            throw new InvalidAuthCodeException(domainName);
        };
    }

    @Override
    public void sendAuthCodeByEmail(String domainName, OpInfo opInfo)
            throws TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException,
            DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException,
            IllegalArgumentException, DomainLockedException, AuthcodeGenerationDomainStateException {
        getOrCreateAuthCode(domainName, opInfo);
        AuthCodeEmailParameters params = new AuthCodeEmailParameters(opInfo.getUserName(), domainDAO.get(domainName));
        emailTemplateSender.sendEmail(EmailTemplateNamesEnum.AUTHCODE_ON_DEMAND.getId(), params);
    }

    private void sendViolationEmail(AuthenticatedUser user, String domainName)
            throws TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException {
        AuthCodeEmailParameters params = new AuthCodeEmailParameters(user.getUsername(), domainDAO.get(domainName));
        emailTemplateSender.sendEmail(EmailTemplateNamesEnum.AUTHCODE_VIOLATION.getId(), params);
    }

    @Override
    public void sendAuthCodeFromPortal(String domainName, String emailAddress, OpInfo opInfo)
            throws TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException,
            DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException,
            AuthCodePortalLimitException, DomainLockedException, AuthcodeGenerationDomainStateException {
        Domain domain = lock(domainName);
        int portalLimit = applicationConfig.getAuthCodePortalLimit();
        if (domain.getAuthCodePortalCount() >= portalLimit) {
            throw new AuthCodePortalLimitException();
        }
        getOrCreateAuthCode(domain, true, opInfo);
        AuthCodeEmailParameters params = new AuthCodeEmailParameters(null, domainDAO.get(domainName), emailAddress);
        emailTemplateSender.sendEmail(EmailTemplateNamesEnum.AUTHCODE_FROM_PORTAL.getId(), params);
    }

    @Override
    public AuthCode getOrCreateAuthCode(String domainName, OpInfo opInfo)
            throws DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException,
            EmptyRemarkException, DomainLockedException, AuthcodeGenerationDomainStateException {
        Domain domain = lock(domainName);
        return getOrCreateAuthCode(domain, false, opInfo);
    }

    private AuthCode getOrCreateAuthCode(Domain domain, boolean portal, OpInfo opInfo)
            throws DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException,
            EmptyRemarkException, DomainLockedException, AuthcodeGenerationDomainStateException {
        try {
            ValidationHelper.checkIsEditable(domain);
        } catch (DomainLockedException | SellRequestExistsException e) {
            throw new AuthcodeGenerationDomainStateException(domain.getName());
        }
        String authCode = domain.getAuthCode();
        Date oldExpirationDate = domain.getAuthCodeExpirationDate();
        int expirationPeriod = applicationConfig.getAuthCodeExpirationPeriod();
        Date newExpirationDate = DateUtils.addDays(new Date(), expirationPeriod);
        String remark;
        if (authCode == null) {
            authCode = AuthcodeGenerator.generateRandom(AUTHCODE_LENGTH);
            domain.setAuthCode(authCode);
            remark = "Authcode generated";
        } else if (!DateUtils.isSameDay(oldExpirationDate, newExpirationDate)) {
            remark = "Authcode validity prolonged";
        } else {
            remark = "Authcode queried";
        }
        domain.setAuthCodeExpirationDate(newExpirationDate);
        if (portal) {
            int count = domain.getAuthCodePortalCount();
            domain.setAuthCodePortalCount(count + 1);
        }
        domain.setRemark(remark);
        updateDomainAndHistory(domain, opInfo);
        return new AuthCode(authCode, newExpirationDate);
    }

    @Override
    public void clearAuthCode(String domainName, OpInfo opInfo)
            throws DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException,
            EmptyRemarkException, DomainLockedException {
        Domain domain = lock(domainName);
        domain.setAuthCode(null);
        domain.setAuthCodeExpirationDate(null);
        domain.setRemark("Authcode expired");
        updateDomainAndHistory(domain, opInfo);
    }

    @Override
    public void clearAuthCodePortalCount(String domainName, OpInfo opInfo)
            throws DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException,
            EmptyRemarkException, DomainLockedException {
        Domain domain = lock(domainName);
        domain.setAuthCodePortalCount(0);
        domain.setRemark("Authcode Portal count cleared");
        updateDomainAndHistory(domain, opInfo);
    }

    @Override
    public void sendBulkAuthCodesByEmail(AuthenticatedUser user, Contact billingContact, Contact adminContact,
            List<Domain> domainList)
            throws TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException {
        DomainListEmailParams params = new DomainListEmailParams(user.getUsername(), billingContact, adminContact,
                domainList);
        emailTemplateSender.sendEmail(EmailTemplateNamesEnum.AUTHCODE_BULK_EXPORT.getId(), params);
    }

    @Override
    public void createTransferRecord(String domainName, Date transferDate, String oldBillC, String newBillC) {
        domainDAO.createTransferRecord(domainName, transferDate, oldBillC, newBillC);
    }

    @Override
    public void updateNameserversForDomainList(AuthenticatedUser user, List<String> domainNames,
            List<Nameserver> nameservers, String customerRemark)
            throws NicHandleNotFoundException, NicHandleNotActiveException, DomainNotFoundException,
            EmptyRemarkException, DomainLockedException {
        String requesterRemark = Validator.isEmpty(customerRemark) ? "DNS change made" : customerRemark;
        Map<String, List<Nameserver>> nameserverMap = new HashMap<>();
        for (String domainName : domainNames) {
            Domain domain = lock(domainName);
            nameserverMap.put(domainName, new ArrayList<>(domain.getNameservers()));
            domain.setRemark(requesterRemark);
            domain.setNameservers(nameservers);
            updateDomainAndHistory(domain, new OpInfo(user));
        }
        for (String domainName : domainNames) {
            sendNameserverModificationEmail(user, domainName, nameserverMap.get(domainName));
        }
    }

    private void sendNameserverModificationEmail(AuthenticatedUser user, String domainName, List<Nameserver> oldList)
            throws NicHandleNotFoundException {
        Domain domain = domainDAO.get(domainName);
        NicHandle creator = nicHandleSearchService.getNicHandle(user.getUsername());

        EmailParameters params = new TacDnsEmailParameters(user.getUsername(), creator, domain, oldList);

        try {
            String billingNh = domain.getBillingContact().getNicHandle();
            boolean modifiedByBillC = billingNh.equalsIgnoreCase(user.getUsername());
            EmailTemplateNamesEnum template;

            if (nicHandleSearchService.isNicHandleDirect(billingNh)) {
                if (modifiedByBillC) {
                    template = EmailTemplateNamesEnum.TAC_DNS_MOD_DIRECT_BILL;
                } else {
                    template = EmailTemplateNamesEnum.TAC_DNS_MOD_DIRECT_AT;
                }
            } else {
                if (modifiedByBillC) {
                    template = EmailTemplateNamesEnum.TAC_DNS_MOD_REG_BILL;
                } else {
                    template = EmailTemplateNamesEnum.TAC_DNS_MOD_REG_AT;
                }
            }
            emailTemplateSender.sendEmail(template.getId(), params);
        } catch (Exception e) {
            LOG.warn("Error sending email notification", e);
        }
    }

    @Override
    public void create(NewDomain newDomain, OpInfo opInfo) {
        Date changeDate = new Date();
        Domain domain = convertNewDomain(newDomain, changeDate);
        domainDAO.create(domain);
        long changeId = historicalDomainDAO.create(domain, changeDate, opInfo.getActorName());
        domainDAO.updateUsingHistory(changeId);
    }

    @Override
    public void delete(AuthenticatedUser user, Domain domain, OpInfo opInfo) {
        servicesRegistry.getSecondaryMarketService().deleteAllRequests(user, domain, opInfo);
        domainDAO.deleteById(domain.getName());
    }

    private Domain convertNewDomain(NewDomain newDomain, Date changeDate) {
        List<Contact> adminContacts = stringsToContacts(newDomain.getAdminContacts());
        List<Contact> techContacts = stringsToContacts(newDomain.getTechContacts());
        List<Contact> billingContacts = stringsToContacts(newDomain.getBillingContacts());
        Contact creator = new Contact(newDomain.getCreator());
        Account account = new Account(newDomain.getResellerAccountId());
        Date registrationDate = changeDate;
        Date renewalDate = changeDate;
        boolean clikPaid = false;
        boolean zonePublished = false;
        Date lockingDate = null;
        Date lockingRenewalDate = null;
        Domain domain = new Domain(newDomain.getName(), newDomain.getHolder(), newDomain.getHolderClass(),
                newDomain.getHolderCategory(), newDomain.getHolderSubcategory(), creator, account, registrationDate,
                renewalDate, newDomain.getRemark(), changeDate, clikPaid, techContacts, billingContacts, adminContacts,
                newDomain.getNameservers(), DsmState.initialState(), zonePublished, lockingDate, lockingRenewalDate);
        domain.setAuthCodePortalCount(0);
        return domain;
    }

    private List<Contact> stringsToContacts(List<String> contacts) {
        return Lists.transform(contacts, new Function<String, Contact>() {
            @Override
            public Contact apply(String contact) {
                return new Contact(contact);
            }
        });
    }

    @Override
    public void sendWhoisDataEmail(AuthenticatedUser user, String domainName)
            throws TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException, IOException,
            InterruptedException, WhoisProcessingException {
        String whoisData = whoisService.generateWhoisResponse(domainName);
        WhoisDataEmailParameters params = new WhoisDataEmailParameters(user.getUsername(), domainDAO.get(domainName),
                whoisData);
        emailTemplateSender.sendEmail(EmailTemplateNamesEnum.WHOIS_DATA.getId(), params);
    }

    public ServicesRegistry getServicesRegistry() {
        return servicesRegistry;
    }

    public void setServicesRegistry(ServicesRegistry servicesRegistry) {
        this.servicesRegistry = servicesRegistry;
    }

}
