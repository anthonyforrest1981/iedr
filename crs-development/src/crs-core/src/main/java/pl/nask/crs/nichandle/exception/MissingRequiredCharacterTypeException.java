package pl.nask.crs.nichandle.exception;

public class MissingRequiredCharacterTypeException extends PasswordValidationException {

    public MissingRequiredCharacterTypeException() {
        super(
                "Password must contain letters in upper and lower case, a digit and a non-alphanumeric character (like :_-.#@|!$%&*+/)");
    }
}
