package pl.nask.crs.web.reports;

import java.io.InputStream;

import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.payment.exceptions.InvoiceNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ViewPDFAction extends AuthenticatedUserAwareAction {

    private InputStream inputStream;

    private String invoiceNumber;

    private PaymentAppService paymentAppService;

    public ViewPDFAction(PaymentAppService paymentAppService) {
        this.paymentAppService = paymentAppService;
    }

    public String getPDF() throws AccessDeniedException {
        try {
            inputStream = paymentAppService.viewPdfInvoice(getUser(), invoiceNumber);
        } catch (InvoiceNotFoundException e) {
            addActionError("invoice not found");
            return ERROR;
        }
        return SUCCESS;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
}
