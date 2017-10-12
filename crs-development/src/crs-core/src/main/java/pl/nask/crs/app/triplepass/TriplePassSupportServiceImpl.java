package pl.nask.crs.app.triplepass;

import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.triplepass.exceptions.FinancialCheckException;
import pl.nask.crs.app.triplepass.exceptions.TechnicalCheckException;
import pl.nask.crs.app.triplepass.exceptions.TicketIllegalStateException;
import pl.nask.crs.app.triplepass.exceptions.TriplePassException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.DomainLockedException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.entities.exceptions.CategoryDoesNotMatchSubcategoryException;
import pl.nask.crs.entities.exceptions.ClassDoesNotMatchCategoryException;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleIncorrectForAccountException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.services.TicketSearchService;

import static pl.nask.crs.app.triplepass.TriplePassHelper.*;

public class TriplePassSupportServiceImpl implements TriplePassSupportService {
    private static final Logger LOG = Logger.getLogger(TriplePassSupportServiceImpl.class);

    private TriplePassAppService triplePassAppService;
    private TicketSearchService ticketSearchService;

    public TriplePassSupportServiceImpl(TriplePassAppService triplePassAppService,
            TicketSearchService ticketSearchService) {
        this.triplePassAppService = triplePassAppService;
        this.ticketSearchService = ticketSearchService;
    }

    @Override
    public void triplePass(AuthenticatedUser user) throws AccessDeniedException {
        List<Ticket> tickets = triplePassAppService.getAdminPassedTickets(user);
        for (Ticket ticket : tickets) {
            String domainName = ticket.getOperation().getDomainNameField().getNewValue();
            try {
                LOG.info("Performing triple pass for domain: " + domainName);
                performTriplePass(user, ticket);
            } catch (Exception e) {
                LOG.warn(getMessageForException(e, ticket), e);
            }
        }
    }

    @Override
    public void triplePass(AuthenticatedUser user, long ticketId)
            throws AccessDeniedException, TriplePassException, TicketNotFoundException, TicketIllegalStateException,
            DomainNotFoundException, NicHandleException, EmptyRemarkException, DomainIllegalStateException,
            ValidationException, ClassDoesNotMatchCategoryException, FinancialCheckException, TechnicalCheckException,
            HostNotConfiguredException, TransactionNotFoundException, CardPaymentException,
            CategoryDoesNotMatchSubcategoryException {
        Ticket ticket = ticketSearchService.getTicket(ticketId);
        performTriplePass(user, ticket);
    }

    private void performTriplePass(AuthenticatedUser user, Ticket ticket)
            throws AccessDeniedException, TriplePassException, TicketNotFoundException, TicketIllegalStateException,
            DomainNotFoundException, NicHandleException, EmptyRemarkException, DomainIllegalStateException,
            ValidationException, ClassDoesNotMatchCategoryException, FinancialCheckException, TechnicalCheckException,
            HostNotConfiguredException, TransactionNotFoundException, CardPaymentException,
            CategoryDoesNotMatchSubcategoryException {
        if (!isRegistrationTicket(ticket) && !isTransferTicket(ticket) && !isModificationTicket(ticket)) {
            throw new TriplePassException("Not a registration nor transfer nor modification ticket " + info(ticket));
        }
        if (isAdminCancelled(ticket)) {
            LOG.info("Ticket admin cancelled " + info(ticket));
            triplePassAppService.performTicketCancellation(user, ticket.getId());
            return;
        }

        if (needsDnsCheck(ticket)) {
            triplePassAppService.performTechnicalCheck(user, ticket.getId(), false);
            ticket = ticketSearchService.getTicket(ticket.getId());
        }

        if (needsFinancialCheck(ticket)) {
            triplePassAppService.performFinancialCheck(user, ticket.getId());
            ticket = ticketSearchService.getTicket(ticket.getId());
        }

        if (isFullPassed(ticket)) {
            performTicketPromotion(user, ticket);
        }
    }

    private void performTicketPromotion(AuthenticatedUser user, Ticket ticket)
            throws AccessDeniedException, TicketNotFoundException, TicketIllegalStateException, DomainNotFoundException,
            NicHandleException, EmptyRemarkException, DomainIllegalStateException, ValidationException,
            ClassDoesNotMatchCategoryException, CategoryDoesNotMatchSubcategoryException {
        if (isRegistrationTicket(ticket)) {
            triplePassAppService.promoteTicketToDomain(user, ticket.getId());
        } else if (isTransferTicket(ticket)) {
            triplePassAppService.promoteTransferTicket(user, ticket.getId());
        } else if (isModificationTicket(ticket)) {
            triplePassAppService.promoteModificationTicket(user, ticket.getId());
        }
    }

    private String info(Ticket t) {
        return String.format("ID: %s, domain: %s, type: %s, admin: %s, tech: %s, financial: %s", t.getId(), t
                .getOperation().getDomainNameField().getNewValue(), t.getOperation().getType(), t.getAdminStatus()
                .getDescription(), t.getTechStatus().getDescription(), t.getFinancialStatus().getDescription());
    }

    private String getMessageForException(Exception exception, Ticket t) {
        try {
            throw exception;
        } catch (DomainIllegalStateException e) {
            return "Domain in illegal state for this operation " + info(t);
        } catch (DomainNotFoundException e) {
            return "Domain not found for the ticket " + info(t);
        } catch (TransactionNotFoundException e) {
            return "Transaction not found for ticket " + info(t);
        } catch (CardPaymentException e) {
            return "Exception during cancellation of realex cc pre-authorisation  " + info(t);
        } catch (FinancialCheckException e) {
            return "Financial pass failed, " + info(t);
        } catch (TicketIllegalStateException e) {
            return "Illegal state of the ticket " + info(t) + ", got " + info(e.getTicket());
        } catch (TechnicalCheckException e) {
            return "Technical pass error, " + info(t) + " Reason: " + e.getMessage();
        } catch (HostNotConfiguredException e) {
            return "DNS Check error, " + info(t) + " Reason: " + e.getMessage();
        } catch (DomainLockedException e) {
            return "Domain is locked for ticket: " + info(t);
        } catch (NicHandleNotFoundException e) {
            return "Nichandle not found: " + e.getNicHandleId() + " for ticket: " + info(t);
        } catch (NicHandleNotActiveException e) {
            return "Nichandle not active: " + e.getNicHandleId() + " for ticket: " + info(t);
        } catch (NicHandleIncorrectForAccountException e) {
            return "Nichandle: " + e.getNicHandleId() + " is incorrect for account: " + e.getAccountId()
                    + " for ticket: " + info(t);
        } catch (EmptyRemarkException e) {
            return "Empty remark for ticket: " + info(t);
        } catch (NicHandleException | ValidationException | ClassDoesNotMatchCategoryException e) {
            return "Domain validation failed: " + info(t);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
