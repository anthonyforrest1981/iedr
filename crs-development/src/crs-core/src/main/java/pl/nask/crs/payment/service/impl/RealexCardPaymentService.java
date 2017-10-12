package pl.nask.crs.payment.service.impl;

import org.apache.log4j.Logger;

import com.google.common.collect.Range;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.exceptions.ParserException;
import pl.nask.crs.payment.exceptions.PaymentSenderException;
import pl.nask.crs.payment.service.CardPaymentService;
import pl.nask.crs.payment.service.PaymentSender;

public class RealexCardPaymentService implements CardPaymentService {
    private final static String SUCCESSFUL_REALEX_RESPONSE = "00";
    private final static Range<Integer> FAILED_TRANSACTION_ERROR_RANGE = Range.closedOpen(100, 200);
    private final static Range<Integer> BANK_SYSTEM_ERROR_RANGE = Range.closedOpen(200, 300);
    private final static Range<Integer> REALEX_SYSTEM_ERROR_RANGE = Range.closedOpen(300, 400);
    private final static Range<Integer> MESSAGE_FORMATION_OR_CONTENT_ERROR_RANGE = Range.closedOpen(500, 600);
    private final static Logger LOG = Logger.getLogger(RealexCardPaymentService.class);

    private final String paymentAccount;
    private final String paymentMerchant;
    private final String paymentPassword;

    private final PaymentSender sender;

    public RealexCardPaymentService(String paymentAccount, String paymentMerchant, String paymentPassword,
            PaymentSender sender) {
        this.paymentAccount = paymentAccount;
        this.paymentMerchant = paymentMerchant;
        this.paymentPassword = paymentPassword;
        this.sender = sender;
    }

    @Override
    public void settleRealexAuthorisation(RealexTransactionIdentifier transactionIdentifier) throws
            CardPaymentException {
        processWithRealexAuthorisation(transactionIdentifier, TransactionType.TYPE_SETTLE,
                "Payment authorisation settled");
    }

    @Override
    public void cancelRealexAuthorisation(RealexTransactionIdentifier transactionIdentifier) throws
            CardPaymentException {
        processWithRealexAuthorisation(transactionIdentifier, TransactionType.TYPE_VOID,
                "Payment authorisation cancelled");
    }

    private void processWithRealexAuthorisation(RealexTransactionIdentifier transactionIdentifier,
            TransactionType transactionType, String msgOnSuccess) throws CardPaymentException {
        ExtendedPaymentRequest extendedRequest = getAuthorisedPaymentRequest(transactionIdentifier, transactionType);
        PaymentResponse response = processPaymentTransaction(extendedRequest);
        checkRequestWasSuccessful(response, extendedRequest.getOrderId(), msgOnSuccess);
    }

    @Override
    public RealexTransactionIdentifier authorisePaymentTransaction(PaymentRequest request) throws CardPaymentException {
        Validator.assertNotNull(request, "request");
        LOG.debug("before Realex auth");
        ExtendedPaymentRequest extendedRequest = getAuthorisationPaymentRequest(request);
        PaymentResponse response = processPaymentTransaction(extendedRequest);
        checkRequestWasSuccessful(response, extendedRequest.getOrderId(), "Payment authorised");
        LOG.debug("after Realex auth");
        return new RealexTransactionIdentifier(response.getAuthcode(), extendedRequest.getOrderId(),
                response.getPasref());
    }

    private void checkRequestWasSuccessful(PaymentResponse response, String orderId, String msgOnSuccess)
            throws CardPaymentException {
        if (SUCCESSFUL_REALEX_RESPONSE.equals(response.getResult())) {
            LOG.info(
                    String.format("%s %s (%s) %s", orderId, msgOnSuccess, response.getResult(), response.getMessage()));
        } else {
            LOG.error(String.format("%s Payment error (%s) %s", orderId, response.getResult(), response.getMessage()));
            throw new CardPaymentException(response.getMessage(), mapRealexCodeToErrorType(response.getResult()));
        }
    }

