package pl.nask.crs.web.secondarymarket;

import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.secondarymarket.BuyRequestStatus;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public class BuyRequestsBrowserAction extends AuthenticatedUserAwareAction {

    private SecondaryMarketAppService service;
    private long id;

    private String newHostmaster;
    private BuyRequestStatus newStatus;

    public BuyRequestsBrowserAction(final SecondaryMarketAppService service) {
        this.service = service;
    }

    public String checkout() throws Exception {
        AuthenticatedUser user = getUser();
        service.checkoutBuyRequest(user, id);
        return SUCCESS;
    }

    public String checkin() throws Exception {
        AuthenticatedUser user = getUser();
        service.checkinBuyRequest(user, id, newStatus);
        return SUCCESS;
    }

    public String reassign() throws Exception {
        AuthenticatedUser user = getUser();
        service.reassignBuyRequest(user, id, newHostmaster);
        return SUCCESS;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNewHostmaster() {
        return newHostmaster;
    }

    public void setNewHostmaster(String newHostmaster) {
        this.newHostmaster = newHostmaster;
    }

    public BuyRequestStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(BuyRequestStatus newStatus) {
        this.newStatus = newStatus;
    }
}
