package pl.nask.crs.user.dao.ibatis.objects;

import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.utils.Validator;

public class InternalUser {

    private String username;
    private String password;
    private int level;
    private boolean internal;
    private String salt;
    private Date passwordChangeDate;
    private boolean forcePasswordChange;
    private boolean useTwoFactorAuthentication;
    private String secret;
    private String name;
    private List<String> permissionNames;

    public InternalUser() {

    }

    public InternalUser(String username, String password, int level, boolean internal, String salt,
            Date passwordChangeDate, boolean forcePasswordChange, boolean useTwoFactorAuthentication,
            String secret, String name, List<String> permissionNames) {
        Validator.assertNotEmpty(username, "username");
        this.username = username;
        this.password = password;
        this.level = level;
        this.internal = internal;
        this.salt = salt;
        this.passwordChangeDate = passwordChangeDate;
        this.forcePasswordChange = forcePasswordChange;
        this.useTwoFactorAuthentication = useTwoFactorAuthentication;
        this.secret = secret;
        this.name = name;
        this.permissionNames = permissionNames;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<String> getPermissionNames() {
        return permissionNames;
    }

    public void setPermissionNames(List<String> permissionNames) {
        this.permissionNames = permissionNames;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isUseTwoFactorAuthentication() {
        return useTwoFactorAuthentication;
    }

    public void setUseTwoFactorAuthentication(boolean useTwoFactorAuthentication) {
        this.useTwoFactorAuthentication = useTwoFactorAuthentication;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public boolean isInternal() {
        return internal;
    }

    public void setInternal(boolean internal) {
        this.internal = internal;
    }

    @Override
    public boolean equals(Object obj) {
        if (username == null)
            return false;
        if (!(obj instanceof InternalUser))
            return false;

        return username.equals(((InternalUser) obj).getUsername());
    }

    @Override
    public int hashCode() {
        return username == null ? 0 : username.hashCode();
    }

    public Date getPasswordChangeDate() {
        return passwordChangeDate;
    }

    public void setPasswordChangeDate(Date passwordChangeDate) {
        this.passwordChangeDate = passwordChangeDate;
    }

    public boolean isForcePasswordChange() {
        return forcePasswordChange;
    }

    public void setForcePasswordChange(boolean forcePasswordChange) {
        this.forcePasswordChange = forcePasswordChange;
    }
}
