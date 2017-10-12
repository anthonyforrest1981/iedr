package pl.nask.crs.api.nichandle;

import java.util.List;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.validation.ValidationHelper;
import pl.nask.crs.api.vo.*;
import pl.nask.crs.api.vo.search.NicHandleSearchCriteriaVO;
import pl.nask.crs.app.GenericValidationException;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.commons.dns.exceptions.DuplicatedNameserverException;
import pl.nask.crs.commons.dns.exceptions.NameserverNameSyntaxException;
import pl.nask.crs.commons.dns.exceptions.TooManyNameserversException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.InvalidEmailException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.CountryFactory;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.defaults.EmailInvoiceFormat;
import pl.nask.crs.defaults.ResellerDefaults;
import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.*;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.security.authentication.*;
import pl.nask.crs.token.PasswordResetTokenExpiredException;
import pl.nask.crs.user.exceptions.InvalidOldPasswordException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

public class NicHandleAppServiceEndpoint extends WsSessionAware implements CRSNicHandleAppService {

    private NicHandleAppService service;
    private NicHandleSearchService searchService;
    private UserAppService userAppService;
    private CountryFactory countryFactory;

    public void setUserAppService(UserAppService userAppService) {
        this.userAppService = userAppService;
    }

    public void setService(NicHandleAppService service) {
        this.service = service;
    }

    public void setSearchService(NicHandleSearchService service) {
        this.searchService = service;
    }

    public void setCountryFactory(CountryFactory countryFactory) {
        this.countryFactory = countryFactory;
    }

