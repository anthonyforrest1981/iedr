package pl.nask.crs.ticket.services.impl;

/*>>>import org.checkerframework.checker.nullness.qual.EnsuresNonNull;*/
/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.*;

import org.apache.log4j.Logger;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.ValidationHelper;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.tickets.TicketConverter;
import pl.nask.crs.app.tickets.exceptions.*;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.dns.DomainNameValidator;
import pl.nask.crs.commons.dns.NameserverValidator;
import pl.nask.crs.commons.dns.exceptions.InvalidDomainNameException;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.ContactUtils;
import pl.nask.crs.contacts.ContactValidator;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainHolderType;
import pl.nask.crs.domains.exceptions.DomainLockedException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.EntitySubcategory;
import pl.nask.crs.entities.OwnerType;
import pl.nask.crs.entities.exceptions.*;
import pl.nask.crs.entities.service.EntityService;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.ticket.*;
import pl.nask.crs.ticket.dao.HistoricalTicketDAO;
import pl.nask.crs.ticket.dao.TicketDAO;
import pl.nask.crs.ticket.exceptions.*;
import pl.nask.crs.ticket.operation.*;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.ticket.services.TicketService;
import pl.nask.crs.user.permissions.NamedPermissionQuery;
import pl.nask.crs.user.permissions.PermissionDeniedException;

public class TicketServiceImpl implements TicketService {

    private static final Logger LOGGER = Logger.getLogger(TicketServiceImpl.class);

    private TicketDAO ticketDAO;
    private HistoricalTicketDAO historicalTicketDAO;
    private final DomainSearchService domainSearchService;
    private NicHandleSearchService nicHandleSearchService;

    private TicketSearchService ticketSearchService;

    private ServicesRegistry servicesRegistry;

    private final AuthorizationService authService;

    private final EntityService entityService;

    @SuppressWarnings("nullness")
    // servicesRegistry is setup via setter by Spring - must be that way to handle circular dependencies
    public TicketServiceImpl(TicketDAO ticketDAO, HistoricalTicketDAO historicalTicketDAO,
            DomainSearchService domainSearchService, NicHandleSearchService nicHandleSearchService,
            TicketSearchService ticketSearchService, EntityService entityService, AuthorizationService authService) {
        Validator.assertNotNull(ticketDAO, "ticket dao");
        Validator.assertNotNull(historicalTicketDAO, "historical ticket dao");
        Validator.assertNotNull(domainSearchService, "domain search service");
        Validator.assertNotNull(nicHandleSearchService, "nic handle search service");
        Validator.assertNotNull(ticketSearchService, "ticket search service");
        Validator.assertNotNull(entityService, "entitles service");
        Validator.assertNotNull(authService, "authorization service");
        this.ticketDAO = ticketDAO;
        this.historicalTicketDAO = historicalTicketDAO;
        this.domainSearchService = domainSearchService;
        this.nicHandleSearchService = nicHandleSearchService;
        this.ticketSearchService = ticketSearchService;
        this.entityService = entityService;
        this.authService = authService;
    }

    @Override
    public Ticket lockTicket(long id) throws TicketNotFoundException {
        if (ticketDAO.lock(id)) {
            final Ticket ticket = ticketDAO.get(id);
            assert ticket != null : "@AssumeAssertion(nullness)";
            return ticket;
        } else {
            throw new TicketNotFoundException(id);
        }
    }

    @Override
    public void checkOut(long ticketId, OpInfo opInfo)
            throws TicketNotFoundException, TicketAlreadyCheckedOutException {
        Validator.assertNotNull(opInfo, "opInfo");
        Ticket ticket = lockTicket(ticketId);
        if (ticket.isCheckedOut()) {
            final Contact checkedOutTo = ticket.getCheckedOutTo();
            if (!Validator.isEmpty(opInfo.getUserName()) && !opInfo.getUserName().equals(checkedOutTo.getNicHandle())) {
                throw new TicketAlreadyCheckedOutException(checkedOutTo);
            }
        }
        ticket.checkOut(new Contact(opInfo.getUserName()));
        updateTicketAndHistory(ticket, opInfo);
    }

    @Override
    public void checkIn(long ticketId, AdminStatus adminStatus, OpInfo opInfo)
            throws TicketNotFoundException, TicketNotCheckedOutException, TicketCheckedOutToOtherException {
        Validator.assertNotNull(adminStatus, "admin status");
        Validator.assertNotNull(opInfo, "opInfo");
        Ticket ticket = lockTicket(ticketId);
        validateCheckedOutTo(ticket, opInfo.getUserName());
        ticket.checkIn();
        ticket.setAdminStatus(adminStatus);
        updateTicketAndHistory(ticket, opInfo);
    }

    @Override
    public void alterStatus(AuthenticatedUser user, long ticketId, AdminStatus adminStatus, OpInfo opInfo)
            throws TicketNotFoundException, TicketCheckedOutToOtherException, TicketEmailException,
            TicketNotCheckedOutException, CategoryDoesNotMatchSubcategoryException, ClassDoesNotMatchCategoryException {
        Validator.assertNotNull(adminStatus, "admin status");
        Validator.assertNotNull(opInfo, "opInfo");
        Ticket ticket = lockTicket(ticketId);
        validateCheckedOutTo(ticket, opInfo.getUserName());
        if (ticket.setAdminStatus(adminStatus)) {
            if (adminStatus == AdminStatus.PASSED) {
                accept(user, ticketId, ticket.getOperation(), opInfo);
            }
            updateTicketAndHistory(ticket, opInfo);
        }
    }

    @Override
    public void reassign(long ticketId, String newHostmasterHandle, OpInfo opInfo)
            throws TicketNotFoundException, TicketNotCheckedOutException, TicketUserCannotCheckOutException {
        Validator.assertNotNull(newHostmasterHandle, "newHostmasterHandle");
        validateNewHostmaster(newHostmasterHandle);
        Ticket ticket = lockTicket(ticketId);
        if (!ticket.isCheckedOut()) {
            throw new TicketNotCheckedOutException();
        }
        Contact checkedOutTo = new Contact(newHostmasterHandle);
        if (!checkedOutTo.equals(ticket.getCheckedOutTo())) {
            ticket.checkIn();
            ticket.checkOut(checkedOutTo);
            updateTicketAndHistory(ticket, opInfo);
        }
    }

