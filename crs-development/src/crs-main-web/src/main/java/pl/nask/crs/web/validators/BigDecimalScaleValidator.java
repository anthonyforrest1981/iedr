package pl.nask.crs.web.validators;

import java.math.BigDecimal;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

public class BigDecimalScaleValidator extends FieldValidatorSupport {

    private int maxScale;

    @Override
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object fieldValue = getFieldValue(fieldName, object);
        BigDecimal value = (BigDecimal) fieldValue;
        if (value != null && value.scale() > maxScale) {
            addFieldError(fieldName, object);
        }
    }

    public int getMaxScale() {
        return maxScale;
    }

    public void setMaxScale(int maxScale) {
        this.maxScale = maxScale;
    }

}
