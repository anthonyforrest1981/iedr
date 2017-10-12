package pl.nask.crs.web.validators;

import java.net.IDN;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

import pl.nask.crs.app.domains.wrappers.DnsWrapper;
import pl.nask.crs.commons.dns.DomainNameValidator;
import pl.nask.crs.domains.nameservers.Nameserver;

public class DomainNameserverDuplicationValidator extends FieldValidatorSupport {

    @Override
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        DnsWrapper dnsWrapper = (DnsWrapper) getFieldValue(fieldName, object);
        Set<String> names = new HashSet<String>();
        final List<Nameserver> nameservers = dnsWrapper.getNameservers();

        for (int i = 0; i < nameservers.size(); i++) {
            Nameserver ns = nameservers.get(i);
            String name = null;
            try {
                name = IDN.toASCII(DomainNameValidator.getCanonicalName(ns.getName()));
            } catch (Exception e) {
                continue;
            }
            if (names.contains(name)) {
                addFieldError(fieldName + ".nameservers[" + i + "].name", object);
            }
            names.add(name);
        }
    }

}