    private void validateNewHostmaster(final String newHostmasterHandle) throws TicketUserCannotCheckOutException {
        try {
            authService.authorize(newHostmasterHandle,
                    new NamedPermissionQuery(TicketAppService.class.getCanonicalName() + ".checkOut"));
        } catch (PermissionDeniedException e) {
            throw new TicketUserCannotCheckOutException("Cannot reassign ticket to user " + newHostmasterHandle
                    + " because the user doesn't have checkout permission");
        }
    }

    private void validateTicketEdit(Ticket ticket) throws TicketEditFlagException {
        final Account account = ticket.getOperation().getResellerAccountField().getNewValue();
        assert account != null : "@AssumeAssertion(nullness)";
        if (!account.isTicketEdit()) {
            throw new TicketEditFlagException();
        }
    }

    @Override
    public void accept(AuthenticatedUser user, long ticketId, DomainOperation domainOperation, OpInfo opInfo)
            throws TicketNotFoundException, TicketEmailException, TicketCheckedOutToOtherException,
            TicketNotCheckedOutException, ClassDoesNotMatchCategoryException, CategoryDoesNotMatchSubcategoryException {
        Ticket ticket = lockTicket(ticketId);
        validateCheckedOutTo(ticket, opInfo.getUserName());

        ticket.setAdminStatus(AdminStatus.PASSED);
        ticket.getOperation().clearFailureReasons();
        populateAndValidateHolderSubcategory(ticket.getOperation(), domainOperation);
        ticket.setHostmastersRemark(opInfo.getRemark());
        ticket.checkIn();
        updateTicketAndHistory(ticket, opInfo);
        sendEmail(user, ticket, TicketEmailType.Accept);
    }

    @Override
    public void sendEmail(AuthenticatedUser user, Ticket ticket, TicketEmailType emailType)
            throws TicketEmailException {
        EmailParameters parameters = new TicketEmailParameters(user.getUsername(), ticket);
        DomainOperationType type = ticket.getOperation().getType();
        EmailTemplateNamesEnum template = null;
        try {
            switch (type) {
                case REG:
                    if (emailType == TicketEmailType.Accept) {
                        template = EmailTemplateNamesEnum.ACCEPT_REGISTRATION;
                    } else {
                        template = EmailTemplateNamesEnum.QUERY_REGISTRATION;
                    }
                    break;
                case XFER:
                    parameters = new TransferTicketEmailParameters(user.getUsername(), ticket);
                    if (emailType == TicketEmailType.Accept) {
                        template = EmailTemplateNamesEnum.ACCEPT_TRANSFER;
                    } else {
                        template = EmailTemplateNamesEnum.QUERY_TRANSFER;
                    }
                    break;
                case MOD:
                    // different templates are to be used by Directs and the Registrars AND the contact type (billing or admin).
                    boolean createdByBillingC = ticket.isCreatedByBillingContact();
                    if (emailType == TicketEmailType.Accept) {
                        assert ticket.getOperation().getResellerAccountField().getNewValue()
                                != null : "@AssumeAssertion(nullness)";
                        if (nicHandleSearchService.isNicHandleDirect(ticket.getCreator().getNicHandle())) {
                            if (createdByBillingC) {
                                template = EmailTemplateNamesEnum.ACCEPT_MODIFICATION_DIRECT_B;
                            } else {
                                template = EmailTemplateNamesEnum.ACCEPT_MODIFICATION_DIRECT_A;
                            }
                        } else {
                            if (createdByBillingC) {
                                template = EmailTemplateNamesEnum.ACCEPT_MODIFICATION_REG_B;
                            } else {
                                template = EmailTemplateNamesEnum.ACCEPT_MODIFICATION_REG_A;
                            }
                        }
                    } else {
                        // Billing NH don't need to know, if the modification made by the Admin or Tech contact was queried.
                        if (ticket.isCreatedByBillingContact()) {
                            sendEmail(EmailTemplateNamesEnum.QUERY_MODIFICATION, parameters);
                        } else {
                            sendEmail(EmailTemplateNamesEnum.QUERY_MODIFICATION_A, parameters);
                        }
                    }
                    break;
                default:
                    throw new IllegalArgumentException("unsupported ticket operation type: " + type);
            }
            if (template != null) {
                sendEmail(template, parameters);
            }
        } catch (Exception e) {
            throw new TicketEmailException(e);
        }
    }

    @Override
    public void reject(AuthenticatedUser user, long ticketId, AdminStatus status, DomainOperation domainOperation,
            OpInfo opInfo)
            throws TicketNotFoundException, InvalidStatusException, EmptyRemarkException, TicketEmailException,
            TicketCheckedOutToOtherException, TicketNotCheckedOutException, ClassDoesNotMatchCategoryException,
            CategoryDoesNotMatchSubcategoryException {
        Ticket ticket = lockTicket(ticketId);
        validateCheckedOutTo(ticket, opInfo.getUserName());
        validateRemark(opInfo.getRemark());
        validateRejectionStatus(status);

        populateFailureReasons(ticket.getOperation(), domainOperation);
        populateAndValidateHolderSubcategory(ticket.getOperation(), domainOperation);
        ticket.setHostmastersRemark(opInfo.getRemark());
        ticket.setAdminStatus(status);
        ticket.checkIn();
        updateTicketAndHistory(ticket, opInfo);
        sendEmail(user, ticket, TicketEmailType.Query);
    }

    @Override
    public void save(long ticketId, DomainOperation domainOperation, OpInfo opInfo)
            throws TicketNotFoundException, EmptyRemarkException, TicketCheckedOutToOtherException,
            TicketNotCheckedOutException, ClassDoesNotMatchCategoryException, CategoryDoesNotMatchSubcategoryException {
        Validator.assertNotNull(domainOperation, "domainOperation");
        Ticket ticket = lockTicket(ticketId);
        validateCheckedOutTo(ticket, opInfo.getUserName());
        validateRemark(opInfo.getRemark());
        populateFailureReasons(ticket.getOperation(), domainOperation);
        populateAndValidateHolderSubcategory(ticket.getOperation(), domainOperation);
        ticket.setHostmastersRemark(opInfo.getRemark());
        updateTicketAndHistory(ticket, opInfo);
    }

    @Override
    public void updateAsAdmin(long ticketId, TicketEdit ticketEdit, /*>>>@Nullable*/ String requestersRemark,
            OpInfo opInfo, boolean clikPaid, boolean forceUpdate)
            throws ValidationException, TicketEditFlagException, EmptyRemarkException, NicHandleException,
            AccountNotFoundException, HolderClassNotExistException, HolderCategoryNotExistException,
            ClassDoesNotMatchCategoryException, TicketNotFoundException, TicketCheckedOutToOtherException,
            TicketNotCheckedOutException, CategoryDoesNotMatchSubcategoryException, HolderSubcategoryNotExistException {
        Ticket ticket = lockTicket(ticketId);
        validateCheckedOutTo(ticket, opInfo.getUserName());
        doUpdate(ticket, ticketEdit, requestersRemark, opInfo, clikPaid, forceUpdate);
    }

