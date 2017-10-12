package pl.nask.crs.nichandle.service.impl.helper;

import org.testng.annotations.Test;

import pl.nask.crs.nichandle.exception.*;

public class PasswordHelperTest {

    @Test
    public void validatePasswordComplex() throws Exception {
        PasswordHelper.validatePassword("qwe123qweE123.", "qwe123qweE123.");
    }

    @Test
    public void validatePasswordSimple() throws Exception {
        PasswordHelper.validatePassword("123qweQW.", "123qweQW.");
    }

    @Test
    public void validatePasswordNonAlphanum() throws Exception {
        PasswordHelper.validatePassword("1qQ:_-.#@|", "1qQ:_-.#@|");
        PasswordHelper.validatePassword("1qQ!$%&*+/", "1qQ!$%&*+/");
    }

    @Test(expectedExceptions = MissingRequiredCharacterTypeException.class)
    public void validatePasswordTooEasyNoSpecialChar() throws Exception {
        PasswordHelper.validatePassword("123qweQW", "123qweQW");
    }

    @Test(expectedExceptions = PasswordTooShortException.class)
    public void validatePasswordTooShort() throws Exception {
        PasswordHelper.validatePassword("12qwQW.", "12qwQW.");
    }

    @Test(expectedExceptions = PasswordTooLongException.class)
    public void validatePasswordTooLong() throws Exception {
        PasswordHelper.validatePassword("1234qwertyQWERTY.", "1234qwertyQWERTY.");
    }

    @Test(expectedExceptions = PasswordsDontMatchException.class)
    public void validatePasswordDontMatch() throws Exception {
        PasswordHelper.validatePassword("123qweQW.", "123qweQW..");
    }

    @Test(expectedExceptions = MissingRequiredCharacterTypeException.class)
    public void validatePasswordTooEasyNoUpperCase() throws Exception {
        PasswordHelper.validatePassword("123qwerty.", "123qwerty.");
    }

    @Test(expectedExceptions = MissingRequiredCharacterTypeException.class)
    public void validatePasswordTooEasyNoDigit() throws Exception {
        PasswordHelper.validatePassword("Qqweqwee.", "Qqweqwee.");
    }

    @Test(expectedExceptions = MissingRequiredCharacterTypeException.class)
    public void validatePasswordTooEasyNoLowercase() throws Exception {
        PasswordHelper.validatePassword("QWEQWEQ1.", "QWEQWEQ1.");
    }

    @Test(expectedExceptions = InvalidCharacterInPasswordException.class)
    public void validatePasswordWithNonAsciiLetter() throws Exception {
        PasswordHelper.validatePassword("11111Qaá1.", "11111Qaá1.");
    }

    @Test(expectedExceptions = InvalidCharacterInPasswordException.class)
    public void validatePasswordWithCaret() throws Exception {
        PasswordHelper.validatePassword("11111Qa1^", "11111Qa1^");
    }

    @Test(expectedExceptions = InvalidCharacterInPasswordException.class)
    public void validatePasswordWithPoundSymbol() throws Exception {
        PasswordHelper.validatePassword("11111Qa1£", "11111Qa1£");
    }

    @Test(expectedExceptions = EmptyPasswordException.class)
    public void validateEmpotyPassword() throws Exception {
        PasswordHelper.validatePassword("", "");
    }

}
