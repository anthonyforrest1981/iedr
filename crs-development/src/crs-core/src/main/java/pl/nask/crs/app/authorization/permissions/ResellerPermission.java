package pl.nask.crs.app.authorization.permissions;

import pl.nask.crs.app.InvalidAccountNumberException;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.payment.Invoice;
import pl.nask.crs.payment.exceptions.DomainManagedByAnotherResellerException;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.SellRequest;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.permissions.PermissionDeniedException;

public class ResellerPermission extends DefaultAccessPermission {

    public ResellerPermission(String id, String name, NicHandleSearchService nicHandleSearchService) {
        super(id, name, nicHandleSearchService);
    }

    public ResellerPermission(String id, Permission namedPermission, NicHandleSearchService nicHandleSearchService) {
        super(id, namedPermission, nicHandleSearchService);
    }

    @Override
    protected boolean verifyAccessToBuyRequest(BuyRequest buyRequest, NicHandle nh) throws PermissionDeniedException {
        long buyRequestBuyerAccountId = getNicHandle(buyRequest.getCreatorNH()).getAccount().getId();
        long userAccountId = nh.getAccount().getId();
        return buyRequestBuyerAccountId == userAccountId;
    }

    @Override
    protected boolean verifyAccessToSellRequest(SellRequest sellRequest, NicHandle nh) {
        long sellRequestAccountId = sellRequest.getBuyRequest().getAccount().getId();
        long userAccountId = nh.getAccount().getId();
        return sellRequestAccountId == userAccountId;
    }

    @Override
    protected boolean verifyAccessToTicket(AuthenticatedUser user, Ticket ticket) throws PermissionDeniedException {
        if (ticket == null) {
            return true;
        }
        NicHandle nicHandle = getNicHandle(user.getUsername());
        NicHandle creator = getNicHandle(ticket.getCreator().getNicHandle());
        return creator.getAccount().getId() == nicHandle.getAccount().getId();
    }

    @Override
    protected boolean verifyAccessToAccount(long accountId, String nicHandleId) throws PermissionDeniedException {
        NicHandle nicHandle = getNicHandle(nicHandleId);
        return accountId == nicHandle.getAccount().getId();
    }

    @Override
    protected boolean validateAccountNumber(AuthenticatedUser user, Long accountNumber)
            throws PermissionDeniedException {
        if (accountNumber == null) {
            return false;
        }
        if (getNicHandleSearchService().isAccountDirect(accountNumber)) {
            return false;
        }
        NicHandle userNh = getNicHandle(user.getUsername());
        if ((long) accountNumber == userNh.getAccount().getId()) {
            return true;
        } else {
            throw new InvalidAccountNumberException();
        }
    }

    @Override
    protected boolean verifyAccessToInvoice(AuthenticatedUser user, Invoice invoice) {
        if (invoice == null) {
            return true;
        }
        return invoice.getBillingNicHandle().equalsIgnoreCase(user.getUsername());
    }

    private boolean isSelfNameChange(String billNHId, NicHandle changedNH) throws PermissionDeniedException {
        return billNHId.equals(changedNH.getNicHandleId()) || isNameNotChanged(changedNH);
    }

    private boolean isNameNotChanged(NicHandle changedNH) throws PermissionDeniedException {
        NicHandle oldNH = getNicHandle(changedNH.getNicHandleId());
        return oldNH.getName().equals(changedNH.getName());
    }

    @Override
    protected boolean verifyAccessToNicHandle(AuthenticatedUser user, NicHandle nicHandle)
            throws PermissionDeniedException {
        if (getNicHandleSearchService().isNicHandleDirect(nicHandle)) {
            return false;
        }
        if (!isSelfNameChange(user.getUsername(), nicHandle)) {
            return false;
        }

        NicHandle userNh = getNicHandle(user.getUsername());
        if (nicHandle.getAccount().getId() == userNh.getAccount().getId()) {
            return true;
        } else {
            throw new InvalidAccountNumberException();
        }
    }

    @Override
    protected boolean verifyAccessToDomain(NicHandle nicHandle, Domain domain) throws PermissionDeniedException {
        if (domain == null) {
            return true;
        }
        if (domain.getResellerAccount().getId() != nicHandle.getAccount().getId()) {
            throw new DomainManagedByAnotherResellerException(domain.getName());
        }
        return true;
    }

    @Override
    public String getDescription() {
        if (getClass() != ResellerPermission.class)
            return null;
        return "Contextual, allows to access domains, tickets and nichandles with the same account number (<>1) as user's. In combination with "
                + getMethodPermission().getId() + " (" + getMethodPermission().getDescription() + ")";
    }
}