    @Override
    public void updateAsOwner(long ticketId, TicketEdit ticketEdit, String requestersRemark, OpInfo opInfo,
            boolean clikPaid, boolean documentsWereSent)
            throws ValidationException, TicketEditFlagException, EmptyRemarkException, NicHandleException,
            AccountNotFoundException, TicketNotFoundException, TicketAlreadyCheckedOutException,
            TicketHolderChangeException, DomainNotFoundException {
        Ticket ticket = lockTicket(ticketId);
        if (ticket.getOperation().getType() == DomainOperationType.XFER) {
            validateHolderNotChanged(ticketEdit);
        }
        fillDomainHolderEntities(ticketEdit, ticket.getOperation());
        validateNotCheckedOut(ticket);
        updateStatuses(ticket, documentsWereSent, ticketEdit.getNameserversField());
        try {
            doUpdate(ticket, ticketEdit, requestersRemark, opInfo, clikPaid, true);
        } catch (HolderSubcategoryNotExistException | CategoryDoesNotMatchSubcategoryException e) {
            LOGGER.error("Subcategory should always exist and match category when updating ticket as owner", e);
            throw new IllegalStateException();
        } catch (HolderClassNotExistException | ClassDoesNotMatchCategoryException |
                HolderCategoryNotExistException e) {
            LOGGER.error("Class and category should always exist and match when updating ticket as owner", e);
            throw new IllegalStateException();
        }
    }

    private void fillDomainHolderEntities(TicketEdit ticketEdit, DomainOperation domainOperation) {
        EntityClass currentHolderClass = domainOperation.getDomainHolderClassField().getNewValue();
        EntityCategory currentHolderCategory = domainOperation.getDomainHolderCategoryField().getNewValue();
        EntitySubcategory currentSubcategory = domainOperation.getDomainHolderSubcategoryField().getNewValue();
        assert currentHolderClass != null : "@AssumeAssertion(nullness)";
        assert currentHolderCategory != null : "@AssumeAssertion(nullness)";
        ticketEdit.getDomainHolderClassField().setNewValue(currentHolderClass.getId());
        ticketEdit.getDomainHolderCategoryField().setNewValue(currentHolderCategory.getId());
        ticketEdit.getDomainHolderSubcategoryField().setNewValue(
                currentSubcategory == null ? null : currentSubcategory.getId());
    }

    private void updateStatuses(Ticket ticket, boolean documentsWereSent, List<NameserverChange> nameserverChanges) {
        if (ticket.getOperation().getType() != DomainOperationType.XFER) {
            ticket.setAdminStatus(documentsWereSent ? AdminStatus.DOCUMENTS_SUBMITTED : AdminStatus.RENEW);
        }
        if (nameserversEdited(ticket, nameserverChanges)) {
            ticket.setTechStatus(TechStatus.NEW);
        }
    }

    private boolean nameserversEdited(Ticket ticket, List<NameserverChange> nameserverChanges) {
        if (ticket.getOperation().getNameserversField().size() != nameserverChanges.size())
            return true;
        Function<NameserverChange, Nameserver> nameserverChangeToNameserver =
                new Function<NameserverChange, Nameserver>() {
                    @Override
                    public /*>>>@Nullable*/ Nameserver apply(/*>>>@Nullable*/ NameserverChange nameserverChange) {
                        if (nameserverChange != null) {
                            return nameserverChange.getNewNameserver();
                        } else {
                            return null;
                        }
                    }
                };
        Iterable<Nameserver> origNameservers =
                Iterables.transform(ticket.getOperation().getNameserversField(), nameserverChangeToNameserver);
        Iterable<Nameserver> newNameservers =
                Iterables.transform(nameserverChanges, nameserverChangeToNameserver);
        return !Iterables.elementsEqual(origNameservers, newNameservers);
    }

    /**
     * Do the actual update operation, that is check all universal validations (that must pass always for a ticket),
     * then update ticket object in the database and create historical entries about the update.
     */
    private void doUpdate(Ticket ticket, TicketEdit ticketEdit, /*>>>@Nullable*/ String requestersRemark,
            OpInfo opInfo, boolean clikPaid, boolean forceUpdate)
            throws ValidationException, TicketEditFlagException, EmptyRemarkException, NicHandleException,
            AccountNotFoundException, HolderClassNotExistException, HolderCategoryNotExistException,
            ClassDoesNotMatchCategoryException, CategoryDoesNotMatchSubcategoryException,
            HolderSubcategoryNotExistException {
        if (!forceUpdate) {
            validateTicketEdit(ticket);
        }
        validateRemark(opInfo.getRemark());
        populateValues(ticket.getOperation(), ticketEdit);
        populateFailureReasons(ticket.getOperation(), ticketEdit);
        ticket.setHostmastersRemark(opInfo.getRemark());
        ticket.setClikPaid(clikPaid);
        if (!Validator.isEmpty(requestersRemark))
            ticket.setRequestersRemark(requestersRemark);
        validateTicket(ticket);
        updateTicketAndHistory(ticket, opInfo);
    }

    private void populateValues(DomainOperation domainOperation, TicketEdit ticketEdit)
            throws NicHandleNotFoundException, AccountNotFoundException, ContactSyntaxException,
            HolderClassMandatoryException, HolderCategoryMandatoryException, HolderClassNotExistException,
            HolderCategoryNotExistException, HolderSubcategoryNotExistException {
        domainOperation.getDomainHolderField().populateValue(ticketEdit.getDomainHolderField());
        populateClass(domainOperation.getDomainHolderClassField(),
                ticketEdit.getDomainHolderClassField().getNewValue());
        populateCategory(domainOperation.getDomainHolderCategoryField(),
                ticketEdit.getDomainHolderCategoryField().getNewValue());
        populateSubcategory(domainOperation.getDomainHolderSubcategoryField(),
                ticketEdit.getDomainHolderSubcategoryField().getNewValue());
        Long accountId = ticketEdit.getResellerAccountField().getNewValue();
        assert accountId != null : "@AssumeAssertion(nullness)";
        Account account = servicesRegistry.getAccountSearchService().getAccount(accountId);
        domainOperation.getResellerAccountField().setNewValue(account);
        populateContacts(domainOperation.getAdminContactsField(), ticketEdit.getAdminContactField());
        populateContacts(domainOperation.getTechContactsField(), ticketEdit.getTechContactField());
        populateContacts(domainOperation.getBillingContactsField(), ticketEdit.getBillingContactField());
        List<NameserverChange> newNameservers = ticketEdit.getNameserversField();
        int size = domainOperation.getNameserversField().size();
        if (newNameservers.size() < size) {
            domainOperation.getNameserversField().subList(newNameservers.size(), size).clear();
        }
        for (int i = 0; i < newNameservers.size(); ++i) {
            if (i >= size) {
                domainOperation.getNameserversField().add(new NameserverChange(
                        new SimpleDomainFieldChange<String>(null, null),
                        new IpFieldChange(null, null),
                        new IpFieldChange(null, null)));
            }
            NameserverChange ns2 = newNameservers.get(i);
            domainOperation.getNameserversField().get(i).populateValue(ns2);
        }
    }

