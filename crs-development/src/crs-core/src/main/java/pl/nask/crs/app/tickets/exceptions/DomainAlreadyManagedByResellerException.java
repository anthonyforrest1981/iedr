package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.domains.exceptions.DomainValidationException;

public class DomainAlreadyManagedByResellerException extends DomainValidationException {

    private static final long serialVersionUID = -4106977567448638388L;

    public DomainAlreadyManagedByResellerException(String domainName) {
        super("Domain already managed by reseller", domainName);
    }
}
