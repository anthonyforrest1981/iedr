package pl.nask.crs.api.account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.accounts.InternalRegistrar;
import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.validation.ValidationHelper;
import pl.nask.crs.api.vo.*;
import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.utils.InvalidEmailException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.nichandle.NewAccount;
import pl.nask.crs.nichandle.exception.EmptyPasswordException;
import pl.nask.crs.nichandle.exception.MissingRequiredCharacterTypeException;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.security.authentication.*;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

public class ResellerAppServiceEndpoint extends WsSessionAware implements CRSResellerAppService {
    private AccountAppService service;
    private AccountSearchService searchService;

    private final static Logger LOG = Logger.getLogger(ResellerAppServiceEndpoint.class);

    /* (non-Javadoc)
    * @see pl.nask.crs.api.account.CRSResellerAppService#get(pl.nask.crs.api.vo.AuthenticatedUserVO, long)
    */
    @Override
    public AccountVO get(AuthenticatedUserVO user, long id)
            throws AccessDeniedException, AccountNotFoundException, UserNotAuthenticatedException,
            InvalidSessionTokenException, SessionExpiredException, AuthenticationException {
        ValidationHelper.validate(user);
        AuthenticatedUser completeUser = validateSessionAndRetrieveFullUserInfo(user, false);
        return new AccountVO(service.get(completeUser, id));
    }

    @Override
    public List<InternalRegistrarVO> getRegistrarsForLogin(AuthenticatedUserVO user)
            throws UserNotAuthenticatedException, InvalidSessionTokenException, SessionExpiredException {
        return toInternalRegistrarVO(searchService.getRegistrarsForLogin());
    }

    @Override
    public NewAccountVO createDirectAccount(NicHandleEditVO nicHandleDetails, String password, boolean useTfa,
            String remoteAddress) throws AccountNotFoundException, NicHandleNotFoundException, NicHandleEmailException,
            AccountNotActiveException, EmptyRemarkException, PasswordAlreadyExistsException, InvalidCountryException,
            InvalidCountyException, ExportException, EmptyPasswordException, MissingRequiredCharacterTypeException,
            InvalidEmailException, LoginException {
        NewAccount newAccount = service.createDirectAccount(nicHandleDetails.toNewNicHandle(), password, useTfa);
        AuthenticatedUser user;
        try {
            user = authenticationService.authenticate(newAccount.getNicHandleId(), password, false, remoteAddress,
                    false, null, false, "ws");
        } catch (Exception e) {
            LOG.warn("Failed to log in as the new user. User will be asked to log in manually.");
            String errorMessage = String.format("Your account was created, but we failed to automatically log you in. "
                    + "Please go to the Login page and use the following Account Number (Nic-Handle) to log in: %s.",
                    newAccount.getNicHandleId());
            if (useTfa) {
                errorMessage += String.format(" You chose to enable TFA, so you will also need this code to generate"
                                + "your PIN: %s.", newAccount.getSecret());
            }
            throw new LoginException(errorMessage);
        }
        return new NewAccountVO(user, newAccount.getSecret());
    }

    /* (non-Javadoc)
     * @see pl.nask.crs.api.account.CRSResellerAppService#setService(pl.nask.crs.app.accounts.AccountAppService)
     */
    public void setService(AccountAppService accountAppService) {
        this.service = accountAppService;
    }

    public void setSearchService(AccountSearchService searchService) {
        this.searchService = searchService;
    }

    private List<InternalRegistrarVO> toInternalRegistrarVO(List<InternalRegistrar> internalRegistrars) {
        if (Validator.isEmpty(internalRegistrars)) {
            return Collections.emptyList();
        } else {
            List<InternalRegistrarVO> ret = new ArrayList<InternalRegistrarVO>(internalRegistrars.size());
            for (InternalRegistrar registrar : internalRegistrars) {
                ret.add(new InternalRegistrarVO(registrar));
            }
            return ret;
        }
    }
}
