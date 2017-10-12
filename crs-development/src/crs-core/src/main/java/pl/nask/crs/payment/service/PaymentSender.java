package pl.nask.crs.payment.service;

import pl.nask.crs.payment.exceptions.PaymentSenderException;

public interface PaymentSender {

    String send(String commandXML) throws PaymentSenderException;

}
