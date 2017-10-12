package pl.nask.crs.app.domains.impl;

import java.io.IOException;
import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.ValidationHelper;
import pl.nask.crs.app.commons.RelatedDomains;
import pl.nask.crs.app.commons.exceptions.DomainNotBillableException;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.domains.DomainAvailability;
import pl.nask.crs.app.domains.ExtendedDomainInfo;
import pl.nask.crs.app.tickets.exceptions.DomainManagedByAnotherResellerException;
import pl.nask.crs.app.tickets.exceptions.DomainModificationPendingException;
import pl.nask.crs.app.tickets.exceptions.DomainTransferPendingException;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.WhoisProcessingException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.ContactType;
import pl.nask.crs.country.Country;
import pl.nask.crs.documents.service.DocumentService;
import pl.nask.crs.domains.*;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.exceptions.*;
import pl.nask.crs.domains.nameservers.NsReport;
import pl.nask.crs.domains.search.*;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.entities.exceptions.CategoryDoesNotMatchSubcategoryException;
import pl.nask.crs.entities.exceptions.ClassDoesNotMatchCategoryException;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.secondarymarket.exceptions.SellRequestExistsException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.search.TicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketSearchService;

import static org.apache.commons.lang.time.DateUtils.addDays;

import static pl.nask.crs.app.utils.UserValidator.validateLoggedIn;
import static pl.nask.crs.commons.utils.DateUtils.*;

public class DomainAppServiceImpl implements DomainAppService {

    private final static Logger LOG = Logger.getLogger(DomainAppServiceImpl.class);

    private static final int MAX_DOMAINS_IN_INFO = 100;

    // Contact types that may be used for filtering while searching for domains ("C" value is no longer used)
    private final List<ContactType> allowedContactTypes = Arrays.asList(ContactType.TECH, ContactType.ADMIN,
            ContactType.BILLING);

    private DomainSearchService domainSearchService;
    private DomainService domainService;
    private DocumentService documentService;
    private TicketSearchService ticketSearchService;
    private NicHandleSearchService nicHandleSearchService;
    private ApplicationConfig applicationConfig;

    public DomainAppServiceImpl(DomainSearchService domainSearchService, DocumentService documentService,
            TicketSearchService ticketSearchService, DomainService domainService,
            NicHandleSearchService nicHandleSearchService, ApplicationConfig applicationConfig) {
        Validator.assertNotNull(domainSearchService, "domain search service");
        Validator.assertNotNull(documentService, "document service");
        Validator.assertNotNull(ticketSearchService, "ticket search service");
        Validator.assertNotNull(domainService, "domain service");
        Validator.assertNotNull(applicationConfig, "applicationConfig");
        this.domainSearchService = domainSearchService;
        this.documentService = documentService;
        this.ticketSearchService = ticketSearchService;
        this.domainService = domainService;
        this.nicHandleSearchService = nicHandleSearchService;
        this.applicationConfig = applicationConfig;
    }

    @Override
    @Transactional(readOnly = true)
    public ExtendedDomainInfo view(AuthenticatedUser user, String domainName)
            throws AccessDeniedException, DomainNotFoundException {
        validateLoggedIn(user);
        return getDomainWrapper(domainName);
    }

