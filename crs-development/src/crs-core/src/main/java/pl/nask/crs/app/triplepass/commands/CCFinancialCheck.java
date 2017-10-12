package pl.nask.crs.app.triplepass.commands;

import pl.nask.crs.app.triplepass.exceptions.FinancialCheckException;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.exceptions.ReservationNotFoundException;
import pl.nask.crs.payment.service.DepositService;
import pl.nask.crs.payment.service.PaymentSearchService;
import pl.nask.crs.payment.service.PaymentService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.exceptions.TicketNotFoundException;
import pl.nask.crs.ticket.services.TicketService;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class CCFinancialCheck extends FinancialCheck {

    protected final Long reservationId;

    public CCFinancialCheck(Ticket ticket, Long reservationId, TicketService ticketService,
            PaymentService paymentService, PaymentSearchService paymentSearchService, DepositService depositService) {
        super(ticket, ticketService, paymentService, paymentSearchService, depositService);
        this.reservationId = reservationId;
    }

    @Override
    public void performFinancialCheck(AuthenticatedUser user, OpInfo opInfo) throws FinancialCheckException {
        try {
            Reservation reservation = paymentService.lockForUpdate(reservationId);
            if (isTransactionInvalidated(reservation.getTransactionId())) {
                setFinancialStatusStalled(ticket.getId(), opInfo);
            } else {
                setReservationReadyAndTicketFinancialPassed(ticket, reservation, opInfo);
            }
        } catch (TicketNotFoundException e) {
            throw new FinancialCheckException(e);
        } catch (ReservationNotFoundException e) {
            throw new FinancialCheckException(e);
        }
    }
}
