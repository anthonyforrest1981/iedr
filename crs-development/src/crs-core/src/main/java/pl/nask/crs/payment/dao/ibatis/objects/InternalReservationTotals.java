package pl.nask.crs.payment.dao.ibatis.objects;

import java.math.BigDecimal;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class InternalReservationTotals {

    private long totalResults;

    private BigDecimal totalAmount;

    private BigDecimal totalVat;

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalVat() {
        return totalVat;
    }

    public void setTotalVat(BigDecimal totalVat) {
        this.totalVat = totalVat;
    }
}