    private void populateClass(SimpleDomainFieldChange<EntityClass> domainHolderClassField,
            /*>>>@Nullable*/ Long classId)
            throws HolderClassMandatoryException, HolderClassNotExistException {
        if (classId == null) {
            throw new HolderClassMandatoryException();
        }
        EntityClass domainHolderClass = entityService.getClass(classId);
        domainHolderClassField.setNewValue(domainHolderClass);
    }

    private void populateCategory(SimpleDomainFieldChange<EntityCategory> domainHolderCategoryField,
            /*>>>@Nullable*/ Long categoryId)
            throws HolderCategoryMandatoryException, HolderCategoryNotExistException {
        if (categoryId == null) {
            throw new HolderCategoryMandatoryException();
        }
        EntityCategory domainHolderCategory = entityService.getCategory(categoryId);
        domainHolderCategoryField.setNewValue(domainHolderCategory);
    }

    private void populateSubcategory(SimpleDomainFieldChange<EntitySubcategory> domainHolderSubcategoryField,
            /*>>>@Nullable*/ Long subcategoryId)
            throws HolderSubcategoryNotExistException {
        EntitySubcategory domainHolderSubcategory = null;
        if (subcategoryId != null) {
            domainHolderSubcategory = entityService.getSubcategory(subcategoryId);
        }
        domainHolderSubcategoryField.setNewValue(domainHolderSubcategory);
    }

    private void populateContacts(List<SimpleDomainFieldChange<Contact>> contacts,
            List<SimpleDomainFieldChange<String>> newContactNames)
            throws NicHandleNotFoundException, ContactSyntaxException {
        for (int i = 0; i < Math.min(contacts.size(), newContactNames.size()); i++) {
            SimpleDomainFieldChange<Contact> contactChange = contacts.get(i);
            String nicHandle = newContactNames.get(i) == null ? null : newContactNames.get(i).getNewValue();
            contactChange.setNewValue(getNewContact(nicHandle));
        }
    }

    private /*>>>@Nullable*/ Contact getNewContact(/*>>>@Nullable*/ String nicHandle)
            throws ContactSyntaxException, NicHandleNotFoundException {
        if (Validator.isEmpty(nicHandle)) {
            return null;
        } else if (!nicHandle.endsWith("-IEDR")) {
            throw new ContactSyntaxException(nicHandle);
        } else {
            return servicesRegistry.getContactSearchService().getContact(nicHandle);
        }
    }

    private List<SimpleDomainFieldChange<Contact>> asDomainFieldChangeContactList(
            Collection</*>>>? extends @Nullable*/String> nicHandleList)
                    throws ContactSyntaxException, NicHandleNotFoundException {
        List<SimpleDomainFieldChange<Contact>> list = new ArrayList<>();
        if (nicHandleList != null) {
            for (String nh : nicHandleList) {
                list.add(new SimpleDomainFieldChange<>(null, getNewContact(nh)));
            }
        }
        return list;
    }

    private void validateCheckedOutTo(Ticket ticket, String hostmasterHandle)
            throws TicketCheckedOutToOtherException, TicketNotCheckedOutException {
        if (ticket.isCheckedOut()) {
            String checkedOutTo = ticket.getCheckedOutTo().getNicHandle();
            if (!checkedOutTo.equalsIgnoreCase(hostmasterHandle)) {
                throw new TicketCheckedOutToOtherException(checkedOutTo, hostmasterHandle);
            }
        } else {
            throw new TicketNotCheckedOutException();
        }
    }

    private void populateFailureReasons(DomainOperation domainOperation, TicketEdit ticketEdit) {
        domainOperation.getDomainNameField().populateFailureReason(ticketEdit.getDomainNameField());
        domainOperation.getDomainHolderField().populateFailureReason(ticketEdit.getDomainHolderField());
        domainOperation.getDomainHolderClassField().populateFailureReason(ticketEdit.getDomainHolderClassField());
        domainOperation.getDomainHolderCategoryField().populateFailureReason(ticketEdit.getDomainHolderCategoryField());
        domainOperation.getResellerAccountField().populateFailureReason(ticketEdit.getResellerAccountField());
        populateContactFailureReasons(domainOperation.getAdminContactsField(), ticketEdit.getAdminContactField());
        populateContactFailureReasons(domainOperation.getTechContactsField(), ticketEdit.getTechContactField());
        populateContactFailureReasons(domainOperation.getBillingContactsField(), ticketEdit.getBillingContactField());
        List<NameserverChange> oldNameservers = domainOperation.getNameserversField();
        List<NameserverChange> newNameservers = ticketEdit.getNameserversField();
        // set nameserver failure reasons only to existing nameservers
        for (int i = 0; i < Math.min(oldNameservers.size(), newNameservers.size()); ++i) {
            NameserverChange ns1 = oldNameservers.get(i);
            NameserverChange ns2 = newNameservers.get(i);
            ns1.populateFailureReason(ns2);
        }
    }

    private void populateFailureReasons(DomainOperation domainOperation, DomainOperation newValues) {
        domainOperation.getDomainNameField().populateFailureReason(newValues.getDomainNameField());
        domainOperation.getDomainHolderField().populateFailureReason(newValues.getDomainHolderField());
        domainOperation.getDomainHolderClassField().populateFailureReason(newValues.getDomainHolderClassField());
        domainOperation.getDomainHolderCategoryField().populateFailureReason(newValues.getDomainHolderCategoryField());
        domainOperation.getResellerAccountField().populateFailureReason(newValues.getResellerAccountField());
        populateContactFailureReasons(domainOperation.getAdminContactsField(), newValues.getAdminContactsField());
        populateContactFailureReasons(domainOperation.getTechContactsField(), newValues.getTechContactsField());
        populateContactFailureReasons(domainOperation.getBillingContactsField(), newValues.getBillingContactsField());
        List<NameserverChange> oldNameservers = domainOperation.getNameserversField();
        List<NameserverChange> newNameservers = newValues.getNameserversField();
        // set nameserver failure reasons only to existing nameservers
        for (int i = 0; i < Math.min(oldNameservers.size(), newNameservers.size()); ++i) {
            NameserverChange ns1 = oldNameservers.get(i);
            NameserverChange ns2 = newNameservers.get(i);
            ns1.populateFailureReason(ns2);
        }
    }

