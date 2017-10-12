package pl.nask.crs.app.authorization;

import java.util.List;

import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.PermissionQuery;

public class ConjunctionPermissionQuery implements CombinedPermissionQuery {

    private List<? extends PermissionQuery> queries;

    public ConjunctionPermissionQuery(List<? extends PermissionQuery> queries) {
        this.queries = queries;
    }

    @Override
    public boolean isMetFor(PermissionAppService service, AuthenticatedUser user) {
        for (PermissionQuery query : queries) {
            if (!service.verifyUserPermission(user, query)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return "ConjunctionPermissionQuery";
    }

}
