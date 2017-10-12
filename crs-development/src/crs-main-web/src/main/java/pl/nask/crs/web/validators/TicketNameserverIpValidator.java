package pl.nask.crs.web.validators;

import pl.nask.crs.commons.dns.IPAddressValidator;
import pl.nask.crs.commons.dns.exceptions.IpSyntaxException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.web.ticket.wrappers.NameserverWrapper;
import pl.nask.crs.web.ticket.wrappers.TicketWrapper;

public class TicketNameserverIpValidator extends ComparableTicketNameserverValidator {

    public static final String IPV_4_FIELDNAME = "ipv4";
    public static final String IPV_6_FIELDNAME = "ipv6";

    @Override
    protected void validateNameserver(Object object, TicketWrapper t, NameserverWrapper ns, int index) {
        validate(IPV_4_FIELDNAME, ns.getIpv4(), object, index);
        validate(IPV_6_FIELDNAME, ns.getIpv6(), object, index);
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
                addFieldError(getFieldName() + ".nameserverWrappers[" + index + "]" + "." + fieldName, object);
            }
        }
    }

}