    private void populateContactFailureReasons(List<SimpleDomainFieldChange<Contact>> contacts,
            List<? extends SimpleDomainFieldChange<?>> frContacts) {
        for (int i = 0; i < Math.min(contacts.size(), frContacts.size()); ++i) {
            SimpleDomainFieldChange<Contact> contact1 = contacts.get(i);
            SimpleDomainFieldChange<?> contact2 = frContacts.get(i);
            contact1.populateFailureReason(contact2);
        }
    }

    private void populateAndValidateHolderSubcategory(DomainOperation domainOperation, DomainOperation newValues)
            throws CategoryDoesNotMatchSubcategoryException, ClassDoesNotMatchCategoryException {
        domainOperation.getDomainHolderSubcategoryField().setNewValue(
                newValues.getDomainHolderSubcategoryField().getNewValue());
        validateHolderEntities(domainOperation);
    }

    private void validateNotCheckedOut(Ticket ticket) throws TicketAlreadyCheckedOutException {
        if (ticket.isCheckedOut()) {
            throw new TicketAlreadyCheckedOutException(ticket.getCheckedOutTo());
        }
    }

    private void validateHolderNotChanged(TicketEdit ticketEdit)
            throws DomainNotFoundException, TicketHolderChangeException {
        String domainName = ticketEdit.getDomainNameField().getNewValue();
        assert domainName != null : "@AssumeAssertion(nullness)";
        Domain domain = domainSearchService.getDomain(domainName);
        if (!domain.getHolder().equals(ticketEdit.getDomainHolderField().getNewValue())) {
            throw new TicketHolderChangeException();
        }
    }

    /*>>>@EnsuresNonNull("#1")*/
    private void validateRemark(/*>>>@Nullable*/ final String remark) throws EmptyRemarkException {
        if (Validator.isEmpty(remark)) {
            throw new EmptyRemarkException();
        }
    }

    private void validateRejectionStatus(/*>>>@Nullable*/ AdminStatus status) throws InvalidStatusException {
        if (status == null) {
            throw new InvalidStatusException();
        }
        if (status == AdminStatus.NEW || status == AdminStatus.PASSED) {
            throw new InvalidStatusException();
        }
    }

    private void updateTicketAndHistory(Ticket ticket, OpInfo opInfo) {
        ticket.updateChangeDate();
        long changeId = historicalTicketDAO.create(ticket, ticket.getChangeDate(), opInfo.getActorName());
        ticketDAO.updateUsingHistory(changeId);
    }

    @Override
    public long createRegistrationTicket(AuthenticatedUser user, TicketRequest request, Long accountId)
            throws AccessDeniedException, HolderClassNotExistException, HolderCategoryNotExistException,
            ClassDoesNotMatchCategoryException, NicHandleException, DomainNotFoundException, ValidationException,
            DomainIsNotCharityException, DomainIsCharityException, EmptyRemarkException, TooManyTicketsException,
            OwnerTypeNotExistException {
        Ticket newTicket = prepareRegistrationTicket(user, request, accountId);
        return createTicket(user, newTicket, new OpInfo(user));
    }

    @Override
    public long createTransferTicket(AuthenticatedUser user, TicketRequest request, Long accountId)
            throws AccessDeniedException, NicHandleException, DomainNotFoundException, ValidationException,
            NicHandleRecreateException, DomainIsNotCharityException, DomainIsCharityException, EmptyRemarkException,
            TooManyTicketsException {
        try {
            Ticket newTicket = prepareTransferTicket(user, request, accountId);
            return createTicket(user, newTicket, new OpInfo(user));
        } catch (HolderClassNotExistException | HolderCategoryNotExistException |
                ClassDoesNotMatchCategoryException e) {
            LOGGER.error("Class and category should always exist and match when creating a transfer ticket");
            throw new IllegalStateException();
        }
    }

    @Override
    public long createModificationTicket(AuthenticatedUser user, String domainName, String domainHolder,
            List<String> adminContacts, List<String> techContacts, List<Nameserver> nameservers, String customerRemark)
            throws UserNotAuthenticatedException, DomainNotFoundException, EmptyRemarkException, ValidationException,
            NicHandleException, DomainIsNotCharityException, DomainIsCharityException, TooManyTicketsException {
        try {
            Ticket newTicket = prepareModificationTicket(user, domainName, domainHolder, adminContacts, techContacts,
                    nameservers, customerRemark);
            return createTicket(user, newTicket, new OpInfo(user));
        } catch (HolderClassNotExistException | ClassDoesNotMatchCategoryException |
                HolderCategoryNotExistException e) {
            LOGGER.error("Class and category should always exist and match when creating a modification ticket");
            throw new IllegalStateException();
        }
    }

    private Ticket prepareRegistrationTicket(AuthenticatedUser user, TicketRequest request, long accountId)
            throws NicHandleNotFoundException, HolderClassNotExistException, ContactSyntaxException,
            HolderCategoryNotExistException, OwnerTypeNotExistException {
        OwnerType mapping = entityService.getOwnerType(request.getDomainOwnerTypeId());
        Ticket ticket = prepareTicketFromRequest(user, request, accountId, mapping.getClassId(),
                mapping.getCategoryId(), null, DomainOperationType.REG);
        ticket.setHostmastersRemark(String.format("Ticket created via %s", request.getTicketSource()));
        return ticket;
    }

    private Ticket prepareTransferTicket(AuthenticatedUser user, TicketRequest request, long accountId)
            throws NicHandleNotFoundException, HolderClassNotExistException, ContactSyntaxException,
            HolderCategoryNotExistException, NicHandleRecreateException, DomainNotFoundException {
        Domain domain = domainSearchService.getDomain(request.getDomainName());
        completeTransferRequest(request, domain, new OpInfo(user), accountId);
        return prepareTicketFromRequest(user, request, accountId, domain.getHolderClass().getId(),
                domain.getHolderCategory().getId(), domain.getHolderSubcategory(), DomainOperationType.XFER);
    }

