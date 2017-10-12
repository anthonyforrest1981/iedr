package pl.nask.crs.app.permissions;

import pl.nask.crs.security.authentication.AccessDeniedException;

public interface UserTestMethods {

    void changePermissionGroups() throws AccessDeniedException;

    void changeTfaAsUser() throws AccessDeniedException;

    void getUserHistory() throws AccessDeniedException;

    void getUser() throws AccessDeniedException;

    void getUserLevel() throws AccessDeniedException;

    void isTfaUsed() throws AccessDeniedException;

}
