package pl.nask.crs.api.users;

import java.util.List;

import javax.jws.WebService;

import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.security.authentication.AuthenticationException;

@WebService(
    serviceName="CRSPermissionsAppService",
    endpointInterface="pl.nask.crs.api.users.CRSPermissionsAppService",
    portName = "CRSPermissionsAppServicePort",
    targetNamespace = "http://domainregistry.ie/")
public class CRSPermissionsAppServiceProxy implements CRSPermissionsAppService {
    private CRSPermissionsAppService service;

    public void setService(CRSPermissionsAppService service) {
        this.service = service;
    }

    public int getUserLevel(AuthenticatedUserVO user)
            throws SessionExpiredException, AuthenticationException, IncorrectUtf8FormatException {
        return service.getUserLevel(user);
    }

    public List<String> getInternalUsers() throws IncorrectUtf8FormatException {
        return service.getInternalUsers();
    }

}