    private Ticket prepareTicketFromRequest(AuthenticatedUser user, TicketRequest request, long accountId,
            long domainClassId, long domainCategoryId, /*>>>@Nullable*/ EntitySubcategory domainSubcategory,
            DomainOperationType operationType)
            throws NicHandleNotFoundException, HolderClassNotExistException, ContactSyntaxException,
            HolderCategoryNotExistException {
        Date crDate = new Date();
        return prepareTicket(user, operationType, crDate, request.getDomainName(), request.getDomainHolder(),
                domainClassId, domainCategoryId, domainSubcategory, accountId, request.getAdminContactNicHandles(),
                Collections.singletonList(request.getTechContactNicHandle()),
                Collections.singletonList(user.getUsername()), request.getNameservers(), request.getRequestersRemark(),
                request.getDefaultAdminStatus(), request.getRegPeriod(), request.getCharityCode(),
                request.getAutorenewMode(), crDate);
    }

    private Ticket prepareModificationTicket(AuthenticatedUser user, String domainName, String domainHolder,
            List<String> adminContacts, List<String> techContacts, List<Nameserver> nameservers, String customerRemark)
                    throws DomainNotFoundException, EmptyRemarkException, ContactSyntaxException,
                    NicHandleNotFoundException, HolderClassNotExistException, HolderCategoryNotExistException {
        if (Validator.isEmpty(customerRemark)) {
            throw new EmptyRemarkException();
        }

        Domain domain = domainSearchService.getDomain(domainName);
        //TODO nameserver change is redundant in modification ticket
        nameservers = nameservers == null ? domain.getNameservers() : nameservers;

        Date crDate = new Date();
        return prepareTicket(user, DomainOperationType.MOD, domain.getRenewalDate(), domain.getName(),
                domainHolder, domain.getHolderClass().getId(), domain.getHolderCategory().getId(),
                domain.getHolderSubcategory(), domain.getResellerAccount().getId(), adminContacts, techContacts,
                ContactUtils.contactsAsStrings(domain.getBillingContacts()), nameservers, customerRemark,
                AdminStatus.NEW, null, null, false, crDate);
    }

    private Ticket prepareTicket(AuthenticatedUser user, DomainOperationType operationType, Date renewalDate,
            String domainName, String domainHolder, Long domainClassId, Long domainCategoryId,
            /*>>>@Nullable*/ EntitySubcategory domainSubcategory, long resellerAccountId,
            List</*>>>? extends @Nullable*/String> adminContacts, List<String> techContacts,
            List<String> billingContacts, List<Nameserver> nameservers, String customerRemark,
            AdminStatus adminStatus, /*>>>@Nullable*/ Period period, /*>>>@Nullable*/ String charityCode,
            boolean autorenewMode, Date crDate) throws ContactSyntaxException, NicHandleNotFoundException,
            HolderClassNotExistException, HolderCategoryNotExistException {
        EntityClass domainClass = entityService.getClass(domainClassId);
        EntityCategory domainCategory = entityService.getCategory(domainCategoryId);
        List<SimpleDomainFieldChange<Contact>> adminList = asDomainFieldChangeContactList(adminContacts);
        List<SimpleDomainFieldChange<Contact>> techList = asDomainFieldChangeContactList(techContacts);
        List<SimpleDomainFieldChange<Contact>> billingList = asDomainFieldChangeContactList(billingContacts);
        List<NameserverChange> nsList = TicketConverter.asNameserverChangeList(nameservers);
        DomainOperation domainOp = TicketConverter.asDomainOperation(operationType, renewalDate, domainName,
                domainHolder, domainClass, domainCategory, domainSubcategory, resellerAccountId,
                adminList, techList, billingList, nsList);

        Contact creator = getNewContact(user.getUsername());
        assert creator != null : "@AssumeAssertion(nullness)";
        return new Ticket(domainOp, crDate, creator, customerRemark, adminStatus, period, charityCode, autorenewMode);
    }

    private void completeTransferRequest(TicketRequest request, Domain domain, OpInfo opInfo, Long accountId)
            throws NicHandleNotFoundException, NicHandleRecreateException {
        request.setDomainHolder(domain.getHolder());
        if (Validator.isEmpty(request.getAdminContactNicHandles()) ||
                Validator.isEmpty(request.getAdminContactNicHandles().get(0))) {
            updateAdminContactsFromDomain(request, domain);
            recreateNicHandles(request, opInfo, accountId);
        }
        if (Validator.isEmpty(request.getNameservers())) {
            updateNsFromDomain(request, domain);
        }
    }

    private void updateNsFromDomain(TicketRequest request, Domain domain) {
        request.setNameservers(domain.getNameservers());
    }

    private void updateAdminContactsFromDomain(TicketRequest request, Domain domain) {
        List<String> adminContacts = new ArrayList<>();
        for (Contact c : domain.getAdminContacts()) {
            adminContacts.add(c.getNicHandle());
        }
        request.setAdminContactNicHandles(adminContacts);
    }

    private void recreateNicHandles(TicketRequest request, OpInfo opInfo, Long accountId)
            throws NicHandleNotFoundException, NicHandleRecreateException {
        List<String> newContacts = new ArrayList<>();
        for (String adminNH : request.getAdminContactNicHandles()) {
            if (adminNH != null) {
                NicHandle oldNH = nicHandleSearchService.getNicHandle(adminNH);
                String remark =
                        "Created during API domain transfer request. Duplicated from " + adminNH + " - API.";
                OpInfo opInfoWithRemark = OpInfo.withModifiedRemark(opInfo, remark);
                try {
                    NewNicHandle newNicHandle = new NewNicHandle(oldNH.getName(), oldNH.getCompanyName(),
                            oldNH.getEmail(), oldNH.getAddress(), oldNH.getCountry().getId(), oldNH.getCounty().getId(),
                            oldNH.getPhones(), oldNH.getFaxes(), null, null);
                    NicHandle recreatedNH = servicesRegistry.getNicHandleService().createNicHandle(accountId,
                            newNicHandle, opInfoWithRemark, true);
                    newContacts.add(recreatedNH.getNicHandleId());
                } catch (NicHandleEmailException e) {
                    // ignore
                } catch (Exception e) {
                    throw new NicHandleRecreateException(adminNH, e);
                }
            }
        }
        request.setAdminContactNicHandles(newContacts);
    }

