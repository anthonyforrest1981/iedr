package pl.nask.crs.security.authentication.impl;

import java.util.List;

import pl.nask.crs.commons.HashAlgorithm;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.security.authentication.*;
import pl.nask.crs.security.dao.LoginAttemptDAO;
import pl.nask.crs.user.dao.UserDAO;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class WsAuthenticationServiceImpl extends AbstractWsAuthenticationService {

    public WsAuthenticationServiceImpl(UserDAO userDAO, List<HashAlgorithm> algorithms,
            ApplicationConfig applicationConfig, LoginAttemptDAO loginAttemptDAO) {
        super(userDAO, algorithms, applicationConfig, loginAttemptDAO);
    }

    /**
     * Method to authentitace a user in the system.
     *
     *
     *
     *
     * @param username username given by the user who wants to authenticate in the system
     * @param password password given by the user who wants to authenticate in the system
     * @param useLoginLock
     * @param code @return AuthenticatedUser which represents authenticated user in the system
     * @throws AuthenticationException  general exception
     * @throws pl.nask.crs.security.authentication.InvalidUsernameOrPasswordException when the username is not found in the system
     * @throws PasswordExpiredException when the password has expired
     * @throws IllegalArgumentException when the username or the password is null
     */
    @Override
    public AuthenticatedUser authenticate(String username, String password, boolean validateExpiration,
            String remoteAddress, boolean useLoginLock, String code, boolean useTfa, String systemDiscriminator)
            throws InvalidUsernameOrPasswordException, PasswordExpiredException, GoogleAuthenticationException,
            LoginLockedException, NoLoginPermissionException {
        AuthenticatedUser authenticatedUser = super.authenticate(username, password, validateExpiration, remoteAddress,
                useLoginLock, code, useTfa, systemDiscriminator);
        cleanupTokens();
        String token = registerToken(authenticatedUser.getUsername());
        return new AuthenticatedUserImpl(authenticatedUser.getUsername(), token);
    }

    @Override
    public AuthenticatedUser authenticateAndSwitchUser(String superUserName, String userName, String superUserPassword,
            boolean validateExpiration, String remoteAddress, boolean useLoginLock)
            throws AuthenticationException, IllegalArgumentException, AccessDeniedException {
        try {
            AuthenticatedUser authenticatedSuperUser = this.authenticate(superUserName, superUserPassword,
                    validateExpiration, remoteAddress, useLoginLock, null, true, "ws");
            return this.switchUser(authenticatedSuperUser, userName);
        } catch (UserNotAuthenticatedException e) {
            //should never happen
            throw new IllegalStateException(e);
        } catch (InvalidSessionTokenException e) {
            //should never happen
            throw new IllegalStateException(e);
        } catch (SessionTokenExpiredException e) {
            //should never happen
            throw new IllegalStateException(e);
        }
    }
}
