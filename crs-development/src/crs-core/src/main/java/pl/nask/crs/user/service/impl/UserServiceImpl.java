package pl.nask.crs.user.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;

import pl.nask.crs.commons.HashAlgorithm;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.security.dao.LoginAttemptDAO;
import pl.nask.crs.token.PasswordResetToken;
import pl.nask.crs.token.PasswordResetTokenExpiredException;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.NRCLevel;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.HistoricalUserDAO;
import pl.nask.crs.user.dao.UserDAO;
import pl.nask.crs.user.exceptions.InvalidOldPasswordException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;
import pl.nask.crs.user.googleauthenticator.SecretGenerator;
import pl.nask.crs.user.service.AuthorizationGroupsFactory;
import pl.nask.crs.user.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDAO dao;
    private HistoricalUserDAO historicalUserDao;
    private HashAlgorithm hashAlgorithm;
    private AuthorizationGroupsFactory groupsFactory;
    private ApplicationConfig applicationConfig;
    private LoginAttemptDAO loginAttemptDao;

    public UserServiceImpl(UserDAO dao, HistoricalUserDAO historicalUserDao,
            HashAlgorithm hashAlgorithm, AuthorizationGroupsFactory groupsFactory, ApplicationConfig applicationConfig,
            LoginAttemptDAO loginAttemptDao) {
        Validator.assertNotNull(dao, "user dao");
        Validator.assertNotNull(historicalUserDao, "historical user service");
        Validator.assertNotNull(hashAlgorithm, "hashAlgorithm");
        Validator.assertNotNull(groupsFactory, "groups factory");
        Validator.assertNotNull(applicationConfig, "applicationConfig");
        Validator.assertNotNull(loginAttemptDao, "login attempt dao");
        this.dao = dao;
        this.historicalUserDao = historicalUserDao;
        this.hashAlgorithm = hashAlgorithm;
        this.groupsFactory = groupsFactory;
        this.applicationConfig = applicationConfig;
        this.loginAttemptDao = loginAttemptDao;
    }

    @Override
    public void changePassword(String nicHandleId, String plainNewPassword, OpInfo opInfo)
            throws PasswordAlreadyExistsException {
        changePassword(nicHandleId, plainNewPassword, false, opInfo);
    }

    @Override
    public void changePassword(String nicHandleId, String plainNewPassword, boolean forcePasswordChange, OpInfo opInfo)
            throws PasswordAlreadyExistsException {
        Validator.assertNotNull(nicHandleId, "nic handle id");
        Validator.assertNotNull(plainNewPassword, "new password");
        nicHandleId = nicHandleId.trim().toUpperCase();
        String salt = hashAlgorithm.getSalt();
        String hashedNewPassword = hashAlgorithm.hashString(plainNewPassword, salt);
        User user = getUser(nicHandleId);
        checkNotTheSame(user, plainNewPassword);
        user.setPassword(hashedNewPassword);
        user.setSalt(salt);
        user.setForcePasswordChange(forcePasswordChange);
        storeUser(user, opInfo);
    }

    @Override
    public void changePassword(String nicHandleId, String oldPassword, String newPassword, OpInfo opInfo)
            throws PasswordAlreadyExistsException, InvalidOldPasswordException {
        Validator.assertNotNull(nicHandleId, "nic handle id");
        Validator.assertNotNull(oldPassword, "old password");
        Validator.assertNotNull(opInfo, "opInfo");
        validateOldPassword(nicHandleId, oldPassword);
        changePassword(nicHandleId, newPassword, opInfo);
    }

    private void validateOldPassword(String nicHandleId, String oldPassword) throws InvalidOldPasswordException {
        User user = dao.get(nicHandleId);
        String encodedOldPassword = hashAlgorithm.hashString(oldPassword, user.getSalt());
        if (!encodedOldPassword.equals(user.getPassword())) {
            throw new InvalidOldPasswordException();
        }
    }

    @Override
    public void changePermissionGroups(String nicHandleId, Set<Group> permissionGroups, OpInfo opInfo) {
        Validator.assertNotNull(nicHandleId, "user");
        Validator.assertNotNull(permissionGroups, "permission groups");
        Validator.assertNotNull(opInfo, "opInfo");
        User user = getUser(nicHandleId);
        user.setPermissionGroups(permissionGroups);
        storeUser(user, opInfo);
    }

    private void checkNotTheSame(User user, String plainNewPass) throws PasswordAlreadyExistsException {
        String currentSalt = user.getSalt();
        if (currentSalt == null) {
            return; // no check is possible
        }
        String hashedNewPassWithOldSalt = hashAlgorithm.hashString(plainNewPass, user.getSalt());
        if (hashedNewPassWithOldSalt.equals(user.getPassword())) {
            throw new PasswordAlreadyExistsException();
        }
    }

    @Override
    public void addUserPermission(String nicHandleId, String permissionName) {
        dao.addUserPermission(nicHandleId, permissionName);
    }

    @Override
    public void removeUserPermission(String nicHandleId, String permissionName) {
        dao.removeUserPermission(nicHandleId, permissionName);
    }

    @Override
    public void resetPassword(String nicHandleId, String token, String ipAddress, OpInfo opInfo) {
        Date now = new Date();
        Date expires = DateUtils.addMinutes(now, applicationConfig.getPasswordResetTokenExpiry());
        int attemptsLeft = applicationConfig.getPasswordResetTokenAttempts();
        String hash = hashToken(token);
        dao.addPasswordReset(nicHandleId, hash, expires, attemptsLeft, ipAddress);
    }

    private String hashToken(String token) {
        return hashAlgorithm.hashString(token, "jSe9VrjJRosMn/hKdT6MPu");
    }

    @Override
    public NRCLevel getUserLevel(String nicHandleId) {
        User u = dao.get(nicHandleId);
        if (u.hasGroup(Level.SuperRegistrar)) {
            return NRCLevel.SUPER_REGISTRAR;
        } else if (u.hasGroup(Level.Registrar)) {
            return NRCLevel.REGISTRAR;
        } else if (u.hasGroup(Level.Direct)) {
            return NRCLevel.DIRECT;
        } else {
            return NRCLevel.TAC;
        }
    }

    @Override
    public void addUserToGroup(String nicHandleId, Level level, OpInfo opInfo) {
        User user = dao.get(nicHandleId);
        Set<Group> permissionGroups = user == null ? new HashSet<Group>() : user.getPermissionGroups();
        Set<Group> levelGroups = groupsFactory.getGroups(level.getLevel());
        permissionGroups.addAll(levelGroups);
        changePermissionGroups(nicHandleId, permissionGroups, opInfo);
    }

    @Override
    public void setUserGroup(String nicHandleId, Level level, OpInfo opInfo) {
        Set<Group> permissionGroups = groupsFactory.getGroups(level.getLevel());
        changePermissionGroups(nicHandleId, permissionGroups, opInfo);
    }

    @Override
    public String changeTfa(String nicHandleId, boolean useTfa, OpInfo opInfo) {
        User user = getUser(nicHandleId);
        user.setUseTwoFactorAuthentication(useTfa);
        storeUser(user, opInfo);
        if (useTfa) {
            return generateSecret(nicHandleId, opInfo);
        }
        return null;
    }

    @Override
    public String generateSecret(String nicHandleId, OpInfo opInfo) {
        Validator.assertNotEmpty(nicHandleId, "nic handle id");
        String newSecret = SecretGenerator.generateSecret();
        User user = getUser(nicHandleId);
        user.setSecret(newSecret);
        storeUser(user, opInfo);
        return newSecret;
    }

    @Override
    public User getUserFromToken(String token, String nicHandleId) throws PasswordResetTokenExpiredException {
        String hashedToken = hashToken(token);
        PasswordResetToken t = dao.getToken(hashedToken);
        if (t == null || !t.isValid(nicHandleId)) {
            throw new PasswordResetTokenExpiredException();
        }
        return getUser(t.getNicHandle());
    }

    @Override
    public void decreaseTokenAttempts(String token) {
        String hashedToken = hashToken(token);
        dao.decreaseTokenAttempts(hashedToken);
    }

    @Override
    public void invalidateToken(String token) {
        String hashedToken = hashToken(token);
        dao.invalidateToken(hashedToken);
    }

    private void create(User user, OpInfo opInfo) {
        dao.create(user);
        updateUserAndHistory(user, opInfo);
    }

    private void updateUserAndHistory(User user, OpInfo opInfo) {
        long changeId = historicalUserDao.create(user, new Date(), opInfo.getActorName());
        dao.updateUsingHistory(changeId);
    }

    private User getUser(String username) {
        User user = dao.get(username);
        if (user == null) {
            user = new NewUser(username);
        }
        return user;
    }

    private void storeUser(User user, OpInfo opInfo) {
        if (user instanceof NewUser) {
            create(user, opInfo);
        } else {
            updateUserAndHistory(user, opInfo);
        }
    }

    private class NewUser extends User {

        public NewUser(String username) {
            super();
            setUsername(username);
        }

    }


    @Override
    public void cleanupResetPassword() {
        int period = applicationConfig.getResetPasswordCleanUpPeriod();
        Date date = DateUtils.addDays(new Date(), -period);
        dao.cleanupResetPassword(date);
    }

    @Override
    public void cleanupLoginAttempts() {
        int period = applicationConfig.getLoginAttemptCleanUpPeriod();
        Date date = DateUtils.addDays(new Date(), -period);
        loginAttemptDao.cleanupAttempts(date);
    }
}
