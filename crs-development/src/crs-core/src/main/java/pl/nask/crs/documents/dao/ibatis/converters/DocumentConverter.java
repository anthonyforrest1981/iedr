package pl.nask.crs.documents.dao.ibatis.converters;

import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.documents.Document;
import pl.nask.crs.documents.DocumentFile;
import pl.nask.crs.documents.DocumentFileType;
import pl.nask.crs.documents.dao.ibatis.objects.InternalDocument;

public class DocumentConverter extends AbstractConverter<InternalDocument, Document> {

    protected Document _to(InternalDocument src) {
        if (src == null)
            return null;
        DocumentFile documentFile = new DocumentFile(src.getDocFilename(), src.getDocType());
        return new Document(src.getId(), src.getDate(), documentFile, src.getDocPurpose(), src.getDocSource(),
                src.getAccountNumber(), src.getDomains(), src.getCreatorNicHandleId(), src.getBuyRequest());
    }

    protected InternalDocument _from(Document document) {
        if (document == null)
            return null;
        final DocumentFile documentFile = document.getDocumentFile();
        assert documentFile != null : "@AssumeAssertion(nullness)";
        final DocumentFileType documentFileType = documentFile.getFileType();
        final String documentFileName = documentFile.getFileName();
        return new InternalDocument(document.getId(), document.getDate(), documentFileType, documentFileName,
                document.getDocPurpose(), document.getDocSource(), document.getAccountNumber(), document.getDomains(),
                document.getCreatorNicHandleId(), document.getBuyRequest());
    }
}
