package pl.nask.crs.payment.dao.ibatis.objects;

import java.math.BigDecimal;
import java.util.Date;

import pl.nask.crs.domains.CustomerType;
import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.PaymentMethod;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class InternalExtendedReservation {

    private String domainName;
    private String invoiceNumber;
    private String billingNicHandle;
    private String billingNicHandleName;
    private PaymentMethod paymentMethod;
    private CustomerType customerType;
    private OperationType operationType;
    private Date settledDate;
    private Date invoiceDate;
    private Date creationDate;
    private int durationMonths;
    private Date renewalDate;
    private Date startDate;
    private BigDecimal netAmount;
    private BigDecimal vatAmount;
    private String orderId;
    private int vatId;
    private Date vatFromDate;
    private String vatCategory;
    private BigDecimal vatRate;

    public InternalExtendedReservation() {}

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getBillingNicHandle() {
        return billingNicHandle;
    }

    public void setBillingNicHandle(String billingNicHandle) {
        this.billingNicHandle = billingNicHandle;
    }

    public String getBillingNicHandleName() {
        return billingNicHandleName;
    }

    public void setBillingNicHandleName(String billingNicHandleName) {
        this.billingNicHandleName = billingNicHandleName;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public Date getSettledDate() {
        return settledDate;
    }

    public void setSettledDate(Date settledDate) {
        this.settledDate = settledDate;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getDurationMonths() {
        return durationMonths;
    }

    public void setDurationMonths(int durationMonths) {
        this.durationMonths = durationMonths;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public int getVatId() {
        return vatId;
    }

    public void setVatId(int vatId) {
        this.vatId = vatId;
    }

    public Date getVatFromDate() {
        return vatFromDate;
    }

    public String getVatCategory() {
        return vatCategory;
    }

    public void setVatFromDate(Date vatFromDate) {
        this.vatFromDate = vatFromDate;
    }

    public void setVatCategory(String vatCategory) {
        this.vatCategory = vatCategory;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }
}
