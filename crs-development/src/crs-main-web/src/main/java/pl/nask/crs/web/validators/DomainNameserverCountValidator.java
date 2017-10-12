package pl.nask.crs.web.validators;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

import pl.nask.crs.app.domains.wrappers.DnsWrapper;
import pl.nask.crs.commons.config.ApplicationConfig;

public class DomainNameserverCountValidator extends FieldValidatorSupport {
    private final ApplicationConfig appConfig;

    public DomainNameserverCountValidator(ApplicationConfig appConfig) {
        this.appConfig = appConfig;
        setDefaultMessage("NS count out of allowed range: ");
    }

    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        final DnsWrapper dnsWrapper = (DnsWrapper) getFieldValue(fieldName, object);
        final int nsCount = dnsWrapper.getNameservers().size();
        if (nsCount < appConfig.getNameserverMinCount() || nsCount > appConfig.getNameserverMaxCount()) {
            addFieldError(fieldName, object);
        }
    }
}
