package pl.nask.crs.api.account;

import java.util.List;

import javax.jws.WebService;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.vo.*;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.utils.InvalidEmailException;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.nichandle.exception.*;
import pl.nask.crs.security.authentication.*;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

@WebService(
    serviceName="CRSResellerAppService",
    endpointInterface="pl.nask.crs.api.account.CRSResellerAppService",
    portName = "CRSResellerAppServicePort",
    targetNamespace = "http://domainregistry.ie/")
public class CRSResellerAppServiceProxy implements CRSResellerAppService {
    private CRSResellerAppService service;

    public void setService(CRSResellerAppService service) {
        this.service = service;
    }

    public AccountVO get(AuthenticatedUserVO user, long id)
            throws AccessDeniedException, AccountNotFoundException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException {
        return service.get(user, id);
    }

    public List<InternalRegistrarVO> getRegistrarsForLogin(AuthenticatedUserVO user)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            IncorrectUtf8FormatException {
        return service.getRegistrarsForLogin(user);
    }

    public NewAccountVO createDirectAccount(NicHandleEditVO nicHandleDetails, String password, boolean useTfa,
            String remoteAddress) throws AccountNotFoundException, NicHandleNotFoundException, NicHandleEmailException,
            AccountNotActiveException, EmptyRemarkException, PasswordAlreadyExistsException, InvalidCountryException,
            InvalidCountyException, ExportException, EmptyPasswordException, PasswordsDontMatchException,
            MissingRequiredCharacterTypeException, InvalidEmailException, IncorrectUtf8FormatException, LoginException {
        return service.createDirectAccount(nicHandleDetails, password, useTfa, remoteAddress);
    }
}
