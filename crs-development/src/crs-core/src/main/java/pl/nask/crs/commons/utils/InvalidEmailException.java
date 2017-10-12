package pl.nask.crs.commons.utils;

import pl.nask.crs.commons.dns.exceptions.InvalidDomainNameException;

public class InvalidEmailException extends Exception {

    public enum Reason {
        NULL_EMAIL_ADDRESS,
        EMAIL_ADDRESS_TOO_LONG,
        NO_AT_MARK,
        TOO_MANY_AT_MARKS,
        NULL_USER_SEGMENT,
        USER_SEGMENT_TOO_LONG,
        NON_ASCII_USER_SEGMENT,
        NULL_DOMAIN_SEGMENT,
        DOMAIN_PATTERN_MISMATCH,
        DOMAIN_NAME_TOO_LONG,
        DOMAIN_LABEL_TOO_LONG,
        DOMAIN_LABEL_EMPTY,
        LABEL_LAST_CHAR_NOT_LETTER_OR_DIGIT,
        LABEL_FIRST_CHAR_NOT_LETTER_OR_DIGIT,
        NOT_VALID_TOP_LEVEL_DOMAIN,
        CANNOT_CONVERT_TO_PUNYCODE,
        UNKNOWN_DOMAIN_ERROR
    }

    private String email;
    private Reason reason;

    public InvalidEmailException(String email, Reason reason) {
        this.email = email;
        this.reason = reason;
    }

    public InvalidEmailException(String email, InvalidDomainNameException e) {
        this.email = email;
        switch (e.getReason()) {
            case NULL_NAME:
                this.reason = Reason.NULL_DOMAIN_SEGMENT;
                break;
            case PATTERN_MISMATCH:
                this.reason = Reason.DOMAIN_PATTERN_MISMATCH;
                break;
            case NAME_TOO_LONG:
                this.reason = Reason.DOMAIN_NAME_TOO_LONG;
                break;
            case LABEL_TOO_LONG:
                this.reason = Reason.DOMAIN_LABEL_TOO_LONG;
                break;
            case LABEL_EMPTY:
                this.reason = Reason.DOMAIN_LABEL_EMPTY;
                break;
            case LABEL_LAST_CHAR_NOT_LETTER_OR_DIGIT:
                this.reason = Reason.LABEL_LAST_CHAR_NOT_LETTER_OR_DIGIT;
                break;
            case LABEL_FIRST_CHAR_NOT_LETTER_OR_DIGIT:
                this.reason = Reason.LABEL_FIRST_CHAR_NOT_LETTER_OR_DIGIT;
                break;
            case NOT_VALID_TOP_LEVEL_DOMAIN:
                this.reason = Reason.NOT_VALID_TOP_LEVEL_DOMAIN;
                break;
            case CANNOT_CONVERT_TO_PUNYCODE:
                this.reason = Reason.CANNOT_CONVERT_TO_PUNYCODE;
                break;
            default:
                this.reason = Reason.UNKNOWN_DOMAIN_ERROR;
        }
    }

    public String getEmail() {
        return this.email;
    }

    public Reason getReason() {
        return this.reason;
    }

    public String getMessage() {
        return "email: [" + email + "] is invalid [" + reason + "]";
    }
}
