package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.domains.exceptions.DomainValidationException;

public class DomainNameSyntaxException extends DomainValidationException {

    public DomainNameSyntaxException(String domainName) {
        super("Invalid syntax of a domain", domainName);
    }
}
