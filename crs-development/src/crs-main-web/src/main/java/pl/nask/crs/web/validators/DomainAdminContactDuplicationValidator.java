package pl.nask.crs.web.validators;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

import pl.nask.crs.app.domains.wrappers.DomainWrapper;

public class DomainAdminContactDuplicationValidator extends FieldValidatorSupport {

    @Override
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object fieldValue = this.getFieldValue(fieldName, object);
        DomainWrapper domainWrapper = (DomainWrapper) fieldValue;
        final String admin1 = domainWrapper.getAdminContact1();
        final String admin2 = domainWrapper.getAdminContact2();

        if (admin1.equals(admin2)) {
            addFieldError(getFieldName() + ".adminContact1", object);
            addFieldError(getFieldName() + ".adminContact2", object);
        }
    }

}
