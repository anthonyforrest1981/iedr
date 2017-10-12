package pl.nask.crs.iedrapi.impl.contact;

import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.app.InvalidAccountNumberException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.iedrapi.APICommandHandler;
import pl.nask.crs.iedrapi.exceptions.*;
import pl.nask.crs.iedrapi.impl.AbstractCommandHandler;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.xml.Constants;

public abstract class AbstractContactCommandHandler<T> extends AbstractCommandHandler implements APICommandHandler<T> {

    public static final String CONTACT_NAMESPACE = Constants.IEAPI_CONTACT_NAMESPACE;

    public NicHandle getNicHandle(AuthenticatedUserVO user, String id) throws AccessDeniedException, IedrApiException {
        if (Validator.isEmpty(id))
            throw new RequiredParameterMissingException(ReasonCode.CONTACT_ID_REQUIRED);
        try {
            return getNicHandleAppService().get(user, id);
        } catch (InvalidAccountNumberException e) {
            throw new AuthorizationErrorException(ReasonCode.CONTACT_MANAGED_BY_ANOTHER_RESELLER, new Value("id",
                    CONTACT_NAMESPACE, id), e);
        } catch (NicHandleNotFoundException e) {
            throw new ObjectDoesNotExistException(ReasonCode.CONTACT_ID_DOES_NOT_EXIST, new Value("id",
                    CONTACT_NAMESPACE, e.getNicHandleId()));
        }
    }

}
