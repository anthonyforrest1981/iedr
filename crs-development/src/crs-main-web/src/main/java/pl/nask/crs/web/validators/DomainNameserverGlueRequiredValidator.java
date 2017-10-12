package pl.nask.crs.web.validators;

import java.net.IDN;

import pl.nask.crs.commons.dns.DomainNameValidator;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.nameservers.Nameserver;

public class DomainNameserverGlueRequiredValidator extends ComparableDomainNameserverValidator {

    @Override
    protected void validateNameserver(Object object, Nameserver ns, String domainName, int index) {
        final String ipv4 = ns.getIpv4Address();
        final String ipv6 = ns.getIpv6Address();
        String name = null;
        String domain = null;
        try {
            name = IDN.toASCII(DomainNameValidator.getCanonicalName(ns.getName()));
            domain = IDN.toASCII(DomainNameValidator.getCanonicalName(domainName));
        } catch (Exception e) {
            return;
        }
        final boolean requiresGlue = name.equals(domain) || name.endsWith("." + domain);
        if (requiresGlue && (Validator.isEmpty(ipv4) && Validator.isEmpty(ipv6))) {
            addFieldError(getFieldName() + ".nameservers[" + index + "].name", object);
        }
    }

}
