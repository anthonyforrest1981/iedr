package pl.nask.crs.app.commons.impl;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.*;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.AppServicesRegistry;
import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.commons.ApplicationConfiguration;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.commons.exceptions.CancelTicketException;
import pl.nask.crs.app.commons.exceptions.CancelTicketWithSettledReservationException;
import pl.nask.crs.app.commons.exceptions.CancelTriplePassedTicketException;
import pl.nask.crs.app.commons.register.RegisterCharityDomain;
import pl.nask.crs.app.commons.register.RegisterDomain;
import pl.nask.crs.app.commons.register.RegisterDomainCC;
import pl.nask.crs.app.commons.transfer.TransferDomain;
import pl.nask.crs.app.commons.transfer.TransferDomainCC;
import pl.nask.crs.app.commons.transfer.TransferDomainCharity;
import pl.nask.crs.app.tickets.exceptions.DomainIsCharityException;
import pl.nask.crs.app.tickets.exceptions.DomainIsNotCharityException;
import pl.nask.crs.app.tickets.exceptions.NicHandleRecreateException;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.dns.NameserverValidator;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.dnscheck.exceptions.DnsCheckProcessingException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.AuthCode;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.RenewalMode;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.dsm.DomainStateMachine;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.dsm.events.TransferCancellation;
import pl.nask.crs.domains.exceptions.*;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.entities.OwnerType;
import pl.nask.crs.entities.exceptions.ClassDoesNotMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.entities.exceptions.OwnerTypeNotExistException;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.CreditCard;
import pl.nask.crs.payment.PaymentSummary;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.Transaction;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.exceptions.DepositNotFoundException;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.payment.service.impl.TransactionCancelStrategy;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.security.authentication.impl.ArtificialUsersRegistry;
import pl.nask.crs.ticket.CustomerStatus;
import pl.nask.crs.ticket.FinancialStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketEmailException;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;

import static pl.nask.crs.app.utils.UserValidator.validateLoggedIn;

public class CommonAppServiceImpl implements CommonAppService {

    private final static Logger LOG = Logger.getLogger(CommonAppServiceImpl.class);
    private final AppServicesRegistry appServicesRegistry;
    private final DomainStateMachine dsm;
    private final ServicesRegistry servicesRegistry;
    private final ArtificialUsersRegistry artificialUsersRegistry;

