package pl.nask.crs.security.authorization;

import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.PermissionDeniedException;
import pl.nask.crs.user.permissions.PermissionQuery;

public interface AuthorizationService {

    void authorize(final String username, PermissionQuery query) throws PermissionDeniedException;

    void authorize(AuthenticatedUser user, PermissionQuery query) throws PermissionDeniedException;

}
