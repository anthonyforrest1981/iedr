package pl.nask.crs.app.authorization.permissions;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.SellRequest;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.permissions.PermissionDeniedException;

public class DirectRegistrarPermission extends ResellerPermission {

    public DirectRegistrarPermission(String id, String name, NicHandleSearchService nicHandleSearchService) {
        super(id, name, nicHandleSearchService);
    }

    public DirectRegistrarPermission(String id, Permission namedPermission,
            NicHandleSearchService nicHandleSearchService) {
        super(id, namedPermission, nicHandleSearchService);
    }

    @Override
    protected boolean verifyAccessToDomain(NicHandle nh, Domain d) {
        if (nh.getAccount().getId() != d.getResellerAccount().getId()) {
            return false;
        }

        if (d.getBillingContact() == null || d.getBillingContact().getNicHandle() == null
                || !d.getBillingContact().getNicHandle().equals(nh.getNicHandleId())) {
            return false;
        }

        return true;
    }

    @Override
    protected boolean verifyAccessToTicket(AuthenticatedUser user, Ticket ticket) {
        return user.getUsername().equals(
                ticket.getOperation().getBillingContactsField().get(0).getNewValue().getNicHandle());
    }

    @Override
    protected boolean verifyAccessToAccount(long accountId, String nicHandleId) {
        return false;
    }

    @Override
    protected boolean verifyAccessToNicHandle(AuthenticatedUser user, NicHandle nicHandle)
            throws PermissionDeniedException {
        String directNh = user.getUsername();
        return directNh.equalsIgnoreCase(nicHandle.getCreator())
                || directNh.equalsIgnoreCase(nicHandle.getNicHandleId())
                || nhIsAContactInDirectsDomain(directNh, nicHandle.getNicHandleId());
    }

    @Override
    protected boolean verifyAccessToSearchForNicHandle(AuthenticatedUser user, Long accountNumber, String creator)
            throws PermissionDeniedException {
        return user.getUsername().equals(creator);
    }

    @Override
    protected boolean verifyAccessToBuyRequest(BuyRequest buyRequest, NicHandle directNh) {
        return directNh.getNicHandleId().equals(buyRequest.getCreatorNH())
                || nhIsAContactInDirectsDomain(directNh.getNicHandleId(), buyRequest.getCreatorNH());
    }

    @Override
    protected boolean verifyAccessToSellRequest(SellRequest sellRequest, NicHandle directNh) {
        if (!getNicHandleSearchService().isAccountDirect(sellRequest.getBuyRequest().getAccount().getId())) {
            return false;
        }
        return directNh.getNicHandleId().equals(sellRequest.getCreatorNH())
                || nhIsAContactInDirectsDomain(directNh.getNicHandleId(), sellRequest.getCreatorNH());
    }

    private boolean nhIsAContactInDirectsDomain(String directNh, String nh) {
        DomainSearchCriteria crit = new DomainSearchCriteria();
        crit.setBillingNH(directNh);
        crit.setNicHandle(nh);
        return getDomainDAO().exists(crit);
    }

    @Override
    public String getDescription() {
        if (getClass() != DirectRegistrarPermission.class)
            return null;
        return "Contextual, allows to access domains, tickets and nichandles if the account number is 1, user is a domain billing contact, creator of a ticket or nichandle, or a nichandle is a contact in one of the domains the user is a billing contact in. In combination with "
                + getMethodPermission().getId() + " (" + getMethodPermission().getDescription() + ")";
    }
}
