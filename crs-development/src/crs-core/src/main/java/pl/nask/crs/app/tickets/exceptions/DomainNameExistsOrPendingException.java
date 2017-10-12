package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.domains.exceptions.DomainValidationException;

public class DomainNameExistsOrPendingException extends DomainValidationException {
    private static final long serialVersionUID = 1276620297251528324L;

    public DomainNameExistsOrPendingException(String domainName) {
        super("Domain name cannot be used", domainName);
    }
}
