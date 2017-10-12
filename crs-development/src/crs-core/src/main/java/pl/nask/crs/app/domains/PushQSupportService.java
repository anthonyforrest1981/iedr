package pl.nask.crs.app.domains;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;

public interface PushQSupportService {

    void pushQ(AuthenticatedUser user, OpInfo opInfo) throws UserNotAuthenticatedException, AccessDeniedException;

}
