package pl.nask.crs.api.document;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.api.vo.*;
import pl.nask.crs.commons.email.service.EmailSendingException;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.documents.exception.DocumentGeneralException;
import pl.nask.crs.security.authentication.AccessDeniedException;

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSDocumentAppService {

    @WebMethod
    DocumentSettingsVO getDocumentSettings(@WebParam(name = "user") AuthenticatedUserVO user)
            throws AccessDeniedException, IncorrectUtf8FormatException;

    @WebMethod
    List<UploadResultVO> handleUpload(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "documents") List<DocumentUploadVO> documents,
            @WebParam(name = "purpose") UploadPurposeVO purpose)
            throws AccessDeniedException, DocumentGeneralException, EmailSendingException, IncorrectUtf8FormatException;

    @WebMethod
    void handleMailUpload(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "replyTo") String replyTo, @WebParam(name = "content") String emailContent, @WebParam(
                    name = "attachments") List<UploadFilenameVO> attachments)
            throws AccessDeniedException, IncorrectUtf8FormatException;
}
