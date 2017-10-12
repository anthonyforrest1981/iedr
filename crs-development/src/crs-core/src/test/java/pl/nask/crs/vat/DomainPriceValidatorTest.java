package pl.nask.crs.vat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.exceptions.DomainPriceDatesOutOfBoundException;
import pl.nask.crs.commons.exceptions.ThirdDecimalPlaceException;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.price.DomainPrice;

public class DomainPriceValidatorTest {
    private Vat rate0 = Vat.newInstance("A", DateUtils.getCurrDate(-100), MoneyUtils.getScaledVatValue(1));
    private Vat rate1 = Vat.newInstance("A", DateUtils.getCurrDate(-10), MoneyUtils.getScaledVatValue(0.2));
    private Vat rate2 = Vat.newInstance("A", DateUtils.getCurrDate(0), MoneyUtils.getScaledVatValue(0.22));
    private Vat rate3 = Vat.newInstance("A", DateUtils.getCurrDate(10), MoneyUtils.getScaledVatValue(0.21));
    private Vat rate4 = Vat.newInstance("A", DateUtils.getCurrDate(20), MoneyUtils.getScaledVatValue(1));

    private List<Vat> vatList = new ArrayList<Vat>(Arrays.asList(rate0, rate1, rate2, rate3, rate4));

    private BigDecimal price = MoneyUtils.getRoundedAndScaledValue(1.5);

    @Test
    public void testPriceValidRate1() throws Exception {
        DomainPriceValidator.validate(vatList, getDomainPrice(-20, -9));
        DomainPriceValidator.validate(vatList, getDomainPrice(-10, -9));
        DomainPriceValidator.validate(vatList, getDomainPrice(-9, -8));
    }

    @Test
    public void testPriceValidRate2() throws Exception {
        DomainPriceValidator.validate(vatList, getDomainPrice(-20, 1));
        DomainPriceValidator.validate(vatList, getDomainPrice(-10, 1));
        DomainPriceValidator.validate(vatList, getDomainPrice(0, 1));
    }

    @Test(expectedExceptions = DomainPriceDatesOutOfBoundException.class)
    public void testPriceInvalidDateFromAfterDateTo() throws Exception {
        DomainPriceValidator.validate(vatList, getDomainPrice(-20, -21));
    }

    @Test(expectedExceptions = ThirdDecimalPlaceException.class)
    public void testPriceInvalidRate3InsideCheckedPeriod() throws Exception {
        DomainPriceValidator.validate(vatList, getDomainPrice(-20, 100));
    }

    @Test(expectedExceptions = ThirdDecimalPlaceException.class)
    public void testPriceInvalidRate3AllCheckedPeriodInside() throws Exception {
        DomainPriceValidator.validate(vatList, getDomainPrice(11, 12));
    }

    @Test(expectedExceptions = ThirdDecimalPlaceException.class)
    public void testPriceInvalidRate3PeriodEnd() throws Exception {
        DomainPriceValidator.validate(vatList, getDomainPrice(9, 10));
    }

    @Test(expectedExceptions = ThirdDecimalPlaceException.class)
    public void testPriceInvalidRate3PeriodStart() throws Exception {
        DomainPriceValidator.validate(vatList, getDomainPrice(19, 20));
    }

    private DomainPrice getDomainPrice(int daysFromNowToDateFrom, int daysFromNowToDateTo) {
        return DomainPrice.newInstance("code", "description", price, 1, DateUtils.getCurrDate(daysFromNowToDateFrom),
                DateUtils.getCurrDate(daysFromNowToDateTo), false, false, false);
    }
}
