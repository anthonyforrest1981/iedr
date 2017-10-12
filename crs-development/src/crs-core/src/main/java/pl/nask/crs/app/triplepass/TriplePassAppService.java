package pl.nask.crs.app.triplepass;

import java.util.List;

import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.triplepass.exceptions.FinancialCheckException;
import pl.nask.crs.app.triplepass.exceptions.TechnicalCheckException;
import pl.nask.crs.app.triplepass.exceptions.TicketIllegalStateException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.entities.exceptions.CategoryDoesNotMatchSubcategoryException;
import pl.nask.crs.entities.exceptions.ClassDoesNotMatchCategoryException;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface TriplePassAppService {

    List<Ticket> getAdminPassedTickets(AuthenticatedUser user) throws AccessDeniedException;

    void performFinancialCheck(AuthenticatedUser user, long ticketId)
            throws AccessDeniedException, FinancialCheckException, TicketIllegalStateException, TicketNotFoundException;

    /**
     * promotes the registration ticket to a domain object
     * @param user logged in user of the application performing the operation
     * @param ticketId ID of the registration ticket to be promoted to a domain object. Only fully passed registration tickets may be promoted
     * @return the name of the created domain
     * @throws TicketIllegalStateException if the ticket is not a registration ticket or it's not fully passed.
     * @throws TicketNotFoundException if there is no ticket with such id
     */

    String promoteTicketToDomain(AuthenticatedUser user, long ticketId)
            throws AccessDeniedException, TicketIllegalStateException, TicketNotFoundException,
            NicHandleNotFoundException;

    String promoteTransferTicket(AuthenticatedUser user, long ticketId)
            throws AccessDeniedException, TicketIllegalStateException, TicketNotFoundException, DomainNotFoundException,
            NicHandleException, EmptyRemarkException, DomainIllegalStateException, ValidationException,
            ClassDoesNotMatchCategoryException, CategoryDoesNotMatchSubcategoryException;

    String promoteModificationTicket(AuthenticatedUser user, long ticketId)
            throws AccessDeniedException, TicketNotFoundException, TicketIllegalStateException, DomainNotFoundException,
            NicHandleException, ValidationException, EmptyRemarkException, ClassDoesNotMatchCategoryException,
            CategoryDoesNotMatchSubcategoryException;

    void performTechnicalCheck(AuthenticatedUser user, long ticketId, boolean interactive)
            throws AccessDeniedException, TicketIllegalStateException, TicketNotFoundException,
            TechnicalCheckException, HostNotConfiguredException;

    void performTicketCancellation(AuthenticatedUser user, long ticketId)
            throws AccessDeniedException, TicketIllegalStateException, TicketNotFoundException,
            TransactionNotFoundException, CardPaymentException, DomainNotFoundException, DomainIllegalStateException;
}
