package pl.nask.crs.api.authentication;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.security.authentication.*;
import pl.nask.crs.token.PasswordResetTokenExpiredException;

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSAuthenticationService {

    /**
     * Authenticates the user into the system
     *
     * @param username      user nic handle
     * @param password      user password
     * @param remoteAddress user ip
     * @return authentication token to be used when calling service methods that require user authentication
     *
     * @throws pl.nask.crs.security.authentication.InvalidUsernameOrPasswordException
     * @throws PasswordExpiredException
     * @throws IllegalArgumentException if username is null
     * @throws AuthenticationException general authentication failure
     */
    @WebMethod
    AuthenticatedUserVO authenticate(@WebParam(name = "username") String username,
            @WebParam(name = "password") String password, @WebParam(name = "remoteAddress") String remoteAddress,
            @WebParam(name = "pin") String pin)
            throws InvalidUsernameOrPasswordException, NoLoginPermissionException, GoogleAuthenticationException,
            LoginLockedException, PasswordExpiredException, IncorrectUtf8FormatException;

    /**
     * Switches super user to user defined by userName param.
     *
     * @param superUser super user authentication token, required
     * @param userName  user name of user to be switched to
     * @return
     *
     * @throws AccessDeniedException
     * @throws UserNotAuthenticatedException
     * @throws pl.nask.crs.security.authentication.InvalidSessionTokenException
     * @throws pl.nask.crs.token.PasswordResetTokenExpiredException
     * @throws pl.nask.crs.security.authentication.InvalidUsernameOrPasswordException
     * @throws NoLoginPermissionException
     */
    @WebMethod
    AuthenticatedUserVO switchUser(@WebParam(name = "superUser") AuthenticatedUserVO superUser, @WebParam(
            name = "userName") String userName)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            PasswordResetTokenExpiredException, InvalidUsernameOrPasswordException, NoLoginPermissionException,
            SessionExpiredException, PasswordExpiredException, IncorrectUtf8FormatException;

    /**
     * Switches back to super user.
     *
     * @param user user authentication token
     * @return
     *
     * @throws AccessDeniedException
     * @throws UserNotAuthenticatedException
     * @throws pl.nask.crs.security.authentication.InvalidSessionTokenException
     * @throws pl.nask.crs.token.PasswordResetTokenExpiredException
     * @throws UserNotSwitchedException
     */
    @WebMethod
    AuthenticatedUserVO unswitch(@WebParam(name = "user") AuthenticatedUserVO user)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            PasswordResetTokenExpiredException, UserNotSwitchedException, SessionExpiredException,
            PasswordExpiredException, IncorrectUtf8FormatException;

    /**
     * Checks if user is switched.
     *
     * @param user user authentication token
     * @return
     *
     * @throws AccessDeniedException
     * @throws UserNotAuthenticatedException
     * @throws pl.nask.crs.security.authentication.InvalidSessionTokenException
     * @throws pl.nask.crs.token.PasswordResetTokenExpiredException
     */
    @WebMethod
    boolean isUserSwitched(@WebParam(name = "user") AuthenticatedUserVO user)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            PasswordResetTokenExpiredException, SessionExpiredException, PasswordExpiredException,
            IncorrectUtf8FormatException;

    /**
     * Authenticates super user in system and switches to standard user.
     * If authentication passes, switched AuthenticatedUser is returned.
     * Otherwise exception is thrown.
     *
     * @param superUserName
     * @param userName
     * @param superUserPassword
     * @param remoteAddress
     * @return
     *
     * @throws AuthenticationException
     * @throws IllegalArgumentException
     */
    @WebMethod
    AuthenticatedUserVO authenticateAndSwitchUser(@WebParam(name = "superUserName") String superUserName, @WebParam(
            name = "userName") String userName, @WebParam(name = "superUserPassword") String superUserPassword,
            @WebParam(name = "remoteAddress") String remoteAddress)
            throws AuthenticationException, IncorrectUtf8FormatException;

    @WebMethod
    void logout(@WebParam(name = "user") AuthenticatedUserVO user)
            throws AccessDeniedException, InvalidSessionTokenException, PasswordResetTokenExpiredException,
            SessionTokenExpiredException, IncorrectUtf8FormatException;
}
