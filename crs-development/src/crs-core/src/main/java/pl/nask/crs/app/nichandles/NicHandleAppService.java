package pl.nask.crs.app.nichandles;

import java.util.List;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.AppSearchService;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.commons.dns.exceptions.DuplicatedNameserverException;
import pl.nask.crs.commons.dns.exceptions.NameserverNameSyntaxException;
import pl.nask.crs.commons.dns.exceptions.TooManyNameserversException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.NicHandleAssignedToDomainException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.InvalidEmailException;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.defaults.EmailInvoiceFormat;
import pl.nask.crs.defaults.ResellerDefaults;
import pl.nask.crs.defaults.exceptions.DefaultsNotFoundException;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.NicHandleStatus;
import pl.nask.crs.nichandle.exception.*;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.GoogleAuthenticationException;
import pl.nask.crs.token.PasswordResetTokenExpiredException;
import pl.nask.crs.user.exceptions.InvalidOldPasswordException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

public interface NicHandleAppService extends AppSearchService<NicHandle, NicHandleSearchCriteria> {

    NicHandle get(AuthenticatedUser user, String nicHandleId) throws AccessDeniedException, NicHandleNotFoundException;

    LimitedSearchResult<HistoricalObject<NicHandle>> history(AuthenticatedUser user, String nicHandleId, int offset,
            int limit) throws AccessDeniedException, NicHandleNotFoundException;

    void alterStatus(AuthenticatedUser user, String nicHandleId, NicHandleStatus status, String hostmastersRemark)
            throws AccessDeniedException, EmptyRemarkException, NicHandleNotFoundException,
            NicHandleAssignedToDomainException, NicHandleIsAccountBillingContactException,
            NicHandleIsTicketContactException;

    void modifyNicHandleOwnAccount(AuthenticatedUser user, String nicHandleId, NewNicHandle newNicHandle, String hostmastersRemark)
            throws AccessDeniedException, NicHandleNotFoundException, AccountNotFoundException,
            AccountNotActiveException, NicHandleIsAccountBillingContactException, NicHandleEmailException,
            EmptyRemarkException, InvalidCountryException, InvalidCountyException, ExportException,
            InvalidEmailException, VatModificationException;

    NicHandle modifyNicHandle(AuthenticatedUser user, String nicHandleId, Long accountId, NewNicHandle newNicHandle,
            String hostmastersRemark)
            throws AccessDeniedException, NicHandleNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleIsAccountBillingContactException, NicHandleEmailException,
            InvalidCountryException, InvalidCountyException, ExportException, InvalidEmailException,
            VatModificationException;

    NicHandle saveNewPassword(AuthenticatedUser user, String nicHandleId, String newPassword1, String newPassword2)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordValidationException,
            NicHandleNotFoundException, AccessDeniedException, NicHandleEmailException, PasswordAlreadyExistsException;

    void changePassword(AuthenticatedUser user, String nicHandleId, String oldPassword, String newPassword1,
            String newPassword2)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordValidationException,
            NicHandleNotFoundException, AccessDeniedException, NicHandleEmailException, PasswordAlreadyExistsException,
            InvalidOldPasswordException;

    NicHandle createNicHandle(AuthenticatedUser user, Long accountId, NewNicHandle newNicHandle,
            String hostmastersRemark)
            throws AccessDeniedException, AccountNotFoundException, AccountNotActiveException,
            NicHandleNotFoundException, EmptyRemarkException, NicHandleEmailException, PasswordAlreadyExistsException,
            InvalidCountryException, InvalidCountyException, ExportException, InvalidEmailException;

    NicHandle createNicHandleOwnAccount(AuthenticatedUser user, NewNicHandle newNicHandle, String hostmastersRemark,
            boolean sendNotificationEmail)
            throws AccessDeniedException, AccountNotFoundException, AccountNotActiveException,
            NicHandleNotFoundException, EmptyRemarkException, NicHandleEmailException, PasswordAlreadyExistsException,
            InvalidCountryException, InvalidCountyException, ExportException, InvalidEmailException;

    /*
     * redeclare to allow aspect to 'catch' this invocation with declaringTypeName of NicHandleAppService
     */
    LimitedSearchResult<NicHandle> search(AuthenticatedUser user, NicHandleSearchCriteria criteria, long offset,
            long limit, List<SortCriterion> orderBy) throws AccessDeniedException;

    ResellerDefaults getDefaults(AuthenticatedUser user) throws AccessDeniedException, DefaultsNotFoundException;

    void saveDefaults(AuthenticatedUser user, String techContactId, List<String> nameservers,
            Integer dnsNotificationPeriod, EmailInvoiceFormat emailInvoiceFormat)
            throws AccessDeniedException, NicHandleException, NameserverNameSyntaxException,
            DuplicatedNameserverException, TooManyNameserversException;

    void removeDeletedNichandles(AuthenticatedUser user) throws AccessDeniedException;

    void removeUserPermission(AuthenticatedUser user, String nicHandleId, String permissionName)
            throws AccessDeniedException;

    void addUserPermission(AuthenticatedUser user, String nicHandleId, String permissionName)
            throws AccessDeniedException;

    void resetPasswordFromToken(String nicHandleId, String token, String pin, String password)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordValidationException,
            NicHandleNotFoundException, NicHandleEmailException, PasswordAlreadyExistsException,
            PasswordResetTokenExpiredException, GoogleAuthenticationException;

    void requestPasswordReset(String nicHandleId, String ipAddress)
            throws NicHandleNotFoundException, NicHandleEmailException;

    void resetPassword(AuthenticatedUser user, String nicHandleId, String ipAddress)
            throws AccessDeniedException, PasswordAlreadyExistsException, NicHandleEmailException,
            NicHandleNotFoundException;

    boolean canBeABillingContact(AuthenticatedUser user, String nicHandleId) throws AccessDeniedException;

    String changeTfa(AuthenticatedUser user, String nicHandleId, boolean useTfa) throws AccessDeniedException;

    void cleanupResetPassword(AuthenticatedUser user) throws AccessDeniedException;

    void cleanupLoginAttempts(AuthenticatedUser user) throws AccessDeniedException;

}
