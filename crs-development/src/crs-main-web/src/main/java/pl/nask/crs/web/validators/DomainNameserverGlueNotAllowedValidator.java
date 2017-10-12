package pl.nask.crs.web.validators;

import java.net.IDN;

import pl.nask.crs.commons.dns.DomainNameValidator;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.nameservers.Nameserver;

public class DomainNameserverGlueNotAllowedValidator extends ComparableDomainNameserverValidator {

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
        final boolean glueNotAllowed = !name.equals(domain) && !name.endsWith("." + domain);
        if (glueNotAllowed && !Validator.isEmpty(ipv4)) {
            addFieldError(getFieldName() + ".nameservers[" + index + "].ipv4", object);
        }
        if (glueNotAllowed && !Validator.isEmpty(ipv6)) {
            addFieldError(getFieldName() + ".nameservers[" + index + "].ipv6", object);
        }
    }

}
