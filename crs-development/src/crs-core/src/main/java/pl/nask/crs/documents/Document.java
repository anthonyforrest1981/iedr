package pl.nask.crs.documents;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import pl.nask.crs.secondarymarket.PlainBuyRequest;

public class Document {

    private static final String DOMAIN_NAMES_SEPARATOR = ",";

    private Long id;
    private Date date;
    private final DocumentFile documentFile;
    private DocumentPurpose docPurpose;
    private String docSource;
    private Long accountNumber;
    private List<String> domains;
    private String creatorNicHandleId;
    private PlainBuyRequest buyRequest;

    public Document(Long id, Date date, DocumentFile documentFile, DocumentPurpose docPurpose, String docuSource,
            Long accountNumber, List<String> domains, String creatorNicHandleId, PlainBuyRequest buyRequest) {
        this.id = id;
        this.date = date;
        this.documentFile = documentFile;
        this.docPurpose = docPurpose;
        this.docSource = docuSource;
        this.accountNumber = accountNumber;
        this.domains = domains;
        this.creatorNicHandleId = creatorNicHandleId;
        this.buyRequest = buyRequest;
    }

    public Document(DocumentFile documentFile, DocumentPurpose docPurpose, String docSource, Long accountNumber,
            List<String> domains, PlainBuyRequest buyRequest, String creatorNicHandleId) {
        this(null, new Date(), documentFile, docPurpose, docSource, accountNumber, domains, creatorNicHandleId, buyRequest);
    }

    public Document(DocumentFile documentFile, DocumentPurpose docPurpose, String docSource, Long accountNumber,
            String creatorNicHandleId) {
        this(documentFile, docPurpose, docSource, accountNumber, null, null, creatorNicHandleId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public DocumentFile getDocumentFile() {
        return documentFile;
    }

    public DocumentPurpose getDocPurpose() {
        return docPurpose;
    }

    public String getDocSource() {
        return docSource;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setDomains(List<String> domains) {
        this.domains = domains;
    }

    public List<String> getDomains() {
        return domains;
    }

    public String getCreatorNicHandleId() {
        return creatorNicHandleId;
    }

    public void setCreatorNicHandleId(String creatorNicHandleId) {
        this.creatorNicHandleId = creatorNicHandleId;
    }

    public String getDomainsAsString() {
        StringBuilder sb = new StringBuilder("");
        if (domains == null || domains.isEmpty())
            return sb.toString();

        for (Iterator i = domains.iterator(); i.hasNext();) {
            sb.append(i.next());
            if (i.hasNext())
                sb.append(DOMAIN_NAMES_SEPARATOR + " ");
        }

        return sb.toString();
    }

    public void addTimeToFileName() {
        documentFile.setAddDate(date);
    }

    public void setDomainsFromString(String joinedDomains) {
        if (joinedDomains != null) {
            String[] dArray = joinedDomains.split(DOMAIN_NAMES_SEPARATOR);
            List<String> splitted = new ArrayList<String>();
            for (String d : dArray) {
                String domain = d.trim();
                if (domain.length() > 0)
                    splitted.add(domain);
            }
            this.domains = splitted;
        } else {
            domains = new ArrayList<String>();
        }
    }

    public PlainBuyRequest getBuyRequest() {
        return buyRequest;
    }

    public void setBuyRequest(PlainBuyRequest buyRequest) {
        this.buyRequest = buyRequest;
    }
}
