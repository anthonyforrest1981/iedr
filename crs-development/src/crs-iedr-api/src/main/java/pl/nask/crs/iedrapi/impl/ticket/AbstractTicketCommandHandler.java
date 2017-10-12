package pl.nask.crs.iedrapi.impl.ticket;

/*>>>import org.checkerframework.checker.nullness.qual.EnsuresNonNull;*/
/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import pl.nask.crs.contacts.Contact;
import pl.nask.crs.iedrapi.exceptions.AuthorizationErrorException;
import pl.nask.crs.iedrapi.exceptions.ObjectDoesNotExistException;
import pl.nask.crs.iedrapi.exceptions.ReasonCode;
import pl.nask.crs.iedrapi.exceptions.Value;
import pl.nask.crs.iedrapi.impl.AbstractCommandHandler;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.xml.Constants;

public class AbstractTicketCommandHandler extends AbstractCommandHandler {

    public static final String TICKET_NAMESPACE = Constants.IEAPI_TICKET_NAMESPACE;

    /*>>>@EnsuresNonNull("#2")*/
    public void checkTicket(AuthenticatedUser user, /*>>>@Nullable*/ final Ticket ticket, String domainName)
            throws AuthorizationErrorException, ObjectDoesNotExistException {
        if (ticket == null)
            throw new ObjectDoesNotExistException(ReasonCode.TICKET_NAME_DOES_NOT_EXIST, new Value("name",
                    TICKET_NAMESPACE, domainName));
    }

}
