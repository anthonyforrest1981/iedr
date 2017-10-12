package pl.nask.crs.app.authorization;

import pl.nask.crs.security.authentication.AuthenticatedUser;

public class AlternativeNamesPermissionQuery implements CombinedPermissionQuery {

    private String[] permissionNames;

    public AlternativeNamesPermissionQuery(String... names) {
        permissionNames = names;
    }

    @Override
    public boolean isMetFor(PermissionAppService service, AuthenticatedUser user) {
        for (String name : permissionNames) {
            if (service.verifyUserPermission(user, name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getName() {
        return "AlternativeNamesPermissionQuery";
    }

}
