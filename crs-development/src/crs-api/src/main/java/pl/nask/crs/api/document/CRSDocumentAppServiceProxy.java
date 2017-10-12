package pl.nask.crs.api.document;

import java.util.List;

import javax.jws.WebService;

import pl.nask.crs.api.vo.*;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.documents.exception.DocumentGeneralException;
import pl.nask.crs.security.authentication.AccessDeniedException;

@WebService(
    serviceName="CRSDocumentAppService",
    endpointInterface="pl.nask.crs.api.document.CRSDocumentAppService",
    portName = "CRSDocumentAppServicePort",
    targetNamespace = "http://domainregistry.ie/")
public class CRSDocumentAppServiceProxy implements CRSDocumentAppService {
    private CRSDocumentAppService service;

    public void setService(CRSDocumentAppService service) {
        this.service = service;
    }

    public DocumentSettingsVO getDocumentSettings(AuthenticatedUserVO user)
            throws AccessDeniedException, IncorrectUtf8FormatException {
        return service.getDocumentSettings(user);
    }

    public List<UploadResultVO> handleUpload(AuthenticatedUserVO user, List<DocumentUploadVO> documents,
            UploadPurposeVO purpose)
            throws AccessDeniedException, DocumentGeneralException, EmailSendingException, IncorrectUtf8FormatException {
        return service.handleUpload(user, documents, purpose);
    }

    public void handleMailUpload(AuthenticatedUserVO user, String replyTo, String emailContent,
            List<UploadFilenameVO> attachments) throws AccessDeniedException, IncorrectUtf8FormatException {
        service.handleMailUpload(user, replyTo, emailContent, attachments);
    }
}
