package pl.nask.crs.iedrapi.impl.domain;

import java.util.List;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.*;
import pl.nask.crs.security.authentication.AccessDeniedException;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_domain_1.MNameType;

/**
 * @author: Artur Gniadzik
 */
public class DomainNrpCommandHandler extends AbstractDomainCommandHandler<MNameType> {

    public ResponseType handle(AuthData auth, MNameType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {

        DomainValidationHelper.commandPlainCheck(command);
        ApiValidator.assertNoError(callback);

        Long accId = getAccountId(auth.getUser());
        List<String> names = command.getName();
        for (String domainName : command.getName()) {
            Domain res = findDomain(auth.getUser(), domainName);
            validateAccountId(accId, res);
        }

        try {
            getDomainAppService().enterVoluntaryNRP(auth.getUser(), names.toArray(new String[names.size()]));
            return ResponseTypeFactory.success();
        } catch (DomainIllegalStateException e) {
            throw new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_VNRP_NOT_POSSIBLE, new Value("name",
                    DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (Exception e) {
            //TODO: what about thrown expections, like NicHandleNotActiveException?
            throw new CommandFailed(e);
        }

    }
}
