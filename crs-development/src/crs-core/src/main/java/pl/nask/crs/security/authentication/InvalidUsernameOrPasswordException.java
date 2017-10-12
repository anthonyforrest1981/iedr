package pl.nask.crs.security.authentication;

/**
 * Exception thrown when username given during authentication in not found in the system.
 *
 * @author Marianna Mysiorska
 */
public class InvalidUsernameOrPasswordException extends LoginException {

    private String username;

    public InvalidUsernameOrPasswordException(String username) {
        this.username = username;
    }

    public InvalidUsernameOrPasswordException(String message, String username) {
        super(message);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
