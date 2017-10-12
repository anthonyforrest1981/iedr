package pl.nask.crs.user.dao.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.token.PasswordResetToken;
import pl.nask.crs.user.dao.ibatis.objects.InternalUser;

public class InternalUserIBatisDAO extends GenericIBatisDAO<InternalUser, String> {

    public InternalUserIBatisDAO() {
        setGetQueryId("user.getUserForUsername");
        setFindQueryId("user.findUser");
        setCountFindQueryId("user.countTotalSearchResult");
        setCreateQueryId("user.createUser");
        setUpdateUsingHistoryQueryId("user.updateUsingHistory");
    }

    public void removeUserPermission(String nicHandleId, String permissionName) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("nicHandle", nicHandleId);
        param.put("permissionName", permissionName);
        performInsert("user.removeUserPermission", param);
    }

    public void addUserPermission(String nicHandleId, String permissionName) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("nicHandle", nicHandleId);
        param.put("permissionName", permissionName);
        performInsert("user.addUserPermission", param);
    }

    public void addPasswordReset(String nicHandleId, String hash, Date now, int attemptsLeft, String ip) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("nicHandleId", nicHandleId);
        map.put("hash", hash);
        map.put("date", now);
        map.put("attemptsLeft", attemptsLeft);
        map.put("ip", ip);
        performInsert("user.addPasswordReset", map);
    }

    public void decreaseTokenAttempts(String token) {
        performUpdate("user.decreaseTokenAttempts", token);
    }

    public void invalidateToken(String token) {
        performUpdate("user.invalidateToken", token);
    }

    public PasswordResetToken getToken(String token) {
        return performQueryForObject("user.getToken", token);
    }

    public void cleanupResetPassword(Date date) {
        performDelete("user.cleanupResetPassword", date);
    }

}
