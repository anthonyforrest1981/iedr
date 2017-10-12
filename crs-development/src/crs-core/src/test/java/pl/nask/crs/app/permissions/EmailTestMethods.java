package pl.nask.crs.app.permissions;

import pl.nask.crs.security.authentication.AccessDeniedException;

public interface EmailTestMethods {
    void getEmailGroup() throws AccessDeniedException;

    void updateEmailGroup() throws AccessDeniedException;

    void createEmailGroup() throws AccessDeniedException;

    void deleteEmailGroup() throws AccessDeniedException;

    void getAllEmailGroups() throws AccessDeniedException;

    void searchForEmailGroups() throws AccessDeniedException;

    void getEmailTemplate() throws AccessDeniedException;

    void saveEditableFieldsInEmailTemplate() throws AccessDeniedException;

    void searchForEmailTemplates() throws AccessDeniedException;


}
