package pl.nask.crs.app.permissions;

import pl.nask.crs.security.authentication.AccessDeniedException;

public interface ConfigTestMethods {

    void getEntries() throws AccessDeniedException;

    void updateEntry() throws AccessDeniedException;

    void createEntry() throws AccessDeniedException;

    void getEntry() throws AccessDeniedException;

}
