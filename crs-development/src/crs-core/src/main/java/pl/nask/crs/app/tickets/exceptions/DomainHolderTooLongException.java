package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

public class DomainHolderTooLongException extends ValidationException {
    @Override
    public String getMessage() {
        return "Holder value is too long";
    }
}
