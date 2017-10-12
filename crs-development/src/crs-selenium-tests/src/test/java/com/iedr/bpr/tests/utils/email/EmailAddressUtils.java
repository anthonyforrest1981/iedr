package com.iedr.bpr.tests.utils.email;

import com.iedr.bpr.tests.SeleniumTest;

import java.util.*;

import static com.iedr.bpr.tests.TestEnvironment.browser;

public class EmailAddressUtils {

    public static List<String> getInvalidEmailAddressList() {
        return new ArrayList<>(getInvalidEmailAddressMap().keySet());
    }

    public static Map<String, String> getInvalidEmailAddressMap() {
        Map<String, String> invalidEmails = new HashMap<>();
        invalidEmails.put("email.iedr.ie",  "Email must have exactly one \"@\""); // No @ char
        invalidEmails.put("em@il@iedr.ie", "Email must have exactly one \"@\""); // Two @ chars
        invalidEmails.put("@email.iedr.ie", "Email must not start with \"@\""); // @ as the first char
        invalidEmails.put("email.iedr.ie@", "Email has illegal domain segment (after \"@\")"); // @ as the last char
        invalidEmails.put("email@iedr,ie", "Email has illegal domain segment (after \"@\")"); // Incorrect char
        invalidEmails.put("email.iedr@ie", "Email has illegal domain segment (after \"@\")"); // One-segment domain
        invalidEmails.put("email@iedr.ii", "Email has illegal domain segment (after \"@\")"); // Invalid top level domain
        invalidEmails.put(getUserPartTooLong(), "Email has too long string before \"@\" (64 characters allowed)");
        invalidEmails.put(getDomainSegmentTooLong(), "Email has illegal domain segment (after \"@\")");
        invalidEmails.put(getWholeAddressTooLong(), "Email is too long (maximum is 254 characters)");
        if (!SeleniumTest.Browser.Edge.equals(browser())) {
            invalidEmails.put(getDomainSegmentTooLongInPunycode(), "Email has illegal domain segment (after \"@\")");
            invalidEmails.put(getWholeAddressTooLongInPunycode(),
                    "Email's punycode form is too long (maximum is 254 characters)");
        }
        return invalidEmails;
    }

    private static String getUserPartTooLong() {
        return String.format("%s@iedr.ie", getStringOfLength(65, 'a'));
    }

    private static String getDomainSegmentTooLong() {
        return String.format("user@%s.ie", getStringOfLength(64, 'a'));
    }

    private static String getWholeAddressTooLong() {
        // 5 + 3 * 64 + 56 + 2 = 255 > 254
        String segment63 = getStringOfLength(63, 'a');
        String segment55 = getStringOfLength(55, 'a');
        return String.format("user@%s.%s.%s.%s.ie", segment63, segment63, segment63, segment55);
    }

    private static String getDomainSegmentTooLongInPunycode() {
        return String.format("user@%s.ie", getStringOfLength(58, 'ó'));
    }

    private static String getWholeAddressTooLongInPunycode() {
        // text: 5 + 3 * 58 + 50 + 2 = 231 < 254
        // punycode: 5 + 3 * 64 + 56 + 2 = 255 > 254
        String segment63 = getStringOfLength(57, 'ó');
        String segment55 = getStringOfLength(49, 'ó');
        return String.format("user@%s.%s.%s.%s.ie", segment63, segment63, segment63, segment55);
    }

    private static String getStringOfLength(int length, char c) {
        char[] charArray = new char[length];
        Arrays.fill(charArray, c);
        return new String(charArray);
    }

}
