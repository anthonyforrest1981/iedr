package pl.nask.crs.app.triplepass;

import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.triplepass.commands.ADPFinancialCheck;
import pl.nask.crs.app.triplepass.commands.CCFinancialCheck;
import pl.nask.crs.app.triplepass.commands.CharityFinancialCheck;
import pl.nask.crs.app.triplepass.exceptions.FinancialCheckException;
import pl.nask.crs.app.triplepass.exceptions.TechnicalCheckException;
import pl.nask.crs.app.triplepass.exceptions.TicketIllegalStateException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.dnscheck.DnsCheckService;
import pl.nask.crs.dnscheck.DnsNotification;
import pl.nask.crs.dnscheck.DnsNotificationService;
import pl.nask.crs.dnscheck.exceptions.DnsCheckProcessingException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.NewDomain;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.dsm.DomainStateMachine;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.domains.dsm.events.*;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.entities.exceptions.CategoryDoesNotMatchSubcategoryException;
import pl.nask.crs.entities.exceptions.ClassDoesNotMatchCategoryException;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.TransactionDetails;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.payment.service.DepositService;
import pl.nask.crs.payment.service.PaymentSearchService;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.CustomerStatus;
import pl.nask.crs.ticket.TechStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.search.TicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.ticket.services.TicketService;
import pl.nask.crs.ticket.services.impl.TicketEmailParameters;
import pl.nask.crs.ticket.services.impl.TransferTicketEmailParameters;

import static pl.nask.crs.app.triplepass.TriplePassHelper.*;

public class TriplePassAppServiceImpl implements TriplePassAppService {
    private static final Logger LOG = Logger.getLogger(TriplePassSupportService.class);

    private PaymentService paymentService;
    private PaymentSearchService paymentSearchService;
    private TicketService ticketService;
    private TicketSearchService ticketSearchService;
    private DomainStateMachine dsm;
    private DnsCheckService dnsCheckService;
    private DnsNotificationService dnsNotificationService;
    private EmailTemplateSender emailTemplateSender;
    private NicHandleSearchService nicHandleSearchService;
    private DomainSearchService domainSearchService;
    private DomainService domainService;
    private DepositService depositService;

