package pl.nask.crs.vat;

import java.math.BigDecimal;

import pl.nask.crs.commons.MoneyUtils;

import static pl.nask.crs.commons.MoneyUtils.DEFAULT_SCALE;

public class PriceWithVat {

    private final BigDecimal netAmount;
    private final Vat vat;
    private final BigDecimal vatAmount;
    private final BigDecimal total;

    public PriceWithVat(BigDecimal netAmount, Vat vat) {
        this.netAmount = netAmount;
        this.netAmount.setScale(DEFAULT_SCALE, BigDecimal.ROUND_HALF_EVEN);
        this.vat = vat;
        this.vatAmount = MoneyUtils.multiply(netAmount, vat.getVatRate());
        this.total = MoneyUtils.add(this.netAmount, this.vatAmount);
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public Vat getVat() {
        return vat;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return String.format("PriceWithVat[netAmount: %s, vat: %s, vatAmount: %s]", netAmount, vat, vatAmount);
    }
}
