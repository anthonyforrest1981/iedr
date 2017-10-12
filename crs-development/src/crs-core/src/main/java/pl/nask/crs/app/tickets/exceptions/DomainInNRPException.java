package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.domains.exceptions.DomainValidationException;

public class DomainInNRPException extends DomainValidationException {

    private static final long serialVersionUID = 7163664405643289936L;

    public DomainInNRPException(String domainName) {
        super("Domain in NRP", domainName);
    }
}
