package pl.nask.crs.api.zone;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.security.authentication.AuthenticationException;

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSZoneAppService {

    @WebMethod
    void zonePublished(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainNames") List<String> domainNames)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException;

    @WebMethod
    void zoneUnpublished(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "domainNames") List<String> domainNames)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException;

    @WebMethod
    void zoneCommit(@WebParam(name = "user") AuthenticatedUserVO user)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException;
}
