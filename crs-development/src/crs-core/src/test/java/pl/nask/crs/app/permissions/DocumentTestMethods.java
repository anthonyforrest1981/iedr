package pl.nask.crs.app.permissions;

import pl.nask.crs.security.authentication.AccessDeniedException;

public interface DocumentTestMethods {

    void addDocument() throws AccessDeniedException;

    void deleteDocumentFile() throws AccessDeniedException;

    void documentFileExists() throws AccessDeniedException;

    void findDocuments() throws AccessDeniedException;

    void getDocument() throws AccessDeniedException;

    void getDocumentSettings() throws AccessDeniedException;

    void getNewDocuments() throws AccessDeniedException;

    void getPath() throws AccessDeniedException;

    void handleMailUpload() throws AccessDeniedException;

    void handleUpload() throws AccessDeniedException;

    void updateDocument() throws AccessDeniedException;

}
