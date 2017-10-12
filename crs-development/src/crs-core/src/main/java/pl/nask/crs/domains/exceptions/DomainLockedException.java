package pl.nask.crs.domains.exceptions;

public class DomainLockedException extends DomainValidationException {
    private static final long serialVersionUID = 1866017080867208927L;

    public DomainLockedException(String domainName) {
        super("Domain is locked", domainName);
    }
}
