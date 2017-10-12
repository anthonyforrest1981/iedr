package pl.nask.crs.web.validators;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

import pl.nask.crs.commons.utils.InvalidEmailException;

public class EmailValidator extends FieldValidatorSupport {

    public EmailValidator() {
        setDefaultMessage("E-mail is not valid");
    }

    @Override
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object fieldValue = this.getFieldValue(fieldName, object);
        String value = fieldValue == null ? "" : fieldValue.toString();
        try {
            pl.nask.crs.commons.utils.EmailValidator.validateEmail(value);
        } catch (InvalidEmailException e) {
            addFieldError(getFieldName(), object);
        }
    }

}
