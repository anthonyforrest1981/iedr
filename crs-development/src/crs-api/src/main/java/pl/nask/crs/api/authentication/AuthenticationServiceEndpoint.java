package pl.nask.crs.api.authentication;

import org.apache.log4j.Logger;

import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.security.authentication.*;

public class AuthenticationServiceEndpoint extends WsSessionAware implements CRSAuthenticationService {
    private static final Logger log = Logger.getLogger(AuthenticationServiceEndpoint.class);

    private NicHandleAppService nicHandleAppService;

    public void setNicHandleAppService(NicHandleAppService nicHandleAppService) {
        this.nicHandleAppService = nicHandleAppService;
    }

    /* (non-Javadoc)
     * @see pl.nask.crs.api.CRSAuthenticationService#authenticate(java.lang.String, java.lang.String)
     */
    public AuthenticatedUserVO authenticate(String username, String password, String remoteAddress, String pin)
            throws InvalidUsernameOrPasswordException, NoLoginPermissionException, GoogleAuthenticationException,
            LoginLockedException, PasswordExpiredException {
        log.debug("called authenticate with username: " + username + " and password: " + password);
        log.debug("calling service method");
        try {
            AuthenticatedUser u = authenticationService.authenticate(username, password, true, remoteAddress, true,
                    pin, true, "ws");
            log.debug("service method returned " + u);
            return new AuthenticatedUserVO(u);
        } catch (PasswordExpiredException e) {
            AuthenticatedUser u = authenticationService.authenticate(username, password, false, remoteAddress, true,
                    pin, true, "ws");
            log.debug("retrying due to password expiry, service method returned " + u);
            AuthenticatedUserVO au = new AuthenticatedUserVO(u);
            au.setPasswordChangeRequired(true);
            return au;
        }
    }

    @Override
    public AuthenticatedUserVO switchUser(AuthenticatedUserVO superUser, String userName)
            throws AccessDeniedException, InvalidUsernameOrPasswordException, NoLoginPermissionException,
            SessionExpiredException, PasswordExpiredException {
        validateSession(superUser);
        AuthenticatedUser realSuperUser = superUser;
        try {
            if (authenticationService.isUserSwitched(superUser)) {
                realSuperUser = authenticationService.unswitch(superUser);
            }
        } catch (UserNotSwitchedException e) {
            log.warn("User was supposed to be switched, a bug here?");
        }

        AuthenticatedUser u = authenticationService.switchUser(realSuperUser, userName);
        return new AuthenticatedUserVO(u);
    }

    @Override
    public AuthenticatedUserVO unswitch(AuthenticatedUserVO user)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            UserNotSwitchedException, SessionExpiredException, PasswordExpiredException {
        validateSession(user);
        AuthenticatedUser u = authenticationService.unswitch(user);
        return new AuthenticatedUserVO(u);
    }

    @Override
    public boolean isUserSwitched(AuthenticatedUserVO user)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            SessionExpiredException, PasswordExpiredException {
        validateSession(user);
        return authenticationService.isUserSwitched(user);
    }

    @Override
    public AuthenticatedUserVO authenticateAndSwitchUser(String superUserName, String userName,
            String superUserPassword, String remoteAddress) throws AuthenticationException {
        AuthenticatedUser u = authenticationService.authenticateAndSwitchUser(superUserName, userName,
                superUserPassword, false, remoteAddress, true);
        return new AuthenticatedUserVO(u);
    }

    @Override
    public void logout(AuthenticatedUserVO user)
            throws AccessDeniedException, InvalidSessionTokenException, SessionTokenExpiredException {
        authenticationService.logout(user);
    }

}
