package pl.nask.crs.iedrapi.exceptions;

import java.util.List;

public class Utf8ValidationException extends IedrApiException {

    public Utf8ValidationException() {
        super(2005);
    }

    public Utf8ValidationException(Exception e) {
        super(2005, e);
    }

    public Utf8ValidationException(List<String> errorMessages) {
        super(2005, errorMessages.toString());
    }
}
