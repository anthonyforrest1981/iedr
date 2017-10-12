package pl.nask.crs.payment.exceptions;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InvoiceNotFoundException extends Exception {
    public int id;
    public String number;

    public InvoiceNotFoundException(String message) {
        super(message);
    }

    public InvoiceNotFoundException(String message, int id) {
        super("Cannot find invoice with id: " + id);
        this.id = id;
    }

    public InvoiceNotFoundException(String message, String number) {
        super("Cannot find invoice with number: " + number);
        this.number = number;
    }
}
