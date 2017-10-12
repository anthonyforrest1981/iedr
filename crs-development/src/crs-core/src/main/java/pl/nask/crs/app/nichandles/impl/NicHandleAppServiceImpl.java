package pl.nask.crs.app.nichandles.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.config.GAConfig;
import pl.nask.crs.commons.dns.NameserverValidator;
import pl.nask.crs.commons.dns.exceptions.DuplicatedNameserverException;
import pl.nask.crs.commons.dns.exceptions.NameserverNameSyntaxException;
import pl.nask.crs.commons.dns.exceptions.TooManyNameserversException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.NicHandleAssignedToDomainException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.InvalidEmailException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.defaults.EmailInvoiceFormat;
import pl.nask.crs.defaults.ResellerDefaults;
import pl.nask.crs.defaults.ResellerDefaultsService;
import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.NicHandleStatus;
import pl.nask.crs.nichandle.exception.*;
import pl.nask.crs.nichandle.search.HistoricalNicHandleSearchCriteria;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;
import pl.nask.crs.nichandle.service.HistoricalNicHandleSearchService;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.nichandle.service.NicHandleService;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.GoogleAuthenticationException;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.security.authentication.googleauthenticator.AuthenticationCodesVerifier;
import pl.nask.crs.token.PasswordResetTokenExpiredException;
import pl.nask.crs.user.NRCLevel;
import pl.nask.crs.user.User;
import pl.nask.crs.user.exceptions.InvalidOldPasswordException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;
import pl.nask.crs.user.service.UserService;

import static pl.nask.crs.app.utils.UserValidator.validateLoggedIn;

public class NicHandleAppServiceImpl implements NicHandleAppService {

    private NicHandleService nicHandleService;
    private NicHandleSearchService nicHandleSearchService;
    private HistoricalNicHandleSearchService historicalNicHandleSearchService;
    private UserService userService;
    private ResellerDefaultsService resellerDefaultsService;
    private ServicesRegistry servicesRegistry;
    private ApplicationConfig applicationConfig;

