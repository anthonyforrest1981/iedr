package pl.nask.crs.payment;

import java.math.BigDecimal;

import pl.nask.crs.commons.MoneyUtils;

public class ExtendedDeposit extends Deposit {

    private BigDecimal reservedFunds;

    public ExtendedDeposit(Deposit deposit, BigDecimal reservedFunds) {
        super(deposit.getNicHandleId(), deposit.getNicHandleName(), deposit.getTransactionDate(), deposit.getOpenBal(),
                deposit.getCloseBal(), deposit.getTransactionAmount(), deposit.getTransactionType(), deposit
                        .getOrderId(), deposit.getCorrectorNH(), deposit.getRemark());
        this.reservedFunds = reservedFunds;
    }

    public BigDecimal getCloseBalMinusReservations() {
        return MoneyUtils.subtract(getCloseBal(), reservedFunds);
    }

    public BigDecimal getReservedFunds() {
        return reservedFunds;
    }
}
