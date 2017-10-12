package pl.nask.crs.payment;

import java.util.Date;

import pl.nask.crs.commons.utils.DateUtils;

public abstract class AbstractDepositSearchCriteria {

    private String nicHandleId;
    private Date transactionDateFrom;
    private Date transactionDateTo;
    private DepositTransactionType transactionType;
    private String correctorNH;
    private String accountBillNH;

    public AbstractDepositSearchCriteria() {}

    public AbstractDepositSearchCriteria(String nicHandleId, String accountBillNH) {
        this.nicHandleId = nicHandleId;
        this.accountBillNH = accountBillNH;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public Date getTransactionDateFrom() {
        return DateUtils.startOfDay(transactionDateFrom);
    }

    public void setTransactionDateFrom(Date transactionDateFrom) {
        this.transactionDateFrom = transactionDateFrom;
    }

    public Date getTransactionDateTo() {
        return DateUtils.endOfDay(transactionDateTo);
    }

    public void setTransactionDateTo(Date transactionDateTo) {
        this.transactionDateTo = transactionDateTo;
    }

    public DepositTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(DepositTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getCorrectorNH() {
        return correctorNH;
    }

    public void setCorrectorNH(String correctorNH) {
        this.correctorNH = correctorNH;
    }

    public String getAccountBillNH() {
        return accountBillNH;
    }

    public void setAccountBillNH(String accountBillNH) {
        this.accountBillNH = accountBillNH;
    }
}
