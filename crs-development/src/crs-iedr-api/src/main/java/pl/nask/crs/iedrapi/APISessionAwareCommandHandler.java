package pl.nask.crs.iedrapi;

import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.security.authentication.AccessDeniedException;

import ie.domainregistry.ieapi_1.ResponseType;

public interface APISessionAwareCommandHandler<T> {
    public ResponseType handle(AuthData auth, T command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException;
}
