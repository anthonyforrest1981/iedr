package pl.nask.crs.security.authentication;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public interface WsAuthenticationService extends AuthenticationService {

    public void validateAndRefreshToken(AuthenticatedUser user)
            throws AccessDeniedException, InvalidSessionTokenException, SessionTokenExpiredException,
            PasswordExpiredException;

    public void validateAndRefreshToken(AuthenticatedUser user, boolean validatePasswordExpiry)
            throws AccessDeniedException, InvalidSessionTokenException, SessionTokenExpiredException,
            PasswordExpiredException;

    /**
     * Switches from super user to standard user
     *
     * @param userName
     * @param superUser
     * @return authentication token of switched user
     * @throws AccessDeniedException
     * @throws InvalidSessionTokenException
     * @throws InvalidUsernameOrPasswordException
     * @throws NoLoginPermissionException
     */
    public AuthenticatedUser switchUser(AuthenticatedUser superUser, String userName)
            throws AccessDeniedException, InvalidSessionTokenException, InvalidUsernameOrPasswordException,
            NoLoginPermissionException, SessionTokenExpiredException,
            PasswordExpiredException;

    /**
     * Switches back to super user
     *
     * @param user
     * @return authentication token of super user
     * @throws AccessDeniedException
     * @throws InvalidSessionTokenException
     * @throws UserNotSwitchedException
     */
    public AuthenticatedUser unswitch(AuthenticatedUser user)
            throws AccessDeniedException, InvalidSessionTokenException, UserNotSwitchedException,
            SessionTokenExpiredException;

    public boolean isUserSwitched(AuthenticatedUser user)
            throws AccessDeniedException, InvalidSessionTokenException, SessionTokenExpiredException;

    /**
     * Returns new AuthenticatedUser with superUserName field filled if user is switched, otherwise returns user.
     *
     * @param user
     * @return
     */
    public AuthenticatedUser getCompleteUser(AuthenticatedUser user) throws AccessDeniedException;

    /**
     * Authenticates super user in system and switches to standard user.
     * If authentication passes, switched AuthenticatedUser is returned.
     * Otherwise exception is thrown.
     *
     *
     * @param superUserName
     * @param userName
     * @param superUserPassword
     * @param validateExpiration
     * @param useLoginLock
     * @return
     * @throws AuthenticationException
     * @throws IllegalArgumentException
     */
    public AuthenticatedUser authenticateAndSwitchUser(String superUserName, String userName, String superUserPassword,
            boolean validateExpiration, String remoteAddress, boolean useLoginLock)
            throws AuthenticationException, IllegalArgumentException, AccessDeniedException;

    public AuthenticatedUser getAuthenticatedUser(String userName, String tokenId)
            throws AccessDeniedException, InvalidSessionTokenException, SessionTokenExpiredException;

    public void logout(AuthenticatedUser user)
            throws AccessDeniedException, InvalidSessionTokenException, SessionTokenExpiredException;
}
