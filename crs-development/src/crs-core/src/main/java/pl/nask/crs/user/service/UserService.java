package pl.nask.crs.user.service;

import java.util.Set;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.token.PasswordResetTokenExpiredException;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.NRCLevel;
import pl.nask.crs.user.User;
import pl.nask.crs.user.exceptions.InvalidOldPasswordException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

public interface UserService {

    void changePassword(String nicHandleId, String newPassword, OpInfo opInfo)
            throws PasswordAlreadyExistsException;

    void changePassword(String nicHandleId, String newPassword, boolean forcePasswordChange, OpInfo opInfo)
            throws PasswordAlreadyExistsException;

    void changePassword(String nicHandleId, String oldPassword, String newPassword, OpInfo opInfo)
            throws PasswordAlreadyExistsException, InvalidOldPasswordException;

    void changePermissionGroups(String nicHandleId, Set<Group> permissionGroups, OpInfo opInfo);

    void addUserPermission(String nicHandleId, String permissionName);

    void resetPassword(String nicHandleId, String token, String ipAddress, OpInfo opInfo);

    void removeUserPermission(String nicHandleId, String permissionName);

    NRCLevel getUserLevel(String nicHandleId);

    void addUserToGroup(String nicHandleId, Level level, OpInfo opInfo);

    void setUserGroup(String nicHandleId, Level level, OpInfo opInfo);

    /**
     * changes TFA flag for user, if useTfa=true returns new secret otherwise null
     */
    String changeTfa(String nicHandleId, boolean useTfa, OpInfo opInfo);

    String generateSecret(String nicHandleId, OpInfo opInfo);

    User getUserFromToken(String token, String nicHandleId) throws PasswordResetTokenExpiredException;

    void decreaseTokenAttempts(String token);

    void invalidateToken(String token);

    void cleanupResetPassword();

    void cleanupLoginAttempts();
}
