package pl.nask.crs.nichandle.exception;

public class InvalidCharacterInPasswordException extends PasswordValidationException {

    public InvalidCharacterInPasswordException() {
        super(
                "Password may contain only letters in upper and lower case, digits and non-alphanumeric characters:_-.#@|!$%&*+/)");
    }
}
