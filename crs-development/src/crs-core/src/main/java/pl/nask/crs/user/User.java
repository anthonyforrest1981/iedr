package pl.nask.crs.user;

import java.util.*;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.permissions.PermissionDeniedException;
import pl.nask.crs.user.permissions.PermissionQuery;

public class User {
    private Logger log = Logger.getLogger(User.class);

    private String username;
    private String password;
    private Set<Group> permissionGroups;
    private boolean internal;
    private String salt;
    private Date passwordChangeDate;
    private boolean forcePasswordChange;
    private boolean useTwoFactorAuthentication;
    private String secret;
    private String name;
    private Map<String, Permission> permissions;

    public User() {}

    public User(String username, String password, Set<Group> permissionGroups, boolean internal, String salt,
            Date passwordChangeDate, boolean forcePasswordChange, boolean useTwoFactorAuthentication, String secret,
            String name, Map<String, Permission> userPermissions) {
        Validator.assertNotEmpty(username, "username");
        Validator.assertNotNull(permissionGroups, "permission groups");
        Validator.assertNotNull(userPermissions, "user permissions");
        this.username = username;
        this.password = password;
        this.permissionGroups = permissionGroups;
        this.internal = internal;
        this.salt = salt;
        this.passwordChangeDate = passwordChangeDate;
        this.forcePasswordChange = forcePasswordChange;
        this.useTwoFactorAuthentication = useTwoFactorAuthentication;
        this.secret = secret;
        this.name = name;
        this.permissions = userPermissions;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        this.passwordChangeDate = new Date();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Group> getPermissionGroups() {
        if (permissionGroups == null) {
            permissionGroups = new HashSet<>();
        }
        return permissionGroups;
    }

    public void setPermissionGroups(Set<Group> permissionGroups) {
        this.permissionGroups = permissionGroups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasGroup(String groupName) {
        Group g = new Group(groupName, Collections.EMPTY_SET);
        return permissionGroups.contains(g);
    }

    public boolean hasGroup(Level accessLevel) {
        return hasGroup(accessLevel.getName());
    }

    public void setPermissions(Map<String, Permission> permissions) {
        this.permissions = permissions;
    }

    public Map<String, Permission> getPermissions() {
        if (permissions == null) {
            permissions = new HashMap<>();
        }
        return permissions;
    }

    public void checkPermission(PermissionQuery query) throws PermissionDeniedException {
        PermissionDeniedException holdException = null;
        Set<Permission> permissionsToCheck = new HashSet<>();
        permissionsToCheck.addAll(getPermissionGroups());
        permissionsToCheck.addAll(getPermissions().values());
        for (Permission perm : permissionsToCheck) {
            try {
                if (perm.implies(query)) {
                    return;
                }
            } catch (PermissionDeniedException e) {
                log.debug(username + " : PermissionDeniedException for " + query);
                holdException = e;
            } catch (Exception e) {
                log.debug(username + " : Exception while checking permission " + query, e);
            }
        }
        log.debug(username + " : Not enough privileges for " + query);
        if (holdException != null) {
            throw holdException;
        } else {
            throw new PermissionDeniedException();
        }
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

    public boolean hasDefaultAccessOnly() {
        return (permissionGroups.size() == 1 && hasGroup(Level.Default));
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