    private PaymentResponse processPaymentTransaction(ExtendedPaymentRequest paymentRequest) throws
            CardPaymentException {
        String responseXML;
        try {
            responseXML = sendPaymentRequest(paymentRequest);
            return parsePaymentResponse(responseXML);
        } catch (PaymentSenderException e) {
            LOG.error(e.getMessage(), e);
            throw new CardPaymentException("Problem with sending the request", CardPaymentException.Type.DELIVERY);
        } catch (ParserException e) {
            LOG.error(e.getMessage(), e);
            throw new CardPaymentException("Problem with parsing the response", CardPaymentException.Type.RESPONSE_FORMAT);
        }
    }

    private String sendPaymentRequest(ExtendedPaymentRequest paymentRequest) throws PaymentSenderException {
        String commandXML = PaymentParser.toXML(paymentRequest);
        String loggableCommandXML = PaymentParser.toXML(ExtendedPaymentRequest.loggableInstance(paymentRequest));
        LOG.info("Sending command:\n" + loggableCommandXML);
        return sender.send(commandXML);
    }

    private PaymentResponse parsePaymentResponse(String responseXML) throws ParserException {
        PaymentResponse response = PaymentParser.fromXML(responseXML);
        sanitizeResponse(response);
        LOG.info("Received response, parsed:\n" + response.toString());
        return response;
    }

    /**
     * Response might contain private data, e.g. in case of xml parse errors a copy of request xml is included in error
     * message.
     *
     * @param response original response from releax
     */
    private void sanitizeResponse(PaymentResponse response) {
        int resultCode = Integer.valueOf(response.getResult());
        if (SUCCESSFUL_REALEX_RESPONSE.equals(response.getResult())) {
            // Transaction successful, nothing to sanitize.
        } else if (FAILED_TRANSACTION_ERROR_RANGE.contains(resultCode)) {
            // Transaction failed. This message shouldn't contain any private data. At the same time it might help the
            // client.
        } else if (BANK_SYSTEM_ERROR_RANGE.contains(resultCode)) {
            response.setMessage("Error processing payment due to bank system's error");
        } else if (REALEX_SYSTEM_ERROR_RANGE.contains(resultCode)) {
            response.setMessage("Error processing payment due to Realex system's error");
        } else if (MESSAGE_FORMATION_OR_CONTENT_ERROR_RANGE.contains(resultCode)) {
            response.setMessage("Error processing payment due to bad data or bad format of payment request");
        } else {
            response.setMessage("General error while processing payment");
        }
    }

    private CardPaymentException.Type mapRealexCodeToErrorType(String code) {
        int resultCode = Integer.valueOf(code);
        if (FAILED_TRANSACTION_ERROR_RANGE.contains(resultCode)) {
            return CardPaymentException.Type.FAILED_TRANSACTION;
        } else if (BANK_SYSTEM_ERROR_RANGE.contains(resultCode)) {
            return CardPaymentException.Type.BANK_SYSTEM_ERROR;
        } else if (REALEX_SYSTEM_ERROR_RANGE.contains(resultCode)) {
            return CardPaymentException.Type.REALEX_SYSTEM_ERROR;
        } else {
            return CardPaymentException.Type.UNKNOWN_ERROR;
        }
    }

    private ExtendedPaymentRequest getAuthorisationPaymentRequest(PaymentRequest request) {
        return ExtendedPaymentRequest
                .authorisationInstance(this.paymentAccount, this.paymentMerchant, this.paymentPassword, request);
    }

    private ExtendedPaymentRequest getAuthorisedPaymentRequest(RealexTransactionIdentifier transactionIdentifier,
            TransactionType transactionType) {
        return ExtendedPaymentRequest
                .authorisedInstance(this.paymentAccount, this.paymentMerchant, this.paymentPassword, transactionType,
                        transactionIdentifier.getAuthcode(), transactionIdentifier.getPasref(),
                        transactionIdentifier.getOrderId());
    }

}
