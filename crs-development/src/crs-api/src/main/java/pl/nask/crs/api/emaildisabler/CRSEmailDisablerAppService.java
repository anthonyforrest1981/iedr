package pl.nask.crs.api.emaildisabler;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.EmailDisablerSearchResultVO;
import pl.nask.crs.api.vo.EmailDisablerSuppressResultVO;
import pl.nask.crs.commons.email.search.EmailDisablerSuppressInfo;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.security.authentication.AccessDeniedException;

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSEmailDisablerAppService {

    @WebMethod
    EmailDisablerSearchResultVO getAll(@WebParam(name = "user") AuthenticatedUserVO user)
            throws AccessDeniedException, IncorrectUtf8FormatException;

    @WebMethod
    EmailDisablerSuppressResultVO modifySuppressionMode(
            @WebParam(name = "disabledIds") List<EmailDisablerSuppressInfo> emaildisablerInfo,
            @WebParam(name = "user") AuthenticatedUserVO user)
            throws AccessDeniedException, IncorrectUtf8FormatException;

}
