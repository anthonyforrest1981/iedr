package pl.nask.crs.secondarymarket.exceptions;

import pl.nask.crs.domains.exceptions.DomainValidationException;

public class SellRequestExistsException extends DomainValidationException {

    public SellRequestExistsException(String domainName) {
        super("Sell request exists for the domain", domainName);
    }

}
