package pl.nask.crs.app.commons.register;

import pl.nask.crs.domains.exceptions.DomainValidationException;

public class CharityRegistrationNotPossibleException extends DomainValidationException {

    private static final long serialVersionUID = -8831607942267220851L;

    public CharityRegistrationNotPossibleException(String domainName) {
        super("Charity reservation not possible for domain", domainName);
    }
}
