package pl.nask.crs.commons.utils;

import java.math.BigDecimal;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.MoneyUtils;

import static pl.nask.crs.commons.MoneyUtils.*;

public class MoneyUtilsTest {

    @Test
    public void roundedAndScaledValueTest() {
        double intAsDouble = 47;
        BigDecimal scaled = getRoundedAndScaledValue(intAsDouble);
        AssertJUnit.assertEquals(47, scaled.doubleValue(), 0.00001);
        AssertJUnit.assertEquals(0, scaled.scale());

        BigDecimal intAsBigDec = BigDecimal.valueOf(47);
        scaled = getRoundedAndScaledValue(intAsBigDec);
        AssertJUnit.assertEquals(47, scaled.doubleValue(), 0.00001);
        AssertJUnit.assertEquals(0, scaled.scale());

        double fractionAsDouble = 0.579;
        scaled = getRoundedAndScaledValue(fractionAsDouble);
        AssertJUnit.assertEquals(0.58, scaled.doubleValue(), 0.00001);
        AssertJUnit.assertEquals(2, scaled.scale());

        BigDecimal fractionAsBigDec = BigDecimal.valueOf(0.579);
        scaled = getRoundedAndScaledValue(fractionAsBigDec);
        AssertJUnit.assertEquals(0.58, scaled.doubleValue(), 0.00001);
        AssertJUnit.assertEquals(2, scaled.scale());
    }

    @Test
    public void scaledVatValueTest() {
        double intAsDouble = 1;
        BigDecimal scaled = getScaledVatValue(intAsDouble);
        AssertJUnit.assertEquals(1, scaled.doubleValue(), 0.00001);
        AssertJUnit.assertEquals(3, scaled.scale());

        BigDecimal intAsBigDec = BigDecimal.valueOf(1);
        scaled = getScaledVatValue(intAsBigDec);
        AssertJUnit.assertEquals(1, scaled.doubleValue(), 0.00001);
        AssertJUnit.assertEquals(3, scaled.scale());

        double fractionAsDouble = 0.2299;
        scaled = getScaledVatValue(fractionAsDouble);
        AssertJUnit.assertEquals(0.23, scaled.doubleValue(), 0.00001);
        AssertJUnit.assertEquals(3, scaled.scale());

        BigDecimal fractionAsBigDec = BigDecimal.valueOf(0.2299);
        scaled = getScaledVatValue(fractionAsBigDec);
        AssertJUnit.assertEquals(0.23, scaled.doubleValue(), 0.00001);
        AssertJUnit.assertEquals(3, scaled.scale());
    }

