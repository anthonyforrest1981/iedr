package pl.nask.crs.app.authorization.queries;

import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.NamedPermissionQuery;

public class NicHandleSearchPermissionQuery extends NamedPermissionQuery {

    private final AuthenticatedUser user;
    private final Long accountNumber;
    private final String creator;

    public NicHandleSearchPermissionQuery(String permissionName, Long accountNumber, String creator,
            AuthenticatedUser user) {
        super(permissionName);
        this.accountNumber = accountNumber;
        this.creator = creator;
        this.user = user;
    }

    public AuthenticatedUser getUser() {
        return user;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public String getCreator() {
        return creator;
    }

}
