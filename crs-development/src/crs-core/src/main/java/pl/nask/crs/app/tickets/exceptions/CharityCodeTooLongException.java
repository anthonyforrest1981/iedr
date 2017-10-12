package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;

public class CharityCodeTooLongException extends ValidationException {
    @Override
    public String getMessage() {
        return "Charity code is too long";
    }

}
