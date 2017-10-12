package pl.nask.crs.web.validators;

import java.math.BigDecimal;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

public class BigDecimalTypeValidator extends FieldValidatorSupport {

    @Override
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object fieldValue = getFieldValue(fieldName, object);
        BigDecimal value = (BigDecimal) fieldValue;
        if (value == null) {
            addFieldError(fieldName, object);
        }
    }

}
