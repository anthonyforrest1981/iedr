package pl.nask.crs.app.permissions;

import pl.nask.crs.security.authentication.AccessDeniedException;

public interface NicHandleTestMethods {

    void getNicHandle() throws AccessDeniedException;

    void getNotOwnNicHandle() throws AccessDeniedException;

    void historyNicHandle() throws AccessDeniedException;

    void historyNotOwnNicHandle() throws AccessDeniedException;

    void alterStatusNicHandle() throws AccessDeniedException;

    void modifyNicHandleOwnAccount() throws AccessDeniedException;

    void modifyNotOwnNicHandle() throws AccessDeniedException;

    void modifyNicHandleAsHostmaster() throws AccessDeniedException;

    void saveNewPassword() throws AccessDeniedException;

    void saveNewPasswordNotOwn() throws AccessDeniedException;

    void changePassword() throws AccessDeniedException;

    void changePasswordNotOwn() throws AccessDeniedException;

    void resetPassword() throws AccessDeniedException;

    void resetPasswordNotOwn() throws AccessDeniedException;

    void createNicHandle() throws AccessDeniedException;

    void createNicHandleOwnAccount() throws AccessDeniedException;

    void getDefaults() throws AccessDeniedException;

    void saveDefaults() throws AccessDeniedException;

    void removeUserPermission() throws AccessDeniedException;

    void addUserPermission() throws AccessDeniedException;

    void changeTfaAsHostmaster() throws AccessDeniedException;

    void canBeABillingContact() throws AccessDeniedException;

    void canBeABillingContactNotOwn() throws AccessDeniedException;

    void cleanupLoginAttempts() throws AccessDeniedException;

    void cleanupResetPassword() throws AccessDeniedException;

    void removeDeletedNichandles() throws AccessDeniedException;

    void searchForNicHandles() throws AccessDeniedException;

    void searchForNicHandlesNotOwn() throws AccessDeniedException;

}
