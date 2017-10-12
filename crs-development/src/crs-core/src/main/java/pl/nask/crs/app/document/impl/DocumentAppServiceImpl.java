package pl.nask.crs.app.document.impl;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.document.DocumentAppService;
import pl.nask.crs.app.document.DocumentSettings;
import pl.nask.crs.app.document.WrongMailFormatException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.service.*;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.documents.*;
import pl.nask.crs.documents.email.DocumentParameters;
import pl.nask.crs.documents.exception.*;
import pl.nask.crs.documents.search.DocumentSearchCriteria;
import pl.nask.crs.documents.service.DocumentService;
import pl.nask.crs.secondarymarket.BuyRequestStatus;
import pl.nask.crs.secondarymarket.exceptions.BuyRequestFrozenAsPassed;
import pl.nask.crs.secondarymarket.exceptions.BuyRequestNotFoundException;
import pl.nask.crs.secondarymarket.services.SecondaryMarketService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.search.TicketSearchCriteria;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.ticket.services.TicketService;

public class DocumentAppServiceImpl implements DocumentAppService {

    private static final Logger LOG = Logger.getLogger(DocumentAppServiceImpl.class);
    public static final String DOMAINS_PREFIX_REGEX = "^domains?:.*$";
    public static final String BUY_REQUEST_PREFIX = "buyrequestid:";
    public static final String PREFIX_DELIMITER = ":";
    public static final int RADIX = 10;

    private final ApplicationConfig appConfig;
    private final DocumentService documentService;
    private final AccountSearchService accountSearchService;
    private final EmailTemplateSender emailTemplateSender;
    private final TicketSearchService ticketSearchService;
    private final TicketService ticketService;
    private final SecondaryMarketService secondaryMarketService;

