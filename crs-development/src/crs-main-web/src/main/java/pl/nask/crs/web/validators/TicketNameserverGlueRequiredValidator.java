package pl.nask.crs.web.validators;

import java.net.IDN;

import pl.nask.crs.commons.dns.DomainNameValidator;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.web.ticket.wrappers.NameserverWrapper;
import pl.nask.crs.web.ticket.wrappers.TicketWrapper;

public class TicketNameserverGlueRequiredValidator extends ComparableTicketNameserverValidator {
    @Override
    protected void validateNameserver(Object o, TicketWrapper ticketWrapper, NameserverWrapper ns, int i) {
        final String ipv4 = ns.getIpv4();
        final String ipv6 = ns.getIpv6();
        String name = null;
        String domain = null;
        try {
            name = IDN.toASCII(DomainNameValidator.getCanonicalName(ns.getName()));
            domain = IDN.toASCII(DomainNameValidator.getCanonicalName(ticketWrapper.getDomainName().getNewValue()));
        } catch (Exception e) {
            return;
        }
        final boolean requiresGlue = name.equals(domain) || name.endsWith("." + domain);
        if (requiresGlue && (Validator.isEmpty(ipv4) && Validator.isEmpty(ipv6))) {
            addFieldError(getFieldName() + ".nameserverWrappers[" + i + "].name", o);
        }
    }
}
