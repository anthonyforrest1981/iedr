package pl.nask.crs.app.commons.exceptions;

import pl.nask.crs.payment.Reservation;

public class CancelTicketWithSettledReservationException extends CancelTicketException {

    public Reservation reservation;

    public CancelTicketWithSettledReservationException(String s, Reservation reservation) {
        super(s);
        this.reservation = reservation;
    }

}
