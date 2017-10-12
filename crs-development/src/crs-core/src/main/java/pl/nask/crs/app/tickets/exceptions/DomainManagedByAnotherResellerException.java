package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.domains.exceptions.DomainValidationException;

public class DomainManagedByAnotherResellerException extends DomainValidationException {

    private static final long serialVersionUID = 6518934161901064217L;

    public DomainManagedByAnotherResellerException(String domainName) {
        super("Domain managed by another reseller", domainName);
    }
}
