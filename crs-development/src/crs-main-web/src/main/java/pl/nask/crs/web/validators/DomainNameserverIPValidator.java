package pl.nask.crs.web.validators;

import pl.nask.crs.commons.dns.IPAddressValidator;
import pl.nask.crs.commons.dns.exceptions.IpSyntaxException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.nameservers.Nameserver;

public class DomainNameserverIPValidator extends ComparableDomainNameserverValidator {

    public static final String IPV_4_FIELDNAME = "ipv4";
    public static final String IPV_6_FIELDNAME = "ipv6";

    public DomainNameserverIPValidator() {
        setDefaultMessage("Wrong ns IP");
    }

    @Override
    protected void validateNameserver(Object object, Nameserver ns, String domainName, int index) {
        validate(IPV_4_FIELDNAME, ns.getIpv4Address(), object, index);
        validate(IPV_6_FIELDNAME, ns.getIpv6Address(), object, index);
    }

    private void validate(String fieldName, String ipAddress, Object object, int index) {
        if (!Validator.isEmpty(ipAddress)) {
            try {
                if (fieldName.equals(IPV_4_FIELDNAME)) {
                    IPAddressValidator.validateIPv4(ipAddress);
                } else {
                    IPAddressValidator.validateIPv6(ipAddress);
                }
            } catch (IpSyntaxException e) {
                addFieldError(getFieldName() + ".nameservers[" + index + "]" + "." + fieldName, object);
            }
        }
    }
}
