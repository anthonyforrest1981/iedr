package pl.nask.crs.payment;

import java.util.Date;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.domains.CustomerType;

public class PlainInvoiceSearchCriteria implements SearchCriteria<PlainInvoice> {

    private String billingNH;

    private String accountName;

    private CustomerType customerType;

    private Date settledFrom;
    private Date settledTo;

    private PaymentMethod paymentMethod;

    private String invoiceNumber;

    private String invoiceNumberFrom;
    private String invoiceNumberTo;

    private Date invoiceDateFrom;
    private Date invoiceDateTo;

    private String settlementDateLike;
    private String invoiceDateLike;

    public String getBillingNH() {
        return billingNH;
    }

    public void setBillingNH(String billingNH) {
        this.billingNH = billingNH;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

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

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getType() {
        return customerType == null ? null : customerType.name();
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

    public String getSettlementDateLike() {
        return settlementDateLike;
    }

    public void setSettlementDateLike(String settlementDateLike) {
        this.settlementDateLike = settlementDateLike;
    }

    public String getInvoiceDateLike() {
        return invoiceDateLike;
    }

    public void setInvoiceDateLike(String invoiceDateLike) {
        this.invoiceDateLike = invoiceDateLike;
    }
}
