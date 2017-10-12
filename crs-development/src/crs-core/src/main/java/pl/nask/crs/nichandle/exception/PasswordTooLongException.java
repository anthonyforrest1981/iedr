package pl.nask.crs.nichandle.exception;

public class PasswordTooLongException extends PasswordValidationException {

    public PasswordTooLongException() {
        super("Password must contain 16 characters at most");
    }
}
