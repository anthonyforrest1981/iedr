package pl.nask.crs.app.commons.transfer;

import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.AppServicesRegistry;
import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.tickets.exceptions.DomainIsCharityException;
import pl.nask.crs.app.tickets.exceptions.DomainIsNotCharityException;
import pl.nask.crs.app.tickets.exceptions.NicHandleRecreateException;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.dsm.DomainStateMachine;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TransferDomainCharity extends TransferDomain {

    public TransferDomainCharity(AppServicesRegistry appRegistry, ServicesRegistry registry, DomainStateMachine dsm,
            AuthenticatedUser user, TicketRequest request) {
        super(appRegistry, registry, dsm, user, request);
    }

    @Override
    public long run() throws TicketNotFoundException, NicHandleException, NotAdmissiblePeriodException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException,
            DomainIsNotCharityException, DomainIsCharityException, InvalidAuthCodeException, IllegalArgumentException,
            TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException, AccessDeniedException,
            EmptyRemarkException, TooManyTicketsException, DomainIllegalStateException, ExportException {
        checkAuthCode();
        long ticketId = createTransferTicketAndLockDomain();
        runTransferEvent(user, registry.getTicketSearchService().getTicket(ticketId));
        exportNh();
        return ticketId;
    }

}
