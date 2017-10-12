package pl.nask.crs.web.validators;

import java.util.List;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

import pl.nask.crs.web.ticket.wrappers.NameserverWrapper;
import pl.nask.crs.web.ticket.wrappers.TicketWrapper;

public abstract class ComparableTicketNameserverValidator extends FieldValidatorSupport {

    @Override
    public void validate(Object o) throws ValidationException {
        String fieldName = getFieldName();
        Object fieldValue = getFieldValue(fieldName, o);
        TicketWrapper ticketWrapper = (TicketWrapper) fieldValue;
        final List<NameserverWrapper> newNameserverWrappers = ticketWrapper.getNewNameserverWrappers();
        for (int i = 0; i < newNameserverWrappers.size(); i++) {
            NameserverWrapper nsWrapper = newNameserverWrappers.get(i);
            validateNameserver(o, ticketWrapper, nsWrapper, i);
        }
    }

    protected abstract void validateNameserver(Object o, TicketWrapper t, NameserverWrapper ns, int i);
}
