package pl.nask.crs.commons.dns.exceptions;

import pl.nask.crs.app.ValidationException;

/**
 * @author: Marcin Tkaczyk
 */
public class TooManyNameserversException extends ValidationException {
    private final int maxAllowedNameserversCount;

    public TooManyNameserversException(int max) {
        maxAllowedNameserversCount = max;
    }

    @Override
    public String getMessage() {
        return "Too many nameservers, at most " + maxAllowedNameserversCount + " allowed";
    }
}
