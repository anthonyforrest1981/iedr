package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.domains.exceptions.DomainValidationException;

public class DomainModificationPendingException extends DomainValidationException {

    private static final long serialVersionUID = 4843837238334621182L;

    public DomainModificationPendingException(String domainName) {
        super("Modification ticket pending for domain", domainName);
    }
}
