package pl.nask.crs.nichandle.exception;

public class PasswordTooShortException extends PasswordValidationException {

    public PasswordTooShortException() {
        super("Password must contain 8 characters at least");
    }
}
