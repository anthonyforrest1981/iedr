package pl.nask.crs.payment;

import java.math.BigDecimal;
import java.util.List;

import pl.nask.crs.vat.PriceWithVat;

import static pl.nask.crs.commons.MoneyUtils.add;
import static pl.nask.crs.commons.MoneyUtils.getRoundedAndScaledValue;

public class Payment {

    private final BigDecimal fee;
    private final BigDecimal vat;
    private final BigDecimal total;

    public Payment(BigDecimal fee, BigDecimal vat, BigDecimal total) {
        this.fee = fee;
        this.vat = vat;
        this.total = total;
    }

    public Payment(PriceWithVat priceWithVat) {
        this(priceWithVat.getNetAmount(), priceWithVat.getVatAmount(), priceWithVat.getTotal());
    }

    public Payment(List<? extends PriceWithVat> prices) {
        BigDecimal tmpFee = BigDecimal.ZERO;
        BigDecimal tmpVat = BigDecimal.ZERO;
        for (PriceWithVat priceWithVat : prices) {
            tmpFee = add(tmpFee, priceWithVat.getNetAmount());
            tmpVat = add(tmpVat, priceWithVat.getVatAmount());
        }
        this.fee = tmpFee;
        this.vat = tmpVat;
        this.total = add(this.fee, this.vat);
    }

    public BigDecimal getFee() {
        return getRoundedAndScaledValue(fee);
    }

    public BigDecimal getVat() {
        return getRoundedAndScaledValue(vat);
    }

    public BigDecimal getTotal() {
        return getRoundedAndScaledValue(total);
    }
}
