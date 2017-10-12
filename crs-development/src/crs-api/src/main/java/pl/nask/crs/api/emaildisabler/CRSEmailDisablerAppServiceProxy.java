package pl.nask.crs.api.emaildisabler;

import java.util.List;

import javax.jws.WebService;

import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.api.vo.EmailDisablerSearchResultVO;
import pl.nask.crs.api.vo.EmailDisablerSuppressResultVO;
import pl.nask.crs.commons.email.search.EmailDisablerSuppressInfo;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.security.authentication.AccessDeniedException;

@WebService(
    serviceName="CRSEmailDisablerAppService",
    endpointInterface="pl.nask.crs.api.emaildisabler.CRSEmailDisablerAppService",
    portName = "CRSEmailDisablerAppServicePort",
    targetNamespace = "http://domainregistry.ie/")
public class CRSEmailDisablerAppServiceProxy implements CRSEmailDisablerAppService {
    private CRSEmailDisablerAppService service;

    public void setService(CRSEmailDisablerAppService service) {
        this.service = service;
    }

    public EmailDisablerSearchResultVO getAll(AuthenticatedUserVO user)
            throws AccessDeniedException, IncorrectUtf8FormatException {
        return service.getAll(user);
    }

    public EmailDisablerSuppressResultVO modifySuppressionMode(List<EmailDisablerSuppressInfo> emaildisablerInfo,
            AuthenticatedUserVO user) throws AccessDeniedException, IncorrectUtf8FormatException {
        return service.modifySuppressionMode(emaildisablerInfo, user);
    }

}
