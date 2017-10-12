package pl.nask.crs.api.nichandle;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.vo.*;
import pl.nask.crs.api.vo.search.NicHandleSearchCriteriaVO;
import pl.nask.crs.app.GenericValidationException;
import pl.nask.crs.commons.dns.exceptions.DuplicatedNameserverException;
import pl.nask.crs.commons.dns.exceptions.NameserverNameSyntaxException;
import pl.nask.crs.commons.dns.exceptions.TooManyNameserversException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.IncorrectUtf8FormatException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.InvalidEmailException;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.defaults.EmailInvoiceFormat;
import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;
import pl.nask.crs.nichandle.exception.*;
import pl.nask.crs.security.authentication.*;
import pl.nask.crs.token.PasswordResetTokenExpiredException;
import pl.nask.crs.user.exceptions.InvalidOldPasswordException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

@WebService(targetNamespace = "http://domainregistry.ie/")
public interface CRSNicHandleAppService {
    /**
     * Returns NicHandleVO object identified by nicHandleId param
     *
     * @param user authentication token, required
     * @param nicHandleId nic handle identifier, required
     * @return
     * @throws AccessDeniedException
     * @throws NicHandleNotFoundException
     * @throws UserNotAuthenticatedException
     * @throws pl.nask.crs.security.authentication.InvalidSessionTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    NicHandleVO get(@WebParam(name = "user") AuthenticatedUserVO user, @WebParam(name = "nicHandle") String nicHandleId)
            throws AccessDeniedException, NicHandleNotFoundException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException;

    /**
     * Updates nic handle identified by nicHandleId param.
     *
     * @param user authentication token, required
     * @param nicHandleId nic handle identifier, required
     * @param nicHandleData nic handle data to be updated, required
     * @param hostmastersRemark remark to be added, required
     * @throws AccessDeniedException
     * @throws NicHandleNotFoundException
     * @throws EmptyRemarkException
     * @throws AccountNotFoundException
     * @throws AccountNotActiveException
     * @throws NicHandleIsAccountBillingContactException
     * @throws NicHandleEmailException
     * @throws InvalidCountryException
     * @throws InvalidCountyException
     * @throws UserNotAuthenticatedException
     * @throws pl.nask.crs.security.authentication.InvalidSessionTokenException
     * @throws SessionExpiredException
     * @throws ExportException
     * @throws InvalidEmailException
     */
    void save(@WebParam(name = "user") AuthenticatedUserVO user, @WebParam(name = "nicHandleId") String nicHandleId,
            @WebParam(name = "nicHandleData") NicHandleEditVO nicHandleData,
            @WebParam(name = "remark") String hostmastersRemark)
            throws AccessDeniedException, NicHandleNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleIsAccountBillingContactException, NicHandleEmailException,
            InvalidCountryException, InvalidCountyException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, ExportException, VatModificationException,
            AuthenticationException, GenericValidationException, InvalidEmailException, IncorrectUtf8FormatException;

