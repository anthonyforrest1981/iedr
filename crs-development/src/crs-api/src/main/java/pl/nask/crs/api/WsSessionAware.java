package pl.nask.crs.api;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.security.authentication.*;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class WsSessionAware {

    protected WsAuthenticationService authenticationService;

    public void setAuthenticationService(WsAuthenticationService authenticationService) {
        Validator.assertNotNull(authenticationService, "ws authentication service");
        this.authenticationService = authenticationService;
    }

    protected void validateSession(AuthenticatedUser user)
            throws AccessDeniedException, InvalidSessionTokenException, SessionExpiredException,
            PasswordExpiredException {
        try {
            authenticationService.validateAndRefreshToken(user);
        } catch (SessionTokenExpiredException e) {
            throw new SessionExpiredException();
        }
    }

    protected AuthenticatedUser validateSessionAndRetrieveFullUserInfo(AuthenticatedUser user)
            throws AccessDeniedException, InvalidSessionTokenException, SessionExpiredException,
            PasswordExpiredException {
        return validateSessionAndRetrieveFullUserInfo(user, true);
    }

    protected AuthenticatedUser validateSessionAndRetrieveFullUserInfo(AuthenticatedUser user,
            boolean validatePasswordExpiry)
            throws AccessDeniedException, InvalidSessionTokenException, SessionExpiredException,
            PasswordExpiredException {
        try {
            authenticationService.validateAndRefreshToken(user, validatePasswordExpiry);
            return authenticationService.getCompleteUser(user);
        } catch (SessionTokenExpiredException e) {
            throw new SessionExpiredException();
        }
    }

}
