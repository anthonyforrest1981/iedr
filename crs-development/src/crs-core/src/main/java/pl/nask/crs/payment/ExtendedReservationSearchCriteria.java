package pl.nask.crs.payment;

import java.util.Date;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.utils.DateUtils;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class ExtendedReservationSearchCriteria implements SearchCriteria<ExtendedReservation> {

    private Date settledFrom;
    private Date settledTo;
    private Date invoiceDateFrom;
    private Date invoiceDateTo;
    private String invoiceNumber;
    private String invoiceNumberFrom;
    private String invoiceNumberTo;

    public Date getSettledFrom() {
        return settledFrom;
    }

    public void setSettledFrom(Date settledFrom) {
        this.settledFrom = DateUtils.startOfDay(settledFrom);
    }

    public Date getSettledTo() {
        return settledTo;
    }

    public void setSettledTo(Date settledTo) {
        this.settledTo = DateUtils.endOfDay(settledTo);
    }

    public Date getInvoiceDateFrom() {
        return invoiceDateFrom;
    }

    public void setInvoiceDateFrom(Date invoiceDateFrom) {
        this.invoiceDateFrom = DateUtils.startOfDay(invoiceDateFrom);
    }

    public Date getInvoiceDateTo() {
        return invoiceDateTo;
    }

    public void setInvoiceDateTo(Date invoiceDateTo) {
        this.invoiceDateTo = DateUtils.endOfDay(invoiceDateTo);
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceNumberFrom() {
        return invoiceNumberFrom;
    }

    public void setInvoiceNumberFrom(String invoiceNumberFrom) {
        this.invoiceNumberFrom = invoiceNumberFrom;
    }

    public String getInvoiceNumberTo() {
        return invoiceNumberTo;
    }

    public void setInvoiceNumberTo(String invoiceNumberTo) {
        this.invoiceNumberTo = invoiceNumberTo;
    }
}
