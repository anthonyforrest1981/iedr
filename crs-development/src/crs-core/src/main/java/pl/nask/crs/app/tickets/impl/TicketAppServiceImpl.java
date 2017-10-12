package pl.nask.crs.app.tickets.impl;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.tickets.TicketInfo;
import pl.nask.crs.app.tickets.TicketModel;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.entities.exceptions.*;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.service.PaymentSearchService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.CustomerStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.TicketNotification;
import pl.nask.crs.ticket.exceptions.*;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.TicketEdit;
import pl.nask.crs.ticket.search.TicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketHistorySearchService;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.ticket.services.TicketService;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.User;
import pl.nask.crs.user.service.UserSearchService;

import static pl.nask.crs.app.utils.UserValidator.validateLoggedIn;

public class TicketAppServiceImpl implements TicketAppService {
    public static final String ACCEPT_REMARK = "This application has been accepted";

    public static final int MAX_DOMAINS_IN_INFO = 100;

    private final TicketSearchService ticketSearchService;

    private final TicketService ticketService;

    private final TicketHistorySearchService ticketHistorySearchService;

    private final DomainDAO domainDAO;

    private final DomainSearchService domainSearchService;
    private final NicHandleSearchService nicHandleSearchService;
    private final PaymentSearchService paymentSearchService;
    private final UserSearchService userSearchService;

    private final ApplicationConfig applicationConfig;

