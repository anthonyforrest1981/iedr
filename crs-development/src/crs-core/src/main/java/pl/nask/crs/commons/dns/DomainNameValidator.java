package pl.nask.crs.commons.dns;

import java.net.IDN;
import java.util.Arrays;
import java.util.List;

import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.dns.exceptions.InvalidDomainNameException;
import pl.nask.crs.domains.services.TopLevelDomainService;

import static pl.nask.crs.commons.dns.exceptions.InvalidDomainNameException.Reason.*;

/**
 * It validates whether a given name is a valid domain name according to RFC
 * 1034 syntax or fully qualified domain name.
 */
@SuppressWarnings("nullness:initialization.fields.uninitialized")
public class DomainNameValidator {

    private static TopLevelDomainService tld;
    private static ApplicationConfig config;

    public void setTopLevelDomainService(TopLevelDomainService tld) {
        DomainNameValidator.tld = tld;
    }

    public void setApplicationConfig(ApplicationConfig config) {
        DomainNameValidator.config = config;
    }

    private static final String ASCII_DOMAIN_PATTERN = "([a-z0-9\\-]+\\.)+[a-z0-9\\-]+";
    private static final String IE_IDN_DOMAIN_PATTERN = "^[a-z0-9áéíóú\\-]+\\.ie$";
    private static final String IE_DOMAIN_PATTERN = "^[a-z0-9\\-]+\\.ie$";

    private static final String TWO_LETTER_IE_DOMAIN = "^[a-záéíóú]{2}\\.ie$";
    private static final String ONE_CHAR_IE_DOMAIN = "^[a-z0-9áéíóú]\\.ie$";

    // There is some confusion about 253 (max visible length excluding trailing dot), 254
    // (max length including trailing dot) and 255 (max specs length).
    // TL;DR: 255 is number of octets, domain name is encoded as series of segments and ends with root segment
    //        (segment with 0 length). Each segment is encoded as [LENGTH, ...CHARACTERS], final optional dot
    //        is a shorthand for final zero-length segment. E.g. www.wp.pl is encoded as
    //        3 W W W 2 W P 2 P L 0
    //        Easy to see that www.wp.pl uses two less 'characters' than full encoding (misses first '3' and last '0').
    //        So without the trailing dot max length is 253, with it 254 (255th octet will be the octet to spell
    //        the length of the first segment).
    // More: https://blogs.msdn.microsoft.com/oldnewthing/20120412-00/?p=7873/
    //       http://www.freesoft.org/CIE/RFC/1035/9.htm
    //
    private static final int MAX_DOMAINNAME_LENGTH = 253;

    private DomainNameValidator() {}

    public static void validateIedrName(String domainName) throws InvalidDomainNameException {
        if (!domainName.toLowerCase().equals(domainName)) {
            throw new InvalidDomainNameException(domainName, UPPERCASE_LETTERS);
        }
        domainName = getValidatedCanonicalName(domainName);
        final String pattern = config.isIDNDomainAllowed() ? IE_IDN_DOMAIN_PATTERN : IE_DOMAIN_PATTERN;
        if (!domainName.matches(pattern)) {
            throw new InvalidDomainNameException(domainName, NOT_VALID_IEDR_NAME);
        }
        if (!config.isOneOrTwoLetterDomainAllowed()) {
            if (domainName.matches(ONE_CHAR_IE_DOMAIN))
                throw new InvalidDomainNameException(domainName, NOT_VALID_IEDR_NAME);
            if (domainName.matches(TWO_LETTER_IE_DOMAIN))
                throw new InvalidDomainNameException(domainName, NOT_VALID_IEDR_NAME);
        }
        // Looks like punycode
        if (domainName.charAt(2) == '-' && domainName.charAt(3) == '-')
            throw new InvalidDomainNameException(domainName, NOT_VALID_IEDR_NAME);
    }

    public static void validateName(String domainName) throws InvalidDomainNameException {
        getValidatedCanonicalName(domainName);
    }

    public static String getValidatedCanonicalName(String domainName) throws InvalidDomainNameException {
        String canonicalName = getNonEmptyCanonicalName(domainName);
        String punycode = getPunycode(canonicalName);
        validatePunycode(punycode, canonicalName);
        return canonicalName;
    }

    public static String getValidatedPunycodeName(String domainName) throws InvalidDomainNameException {
        String canonicalName = getNonEmptyCanonicalName(domainName);
        String punycode = getPunycode(canonicalName);
        validatePunycode(punycode, canonicalName);
        return punycode;
    }

    private static void validatePunycode(String punycode, String nameToLog) throws InvalidDomainNameException {
        List<String> topLevelDomains = tld.getAll();

        if (!punycode.matches(ASCII_DOMAIN_PATTERN))
            throw new InvalidDomainNameException(nameToLog, PATTERN_MISMATCH);

        if (punycode.length() > MAX_DOMAINNAME_LENGTH)
            throw new InvalidDomainNameException(nameToLog, NAME_TOO_LONG);

        List<String> pieces = Arrays.asList(punycode.split("\\."));

        if (!topLevelDomains.contains(pieces.get(pieces.size() - 1)))
            throw new InvalidDomainNameException(nameToLog, NOT_VALID_TOP_LEVEL_DOMAIN);

        for (String piece : pieces) {
            if (piece.length() == 0)
                throw new InvalidDomainNameException(nameToLog, LABEL_EMPTY);

            if (piece.length() > 63)
                throw new InvalidDomainNameException(nameToLog, LABEL_TOO_LONG);

            if (!Character.isLetterOrDigit(piece.charAt(piece.length() - 1)))
                throw new InvalidDomainNameException(nameToLog, LABEL_LAST_CHAR_NOT_LETTER_OR_DIGIT);

            if (!Character.isLetterOrDigit(piece.charAt(0)))
                throw new InvalidDomainNameException(nameToLog, LABEL_FIRST_CHAR_NOT_LETTER_OR_DIGIT);

        }
    }

    public static String getCanonicalName(String name) {
        if (name == null)
            return null;
        name = name.toLowerCase();
        if (name.endsWith("."))
            name = name.substring(0, name.length() - 1);
        return name;
    }

    private static String getNonEmptyCanonicalName(String name) throws InvalidDomainNameException {
        String canonicalName = getCanonicalName(name);
        if (canonicalName == null || canonicalName.length() == 0) {
            throw new InvalidDomainNameException(null, NULL_NAME);
        }
        return canonicalName;
    }

    public static String getPunycode(String name) throws InvalidDomainNameException {

        try {
            return IDN.toASCII(name);
        } catch (Exception e) {
            throw new InvalidDomainNameException(name, CANNOT_CONVERT_TO_PUNYCODE);
        }
    }

}
