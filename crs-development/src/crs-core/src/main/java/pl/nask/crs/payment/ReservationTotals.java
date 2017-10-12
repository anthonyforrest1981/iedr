package pl.nask.crs.payment;

import java.math.BigDecimal;

import static pl.nask.crs.commons.MoneyUtils.DEFAULT_SCALE;
import static pl.nask.crs.commons.MoneyUtils.add;
import static pl.nask.crs.commons.MoneyUtils.getRoundedAndScaledValue;

public class ReservationTotals {

    private long totalResults;

    private BigDecimal totalAmount;

    private BigDecimal totalVat;

    private BigDecimal total;

    public ReservationTotals(long totalResults, BigDecimal totalAmount, BigDecimal totalVat) {
        this.totalResults = totalResults;
        this.totalAmount = getRoundedAndScaledValue(totalAmount);
        this.totalVat = getRoundedAndScaledValue(totalVat);
        this.total = getRoundedAndScaledValue(add(this.totalAmount, this.totalVat));
    }

    public long getTotalResults() {
        return totalResults;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getTotalVat() {
        return totalVat;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return String.format("ReservationTotals[totalResults: %s, totalAmount: %s, totalVat: %s]", totalResults,
                totalAmount, totalVat);
    }
}
