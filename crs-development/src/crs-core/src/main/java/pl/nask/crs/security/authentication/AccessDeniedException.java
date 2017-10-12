package pl.nask.crs.security.authentication;

public class AccessDeniedException extends AuthenticationException {

    public AccessDeniedException() {
        super("Permission denied");
    }

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

}
