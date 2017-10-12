package pl.nask.crs.security.authentication.impl;

import java.util.*;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.HashAlgorithm;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.security.authentication.*;
import pl.nask.crs.security.dao.LoginAttemptDAO;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.UserDAO;
import pl.nask.crs.user.permissions.LoginPermission;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
abstract class AbstractWsAuthenticationService extends AuthenticationServiceImpl implements WsAuthenticationService {

    UserDAO userDAO;
    final List<HashAlgorithm> algorithms;
    final Map<String, SessionToken> tokens;
    long lastTokensCleanup;
    final static long TOKEN_CLEANUP_INTERVAL = 60 * 60 * 1000;
    {
        tokens = Collections.synchronizedMap(new HashMap<String, SessionToken>());
        lastTokensCleanup = new Date().getTime();
    }

    public AbstractWsAuthenticationService(UserDAO userDAO, List<HashAlgorithm> algorithms,
            ApplicationConfig applicationConfig, LoginAttemptDAO loginAttemptDAO) {
        super(userDAO, algorithms, applicationConfig, loginAttemptDAO);
        this.algorithms = algorithms;
        this.userDAO = userDAO;
    }

    // session timeout in millis
    private long getSessionTimeout() {
        return 60L * 1000 * applicationConfig.getUserSessionTimeout();
    }

    String registerToken(String userName) {
        synchronized (tokens) {
            SessionToken token = new SessionToken(userName, getNewUUID());
            tokens.put(token.getTokenId(), token);
            return token.getTokenId();
        }
    }

    String getNewUUID() {
        return UUID.randomUUID().toString();
    }

    void cleanupTokens() {
        long sessionTimeout = getSessionTimeout();
        long currDateInMilis = new Date().getTime();
        if (isCleanupNecessary(currDateInMilis)) {
            synchronized (tokens) {
                Iterator<Map.Entry<String, SessionToken>> iterator = tokens.entrySet().iterator();
                while (iterator.hasNext()) {
                    SessionToken token = iterator.next().getValue();
                    if (token.isExpired(sessionTimeout)) {
                        String username;
                        if (token.getSuperUserName() != null) {
                            username = token.getSuperUserName() + " (acting as " + token.getUserName() + ")";
                        } else {
                            username = token.getUserName();
                        }
                        Logger.getLogger(WsAuthenticationService.class)
                                .info("Expired login token cleaned up for " + username);
                        iterator.remove();
                    }
                }
            }
            lastTokensCleanup = currDateInMilis;
        }
    }

    boolean isCleanupNecessary(long currDate) {
        return lastTokensCleanup + TOKEN_CLEANUP_INTERVAL < currDate;
    }

    @Override
    public void validateAndRefreshToken(AuthenticatedUser user)
            throws AccessDeniedException, InvalidSessionTokenException, SessionTokenExpiredException,
            PasswordExpiredException {
        validateAndRefreshToken(user, true);
    }

    @Override
    public void validateAndRefreshToken(AuthenticatedUser user, boolean validatePasswordExpiry)
            throws AccessDeniedException, InvalidSessionTokenException, SessionTokenExpiredException,
            PasswordExpiredException {
        validateToken(user.getUsername(), user.getAuthenticationToken());
        refreshToken(user.getAuthenticationToken());
        if (validatePasswordExpiry) {
            SessionToken token = tokens.get(user.getAuthenticationToken());
            String loggedInUser = isSwitched(token) ? token.getSuperUserName() : token.getUserName();
            if (!isPasswordActive(loggedInUser)) {
                throw new PasswordExpiredException("Your password is expired");
            }
        }
    }

    void validateToken(String userName, String tokenId)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionTokenExpiredException {
        synchronized (tokens) {
            SessionToken token = tokens.get(tokenId);
            if (token == null) {
                throw new UserNotAuthenticatedException();
            }
            if (!userName.equals(token.getUserName())) {
                throw new InvalidSessionTokenException();
            }
            if (token.isExpired(getSessionTimeout())) {
                tokens.remove(tokenId);
                throw new SessionTokenExpiredException();
            }
        }
    }

