package pl.nask.crs.web.validators;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

import pl.nask.crs.commons.dns.DomainNameValidator;
import pl.nask.crs.commons.dns.exceptions.InvalidDomainNameException;

public class DomainName extends FieldValidatorSupport {
    private final String DEFAULT_MESSAGE = "Wrong nameserver name";

    public DomainName() {
        setDefaultMessage(DEFAULT_MESSAGE);
    }

    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object fieldValue = this.getFieldValue(fieldName, object);
        String value = fieldValue == null ? null : fieldValue.toString();
        try {
            DomainNameValidator.validateName(value);
        } catch (InvalidDomainNameException e) {
            addFieldError(getFieldName(), object);
        }
    }

}
