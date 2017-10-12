package pl.nask.crs.api.document;

import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.vo.*;
import pl.nask.crs.app.document.DocumentAppService;
import pl.nask.crs.documents.UploadResult;
import pl.nask.crs.documents.exception.DocumentGeneralException;
import pl.nask.crs.security.authentication.AccessDeniedException;

public class DocumentAppServiceEndpoint extends WsSessionAware implements CRSDocumentAppService {

    private DocumentAppService documentAppService;

    public void setDocumentAppService(DocumentAppService service) {
        this.documentAppService = service;
    }

    @Override
    public DocumentSettingsVO getDocumentSettings(AuthenticatedUserVO userVO) throws AccessDeniedException {
        return new DocumentSettingsVO(documentAppService.getDocumentSettings(userVO));
    }

    @Override
    public List<UploadResultVO> handleUpload(AuthenticatedUserVO user, List<DocumentUploadVO> uploads,
            UploadPurposeVO purpose) throws AccessDeniedException, DocumentGeneralException {
        List<UploadResult> upResults = documentAppService.handleUpload(user, uploads, purpose.asDocumentPurpose());
        List<UploadResultVO> result = new ArrayList<UploadResultVO>();
        for (UploadResult res : upResults)
            result.add(new UploadResultVO(res));
        return result;
    }

    @Override
    public void handleMailUpload(AuthenticatedUserVO user, String replyTo, String emailContent,
            List<UploadFilenameVO> attachmentFilenames) throws AccessDeniedException {
        documentAppService.handleMailUpload(user, replyTo, emailContent, attachmentFilenames);
    }

}
