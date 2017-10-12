package pl.nask.crs.web.validators;

import pl.nask.crs.commons.dns.DomainNameValidator;
import pl.nask.crs.commons.dns.exceptions.InvalidDomainNameException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.nameservers.Nameserver;

public class DomainNameserverNameValidator extends ComparableDomainNameserverValidator {

    public DomainNameserverNameValidator() {
        setDefaultMessage("Wrong ns name");
    }

    @Override
    protected void validateNameserver(Object object, Nameserver ns, String domainName, int index) {
        String value = ns.getName();
        if (Validator.isEmpty(value)) {
            addFieldError(getFieldName() + ".nameservers[" + index + "]" + ".name", object);
        } else {
            try {
                DomainNameValidator.validateName(value);
            } catch (InvalidDomainNameException e) {
                addFieldError(getFieldName() + ".nameservers[" + index + "]" + ".name", object);
            }
        }

    }
}
