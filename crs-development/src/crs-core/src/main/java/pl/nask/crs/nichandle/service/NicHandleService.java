package pl.nask.crs.nichandle.service;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.List;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.NicHandleAssignedToDomainException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.utils.InvalidEmailException;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.nichandle.NewAccount;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.NicHandleStatus;
import pl.nask.crs.nichandle.exception.*;
import pl.nask.crs.user.exceptions.InvalidOldPasswordException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

public interface NicHandleService {

    void save(String nicHandleId, Long accountId, NewNicHandle newNicHandle, OpInfo opInfo,
            boolean allowVatChange)
            throws NicHandleNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleIsAccountBillingContactException, NicHandleEmailException,
            InvalidCountryException, InvalidCountyException, ExportException, InvalidEmailException,
            VatModificationException;

    void changeNicHandleAccount(String nicHandleId, Long accountId, OpInfo opInfo)
            throws NicHandleNotFoundException, AccountNotActiveException, AccountNotFoundException,
            NicHandleIsAccountBillingContactException, ExportException, NicHandleEmailException, EmptyRemarkException,
            InvalidEmailException, InvalidCountyException;

    void alterStatus(String nicHandleId, NicHandleStatus status, OpInfo opInfo)
            throws NicHandleNotFoundException, NicHandleAssignedToDomainException, EmptyRemarkException,
            NicHandleIsAccountBillingContactException, NicHandleIsTicketContactException;

    NicHandle createNicHandle(Long accountNumber, NewNicHandle newNicHandle, OpInfo opInfo,
            boolean sendNotificationEmail)
            throws AccountNotFoundException, NicHandleNotFoundException, EmptyRemarkException, NicHandleEmailException,
            PasswordAlreadyExistsException, AccountNotActiveException, InvalidCountryException, InvalidCountyException,
            ExportException, InvalidEmailException;

    void saveNewPassword(String password1, String password2, String nicHandleId, OpInfo opInfo,
            String loggedUserName)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordValidationException,
            NicHandleNotFoundException, NicHandleEmailException, PasswordAlreadyExistsException;

    void changePassword(String oldPassword, String password1, String password2, String nicHandleId,
            OpInfo opInfo)
            throws EmptyPasswordException, PasswordsDontMatchException, PasswordValidationException,
            NicHandleNotFoundException, NicHandleEmailException, PasswordAlreadyExistsException,
            InvalidOldPasswordException;

    void resetPassword(String nicHandleId, OpInfo opInfo, String ipAddress, String loggedUserName)
            throws NicHandleNotFoundException, NicHandleEmailException;

    void confirmNicHandleIsNotAssignedToAnyDomain(String nicHandleId) throws NicHandleAssignedToDomainException;

    void removeDeletedNichandles();

    void removeUserPermission(String nicHandleId, String permissionName);

    void addUserPermission(String nicHandleId, String permissionName);

    NewAccount createDirectAccount(String name, String companyName, String email, String address,
            int countryId, int countyId, List<String> phones, List<String> faxes, String vatNo, OpInfo opInfo,
            String password, boolean useTfa, boolean forcePasswordChange)
            throws AccountNotFoundException, AccountNotActiveException, NicHandleNotFoundException,
            NicHandleEmailException, EmptyRemarkException, PasswordAlreadyExistsException, InvalidCountryException,
            InvalidCountyException, ExportException, InvalidEmailException;

    void triggerExport(String nicHandleId, OpInfo opInfo)
            throws ExportException, NicHandleNotFoundException;
}
