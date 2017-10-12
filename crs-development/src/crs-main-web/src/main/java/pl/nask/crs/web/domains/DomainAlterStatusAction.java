package pl.nask.crs.web.domains;

import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.app.triplepass.TriplePassSupportService;
import pl.nask.crs.domains.NewDomainStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public class DomainAlterStatusAction extends AuthenticatedUserAwareAction {
    private final TriplePassSupportService triplePassSupportService;
    private final DomainAppService domainAppService;
    private final TicketSearchService ticketSearchService;

    private String domainName;

    private NewDomainStatus newStatus;
    private String remark;

    public DomainAlterStatusAction(TriplePassSupportService triplePassSupportService, DomainAppService domainAppService,
            TicketSearchService ticketSearchService) {
        super();
        this.triplePassSupportService = triplePassSupportService;
        this.domainAppService = domainAppService;
        this.ticketSearchService = ticketSearchService;
    }

    public String alterStatus() throws Exception {
        if (newStatus == NewDomainStatus.Deleted) {
            domainAppService.enterVoluntaryNRP(getUser(), domainName);
        } else if (newStatus == NewDomainStatus.Active) {
            try {
                Ticket ticket = ticketSearchService.getTicketForDomain(domainName);
                triplePassSupportService.triplePass(getUser(), ticket.getId());
            } catch (TooManyTicketsException e) {
                return ERROR;
            }
        } else if (newStatus == NewDomainStatus.Reactivate) {
            domainAppService.removeFromVoluntaryNRP(getUser(), domainName);
        }
        return SUCCESS;
    }

    public void setNewStatus(NewDomainStatus status) {
        this.newStatus = status;
    }

    public NewDomainStatus getNewStatus() {
        return newStatus;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
