package pl.nask.crs.app.authorization;

import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.PermissionQuery;

public interface CombinedPermissionQuery extends PermissionQuery {

    public boolean isMetFor(PermissionAppService service, AuthenticatedUser user);

}
