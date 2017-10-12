package pl.nask.crs.ticket.exceptions;

public class TicketUserCannotCheckOutException extends Exception {
    public TicketUserCannotCheckOutException(String msg) {
        super(msg);
    }
}
