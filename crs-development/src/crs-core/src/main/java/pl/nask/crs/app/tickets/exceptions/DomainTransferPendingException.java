package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.domains.exceptions.DomainValidationException;

public class DomainTransferPendingException extends DomainValidationException {

    private static final long serialVersionUID = 5288043055293803664L;

    public DomainTransferPendingException(String domainName) {
        super("Transfer ticket pending for domain", domainName);
    }
}
