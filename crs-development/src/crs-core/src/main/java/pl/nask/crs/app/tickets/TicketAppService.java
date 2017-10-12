package pl.nask.crs.app.tickets;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.List;

import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.exceptions.*;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.TicketNotification;
import pl.nask.crs.ticket.exceptions.*;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.TicketEdit;
import pl.nask.crs.ticket.search.TicketSearchCriteria;

public interface TicketAppService extends AppSearchService<Ticket, TicketSearchCriteria> {

    // Redeclared to allow doing a permission check.
    @Override
    LimitedSearchResult<Ticket> search(AuthenticatedUser user, TicketSearchCriteria criteria, long offset, long limit,
            /*>>>@Nullable*/ List<SortCriterion> orderBy) throws AccessDeniedException;

    LimitedSearchResult<Ticket> findOwn(AuthenticatedUser user, TicketSearchCriteria criteria, long offset, long limit,
            /*>>>@Nullable*/ List<SortCriterion> orderBy) throws AccessDeniedException, NicHandleNotFoundException;

    TicketModel view(AuthenticatedUser user, long ticketId) throws AccessDeniedException, TicketNotFoundException;

    TicketModel history(AuthenticatedUser user, long ticketId) throws AccessDeniedException, TicketNotFoundException;

    TicketModel revise(AuthenticatedUser user, long ticketId) throws AccessDeniedException, TicketNotFoundException;

    TicketModel edit(AuthenticatedUser user, long ticketId) throws AccessDeniedException, TicketNotFoundException;

    void checkOut(AuthenticatedUser user, long ticketId)
            throws AccessDeniedException, TicketNotFoundException, TicketAlreadyCheckedOutException;

    void checkIn(AuthenticatedUser user, long ticketId, AdminStatus status)
            throws AccessDeniedException, TicketNotFoundException, TicketNotCheckedOutException,
            TicketCheckedOutToOtherException;

    void alterStatus(AuthenticatedUser user, long ticketId, AdminStatus status)
            throws AccessDeniedException, TicketNotFoundException, TicketCheckedOutToOtherException,
            TicketEmailException, TicketNotCheckedOutException, ClassDoesNotMatchCategoryException,
            CategoryDoesNotMatchSubcategoryException;

    void reassign(AuthenticatedUser user, long ticketId, String newHostmasterHandle)
            throws AccessDeniedException, TicketNotFoundException, TicketNotCheckedOutException,
            TicketUserCannotCheckOutException;

    void save(AuthenticatedUser user, long ticketId, DomainOperation failureReasons, String hostmastersRemark)
            throws AccessDeniedException, TicketNotFoundException, EmptyRemarkException,
            TicketCheckedOutToOtherException, TicketNotCheckedOutException, CategoryDoesNotMatchSubcategoryException,
            ClassDoesNotMatchCategoryException;

    void accept(AuthenticatedUser user, long ticketId, DomainOperation domainOperation, String hostmastersRemark)
            throws AccessDeniedException, TicketNotFoundException, TicketEmailException,
            TicketCheckedOutToOtherException, TicketNotCheckedOutException, CategoryDoesNotMatchSubcategoryException,
            ClassDoesNotMatchCategoryException;

    void reject(AuthenticatedUser user, long ticketId, AdminStatus status, DomainOperation domainOperation,
            String hostmastersRemark)
            throws AccessDeniedException, TicketNotFoundException, InvalidStatusException, EmptyRemarkException,
            TicketEmailException, TicketCheckedOutToOtherException, TicketNotCheckedOutException,
            CategoryDoesNotMatchSubcategoryException, ClassDoesNotMatchCategoryException;

    void updateAsAdmin(AuthenticatedUser user, long ticketId, TicketEdit ticketEdit, /*>>>@Nullable*/
            String requestersRemark, String hostmastersRemark, boolean clikPaid, boolean forceUpdate)
            throws AccessDeniedException, AccessDeniedException, ValidationException, TicketEditFlagException,
            EmptyRemarkException, NicHandleException, AccountNotFoundException, HolderClassNotExistException,
            HolderCategoryNotExistException, ClassDoesNotMatchCategoryException, TicketNotFoundException,
            TicketCheckedOutToOtherException, TicketNotCheckedOutException, CategoryDoesNotMatchSubcategoryException,
            HolderSubcategoryNotExistException;

    void updateAsOwner(AuthenticatedUser user, long ticketId, TicketEdit ticketEdit, String requestersRemark,
            String hostmastersRemark, boolean clikPaid, boolean documentsWereSent)
            throws AccessDeniedException, ValidationException, TicketEditFlagException, EmptyRemarkException,
            NicHandleException, AccountNotFoundException, TicketNotFoundException, TicketAlreadyCheckedOutException,
            TicketHolderChangeException, DomainNotFoundException;

    /**
     * Gets one ticket for given domain or <code>NULL</code> if there are no tickets about given domain
     * @param user logged in user, used for authorization
     * @param domainName name of domain of searched ticket
     *
     * @return null if no ticket if found, full ticket (including dns data) if ticket exists
     * @throws pl.nask.crs.app.tickets.exceptions.TooManyTicketsException if there is more than one ticket
     *     for given domain
     */
    /*>>>@Nullable*/ Ticket getTicketForDomain(AuthenticatedUser user, String domainName)
            throws AccessDeniedException, TooManyTicketsException;

    List<TicketNotification> findTicketNotifications(AuthenticatedUser user) throws AccessDeniedException;

    void sendTicketExpirationEmail(AuthenticatedUser user, TicketNotification ticketNotification)
            throws AccessDeniedException, TicketNotFoundException;

    List<Ticket> findTicketsToCleanup(AuthenticatedUser user) throws AccessDeniedException;
}
