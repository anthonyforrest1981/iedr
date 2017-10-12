package pl.nask.crs.domains.dsm.events;

import java.math.BigDecimal;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.TransactionDetails;
import pl.nask.crs.ticket.Ticket;

public class TransferWithAutorenewToRegistrar extends AbstractTransferEvent {
    public TransferWithAutorenewToRegistrar(Ticket t, PaymentMethod paymentMethod, TransactionDetails transactionDetails,
            BigDecimal transactionValue, String orderId) {
        super(DsmEventName.TransferWithAutorenewToRegistrar, t);
        setParameter(PAY_METHOD, paymentMethod);
        setParameter(TRANSACTION_DETAIL, transactionDetails);
        setParameter(TRANSACTION_VALUE, MoneyUtils.getRoundedAndScaledValue(transactionValue));
        setParameter(ORDER_ID, orderId);
    }

    public TransferWithAutorenewToRegistrar(Ticket t) {
        super(DsmEventName.TransferWithAutorenewToRegistrar, t);
        setParameter(PAY_METHOD, null);
    }
}
