package pl.nask.crs.app.permissions;

import pl.nask.crs.security.authentication.AccessDeniedException;

public interface AccountTestMethods {

    void getAccount() throws AccessDeniedException;

    void getNotOwnAccount() throws AccessDeniedException;

    void historyAccount() throws AccessDeniedException;

    void alterStatusAccount() throws AccessDeniedException;

    void saveAccount() throws AccessDeniedException;

    void saveAccountFlagChanged() throws AccessDeniedException;

    void createAccount() throws AccessDeniedException;

}