    public TicketAppServiceImpl(TicketSearchService ticketSearchService, TicketService ticketService,
            TicketHistorySearchService ticketHistorySearchService, DomainDAO domainDAO,
            DomainSearchService domainSearchService, NicHandleSearchService nicHandleSearchService,
            PaymentSearchService paymentSearchService, UserSearchService userSearchService,
            ApplicationConfig applicationConfig) {
        Validator.assertNotNull(ticketSearchService, "ticket search service");
        Validator.assertNotNull(ticketService, "ticketService");
        Validator.assertNotNull(ticketHistorySearchService, "ticket history search service");
        Validator.assertNotNull(domainDAO, "domain dao");
        Validator.assertNotNull(domainSearchService, "domain search service");
        Validator.assertNotNull(nicHandleSearchService, "nicHandleSearchService");
        Validator.assertNotNull(paymentSearchService, "paymentSearchService");
        Validator.assertNotNull(userSearchService, "userSearchService");
        Validator.assertNotNull(applicationConfig, "applicationConfig");
        this.ticketSearchService = ticketSearchService;
        this.ticketService = ticketService;
        this.ticketHistorySearchService = ticketHistorySearchService;
        this.domainDAO = domainDAO;
        this.domainSearchService = domainSearchService;
        this.nicHandleSearchService = nicHandleSearchService;
        this.paymentSearchService = paymentSearchService;
        this.userSearchService = userSearchService;
        this.applicationConfig = applicationConfig;
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<Ticket> search(AuthenticatedUser user, TicketSearchCriteria criteria, long offset,
            long limit, /*>>>@Nullable*/ List<SortCriterion> orderBy) throws AccessDeniedException {
        validateLoggedIn(user);
        return ticketSearchService.find(criteria, offset, limit, orderBy);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<Ticket> findOwn(AuthenticatedUser user, TicketSearchCriteria criteria, long offset,
            long limit, /*>>>@Nullable*/ List<SortCriterion> orderBy)
            throws AccessDeniedException, NicHandleNotFoundException {
        validateLoggedIn(user);
        applyOwnFilter(criteria, user.getUsername());
        return ticketSearchService.find(criteria, offset, limit, orderBy);
    }

    private void applyOwnFilter(TicketSearchCriteria criteria, String nicHandleId)
            throws NicHandleNotFoundException {
        NicHandle nicHandle = nicHandleSearchService.getNicHandle(nicHandleId);
        User user = userSearchService.get(nicHandleId);
        if (user.hasGroup(Level.Registrar) || user.hasGroup(Level.SuperRegistrar)) {
            criteria.setAccountId(nicHandle.getAccount().getId());
        } else if (user.hasGroup(Level.Direct)) {
            criteria.setBillNicHandle(nicHandleId);
        } else {
            criteria.setCreatorNh(user.getUsername());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TicketModel view(AuthenticatedUser user, long ticketId)
            throws AccessDeniedException, TicketNotFoundException {
        // todo: permission check
        validateLoggedIn(user);
        return getTicketModel(ticketId);
    }

    @Override
    @Transactional(readOnly = true)
    public TicketModel history(AuthenticatedUser user, long ticketId)
            throws AccessDeniedException, TicketNotFoundException {
        // todo: permission check
        validateLoggedIn(user);
        try {
            return getTicketModel(ticketId);
        } catch (TicketNotFoundException e) {
            // no current ticket found
            // limit search result to 50 entries
            List<HistoricalObject<Ticket>> history = ticketHistorySearchService.getTicketHistory(ticketId);
            return new TicketModel(null, new TicketInfo(), history);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TicketModel revise(AuthenticatedUser user, long ticketId)
            throws AccessDeniedException, TicketNotFoundException {
        // todo: permission check
        validateLoggedIn(user);
        return getTicketModel(ticketId);
    }

    @Override
    @Transactional(readOnly = true)
    public TicketModel edit(AuthenticatedUser user, long ticketId)
            throws AccessDeniedException, TicketNotFoundException {
        // todo: permission check
        validateLoggedIn(user);
        return getTicketModel(ticketId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkOut(AuthenticatedUser user, long ticketId)
            throws AccessDeniedException, TicketNotFoundException, TicketAlreadyCheckedOutException {
        // todo: permission check
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user);
        ticketService.checkOut(ticketId, opInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkIn(AuthenticatedUser user, long ticketId, AdminStatus status)
            throws AccessDeniedException, TicketNotFoundException, TicketNotCheckedOutException,
            TicketCheckedOutToOtherException {
        // todo: permission check
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user);
        ticketService.checkIn(ticketId, status, opInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void alterStatus(AuthenticatedUser user, long ticketId, AdminStatus status)
            throws AccessDeniedException, TicketNotFoundException, TicketCheckedOutToOtherException,
            TicketEmailException, TicketNotCheckedOutException, ClassDoesNotMatchCategoryException,
            CategoryDoesNotMatchSubcategoryException {
        // todo: permission check
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user, "Accepted by alter status");
        ticketService.alterStatus(user, ticketId, status, opInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reassign(AuthenticatedUser user, long ticketId, String newHostmasterHandle)
            throws AccessDeniedException, TicketNotFoundException, TicketNotCheckedOutException,
            TicketUserCannotCheckOutException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user);
        ticketService.reassign(ticketId, newHostmasterHandle, opInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(AuthenticatedUser user, long ticketId, DomainOperation failureReasons, String hostmastersRemark)
            throws AccessDeniedException, TicketNotFoundException, EmptyRemarkException,
            TicketCheckedOutToOtherException, TicketNotCheckedOutException, CategoryDoesNotMatchSubcategoryException,
            ClassDoesNotMatchCategoryException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user, hostmastersRemark);
        ticketService.save(ticketId, failureReasons, opInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = TicketEmailException.class)
    public void accept(AuthenticatedUser user, long ticketId, DomainOperation domainOperation, String hostmastersRemark)
            throws AccessDeniedException, TicketNotFoundException, TicketEmailException,
            TicketCheckedOutToOtherException, TicketNotCheckedOutException, CategoryDoesNotMatchSubcategoryException,
            ClassDoesNotMatchCategoryException {
        validateLoggedIn(user);
        if (Validator.isEmpty(hostmastersRemark)) {
            hostmastersRemark = ACCEPT_REMARK;
        }
        OpInfo opInfo = new OpInfo(user, hostmastersRemark);
        ticketService.accept(user, ticketId, domainOperation, opInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = TicketEmailException.class)
    public void reject(AuthenticatedUser user, long ticketId, AdminStatus status, DomainOperation domainOperation,
            String hostmastersRemark)
            throws AccessDeniedException, TicketNotFoundException, InvalidStatusException, EmptyRemarkException,
            TicketEmailException, TicketCheckedOutToOtherException, TicketNotCheckedOutException,
            CategoryDoesNotMatchSubcategoryException, ClassDoesNotMatchCategoryException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user, hostmastersRemark);
        ticketService.reject(user, ticketId, status, domainOperation, opInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAsAdmin(AuthenticatedUser user, long ticketId, TicketEdit ticketEdit, /*>>>@Nullable*/
            String requestersRemark, String hostmastersRemark, boolean clikPaid, boolean forceUpdate)
            throws AccessDeniedException, ValidationException, TicketEditFlagException, EmptyRemarkException,
            NicHandleException, AccountNotFoundException, HolderClassNotExistException, HolderCategoryNotExistException,
            ClassDoesNotMatchCategoryException, TicketNotFoundException, TicketCheckedOutToOtherException,
            TicketNotCheckedOutException, CategoryDoesNotMatchSubcategoryException, HolderSubcategoryNotExistException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user, hostmastersRemark);
        ticketService.updateAsAdmin(ticketId, ticketEdit, requestersRemark, opInfo, clikPaid, forceUpdate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAsOwner(AuthenticatedUser user, long ticketId, TicketEdit ticketEdit, String requestersRemark,
            String hostmastersRemark, boolean clikPaid, boolean documentsWereSent)
            throws AccessDeniedException, ValidationException, TicketEditFlagException, EmptyRemarkException,
            NicHandleException, AccountNotFoundException, TicketNotFoundException, TicketAlreadyCheckedOutException,
            TicketHolderChangeException, DomainNotFoundException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user, hostmastersRemark);
        ticketService.updateAsOwner(ticketId, ticketEdit, requestersRemark, opInfo, clikPaid, documentsWereSent);
    }

    private TicketModel getTicketModel(long ticketId) throws TicketNotFoundException {
        // getTicketHistory the ticket
        Ticket ticket = ticketSearchService.getTicket(ticketId);

        // getTicketHistory additional info about the ticket
        String domainName = ticket.getOperation().getDomainNameField().getNewValue();
        String ticketHolder = ticket.getOperation().getDomainHolderField().getNewValue();
        assert domainName != null : "@AssumeAssertion(nullness)";

        TicketInfo info = getAdditionalInfo(ticket, domainName, ticketHolder, ticket.isHasDocuments());

        // getTicketHistory the history of the ticket
        // limit search result to 50 entries
        List<HistoricalObject<Ticket>> history = ticketHistorySearchService.getTicketHistory(ticketId);

        return new TicketModel(ticket, info, history);
    }

    private TicketInfo getAdditionalInfo(Ticket ticket, String domainName, /*>>>@Nullable*/ String ticketHolder,
            boolean hasDocuments) {
        Validator.assertNotNull(domainName, "domain name");

        // find previous domain holder
        Domain domain = domainDAO.get(domainName);
        String currentHolder = null;
        if (domain != null) {
            currentHolder = domain.getHolder();
        }
        DomainSearchCriteria criteria = new DomainSearchCriteria();
        criteria.setDomainName(domainName);
        criteria.setDomainHolder(currentHolder);
        String previousHolder = domainDAO.getPreviousHolder(criteria);

        // find the domains owned by the same domain holder as ticketHolder (if any)
        Collection<String> domainNames = new TreeSet<String>();
        Collection<String> pendingDomainNames = new TreeSet<String>();
        if (ticketHolder != null) {
            domainNames.addAll(domainSearchService.findDomainsForHolder(ticketHolder, MAX_DOMAINS_IN_INFO, domainName));
            pendingDomainNames.addAll(ticketSearchService.findPendingDomainsForHolder(ticketHolder,
                    MAX_DOMAINS_IN_INFO, domainName));
        }

        PaymentMethod paymentMethod = null;

        if (ticket.getOperation().getType() != DomainOperation.DomainOperationType.MOD && !ticket.isCharity()) {
            Reservation res = paymentSearchService.getReservationForTicket(ticket.getId());
            if (res == null) {
                paymentMethod = PaymentMethod.ADP;
            } else {
                paymentMethod = res.getPaymentMethod();
            }
        }

        return new TicketInfo(domainName, ticketHolder, previousHolder, domainNames, pendingDomainNames, hasDocuments,
                paymentMethod);
    }

    @Override
    @Transactional(readOnly = true)
    public /*>>>@Nullable*/ Ticket getTicketForDomain(AuthenticatedUser user, String domainName)
            throws TooManyTicketsException {
        return ticketSearchService.getTicketForDomain(domainName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketNotification> findTicketNotifications(AuthenticatedUser user)
            throws AccessDeniedException {
        validateLoggedIn(user);
        List<Integer> ticketExpirationNotificationPeriods = applicationConfig.getTicketExpirationNotificationPeriods();
        ticketExpirationNotificationPeriods.add(0);
        Collections.sort(ticketExpirationNotificationPeriods);
        List<TicketNotification> notifications = new ArrayList<>();
        for (int i = 1; i < ticketExpirationNotificationPeriods.size(); i++) {
            int minDaysToExpiration = ticketExpirationNotificationPeriods.get(i - 1) + 1;
            int maxDaysToExpiration = ticketExpirationNotificationPeriods.get(i);
            notifications.addAll(findTicketNotificationsForPeriod(minDaysToExpiration, maxDaysToExpiration, user));
        }
        return notifications;
    }

    private List<TicketNotification> findTicketNotificationsForPeriod(int minDaysToExpiration, int maxDaysToExpiration,
            AuthenticatedUser user) {
        int ticketExpirationPeriod = applicationConfig.getTicketExpirationPeriod();
        List<TicketNotification> notifications = new ArrayList<>();
        List<Ticket> tickets = findExpiringTickets(minDaysToExpiration, maxDaysToExpiration, ticketExpirationPeriod);
        for (Ticket ticket : tickets) {
            notifications.add(new TicketNotification(ticket.getId(), maxDaysToExpiration));
        }
        return notifications;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendTicketExpirationEmail(AuthenticatedUser user, TicketNotification ticketNotification)
            throws AccessDeniedException, TicketNotFoundException {
        Ticket ticket = ticketSearchService.getTicket(ticketNotification.getTicketId());
        int ticketExpirationPeriod = applicationConfig.getTicketExpirationPeriod();
        int numberOfDaysPassed = DateUtils.diffInDays(ticket.getCreationDate(), new Date());
        int numberOfDaysRemaining = ticketExpirationPeriod - numberOfDaysPassed;
        ticketService.sendTicketExpirationEmail(user, ticket, numberOfDaysRemaining, numberOfDaysPassed,
                ticketNotification.getNotificationPeriod());
    }

    private List<Ticket> findExpiringTickets(int minDaysToExpiration, int maxDaysToExpiration,
            int ticketExpirationPeriod) {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        Date ticketCreatedFrom = DateUtils.getCurrDate(minDaysToExpiration - ticketExpirationPeriod);
        Date ticketCreatedTo = DateUtils.getCurrDate(maxDaysToExpiration - ticketExpirationPeriod);
        criteria.setFrom(DateUtils.startOfDay(ticketCreatedFrom));
        criteria.setTo(DateUtils.endOfDay(ticketCreatedTo));
        return ticketSearchService.findAll(criteria, null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findTicketsToCleanup(AuthenticatedUser user) throws UserNotAuthenticatedException {
        validateLoggedIn(user);
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setCustomerStatus(CustomerStatus.CANCELLED);
        List<Ticket> customerCancelled = ticketSearchService.findAll(criteria, null);

        criteria = new TicketSearchCriteria();
        criteria.setAdminStatus(AdminStatus.CANCELLED);
        List<Ticket> adminCancelled = ticketSearchService.findAll(criteria, null);

        criteria = new TicketSearchCriteria();
        int ticketExpirationPeriod = applicationConfig.getTicketExpirationPeriod();
        criteria.setTo(DateUtils.getCurrDate(-ticketExpirationPeriod));
        List<Ticket> expired = ticketSearchService.findAll(criteria, null);

        List<Ticket> ret = new ArrayList<Ticket>();
        ret.addAll(customerCancelled);
        ret.addAll(adminCancelled);
        ret.addAll(expired);
        return ret;
    }
}