    /**
     * Changes password for nic handle identified by nicHandleId param.
     *
     * @param user authentication token, required
     * @param nicHandleId nic handle identifier, required
     * @param oldPassword nic handle old password, required
     * @param newPassword1 new password, required
     * @param newPassword2 repeated new password, required
     * @throws EmptyPasswordException
     * @throws PasswordsDontMatchException
     * @throws PasswordValidationException
     * @throws NicHandleNotFoundException
     * @throws AccessDeniedException
     * @throws NicHandleEmailException
     * @throws PasswordAlreadyExistsException
     * @throws UserNotAuthenticatedException
     * @throws pl.nask.crs.security.authentication.InvalidSessionTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    void changePassword(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "nicHandle") String nicHandleId, @WebParam(name = "oldPassword") String oldPassword,
            @WebParam(name = "password") String newPassword1, @WebParam(name = "repeatedPassword") String newPassword2)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordValidationException,
            NicHandleNotFoundException, AccessDeniedException, NicHandleEmailException, PasswordAlreadyExistsException,
            InvalidOldPasswordException, UserNotAuthenticatedException, InvalidSessionTokenException,
            SessionExpiredException, AuthenticationException, IncorrectUtf8FormatException;

    /**
     * Creates new nic handle.
     *
     * @param user authentication token, required
     * @param nicHandleCreateWrapper new nic handle data
     * @param hostmastersRemark remark to be added, required
     * @return
     * @throws AccessDeniedException
     * @throws AccountNotFoundException
     * @throws AccountNotActiveException
     * @throws NicHandleNotFoundException
     * @throws EmptyRemarkException
     * @throws NicHandleEmailException
     * @throws PasswordAlreadyExistsException
     * @throws InvalidCountryException
     * @throws InvalidCountyException
     * @throws UserNotAuthenticatedException
     * @throws pl.nask.crs.security.authentication.InvalidSessionTokenException
     * @throws SessionExpiredException
     * @throws ExportException
     * @throws InvalidEmailException
     */
    @WebMethod
    String create(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "newNicHandle") NicHandleEditVO nicHandleCreateWrapper,
            @WebParam(name = "remark") String hostmastersRemark)
            throws AccessDeniedException, AccountNotFoundException, AccountNotActiveException,
            NicHandleNotFoundException, EmptyRemarkException, NicHandleEmailException, PasswordAlreadyExistsException,
            InvalidCountryException, InvalidCountyException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, ExportException, GenericValidationException,
            AuthenticationException, InvalidEmailException, IncorrectUtf8FormatException;

    /**
     * Searches for nic handles matching given criteria in the given order.
     * Result is limited by offset and limit parameters.
     *
     * @param user authentication token, required
     * @param criteria nic ahndle search criteria, required
     * @param offset, required
     * @param limit, maximum number of nic handles to be retuned, required
     * @param orderBy sorting criteria, optional
     * @return
     * @throws UserNotAuthenticatedException
     * @throws pl.nask.crs.security.authentication.InvalidSessionTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    NicHandleSearchResultVO find(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "criteria") NicHandleSearchCriteriaVO criteria, @WebParam(name = "offset") long offset,
            @WebParam(name = "limit") long limit, @WebParam(name = "sortCriteria") List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            AuthenticationException, IncorrectUtf8FormatException;

    /**
     * Return reseller defaults(tech contact, nameservers)
     *
     * @param user authentication token, required
     * @param nicHandle nic handle identifier, required
     * @return
     * @throws UserNotAuthenticatedException
     * @throws pl.nask.crs.security.authentication.InvalidSessionTokenException
     * @throws SessionExpiredException
     */
    @WebMethod
    ResellerDefaultsVO getDefaults(@WebParam(name = "user") AuthenticatedUserVO user)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            DefaultsNotFoundException, AuthenticationException, IncorrectUtf8FormatException;

    @WebMethod
    void saveDefaults(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "techContactId") String techContactId,
            @WebParam(name = "nameservers") List<String> nameservers,
            @WebParam(name = "dnsNotificationPeriod") Integer dnsNotificationPeriod,
            EmailInvoiceFormat emailInvoiceFormat)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            DefaultsNotFoundException, AuthenticationException, IncorrectUtf8FormatException,
            DuplicatedNameserverException, NameserverNameSyntaxException, NicHandleException,
            TooManyNameserversException;

    @WebMethod
    String changeTfaFlag(@WebParam(name = "user") AuthenticatedUserVO user, @WebParam(
            name = "useTwoFactorAuthentication") boolean useTwoFactorAuthentication)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException;

    @WebMethod
    boolean isTfaUsed(@WebParam(name = "user") AuthenticatedUserVO user)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException;

    @WebMethod
    void requestPasswordReset(@WebParam(name = "username") String hicHandleId,
            @WebParam(name = "ipAddress") String ipAddress)
            throws NicHandleNotFoundException, NicHandleEmailException, IncorrectUtf8FormatException;

    @WebMethod
    void resetPassword(@WebParam(name = "nicHandleId") String nicHandleId, @WebParam(name = "token") String token,
            @WebParam(name = "pin") String pin, @WebParam(name = "newPassword") String password)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordValidationException,
            NicHandleNotFoundException, NicHandleEmailException, PasswordAlreadyExistsException,
            PasswordResetTokenExpiredException, IncorrectUtf8FormatException, GoogleAuthenticationException;

    @WebMethod
    boolean canBeABillingContact(@WebParam(name = "user") AuthenticatedUserVO user,
            @WebParam(name = "nicHandle") String nicHandle)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException;
}
