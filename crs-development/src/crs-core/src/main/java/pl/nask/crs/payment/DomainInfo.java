package pl.nask.crs.payment;

import java.math.BigDecimal;
import java.util.Date;

import pl.nask.crs.commons.MoneyUtils;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DomainInfo {

    private String domainName;

    private String orderId;

    private Date regDate;

    private Date renDate;

    private Date transferDate;

    private OperationType operationType;

    private int durationMonths;

    private BigDecimal netAmount;

    private BigDecimal vatAmount;

    private BigDecimal total;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getRenDate() {
        return renDate;
    }

    public void setRenDate(Date renDate) {
        this.renDate = renDate;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getTransferDate() {
        return operationType.equals(OperationType.TRANSFER) ? transferDate : null;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public int getDurationMonths() {
        return durationMonths;
    }

    public void setDurationMonths(int durationMonths) {
        this.durationMonths = durationMonths;
    }

    public BigDecimal getTotal() {
        return MoneyUtils.add(netAmount, vatAmount);
    }

    @Override
    public String toString() {
        return String.format("DomainInfo[domainName: %s, netAmount: %s, vatAmount: %s]", domainName, netAmount,
                vatAmount);
    }
}
