package pl.nask.crs.app.commons.register;

import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.AppServicesRegistry;
import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.ValidationException;
import pl.nask.crs.app.commons.TicketRequest;
import pl.nask.crs.app.tickets.exceptions.DomainIsCharityException;
import pl.nask.crs.app.tickets.exceptions.DomainIsNotCharityException;
import pl.nask.crs.app.tickets.exceptions.NicHandleRecreateException;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.entities.exceptions.ClassDoesNotMatchCategoryException;
import pl.nask.crs.entities.exceptions.HolderCategoryNotExistException;
import pl.nask.crs.entities.exceptions.HolderClassNotExistException;
import pl.nask.crs.entities.exceptions.OwnerTypeNotExistException;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.payment.CreditCard;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.exceptions.NotAdmissiblePeriodException;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class RegisterDomainCC extends RegisterDomain {

    private final CreditCard creditCard;

    public RegisterDomainCC(AppServicesRegistry appRegistry, ServicesRegistry registry, AuthenticatedUser user,
            TicketRequest request, CreditCard creditCard) {
        super(appRegistry, registry, user, request);
        this.creditCard = creditCard;
    }

    public long run() throws NicHandleException, AccessDeniedException, NotAdmissiblePeriodException,
            HolderClassNotExistException, HolderCategoryNotExistException, ClassDoesNotMatchCategoryException,
            DomainNotFoundException, ValidationException, NicHandleRecreateException, AccountNotFoundException,
            DomainIsNotCharityException, CardPaymentException, DomainIsCharityException, EmptyRemarkException,
            TooManyTicketsException, ExportException, OwnerTypeNotExistException {
        long ticketId = createRegistrationTicket();
        registry.getPaymentService().authorizeCCPaymentForTicket(user, user.getUsername(), request.getDomainName(),
                request.getDomainHolder(), request.getRegPeriod(), OperationType.REGISTRATION, creditCard, ticketId);
        sendRegistrationApplicationEmail(user, ticketId);
        exportNh();
        return ticketId;
    }

}
