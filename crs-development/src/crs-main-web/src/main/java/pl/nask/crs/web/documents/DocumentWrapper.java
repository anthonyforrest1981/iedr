package pl.nask.crs.web.documents;

import java.util.ArrayList;
import java.util.Date;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.documents.Document;
import pl.nask.crs.documents.DocumentFile;
import pl.nask.crs.documents.DocumentFileType;
import pl.nask.crs.documents.DocumentPurpose;

/**
 * @author Piotr Tkaczyk
 */
public class DocumentWrapper {

    private Long id;

    private String fileName;

    private String fileType;

    private String docPurpose;

    private String docSource;

    private Long account;

    private String domainNames;

    private Long buyRequestId;

    private Date crDate;

    private String creatorNicHandleId;

    public DocumentWrapper() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Validator.assertNotNull(id, "Document id");
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        Validator.assertNotEmpty(fileName, "File name");
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        Validator.assertNotEmpty(fileType, "File type");
        this.fileType = fileType;
    }

    public String getDocPurpose() {
        return docPurpose;
    }

    public void setDocPurpose(String docPurpose) {
        Validator.assertNotEmpty(docPurpose, "Document purpose");
        this.docPurpose = docPurpose;
    }

    public String getDocSource() {
        return docSource;
    }

    public void setDocSource(String docSource) {
        this.docSource = docSource;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        if (account != null && account == -1)
            this.account = null;
        else
            this.account = account;
    }

    public String getDomainNames() {
        return domainNames;
    }

    public void setDomainNames(String domainNames) {
        Validator.assertNotEmpty(domainNames, "Domain names");
        this.domainNames = domainNames;
    }

    public Date getCrDate() {
        return crDate;
    }

    public String getCreatorNicHandleId() {
        return creatorNicHandleId;
    }

    public void setCreatorNicHandleId(String creatorNicHandleId) {
        this.creatorNicHandleId = creatorNicHandleId;
    }

    public Long getBuyRequestId() {
        return buyRequestId;
    }

    public void setBuyRequestId(Long buyRequestId) {
        this.buyRequestId = buyRequestId;
    }

    public boolean isAssigned() {
        return DocumentFileType.valueOf(fileType).isAssigned();
    }

    public Document getDocument() {
        Document document;
        DocumentFileType dft = DocumentFileType.valueOf(fileType);
        DocumentFile df = new DocumentFile(fileName, dft);
        DocumentPurpose purpose = null;
        if (docPurpose != null) {
            purpose = DocumentPurpose.fromValue(docPurpose);
        }
        if (id == null)
            document = new Document(df, purpose, docSource, account, creatorNicHandleId);
        else
            document = new Document(id, new Date(), df, purpose, docSource, account, new ArrayList<String>(),
                    creatorNicHandleId, null);

        document.setDomainsFromString(domainNames);

        return document;
    }

    public boolean isTiff() {
        return DocumentFile.isTiff(fileName);
    }

    public boolean isPicture() {
        return DocumentFile.isPicture(fileName);
    }

    public void setDocument(Document document) {
        Validator.assertNotNull(document, "Document");
        this.id = document.getId();
        this.fileName = document.getDocumentFile().getFileName();
        this.fileType = document.getDocumentFile().getFileType().getFileTypeAsString();
        this.docPurpose = document.getDocPurpose().getValue();
        this.docSource = document.getDocSource();
        this.account = document.getAccountNumber();
        this.domainNames = document.getDomainsAsString();
        this.crDate = document.getDate();
        this.creatorNicHandleId = document.getCreatorNicHandleId();
        if (document.getBuyRequest() != null) {
            this.buyRequestId = document.getBuyRequest().getId();
        } else {
            this.buyRequestId = null;
        }
    }
}
