package pl.nask.crs.iedrapi.impl.ticket;

import pl.nask.crs.app.commons.exceptions.CancelTicketException;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.iedrapi.*;
import pl.nask.crs.iedrapi.exceptions.*;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_ticket_1.SNameType;

public class TicketDeleteCommandHandler extends AbstractTicketCommandHandler implements APICommandHandler<SNameType> {

    @Override
    public ResponseType handle(AuthData auth, SNameType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        ApiValidator.assertNoError(callback);
        AuthenticatedUser user = auth.getUser();
        Ticket t = getTicketForDomain(user, command.getName());

        checkTicket(user, t, command.getName());

        try {
            getCommonAppService().cancelTicketAsOwner(user, t.getId());
        } catch (TicketNotFoundException e) {
            throw new ObjectDoesNotExistException(ReasonCode.TICKET_NAME_DOES_NOT_EXIST, new Value("name",
                    TICKET_NAMESPACE, command.getName()));
        } catch (CancelTicketException e) {
            throw new CommandFailed(e);
        } catch (CardPaymentException e) {
            throw new CommandFailed(e);
        } catch (DomainNotFoundException e) {
            // should not happen, XFER/MOD/DEL tickets must correspond to a domain, REG ticket would
            // not look for a domain
            throw new CommandFailed(e);
        } catch (DomainIllegalStateException e) {
            throw new CommandFailed(e);
        }
        return ResponseTypeFactory.success();
    }

}
