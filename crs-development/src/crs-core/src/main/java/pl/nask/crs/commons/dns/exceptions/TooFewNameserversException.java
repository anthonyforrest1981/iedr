package pl.nask.crs.commons.dns.exceptions;

import pl.nask.crs.app.ValidationException;

public class TooFewNameserversException extends ValidationException {
    private final int minRequiredCount;

    public TooFewNameserversException(int nameserverMinCount) {
        this.minRequiredCount = nameserverMinCount;
    }

    @Override
    public String getMessage() {
        return "Too few nameservers, at least " + minRequiredCount + " required";
    }
}
