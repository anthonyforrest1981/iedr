package pl.nask.crs.security.authentication.impl;

import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;

/**
 * An authentication always succeeds.
 *
 * @author Patrycja Wegrzynowicz
 */
public class EmptyAuthenticationService implements AuthenticationService {

    @Override
    public AuthenticatedUser authenticate(String username, String password, boolean validateExpiration,
            String remoteAddress, boolean useLoginLock, String code, boolean useTfa, String systemDiscriminator) {
        return new AuthenticatedUserImpl("IDL2-IEDR");
    }

}
