package pl.nask.crs.commons.dns.exceptions;

import pl.nask.crs.app.ValidationException;

public class InvalidDomainNameException extends ValidationException {

    public enum Reason {
        NULL_NAME,
        PATTERN_MISMATCH,
        NAME_TOO_LONG,
        LABEL_TOO_LONG,
        LABEL_EMPTY,
        LABEL_LAST_CHAR_NOT_LETTER_OR_DIGIT,
        LABEL_FIRST_CHAR_NOT_LETTER_OR_DIGIT,
        NOT_VALID_IEDR_NAME,
        TOO_MANY_LABELS,
        NOT_VALID_TOP_LEVEL_DOMAIN,
        CANNOT_CONVERT_TO_PUNYCODE,
        UPPERCASE_LETTERS
    }

    private String name;
    private Reason reason;

    public InvalidDomainNameException(String name, Reason reason) {
        this.name = name;
        this.reason = reason;
    }

    public String getName() {
        return this.name;
    }

    public Reason getReason() {
        return this.reason;
    }

    public String getMessage() {
        return "name: [" + name + "] is invalid [" + reason + "]";
    }
}