    private long createTicket(AuthenticatedUser user, Ticket newTicket, OpInfo opInfo)
            throws ValidationException, DomainIsNotCharityException, DomainIsCharityException, TooManyTicketsException,
            DomainNotFoundException, NicHandleException, ClassDoesNotMatchCategoryException, EmptyRemarkException {
        boolean ticketToCancel = false;
        Validator.assertNotNull(newTicket, "new ticket");
        try {
            validateNewTicket(newTicket);
        } catch (DomainModificationPendingException e) {
            if (newTicket.getOperation().getType() == DomainOperationType.XFER) {
                ticketToCancel = true;
            } else {
                throw new DomainModificationPendingException(e.getDomainName());
            }
        }
        try {
            validateTicket(newTicket);
        } catch (CategoryDoesNotMatchSubcategoryException e) {
            LOGGER.error("Category should always match subcategory when creating a ticket", e);
            throw new IllegalStateException();
        }
        // Transfer ticket has a higher priority - an existing modification ticket gets cancelled
        if (ticketToCancel) {
            String domainName = newTicket.getOperation().getDomainNameField().getNewValue();
            assert domainName != null : "@AssumeAssertion(nullness)";
            cancelModificationTicket(user, domainName, new OpInfo(user, "Mod Ticket cancelled by a Transfer Ticket"));
        }
        long ticketId = ticketDAO.createTicket(newTicket);
        Ticket ticket = ticketDAO.get(ticketId);
        assert ticket != null : "@AssumeAssertion(nullness)";
        ticket.getOperation().setNameserversField(newTicket.getOperation().getNameserversField());
        long changeId = historicalTicketDAO.create(ticket, newTicket.getChangeDate(), opInfo.getActorName());
        ticketDAO.updateUsingHistory(changeId);
        return ticketId;
    }

    private void validateNewTicket(Ticket newTicket)
            throws ValidationException, TooManyTicketsException, DomainNotFoundException, DomainIsNotCharityException,
            DomainIsCharityException {
        ValidationHelper.validateDomainName(newTicket);
        String domainName = newTicket.getOperation().getDomainNameField().getNewValue();
        assert domainName != null : "@AssumeAssertion(nullness)";
        assert newTicket.getOperation().getResellerAccountField().getNewValue() != null : "@AssumeAssertion(nullness)";
        long accountId = newTicket.getOperation().getResellerAccountField().getNewValue().getId();
        Domain domain;
        switch (newTicket.getOperation().getType()) {
            case REG:
                validateNewDomainName(domainName);
                break;
            case MOD:
                domain = domainSearchService.getDomain(domainName);
                ValidationHelper.validateAccountId(accountId, domain);
                ticketSearchService.validateTicketPending(domainName);
                break;
            case XFER:
                domain = domainSearchService.getDomain(domainName);
                validateCharityTransfer(newTicket, domain);
                final Contact billingContact = newTicket.getOperation().getBillingContactsField().get(0).getNewValue();
                assert billingContact != null : "@AssumeAssertion(nullness)";
                validateNewTransferTicketResellers(domain, accountId, billingContact.getNicHandle());
                ticketSearchService.validateTicketPending(domainName);
                break;
            default:
                throw new IllegalArgumentException("Unhandled ticket type: " + newTicket.getOperation().getType());
        }
    }

    private void validateTicket(Ticket ticket)
            throws ValidationException, ClassDoesNotMatchCategoryException, NicHandleException,
            CategoryDoesNotMatchSubcategoryException {
        ValidationHelper.validateRequestersRemark(ticket);
        ValidationHelper.validateHolder(ticket);
        validateHolderEntities(ticket.getOperation());
        ValidationHelper.validateCharityCode(ticket);
        final String domainName = ticket.getOperation().getDomainNameField().getNewValue();
        assert domainName != null : "@AssumeAssertion(nullness)";
        NameserverValidator
                .checkNameserverChanges(domainName, ticket.getOperation().getNameserversField(), servicesRegistry);
        final Account accountNewValue = ticket.getOperation().getResellerAccountField().getNewValue();
        assert accountNewValue != null : "@AssumeAssertion(nullness)";
        long accountId = accountNewValue.getId();
        ContactValidator.checkTicketContacts(ticket, accountId, servicesRegistry);
    }

    @Override
    public void cancelModificationTicket(AuthenticatedUser user, String domainName, OpInfo opInfo)
            throws DomainNotFoundException, NicHandleException, EmptyRemarkException, TooManyTicketsException,
            DomainLockedException {
        Ticket ticket = ticketSearchService.getTicketForDomain(domainName);
        assert ticket != null : "@AssumeAssertion(nullness)";
        delete(ticket, opInfo);
        servicesRegistry.getDomainService().modifyRemark(user, domainName, opInfo.getRemark());
    }

    private void validateNewDomainName(String domainName)
            throws DomainNameExistsOrPendingException, DomainNameSyntaxException, TooManyTicketsException {
        try {
            DomainNameValidator.validateIedrName(domainName);
        } catch (InvalidDomainNameException e) {
            throw new DomainNameSyntaxException(domainName);
        }

        // validate if domain or tickets for domain already exists
        if (domainSearchService.exists(domainName) || ticketSearchService.getTicketForDomain(domainName) != null) {
            throw new DomainNameExistsOrPendingException(domainName);
        }
    }

    private void validateCharityTransfer(Ticket ticket, Domain domainToTransfer)
            throws DomainIsNotCharityException, DomainIsCharityException {
        if (ticket.isCharity()) {
            if (!domainToTransfer.getDsmState().getDomainHolderType().equals(DomainHolderType.Charity)) {
                throw new DomainIsNotCharityException(domainToTransfer.getName());
            }
        } else {
            if (domainToTransfer.getDsmState().getDomainHolderType().equals(DomainHolderType.Charity)) {
                throw new DomainIsCharityException(domainToTransfer.getName());
            }
        }
    }

    private void validateNewTransferTicketResellers(Domain domain, long accountId, String newBillingNH)
            throws DomainAlreadyManagedByResellerException {
        if (isDirectToDirectTransfer(accountId, domain)) {
            String oldBillingNH = domain.getBillingContacts().get(0).getNicHandle();
            if (oldBillingNH.equals(newBillingNH))
                throw new DomainAlreadyManagedByResellerException(domain.getName());
        } else {
            if (accountId == domain.getResellerAccount().getId())
                throw new DomainAlreadyManagedByResellerException(domain.getName());
        }
    }

    private boolean isDirectToDirectTransfer(long accountId, Domain domain) {
        long domainAccount = domain.getResellerAccount().getId();
        return servicesRegistry.getNicHandleSearchService().isAccountDirect(accountId) &&
                servicesRegistry.getNicHandleSearchService().isAccountDirect(domainAccount);
    }

