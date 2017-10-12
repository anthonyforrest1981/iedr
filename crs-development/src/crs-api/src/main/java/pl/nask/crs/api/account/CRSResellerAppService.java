package pl.nask.crs.api.account;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
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

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSResellerAppService {
    /**
     * Returns account
     *
     * @param user authentication token, required
     * @param id account id to be returned, required
     * @return
     * @throws AccessDeniedException
     * @throws AccountNotFoundException
     * @throws UserNotAuthenticatedException
     * @throws pl.nask.crs.security.authentication.InvalidSessionTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    AccountVO get(@WebParam(name = "user") AuthenticatedUserVO user, @WebParam(name = "accountId") long id)
            throws AccessDeniedException, AccountNotFoundException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException;

    @WebMethod
    List<InternalRegistrarVO> getRegistrarsForLogin(@WebParam(name = "user") AuthenticatedUserVO user)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            IncorrectUtf8FormatException;

    /**
     * Allows the user to create a Direct account. This does not check any privileges since the user dies not exist yet.
     *
     * @return ID of created NicHandle
     * @throws ExportException
     * @throws InvalidCountyException
     * @throws InvalidCountryException
     * @throws PasswordAlreadyExistsException
     * @throws EmptyRemarkException
     * @throws AccountNotActiveException
     * @throws NicHandleEmailException
     * @throws NicHandleNotFoundException
     * @throws AccountNotFoundException
     * @throws MissingRequiredCharacterTypeException
     * @throws PasswordsDontMatchException
     * @throws EmptyPasswordException
     * @throws InvalidEmailException
     */
    @WebMethod
    NewAccountVO createDirectAccount(@WebParam(name = "nicHandleDetails") NicHandleEditVO nicHandleDetails, @WebParam(
            name = "newPassword") String password, @WebParam(name = "useTfa") boolean useTfa,
            @WebParam(name = "remoteAddress") String remoteAddress)
            throws AccountNotFoundException, NicHandleNotFoundException, NicHandleEmailException,
            AccountNotActiveException, EmptyRemarkException, PasswordAlreadyExistsException, InvalidCountryException,
            InvalidCountyException, ExportException, EmptyPasswordException, PasswordsDontMatchException,
            MissingRequiredCharacterTypeException, InvalidEmailException, IncorrectUtf8FormatException, LoginException;
}