    public CommonAppServiceImpl(AppServicesRegistry registry, DomainStateMachine dsm,
            ServicesRegistry servicesRegistry, ArtificialUsersRegistry artificialUsersRegistry) {
        Validator.assertNotNull(registry, "serviceRegistry");
        Validator.assertNotNull(dsm, "dsm");
        Validator.assertNotNull(servicesRegistry, "servicesRegistry");
        this.appServicesRegistry = registry;
        this.dsm = dsm;
        this.servicesRegistry = servicesRegistry;
        this.artificialUsersRegistry = artificialUsersRegistry;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long registerDomain(AuthenticatedUser user, TicketRequest request, CreditCard creditCard)
            throws NicHandleException, AccessDeniedException, NotAdmissiblePeriodException,
            HolderClassNotExistException, HolderCategoryNotExistException, ClassDoesNotMatchCategoryException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException,
            DomainIsNotCharityException, CardPaymentException, DomainIsCharityException, EmptyRemarkException,
            TooManyTicketsException, ExportException, DepositNotFoundException, OwnerTypeNotExistException {
        validateLoggedIn(user);
        if (request.isCharity()) {
            return new RegisterCharityDomain(appServicesRegistry, servicesRegistry, user, request).run();
        } else if (creditCard == null) {
            return new RegisterDomain(appServicesRegistry, servicesRegistry, user, request).run();
        } else {
            return new RegisterDomainCC(appServicesRegistry, servicesRegistry, user, request, creditCard).run();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long transfer(AuthenticatedUser user, TicketRequest request, CreditCard creditCard)
            throws TicketNotFoundException, NicHandleException, NotAdmissiblePeriodException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException,
            DomainIsNotCharityException, CardPaymentException, DomainIsCharityException, InvalidAuthCodeException,
            IllegalArgumentException, TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException,
            AccessDeniedException, EmptyRemarkException, DomainIllegalStateException, TooManyTicketsException,
            ExportException, DepositNotFoundException {
        validateLoggedIn(user);

        if (request.isCharity()) {
            return new TransferDomainCharity(appServicesRegistry, servicesRegistry, dsm, user, request).run();
        } else if (creditCard == null) {
            return new TransferDomain(appServicesRegistry, servicesRegistry, dsm, user, request).run();
        } else {
            return new TransferDomainCC(appServicesRegistry, servicesRegistry, dsm, user, request, creditCard).run();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelTicketAsOwner(AuthenticatedUser user, long ticketId)
            throws TicketNotFoundException, CancelTicketException, CardPaymentException, UserNotAuthenticatedException,
            DomainNotFoundException, DomainIllegalStateException {
        validateLoggedIn(user);
        Ticket ticket = servicesRegistry.getTicketService().lockTicket(ticketId);
        try {
            cancelTicket(user, ticket, TransactionCancelStrategy.LEAVE_AS_CANCELLED);
        } catch (TransactionNotFoundException e) {
            //should never happen
            throw new IllegalStateException("Transaction not found");
        }
        servicesRegistry.getTicketService().updateCustomerStatus(ticketId, CustomerStatus.CANCELLED, new OpInfo(user));
    }

    @Override
    @Transactional
    public void zonePublished(AuthenticatedUser user, List<String> domainNames) {
        servicesRegistry.getDomainService().zonePublished(domainNames);
    }

    @Override
    @Transactional
    public void zoneUnpublished(AuthenticatedUser user, List<String> domainNames) {
        servicesRegistry.getDomainService().zoneUnpublished(domainNames);
    }

    @Override
    @Transactional
    public void zoneCommit(AuthenticatedUser user) {
        servicesRegistry.getDomainService().zoneCommit();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isTransferPossible(AuthenticatedUser user, String domainName) throws UserNotAuthenticatedException {
        validateLoggedIn(user);
        try {
            NicHandle nh = servicesRegistry.getNicHandleSearchService().getNicHandle(user.getUsername());
            Domain d = servicesRegistry.getDomainSearchService().getDomain(domainName);
            if (isSameReseller(d, nh)) {
                LOG.info("Transfer of domain: " + domainName + " not possible: same reseller.");
                return false;
            }
            if (isPendingTicket(domainName)) {
                LOG.info("Transfer of domain: " + domainName + " not possible: other pending transfer tickets.");
                return false;
            }
            if (!isXferEventAvailable(domainName)) {
                LOG.info("Transfer of domain: " + domainName + " not possible: transfer event not available.");
                return false;
            }
            return true;
        } catch (DomainNotFoundException e) {
            LOG.info("Transfer of domain: " + domainName + " not possible: domain not found.");
            return false;
        } catch (NicHandleNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    private boolean isSameReseller(Domain domain, NicHandle nicHandle) {
        if (isDirect(nicHandle)) {
            return domain.getBillingContact().getNicHandle().equalsIgnoreCase(nicHandle.getNicHandleId());
        } else {
            return domain.getResellerAccount().getId() == nicHandle.getAccount().getId();
        }
    }

    private boolean isDirect(NicHandle nicHandle) {
        return servicesRegistry.getNicHandleSearchService().isNicHandleDirect(nicHandle);
    }

    private boolean isPendingTicket(String domainName) {
        Ticket ticket;
        try {
            ticket = servicesRegistry.getTicketSearchService().getTicketForDomain(domainName);
        } catch (TooManyTicketsException e) {
            return true;
        }
        return (ticket != null && ticket.getOperation().getType() != DomainOperationType.MOD);
    }

    private boolean isXferEventAvailable(String domainName) {
        List<String> invalidDomains = servicesRegistry.getDomainService().checkEventAvailable(
                Arrays.asList(domainName), DsmEventName.TransferRequest);
        return invalidDomains.size() == 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long modifyDomain(AuthenticatedUser user, String domainName, String domainHolder, List<String> adminContacts,
            List<String> techContacts, List<Nameserver> nameservers, RenewalMode renewalMode, String customerRemark)
            throws AccessDeniedException, DomainNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleException, ValidationException, DnsCheckProcessingException,
            HostNotConfiguredException, TooManyTicketsException {
        validateLoggedIn(user);
        Validator.assertNotEmpty(domainName, "domainName");
        Validator.assertNotEmpty(domainHolder, "domainHolder");
        Validator.assertNotEmpty(adminContacts, "adminContacts");
        Validator.assertNotEmpty(techContacts, "techContacts");
        Validator.assertNotNull(renewalMode, "renewalMode");
        NameserverValidator.checkNameservers(domainName, nameservers, servicesRegistry);
        Domain oldDomain = servicesRegistry.getDomainSearchService().getDomain(domainName);

        boolean renewalModeChanged = (renewalMode != oldDomain.getDsmState().getRenewalMode());

        appServicesRegistry.getDomainAppService().validateDomainToModify(user, domainName, renewalModeChanged);
        if (renewalModeChanged) {
            try {
                // permission check must be made before renewal mode is changed.
                appServicesRegistry.getDomainAppService().modifyRenewalMode(user, Arrays.asList(domainName),
                        renewalMode);
            } catch (DomainIllegalStateException e) {
                throw new RenewalModeUnableToModifyException(e.getDomainName());
            }
        }

        if (isNameserverListModified(nameservers, oldDomain, true)) {
            updateDomainNameservers(user, Arrays.asList(domainName), nameservers, customerRemark);
        }
        return modifyDomainHolderAndContacts(user, domainName, domainHolder, adminContacts, techContacts, nameservers,
                customerRemark, oldDomain);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyNameservers(AuthenticatedUser user, List<String> domainNames, List<Nameserver> nameservers,
            String customerRemark)
            throws AccessDeniedException, DomainNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleNotFoundException, NicHandleNotActiveException, ValidationException,
            DnsCheckProcessingException, HostNotConfiguredException {
        validateLoggedIn(user);
        Validator.assertNotEmpty(domainNames, "domainNames");

        List<String> modifiedDomains = new ArrayList<>();
        for (String domainName : domainNames) {
            NameserverValidator.checkNameservers(domainName, nameservers, servicesRegistry);
            Domain domain = servicesRegistry.getDomainSearchService().getDomain(domainName);
            if (isNameserverListModified(nameservers, domain, false)) {
                modifiedDomains.add(domainName);
            }
        }
        updateDomainNameservers(user, modifiedDomains, nameservers, customerRemark);
    }

    private void updateDomainNameservers(AuthenticatedUser user, List<String> domainNames,
            List<Nameserver> nameservers, String customerRemark)
            throws NicHandleNotFoundException, NicHandleNotActiveException, DomainNotFoundException,
            EmptyRemarkException, HostNotConfiguredException, DnsCheckProcessingException, DomainLockedException {
        servicesRegistry.getDnsCheckService().check(domainNames, nameservers, user.getUsername(), true);
        servicesRegistry.getDomainService().updateNameserversForDomainList(user, domainNames, nameservers,
                customerRemark);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void validateNameservers(List<String> domains, List<Nameserver> nameservers, String username,
            boolean sendError)
            throws HostNotConfiguredException, DnsCheckProcessingException, UserNotAuthenticatedException {
        servicesRegistry.getDnsCheckService().check(domains, nameservers, username, sendError);
    }

    private Long modifyDomainHolderAndContacts(AuthenticatedUser user, String domainName, String domainHolder,
            List<String> adminContacts, List<String> techContacts, List<Nameserver> nameservers, String customerRemark,
            Domain oldDomain)
            throws NicHandleException, DomainNotFoundException, ValidationException, EmptyRemarkException,
            AccessDeniedException, TooManyTicketsException {
        try {
            if (isHolderModified(domainHolder, oldDomain)
                    || isContactListModified(adminContacts, techContacts, oldDomain)) {
                return servicesRegistry.getTicketService().createModificationTicket(user, domainName, domainHolder,
                        adminContacts, techContacts, nameservers, customerRemark);
            }
            return null;
        } catch (DomainIsNotCharityException e) {
            //should never happen
            throw new IllegalStateException(e);
        } catch (DomainIsCharityException e) {
            //should never happen
            throw new IllegalStateException(e);
        }
    }

    private boolean isHolderModified(String domainHolder, Domain oldDomain) {
        return !domainHolder.equals(oldDomain.getHolder());
    }

    private boolean isContactListModified(List<String> adminContacts, List<String> techContacts, Domain oldDomain) {
        return isContactListModified(adminContacts, oldDomain.getAdminContacts())
                || isContactListModified(techContacts, oldDomain.getTechContacts());
    }

    private boolean isContactListModified(List<String> newList, List<Contact> oldList) {
        removeEmptyContactsNames(newList);
        removeEmptyContacts(oldList);
        if (newList.size() != oldList.size())
            return true;

        for (int i = 0; i < newList.size(); i++) {
            String newValue = newList.get(i);
            Contact c = oldList.get(i);
            if (!newValue.equals(c.getNicHandle())) {
                return true;
            }
        }

        return false;
    }

    private void removeEmptyContactsNames(List<String> list) {
        if (!Validator.isEmpty(list)) {
            for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
                String contact = iterator.next();
                if (Validator.isEmpty(contact)) {
                    iterator.remove();
                }
            }
        }
    }

    private void removeEmptyContacts(List<Contact> list) {
        if (Validator.isEmpty(list)) {
            for (Iterator<Contact> iterator = list.iterator(); iterator.hasNext();) {
                Contact contact = iterator.next();
                if (Validator.isEmpty(contact.getNicHandle())) {
                    iterator.remove();
                }
            }
        }
    }

    private boolean isNameserverListModified(List<Nameserver> newList, Domain oldDomain, boolean ipCheck) {
        List<Nameserver> oldList = oldDomain.getNameservers();
        if (newList.size() != oldList.size()) {
            return true;
        }
        for (int i = 0; i < newList.size(); i++) {
            Nameserver newNameserver = newList.get(i);
            Nameserver oldNameserver = oldList.get(i);
            if (isNameserverModified(newNameserver, oldNameserver, ipCheck)) {
                return true;
            }
        }
        return false;
    }

    private boolean isNameserverModified(Nameserver newNameserver, Nameserver oldNameserver, boolean ipCheck) {
        if (oldNameserver == newNameserver) {
            return false;
        }
        if (oldNameserver == null || newNameserver == null) {
            return true;
        }
        if (!Validator.isEqual(newNameserver.getName(), oldNameserver.getName())) {
            return true;
        }
        if (!ipCheck) {
            return false;
        } else if (!Validator.isEqual(newNameserver.getIpv4Address(), oldNameserver.getIpv4Address())
                || !Validator.isEqual(newNameserver.getIpv6Address(), oldNameserver.getIpv6Address())) {
            return true;
        }

        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cleanupTicket(AuthenticatedUser user, OpInfo opInfo, final long ticketId)
            throws TicketNotFoundException, CardPaymentException, TicketEmailException, TransactionNotFoundException,
            DomainNotFoundException, DomainIllegalStateException {
        Ticket ticket = servicesRegistry.getTicketService().lockTicket(ticketId);
        try {
            if (ticket.getCustomerStatus().equals(CustomerStatus.CANCELLED)) {
                cancelTransactionWithTicket(user, ticket, TransactionCancelStrategy.PURGE);
            } else {
                cancelTicket(user, ticket, purgeSuccessfullyCancelledOrExpiredTransactionStrategy(ticketId));
            }
        } catch (CancelTriplePassedTicketException e) {
            LOG.error("Skipping transaction cancellation for ticket due to invalid ticket state, ticketId: "
                    + ticketId);
        } catch (CancelTicketWithSettledReservationException e) {
            LOG.error("Transaction cancellation skipped due to reservation state: " + e.reservation + ", ticketId: "
                    + ticketId);
        }
        servicesRegistry.getTicketService().deleteAndNotify(user, ticket, opInfo);
    }

    private TransactionCancelStrategy purgeSuccessfullyCancelledOrExpiredTransactionStrategy(final long ticketId) {
        return new TransactionCancelStrategy() {
            @Override
            public void handleRealexError(Transaction transaction, CardPaymentException e) throws CardPaymentException {
                if (reservationExpired()) {
                    LOG.info("Failed to cancel an expired transaction with ID=" + transaction.getId()
                            + ", probably was already removed by realex");
                } else {
                    throw e;
                }
            }

            @Override
            public boolean shouldPurgeTransaction() {
                return true;
            }

            private boolean reservationExpired() {
                final int reservationExpirationPeriod = servicesRegistry.getApplicationConfig()
                        .getReservationExpirationPeriod();
                Reservation reservation = servicesRegistry.getPaymentSearchService().getReservationForTicket(ticketId);
                return DateUtils.addDays(new Date(), -reservationExpirationPeriod).after(reservation.getCreationDate());
            }
        };
    }

    private void cancelTicket(AuthenticatedUser user, Ticket ticket, TransactionCancelStrategy cancelStrategy)
            throws CardPaymentException, DomainIllegalStateException, DomainNotFoundException,
            CancelTriplePassedTicketException, CancelTicketWithSettledReservationException,
            TransactionNotFoundException {
        verifyNotTriplePassed(ticket);
        cancelTransactionWithTicket(user, ticket, cancelStrategy);
        if (ticket.getOperation().getType() == DomainOperationType.XFER) {
            runTransferCancellationEvent(user, ticket, new OpInfo(user));
        }
    }

    private void cancelTransactionWithTicket(AuthenticatedUser user, Ticket ticket,
            TransactionCancelStrategy cancelStrategy)
            throws CardPaymentException, CancelTicketWithSettledReservationException, TransactionNotFoundException {
        DomainOperationType ticketType = ticket.getOperation().getType();
        if (ticketType == DomainOperationType.REG || ticketType == DomainOperationType.XFER) {
            Reservation reservation =
                    servicesRegistry.getPaymentSearchService().getReservationForTicket(ticket.getId());
            if (reservation == null) {
                LOG.warn("Transaction cancellation skipped - no reservation for the ticket, ticketId: "
                        + ticket.getId());
                return;
            }
            verifyNotSettled(reservation);
            servicesRegistry.getPaymentService().cancelTransaction(user, reservation.getTransactionId(),
                    cancelStrategy);
        }
    }

    private void verifyNotSettled(Reservation reservation) throws CancelTicketWithSettledReservationException {
        if (reservation.isSettled()) {
            throw new CancelTicketWithSettledReservationException("Ticket reservation settled", reservation);
        }
    }

    private void verifyNotTriplePassed(Ticket ticket) throws CancelTriplePassedTicketException {
        boolean isTriplePassed = ticket.getFinancialStatus() == FinancialStatus.PASSED;
        if (isTriplePassed) {
            throw new CancelTriplePassedTicketException("Ticket triple passed");
        }
    }

    private void runTransferCancellationEvent(AuthenticatedUser user, Ticket t, OpInfo opInfo)
            throws DomainNotFoundException, DomainIllegalStateException {
        final TransferCancellation transferCancellation = new TransferCancellation(t);
        final String domainName = t.getOperation().getDomainNameField().getNewValue();
        assert domainName != null : "@AssumeAssertion(nullness)";
        if (dsm.validateEvent(domainName, transferCancellation)) {
            dsm.handleEvent(user, domainName, transferCancellation, opInfo);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentSummary reauthoriseTransaction(AuthenticatedUser user, long transactionId, CreditCard creditCard)
            throws DomainNotFoundException, TransactionNotFoundException, TicketNotFoundException,
            NotAdmissiblePeriodException, CardPaymentException, NicHandleNotFoundException, UserNotAuthenticatedException {
        validateLoggedIn(user);
        Transaction transaction = servicesRegistry.getPaymentSearchService().getTransaction(transactionId);
        validateState(transaction);
        // all registration/transfer transaction reservations are for the same product, vat
        Reservation reservation = transaction.getReservations().get(0);
        Ticket ticket = servicesRegistry.getTicketSearchService().getTicket(reservation.getTicketId());
        return servicesRegistry.getPaymentService().reauthoriseTransaction(transaction, ticket, creditCard);
    }

    private void validateState(Transaction transaction) {
        if (!transaction.isInvalidated() || transaction.isSettlementEnded()) {
            throw new IllegalStateException("Transaction invalid state for reauthorisation, transactionId="
                    + transaction.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyAuthCode(AuthenticatedUser user, String domainName, String authCode, int failureCount)
            throws InvalidAuthCodeException, IllegalArgumentException, TemplateNotFoundException,
            TemplateInstantiatingException, EmailSendingException, UserNotAuthenticatedException {
        validateLoggedIn(user);
        servicesRegistry.getDomainService().verifyAuthCode(user, domainName, authCode, failureCount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthCode generateOrProlongAuthCode(AuthenticatedUser user, String domainName)
            throws DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException,
            EmptyRemarkException, AccessDeniedException, DomainLockedException,
            AuthcodeGenerationDomainStateException {
        validateLoggedIn(user);
        validateDomainAuthcodeManagement(user, domainName);
        return servicesRegistry.getDomainService().getOrCreateAuthCode(domainName, new OpInfo(user));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendAuthCodeByEmail(AuthenticatedUser user, String domainName)
            throws TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException,
            DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException,
            AccessDeniedException, IllegalArgumentException, DomainLockedException,
            AuthcodeGenerationDomainStateException {
        validateLoggedIn(user);
        validateDomainAuthcodeManagement(user, domainName);
        servicesRegistry.getDomainService().sendAuthCodeByEmail(domainName, new OpInfo(user));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendAuthCodeFromPortal(String domainName, String emailAddress)
            throws TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException,
            DomainNotFoundException, NicHandleNotFoundException, NicHandleNotActiveException, EmptyRemarkException,
            AuthCodePortalEmailException, AuthCodePortalLimitException, IllegalArgumentException,
            DomainLockedException, AuthcodeGenerationDomainStateException {
        validateEmailWithDomain(domainName, emailAddress);
        AuthenticatedUser portalUser = artificialUsersRegistry.getPortalAuthenticatedUser();
        servicesRegistry.getDomainService().sendAuthCodeFromPortal(domainName, emailAddress, new OpInfo(portalUser));
    }

    private void validateEmailWithDomain(String domainName, String emailAddress)
            throws DomainNotFoundException, AuthCodePortalEmailException {
        Domain domain = servicesRegistry.getDomainSearchService().getDomain(domainName);
        List<Contact> contactList = new ArrayList<>();
        contactList.addAll(domain.getBillingContacts());
        contactList.addAll(domain.getAdminContacts());
        for (Contact contact : contactList) {
            if (contact.getEmail().equalsIgnoreCase(emailAddress)) {
                return;
            }
        }
        throw new AuthCodePortalEmailException();
    }

    private void validateDomainAuthcodeManagement(final AuthenticatedUser user, String domainName)
            throws AccessDeniedException, DomainNotFoundException {
        Domain domain = servicesRegistry.getDomainSearchService().getDomain(domainName);
        List<Contact> contactList = new ArrayList<>();
        contactList.addAll(domain.getBillingContacts());
        contactList.addAll(domain.getAdminContacts());
        final Predicate<Contact> contactIsLoggedUser = new Predicate<Contact>() {
            @Override
            public boolean apply(Contact contact) {
                return contact.getNicHandle().equals(user.getUsername());
            }
        };
        if (Iterables.all(contactList, Predicates.not(contactIsLoggedUser))) {
            throw new AccessDeniedException("Only admin-c or bill-c can generate authcode");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationConfiguration getApplicationConfiguration() {
        ApplicationConfig applicationConfig = servicesRegistry.getApplicationConfig();
        return new ApplicationConfiguration(servicesRegistry.getTopLevelDomainService().getAll(),
                applicationConfig.isOneOrTwoLetterDomainAllowed(), applicationConfig.isIDNDomainAllowed(),
                applicationConfig.getNameserverMinCount(), applicationConfig.getNameserverMaxCount(),
                applicationConfig.getDepositMinLimit(), applicationConfig.getDepositMaxLimit(),
                applicationConfig.getAuthCodeFailureLimit(), applicationConfig.getTicketExpirationPeriod(),
                applicationConfig.getSecondaryMarketCountdownPeriod(),
                applicationConfig.getSecondaryMarketAuthcodeExpirationPeriod());
    }

    @Override
    @Transactional(readOnly = true)
    public OwnerType getOwnerTypeByName(AuthenticatedUser user, String name)
            throws AccessDeniedException, OwnerTypeNotExistException {
        validateLoggedIn(user);
        return servicesRegistry.getEntityService().getOwnerTypeByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OwnerType> getOwnerTypes(AuthenticatedUser user) throws AccessDeniedException {
        validateLoggedIn(user);
        return servicesRegistry.getEntityService().getAllOwnerTypes();
    }

}
