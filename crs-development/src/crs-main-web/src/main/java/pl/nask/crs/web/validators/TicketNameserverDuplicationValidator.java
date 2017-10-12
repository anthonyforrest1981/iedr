package pl.nask.crs.web.validators;

import java.net.IDN;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

import pl.nask.crs.commons.dns.DomainNameValidator;
import pl.nask.crs.web.ticket.wrappers.NameserverWrapper;
import pl.nask.crs.web.ticket.wrappers.TicketWrapper;

public class TicketNameserverDuplicationValidator extends FieldValidatorSupport {
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object fieldValue = getFieldValue(fieldName, object);
        Set<String> names = new HashSet<String>();
        TicketWrapper ticketWrapper = (TicketWrapper) fieldValue;
        final List<NameserverWrapper> nameservers = ticketWrapper.getNewNameserverWrappers();

        for (int i = 0; i < nameservers.size(); i++) {
            NameserverWrapper ns = nameservers.get(i);
            String name = null;
            try {
                name = IDN.toASCII(DomainNameValidator.getCanonicalName(ns.getName()));
            } catch (Exception e) {
                continue;
            }
            if (names.contains(name)) {
                addFieldError(fieldName + ".nameserverWrappers[" + i + "].name", object);
            }
            names.add(name);
        }
    }
}
