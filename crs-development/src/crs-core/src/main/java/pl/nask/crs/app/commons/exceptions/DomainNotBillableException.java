package pl.nask.crs.app.commons.exceptions;

import pl.nask.crs.domains.exceptions.DomainValidationException;

public class DomainNotBillableException extends DomainValidationException {

    private static final long serialVersionUID = -2302360736344942227L;

    public DomainNotBillableException(String domainName) {
        super("Domain is not billable", domainName);
    }

    public DomainNotBillableException(String domainName, String domainBillingStatus) {
        super("Domain is not billable", domainName, String.format("status is %s", domainBillingStatus));
    }
}
