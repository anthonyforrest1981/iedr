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
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.email.service.TemplateInstantiatingException;
import pl.nask.crs.commons.email.service.TemplateNotFoundException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.dsm.DomainStateMachine;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.domains.dsm.events.TransferRequest;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.exceptions.DepositNotFoundException;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;

public class TransferDomain {

    protected final AppServicesRegistry appRegistry;
    protected final ServicesRegistry registry;
    protected final DomainStateMachine dsm;
    protected final AuthenticatedUser user;
    protected final TicketRequest request;

    public TransferDomain(AppServicesRegistry appRegistry, ServicesRegistry registry, DomainStateMachine dsm,
            AuthenticatedUser user, TicketRequest request) {
        this.appRegistry = appRegistry;
        this.registry = registry;
        this.dsm = dsm;
        this.user = user;
        this.request = request;
    }

    public long run() throws TicketNotFoundException, NicHandleException, NotAdmissiblePeriodException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException,
            DomainIsNotCharityException, DomainIsCharityException, InvalidAuthCodeException, IllegalArgumentException,
            TemplateNotFoundException, TemplateInstantiatingException, EmailSendingException, AccessDeniedException,
            EmptyRemarkException, DomainIllegalStateException, TooManyTicketsException, ExportException,
            CardPaymentException, DepositNotFoundException {
        checkAuthCode();
        if (isDirect()) {
            throw new DepositNotFoundException("Deposit payment not possible for Direct account");
        }
        checkRenewalPeriod();
        long ticketId = createTransferTicketAndLockDomain();
        runTransferEvent(user, registry.getTicketSearchService().getTicket(ticketId));
        exportNh();
        return ticketId;
    }

    private boolean isDirect() throws NicHandleNotFoundException {
        return registry.getNicHandleSearchService().isNicHandleDirect(user.getUsername());
    }

    protected long createTransferTicketAndLockDomain()
            throws NicHandleException, AccessDeniedException, NotAdmissiblePeriodException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException,
            DomainIsNotCharityException, DomainIsCharityException, EmptyRemarkException, TooManyTicketsException {
        dsm.validateEvent(request.getDomainName(), DsmEventName.TransferRequest);
        NicHandle creatorNh = registry.getNicHandleSearchService().getNicHandle(user.getUsername());
        return registry.getTicketService().createTransferTicket(user, request, creatorNh.getAccount().getId());
    }

    protected void runTransferEvent(AuthenticatedUser user, Ticket t)
            throws DomainNotFoundException, DomainIllegalStateException {
        dsm.handleEvent(user, request.getDomainName(), new TransferRequest(t), new OpInfo(user));
    }

    protected void checkRenewalPeriod() throws NotAdmissiblePeriodException, NicHandleNotFoundException {
        int periodInYears = request.getRegPeriod().getYears();
        registry.getPaymentSearchService().getProductPrice(Period.fromYears(periodInYears), OperationType.TRANSFER,
                user.getUsername());
    }

    protected void checkAuthCode()
            throws InvalidAuthCodeException, IllegalArgumentException, TemplateNotFoundException,
            TemplateInstantiatingException, EmailSendingException {
        registry.getDomainService().verifyAuthCode(user, request.getDomainName(), request.getAuthCode());
    }

    protected void exportNh() throws NicHandleNotFoundException, ExportException {
        OpInfo opInfo = new OpInfo(user, "NicHandle was exported due to domain transfer");
        registry.getNicHandleService().triggerExport(user.getUsername(), opInfo);
    }

}
