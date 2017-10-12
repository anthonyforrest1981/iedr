package pl.nask.crs.app.document;

import java.util.List;

import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.documents.*;
import pl.nask.crs.documents.exception.*;
import pl.nask.crs.documents.search.DocumentSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public interface DocumentAppService {

    public DocumentSettings getDocumentSettings(AuthenticatedUser user) throws AccessDeniedException;

    LimitedSearchResult<Document> getNewDocuments(AuthenticatedUser user, int offset, int limit,
            List<SortCriterion> sortBy)
            throws AccessDeniedException, NoSuchDirectoryException, WrongFaxFileExtensionException;

    LimitedSearchResult<Document> findDocuments(AuthenticatedUser user, DocumentSearchCriteria criteria, int offset,
            int limit, List<SortCriterion> orderBy) throws AccessDeniedException;

    Document get(AuthenticatedUser user, Long id) throws AccessDeniedException;

    void add(AuthenticatedUser user, Document document)
            throws AccessDeniedException, EmailSendingException, DocumentGeneralException, NoSuchDirectoryException,
            NoSuchDocumentException, DocumentFileMovingException;

    void deleteDocumentFile(AuthenticatedUser user, Document document)
            throws AccessDeniedException, DocumentFileMovingException, NoSuchDirectoryException,
            WrongFaxFileExtensionException, NoSuchDocumentException;

    boolean documentFileExists(AuthenticatedUser user, Document document)
            throws AccessDeniedException, NoSuchDirectoryException, WrongFaxFileExtensionException;

    String getPath(AuthenticatedUser user, Document document) throws AccessDeniedException;

    void update(AuthenticatedUser user, Document document) throws AccessDeniedException, DocumentGeneralException;

    List<UploadResult> handleUpload(AuthenticatedUser user, List<? extends DocumentUpload> uploads,
            DocumentPurpose documentPurpose) throws AccessDeniedException, DocumentGeneralException;

    void handleMailUpload(AuthenticatedUser user, String replyTo, String mailContent,
            List<? extends UploadFilename> attachmentFilenames) throws AccessDeniedException;
}
