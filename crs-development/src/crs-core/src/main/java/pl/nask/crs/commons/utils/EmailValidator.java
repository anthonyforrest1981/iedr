package pl.nask.crs.commons.utils;

import java.util.Arrays;
import java.util.List;

import pl.nask.crs.commons.dns.DomainNameValidator;
import pl.nask.crs.commons.dns.exceptions.InvalidDomainNameException;

import static pl.nask.crs.commons.utils.InvalidEmailException.Reason.*;

public final class EmailValidator {
    private static final String USER_SEGMENT_PATTERN = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*";
    private static final int MAX_USER_SEGMENT_LENGTH = 64;
    private static final int MAX_TOTAL_EMAIL_LENGTH = 254;

    private EmailValidator() {}

    public static void validateEmail(String email) throws InvalidEmailException {
        if (email == null)
            throw new InvalidEmailException(email, NULL_EMAIL_ADDRESS);
        email = email.toLowerCase();
        email = email.trim();
        List<String> segments = Arrays.asList(email.split("@", -1));
        if (segments.size() < 2) {
            throw new InvalidEmailException(email, NO_AT_MARK);
        }
        if (segments.size() > 2) {
            throw new InvalidEmailException(email, TOO_MANY_AT_MARKS);
        }
        String userSegment = segments.get(0);
        String domainSegment = segments.get(1);
        if (userSegment.isEmpty()) {
            throw new InvalidEmailException(email, NULL_USER_SEGMENT);
        }
        if (userSegment.length() > MAX_USER_SEGMENT_LENGTH) {
            throw new InvalidEmailException(email, USER_SEGMENT_TOO_LONG);
        }
        if (!userSegment.matches(USER_SEGMENT_PATTERN)) {
            throw new InvalidEmailException(email, NON_ASCII_USER_SEGMENT);
        }
        if (domainSegment.isEmpty()) {
            throw new InvalidEmailException(email, NULL_DOMAIN_SEGMENT);
        }
        try {
            String punycode = DomainNameValidator.getValidatedPunycodeName(domainSegment);
            if ((userSegment + "@" + punycode).length() > MAX_TOTAL_EMAIL_LENGTH) {
                throw new InvalidEmailException(email, EMAIL_ADDRESS_TOO_LONG);
            }
        } catch (InvalidDomainNameException e) {
            throw new InvalidEmailException(email, e);
        }
    }
}
