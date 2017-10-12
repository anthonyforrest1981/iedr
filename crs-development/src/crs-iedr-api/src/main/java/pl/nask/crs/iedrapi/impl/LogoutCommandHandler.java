package pl.nask.crs.iedrapi.impl;

import org.apache.log4j.Logger;

import pl.nask.crs.iedrapi.*;
import pl.nask.crs.iedrapi.exceptions.CommandUseError;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.security.authentication.AccessDeniedException;

import ie.domainregistry.ieapi_1.ResponseType;

public class LogoutCommandHandler implements APISessionAwareCommandHandler<Object> {
    private static final Logger log = Logger.getLogger(LogoutCommandHandler.class);

    @Override
    public ResponseType handle(AuthData auth, Object command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        ApiValidator.assertNoError(callback);
        boolean newSess = !auth.isUserLoggedIn();
        auth.logout();
        log.info("Logging out");
        if (newSess) // logout can be only called on running session
            throw new CommandUseError();

        return ResponseTypeFactory.createCCSEndSession();
    }

}
