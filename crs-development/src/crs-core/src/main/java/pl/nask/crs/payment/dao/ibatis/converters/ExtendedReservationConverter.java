package pl.nask.crs.payment.dao.ibatis.converters;

import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.payment.ExtendedReservation;
import pl.nask.crs.payment.dao.ibatis.objects.InternalExtendedReservation;
import pl.nask.crs.vat.PriceWithVat;
import pl.nask.crs.vat.Vat;

public class ExtendedReservationConverter extends AbstractConverter<InternalExtendedReservation, ExtendedReservation> {

    @Override
    protected ExtendedReservation _to(InternalExtendedReservation internalExtendedReservation) {
        Vat vat = new Vat(internalExtendedReservation.getVatId(), internalExtendedReservation.getVatCategory(),
                internalExtendedReservation.getVatFromDate(), internalExtendedReservation.getVatRate());
        PriceWithVat price = new PriceWithVat(internalExtendedReservation.getNetAmount(), vat);
        return new ExtendedReservation(internalExtendedReservation.getDomainName(),
                internalExtendedReservation.getInvoiceNumber(), internalExtendedReservation.getBillingNicHandle(),
                internalExtendedReservation.getBillingNicHandleName(), internalExtendedReservation.getPaymentMethod(),
                internalExtendedReservation.getCustomerType(), internalExtendedReservation.getOperationType(),
                internalExtendedReservation.getSettledDate(), internalExtendedReservation.getInvoiceDate(),
                internalExtendedReservation.getCreationDate(), internalExtendedReservation.getDurationMonths(),
                internalExtendedReservation.getRenewalDate(), price, internalExtendedReservation.getStartDate(),
                internalExtendedReservation.getOrderId());
    }

    @Override
    protected InternalExtendedReservation _from(ExtendedReservation extendedReservation) {
        return null;//skipped since we read this value only
    }
}