    private void validateHolderEntities(DomainOperation domainOperation)
            throws ClassDoesNotMatchCategoryException, CategoryDoesNotMatchSubcategoryException {
        final EntityClass holderClass = domainOperation.getDomainHolderClassField().getNewValue();
        final EntityCategory holderCategory = domainOperation.getDomainHolderCategoryField().getNewValue();
        final EntitySubcategory holderSubcategory =
                domainOperation.getDomainHolderSubcategoryField().getNewValue();
        assert holderClass != null : "@AssumeAssertion(nullness)";
        assert holderCategory != null : "@AssumeAssertion(nullness)";
        entityService.validateHolderEntities(holderClass, holderCategory, holderSubcategory);
    }

    @Override
    public void delete(Ticket ticket, OpInfo opInfo) {
        ticketDAO.deleteById(ticket.getId());
    }

    @Override
    public void deleteAndNotify(AuthenticatedUser user, Ticket ticket, OpInfo opInfo) throws TicketEmailException {
        delete(ticket, opInfo);
        sendTicketRemovedEmail(user, ticket);
    }

    private void sendTicketRemovedEmail(AuthenticatedUser user, Ticket ticket) {
        EmailParameters parameters = new TicketEmailParameters(user.getUsername(), ticket);
        sendEmail(EmailTemplateNamesEnum.TICKET_CLEANUP, parameters);

        if (ticket.isCanceled()) {
            return;
        }

        DomainOperationType type = ticket.getOperation().getType();
        if (type == DomainOperationType.XFER) {
            // TRANSFER email has different semantics of BILL_C (it's losing one) than all other mails
            parameters = new TransferTicketEmailParameters(user.getUsername(), ticket);
            sendEmail(EmailTemplateNamesEnum.TRANSFER_REQUEST_EXPIRED, parameters);
        } else if (type == DomainOperationType.MOD) {
            sendEmail(EmailTemplateNamesEnum.MOD_REQUEST_EXPIRED, parameters);
        } else if (type == DomainOperationType.REG) {
            sendEmail(EmailTemplateNamesEnum.REG_REQUEST_EXPIRED, parameters);
        }
    }

    private void sendEmail(EmailTemplateNamesEnum template, EmailParameters parameters) {
        try {
            servicesRegistry.getEmailTemplateSender().sendEmail(template.getId(), parameters);
        } catch (Exception e) {
            LOGGER.error("Error sending email id=" + template.getId(), e);
        }
    }

    @Override
    public void updateFinancialStatus(long ticketId, FinancialStatus newFinancialStatus, OpInfo opInfo)
            throws TicketNotFoundException {
        updateStatuses(ticketId, null, null, newFinancialStatus, opInfo);
    }

    @Override
    public void updateTechStatus(long ticketId, TechStatus newTechStatus, OpInfo opInfo)
            throws TicketNotFoundException {
        updateStatuses(ticketId, null, newTechStatus, null, opInfo);
    }

    @Override
    public void updateAdminStatus(long ticketId, AdminStatus newAdminStatus, OpInfo opInfo)
            throws TicketNotFoundException {
        updateStatuses(ticketId, newAdminStatus, null, null, opInfo);
    }

    @Override
    public void updateCustomerStatus(long ticketId, CustomerStatus newCustomerStatus, OpInfo opInfo)
            throws TicketNotFoundException {
        Ticket ticket = lockTicket(ticketId);

        ticket.setCustomerStatus(newCustomerStatus);
        ticket.setRequestersRemark("Customer status changed");
        updateTicketAndHistory(ticket, opInfo);
    }

    private void updateStatuses(long ticketId,
            /*>>>@Nullable*/ AdminStatus as,
            /*>>>@Nullable*/ TechStatus ts,
            /*>>>@Nullable*/ FinancialStatus fs,
            OpInfo opInfo) throws TicketNotFoundException {
        Ticket ticket = lockTicket(ticketId);
        if (as != null) {
            ticket.updateStatus(as, opInfo.getRemark() == null ? "Admin status changed" : opInfo.getRemark());
        }

        if (ts != null) {
            ticket.updateStatus(ts, opInfo.getRemark() == null ? "Tech status changed" : opInfo.getRemark());
        }

        if (fs != null) {
            ticket.updateStatus(fs, opInfo.getRemark() == null ? "Financial status changed" : opInfo.getRemark());
        }

        updateTicketAndHistory(ticket, opInfo);
    }

    @Override
    public void sendTicketExpirationEmail(AuthenticatedUser user, Ticket ticket, int daysRemaining, int daysPassed,
            int ticketNotificationPeriod) {
        TicketNotification notification = ticketDAO.getTicketNotification(ticket.getId(), ticketNotificationPeriod);
        if (notification == null) {
            EmailTemplateNamesEnum template = getExpirationNotificationTemplate(ticket);
            if (template == null) {
                LOGGER.warn("Invalid ticket type to send expiration email: " + ticket.getOperation().getType());
            } else {
                EmailParameters parameters = getExpirationNotificationParameters(user, ticket, daysRemaining,
                        daysPassed);
                sendEmail(template, parameters);
                notification = new TicketNotification(ticket.getId(), ticketNotificationPeriod);
                ticketDAO.createTicketNotification(notification);
            }
        } else {
            LOGGER.info("Notification: " + notification.toString() + " already sent.");
        }
    }

    public ServicesRegistry getServicesRegistry() {
        return servicesRegistry;
    }

    public void setServicesRegistry(ServicesRegistry servicesRegistry) {
        this.servicesRegistry = servicesRegistry;
    }

    private /*>>>@Nullable*/ EmailTemplateNamesEnum getExpirationNotificationTemplate(Ticket ticket) {
        switch (ticket.getOperation().getType()) {
            case REG:
                return EmailTemplateNamesEnum.REG_TICKET_EXPIRATION;
            case XFER:
                return EmailTemplateNamesEnum.XFER_TICKET_EXPIRATION;
            case MOD:
                return EmailTemplateNamesEnum.MOD_TICKET_EXPIRATION;
            default:
                return null;
        }
    }

    private EmailParameters getExpirationNotificationParameters(AuthenticatedUser user, Ticket ticket,
            int daysRemaining, int daysPassed) {
        if (ticket.getOperation().getType() == DomainOperationType.XFER) {
            return new TransferTicketEmailParameters(user.getUsername(), ticket, daysRemaining, daysPassed);
        } else {
            return new TicketEmailParameters(user.getUsername(), ticket, daysRemaining, daysPassed);
        }
    }

}
