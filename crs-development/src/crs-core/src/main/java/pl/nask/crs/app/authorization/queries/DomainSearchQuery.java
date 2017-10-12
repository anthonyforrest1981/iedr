package pl.nask.crs.app.authorization.queries;

import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.NamedPermissionQuery;

public class DomainSearchQuery extends NamedPermissionQuery {

    private String billingNH;
    private Long accountId;
    private AuthenticatedUser user;

    public DomainSearchQuery(AuthenticatedUser user, String permissionName, String billingNH, Long accountId) {
        super(permissionName);
        this.user = user;
        this.accountId = accountId;
        this.billingNH = billingNH;
    }

    public String getBillingNH() {
        return billingNH;
    }

    public Long getAccountId() {
        return accountId;
    }

    public AuthenticatedUser getUser() {
        return user;
    }
}
