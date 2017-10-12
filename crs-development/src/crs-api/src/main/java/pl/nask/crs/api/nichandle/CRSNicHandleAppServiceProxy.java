package pl.nask.crs.api.nichandle;

import java.util.List;

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

@WebService(
    serviceName="CRSNicHandleAppService",
    endpointInterface="pl.nask.crs.api.nichandle.CRSNicHandleAppService",
    portName = "CRSNicHandleAppServicePort",
    targetNamespace = "http://domainregistry.ie/")
public class CRSNicHandleAppServiceProxy implements CRSNicHandleAppService {
    private CRSNicHandleAppService service;

    public void setService(CRSNicHandleAppService service) {
        this.service = service;
    }

    public NicHandleVO get(AuthenticatedUserVO user, String nicHandleId)
            throws AccessDeniedException, NicHandleNotFoundException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, AuthenticationException,
            IncorrectUtf8FormatException {
        return service.get(user, nicHandleId);
    }

    public void changePassword(AuthenticatedUserVO user, String nicHandleId, String oldPassword, String newPassword1,
            String newPassword2)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordValidationException,
            NicHandleNotFoundException, AccessDeniedException, NicHandleEmailException, PasswordAlreadyExistsException,
            InvalidOldPasswordException, UserNotAuthenticatedException, InvalidSessionTokenException,
            SessionExpiredException, AuthenticationException, IncorrectUtf8FormatException {
        service.changePassword(user, nicHandleId, oldPassword, newPassword1, newPassword2);
    }

    public void save(AuthenticatedUserVO user, String nicHandleId, NicHandleEditVO nicHandleData,
            String hostmastersRemark)
            throws AccessDeniedException, NicHandleNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleIsAccountBillingContactException, NicHandleEmailException,
            InvalidCountryException, InvalidCountyException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, ExportException, VatModificationException,
            AuthenticationException, GenericValidationException, InvalidEmailException, IncorrectUtf8FormatException {
        service.save(user, nicHandleId, nicHandleData, hostmastersRemark);
    }

    public String create(AuthenticatedUserVO user, NicHandleEditVO nicHandleCreateWrapper, String hostmastersRemark)
            throws AccessDeniedException, AccountNotFoundException, AccountNotActiveException,
            NicHandleNotFoundException, EmptyRemarkException, NicHandleEmailException, PasswordAlreadyExistsException,
            InvalidCountryException, InvalidCountyException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, ExportException, GenericValidationException,
            AuthenticationException, InvalidEmailException, IncorrectUtf8FormatException {
        return service.create(user, nicHandleCreateWrapper, hostmastersRemark);
    }

    public NicHandleSearchResultVO find(AuthenticatedUserVO user, NicHandleSearchCriteriaVO criteria, long offset,
            long limit, List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            AuthenticationException, IncorrectUtf8FormatException {
        return service.find(user, criteria, offset, limit, orderBy);
    }

    public ResellerDefaultsVO getDefaults(AuthenticatedUserVO user)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            DefaultsNotFoundException, AuthenticationException, IncorrectUtf8FormatException {
        return service.getDefaults(user);
    }

    public void saveDefaults(AuthenticatedUserVO user, String techContactId, List<String> nameservers,
            Integer dnsNotificationPeriod, EmailInvoiceFormat emailInvoiceFormat)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            DefaultsNotFoundException, AuthenticationException, IncorrectUtf8FormatException,
            DuplicatedNameserverException, NameserverNameSyntaxException, NicHandleException,
            TooManyNameserversException {
        service.saveDefaults(user, techContactId, nameservers, dnsNotificationPeriod, emailInvoiceFormat);
    }

    public String changeTfaFlag(AuthenticatedUserVO user, boolean useTwoFactorAuthentication)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException {
        return service.changeTfaFlag(user, useTwoFactorAuthentication);
    }

    public boolean isTfaUsed(AuthenticatedUserVO user)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException {
        return service.isTfaUsed(user);
    }

    public void requestPasswordReset(String hicHandleId, String ipAddress)
            throws NicHandleNotFoundException, NicHandleEmailException, IncorrectUtf8FormatException {
        service.requestPasswordReset(hicHandleId, ipAddress);
    }

    public void resetPassword(String nicHandleId, String token, String pin, String password)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordValidationException,
            NicHandleNotFoundException, NicHandleEmailException, PasswordAlreadyExistsException,
            PasswordResetTokenExpiredException, IncorrectUtf8FormatException, GoogleAuthenticationException {
        service.resetPassword(nicHandleId, token, pin, password);
    }

    public boolean canBeABillingContact(AuthenticatedUserVO user, String nicHandle)
            throws AuthenticationException, SessionExpiredException, IncorrectUtf8FormatException {
        return service.canBeABillingContact(user, nicHandle);
    }
}
