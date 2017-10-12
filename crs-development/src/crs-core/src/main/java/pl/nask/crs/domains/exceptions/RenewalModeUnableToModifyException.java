package pl.nask.crs.domains.exceptions;

public class RenewalModeUnableToModifyException extends DomainValidationException {
    private static final long serialVersionUID = 1866017080867208927L;

    public RenewalModeUnableToModifyException(String domainName) {
        super("Current domain state prohibits renewal mode modification for a domain", domainName);
    }
}
