package pl.nask.crs.payment.dao.ibatis.converters;

import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.payment.Reservation;
import pl.nask.crs.payment.dao.ibatis.objects.InternalReservation;
import pl.nask.crs.ticket.dao.ibatis.objects.InternalTicket;
import pl.nask.crs.vat.PriceWithVat;
import pl.nask.crs.vat.Vat;

public class ReservationConverter extends AbstractConverter<InternalReservation, Reservation> {

    private GenericDAO<InternalTicket, Long> ticketDao;

    public ReservationConverter(GenericDAO<InternalTicket, Long> ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Override
    protected Reservation _to(InternalReservation internalReservation) {
        Vat vat = new Vat(internalReservation.getVatId(), internalReservation.getVatCategory(),
                internalReservation.getVatFromDate(), internalReservation.getVatRate());
        PriceWithVat priceWithVat = new PriceWithVat(internalReservation.getAmount(), vat);

        InternalTicket ticket = (internalReservation.getTicketId() == null) ? null : ticketDao.get(internalReservation
                .getTicketId());
        Reservation res = new Reservation(internalReservation.getId(), internalReservation.getNicHandleId(),
                internalReservation.getDomainName(), internalReservation.getDurationMonths(),
                internalReservation.getCreationDate(), priceWithVat, internalReservation.getProductId(),
                internalReservation.isReadyForSettlement(), internalReservation.isSettled(),
                internalReservation.getSettledDate(), internalReservation.getTicketId(),
                internalReservation.getTransactionId(), internalReservation.getOperationType(),
                internalReservation.getPaymentMethod(), internalReservation.getInvoiceNumber(),
                internalReservation.getOrderId(), internalReservation.getFinancialStatus(), (ticket != null));
        res.setEndDate(internalReservation.getEndDate());
        res.setStartDate(internalReservation.getStartDate());
        return res;
    }

    @Override
    protected InternalReservation _from(Reservation reservation) {
        InternalReservation res = new InternalReservation(reservation.getId(), reservation.getNicHandleId(),
                reservation.getDomainName(), reservation.getDurationMonths(), reservation.getCreationDate(),
                reservation.getProductId(), reservation.getNetAmount(), reservation.getVatId(),
                reservation.getVatAmount(), reservation.isReadyForSettlement(), reservation.isSettled(),
                reservation.getSettledDate(), reservation.getTicketId(), reservation.getTransactionId(),
                reservation.getOperationType(), reservation.getPaymentMethod(), reservation.getInvoiceNumber());
        res.setStartDate(reservation.getStartDate());
        res.setEndDate(reservation.getEndDate());
        return res;
    }
}
