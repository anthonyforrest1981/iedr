package pl.nask.crs.commons.dns;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import pl.nask.crs.commons.dns.exceptions.InvalidIPv4AddressException;
import pl.nask.crs.commons.dns.exceptions.InvalidIPv6AddressException;

/**
 * @author Patrycja Wegrzynowicz
 * @author Piotr Tkaczyk
 */
public class IPAddressValidator {

    public static final String IPV4_REGEXP = "^((25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2}))\\.((25[0-5]|2[0-4][0-9]|[0-1]?[0-9\n"
            + "]{1,2}))\\.((25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2}))\\.((25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2}))$";

    public static void validateIPv4(String address) throws InvalidIPv4AddressException {
        if (address == null)
            throw new IllegalArgumentException("empty ipv4 address");
        if (!Pattern.matches(IPV4_REGEXP, address))
            throw new InvalidIPv4AddressException(address);
    }

    public static void validateIPv6(String address) throws InvalidIPv6AddressException {
        if (address == null)
            throw new IllegalArgumentException("empty ipv6 address");
        try {
            int doubleColonPosition = address.indexOf("::");
            if (doubleColonPosition == -1) {
                List<String> pieces = Arrays.asList(address.split(":"));
                if (pieces.size() == 7) { // possibility of ipv4 on end
                    for (Iterator iterator = pieces.iterator(); iterator.hasNext();) {
                        String piece = (String) iterator.next();
                        if (!validateHEX4(piece)) {
                            if (!iterator.hasNext())
                                validateIPv4(piece);
                            else
                                throw new InvalidIPv6AddressException(address);
                        }
                    }
                } else {
                    if (pieces.size() != 8)
                        throw new InvalidIPv6AddressException(address);

                    for (String piece : pieces)
                        if (!validateHEX4(piece))
                            throw new InvalidIPv6AddressException(address);
                }
            } else {
                if (address.indexOf("::", doubleColonPosition + 1) != -1)
                    throw new InvalidIPv6AddressException(address);
                List<String> pieces = Arrays.asList(address.split("::"));
                if (pieces.size() != 0) // if not "::" address
                    for (Iterator piecesIterator = pieces.iterator(); piecesIterator.hasNext();) {
                        List<String> parts = Arrays.asList(((String) piecesIterator.next()).split(":"));
                        for (Iterator partsIterator = parts.iterator(); partsIterator.hasNext();) {
                            String part = (String) partsIterator.next();
                            if (!validateHEX4(part))
                                if (!partsIterator.hasNext() && !piecesIterator.hasNext())
                                    validateIPv4(part);
                                else
                                    throw new InvalidIPv6AddressException(address);
                        }
                    }
            }
        } catch (InvalidIPv4AddressException e) {
            throw new InvalidIPv6AddressException(address);
        }
    }

    private static boolean validateHEX4(String hex4) {
        if (hex4.length() > 4)
            return false;
        try {
            if (hex4.length() != 0)
                Integer.parseInt(hex4, 16);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
