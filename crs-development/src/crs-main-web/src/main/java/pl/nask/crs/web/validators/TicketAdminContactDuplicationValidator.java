package pl.nask.crs.web.validators;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

import pl.nask.crs.web.ticket.wrappers.TicketWrapper;

public class TicketAdminContactDuplicationValidator extends FieldValidatorSupport {

    @Override
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Object fieldValue = this.getFieldValue(fieldName, object);
        TicketWrapper ticketWrapper = (TicketWrapper) fieldValue;
        final String admin1 = ticketWrapper.getAdminContact1().getNicHandle();
        final String admin2 = ticketWrapper.getAdminContact2().getNicHandle();

        if (admin1.equals(admin2)) {
            addFieldError(getFieldName() + ".adminContact1.nicHandle", object);
            addFieldError(getFieldName() + ".adminContact2.nicHandle", object);
        }
    }

}
