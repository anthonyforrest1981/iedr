package pl.nask.crs.api.zone;

import java.util.List;

import javax.jws.WebService;

import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.security.authentication.AuthenticationException;

@WebService(
    serviceName = "CRSZoneAppService",
    endpointInterface = "pl.nask.crs.api.zone.CRSZoneAppService",
    portName = "CRSZoneAppServicePort",
    targetNamespace = "http://domainregistry.ie/")
public class CRSZoneAppServiceProxy implements CRSZoneAppService {
    private CRSZoneAppService service;

    public void setService(CRSZoneAppService service) {
        this.service = service;
    }

    public void zonePublished(AuthenticatedUserVO user, List<String> domainNames)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException {
        service.zonePublished(user, domainNames);
    }

    public void zoneUnpublished(AuthenticatedUserVO user, List<String> domainNames)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException {
        service.zoneUnpublished(user, domainNames);
    }

    public void zoneCommit(AuthenticatedUserVO user)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException {
        service.zoneCommit(user);
    }
}
