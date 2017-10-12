package pl.nask.crs.web.validators;

import java.util.List;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

import pl.nask.crs.app.domains.wrappers.DnsWrapper;
import pl.nask.crs.domains.nameservers.Nameserver;

public abstract class ComparableDomainNameserverValidator extends FieldValidatorSupport {

    @Override
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        final DnsWrapper dnsWrapper = (DnsWrapper) getFieldValue(fieldName, object);
        final List<Nameserver> currentNameservers = dnsWrapper.getNameservers();
        for (int i = 0; i < currentNameservers.size(); i++) {
            Nameserver ns = currentNameservers.get(i);
            validateNameserver(object, ns, dnsWrapper.getDomainName(), i);
        }
    }

    protected abstract void validateNameserver(Object object, Nameserver ns, String domainName, int index);
}
