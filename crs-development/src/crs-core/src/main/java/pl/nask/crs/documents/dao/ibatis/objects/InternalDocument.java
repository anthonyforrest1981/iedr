package pl.nask.crs.documents.dao.ibatis.objects;

import java.util.Date;
import java.util.List;

import pl.nask.crs.documents.DocumentFileType;
import pl.nask.crs.documents.DocumentPurpose;
import pl.nask.crs.secondarymarket.PlainBuyRequest;

public class InternalDocument {

    private Long id;
    private Date date;
    private DocumentFileType docType;
    private String docFilename;
    private DocumentPurpose docPurpose;
    private String docSource;
    private Long accountNumber;
    private List<String> domains;
    private String creatorNicHandleId;
    private PlainBuyRequest buyRequest;

    public InternalDocument() {};

    public InternalDocument(Long id, Date date, DocumentFileType docType, String docFilename,
            DocumentPurpose docPurpose, String docSource, Long accountNumber, List<String> domains,
            String creatorNicHandleId, PlainBuyRequest buyRequest) {
        this.id = id;
        this.date = date;
        this.docType = docType;
        this.docFilename = docFilename;
        this.docPurpose = docPurpose;
        this.docSource = docSource;
        this.accountNumber = accountNumber;
        this.domains = domains;
        this.creatorNicHandleId = creatorNicHandleId;
        this.buyRequest = buyRequest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DocumentFileType getDocType() {
        return docType;
    }

    public void setDocType(DocumentFileType docType) {
        this.docType = docType;
    }

    public String getDocFilename() {
        return docFilename;
    }

    public void setDocFilename(String docFilename) {
        this.docFilename = docFilename;
    }

    public DocumentPurpose getDocPurpose() {
        return docPurpose;
    }

    public void setDocPurpose(DocumentPurpose docPurpose) {
        this.docPurpose = docPurpose;
    }

    public String getDocSource() {
        return docSource;
    }

    public void setDocSource(String docSource) {
        this.docSource = docSource;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public List<String> getDomains() {
        return domains;
    }

    public void setDomains(List<String> domains) {
        this.domains = domains;
    }

    public String getCreatorNicHandleId() {
        return creatorNicHandleId;
    }

    public void setCreatorNicHandleId(String creatorNicHandleId) {
        this.creatorNicHandleId = creatorNicHandleId;
    }

    public PlainBuyRequest getBuyRequest() {
        return buyRequest;
    }

    public void setBuyRequest(PlainBuyRequest buyRequest) {
        this.buyRequest = buyRequest;
    }
}
