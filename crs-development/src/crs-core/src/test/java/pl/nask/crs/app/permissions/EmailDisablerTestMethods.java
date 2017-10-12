package pl.nask.crs.app.permissions;

import pl.nask.crs.security.authentication.AccessDeniedException;

public interface EmailDisablerTestMethods {

    void getAllEmailDisablersFor() throws AccessDeniedException;

    void getAllEmailDisablersOfEmailGroup() throws AccessDeniedException;

    void getAllEmailDisablersOfTemplate() throws AccessDeniedException;

    void modifyEmailDisablerSuppressionMode() throws AccessDeniedException;

}
