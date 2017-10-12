package pl.nask.crs.app.authorization.queries;

import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.NamedPermissionQuery;

public class SellRequestPermissionQuery extends NamedPermissionQuery {

    private final long sellRequestId;
    private final AuthenticatedUser user;

    public SellRequestPermissionQuery(String name, long sellRequestId, AuthenticatedUser user) {
        super(name);
        this.sellRequestId = sellRequestId;
        this.user = user;
    }

    public long getSellRequestId() {
        return sellRequestId;
    }

    public AuthenticatedUser getUser() {
        return user;
    }

}
