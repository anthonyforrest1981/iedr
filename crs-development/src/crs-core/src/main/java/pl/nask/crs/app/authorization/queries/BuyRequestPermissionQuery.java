package pl.nask.crs.app.authorization.queries;

import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.NamedPermissionQuery;

public class BuyRequestPermissionQuery extends NamedPermissionQuery {

    private final long buyRequestId;
    private final AuthenticatedUser user;

    public BuyRequestPermissionQuery(String name, long buyRequestId, AuthenticatedUser user) {
        super(name);
        this.buyRequestId = buyRequestId;
        this.user = user;
    }

    public long getBuyRequestId() {
        return buyRequestId;
    }

    public AuthenticatedUser getUser() {
        return user;
    }
}
