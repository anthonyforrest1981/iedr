package pl.nask.crs.app.authorization.permissions;

import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.NamedPermission;

public class OwnNhPermission extends DirectRegistrarPermission {

    public OwnNhPermission(String id, NamedPermission namedPermission, NicHandleSearchService nicHandleSearchService) {
        super(id, namedPermission, nicHandleSearchService);
    }

    @Override
    protected boolean verifyAccessToNicHandle(AuthenticatedUser user, NicHandle nicHandle) {
        return user.getUsername().equalsIgnoreCase(nicHandle.getNicHandleId());
    }

    @Override
    public String getDescription() {
        if (getClass() != OwnNhPermission.class)
            return null;
        return "Contextual permission, grants access to user's NicHandle object, combined with "
                + getMethodPermission().getId() + " (" + getMethodPermission().getDescription() + ")";
    }
}
