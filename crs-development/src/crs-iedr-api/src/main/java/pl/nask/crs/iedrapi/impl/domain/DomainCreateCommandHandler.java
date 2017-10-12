package pl.nask.crs.iedrapi.impl.domain;

import pl.nask.crs.api.vo.RegistrationRequestVO;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_domain_1.CreateType;

/**
 * @author: Marcin Tkaczyk
 */
public class DomainCreateCommandHandler extends AbstractDomainCommandHandler<CreateType> {

    public ResponseType handle(AuthData auth, CreateType command, ValidationCallback callback) throws IedrApiException {
        AuthenticatedUser user = auth.getUser();

        DomainValidationHelper.commandPlainCheck(command);
        ApiValidator.assertNoError(callback);
        try {
            RegistrationRequestVO request = prepareRegistrationRequest(user, command);
            long ticketId = getCommonAppService().registerDomain(user, request, null);
            return ResponseTypeFactory.success(prepareResponse(command.getName().toLowerCase(), ticketId));
        } catch (CardPaymentException e) {
            throw mapPaymentException(e);
        } catch (Exception e) {
            throw mapException(e);
        }
    }
}
