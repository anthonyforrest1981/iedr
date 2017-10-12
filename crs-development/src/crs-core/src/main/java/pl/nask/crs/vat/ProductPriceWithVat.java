package pl.nask.crs.vat;

import java.math.BigDecimal;

import pl.nask.crs.commons.Period;

public class ProductPriceWithVat extends PriceWithVat {

    private final Period period;
    private final int productId;

    public ProductPriceWithVat(Period period, int productId, BigDecimal netAmount, Vat vat) {
        super(netAmount, vat);
        this.period = period;
        this.productId = productId;
    }

    public Period getPeriod() {
        return period;
    }

    public int getProductId() {
        return productId;
    }

    @Override
    public String toString() {
        return String.format("productCode: %s, periodInYears: %s, %s",
                productId, period == null ? null : period.getYears(), super.toString());
    }

}