    public NicHandleAppServiceImpl(NicHandleService nicHandleService, NicHandleSearchService nicHandleSearchService,
            HistoricalNicHandleSearchService historicalNicHandleSearchService, UserService userService,
            ResellerDefaultsService resellerDefaultsService, ApplicationConfig applicationConfig) {
        this.nicHandleService = nicHandleService;
        this.nicHandleSearchService = nicHandleSearchService;
        this.historicalNicHandleSearchService = historicalNicHandleSearchService;
        this.userService = userService;
        this.resellerDefaultsService = resellerDefaultsService;
        this.applicationConfig = applicationConfig;
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<NicHandle> search(AuthenticatedUser user, NicHandleSearchCriteria criteria, long offset,
            long limit, List<SortCriterion> orderBy) throws AccessDeniedException {
        validateLoggedIn(user);
        return nicHandleSearchService.findNicHandle(criteria, offset, limit, orderBy);
    }

    @Override
    @Transactional(readOnly = true)
    public NicHandle get(AuthenticatedUser user, String nicHandleId)
            throws AccessDeniedException, NicHandleNotFoundException {
        validateLoggedIn(user);
        return nicHandleSearchService.getNicHandle(nicHandleId);
    }

    @Override
    @Transactional(readOnly = true)
    public LimitedSearchResult<HistoricalObject<NicHandle>> history(AuthenticatedUser user, String nicHandleId,
            int offset, int limit) throws AccessDeniedException, NicHandleNotFoundException {
        validateLoggedIn(user);
        HistoricalNicHandleSearchCriteria criteria = new HistoricalNicHandleSearchCriteria();
        criteria.setNicHandleId(nicHandleId);
        return historicalNicHandleSearchService.findHistoricalNicHandle(criteria, offset, limit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void alterStatus(AuthenticatedUser user, String nicHandleId, NicHandleStatus status,
            String hostmastersRemark)
            throws AccessDeniedException, EmptyRemarkException, NicHandleNotFoundException,
            NicHandleAssignedToDomainException, NicHandleIsAccountBillingContactException,
            NicHandleIsTicketContactException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user, hostmastersRemark);
        nicHandleService.alterStatus(nicHandleId, status, opInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = NicHandleEmailException.class)
    public void modifyNicHandleOwnAccount(AuthenticatedUser user, String nicHandleId, NewNicHandle newNicHandle,
            String hostmastersRemark)
            throws AccessDeniedException, NicHandleNotFoundException, AccountNotFoundException,
            AccountNotActiveException, NicHandleIsAccountBillingContactException, NicHandleEmailException,
            EmptyRemarkException, InvalidCountryException, InvalidCountyException, ExportException,
            InvalidEmailException, VatModificationException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user, hostmastersRemark);
        long accountId = nicHandleSearchService.getNicHandle(user.getUsername()).getAccount().getId();
        nicHandleService.save(nicHandleId, accountId, newNicHandle, opInfo, false);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = NicHandleEmailException.class)
    public NicHandle modifyNicHandle(AuthenticatedUser user, String nicHandleId, Long accountId,
            NewNicHandle newNicHandle, String hostmastersRemark)
            throws AccessDeniedException, NicHandleNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleIsAccountBillingContactException, NicHandleEmailException,
            InvalidCountryException, InvalidCountyException, ExportException, InvalidEmailException,
            VatModificationException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user, hostmastersRemark);
        nicHandleService.save(nicHandleId, accountId, newNicHandle, opInfo, true);
        return nicHandleSearchService.getNicHandle(nicHandleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = NicHandleEmailException.class)
    public NicHandle saveNewPassword(AuthenticatedUser user, String nicHandleId, String newPassword1,
            String newPassword2)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordValidationException,
            NicHandleNotFoundException, AccessDeniedException, NicHandleEmailException, PasswordAlreadyExistsException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user);
        nicHandleService.saveNewPassword(newPassword1, newPassword2, nicHandleId, opInfo, user.getUsername());
        return nicHandleSearchService.getNicHandle(nicHandleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = NicHandleEmailException.class)
    public void changePassword(AuthenticatedUser user, String nicHandleId, String oldPassword, String newPassword1,
            String newPassword2)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordValidationException,
            NicHandleNotFoundException, AccessDeniedException, NicHandleEmailException, PasswordAlreadyExistsException,
            InvalidOldPasswordException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user);
        nicHandleService.changePassword(oldPassword, newPassword1, newPassword2, nicHandleId, opInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = NicHandleEmailException.class)
    public NicHandle createNicHandle(AuthenticatedUser user, Long accountNumber, NewNicHandle newNicHandle,
            String hostmastersRemark)
            throws AccessDeniedException, AccountNotFoundException, AccountNotActiveException,
            NicHandleNotFoundException, EmptyRemarkException, NicHandleEmailException, PasswordAlreadyExistsException,
            InvalidCountryException, InvalidCountyException, ExportException, InvalidEmailException {
        Validator.assertNotNull(newNicHandle, "new nic handle");
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user, hostmastersRemark);
        return nicHandleService.createNicHandle(accountNumber, newNicHandle, opInfo, true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = NicHandleEmailException.class)
    public NicHandle createNicHandleOwnAccount(AuthenticatedUser user, NewNicHandle newNicHandle,
            String hostmastersRemark, boolean sendNotificationEmail)
            throws AccessDeniedException, AccountNotFoundException, AccountNotActiveException,
            NicHandleNotFoundException, EmptyRemarkException, NicHandleEmailException, PasswordAlreadyExistsException,
            InvalidCountryException, InvalidCountyException, ExportException, InvalidEmailException {
        Validator.assertNotNull(newNicHandle, "new nic handle");
        validateLoggedIn(user);
        long accountNumber = nicHandleSearchService.getNicHandle(user.getUsername()).getAccount().getId();
        OpInfo opInfo = new OpInfo(user, hostmastersRemark);
        return nicHandleService.createNicHandle(accountNumber, newNicHandle, opInfo, sendNotificationEmail);
    }

    @Override
    @Transactional(readOnly = true)
    public ResellerDefaults getDefaults(AuthenticatedUser user) throws DefaultsNotFoundException {
        return resellerDefaultsService.get(user.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDefaults(AuthenticatedUser user, String techContactId, List<String> nameservers,
            Integer dnsNotificationPeriod, EmailInvoiceFormat emailInvoiceFormat)
            throws UserNotAuthenticatedException, NicHandleException, NameserverNameSyntaxException,
            DuplicatedNameserverException, TooManyNameserversException {
        validateLoggedIn(user);
        nicHandleSearchService.confirmNicHandleActive(techContactId);
        String nicHandle = user.getUsername();
        long accountId = nicHandleSearchService.getNicHandle(nicHandle).getAccount().getId();
        nicHandleSearchService.validateNicHandleWithAccount(techContactId, accountId);
        NameserverValidator.checkNameserverNames(nameservers, servicesRegistry);
        try {
            ResellerDefaults defaults = resellerDefaultsService.get(nicHandle);
            defaults.setTechContactId(techContactId);
            defaults.setNameservers(nameservers);
            defaults.setDnsNotificationPeriod(dnsNotificationPeriod);
            if (emailInvoiceFormat != null) {
                defaults.setEmailInvoiceFormat(emailInvoiceFormat);
            }
            resellerDefaultsService.save(defaults);
        } catch (DefaultsNotFoundException e) {
            ResellerDefaults newDefaults = new ResellerDefaults(nicHandle, techContactId, nameservers,
                    dnsNotificationPeriod, emailInvoiceFormat);
            resellerDefaultsService.create(newDefaults);
        }
    }

    @Override
    @Transactional
    public void removeDeletedNichandles(AuthenticatedUser user) throws UserNotAuthenticatedException {
        validateLoggedIn(user);
        nicHandleService.removeDeletedNichandles();
    }

    @Override
    @Transactional
    public void addUserPermission(AuthenticatedUser user, String nicHandleId, String permissionName) {
        nicHandleService.addUserPermission(nicHandleId, permissionName);
    }

    @Override
    @Transactional
    public void removeUserPermission(AuthenticatedUser user, String nicHandleId, String permissionName) {
        nicHandleService.removeUserPermission(nicHandleId, permissionName);
    }

    @Transactional
    @Override
    public void requestPasswordReset(String nicHandleId, String ipAddress)
            throws NicHandleNotFoundException, NicHandleEmailException {
        NicHandle nicHandle = nicHandleSearchService.getNicHandle(nicHandleId);
        OpInfo opInfo = new OpInfo(nicHandle);
        String loggedUserName = null;
        nicHandleService.resetPassword(nicHandleId, opInfo, ipAddress, loggedUserName);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void resetPassword(AuthenticatedUser user, String nicHandleId, String ipAddress)
            throws AccessDeniedException, PasswordAlreadyExistsException, NicHandleEmailException,
            NicHandleNotFoundException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user);
        nicHandleService.resetPassword(nicHandleId, opInfo, ipAddress, user.getUsername());
    }

    @Transactional(readOnly = true)
    @Override
    public boolean canBeABillingContact(AuthenticatedUser user, String nicHandleId) throws AccessDeniedException {
        validateLoggedIn(user);
        NRCLevel level = userService.getUserLevel(nicHandleId);
        switch (level) {
            case DIRECT:
            case REGISTRAR:
            case SUPER_REGISTRAR:
                return true;
            default:
                return false;
        }
    }

    @Transactional(noRollbackFor = Exception.class)
    @Override
    public void resetPasswordFromToken(String nicHandleId, String token, String pin, String password)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordValidationException,
            NicHandleNotFoundException, NicHandleEmailException, PasswordAlreadyExistsException,
            PasswordResetTokenExpiredException, GoogleAuthenticationException {
        User userFromToken = userService.getUserFromToken(token, nicHandleId);
        if (userFromToken.isUseTwoFactorAuthentication() && Validator.isEmpty(pin)) {
            throw new GoogleAuthenticationException("Invalid PIN");
        }
        userService.decreaseTokenAttempts(token);
        if (userFromToken.isUseTwoFactorAuthentication()) {
            verifyTfaPin(userFromToken, pin);
        }
        NicHandle nicHandle = nicHandleSearchService.getNicHandle(userFromToken.getUsername());
        OpInfo opInfo = new OpInfo(nicHandle);
        String loggedUserName = null;
        nicHandleService.saveNewPassword(password, password, nicHandle.getNicHandleId(), opInfo, loggedUserName);
        userService.invalidateToken(token);
    }

    private void verifyTfaPin(User user, String pin) throws GoogleAuthenticationException {
        boolean pinValid = !Validator.isEmpty(pin);
        GAConfig gaConfig = applicationConfig.getGoogleAuthenticationConfig();
        pinValid = pinValid && AuthenticationCodesVerifier.verifyCode(user.getSecret(), pin,
                gaConfig.getPastIntervals(), gaConfig.getFutureIntervals());
        if (!pinValid) {
            throw new GoogleAuthenticationException("Invalid PIN");
        }
    }

    public void setServicesRegistry(ServicesRegistry servicesRegistry) {
        this.servicesRegistry = servicesRegistry;
    }

    @Transactional(noRollbackFor = Exception.class)
    @Override
    public String changeTfa(AuthenticatedUser user, String nicHandleId, boolean useTfa) throws AccessDeniedException {
        OpInfo opInfo = new OpInfo(user);
        return userService.changeTfa(nicHandleId, useTfa, opInfo);
    }

    @Transactional(noRollbackFor = Exception.class)
    @Override
    public void cleanupResetPassword(AuthenticatedUser user) throws AccessDeniedException {
        userService.cleanupResetPassword();
    }

    @Transactional(noRollbackFor = Exception.class)
    @Override
    public void cleanupLoginAttempts(AuthenticatedUser user) throws AccessDeniedException {
        userService.cleanupLoginAttempts();
    }
}
