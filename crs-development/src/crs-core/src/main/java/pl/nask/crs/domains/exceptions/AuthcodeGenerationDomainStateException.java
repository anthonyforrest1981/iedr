package pl.nask.crs.domains.exceptions;

public class AuthcodeGenerationDomainStateException extends DomainValidationException {
    private static final long serialVersionUID = 1866017080867208927L;

    public AuthcodeGenerationDomainStateException(String domainName) {
        super("Cannot generate authcode for the domain", domainName);
    }
}
