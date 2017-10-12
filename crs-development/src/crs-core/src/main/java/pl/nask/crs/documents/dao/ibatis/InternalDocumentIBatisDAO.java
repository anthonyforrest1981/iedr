package pl.nask.crs.documents.dao.ibatis;

import java.util.HashMap;
import java.util.Map;

import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.documents.DocumentFile;
import pl.nask.crs.documents.dao.ibatis.objects.InternalDocument;

public class InternalDocumentIBatisDAO extends GenericIBatisDAO<InternalDocument, Long> {

    static final Map<String, String> sortMap = new HashMap<>();
    static {
        sortMap.put("docSource", "IND.DOC_SOURCE");
        sortMap.put("docPurpose", "IND.DOC_PURPOSE");
        sortMap.put("date", "IND.Create_TS");
        sortMap.put("id", "IND.DOC_ID");
        sortMap.put("docType", "IND.DOC_TYPE");
        sortMap.put("domain_name", "domain_name");
        sortMap.put("buyRequestId", "IND.BuyRequestId");

    }

    public InternalDocumentIBatisDAO() {
        setGetQueryId("document.getFaxById");
        setFindQueryId("document.findFax");
        setCountFindQueryId("document.countTotalSearchResult");
        setDeleteQueryId("document.deleteDocById");
        setSortMapping(sortMap);
    }

    @Override
    public void create(InternalDocument document) {
        String newFilename = DocumentFile.getNormalizedFilenameWithAddDate(document.getDocFilename(), document.getDate());
        document.setDocFilename(newFilename);
        performInsert("document.insertFax", document);
        if (!document.getDomains().isEmpty()) {
            performInsert("document.insertFaxDomain", document);
        }
    }

    @Override
    public void update(InternalDocument document) {
        performUpdate("document.updateDocumentBuyRequest", document);
        Long id = document.getId();
        performDelete("document.deleteDocDomainById", id);
        if (!document.getDomains().isEmpty()) {
            performInsert("document.insertFaxDomain", document);
        }
    }
}