    public DocumentAppServiceImpl(ApplicationConfig appConfig, DocumentService documentService,
            AccountSearchService accountSearchService, EmailTemplateSender emailTemplateSender,
            TicketSearchService ticketSearchService, TicketService ticketService,
            SecondaryMarketService secondaryMarketService) {
        this.appConfig = appConfig;
        this.documentService = documentService;
        this.accountSearchService = accountSearchService;
        this.emailTemplateSender = emailTemplateSender;
        this.ticketSearchService = ticketSearchService;
        this.ticketService = ticketService;
        this.secondaryMarketService = secondaryMarketService;
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentSettings getDocumentSettings(AuthenticatedUser user) throws AccessDeniedException {
        return new DocumentSettings(appConfig.getDocumentUploadSizeLimit(), appConfig.getDocumentUploadCountLimit(),
                appConfig.getDocumentAllowedTypes());
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<Document> getNewDocuments(AuthenticatedUser user, int offset, int limit,
            List<SortCriterion> sortBy) throws NoSuchDirectoryException, WrongFaxFileExtensionException {
        return documentService.getNewDocuments(offset, limit, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<Document> findDocuments(AuthenticatedUser user, DocumentSearchCriteria criteria,
            int offset, int limit, List<SortCriterion> orderBy) {
        return documentService.findDocuments(criteria, offset, limit, orderBy);
    }

    @Override
    @Transactional(readOnly = true)
    public Document get(AuthenticatedUser user, Long id) {
        return documentService.get(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(AuthenticatedUser user, Document document)
            throws EmailSendingException, DocumentGeneralException {
        documentService.add(document, true);
        if (document.getAccountNumber() != null) {
            Account acc = findAccount(document.getAccountNumber());
            String email = acc.getBillingContact().getEmail();
            String accountNicHandle = acc.getBillingContact().getNicHandle();
            try {
                emailTemplateSender.sendEmail(EmailTemplateNamesEnum.ADD_DOC.getId(), new DocumentParameters(document,
                        email, user.getUsername(), accountNicHandle));
            } catch (Exception e) {
                throw new EmailSendingException(e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDocumentFile(AuthenticatedUser user, Document document)
            throws DocumentFileMovingException, NoSuchDirectoryException, WrongFaxFileExtensionException,
            NoSuchDocumentException {
        documentService.deleteDocumentFile(document.getDocumentFile());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean documentFileExists(AuthenticatedUser user, Document document)
            throws NoSuchDirectoryException, WrongFaxFileExtensionException {
        return documentService.documentFileExists(document);
    }

    @Override
    @Transactional(readOnly = true)
    public String getPath(AuthenticatedUser user, Document document) {
        return documentService.getPath(document);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(AuthenticatedUser user, Document document) throws DocumentGeneralException {
        documentService.update(document);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<UploadResult> handleUpload(AuthenticatedUser user, List<? extends DocumentUpload> uploads,
            DocumentPurpose documentPurpose) throws DocumentGeneralException {
        return handleUpload(user, true, DocumentFileType.UPLOAD_VIA_CONSOLE, uploads, documentPurpose);
    }

    private void updateAffectedTicketsAndBuyRequests(AuthenticatedUser user, DocumentFileType sourceOfUpload,
            Set<String> affectedDomains, Set<Long> affectedBuyRequestIds) {
        StringBuilder remarkBuilder = new StringBuilder("Documents uploaded");
        if (sourceOfUpload == DocumentFileType.UPLOAD_VIA_CONSOLE)
            remarkBuilder.append(" via console");
        else if (sourceOfUpload == DocumentFileType.UPLOAD_VIA_MAIL)
            remarkBuilder.append(" via mail");

        OpInfo opInfo = new OpInfo(user, remarkBuilder.toString());
        for (String domainName : affectedDomains) {
            final TicketSearchCriteria criteria = new TicketSearchCriteria();
            criteria.setExactDomainName(domainName);
            List<Ticket> tickets = ticketSearchService.findAll(criteria, null);
            for (Ticket t : tickets) {
                try {
                    AdminStatus newStatus;
                    final DomainOperation.DomainOperationType ticketType = t.getOperation().getType();
                    if (ticketType == DomainOperation.DomainOperationType.REG
                            || ticketType == DomainOperation.DomainOperationType.MOD) {
                        newStatus = AdminStatus.RENEW;
                    } else {
                        // don't change admin status, only set remark
                        newStatus = t.getAdminStatus();
                    }
                    ticketService.updateAdminStatus(t.getId(), newStatus, opInfo);
                } catch (TicketNotFoundException e) {
                    LOG.error("Ticket " + t.getId() + " not found in renewing after document upload", e);
                }
            }
        }
        for (Long buyRequestId : affectedBuyRequestIds) {
            try {
                secondaryMarketService.updateStatus(opInfo, buyRequestId, BuyRequestStatus.RENEW);
            } catch (BuyRequestNotFoundException e) {
                LOG.error("Buy request " + buyRequestId + " not found in renewing after document upload", e);
            } catch (BuyRequestFrozenAsPassed e) {
                LOG.error("Buy request " + buyRequestId + " is already passed in renewing after document upload", e);
            } catch (EmptyRemarkException e) {
                LOG.error("Empty remark in renewing after document upload", e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleMailUpload(AuthenticatedUser user, String replyTo, String mailContent,
            List<? extends UploadFilename> attachmentFilenames) {
        DocumentPurpose documentPurpose = null;
        Set<String> domainNames = new TreeSet<>();
        Long buyRequestId = null;
        boolean parseSuccessful = false;
        try {
            MailContent parsedMail = parseEmailUpload(mailContent);
            documentPurpose = parsedMail.getPurpose();
            domainNames.addAll(parsedMail.getDomainNames());
            buyRequestId = parsedMail.getBuyRequestId();
            parseSuccessful = true;
        } catch (WrongMailFormatException e) {
        }

        List<DocumentUpload> docUploads = new ArrayList<>(attachmentFilenames.size());
        for (UploadFilename attachmentFilename : attachmentFilenames) {
            docUploads.add(new DocumentUploadImpl(attachmentFilename.getFilesystemName(), new ArrayList<>(domainNames),
                    buyRequestId));
        }

        try {
            List<UploadResult> results = handleUpload(user, false, DocumentFileType.UPLOAD_VIA_MAIL, docUploads,
                    documentPurpose);
            EmailDetails details = calculateEmailDetails(parseSuccessful, domainNames, buyRequestId, results);
            sendEmail(details.templateName, replyTo, prepareInfo(domainNames, buyRequestId, results, attachmentFilenames, details));
        } catch (DocumentsCountOutOfBoundsException e) {
            LOG.info("Upload of document by mail failed due to wrong number of documents: " + docUploads.size());
            sendEmail(EmailTemplateNamesEnum.DOCUMENT_UPLOAD_FAILURE, replyTo, e.getMessage());
        } catch (Exception e) {
            LOG.error("Upload of document by mail failed", e);
            sendEmail(EmailTemplateNamesEnum.DOCUMENT_UPLOAD_FAILURE, replyTo,
                    "General server error occured, please contact IEDR.");
        }
    }

    private EmailDetails calculateEmailDetails(boolean successfulParsing, Set<String> allDomainNames,
            Long buyRequestIds, List<UploadResult> result) {

        if (successfulParsing) {
            Set<String> knownDomains = new TreeSet<>();
            Set<Long> knownBuyRequests = new TreeSet<>();
            for (UploadResult ur : result) {
                knownDomains.addAll(ur.getStatus().getKnownDomains());
                final Long buyRequestId = ur.getStatus().getBuyRequestId();
                if (buyRequestId != null) {
                    knownBuyRequests.add(buyRequestId);
                }
            }

            final Set<String> unknownDomains = new TreeSet<>(allDomainNames);
            unknownDomains.removeAll(knownDomains);
            final Set<Long> unknownBuyRequests = new TreeSet<>();
            if (buyRequestIds != null) {
                unknownBuyRequests.add(buyRequestIds);
            }
            unknownBuyRequests.removeAll(knownBuyRequests);
            return new EmailDetails(EmailTemplateNamesEnum.DOCUMENT_UPLOAD_SUCCESSFUL, knownDomains, knownBuyRequests,
                    unknownDomains, unknownBuyRequests);
        }
        return new EmailDetails(EmailTemplateNamesEnum.DOCUMENT_MAIL_PARSE_FAILURE, Collections.<String>emptySet(),
                Collections.<Long>emptySet(), Collections.<String>emptySet(), Collections.<Long>emptySet());
    }

    private static class EmailDetails {
        EmailTemplateNamesEnum templateName;
        Set<String> knownDomains;
        Set<Long> knownBuyRequestIds;
        Set<String> unknownDomains;
        Set<Long> unknownBuyRequestIds;

        public EmailDetails(EmailTemplateNamesEnum templateName, Set<String> knownDomains, Set<Long> knownBuyRequests,
                Set<String> unknownDomains, Set<Long> unknownBuyRequestIds) {
            this.templateName = templateName;
            this.knownDomains = new TreeSet<>(knownDomains);
            this.knownBuyRequestIds = new TreeSet<>(knownBuyRequests);
            this.unknownDomains = new TreeSet<>(unknownDomains);
            this.unknownBuyRequestIds = new TreeSet<>(unknownBuyRequestIds);
        }
    }

    private List<UploadResult> handleUpload(AuthenticatedUser user, boolean checkPermissions,
            DocumentFileType sourceOfUpload, List<? extends DocumentUpload> uploads, DocumentPurpose documentPurpose)
            throws DocumentGeneralException {
        List<UploadResult> result = new ArrayList<>(uploads.size());
        try {
            if (uploads.size() == 0) {
                throw new DocumentsCountOutOfBoundsException(
                        "No files were uploaded, required at least one attachment");
            }
            if (uploads.size() > appConfig.getDocumentUploadCountLimit()) {
                throw new DocumentsCountOutOfBoundsException("Too many files uploaded, max allowed is " + Integer
                        .toString(appConfig.getDocumentUploadCountLimit()));
            }
            Set<String> affectedDomainNames = new HashSet<>();
            Set<Long> affectedBuyRequests = new HashSet<>();
            for (DocumentUpload upload : uploads) {
                UploadStatus uploadStatus;
                try {
                    final DocumentFile docFile = new DocumentFile(upload.getFilename(), sourceOfUpload);

                    uploadStatus = documentService
                            .uploadedDocumentFile(user, checkPermissions, documentPurpose, docFile, upload.getDomains(),
                                    upload.getBuyRequestId());
                    affectedDomainNames.addAll(uploadStatus.getKnownDomains());
                    if (uploadStatus.getBuyRequestId() != null) {
                        affectedBuyRequests.add(uploadStatus.getBuyRequestId());
                    }
                } catch (UploadNotByBillCException e) {
                    uploadStatus = new UploadStatus(UploadStatusEnum.UPLOAD_INSUFFICIENT_PERMISSIONS_FOR_DOMAIN,
                            documentService.getKnownDomains(upload.getDomains()), getKnownBuyRequest(upload));
                } catch (UploadInsufficientPermissions e) {
                    uploadStatus = new UploadStatus(UploadStatusEnum.UPLOAD_INSUFFICIENT_PERMISSIONS_FOR_BUY_REQUEST,
                            documentService.getKnownDomains(upload.getDomains()), getKnownBuyRequest(upload));
                } catch (WrongFileSizeException e) {
                    uploadStatus = new UploadStatus(UploadStatusEnum.FILE_TOO_BIG,
                            documentService.getKnownDomains(upload.getDomains()), getKnownBuyRequest(upload));
                } catch (WrongFaxFileExtensionException e) {
                    uploadStatus = new UploadStatus(UploadStatusEnum.WRONG_FILE_TYPE,
                            documentService.getKnownDomains(upload.getDomains()), getKnownBuyRequest(upload));
                }

                result.add(new UploadResult(upload.getFilename(), uploadStatus));
            }
            updateAffectedTicketsAndBuyRequests(user, sourceOfUpload, affectedDomainNames, affectedBuyRequests);
            return result;
        } finally {
            final Predicate<UploadResult> statusIsOk = new Predicate<UploadResult>() {
                @Override
                public boolean apply(UploadResult uploadResult) {
                    return uploadResult.getStatus().getStatus() == UploadStatusEnum.OK;
                }
            };
            final Function<UploadResult, String> extractFilenameFromResult = new Function<UploadResult, String>() {
                @Override
                public String apply(UploadResult uploadResult) {
                    return uploadResult.getDocumentName();
                }
            };
            final Function<DocumentUpload, String> extractFilenameFromUpload = new Function<DocumentUpload, String>() {
                @Override
                public String apply(DocumentUpload documentUpload) {
                    return documentUpload.getFilename();
                }
            };

            Iterable<String> notSuccessfullyUploadedFiles = Iterables
                    .filter(Iterables.transform(uploads, extractFilenameFromUpload), Predicates.not(Predicates
                            .in(Collections2
                                    .transform(Collections2.filter(result, statusIsOk), extractFilenameFromResult))));
            for (String filename : notSuccessfullyUploadedFiles) {
                final DocumentFile docFile = new DocumentFile(filename, sourceOfUpload);
                try {
                    documentService.deleteFile(docFile);
                } catch (DocumentGeneralException e) {
                    LOG.error("Error cleaning failed uploaded file " + filename, e);
                }
            }
        }
    }

    private Long getKnownBuyRequest(DocumentUpload upload) {
        Long buyRequestId = null;
        try {
            if (upload.getBuyRequestId() != null) {
                buyRequestId = secondaryMarketService.getBuyRequest(upload.getBuyRequestId()).getId();
            }
        } catch (BuyRequestNotFoundException e) {}
        return buyRequestId;
    }

    private MailContent parseEmailUpload(String mailContent) throws WrongMailFormatException {
        List<String> domainNames = new ArrayList<>();
        Long buyRequestId = null;
        for (String line : mailContent.split("\n")) {
            String lowercasedAndCollapsed = line.toLowerCase().replaceAll("[\\s]+", "");
            if (lowercasedAndCollapsed.matches(DOMAINS_PREFIX_REGEX)) {
                String domainsString = line.substring(line.indexOf(PREFIX_DELIMITER) + 1);
                for (String domain : domainsString.split(",")) {
                    final String domainName = StringUtils.strip(domain);
                    if (!domainName.isEmpty()) {
                        domainNames.add(domainName);
                    }
                }
            } else if (lowercasedAndCollapsed.startsWith(BUY_REQUEST_PREFIX)) {
                String buyRequestIdAsString = line.substring(line.indexOf(PREFIX_DELIMITER) + 1);
                buyRequestIdAsString = StringUtils.strip(buyRequestIdAsString);
                try {
                    buyRequestId = Long.parseLong(buyRequestIdAsString, RADIX);
                } catch (NumberFormatException e) {
                    throw new WrongMailFormatException();
                }
            }
        }
        if (domainNames.isEmpty() && buyRequestId == null)
            throw new WrongMailFormatException();
        return new MailContent(domainNames, buyRequestId, DocumentPurpose.MISCELLANEOUS);
    }

    private static class DocumentUploadImpl implements DocumentUpload {

        private final String filename;
        private final List<String> domains;
        private final Long buyRequestId;

        public DocumentUploadImpl(String filename, List<String> domains, Long buyRequestId) {
            this.filename = filename;
            this.domains = domains;
            this.buyRequestId = buyRequestId;
        }

        @Override
        public String getFilename() {
            return filename;
        }

        @Override
        public List<String> getDomains() {
            return domains;
        }

        @Override
        public Long getBuyRequestId() {
            return buyRequestId;
        }
    }

    private void sendEmail(EmailTemplateNamesEnum emailEnum, String replyTo, String info) {
        try {
            MapBasedEmailParameters params = new MapBasedEmailParameters();
            params.set(ParameterNameEnum.CREATOR_C_EMAIL, replyTo);
            params.set(ParameterNameEnum.INFO, info);
            emailTemplateSender.sendEmail(emailEnum.getId(), params);
        } catch (Exception e) {
            LOG.error("Error sending email", e);
        }
    }

    private String prepareInfo(Set<String> domains, Long buyRequestId, List<UploadResult> results,
            List<? extends UploadFilename> uploadFilenames,
            EmailDetails emailDetails) {
        Map<String, String> systemToUserFilename = new HashMap<>();
        for (UploadFilename uploadFilename : uploadFilenames) {
            systemToUserFilename.put(uploadFilename.getFilesystemName(), uploadFilename.getUserFilename());
        }
        StringBuilder result = new StringBuilder();
        String pastVerb = results.size() > 1 ? "were" : "was";
        result.append(results.size()).append(" ").append(pluralize("document", results.size()))
                .append(" " + pastVerb + " uploaded.").append("\n");

        if (!domains.isEmpty()) {
            List<String> wrappedDomains = new ArrayList<>();
            for (String domain : domains)
                wrappedDomains.add("\"" + domain + "\"");
            result.append("Found domains to which documents should be assigned: ")
                    .append(StringUtils.join(wrappedDomains, ", ")).append("\n\n");
        }

        if (buyRequestId != null) {
            result.append("Found buy request id to which documents should be assigned: ")
                    .append(buyRequestId).append("\n\n");
        }

        result.append("Result of document uploads:\n");
        for (UploadResult uploadResult : results) {
            String filename = systemToUserFilename.get(uploadResult.getDocumentName());
            result.append(filename).append(": ").append(humanize(uploadResult.getStatus().getStatus())).append("\n");
        }

        if (!emailDetails.unknownBuyRequestIds.isEmpty() || !emailDetails.unknownDomains.isEmpty()) {
            List<String> domainAndBuyRequest = new ArrayList<>();
            if (!emailDetails.unknownDomains.isEmpty())
                domainAndBuyRequest.add("domains");
            if (!emailDetails.unknownBuyRequestIds.isEmpty())
                domainAndBuyRequest.add("buy requests");
            result
                .append("\n")
                .append("Please note that the following ")
                .append(StringUtils.join(domainAndBuyRequest, " and "))
                .append(" listed in your email could not be identified:")
                .append(unknownEntitiesSummary(emailDetails));
            result
                .append("These are not registered ")
                .append(StringUtils.join(domainAndBuyRequest, " and "))
                .append(" and there are no applications pending for them.")
                .append("\n\n")
                .append("The IEDR will try to determine if they could relate to any other ")
                .append(StringUtils.join(domainAndBuyRequest, " or "))
                .append(", but if you do not hear from us regarding other ")
                .append(StringUtils.join(domainAndBuyRequest, " or "))
                .append(" you may wish to register or modify, please contact us for more information.")
                .append("\n\n");

        }
        return result.toString();
    }

    private String unknownEntitiesSummary(EmailDetails emailDetails) {
        StringBuilder result = new StringBuilder();
        if (!emailDetails.unknownDomains.isEmpty() && !emailDetails.unknownBuyRequestIds.isEmpty()) {
            result.append("\n\n");
            result.append("Domains: ")
                  .append(StringUtils.join(emailDetails.unknownDomains, ", "))
                  .append("\n\n");
            result.append("Buy requests: ")
                  .append(StringUtils.join(emailDetails.unknownBuyRequestIds, ", "))
                  .append("\n\n");
        } else {
            result.append(" ");
            if (!emailDetails.unknownDomains.isEmpty()) {
                result.append(StringUtils.join(emailDetails.unknownDomains, ", "));
            } else {
                result.append(StringUtils.join(emailDetails.unknownBuyRequestIds, ", "));
            }
            result.append("\n\n");
        }
        return result.toString();
    }

    private String humanize(UploadStatusEnum status) {
        switch (status) {
            case OK:
                return "OK, file saved";
            case FILE_TOO_BIG:
                return "File too big, discarded";
            case WRONG_FILE_TYPE:
                return "Wrong file type, discarded";
            default:
            case UPLOAD_INSUFFICIENT_PERMISSIONS_FOR_BUY_REQUEST:
            case UPLOAD_INSUFFICIENT_PERMISSIONS_FOR_DOMAIN:
                // impossible at this step of handling email
                return "Unknown error, file is discarded";
        }
    }

    private String pluralize(String base, int count) {
        return base + (count == 1 ? "" : "s");
    }

    private static class MailContent {
        private final List<String> domainNames;
        private final Long buyRequestId;
        private final DocumentPurpose purpose;

        private MailContent(List<String> domainNames, Long buyRequestId, DocumentPurpose purpose) {
            this.domainNames = domainNames;
            this.buyRequestId = buyRequestId;
            this.purpose = purpose;
        }

        public List<String> getDomainNames() {
            return domainNames;
        }

        public DocumentPurpose getPurpose() {
            return purpose;
        }

        public Long getBuyRequestId() {
            return buyRequestId;
        }
    }

    private Account findAccount(Long account) throws DocumentGeneralException {
        Account acc;
        try {
            acc = accountSearchService.getAccount(account);
        } catch (AccountNotFoundException e) {
            throw new DocumentGeneralException("Account not found for account number: " + account);
        }
        if (!acc.isActive()) {
            throw new DocumentGeneralException("Account not active: " + acc);
        }
        return acc;
    }
}
