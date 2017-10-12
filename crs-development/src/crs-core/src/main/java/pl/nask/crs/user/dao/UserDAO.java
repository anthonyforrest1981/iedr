package pl.nask.crs.user.dao;

import java.util.Date;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.token.PasswordResetToken;
import pl.nask.crs.user.User;

public interface UserDAO extends GenericDAO<User, String> {

    void removeUserPermission(String nicHandleId, String permissionName);

    void addPasswordReset(String nicHandleId, String hash, Date now, int attemptsLeft, String ip);

    void addUserPermission(String nicHandleId, String permissionName);

    PasswordResetToken getToken(String token);

    void decreaseTokenAttempts(String token);

    void invalidateToken(String token);

    void cleanupResetPassword(Date date);

}
