package pl.nask.crs.security.authentication;

public class LoginException extends AuthenticationException {
    public LoginException() {}

    public LoginException(String message) {
        super(message);
    }
}
