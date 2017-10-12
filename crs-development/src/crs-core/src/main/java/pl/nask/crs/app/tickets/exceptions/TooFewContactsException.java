package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;
import pl.nask.crs.contacts.ContactType;

public class TooFewContactsException extends ValidationException {
    private static final long serialVersionUID = 4360626486079935651L;

    private ContactType type;
    private int min;
    private int size;

    public TooFewContactsException(ContactType type, int min, int size) {
        this.type = type;
        this.min = min;
        this.size = size;
    }

    @Override
    public String getMessage() {
        return String.format("Too few %s contacts. Got %s, min is %s", type, size, min);
    }

    public ContactType getType() {
        return type;
    }

    public int getMin() {
        return min;
    }

    public int getSize() {
        return size;
    }
}
