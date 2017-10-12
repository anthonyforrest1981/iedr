package pl.nask.crs.api.users;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSPermissionsAppService {

    /**
     *
     * @param user
     * @return 1 for Direct, 2 for Registrar, 3 for SuperRegistrar, 4 for tech or admin contact. 0 for undefined (not a customer)
     * @throws AccessDeniedException
     * @throws UserNotAuthenticatedException
     * @throws pl.nask.crs.security.authentication.InvalidSessionTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    int getUserLevel(@WebParam(name = "user") AuthenticatedUserVO user)
            throws SessionExpiredException, AuthenticationException, IncorrectUtf8FormatException;

    @WebMethod
    List<String> getInternalUsers() throws IncorrectUtf8FormatException;

}
