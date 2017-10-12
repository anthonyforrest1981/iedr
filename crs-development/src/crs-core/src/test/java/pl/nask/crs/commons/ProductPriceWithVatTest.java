package pl.nask.crs.commons;

import java.util.Date;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.vat.ProductPriceWithVat;
import pl.nask.crs.vat.Vat;

public class ProductPriceWithVatTest {

    @Test
    public void vatTest() {
        Vat vat = new Vat(1, "A", new Date(), MoneyUtils.getScaledVatValue(0.23));
        ProductPriceWithVat productPriceWithVat = new ProductPriceWithVat(Period.fromYears(1), 13,
                MoneyUtils.getRoundedAndScaledValue(55), vat);
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(55), productPriceWithVat.getNetAmount());
        AssertJUnit.assertEquals(MoneyUtils.getScaledVatValue(0.23), productPriceWithVat.getVat().getVatRate());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(12.65), productPriceWithVat.getVatAmount());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(67.65), productPriceWithVat.getTotal());
        AssertJUnit.assertEquals(13, productPriceWithVat.getProductId());

        vat = new Vat(1, "A", new Date(), MoneyUtils.getScaledVatValue(0.233));
        productPriceWithVat = new ProductPriceWithVat(Period.fromYears(1), 13, MoneyUtils.getRoundedAndScaledValue(55.33), vat);
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(55.33), productPriceWithVat.getNetAmount());
        AssertJUnit.assertEquals(MoneyUtils.getScaledVatValue(0.233), productPriceWithVat.getVat().getVatRate());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(12.89), productPriceWithVat.getVatAmount());
        AssertJUnit.assertEquals(MoneyUtils.getRoundedAndScaledValue(68.22), productPriceWithVat.getTotal());
        AssertJUnit.assertEquals(13, productPriceWithVat.getProductId());
    }
}
