package pl.nask.crs.payment.dao.ibatis.objects;

import java.math.BigDecimal;
import java.util.Date;

import pl.nask.crs.payment.DepositTransactionType;

public class InternalHistoricalDeposit extends InternalDeposit {

    private long changeId;

    private Date histChangeDate;

    public InternalHistoricalDeposit() {

    }

    public InternalHistoricalDeposit(String nicHandleId, Date transactionDate, BigDecimal openBal, BigDecimal closeBal,
            BigDecimal transactionAmount, DepositTransactionType transactionType, String orderId, String correctorNH,
            String remark) {
        super(nicHandleId, transactionDate, openBal, closeBal, transactionAmount, transactionType, orderId, correctorNH,
                remark);
    }

    public long getChangeId() {
        return changeId;
    }

    public void setChangeId(long changeId) {
        this.changeId = changeId;
    }

    public Date getHistChangeDate() {
        return histChangeDate;
    }

    public void setHistChangeDate(Date histChangeDate) {
        this.histChangeDate = histChangeDate;
    }

}
