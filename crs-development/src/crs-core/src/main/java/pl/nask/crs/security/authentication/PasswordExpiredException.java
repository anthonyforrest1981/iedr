package pl.nask.crs.security.authentication;

/**
 * Exception thrown during authentication when the password has expired.
 *
 * @author Marianna Mysiorska
 */
public class PasswordExpiredException extends LoginException {
    public PasswordExpiredException() {}

    public PasswordExpiredException(String message) {
        super(message);
    }
}
