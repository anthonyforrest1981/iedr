package pl.nask.crs.domains.exceptions;

public class DomainLockingRenewalDateOutOfBoundsException extends DomainValidationException {

    public DomainLockingRenewalDateOutOfBoundsException(String message, String domainName) {
        super(message, domainName);
    }
}
