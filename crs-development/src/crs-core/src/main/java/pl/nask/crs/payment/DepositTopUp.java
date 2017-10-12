package pl.nask.crs.payment;

import java.math.BigDecimal;
import java.util.Date;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DepositTopUp {

    private Date operationDate;
    private BigDecimal topUpAmount;
    private String orderId;
    private BigDecimal closingBalance;

    public DepositTopUp() {}

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public void setTopUpAmount(BigDecimal topUpAmount) {
        this.topUpAmount = topUpAmount;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setClosingBalance(BigDecimal closingBalance) {
        this.closingBalance = closingBalance;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public BigDecimal getTopUpAmount() {
        return topUpAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public BigDecimal getClosingBalance() {
        return closingBalance;
    }

    @Override
    public String toString() {
        return String.format("DepositTopUp[orderId=%s, topUpAmount=%s, operationDate=%s]", orderId, topUpAmount,
                operationDate);
    }
}
