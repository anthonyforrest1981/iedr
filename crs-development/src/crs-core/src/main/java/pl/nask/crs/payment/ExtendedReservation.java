package pl.nask.crs.payment;

import java.math.BigDecimal;
import java.util.Date;

import pl.nask.crs.domains.CustomerType;
import pl.nask.crs.vat.PriceWithVat;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class ExtendedReservation {

    private final String invoiceNumber;
    private final String domainName;
    private final String billingNicHandle;
    private final String billingNicHandleName;
    private final PaymentMethod paymentMethod;
    private final CustomerType customerType;
    private final OperationType operationType;
    private final Date settledDate;
    private final Date invoiceDate;
    private final Date creationDate;
    private final Date startDate;
    private final int durationMonths;
    private final Date renewalDate;
    private final PriceWithVat priceWithVat;
    private final String orderId;

    public ExtendedReservation(String domainName, String invoiceNumber, String billingNicHandle,
            String billingNicHandleName, PaymentMethod paymentMethod, CustomerType customerType,
            OperationType operationType, Date settledDate, Date invoiceDate, Date creationDate, int durationMonths,
            Date renewalDate, PriceWithVat priceWithVat, Date startDate, String orderId) {
        this.domainName = domainName;
        this.invoiceNumber = invoiceNumber;
        this.billingNicHandle = billingNicHandle;
        this.billingNicHandleName = billingNicHandleName;
        this.paymentMethod = paymentMethod;
        this.customerType = customerType;
        this.operationType = operationType;
        this.settledDate = settledDate;
        this.invoiceDate = invoiceDate;
        this.creationDate = creationDate;
        this.durationMonths = durationMonths;
        this.renewalDate = renewalDate;
        this.priceWithVat = priceWithVat;
        this.startDate = startDate;
        this.orderId = orderId;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getBillingNicHandle() {
        return billingNicHandle;
    }

    public String getBillingNicHandleName() {
        return billingNicHandleName;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public Date getSettledDate() {
        return settledDate;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public int getDurationMonths() {
        return durationMonths;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public BigDecimal getNetAmount() {
        return priceWithVat.getNetAmount();
    }

    public BigDecimal getVatAmount() {
        return priceWithVat.getVatAmount();
    }

    public BigDecimal getTotal() {
        return priceWithVat.getTotal();
    }

    public String getOrderId() {
        return orderId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    @Override
    public String toString() {
        return String.format(
                "ExtendedReservation[invoiceNumber=%s, domainName=%s, customerType=%s, paymentMethod=%s, total=%s]",
                invoiceNumber, domainName, customerType, paymentMethod, getTotal());
    }

    public Date getStartDate() {
        return startDate;
    }
}
