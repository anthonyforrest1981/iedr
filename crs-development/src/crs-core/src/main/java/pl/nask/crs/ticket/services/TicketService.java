package pl.nask.crs.ticket.services;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.List;

import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.tickets.exceptions.DomainIsCharityException;
import pl.nask.crs.app.tickets.exceptions.DomainIsNotCharityException;
import pl.nask.crs.app.tickets.exceptions.NicHandleRecreateException;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.domains.exceptions.DomainLockedException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.entities.exceptions.*;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.ticket.*;
import pl.nask.crs.ticket.exceptions.*;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.TicketEdit;

/**
 * @author Patrycja Wegrzynowicz
 */
public interface TicketService {

    Ticket lockTicket(long id) throws TicketNotFoundException;

    void checkOut(long ticketId, OpInfo opInfo)
            throws TicketNotFoundException, TicketAlreadyCheckedOutException;

    void checkIn(long ticketId, AdminStatus status, OpInfo opInfo)
            throws TicketNotFoundException, TicketNotCheckedOutException, TicketCheckedOutToOtherException;

    void alterStatus(AuthenticatedUser user, long ticketId, AdminStatus status, OpInfo opInfo)
            throws TicketNotFoundException, TicketCheckedOutToOtherException, TicketEmailException,
            TicketNotCheckedOutException, CategoryDoesNotMatchSubcategoryException, ClassDoesNotMatchCategoryException;

    void reassign(long ticketId, String newHostmasterHandle, OpInfo opInfo)
            throws TicketNotFoundException, TicketNotCheckedOutException, TicketUserCannotCheckOutException;

    void save(long ticketId, DomainOperation domainOperation, OpInfo opInfo)
            throws TicketNotFoundException, EmptyRemarkException, TicketCheckedOutToOtherException,
            TicketNotCheckedOutException, ClassDoesNotMatchCategoryException, CategoryDoesNotMatchSubcategoryException;

    void accept(AuthenticatedUser user, long ticketId, DomainOperation domainOperation, OpInfo opInfo)
            throws TicketNotFoundException, TicketEmailException, TicketCheckedOutToOtherException,
            TicketNotCheckedOutException, ClassDoesNotMatchCategoryException, CategoryDoesNotMatchSubcategoryException;

    void reject(AuthenticatedUser user, long ticketId, AdminStatus status, DomainOperation domainOperation,
            OpInfo opInfo)
            throws TicketNotFoundException, InvalidStatusException, EmptyRemarkException, TicketEmailException,
            TicketCheckedOutToOtherException, TicketNotCheckedOutException, ClassDoesNotMatchCategoryException,
            CategoryDoesNotMatchSubcategoryException;

    /**
     * Update performed by internal IEDR users, mostly to progress ticket through triple pass (changing statuses) or
     * filling in why ticket won't be accepted. Ticket must be checked out to currently logged in user in order to
     * perform that update.
     * @param ticketId id of ticket to update
     * @param ticketEdit new values for domain operation (including failure reasons)
     * @param requestersRemark requester's remark
     * @param opInfo operation info
     * @param clikPaid new value for clikPaid flag
     * @param forceUpdate force update to the ticket even if reseller's account does not have Ticket_Edit flag ON
     */
    void updateAsAdmin(long ticketId, TicketEdit ticketEdit, /*>>>@Nullable*/ String requestersRemark, OpInfo opInfo,
            boolean clikPaid, boolean forceUpdate)
            throws ValidationException, TicketEditFlagException, EmptyRemarkException, NicHandleException,
            AccountNotFoundException, HolderClassNotExistException, HolderCategoryNotExistException,
            ClassDoesNotMatchCategoryException, TicketNotFoundException, TicketCheckedOutToOtherException,
            TicketNotCheckedOutException, CategoryDoesNotMatchSubcategoryException, HolderSubcategoryNotExistException;

    /**
     * Update performed by the owner of the ticket, i.e. the owner is editing the ticket, must be not checked out by
     * IEDR's internal user, cannot explicitly change internal ticket's status (admin, tech, etc.), but will update
     * the status resetting it back to starting values if appropriate fields are changed (e.g. nameservers cause techStatus
     * resetted back to NEW).
     * @param ticketId Id of the ticket to be edited
     * @param ticketEdit domain operation holding new values
     * @param requestersRemark requester's remarks
     * @param opInfo operation info
     * @param clikPaid new value for clikPaid flag
     * @param documentsWereSent editing ticket resets admin status. If documents were sent, it gets resetted to
     *                          DOCUMENTS_SUBMITTED, otherwise it will be RENEW
     */
    void updateAsOwner(long ticketId, TicketEdit ticketEdit, String requestersRemark, OpInfo opInfo,
            boolean clikPaid, boolean documentsWereSent)
            throws ValidationException, TicketEditFlagException, EmptyRemarkException, NicHandleException,
            AccountNotFoundException, TicketNotFoundException, TicketAlreadyCheckedOutException,
            TicketHolderChangeException, DomainNotFoundException;

    long createRegistrationTicket(AuthenticatedUser user, TicketRequest request, Long accountId)
            throws AccessDeniedException, HolderClassNotExistException, HolderCategoryNotExistException,
            ClassDoesNotMatchCategoryException, NicHandleException, DomainNotFoundException, ValidationException,
            DomainIsNotCharityException, DomainIsCharityException, EmptyRemarkException, TooManyTicketsException,
            OwnerTypeNotExistException;

    long createTransferTicket(AuthenticatedUser user, TicketRequest request, Long accountId)
            throws AccessDeniedException, NicHandleException, DomainNotFoundException, ValidationException,
            NicHandleRecreateException, DomainIsNotCharityException, DomainIsCharityException, EmptyRemarkException,
            TooManyTicketsException;

    long createModificationTicket(AuthenticatedUser user, String domainName, String domainHolder,
            List<String> adminContacts, List<String> techContacts, List<Nameserver> nameservers, String customerRemark)
            throws UserNotAuthenticatedException, DomainNotFoundException, EmptyRemarkException, ValidationException,
            NicHandleException, DomainIsNotCharityException, DomainIsCharityException, TooManyTicketsException;

    void delete(Ticket ticket, OpInfo opInfo);

    void deleteAndNotify(AuthenticatedUser user, Ticket ticket, OpInfo opInfo) throws TicketEmailException;

    void updateFinancialStatus(long ticketId, FinancialStatus newFinancialStatus, OpInfo opInfo)
            throws TicketNotFoundException;

    void updateTechStatus(long ticketId, TechStatus newTechStatus, OpInfo opInfo)
            throws TicketNotFoundException;

    void updateAdminStatus(long ticketId, AdminStatus newAdminStatus, OpInfo opInfo) throws TicketNotFoundException;

    void updateCustomerStatus(long ticketId, CustomerStatus newCustomerStatus, OpInfo opInfo)
            throws TicketNotFoundException;

    void cancelModificationTicket(AuthenticatedUser user, String domainName, OpInfo opInfo)
            throws DomainNotFoundException, NicHandleException, EmptyRemarkException, TooManyTicketsException,
            DomainLockedException;

    void sendEmail(AuthenticatedUser user, Ticket ticket, TicketEmailType emailType)
            throws TicketEmailException;

    void sendTicketExpirationEmail(AuthenticatedUser user, Ticket ticket, int daysRemaining, int daysPassed,
            int ticketNotificationPeriod);

}
