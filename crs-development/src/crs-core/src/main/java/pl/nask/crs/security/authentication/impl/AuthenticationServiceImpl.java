package pl.nask.crs.security.authentication.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import pl.nask.crs.commons.HashAlgorithm;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.config.GAConfig;
import pl.nask.crs.commons.config.LoginLockoutConfig;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.security.Cause;
import pl.nask.crs.security.LoginAttempt;
import pl.nask.crs.security.authentication.*;
import pl.nask.crs.security.authentication.googleauthenticator.AuthenticationCodesVerifier;
import pl.nask.crs.security.dao.LoginAttemptDAO;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.UserDAO;

/**
 * Class serves to authenticate a user in the system. Uses the list of
 * HashAlgorithm interface to check a password (check against every algorithm
 * from the list until the password matches) Uses the UserDAO interface to get
 * system information about a user.
 *
 * @author Marianna Mysiorska
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDAO userDAO;
    private final List<HashAlgorithm> algorithms;
    protected final ApplicationConfig applicationConfig;
    private final LoginAttemptDAO loginAttemptDAO;

    public AuthenticationServiceImpl(UserDAO userDAO, List<HashAlgorithm> algorithms,
            ApplicationConfig applicationConfig, LoginAttemptDAO loginAttemptDAO) {
        this.algorithms = algorithms;
        this.userDAO = userDAO;
        this.applicationConfig = applicationConfig;
        this.loginAttemptDAO = loginAttemptDAO;
    }

    @Override
    public AuthenticatedUser authenticate(String username, String password, boolean validateExpiration,
            String remoteAddress, boolean useLoginLock, String code, boolean useTfa, String systemDiscriminator)
            throws InvalidUsernameOrPasswordException, PasswordExpiredException, LoginLockedException,
            NoLoginPermissionException, GoogleAuthenticationException {
        Validator.assertNotNull(username, "username");
        Validator.assertNotNull(password, "password");
        username = username.trim().toUpperCase();

        LoginAttempt lastAttempt = loginAttemptDAO.getLastAttemptByNic(username);
        int failedAttemptsCount = lastAttempt == null ? 0 : lastAttempt.getFailureCount();
        Date attemptDate = lastAttempt == null ? null : lastAttempt.getDate();
        loginLock(useLoginLock, failedAttemptsCount, attemptDate);

        User user = userDAO.get(username);
        if (user == null) {
            loginAttemptDAO.createAttempt(LoginAttempt.newFailedInstance(username, remoteAddress, Cause.INVALID_NIC,
                    ++failedAttemptsCount));
            Logger.getLogger(AuthenticationServiceImpl.class).info("Failed login: unknown user " + username);
            throw new InvalidUsernameOrPasswordException(username);
        }

        AuthenticatedUser u = authenticateWithPassword(user, password, validateExpiration, remoteAddress,
                failedAttemptsCount, systemDiscriminator);

        if (useTfa && user.isUseTwoFactorAuthentication()) {
            Validator.assertNotEmpty(user.getSecret(), "secret");
            if (Validator.isEmpty(code)) {
                Logger.getLogger(AuthenticationServiceImpl.class).info("Failed login: missing tfa pin for " + username);
                throw new GoogleAuthenticationException("Invalid pin");
            }
            GAConfig gaConfig = applicationConfig.getGoogleAuthenticationConfig();
            if (!AuthenticationCodesVerifier.verifyCode(user.getSecret(), code, gaConfig.getPastIntervals(),
                    gaConfig.getFutureIntervals())) {
                Logger.getLogger(AuthenticationServiceImpl.class).info("Failed login: invalid tfa pin for " + username);
                loginAttemptDAO.createAttempt(LoginAttempt.newFailedInstance(username, remoteAddress,
                        Cause.INVALID_GA_PIN, ++failedAttemptsCount));
                throw new GoogleAuthenticationException("Invalid pin");
            }
        }

        Logger.getLogger(AuthenticationServiceImpl.class).info("Successful login: user " + username);
        loginAttemptDAO.createAttempt(LoginAttempt.newSuccessInstance(username, remoteAddress));
        return u;
    }

    private void loginLock(boolean useLoginLock, int failedAttemptsCount, Date attemptDate)
            throws LoginLockedException {
        if (useLoginLock && failedAttemptsCount > 0) {
            int lockoutTimeInSeconds = getLockoutPeriod(failedAttemptsCount);

            Date currentDate = new Date();
            Date lockoutExpirationDate = DateUtils.addSeconds(attemptDate, lockoutTimeInSeconds);

            if (currentDate.before(lockoutExpirationDate)) {
                long secondsLeft = (lockoutExpirationDate.getTime() - currentDate.getTime()) / 1000;
                throw new LoginLockedException(secondsLeft);
            }
        }
    }

    private int getLockoutPeriod(int failedAttemptsCount) {
        LoginLockoutConfig config = applicationConfig.getLoginLockoutConfig();
        int lockoutTimeInSeconds = config.getInitialLockoutPeriod();
        if (failedAttemptsCount > 1) {
            int lockoutIncreaseFactor = config.getLockoutIncreaseFactor();
            int maximumLockoutPeriod = config.getMaximumLockoutPeriod();
            for (int i = 1; i < failedAttemptsCount; i++) {
                lockoutTimeInSeconds *= lockoutIncreaseFactor;
                if (lockoutTimeInSeconds > maximumLockoutPeriod) {
                    lockoutTimeInSeconds = maximumLockoutPeriod;
                    break;
                }
            }
        }
        return lockoutTimeInSeconds;
    }

    private AuthenticatedUser authenticateWithPassword(User user, String password, boolean validateExpiration,
            String remoteAddress, int failedAttemptsCount, String systemDiscriminator)
            throws NoLoginPermissionException, PasswordExpiredException, InvalidUsernameOrPasswordException {
        for (HashAlgorithm algorithm : algorithms) {
            String encodedPassword = algorithm.hashString(password, user.getSalt());
            if (encodedPassword.equals(user.getPassword())) {
                if (!LoginPermissionChecker.checkLoginPermission(user, systemDiscriminator)) {
                    Logger.getLogger(AuthenticationServiceImpl.class)
                            .info("Failed login: no login permission for " + user.getUsername());
                    throw new NoLoginPermissionException(user.getUsername());
                }
                if (validateExpiration && !isPasswordActive(user.getUsername())) {
                    Logger.getLogger(AuthenticationServiceImpl.class)
                            .info("Failed login: password expired for " + user.getUsername());
                    throw new PasswordExpiredException();
                }
                return new AuthenticatedUserImpl(user.getUsername());
            }
        }
        Logger.getLogger(AuthenticationServiceImpl.class).info(
                "Failed login: invalid password for " + user.getUsername());
        loginAttemptDAO.createAttempt(LoginAttempt
                .newFailedInstance(user.getUsername(), remoteAddress, Cause.INVALID_PASSWORD, ++failedAttemptsCount));
        throw new InvalidUsernameOrPasswordException(user.getUsername());
    }

    protected boolean isPasswordActive(String userName) {
        User user = userDAO.get(userName);
        if (user == null || user.isForcePasswordChange()) {
            return false;
        }
        Date lastChange = user.getPasswordChangeDate();
        int period = applicationConfig.getPasswordExpiryPeriod();
        Date passwordExpiringDate = DateUtils.addDays(DateUtils.truncate(lastChange, Calendar.DATE), period);
        Date now = new Date();
        boolean expiresInTheFuture = passwordExpiringDate.after(now);
        return expiresInTheFuture;
    }
}
