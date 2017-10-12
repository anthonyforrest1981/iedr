package pl.nask.crs.web.validators;

import java.math.BigDecimal;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

import pl.nask.crs.commons.config.ApplicationConfig;

public class AccountDepositTopUpAmountValidator extends FieldValidatorSupport {

    private final ApplicationConfig appConfig;

    public AccountDepositTopUpAmountValidator(ApplicationConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object fieldValue = getFieldValue(fieldName, object);
        BigDecimal topUpAmount = (BigDecimal) fieldValue;
        if (topUpAmount != null && (topUpAmount.compareTo(appConfig.getDepositMinLimit()) < 0 ||
                topUpAmount.compareTo(appConfig.getDepositMaxLimit()) > 0)) {
            addFieldError(fieldName, object);
        }
    }

}
