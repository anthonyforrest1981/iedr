package pl.nask.crs.user.dao.ibatis;

import java.util.Date;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.token.PasswordResetToken;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.UserDAO;
import pl.nask.crs.user.dao.ibatis.objects.InternalUser;

public class ConvertingUserDAO extends ConvertingGenericDAO<InternalUser, User, String> implements UserDAO {

    private InternalUserIBatisDAO dao;

    public ConvertingUserDAO(InternalUserIBatisDAO internalDao, Converter<InternalUser, User> internalConverter) {
        super(internalDao, internalConverter);
        Validator.assertNotNull(internalDao, "internal dao");
        this.dao = internalDao;
    }

    @Override
    public void addUserPermission(String nicHandleId, String permissionName) {
        dao.addUserPermission(nicHandleId, permissionName);
    }

    @Override
    public void addPasswordReset(String nicHandleId, String hash, Date now, int attemptsLeft, String ip) {
        dao.addPasswordReset(nicHandleId, hash, now, attemptsLeft, ip);
    }

    @Override
    public void removeUserPermission(String nicHandleId, String permissionName) {
        dao.removeUserPermission(nicHandleId, permissionName);
    }

    @Override
    public void decreaseTokenAttempts(String token) {
        dao.decreaseTokenAttempts(token);
    }

    @Override
    public void invalidateToken(String token) {
        dao.invalidateToken(token);
    }

    @Override
    public PasswordResetToken getToken(String token) {
        return dao.getToken(token);
    }

    @Override
    public void cleanupResetPassword(Date date) {
        dao.cleanupResetPassword(date);
    }

}
