package pl.nask.crs.payment.dao.ibatis.objects;

import java.math.BigDecimal;
import java.util.Date;

import pl.nask.crs.payment.DepositTransactionType;

public class InternalDeposit {

    private String nicHandleId;
    private String nicHandleName;
    private Date transactionDate;
    private BigDecimal openBal;
    private BigDecimal closeBal;
    private BigDecimal transactionAmount;
    private DepositTransactionType transactionType;
    private String orderId;
    private String correctorNH;
    private String remark;

    public InternalDeposit() {}

    public InternalDeposit(String nicHandleId, Date transactionDate, BigDecimal openBal, BigDecimal closeBal,
            BigDecimal transactionAmount, DepositTransactionType transactionType, String orderId, String correctorNH,
            String remark) {
        this.nicHandleId = nicHandleId;
        this.transactionDate = transactionDate;
        this.openBal = openBal;
        this.closeBal = closeBal;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
        this.orderId = orderId;
        this.correctorNH = correctorNH;
        this.remark = remark;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public String getNicHandleName() {
        return nicHandleName;
    }

    public void setNicHandleName(String nicHandleName) {
        this.nicHandleName = nicHandleName;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getOpenBal() {
        return openBal;
    }

    public void setOpenBal(BigDecimal openBal) {
        this.openBal = openBal;
    }

    public BigDecimal getCloseBal() {
        return closeBal;
    }

    public void setCloseBal(BigDecimal closeBal) {
        this.closeBal = closeBal;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public DepositTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(DepositTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCorrectorNH() {
        return correctorNH;
    }

    public void setCorrectorNH(String correctorNH) {
        this.correctorNH = correctorNH;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