    @Override
    @Transactional(readOnly = true)
    public Domain viewPlain(AuthenticatedUser user, String domainName)
            throws AccessDeniedException, DomainNotFoundException {
        validateLoggedIn(user);
        return domainSearchService.getDomain(domainName);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Domain> getAll(AuthenticatedUser user, List<String> selected)
            throws AccessDeniedException, DomainNotFoundException {
        validateLoggedIn(user);
        List<Domain> result = new ArrayList<>();
        for (String domainName : selected) {
            result.add(domainSearchService.getDomain(domainName));
        }
        return result;
    }

    private ExtendedDomainInfo getDomainWrapper(String domainName) throws DomainNotFoundException {
        Domain domain = domainSearchService.getDomain(domainName);
        ExtendedDomainInfo domainWrapper = new ExtendedDomainInfo(domain);
        domainWrapper.setDocuments(documentService.hasDocumentsForDomain(domainName));
        domainWrapper.setTickets(hasTicketsForDomain(domainName));
        addDomainHoldersDomains(domainName, domain.getHolder(), domainWrapper);
        return domainWrapper;
    }

    private boolean hasTicketsForDomain(String domainName) {
        //find tickets
        TicketSearchCriteria ticketsForDomain = new TicketSearchCriteria();
        ticketsForDomain.setDomainName(domainName);
        Ticket ticket;
        try {
            ticket = ticketSearchService.getTicketForDomain(domainName);
        } catch (TooManyTicketsException e) {
            return true;
        }
        return (ticket != null);
    }

    @Override
    @Transactional(readOnly = true)
    public ExtendedDomainInfo edit(AuthenticatedUser user, String domainName)
            throws AccessDeniedException, DomainNotFoundException {
        validateLoggedIn(user);
        return getDomainWrapper(domainName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(AuthenticatedUser user, Domain domain)
            throws AccessDeniedException, NicHandleException, DomainNotFoundException, ValidationException,
            EmptyRemarkException, ClassDoesNotMatchCategoryException, CategoryDoesNotMatchSubcategoryException {
        validateLoggedIn(user);
        domainService.save(domain, new OpInfo(user));
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<Domain> search(AuthenticatedUser user, DomainSearchCriteria criteria, long offset,
            long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
        validateLoggedIn(user);
        applyFilter(criteria);
        return domainSearchService.find(criteria, offset, limit, orderBy);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<Domain> searchFull(AuthenticatedUser user, DomainSearchCriteria criteria, long offset,
            long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
        validateLoggedIn(user);
        applyFilter(criteria);
        return domainSearchService.fullFind(criteria, offset, limit, orderBy);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<Domain> searchFullWithLockingActive(AuthenticatedUser user,
            DomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy)
            throws AccessDeniedException {
        validateLoggedIn(user);
        applyFilter(criteria);
        criteria.setLockingActive(true);
        return domainSearchService.fullFind(criteria, offset, limit, orderBy);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<PlainDomain> findTransferredInDomains(AuthenticatedUser user,
            PlainDomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException {
        validateLoggedIn(user);
        criteria.setBillingNH(user.getUsername());
        criteria.filterValues();
        return domainSearchService.findTransferredInDomains(criteria, offset, limit, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<PlainDomain> findTransferredAwayDomains(AuthenticatedUser user,
            PlainDomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
            throws AccessDeniedException {
        validateLoggedIn(user);
        criteria.setBillingNH(user.getUsername());
        criteria.filterValues();
        return domainSearchService.findTransferredAwayDomains(criteria, offset, limit, sortBy);
    }

    private void addDomainHoldersDomains(String domainName, String domainHolder, ExtendedDomainInfo domainWrapper) {
        Validator.assertNotNull(domainName, "domain name");

        // find the domains owned by the same domain holder as domainHolder
        Collection<String> domainNames = new TreeSet<>();
        Collection<String> pendingDomainNames = new TreeSet<>();
        if (domainHolder != null) {
            domainNames.addAll(domainSearchService.findDomainsForHolder(domainHolder, MAX_DOMAINS_IN_INFO, domainName));
            pendingDomainNames.addAll(ticketSearchService.findPendingDomainsForHolder(domainHolder,
                    MAX_DOMAINS_IN_INFO, domainName));
        }
        domainWrapper.setRelatedDomainNames(domainNames);
        domainWrapper.setPendingDomainNames(pendingDomainNames);
    }

    @Override
    @Transactional
    public void enterVoluntaryNRP(AuthenticatedUser user, String... domainNames)
            throws UserNotAuthenticatedException, DomainNotFoundException, DomainIllegalStateException {
        validateLoggedIn(user);
        domainService.enterVoluntaryNRP(user, new OpInfo(user), domainNames);
    }

    @Override
    @Transactional
    public void removeFromVoluntaryNRP(AuthenticatedUser user, String... domainNames)
            throws UserNotAuthenticatedException, DomainNotFoundException, DomainIllegalStateException {
        validateLoggedIn(user);
        domainService.removeFromVoluntaryNRP(user, new OpInfo(user), domainNames);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isEventValid(AuthenticatedUser user, String domainName, DsmEventName eventName)
            throws UserNotAuthenticatedException {
        validateLoggedIn(user);
        return domainService.isEventValid(domainName, eventName);
    }

    @Override
    @Transactional(readOnly = true)
    public DomainAvailability checkAvailability(AuthenticatedUser user, String domainName)
            throws UserNotAuthenticatedException {
        validateLoggedIn(user);
        try {
            boolean domainCreated = isDomainCreated(domainName);
            boolean domainManagedByUser = domainCreated && isManagedByUser(domainName, user.getUsername());
            return new DomainAvailability(domainName, domainCreated, isRegTicketCreated(domainName),
                    user.getUsername(), domainManagedByUser, false);
        } catch (Exception e) {
            LOG.error("Error while checking domain availability: " + domainName, e);
            return new DomainAvailability(domainName, false, false, user.getUsername(), false, true);
        }
    }

    private boolean isRegTicketCreated(String domainName) throws TooManyTicketsException {
        Ticket ticket = ticketSearchService.getTicketForDomain(domainName);
        return (ticket != null && ticket.getOperation().getType() == DomainOperationType.REG);
    }

    private boolean isDomainCreated(String domainName) {
        try {
            domainSearchService.getDomain(domainName);
            return true;
        } catch (DomainNotFoundException e) {
            return false;
        }
    }

    private boolean isManagedByUser(String domainName, String userName) {
        try {
            NicHandle nicHandle = nicHandleSearchService.getNicHandle(userName);
            Domain domain = domainSearchService.getDomain(domainName);
            if (nicHandleSearchService.isNicHandleDirect(nicHandle)) {
                String domainBillingNH = domain.getBillingContacts().get(0).getNicHandle();
                return domainBillingNH.equalsIgnoreCase(userName);
            } else {
                return nicHandle.getAccount().getId() == domain.getResellerAccount().getId();
            }
        } catch (DomainNotFoundException e) {
            return false;
        } catch (NicHandleNotFoundException e) {
            // should never happen
            throw new IllegalStateException(e);
        }
    }

    @Override
    @Transactional
    public void runDeleteProcess(AuthenticatedUser user, OpInfo opInfo, String domainName) {
        try {
            domainService.runDeletedDomainRemoval(user, domainName, opInfo);
        } catch (Exception e) {
            LOG.error("Exception during running DeletedDomainRemoval event!", e);
        }
    }

    @Override
    @Transactional
    public void runRenewalDatePasses(AuthenticatedUser user, OpInfo opInfo, String domainName) {
        try {
            domainService.runRenewalDatePasses(user, domainName, opInfo);
        } catch (Exception e) {
            LOG.error("Exception during running RenewaDatePasses event!", e);
        }
    }

    @Override
    @Transactional
    public void runSuspensionDatePasses(AuthenticatedUser user, OpInfo opInfo, String domainName) {
        try {
            domainService.runSuspensionDatePasses(user, domainName, opInfo);
        } catch (Exception e) {
            LOG.error("Exception during running SuspensionDatePasses event!", e);
        }
    }

    @Override
    @Transactional
    public void runDeletionDatePasses(AuthenticatedUser user, OpInfo opInfo, String domainName) {
        try {
            domainService.runDeletionDatePasses(user, domainName, opInfo);
        } catch (Exception e) {
            LOG.error("Exception during running DeletionDatePasses event!", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void forceDSMEvent(AuthenticatedUser user, List<String> domainNames, DsmEventName eventName, String remark)
            throws DomainNotFoundException, EmptyRemarkException, DomainIllegalStateException,
            UserNotAuthenticatedException {
        validateLoggedIn(user);
        domainService.forceDSMEvent(user, domainNames, eventName, new OpInfo(user, remark));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void forceDSMState(AuthenticatedUser user, List<String> domainNames, int state, String remark)
            throws DomainNotFoundException, EmptyRemarkException, UserNotAuthenticatedException {
        validateLoggedIn(user);
        domainService.forceDSMState(domainNames, state, new OpInfo(user, remark));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Integer> getDsmStates(AuthenticatedUser user) throws UserNotAuthenticatedException {
        validateLoggedIn(user);
        return domainService.getDsmStates();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateHolderType(AuthenticatedUser user, String domainName, DomainHolderType newHolderType,
            String remark)
            throws EmptyRemarkException, ReservationPendingException, DomainIllegalStateException,
            DomainNotFoundException {
        domainService.updateHolderType(user, domainName, newHolderType, new OpInfo(user, remark));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lock(AuthenticatedUser user, String domainName, String hostmastersRemark)
            throws EmptyRemarkException, DomainIllegalStateException, DomainNotFoundException,
            TemplateNotFoundException, EmailSendingException, TemplateInstantiatingException,
            DomainModificationPendingException, DomainTransferPendingException, TooManyTicketsException {
        domainService.lock(user, domainName, new OpInfo(user, hostmastersRemark));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlock(AuthenticatedUser user, String domainName, String hostmastersRemark,
            boolean disableLockingService)
            throws EmptyRemarkException, DomainIllegalStateException, DomainNotFoundException,
            TemplateNotFoundException, EmailSendingException, TemplateInstantiatingException {
        domainService.unlock(user, domainName, new OpInfo(user, hostmastersRemark), disableLockingService);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rollLockRenewalDates(AuthenticatedUser user, Map<String, Date> domainsNewLockingRenewalDates)
            throws EmailSendingException, TemplateNotFoundException, TemplateInstantiatingException,
            DomainIllegalStateException, DomainLockingRenewalDateOutOfBoundsException {
        domainService.rollLockRenewalDates(domainsNewLockingRenewalDates, new OpInfo(user));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void revertToBillable(AuthenticatedUser user, List<String> domainNames)
            throws DomainNotFoundException, DomainIllegalStateException {
        domainService.revertToBillable(user, domainNames, new OpInfo(user));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void runNotificationProcess(AuthenticatedUser user) throws UserNotAuthenticatedException {
        validateLoggedIn(user);
        List<Integer> renewalNotificationPeriods = applicationConfig.getRenewalNotificationPeriods();
        sendNotificationForExpiredDomains(user);
        List<Integer> periods = new ArrayList<>();
        Collections.sort(renewalNotificationPeriods);
        periods.add(0);
        periods.addAll(renewalNotificationPeriods);
        sendNotificationsForExpiringDomains(user, periods);
    }

    private void sendNotificationForExpiredDomains(AuthenticatedUser user) {
        List<Domain> domainToNotify = getExpiredDomains();
        LOG.info("Found " + domainToNotify.size() + " expired domains. ");
        LOG.info("Expired domains are: " + domainToNotify);
        for (Domain domain : domainToNotify) {
            // domains registered today are excluded from notification
            if (domain.getRegistrationDate().before(startOfDay(new Date()))) {
                sendNotification(domain, user);
            }
        }
    }

    private List<Domain> getExpiredDomains() {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setType(CustomerType.Direct);
        criteria.setDomainHolderTypes(DomainHolderType.Billable);
        // Don't send the notification to domains that are already in NRP
        criteria.setActive(true);
        criteria.setRenewalDate(new Date());
        return domainSearchService.findAll(criteria, null);
    }

    private void sendNotification(Domain domain, AuthenticatedUser user) {
        try {
            domainService.sendNotification(domain, user);
        } catch (Exception e) {
            LOG.error("Exception during sending notification!", e);
        }
    }

    private void sendNotificationsForExpiringDomains(AuthenticatedUser user, List<Integer> notificationPeriods) {
        for (int i = 1; i < notificationPeriods.size(); i++) {
            sendNotificationForPeriod(notificationPeriods.get(i - 1), notificationPeriods.get(i), user);
        }
    }

    private void sendNotificationForPeriod(int periodStart, int periodEnd, AuthenticatedUser user) {
        List<Domain> domainToNotify = getExpiringDomains(periodStart, periodEnd);
        LOG.info("Sending close expiry notification (" + periodEnd + " days) for the following ("
                + domainToNotify.size() + " domains: ");
        LOG.info(domainToNotify);
        for (Domain domain : domainToNotify) {
            // domains transferred today are excluded from notification
            Date transferDate = domain.getTransferDate();
            if (transferDate == null || transferDate.before(startOfDay(new Date()))) {
                sendNotification(domain, user, periodEnd);
            }
        }
    }

    private List<Domain> getExpiringDomains(int periodStart, int periodEnd) {
        Date notificationDate = getCurrDate(periodEnd);
        Date notificationDateMin = getCurrDate(periodStart + 1);
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setType(CustomerType.Direct);
        criteria.setDomainHolderTypes(DomainHolderType.Billable);
        criteria.setActive(true);
        criteria.setRenewalTo(notificationDate);
        criteria.setRenewalFrom(notificationDateMin);
        return domainSearchService.findAll(criteria, null);
    }

    private void sendNotification(Domain domain, AuthenticatedUser user, int period) {
        try {
            domainService.sendNotification(domain, user, period);
        } catch (Exception e) {
            LOG.error("Exception during sending notification!", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> checkPayAvailable(AuthenticatedUser user, List<String> domainNames)
            throws UserNotAuthenticatedException {
        validateLoggedIn(user);
        return domainService.checkEventAvailable(domainNames, DsmEventName.PaymentInitiated);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modifyRenewalMode(AuthenticatedUser user, List<String> domainNames, RenewalMode renewalMode)
            throws UserNotAuthenticatedException, DomainNotFoundException, DomainIllegalStateException {
        validateLoggedIn(user);
        domainService.modifyRenewalMode(user, domainNames, renewalMode, new OpInfo(user));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Country> getCountries(AuthenticatedUser user) {
        // does not need the user to be logged-in
        return domainService.getCountries();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isCharity(AuthenticatedUser user, String domainName) throws DomainNotFoundException {
        Domain d = domainSearchService.getDomain(domainName);
        return d.getDsmState().getDomainHolderType() == DomainHolderType.Charity;
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<DeletedDomain> findDeletedDomains(AuthenticatedUser user,
            DeletedDomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException {
        validateLoggedIn(user);
        return domainSearchService.findDeletedDomains(criteria, offset, limit, orderBy);
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<DeletedDomain> findOwnDeletedDomains(AuthenticatedUser user,
            DeletedDomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException {
        validateLoggedIn(user);
        if (criteria == null) {
            criteria = new DeletedDomainSearchCriteria();
        }
        criteria.setBillingNH(user.getUsername());
        return domainSearchService.findDeletedDomains(criteria, offset, limit, orderBy);
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<Domain> findDomainAutorenewals(AuthenticatedUser user, DomainSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> orderBy) throws UserNotAuthenticatedException {
        validateLoggedIn(user);
        return domainSearchService.findDomainAutorenewals(criteria, offset, limit, orderBy);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Domain> findDomainsToAuthCodeCleanup(AuthenticatedUser user) throws AccessDeniedException {
        validateLoggedIn(user);
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setAuthcExpTo(new Date());
        return domainSearchService.findAll(criteria, null);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void authCodeCleanup(AuthenticatedUser user, String domainName)
            throws AccessDeniedException, DomainLockedException, NicHandleNotFoundException,
            NicHandleNotActiveException, DomainNotFoundException, EmptyRemarkException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user);
        domainService.clearAuthCode(domainName, opInfo);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Domain> findDomainsToAuthCodePortalCleanup(AuthenticatedUser user) throws AccessDeniedException {
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setAuthCodeFromPortal(true);
        return domainSearchService.findAll(criteria, null);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void authCodePortalCleanup(AuthenticatedUser user, String domainName)
            throws AccessDeniedException, DomainLockedException, NicHandleNotFoundException,
            NicHandleNotActiveException, DomainNotFoundException, EmptyRemarkException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user);
        domainService.clearAuthCodePortalCount(domainName, opInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendAuthCodeByEmail(AuthenticatedUser user, String domainName)
            throws TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException,
            DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException,
            DomainLockedException, AuthcodeGenerationDomainStateException {
        domainService.sendAuthCodeByEmail(domainName, new OpInfo(user));
    }

    @Transactional(noRollbackFor = BulkExportAuthCodesException.class)
    @Override
    public void bulkExportAuthCodes(AuthenticatedUser user, List<String> domainNames)
            throws BulkExportAuthCodesException, BulkExportAuthCodesTotalException, UserNotAuthenticatedException {
        List<Domain> domainList = new ArrayList<>();
        List<String> errorList = new ArrayList<>();
        validateLoggedIn(user);
        for (String domainName : domainNames) {
            try {
                domainService.getOrCreateAuthCode(domainName, new OpInfo(user));
                domainList.add(domainSearchService.getDomain(domainName));
            } catch (Exception e) {
                LOG.error("Exception during generaring an authcode for a domain: " + domainName, e);
                errorList.add(domainName + ": " + e.getMessage());
            }
        }
        if (domainList.isEmpty()) {
            throw new BulkExportAuthCodesTotalException(errorList.toString());
        }
        Map<Contact, List<Domain>> billCMap = mapDomainsByBillC(domainList);
        for (Map.Entry<Contact, List<Domain>> billCEntry : billCMap.entrySet()) {
            Contact billC = billCEntry.getKey();
            List<Domain> billCDomainList = billCEntry.getValue();
            Map<Contact, List<Domain>> adminMap = mapDomainsByAdmin(billCDomainList);
            for (Map.Entry<Contact, List<Domain>> entry : adminMap.entrySet()) {
                Contact admin = entry.getKey();
                List<Domain> adminDomainList = entry.getValue();
                try {
                    domainService.sendBulkAuthCodesByEmail(user, billC, admin, adminDomainList);
                } catch (Exception e) {
                    LOG.error("Exception during exporting authcodes (billC: " + billC.getNicHandle() + ", adminC: "
                            + admin.getNicHandle() + ")", e);
                    errorList.add(billC.getNicHandle() + ", " + admin.getNicHandle() + ": " + e.getMessage());
                }
            }
        }
        if (!errorList.isEmpty()) {
            throw new BulkExportAuthCodesException(errorList.toString(), domainList.size(), domainNames.size());
        }
    }

    private Map<Contact, List<Domain>> mapDomainsByBillC(List<Domain> domainList) {
        Map<Contact, List<Domain>> billCMap = new HashMap<>();
        for (Domain domain : domainList) {
            Contact billC = domain.getBillingContact();
            List<Domain> list = billCMap.get(billC);
            if (list == null) {
                list = new ArrayList<>();
                list.add(domain);
                billCMap.put(billC, list);
            } else {
                list.add(domain);
            }
        }
        return billCMap;
    }

    private Map<Contact, List<Domain>> mapDomainsByAdmin(List<Domain> domainList) {
        Map<Contact, List<Domain>> adminMap = new HashMap<>();
        for (Domain domain : domainList) {
            for (Contact admin : domain.getAdminContacts()) {
                List<Domain> list = adminMap.get(admin);
                if (list == null) {
                    list = new ArrayList<>();
                    list.add(domain);
                    adminMap.put(admin, list);
                } else {
                    list.add(domain);
                }
            }
        }
        return adminMap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendWhoisDataEmail(AuthenticatedUser user, String domainName)
            throws TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException, IOException,
            InterruptedException, WhoisProcessingException {
        domainService.sendWhoisDataEmail(user, domainName);
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<Domain> findOwn(AuthenticatedUser user, DomainSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
        criteria.setNicHandle(user.getUsername());
        applyFilter(criteria);
        return domainSearchService.find(criteria, offset, limit, orderBy);
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<PlainDomain> findOwnPlain(AuthenticatedUser user, PlainDomainSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
        criteria.setBillingNH(user.getUsername());
        criteria.filterValues();
        return domainSearchService.findPlain(criteria, offset, limit, orderBy);
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<ExtendedDomain> findOwnExtended(AuthenticatedUser user,
            ExtendedDomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> orderBy)
                    throws AccessDeniedException {
        criteria.setNicHandle(user.getUsername());
        applyFilter(criteria);
        return domainSearchService.findExtended(criteria, offset, limit, orderBy);
    }

    private void applyFilter(AbstractDomainSearchCriteria<? extends Domain> criteria) {
        if (criteria.getNicHandle() != null) {
            if (criteria.getContactType() == null) {
                criteria.setContactType(allowedContactTypes);
            } else {
                List<ContactType> contactTypes = new ArrayList<>(criteria.getContactType());
                contactTypes.retainAll(allowedContactTypes);
                if (contactTypes.isEmpty()) {
                    contactTypes = allowedContactTypes;
                }
                criteria.setContactType(contactTypes);
            }
            criteria.filterValues();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<ExtendedDomain> findExtended(AuthenticatedUser user,
            ExtendedDomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
                    throws AccessDeniedException {
        validateLoggedIn(user);
        return domainSearchService.findExtended(criteria, offset, limit, sortBy);
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<ExtendedDomain> findDomainsForCurrentRenewal(AuthenticatedUser user,
            RenewalDateType renewalDateType, ExtendedDomainSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy)
                    throws AccessDeniedException {
        validateLoggedIn(user);

        criteria.setBillingNH(user.getUsername());
        criteria.setActive(true);
        criteria.setDomainRenewalModes(RenewalMode.NoAutorenew);
        criteria.setDomainHolderTypes(DomainHolderType.Billable);
        Date startDate = null;
        Date endDate = null;
        Date currDate = new Date();
        switch (renewalDateType) {
            case OVERDUE:
                startDate = endDate = addDays(currDate, -1);
                break;
            case RENEWAL_TODAY:
                startDate = endDate = currDate;
                break;
            case RENEWAL_THIS_MONTH:
                startDate = startOfMonth(currDate);
                endDate = endOfMonth(currDate);
                break;
            default:
                throw new IllegalArgumentException("Invalid renewal date type: " + renewalDateType);
        }
        criteria.setRenewalFrom(startOfDay(startDate));
        criteria.setRenewalTo(endOfDay(endDate));
        criteria.setCurrentRenewal(true);
        criteria.filterValues();
        return domainSearchService.findExtended(criteria, offset, limit, sortBy);
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<ExtendedDomain> findDomainsForFutureRenewal(AuthenticatedUser user, int month,
            ExtendedDomainSearchCriteria criteria, long offset, long limit, List<SortCriterion> sortBy)
                    throws AccessDeniedException {
        criteria.setBillingNH(user.getUsername());
        criteria.setActive(true);
        criteria.setDomainRenewalModes(RenewalMode.NoAutorenew);
        criteria.setDomainHolderTypes(DomainHolderType.Billable);
        criteria.setRenewalMonth(month);
        criteria.setFutureRenewal(true);
        criteria.filterValues();
        return domainSearchService.findExtended(criteria, offset, limit, sortBy);
    }

    @Transactional(readOnly = true)
    @Override
    public LimitedSearchResult<NsReport> getNsReports(AuthenticatedUser user, NsReportSearchCriteria criteria,
            long offset, long limit, List<SortCriterion> sortBy) throws AccessDeniedException {
        return domainSearchService.getNsReports(user.getUsername(), criteria, offset, limit, sortBy);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DomainCountForContact> findDomainCountForContact(AuthenticatedUser user,
            DomainCountForContactSearchCriteria criteria) throws AccessDeniedException {
        String nicHandle = user.getUsername();
        criteria.setNicHandle(nicHandle);
        criteria.setContactType(allowedContactTypes);
        criteria.filterNrpStatuses();
        return domainSearchService.findDomainCountForContact(criteria);
    }

    @Transactional(readOnly = true)
    @Override
    public void validateDomainToModify(AuthenticatedUser user, String domainName, boolean isRenewalModeChange)
            throws DomainNotFoundException, NicHandleNotFoundException, DomainLockedException,
            DomainManagedByAnotherResellerException, DomainNotBillableException, SellRequestExistsException {
        Domain domain = domainSearchService.getDomain(domainName);
        NicHandle nh = nicHandleSearchService.getNicHandle(user.getUsername());
        long accountId = nh.getAccount().getId();
        ValidationHelper.checkIsEditable(domain);
        if (accountId != domain.getResellerAccount().getId()) {
            throw new DomainManagedByAnotherResellerException(domain.getName());
        }
        if (isRenewalModeChange) {
            validateIsBillable(domain);
        }
    }

    private void validateIsBillable(Domain domain) throws DomainNotBillableException {
        if (domain.getDsmState().getDomainHolderType() != DomainHolderType.Billable) {
            throw new DomainNotBillableException(domain.getName(), domain.getDsmState().getDomainHolderType()
                    .getDescription());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public RelatedDomains getRelatedDomains(AuthenticatedUser user, String domainHolder, int limit,
            String exceptDomain) throws AccessDeniedException {
        validateLoggedIn(user);
        List<String> relatedDomains = domainSearchService.findDomainsForHolder(domainHolder, limit, exceptDomain);
        List<String> pendingDomains = ticketSearchService.findPendingDomainsForHolder(domainHolder, limit, exceptDomain);
        return new RelatedDomains(relatedDomains, pendingDomains);
    }
}
