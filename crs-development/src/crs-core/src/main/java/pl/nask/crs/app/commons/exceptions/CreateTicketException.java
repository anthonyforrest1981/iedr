package pl.nask.crs.app.commons.exceptions;

public class CreateTicketException extends Exception {

    private static final long serialVersionUID = -7815284897641795595L;

    public CreateTicketException(String s, Exception throwable) {
        super(s, throwable);
    }

    public CreateTicketException(Exception throwable) {
        super("Exeption when creating ticket: " + throwable.getClass() + " (" + throwable.getMessage() + ")", throwable);
    }

    public Exception getExceptionCause() {
        final Exception cause = (Exception) getCause();
        assert cause != null : "@AssumeAssertion(nullness)";
        return cause;
    }
}
