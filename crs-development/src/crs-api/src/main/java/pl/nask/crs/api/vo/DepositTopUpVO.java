package pl.nask.crs.api.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.payment.DepositTopUp;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
@XmlRootElement
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class DepositTopUpVO {

    private Date operationDate;
    private BigDecimal topUpAmount;
    private String orderId;
    private BigDecimal closingBalance;

    public DepositTopUpVO() {}

    public DepositTopUpVO(DepositTopUp depositTopUp) {
        this.operationDate = depositTopUp.getOperationDate();
        this.topUpAmount = depositTopUp.getTopUpAmount();
        this.orderId = depositTopUp.getOrderId();
        this.closingBalance = depositTopUp.getClosingBalance();
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
}
