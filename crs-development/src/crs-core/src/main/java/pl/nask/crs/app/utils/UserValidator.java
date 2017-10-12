package pl.nask.crs.app.utils;

import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;

public class UserValidator {

    private UserValidator() {}

    public static void validateLoggedIn(AuthenticatedUser user) throws UserNotAuthenticatedException {
        if (user == null) {
            throw new UserNotAuthenticatedException();
        }
    }
}