    void refreshToken(String tokenId) {
        synchronized (tokens) {
            SessionToken token = tokens.get(tokenId);
            token.refresh();
        }
    }

    @Override
    public AuthenticatedUser switchUser(AuthenticatedUser superUser, String userName)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            InvalidUsernameOrPasswordException, NoLoginPermissionException, SessionTokenExpiredException,
            PasswordExpiredException {

        Validator.assertNotNull(userName, "new user name");

        validateAndRefreshToken(superUser);

        userName = userName.trim().toUpperCase();
        User user = userDAO.get(userName);
        if (user == null)
            throw new InvalidUsernameOrPasswordException(userName);
        if (!LoginPermissionChecker.checkLoginPermission(user, LoginPermission.WS)) {
            throw new NoLoginPermissionException(user.getUsername());
        }

        String token = registerToken(userName, superUser.getUsername(), superUser.getAuthenticationToken());
        Logger.getLogger(WsAuthenticationService.class)
                .info("User: " + superUser.getUsername() + " acts on behalf of " + userName);
        return new AuthenticatedUserImpl(user.getUsername(), superUser.getUsername(), token);
    }

    String registerToken(String userName, String superUserName, String superUserTokenId) {
        synchronized (tokens) {
            SessionToken token = new SessionToken(userName, getNewUUID(), superUserName, superUserTokenId);
            tokens.put(token.getTokenId(), token);
            return token.getTokenId();
        }
    }

    @Override
    public AuthenticatedUser unswitch(AuthenticatedUser user)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            UserNotSwitchedException, SessionTokenExpiredException {
        SessionToken token = tokens.get(user.getAuthenticationToken());
        validateToken(user.getUsername(), user.getAuthenticationToken());
        if (!isSwitched(token))
            throw new UserNotSwitchedException();
        String newToken = registerToken(token.getSuperUserName());
        Logger.getLogger(WsAuthenticationService.class)
                .info("User: " + token.getSuperUserName() + " no longer acts on behalf of " + token.getUserName());
        return new AuthenticatedUserImpl(token.getSuperUserName(), newToken);
    }

    private boolean isSwitched(SessionToken token) {
        return token.getSuperUserName() != null;
    }

    @Override
    public boolean isUserSwitched(AuthenticatedUser user)
            throws AccessDeniedException, UserNotAuthenticatedException, InvalidSessionTokenException,
            SessionTokenExpiredException {
        SessionToken token = tokens.get(user.getAuthenticationToken());
        validateToken(user.getUsername(), user.getAuthenticationToken());
        return isSwitched(token);
    }

    @Override
    public AuthenticatedUser getCompleteUser(AuthenticatedUser user) {
        SessionToken token = tokens.get(user.getAuthenticationToken());
        if (isSwitched(token)) {
            return new AuthenticatedUserImpl(user.getUsername(), token.getSuperUserName(),
                    user.getAuthenticationToken());
        } else {
            return user;
        }
    }

    @Override
    public AuthenticatedUser getAuthenticatedUser(String userName, String tokenId)
            throws AccessDeniedException, InvalidSessionTokenException, SessionTokenExpiredException {
        validateToken(userName, tokenId);
        return new AuthenticatedUserImpl(userName, tokenId);
    }

    @Override
    public void logout(AuthenticatedUser user)
            throws AccessDeniedException, InvalidSessionTokenException, SessionTokenExpiredException {
        removeToken(user);
    }

    private void removeToken(AuthenticatedUser user)
            throws AccessDeniedException, InvalidSessionTokenException, SessionTokenExpiredException {
        validateToken(user.getUsername(), user.getAuthenticationToken());
        user = getCompleteUser(user);
        String loggerUser = user.getSuperUserName() != null
                ? user.getSuperUserName() + " acting on behalf of " + user.getUsername()
                : user.getUsername();
        Logger.getLogger(WsAuthenticationService.class)
                .info("User logged out of ws (console): " + loggerUser);
        tokens.remove(user.getAuthenticationToken());
    }
}
