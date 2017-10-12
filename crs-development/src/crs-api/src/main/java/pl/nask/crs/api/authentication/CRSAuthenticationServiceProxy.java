package pl.nask.crs.api.authentication;

import javax.jws.WebService;

import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.security.authentication.*;
import pl.nask.crs.token.PasswordResetTokenExpiredException;

@WebService(
    serviceName="CRSAuthenticationService",
    endpointInterface="pl.nask.crs.api.authentication.CRSAuthenticationService",
    portName = "CRSAuthenticationServicePort",
    targetNamespace = "http://domainregistry.ie/")
public class CRSAuthenticationServiceProxy implements CRSAuthenticationService {
    private CRSAuthenticationService service;

    public void setService(CRSAuthenticationService service) {
        this.service = service;
    }

    public AuthenticatedUserVO authenticate(String username, String password, String remoteAddress, String pin)
            throws InvalidUsernameOrPasswordException, NoLoginPermissionException, GoogleAuthenticationException,
            LoginLockedException, PasswordExpiredException, IncorrectUtf8FormatException {
        return service.authenticate(username, password, remoteAddress, pin);
    }

    public AuthenticatedUserVO switchUser(AuthenticatedUserVO superUser, String userName)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            PasswordResetTokenExpiredException, InvalidUsernameOrPasswordException, NoLoginPermissionException,
            SessionExpiredException, PasswordExpiredException, IncorrectUtf8FormatException {
        return service.switchUser(superUser, userName);
    }

    public AuthenticatedUserVO unswitch(AuthenticatedUserVO user)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            PasswordResetTokenExpiredException, UserNotSwitchedException, SessionExpiredException,
            PasswordExpiredException, IncorrectUtf8FormatException {
        return service.unswitch(user);
    }

    public boolean isUserSwitched(AuthenticatedUserVO user)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            PasswordResetTokenExpiredException, SessionExpiredException, PasswordExpiredException,
            IncorrectUtf8FormatException {
        return service.isUserSwitched(user);
    }

    public AuthenticatedUserVO authenticateAndSwitchUser(String superUserName, String userName,
            String superUserPassword, String remoteAddress)
            throws AuthenticationException, IncorrectUtf8FormatException {
        return service.authenticateAndSwitchUser(superUserName, userName, superUserPassword, remoteAddress);
    }

    public void logout(AuthenticatedUserVO user)
            throws AccessDeniedException, InvalidSessionTokenException, PasswordResetTokenExpiredException,
            SessionTokenExpiredException, IncorrectUtf8FormatException {
        service.logout(user);
    }
}
