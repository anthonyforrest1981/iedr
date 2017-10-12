package pl.nask.crs.documents.service;

import java.util.List;
import java.util.Set;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.documents.Document;
import pl.nask.crs.documents.DocumentFile;
import pl.nask.crs.documents.DocumentPurpose;
import pl.nask.crs.documents.UploadStatus;
import pl.nask.crs.documents.exception.*;
import pl.nask.crs.documents.search.DocumentSearchCriteria;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * @author Marianna Mysiorska
 * @author Piotr Tkaczyk
 */
public interface DocumentService {

    LimitedSearchResult<Document> getNewDocuments(int offset, int limit, List<SortCriterion> sortBy)
            throws NoSuchDirectoryException, WrongFaxFileExtensionException;

    LimitedSearchResult<Document> findDocuments(DocumentSearchCriteria criteria, int offset, int limit,
            List<SortCriterion> orderBy);

    SearchResult<Document> findDocuments(DocumentSearchCriteria criteria, List<SortCriterion> orderBy);

    boolean hasDocumentsForDomain(String domainName);

    boolean hasDocumentsForBuyRequest(long buyRequestId);

    Document get(Long id);

    void add(Document document, boolean fileOkToMove)
            throws DocumentGeneralException, NoSuchDirectoryException, NoSuchDocumentException,
            DocumentFileMovingException;

    void deleteDocumentFile(DocumentFile document)
            throws DocumentFileMovingException, NoSuchDirectoryException, WrongFaxFileExtensionException,
            NoSuchDocumentException;

    void deleteFile(DocumentFile docFile)
            throws NoSuchDirectoryException, DocumentFileMovingException, NoSuchDocumentException;

    boolean documentFileExists(Document document) throws NoSuchDirectoryException, WrongFaxFileExtensionException;

    String getPath(Document document);

    void update(Document document) throws DocumentGeneralException;

    UploadStatus uploadedDocumentFile(AuthenticatedUser user, boolean checkPermissions, DocumentPurpose purpose,
            DocumentFile docFile, List<String> domainNames, Long buyRequestId) throws DocumentGeneralException;

    Set<String> getKnownDomains(List<String> domainNames);
}
