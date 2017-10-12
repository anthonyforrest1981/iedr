package pl.nask.crs.documents.service;

import java.io.File;
import java.util.*;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.search.AccountSearchCriteria;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.authorization.queries.BuyRequestPermissionQuery;
import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.CollectionUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.documents.*;
import pl.nask.crs.documents.dao.ibatis.DocumentDAO;
import pl.nask.crs.documents.exception.*;
import pl.nask.crs.documents.search.DocumentSearchCriteria;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.services.DomainSearchService;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.exceptions.BuyRequestNotFoundException;
import pl.nask.crs.secondarymarket.services.SecondaryMarketService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.user.permissions.PermissionDeniedException;

public class DocumentServiceImpl implements DocumentService {

    private final DocumentDAO dao;
    private final IncomingDocumentsManager incomingDocumentsManager;
    private final AccountSearchService accountSearchService;
    private final ApplicationConfig appConfig;
    private final DomainSearchService domainSearchService;
    private final TicketSearchService ticketSeachService;
    private final SecondaryMarketService secondaryMarketService;
    private final AuthorizationService authorizationService;

    public DocumentServiceImpl(DocumentDAO dao, IncomingDocumentsManager incomingDocumentsManager,
            AccountSearchService accountSearchService, ApplicationConfig appConfig,
            DomainSearchService domainSearchService, TicketSearchService ticketSearchService,
            SecondaryMarketService secondaryMarketService, AuthorizationService authorizationService) {
        Validator.assertNotNull(dao, "Document dao cannot be null.");
        Validator.assertNotNull(incomingDocumentsManager, "Documents manager cannot be null.");
        Validator.assertNotNull(accountSearchService, "Account search service");
        Validator.assertNotNull(appConfig, "Application config");
        this.dao = dao;
        this.incomingDocumentsManager = incomingDocumentsManager;
        this.accountSearchService = accountSearchService;
        this.appConfig = appConfig;
        this.domainSearchService = domainSearchService;
        this.ticketSeachService = ticketSearchService;
        this.secondaryMarketService = secondaryMarketService;
        this.authorizationService = authorizationService;
    }

    @Override
    public LimitedSearchResult<Document> getNewDocuments(int offset, int limit, List<SortCriterion> sortBy)
            throws NoSuchDirectoryException, WrongFaxFileExtensionException {
        List<DocumentFile> incomingDocuments = incomingDocumentsManager.getNewDocumentFiles();
        int filesCount = incomingDocumentsManager.getNewDocumentFilesCount();
        List<Document> result = new ArrayList<Document>(incomingDocuments.size());
        for (DocumentFile documentFile : incomingDocuments) {
            Document document = new Document(documentFile, null, "", null, new ArrayList<String>(), null, null);
            document.setDate(documentFile.getModificationDate());
            result.add(document);
        }
        if (sortBy != null && sortBy.size() != 0)
            Collections.sort(result, new DocumentComparator(sortBy));

        int calculatedLimit = Math.min(limit, filesCount);
        List<Document> resultLimited = new ArrayList<Document>(calculatedLimit);

        for (int i = 0; i < calculatedLimit; i++) {
            int index = offset + i;
            if (index < result.size()) {
                resultLimited.add(result.get(index));
            }
        }

        return new LimitedSearchResult<>(null, calculatedLimit, offset, resultLimited, filesCount);
    }

    @Override
    public LimitedSearchResult<Document> findDocuments(DocumentSearchCriteria criteria, int offset, int limit,
            List<SortCriterion> orderBy) {
        return dao.find(criteria, offset, limit, orderBy);
    }

    @Override
    public SearchResult<Document> findDocuments(DocumentSearchCriteria criteria, List<SortCriterion> orderBy) {
        return dao.find(criteria, orderBy);
    }

    @Override
    public boolean hasDocumentsForDomain(String domainName) {
        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setDomainName(domainName);
        return dao.exists(criteria);
    }

    @Override
    public boolean hasDocumentsForBuyRequest(long buyRequestId) {
        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setBuyRequestId(buyRequestId);
        return dao.exists(criteria);
    }

    @Override
    public Document get(Long id) {
        return dao.get(id);
    }