    /* (non-Javadoc)
    * @see pl.nask.crs.api.nichandle.CRSNicHandleAppService#get(pl.nask.crs.api.vo.AuthenticatedUserVO, java.lang.String)
    */
    @Override
    public NicHandleVO get(AuthenticatedUserVO user, String nicHandleId)
            throws AccessDeniedException, NicHandleNotFoundException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, AuthenticationException {
        ValidationHelper.validate(user);
        Validator.assertNotEmpty(nicHandleId, "nic handle id");
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user, false);
        return new NicHandleVO(service.get(completeUser, nicHandleId));
    }

    /* (non-Javadoc)
     * @see pl.nask.crs.api.nichandle.CRSNicHandleAppService#save(pl.nask.crs.api.vo.AuthenticatedUserVO, java.lang.String, pl.nask.crs.api.vo.NicHandleEditVO, java.lang.String)
     */
    @Override
    public void save(AuthenticatedUserVO user, String nicHandleId, NicHandleEditVO nicHandleDetails,
            String hostmastersRemark)
            throws NicHandleNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleIsAccountBillingContactException, NicHandleEmailException,
            InvalidCountryException, InvalidCountyException, SessionExpiredException, ExportException,
            VatModificationException, AuthenticationException, GenericValidationException, InvalidEmailException {
        ValidationHelper.validate(user);
        Validator.assertNotEmpty(hostmastersRemark, "hostmaster remark");
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        service.modifyNicHandleOwnAccount(completeUser, nicHandleId, nicHandleDetails.toNewNicHandle(),
                hostmastersRemark);
    }

    /* (non-Javadoc)
    * @see pl.nask.crs.api.nichandle.CRSNicHandleAppService#changePassword(pl.nask.crs.api.vo.AuthenticatedUserVO, java.lang.String, java.lang.String, java.lang.String)
    */
    @Override
    public void changePassword(AuthenticatedUserVO user, String nicHandleId, String oldPassword, String newPassword1,
            String newPassword2)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordValidationException,
            NicHandleNotFoundException, AccessDeniedException, NicHandleEmailException, PasswordAlreadyExistsException,
            InvalidOldPasswordException, UserNotAuthenticatedException, InvalidSessionTokenException,
            SessionExpiredException, AuthenticationException {
        ValidationHelper.validate(user);
        Validator.assertNotEmpty(nicHandleId, "nic handle id");
        Validator.assertNotEmpty(oldPassword, "old password");
        Validator.assertNotEmpty(newPassword1, "new password 1");
        Validator.assertNotEmpty(newPassword2, "new password 2");
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user, false);
        service.changePassword(completeUser, nicHandleId, oldPassword, newPassword1, newPassword2);
    }

    /* (non-Javadoc)
     * @see pl.nask.crs.api.nichandle.CRSNicHandleAppService#create(pl.nask.crs.api.vo.AuthenticatedUserVO, pl.nask.crs.api.vo.NicHandleEditVO, java.lang.String)
     */
    @Override
    public String create(AuthenticatedUserVO user, NicHandleEditVO nicHandleDetails, String hostmastersRemark)
            throws AccountNotFoundException, AccountNotActiveException, NicHandleNotFoundException,
            EmptyRemarkException, NicHandleEmailException, PasswordAlreadyExistsException, InvalidCountryException,
            InvalidCountyException, SessionExpiredException, ExportException, GenericValidationException,
            AuthenticationException, InvalidEmailException {
        ValidationHelper.validate(user);
        Validator.assertNotEmpty(hostmastersRemark, "hostmaster remark");
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user);
        return service.createNicHandleOwnAccount(completeUser, nicHandleDetails.toNewNicHandle(),
                hostmastersRemark, true).getNicHandleId();
    }

    @Override
    public NicHandleSearchResultVO find(AuthenticatedUserVO user, NicHandleSearchCriteriaVO criteria, long offset,
            long limit, List<SortCriterion> orderBy)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            AuthenticationException {
        ValidationHelper.validate(user);
        Validator.assertNotNull(criteria, "criteria");
        validateSession(user);
        LimitedSearchResult<NicHandle> searchRes = searchService.findNicHandle(criteria.toSearchCriteria(), offset,
                limit, orderBy);
        NicHandleSearchResultVO res = new NicHandleSearchResultVO(searchRes);
        return res;
    }

    @Override
    public ResellerDefaultsVO getDefaults(AuthenticatedUserVO user)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            DefaultsNotFoundException, AuthenticationException {
        ValidationHelper.validate(user);
        AuthenticatedUser u = validateSessionAndRetrieveFullUserInfo(user, false);
        ResellerDefaults defaults = service.getDefaults(u);
        return new ResellerDefaultsVO(defaults);
    }

    @Override
    public void saveDefaults(AuthenticatedUserVO user, String techContactId, List<String> nameservers,
            Integer dnsNotificationPeriod, EmailInvoiceFormat emailInvoiceFormat)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException,
            DefaultsNotFoundException, AuthenticationException, DuplicatedNameserverException,
            NameserverNameSyntaxException, NicHandleException, TooManyNameserversException {
        ValidationHelper.validate(user);
        validateSession(user);
        service.saveDefaults(user, techContactId, nameservers, dnsNotificationPeriod, emailInvoiceFormat);
    }

    @Override
    public String changeTfaFlag(AuthenticatedUserVO user, boolean useTwoFactorAuthentication)
            throws AuthenticationException, SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        return userAppService.changeTfa(user, useTwoFactorAuthentication);
    }

    @Override
    public boolean isTfaUsed(AuthenticatedUserVO user) throws AuthenticationException, SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        return userAppService.isTfaUsed(user);
    }

    @Override
    public boolean canBeABillingContact(AuthenticatedUserVO user, String nicHandle)
            throws AuthenticationException, SessionExpiredException {
        ValidationHelper.validate(user);
        validateSession(user);
        return service.canBeABillingContact(user, nicHandle);
    }

    @Override
    public void requestPasswordReset(String nicHandleId, String ipAddress)
            throws NicHandleNotFoundException, NicHandleEmailException {
        service.requestPasswordReset(nicHandleId, ipAddress);
    }

    @Override
    public void resetPassword(String nicHandleId, String token, String pin, String password)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordValidationException,
            NicHandleNotFoundException, NicHandleEmailException, PasswordAlreadyExistsException,
            PasswordResetTokenExpiredException, GoogleAuthenticationException {
        service.resetPasswordFromToken(nicHandleId, token, pin, password);
    }
}