    @Test
    public void valueInLowestCurrencyUnitTest() {
        AssertJUnit.assertEquals(1810, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.10)));
        AssertJUnit.assertEquals(1811, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.11)));
        AssertJUnit.assertEquals(1812, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.12)));
        AssertJUnit.assertEquals(1813, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.13)));
        AssertJUnit.assertEquals(1814, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.14)));
        AssertJUnit.assertEquals(1815, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.15)));
        AssertJUnit.assertEquals(1816, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.16)));
        AssertJUnit.assertEquals(1817, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.17)));
        AssertJUnit.assertEquals(1818, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.18)));
        AssertJUnit.assertEquals(1819, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.19)));
        AssertJUnit.assertEquals(1820, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.2)));
        AssertJUnit.assertEquals(1820, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.201)));
        AssertJUnit.assertEquals(1820, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.205)));
        AssertJUnit.assertEquals(1821, getValueInLowestCurrencyUnit(BigDecimal.valueOf(18.206)));
        AssertJUnit.assertEquals(-1821, getValueInLowestCurrencyUnit(BigDecimal.valueOf(-18.206)));
    }

    @Test
    public void valueInStandardCurrencyUnit() {
        AssertJUnit.assertEquals(BigDecimal.valueOf(18.14), getValueInStandardCurrencyUnit(1814));
        AssertJUnit.assertEquals(BigDecimal.valueOf(18.15), getValueInStandardCurrencyUnit(1815));
        AssertJUnit.assertEquals(BigDecimal.valueOf(18.16), getValueInStandardCurrencyUnit(1816));
        AssertJUnit.assertEquals(BigDecimal.valueOf(18.17), getValueInStandardCurrencyUnit(1817));
        AssertJUnit.assertEquals(BigDecimal.valueOf(18.18), getValueInStandardCurrencyUnit(1818));
        AssertJUnit.assertEquals(BigDecimal.valueOf(18.19), getValueInStandardCurrencyUnit(1819));
        AssertJUnit.assertEquals(BigDecimal.valueOf(18.20).setScale(2), getValueInStandardCurrencyUnit(1820));
        AssertJUnit.assertEquals(BigDecimal.valueOf(18.21), getValueInStandardCurrencyUnit(1821));
        AssertJUnit.assertEquals(BigDecimal.valueOf(182.15), getValueInStandardCurrencyUnit(18215));
        AssertJUnit.assertEquals(BigDecimal.valueOf(182.16), getValueInStandardCurrencyUnit(18216));
        AssertJUnit.assertEquals(BigDecimal.valueOf(-182.16), getValueInStandardCurrencyUnit(-18216));
    }

    @Test
    public void substractionTest() {
        AssertJUnit.assertEquals(BigDecimal.valueOf(65635.01),
                subtract(BigDecimal.valueOf(65651.95), BigDecimal.valueOf(16.94)));
        AssertJUnit.assertEquals(BigDecimal.valueOf(65616.86),
                subtract(BigDecimal.valueOf(65635.01), BigDecimal.valueOf(18.15)));
        AssertJUnit.assertEquals(BigDecimal.valueOf(65549.15),
                subtract(BigDecimal.valueOf(65566.09), BigDecimal.valueOf(16.94)));
        AssertJUnit.assertEquals(BigDecimal.valueOf(65549.15),
                subtract(BigDecimal.valueOf(65566.09), BigDecimal.valueOf(16.94)));
        AssertJUnit.assertEquals(BigDecimal.valueOf(9999999.08),
                subtract(BigDecimal.valueOf(10000000.03), BigDecimal.valueOf(0.95)));
        AssertJUnit.assertEquals(BigDecimal.valueOf(9999999.08),
                subtract(BigDecimal.valueOf(10000000.03), BigDecimal.valueOf(0.95f)));

        AssertJUnit.assertEquals(BigDecimal.valueOf(-9999999.08),
                subtract(BigDecimal.valueOf(-10000000.03), BigDecimal.valueOf(-0.95f)));
    }

    @Test
    public void additionTest() {
        AssertJUnit.assertEquals(BigDecimal.valueOf(9000000.98),
                add(BigDecimal.valueOf(9000000.03), BigDecimal.valueOf(0.95)));
        AssertJUnit.assertEquals(BigDecimal.valueOf(9000001.09),
                add(BigDecimal.valueOf(9000000.14), BigDecimal.valueOf(0.95)));

        AssertJUnit.assertEquals(BigDecimal.valueOf(-9000001.09),
                add(BigDecimal.valueOf(-9000000.14), BigDecimal.valueOf(-0.95)));
    }

    @Test
    public void multiplicationTest() {
        AssertJUnit.assertEquals(BigDecimal.valueOf(13.65),
                multiply(BigDecimal.valueOf(65.00), BigDecimal.valueOf(0.21)));
        AssertJUnit.assertEquals(BigDecimal.valueOf(20.91),
                multiply(BigDecimal.valueOf(99.55), BigDecimal.valueOf(0.21)));

        AssertJUnit.assertEquals(BigDecimal.valueOf(-20.91),
                multiply(BigDecimal.valueOf(-99.55), BigDecimal.valueOf(0.21)));
    }

    @Test
    public void hasThirdDecimalPlaceTest() throws Exception {
        BigDecimal price;
        price = MoneyUtils.getRoundedAndScaledValue(BigDecimal.valueOf(100.1), 2);
        AssertJUnit.assertFalse(MoneyUtils.hasThirdDecimalPlace(price, BigDecimal.valueOf(0.10)));
        price = MoneyUtils.getRoundedAndScaledValue(BigDecimal.valueOf(100.1), 5);
        AssertJUnit.assertFalse(MoneyUtils.hasThirdDecimalPlace(price, BigDecimal.valueOf(0.1)));
        price = MoneyUtils.getRoundedAndScaledValue(BigDecimal.valueOf(100.00), 2);
        AssertJUnit.assertFalse(MoneyUtils.hasThirdDecimalPlace(price, BigDecimal.valueOf(0.0)));
        price = MoneyUtils.getRoundedAndScaledValue(BigDecimal.valueOf(100.00), 5);
        AssertJUnit.assertFalse(MoneyUtils.hasThirdDecimalPlace(price, BigDecimal.valueOf(0.0)));
        price = MoneyUtils.getRoundedAndScaledValue(BigDecimal.valueOf(100.00));
        AssertJUnit.assertFalse(MoneyUtils.hasThirdDecimalPlace(price, BigDecimal.valueOf(0.0)));
        price = MoneyUtils.getRoundedAndScaledValue(BigDecimal.valueOf(0));
        AssertJUnit.assertFalse(MoneyUtils.hasThirdDecimalPlace(price, BigDecimal.valueOf(0)));
        price = MoneyUtils.getRoundedAndScaledValue(BigDecimal.valueOf(100.2), 2);
        AssertJUnit.assertFalse(MoneyUtils.hasThirdDecimalPlace(price, BigDecimal.valueOf(0.2)));
        price = MoneyUtils.getRoundedAndScaledValue(BigDecimal.valueOf(100.54));
        AssertJUnit.assertTrue(MoneyUtils.hasThirdDecimalPlace(price, BigDecimal.valueOf(0.32)));
    }

}
