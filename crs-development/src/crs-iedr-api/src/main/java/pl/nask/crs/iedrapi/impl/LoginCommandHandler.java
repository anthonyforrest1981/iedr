package pl.nask.crs.iedrapi.impl;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.iedrapi.*;
import pl.nask.crs.iedrapi.exceptions.*;
import pl.nask.crs.nichandle.exception.*;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.user.exceptions.PasswordAlreadyExistsException;

import ie.domainregistry.ieapi_1.LoginType;
import ie.domainregistry.ieapi_1.ResponseType;

public class LoginCommandHandler extends AbstractCommandHandler implements APISessionAwareCommandHandler<LoginType> {
    private static final Logger log = Logger.getLogger(LoginCommandHandler.class);

    @Override
    public ResponseType handle(AuthData auth, LoginType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        if (auth.isUserLoggedIn()) {
            throw new CommandUseError();
        }
        try {
            try {
                String username = command.getClID();
                String password = command.getPw();
                String newPassword = command.getNewPW();
                // TODO: check the code (is session id saved into db?)
                ApiValidator.paramNotEmpty("clid", username);

                IedrApiConfig.getUserAwareAppender().registerAppender(username);

                ApiValidator.paramNotEmpty("pw", password);
                ApiValidator.assertNoError(callback);

                AuthenticatedUser user = getAuthService().authenticate(username, password, false,
                        auth.getRemoteAddr(), false, null, false, "api");

                if (!Validator.isEmpty(command.getNewPW())) {
                    getNicHandleAppService().saveNewPassword(user, username, newPassword, newPassword);
                }

                auth.login(user);
                return ResponseTypeFactory.success();

            } catch (AuthenticationException | NicHandleNotFoundException e) {
                throw new ApiAuthenticationException(e);
            } catch (PasswordValidationException e) {
                throw new ParamValuePolicyErrorException(ReasonCode.NEW_PASSWORD_TO_EASY);
            } catch (NicHandleEmailException e) {
                log.warn("email exeption", e);
                // success - ignore errors while sending an email
                return ResponseTypeFactory.successNoRes();
            } catch (PasswordAlreadyExistsException e) {
                throw new ParamValuePolicyErrorException(ReasonCode.NEW_PASSWORD_EQUALS_TO_CURRENT);
            } catch (EmptyPasswordException | PasswordsDontMatchException e) {
                // shall never happen
                throw new CommandFailed(e);
            }

        } catch (IedrApiException e) {
            auth.logout();
            throw e;
        }
    }

}
