package pl.nask.crs.payment.dao.ibatis.objects;

import java.math.BigDecimal;
import java.util.Date;

import pl.nask.crs.payment.OperationType;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.ticket.FinancialStatus;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class InternalReservation {

    private long id;
    private String nicHandleId;
    private String domainName;
    private int durationMonths;
    private Date creationDate;
    private Integer productId;
    private BigDecimal amount;
    private int vatId;
    private String vatCategory;
    private Date vatFromDate;
    private BigDecimal vatRate;
    private BigDecimal vatAmount;
    private boolean readyForSettlement;
    private boolean settled;
    private Date settledDate;
    private Long ticketId;
    private Long transactionId;
    private OperationType operationType;

    private Date startDate;
    private Date endDate;

    private PaymentMethod paymentMethod;

    /**
     * This is probably not in use! Remove!
     *
     * @deprecated
     */

    private String invoiceNumber;

    private String orderId;

    private FinancialStatus financialStatus;

    public InternalReservation() {}

    public InternalReservation(long id, String nicHandleId, String domainName, int durationMonths, Date creationDate,
            Integer productId, BigDecimal amount, int vatId, BigDecimal vatAmount, boolean readyForSettlement,
            boolean settled, Date settledDate, Long ticketId, Long transactionId, OperationType operationType,
            PaymentMethod paymentMethod, String invoiceNumber) {
        this.id = id;
        this.nicHandleId = nicHandleId;
        this.domainName = domainName;
        this.durationMonths = durationMonths;
        this.creationDate = creationDate;
        this.productId = productId;
        this.amount = amount;
        this.vatId = vatId;
        this.vatAmount = vatAmount;
        this.readyForSettlement = readyForSettlement;
        this.settled = settled;
        this.settledDate = settledDate;
        this.ticketId = ticketId;
        this.transactionId = transactionId;
        this.operationType = operationType;
        this.paymentMethod = paymentMethod;
        this.invoiceNumber = invoiceNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public int getVatId() {
        return vatId;
    }

    public void setVatId(int vatId) {
        this.vatId = vatId;
    }

    public String getVatCategory() {
        return vatCategory;
    }

    public void setVatCategory(String vatCategory) {
        this.vatCategory = vatCategory;
    }

    public Date getVatFromDate() {
        return vatFromDate;
    }

    public void setVatFromDate(Date vatFromDate) {
        this.vatFromDate = vatFromDate;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
    }

    public boolean isSettled() {
        return settled;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }

    public Date getSettledDate() {
        return settledDate;
    }

    public void setSettledDate(Date settledDate) {
        this.settledDate = settledDate;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public int getDurationMonths() {
        return durationMonths;
    }

    public void setDurationMonths(int durationMonths) {
        this.durationMonths = durationMonths;
    }

    public boolean isReadyForSettlement() {
        return readyForSettlement;
    }

    public void setReadyForSettlement(boolean readyForSettlement) {
        this.readyForSettlement = readyForSettlement;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public FinancialStatus getFinancialStatus() {
        return financialStatus;
    }

    public void setFinancialStatus(FinancialStatus financialStatus) {
        this.financialStatus = financialStatus;
    }
}