    public TriplePassAppServiceImpl(PaymentService paymentService, PaymentSearchService paymentSearchService,
            TicketService ticketService, DomainStateMachine dsm, TicketSearchService ticketSearchService,
            DnsCheckService dnsCheckService, DnsNotificationService dnsNotificationService,
            EmailTemplateSender emailTemplateSender, NicHandleSearchService nicHandleSearchService,
            DomainSearchService domainSearchService, DomainService domainService, DepositService depositService) {
        Validator.assertNotNull(paymentService, "payment service");
        Validator.assertNotNull(paymentSearchService, "paymentSearchService");
        Validator.assertNotNull(ticketService, "ticket service");
        Validator.assertNotNull(dsm, "domain state machine");
        Validator.assertNotNull(ticketSearchService, "ticketSearchService");
        Validator.assertNotNull(dnsCheckService, "dnsCheckService");
        Validator.assertNotNull(dnsNotificationService, "dnsNotificationService");
        Validator.assertNotNull(emailTemplateSender, "emailTemplateSender");
        Validator.assertNotNull(nicHandleSearchService, "nicHandleSearchService");
        Validator.assertNotNull(domainSearchService, "domainSearchService");
        Validator.assertNotNull(domainService, "domainService");
        Validator.assertNotNull(depositService, "depositService");
        this.paymentService = paymentService;
        this.paymentSearchService = paymentSearchService;
        this.ticketService = ticketService;
        this.dsm = dsm;
        this.ticketSearchService = ticketSearchService;
        this.dnsCheckService = dnsCheckService;
        this.dnsNotificationService = dnsNotificationService;
        this.emailTemplateSender = emailTemplateSender;
        this.nicHandleSearchService = nicHandleSearchService;
        this.domainSearchService = domainSearchService;
        this.domainService = domainService;
        this.depositService = depositService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Ticket> getAdminPassedTickets(AuthenticatedUser user) throws UserNotAuthenticatedException {
        TicketSearchCriteria criteria = new TicketSearchCriteria();
        criteria.setAdminStatus(AdminStatus.PASSED);
        criteria.setCustomerStatus(CustomerStatus.NEW);
        return ticketSearchService.findAll(criteria, Arrays.asList(new SortCriterion("ticketId", true)));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void performFinancialCheck(AuthenticatedUser user, long ticketId)
            throws FinancialCheckException, TicketNotFoundException, TicketIllegalStateException {
        LOG.info("Performing financial check, ticketId=" + ticketId);
        Ticket t = refreshTicket(ticketId);
        assertTrue(isAdminPassed(t), "admin passed", t);
        assertTrue(isTechPassed(t), "tech passed", t);

        if (isCanceled(t) || isFinancialPassed(t)) {
            LOG.info("Financial check passed due to current financial status (passed)");
            return;
        }

        Reservation reservation = paymentSearchService.getReservationForTicket(ticketId);

        if (isCharityTicket(t)) {
            new CharityFinancialCheck(t, ticketService, paymentService, paymentSearchService, depositService)
                    .performFinancialCheck(user, new OpInfo(user));
        } else if (isADPTransaction(reservation)) {
            new ADPFinancialCheck(t, reservation == null ? null : reservation.getId(), ticketService, paymentService,
                    paymentSearchService, emailTemplateSender, nicHandleSearchService, depositService,
                    domainSearchService).performFinancialCheck(user, new OpInfo(user));
        } else {
            new CCFinancialCheck(t, reservation.getId(), ticketService, paymentService, paymentSearchService,
                    depositService).performFinancialCheck(user, new OpInfo(user));
        }
    }

    private boolean isADPTransaction(Reservation reservation) throws FinancialCheckException {
        if (reservation == null)
            return true;

        try {
            return paymentSearchService.getTransaction(reservation.getTransactionId()).isADPTransaction();
        } catch (TransactionNotFoundException e) {
            throw new FinancialCheckException("No transaction found for reservation id=" + reservation.getId(), e);
        }
    }

    private void assertTrue(boolean check, String expectedState, Ticket t) throws TicketIllegalStateException {
        if (!check)
            throw new TicketIllegalStateException("Expected state was: " + expectedState, t);
    }

    /**
     * This one must run in the transaction!
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String promoteTicketToDomain(AuthenticatedUser user, long ticketId)
            throws TicketIllegalStateException, TicketNotFoundException, NicHandleNotFoundException {
        OpInfo opInfo = new OpInfo(user);
        // refresh the ticket in the current transaction
        Ticket t = refreshTicket(ticketId);

        if (!isFullPassed(t)) {
            throw new TicketIllegalStateException("not fully passed", t);
        }

        if (!isRegistrationTicket(t)) {
            throw new TicketIllegalStateException("not registration ticket", t);
        }

        DomainOperation op = t.getOperation();
        String domainName = op.getDomainNameField().getNewValue();

        List<String> techContacts = convertContactsToStringList(op.getTechContactsField());
        List<String> billContacts = convertContactsToStringList(op.getBillingContactsField());
        List<String> adminContacts = convertContactsToStringList(op.getAdminContactsField());
        List<Nameserver> nameservers = convertNameservers(op.getNameserversField());
        NewDomain newDomain = new NewDomain(domainName, op.getDomainHolderField().getNewValue(),
                op.getDomainHolderClassField().getNewValue(), op.getDomainHolderCategoryField().getNewValue(),
                op.getDomainHolderSubcategoryField().getNewValue(), t.getCreator().getNicHandle(),
                op.getResellerAccountField().getNewValue().getId(), t.getRequestersRemark(), techContacts, billContacts,
                adminContacts, nameservers, t.getDomainPeriod());

        DsmEvent event;
        if (isCharityTicket(t)) {
            Date renewalDate = DateUtils.addYears(new Date(), 1);

            if (isDirectRegistration(t)) {
                event = new CreateCharityDomainDirect(renewalDate);
            } else {
                event = new CreateCharityDomainRegistrar(renewalDate);
            }
        } else {
            if (isDirectRegistration(t)) {
                event = new CreateBillableDomainDirect(new Date());
            } else {
                if (t.isAutorenewMode()) {
                    event = new CreateBillableAutorenewedDomainRegistrar(new Date());
                } else {
                    event = new CreateBillableDomainRegistrar(new Date());
                }
            }
        }

        dsm.handleEvent(user, newDomain, event, opInfo);

        ticketService.delete(t, opInfo);

        return domainName;
    }

    private Ticket refreshTicket(long ticketId) throws TicketIllegalStateException, TicketNotFoundException {
        Ticket t = ticketService.lockTicket(ticketId);

        if (!isRegistrationTicket(t) && !isTransferTicket(t) && !isModificationTicket(t)) {
            throw new TicketIllegalStateException("not a registration nor transfer ticket", t);
        }

        return t;
    }

    private boolean isCharityTicket(Ticket t) {
        return t.isCharity();
    }

    private boolean isDirectRegistration(Ticket t) throws NicHandleNotFoundException {
        return nicHandleSearchService.isNicHandleDirect(t.getCreator().getNicHandle());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String promoteTransferTicket(AuthenticatedUser user, long ticketId)
            throws TicketIllegalStateException, TicketNotFoundException, DomainNotFoundException, NicHandleException,
            EmptyRemarkException, DomainIllegalStateException, ValidationException, ClassDoesNotMatchCategoryException,
            CategoryDoesNotMatchSubcategoryException {
        OpInfo opInfo = new OpInfo(user);
        // refresh the ticket in the current transaction
        Ticket ticket = refreshTicket(ticketId);

        if (!isFullPassed(ticket)) {
            throw new TicketIllegalStateException("not fully passed", ticket);
        }

        if (!isTransferTicket(ticket)) {
            throw new TicketIllegalStateException("not transfer ticket", ticket);
        }
        Date transferDate = new Date();
        Domain domain = prepareDomainToTransfer(ticket, transferDate);
        String oldBillC = ticket.getOperation().getBillingContactsField().get(0).getCurrentValue().getNicHandle();
        String newBillC = ticket.getOperation().getBillingContactsField().get(0).getNewValue().getNicHandle();
        domainService.createTransferRecord(domain.getName(), transferDate, oldBillC, newBillC);
        domainService.save(domain, opInfo);

        DsmEvent event;
        if (isDirectRegistration(ticket)) {
            event = new TransferToDirect(ticket);
        } else {
            Reservation r = paymentSearchService.getReservationForTicket(ticket.getId());
            if (r == null) {
                LOG.warn("No reservation found for ticketId = " + ticket.getId()
                        + ", will trigger TransferToRegistrar without PAY_METHOD");
                if (ticket.isAutorenewMode()) {
                    event = new TransferWithAutorenewToRegistrar(ticket);
                } else {
                    event = new TransferToRegistrar(ticket);
                }
            } else {
                TransactionDetails details = new TransactionDetails(
                        ticket.getOperation().getDomainNameField().getNewValue(),
                        ticket.getOperation().getDomainHolderField()
                                .getNewValue(), ticket.getDomainPeriod().getYears(), OperationType.TRANSFER,
                        r.getTotal());
                try {
                    BigDecimal transactionValue =
                            paymentSearchService.getTransaction(r.getTransactionId()).getTotalCost();
                    if (ticket.isAutorenewMode()) {
                        event = new TransferWithAutorenewToRegistrar(ticket, r.getPaymentMethod(), details,
                                transactionValue,
                                r.getOrderId());
                    } else {
                        event = new TransferToRegistrar(ticket, r.getPaymentMethod(), details, transactionValue,
                                r.getOrderId());
                    }
                } catch (TransactionNotFoundException e) {
                    String msg = "No transaction found for id=" + r.getTransactionId() + ", reservationId=" + r.getId()
                            + ", orderId=" + r.getOrderId();
                    LOG.error(msg);
                    throw new IllegalStateException(msg, e);
                }
            }
        }

        dsm.handleEvent(user, domain, event, opInfo);

        ticketService.delete(ticket, opInfo);

        // Remove domain related DNS notifications.
        dnsNotificationService.removeObsoleteNotifications(domain.getName(), null, Collections.<String>emptySet());

        return domain.getName();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String promoteModificationTicket(AuthenticatedUser user, long ticketId)
            throws TicketNotFoundException, TicketIllegalStateException, DomainNotFoundException, NicHandleException,
            ValidationException, EmptyRemarkException, ClassDoesNotMatchCategoryException,
            CategoryDoesNotMatchSubcategoryException {
        OpInfo opInfo = new OpInfo(user);
        Ticket t = refreshTicket(ticketId);
        if (!isFullPassed(t)) {
            throw new TicketIllegalStateException("not fully passed", t);
        }

        if (!isModificationTicket(t)) {
            throw new TicketIllegalStateException("not modification ticket", t);
        }

        Domain domain = prepareDomainToModify(t);
        domainService.save(domain, opInfo);
        ticketService.delete(t, opInfo);
        return domain.getName();
    }

    private Domain prepareDomainToModify(Ticket t) throws DomainNotFoundException {
        DomainOperation op = t.getOperation();
        String domainName = op.getDomainNameField().getNewValue();
        Domain domain = domainSearchService.getDomain(domainName);

        List<Contact> billContacts = convertContactsToContactList(op.getBillingContactsField());
        List<Contact> techContacts = convertContactsToContactList(op.getTechContactsField());
        List<Contact> adminContacts = convertContactsToContactList(op.getAdminContactsField());
        List<Nameserver> nameservers = convertNameservers(op.getNameserversField());

        domain.setBillingContacts(billContacts);
        domain.setTechContacts(techContacts);
        domain.setAdminContacts(adminContacts);
        domain.setNameservers(nameservers);

        domain.setHolder(op.getDomainHolderField().getNewValue());
        domain.setHolderClass(op.getDomainHolderClassField().getNewValue());
        domain.setHolderCategory(op.getDomainHolderCategoryField().getNewValue());
        domain.setHolderSubcategory(op.getDomainHolderSubcategoryField().getNewValue());
        domain.setRemark(t.getHostmastersRemark());

        return domain;
    }

    private Domain prepareDomainToTransfer(Ticket t, Date transferDate) throws DomainNotFoundException {
        DomainOperation op = t.getOperation();
        String domainName = op.getDomainNameField().getNewValue();
        Domain domain = domainSearchService.getDomain(domainName);

        Account gainingCustomerAccount = op.getResellerAccountField().getNewValue();
        List<Contact> billContacts = convertContactsToContactList(op.getBillingContactsField());
        List<Contact> techContacts = convertContactsToContactList(op.getTechContactsField());
        List<Contact> adminContacts = convertContactsToContactList(op.getAdminContactsField());
        List<Nameserver> nameservers = convertNameservers(op.getNameserversField());

        domain.setResellerAccount(gainingCustomerAccount);
        domain.setBillingContacts(billContacts);
        domain.setTechContacts(techContacts);
        domain.setAdminContacts(adminContacts);
        domain.setNameservers(nameservers);
        domain.setTransferDate(transferDate);
        domain.setAuthCode(null);
        domain.setAuthCodeExpirationDate(null);

        return domain;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void performTechnicalCheck(AuthenticatedUser user, long ticketId, boolean interactive)
            throws TicketIllegalStateException, TicketNotFoundException, TechnicalCheckException,
            HostNotConfiguredException {
        LOG.info("Performing technical check, ticketId=" + ticketId);
        Ticket t = refreshTicket(ticketId);
        assertTrue(isAdminPassed(t), "admin passed", t);
        if (isCanceled(t) || isTechPassed(t)) {
            LOG.info("Tech check passed due to current tech status (passed)");
            return;
        }

        final String domainName = t.getOperation().getDomainNameField().getNewValue();
        List<NameserverChange> nameserverChanges = t.getOperation().getNameserversField();

        Set<String> failedNameservers = new HashSet<>();
        for (NameserverChange ns : nameserverChanges) {
            final String nsName = ns.getName().getNewValue();
            if (nsName != null) {
                failedNameservers.add(nsName);
            }
        }
        try {
            Function<NameserverChange, Nameserver> getNewNameserver = new Function<NameserverChange, Nameserver>() {
                public Nameserver apply(NameserverChange ns) {
                    return ns.getNewNameserver();
                }
            };
            List<Nameserver> newNameservers = Lists.transform(nameserverChanges, getNewNameserver);
            dnsCheckService.check(Arrays.asList(domainName), newNameservers, user.getUsername(), true);
            failedNameservers = new HashSet<>();
        } catch (DnsCheckProcessingException e) {
            throw new TechnicalCheckException(e);
        } catch (HostNotConfiguredException e) {
            LOG.info("Tech check failed", e);
            setTechnicalStatusStalled(t.getId(), new OpInfo(user));
            //TODO refactor to send immediately?
            if (interactive)
                throw e;
            else {
                addDnsNotification(domainName, ticketId, e);
                return;
            }
        } finally {
            dnsNotificationService.removeObsoleteNotifications(domainName, ticketId, failedNameservers);
        }
        setTechnicalStatusPassed(t.getId(), new OpInfo(user));
        sendDNSSuccessEmail(user, t);
    }

    private void sendDNSSuccessEmail(AuthenticatedUser user, Ticket t) {
        try {
            DomainOperation.DomainOperationType operationType = t.getOperation().getType();
            switch (operationType) {
                case XFER:
                    TransferTicketEmailParameters transferParameters =
                            new TransferTicketEmailParameters(user.getUsername(), t);
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.XFER_DNS_CHECK_SUCCESS.getId(),
                            transferParameters);
                    break;
                case REG:
                    TicketEmailParameters parameters = new TicketEmailParameters(user.getUsername(), t);
                    emailTemplateSender.sendEmail(EmailTemplateNamesEnum.NREG_DNS_CHECK_SUCCESS.getId(), parameters);
                    break;
                case MOD:
                    break;
                default:
                    throw new IllegalArgumentException("Invalid domain operation type: " + operationType);
            }
        } catch (Exception e) {
            LOG.error("Error while sending dns success during transfer email", e);
        }
    }

    private void setTechnicalStatusStalled(long ticketId, OpInfo opInfo) throws TicketNotFoundException {
        LOG.info("Setting tech status to Stalled, ticketId=" + ticketId);
        ticketService.updateTechStatus(ticketId, TechStatus.STALLED, opInfo);
    }

    private void addDnsNotification(String domainName, long ticketId, HostNotConfiguredException e) {
        for (String nsName : e.getNameserverNames()) {
            DnsNotification dnsNotification = new DnsNotification(domainName, ticketId, nsName, new Date(),
                    e.getSingleOutputMessage(domainName, nsName, true));
            dnsNotificationService.createNotification(dnsNotification);
        }
    }

    private void setTechnicalStatusPassed(long ticketId, OpInfo opInfo) throws TicketNotFoundException {
        LOG.info("Setting tech status to Passed, ticketId=" + ticketId);
        ticketService.updateTechStatus(ticketId, TechStatus.PASSED, opInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void performTicketCancellation(AuthenticatedUser user, long ticketId)
            throws TicketIllegalStateException, TicketNotFoundException, TransactionNotFoundException,
            CardPaymentException, DomainNotFoundException, DomainIllegalStateException {
        OpInfo opInfo = new OpInfo(user);
        Ticket t = refreshTicket(ticketId);
        if (!isAdminCancelled(t)) {
            throw new TicketIllegalStateException("ticket not cancelled", t);
        }
        if (t.getOperation().getType() == DomainOperation.DomainOperationType.XFER) {
            dsm.handleEvent(user, t.getOperation().getDomainNameField().getNewValue(), new TransferCancellation(t),
                    opInfo);
        }
    }
}
