package pl.nask.crs.app.commons.register;

import org.apache.log4j.Logger;

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
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.entities.exceptions.ClassDoesNotMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.entities.exceptions.OwnerTypeNotExistException;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.exceptions.DepositNotFoundException;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.services.impl.TicketEmailParameters;

public class RegisterDomain {
    protected final static Logger LOG = Logger.getLogger(RegisterDomain.class);

    protected final AppServicesRegistry appRegistry;
    protected final ServicesRegistry registry;
    protected final AuthenticatedUser user;
    protected final TicketRequest request;

    public RegisterDomain(AppServicesRegistry appRegistry, ServicesRegistry registry, AuthenticatedUser user,
            TicketRequest request) {
        this.appRegistry = appRegistry;
        this.registry = registry;
        this.user = user;
        this.request = request;
    }

    public long run() throws NicHandleException, AccessDeniedException, NotAdmissiblePeriodException,
            HolderClassNotExistException, HolderCategoryNotExistException, ClassDoesNotMatchCategoryException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException,
            DomainIsNotCharityException, CardPaymentException, DomainIsCharityException, EmptyRemarkException,
            TooManyTicketsException, ExportException, DepositNotFoundException, OwnerTypeNotExistException {
        if (isDirect()) {
            throw new DepositNotFoundException("Deposit payment not possible for Direct account");
        }
        long id = createRegistrationTicket();
        sendRegistrationApplicationEmail(user, id);
        exportNh();
        return id;
    }

    private boolean isDirect() throws NicHandleNotFoundException {
        return registry.getNicHandleSearchService().isNicHandleDirect(user.getUsername());
    }

    protected void sendRegistrationApplicationEmail(AuthenticatedUser user, long ticketId) {
        try {
            Ticket ticket = registry.getTicketSearchService().getTicket(ticketId);
            TicketEmailParameters parameters = new TicketEmailParameters(user.getUsername(), ticket);
            registry.getEmailTemplateSender().sendEmail(EmailTemplateNamesEnum.NREG_APPLICATION.getId(), parameters);
        } catch (Exception e) {
            LOG.warn("Error while sending new registration application email", e);
        }
    }

    public static void checkPeriodInYears(TicketRequest request) throws NotAdmissiblePeriodException {
        try {
            if (request.getRegPeriod().getYears() != 1)
                throw new NotAdmissiblePeriodException(request.getRegPeriod().getYears(), "Y");
        } catch (IllegalStateException e) {
            throw new NotAdmissiblePeriodException(request.getRegPeriod().getMonths(), "M");
        }
    }

    protected long createRegistrationTicket()
            throws NicHandleException, AccessDeniedException, NotAdmissiblePeriodException,
            HolderClassNotExistException, HolderCategoryNotExistException, ClassDoesNotMatchCategoryException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException,
            DomainIsNotCharityException, DomainIsCharityException, EmptyRemarkException, TooManyTicketsException,
            OwnerTypeNotExistException {
        NicHandle creatorNh = registry.getNicHandleSearchService().getNicHandle(user.getUsername());

        // check the registration period
        int periodInYears = request.getRegPeriod().getYears();
        registry.getPaymentSearchService().getProductPrice(Period.fromYears(periodInYears), OperationType.REGISTRATION,
                user.getUsername());

        return registry.getTicketService().createRegistrationTicket(user, request, creatorNh.getAccount().getId());
    }

    protected void exportNh() throws NicHandleNotFoundException, ExportException {
        OpInfo opInfo = new OpInfo(user, "NicHandle was exported due to domain registration");
        registry.getNicHandleService().triggerExport(user.getUsername(), opInfo);
    }
}
