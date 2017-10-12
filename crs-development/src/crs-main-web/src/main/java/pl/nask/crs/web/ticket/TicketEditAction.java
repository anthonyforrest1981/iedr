package pl.nask.crs.web.ticket;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Preparable;

import pl.nask.crs.app.tickets.TicketModel;
import pl.nask.crs.app.utils.ContactValidator;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.TicketEdit;

/**
 * Action for the Ticket EDIT view. Action methods:
 * <ul>
 * <li>{{@link #save()}</li>
 * </ul>
 *
 * @author Artur Gniadzik
 */
public class TicketEditAction extends TicketAction implements Preparable {
    private final Logger log = Logger.getLogger(this.getClass());

    private String responseText;

    protected ContactValidator contactValidator;

    public TicketEditAction(ApplicationConfig config) {
        super(config);
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    /**
     * Saves changes in the ticket. Forwards to the REVISE view.
     *
     * @return REVISE if the save operation is successful, ERROR if an error occurs.
     */
    public String save() throws Exception {
        return update(false);
    }

    public String forceSave() throws Exception {
        return update(true);
    }

    private String update(boolean forceUpdate) throws Exception {
        try {
            log();
            log.error("Save");
            Ticket ticket = getTicket();
            getTicketAppService().updateAsAdmin(getUser(), ticket.getId(), new TicketEdit(ticket.getOperation()),
                    null, responseText, ticket.isClikPaid(), forceUpdate);
            return REVISE;
        } catch (NicHandleNotActiveException ex) {
            addActionError("Contact " + ex.getNicHandleId() + " is not Active.");
            return ERROR;
        }
    }

    protected TicketModel getTicketModel(long id) throws AccessDeniedException, TicketNotFoundException {
        return getTicketAppService().edit(getUser(), id);
    }

    @Override
    public void prepare() throws Exception {
        if (ticketModel != null) {
            getTicketWrapper().updateNameserversWithNewValues();
        }
    }
}