    @Override
    public void deleteDocumentFile(DocumentFile document)
            throws DocumentFileMovingException, NoSuchDirectoryException, WrongFaxFileExtensionException,
            NoSuchDocumentException {
        Validator.assertNotNull(document, "DocumentFile");
        incomingDocumentsManager.deleteDocumentFile(document);
    }

    @Override
    public void deleteFile(DocumentFile docFile)
            throws NoSuchDirectoryException, DocumentFileMovingException, NoSuchDocumentException {
        incomingDocumentsManager.deleteFile(docFile);
    }

    @Override
    public String getPath(Document document) {
        Validator.assertNotNull(document, "Document");
        Validator.assertNotNull(document.getDocumentFile(), "Document file");
        DocumentFileType docFileType = document.getDocumentFile().getFileType();
        Validator.assertNotNull(docFileType, "Document file type");
        return incomingDocumentsManager.getDirMapping(docFileType);
    }

    @Override
    public boolean documentFileExists(Document document)
            throws NoSuchDirectoryException, WrongFaxFileExtensionException {
        Validator.assertNotNull(document, "Document");
        Validator.assertNotNull(document.getDocumentFile(), "Document file");

        return incomingDocumentsManager.documentFileExists(document.getDocumentFile());
    }

    @Override
    public void update(Document document) throws DocumentGeneralException {
        Validator.assertNotNull(document, "Document");
        DocumentFile dFile = document.getDocumentFile();
        Validator.assertNotNull(dFile, "Document file");
        DocumentFileType dFileType = dFile.getFileType();
        Validator.assertNotNull(dFileType, "Document file type");
        if (!dFileType.isAssigned())
            throw new DocumentGeneralException("Document is not assigned.");
        dao.update(document);
    }

    @Override
    public void add(Document document, boolean fileOkToMove) throws DocumentGeneralException {
        Validator.assertNotNull(document, "Document");
        DocumentFile dFile = document.getDocumentFile();
        Validator.assertNotNull(dFile, "Document file");
        DocumentFileType dFileType = dFile.getFileType();
        Validator.assertNotNull(dFileType, "Document file type");
        if (dFileType.isAssigned())
            throw new DocumentGeneralException("Document is assigned.");

        /*
         * bug #1055 documentFile filename must be maximum 50 chars long. This
         * includes file extension, date and the date separator. file extension
         * takes 4 chars + '.' == 5 chars, date (without ms) takes 15 chars +
         * '.'. This leaves 29 chars for the filename without an extension. The
         * solution is to generate filenames which are shorter than the limit
         * (using UUID). In this case DocumentFile, IncomingDocumentsManager and
         * InternalDocumentIBatisDAO must be modified.
         */
        document.addTimeToFileName();
        dao.create(document);
        incomingDocumentsManager.markDocumentFileAsAssigned(document.getDocumentFile(), fileOkToMove);
    }

    @Override
    public UploadStatus uploadedDocumentFile(AuthenticatedUser user, boolean checkPermissions, DocumentPurpose purpose,
            DocumentFile docFile, List<String> domainNames, Long buyRequestId) throws DocumentGeneralException {
        domainNames = domainNames == null ? new ArrayList<String>() : domainNames;
        File documentFile = incomingDocumentsManager.getFileByDocument(docFile);
        validateDocumentFile(documentFile);
        ValidatedDomainSet domains = splitDomains(domainNames, checkPermissions ? user.getUsername() : null);
        BuyRequest buyRequest = null;
        try {
            if (buyRequestId != null) {
                buyRequest = secondaryMarketService.getBuyRequest(buyRequestId);
                // permissions should be the same as for modifyBuyRequest
                if (checkPermissions) {
                    final String methodName = SecondaryMarketAppService.class.getCanonicalName() + ".modifyBuyRequest";
                    BuyRequestPermissionQuery query = new BuyRequestPermissionQuery(methodName, buyRequestId, user);
                    authorizationService.authorize(user, query);
                }
            }
        } catch (PermissionDeniedException e) {
            throw new UploadInsufficientPermissions();
        } catch (BuyRequestNotFoundException e) {
            // ignore non-existing buyRequest, we want to save documents anyway
        }

        if (purpose != null && (domains.anyDomainExists() || buyRequest != null)) {
            OpInfo op = new OpInfo(user);
            AccountSearchCriteria criteria = new AccountSearchCriteria();
            criteria.setNicHandle(op.getUserName());
            List<Account> accounts = accountSearchService.getAccounts(criteria);
            Long accountNumber = null;
            if (accounts.size() == 1)
                accountNumber = accounts.get(0).getId();

            Document doc = new Document(docFile, purpose, op.getActorNameForRemark(), accountNumber, new ArrayList<>(
                    domains.getExistingDomains()), buyRequest, op.getActorName());
            add(doc, domains.allDomainsExist() && (buyRequestId == null || buyRequest != null));
        }
        // save if purpose is blank, any domain is unknown, or buyRequest was not recognized
        if (purpose == null || !domains.allDomainsExist() || (buyRequestId != null && buyRequest == null)) {
            incomingDocumentsManager.markFileAs(DocumentFileType.ATTACHMENT_NEW, documentFile);
        }
        final Long id = buyRequest != null ? buyRequest.getId() : null;
        return new UploadStatus(UploadStatusEnum.OK, domains.getExistingDomains(), id);
    }

