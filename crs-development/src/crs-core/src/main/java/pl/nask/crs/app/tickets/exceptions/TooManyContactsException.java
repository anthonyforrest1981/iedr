package pl.nask.crs.app.tickets.exceptions;

import pl.nask.crs.app.ValidationException;
import pl.nask.crs.contacts.ContactType;

public class TooManyContactsException extends ValidationException {
    private static final long serialVersionUID = -9207567834234104105L;

    private ContactType type;
    private int max;
    private int size;

    public TooManyContactsException(ContactType type, int max, int size) {
        this.type = type;
        this.max = max;
        this.size = size;
    }

    @Override
    public String getMessage() {
        return String.format("Too many admin contacts. Got %s, max is %s", size, max);
    }

    public ContactType getType() {
        return type;
    }

    public int getMax() {
        return max;
    }

    public int getSize() {
        return size;
    }
}
