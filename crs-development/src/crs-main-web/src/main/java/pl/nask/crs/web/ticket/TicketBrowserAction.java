package pl.nask.crs.web.ticket;

import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.tickets.TicketModel;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

/**
 * Action class for the ticket browser. Allows to perform action on the selected ticket (ticket with given id).
 *
 * @author Artur Gniadzik
 */
public class TicketBrowserAction extends AuthenticatedUserAwareAction {

    private TicketAppService ticketService;

    private long id;

    private AdminStatus newAdminStatus;

    private String newHostmaster;

    public TicketBrowserAction(TicketAppService ticketService) {
        Validator.assertNotNull(ticketService, "ticket service");
        this.ticketService = ticketService;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPreviousTicketId() {
        return id;
    }

    public AdminStatus getNewAdminStatus() {
        return newAdminStatus;
    }

    public void setNewAdminStatus(AdminStatus newAdminStatus) {
        this.newAdminStatus = newAdminStatus;
    }

    public String getNewHostmaster() {
        return newHostmaster;
    }

    public void setNewHostmaster(String newHostmaster) {
        this.newHostmaster = newHostmaster;
    }

    public String checkIn() throws Exception {
        ticketService.checkIn(getUser(), id, newAdminStatus);
        return SUCCESS;
    }

    public String checkOut() throws Exception {
        AuthenticatedUser user = getUser();
        ticketService.checkOut(user, id);
        return SUCCESS;
    }

    public String reassign() throws Exception {
        ticketService.reassign(getUser(), id, newHostmaster);
        return SUCCESS;
    }

    public String alterStatus() throws Exception {
        if (newAdminStatus == AdminStatus.PASSED && hasFailureReasons(id)) {
            addActionError("Cannot set admin status to PASSED: ticket contains failure reasons.");
            return ERROR;
        }
        ticketService.alterStatus(getUser(), id, newAdminStatus);
        return SUCCESS;
    }

    private boolean hasFailureReasons(long id) throws Exception {
        TicketModel ticketModel = ticketService.view(getUser(), id);
        return ticketModel.getTicket().getOperation().hasFailureReasons();
    }

}