    @Override
    public Set<String> getKnownDomains(List<String> domainNames) {
        try {
            return splitDomains(domainNames, null).getExistingDomains();
        } catch (DocumentGeneralException e) {
            Logger.getLogger(DocumentService.class).error("UploadNotByBillC thrown while getting known domains");
            return Collections.emptySet();
        }
    }

    private void validateDocumentFile(File documentFile) throws WrongFaxFileExtensionException, WrongFileSizeException {
        List<String> allowedExtensions = appConfig.getDocumentAllowedTypes();
        Integer maxAllowedFileSize = appConfig.getDocumentUploadSizeLimit();

        String filename = documentFile.getName();
        String fileExtension = FilenameUtils.getExtension(filename).toLowerCase();
        if (!allowedExtensions.contains(fileExtension)) {
            throw new WrongFaxFileExtensionException(fileExtension + " is not allowed. Allowed extensions "
                    + CollectionUtils.toString(allowedExtensions, ", "));
        }
        final long filesize = documentFile.length();
        if (filesize > maxAllowedFileSize) {
            throw new WrongFileSizeException("File is too big, received " + Long.toString(filesize)
                    + " bytes, but max allowed is " + maxAllowedFileSize + " bytes");
        }
    }

    private ValidatedDomainSet splitDomains(List<String> domainNames, String username) throws DocumentGeneralException {
        Set<String> existingDomains = new TreeSet<>();
        Set<String> nonExistingDomains = new TreeSet<>();
        for (String domainName : domainNames) {
            String billC = null;
            try {
                Domain domain = domainSearchService.getDomain(domainName);
                billC = domain.getBillingContactNic();
            } catch (DomainNotFoundException e) {
                Ticket t;
                try {
                    t = ticketSeachService.getTicketForDomain(domainName);
                } catch (TooManyTicketsException e1) {
                    throw new DocumentGeneralException("More than 1 registration ticket for the domain.");
                }
                if (t != null) {
                    billC = t.getOperation().getBillingContactsField().get(0).getNewValue().getNicHandle();
                }
            }
            if (billC == null) {
                nonExistingDomains.add(domainName);
            } else {
                existingDomains.add(domainName);
                if (username != null && !billC.equals(username))
                    throw new UploadNotByBillCException();
            }
        }
        return new ValidatedDomainSet(existingDomains, nonExistingDomains);
    }

    static class DocumentComparator implements Comparator<Document> {

        private SortCriterion sortBy;

        public DocumentComparator(List<SortCriterion> sortBy) {
            this.sortBy = sortBy.get(0);
        }

        @Override
        public int compare(Document o1, Document o2) {
            if (sortBy.isAscending())
                return o1.getDate().compareTo(o2.getDate());
            else
                return o2.getDate().compareTo(o1.getDate());
        }
    }

    private static class ValidatedDomainSet {
        private SortedSet<String> existingDomains;
        private SortedSet<String> nonExistingDomains;

        public ValidatedDomainSet(Set<String> existingDomains, Set<String> nonExistingDomains) {
            this.existingDomains = new TreeSet<>(existingDomains);
            this.nonExistingDomains = new TreeSet<>(nonExistingDomains);
        }

        public SortedSet<String> getExistingDomains() {
            return existingDomains;
        }

        public boolean allDomainsExist() {
            return nonExistingDomains.isEmpty();
        }

        public boolean anyDomainExists() {
            return !existingDomains.isEmpty();
        }
    }

}
