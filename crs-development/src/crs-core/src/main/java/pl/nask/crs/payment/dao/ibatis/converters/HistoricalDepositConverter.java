package pl.nask.crs.payment.dao.ibatis.converters;

import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.dao.ibatis.objects.InternalHistoricalDeposit;

public class HistoricalDepositConverter
        extends AbstractConverter<InternalHistoricalDeposit, HistoricalObject<Deposit>> {

    protected HistoricalObject<Deposit> _to(InternalHistoricalDeposit internalDeposit) {
        Deposit deposit = new Deposit(internalDeposit.getNicHandleId(), internalDeposit.getNicHandleName(),
                internalDeposit.getTransactionDate(), internalDeposit.getOpenBal(), internalDeposit.getCloseBal(),
                internalDeposit.getTransactionAmount(), internalDeposit.getTransactionType(),
                internalDeposit.getOrderId(), internalDeposit.getCorrectorNH(), internalDeposit.getRemark());
        HistoricalObject<Deposit> histDeposit = new HistoricalObject<>();
        histDeposit.setObject(deposit);
        histDeposit.setChangeId(internalDeposit.getChangeId());
        histDeposit.setChangeDate(internalDeposit.getHistChangeDate());
        // DepositHist table has no Chng_NH column.
        histDeposit.setChangedBy(null);
        return histDeposit;
    }

    protected InternalHistoricalDeposit _from(HistoricalObject<Deposit> histDeposit) {
        Deposit deposit = histDeposit.getObject();
        InternalHistoricalDeposit ihd = new InternalHistoricalDeposit(deposit.getNicHandleId(),
                deposit.getTransactionDate(), deposit.getOpenBal(), deposit.getCloseBal(),
                deposit.getTransactionAmount(), deposit.getTransactionType(), deposit.getOrderId(),
                deposit.getCorrectorNH(), deposit.getRemark());
        ihd.setChangeId(histDeposit.getChangeId());
        ihd.setHistChangeDate(histDeposit.getChangeDate());
        return ihd;
    }
}
