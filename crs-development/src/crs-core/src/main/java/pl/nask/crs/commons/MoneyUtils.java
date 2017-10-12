package pl.nask.crs.commons;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class MoneyUtils {

    public static final MathContext mc = new MathContext(9, RoundingMode.HALF_EVEN);
    public static final int DEFAULT_SCALE = 2;
    public static final int VAT_SCALE = 3;
    private static final int LOWEST_CURRENCY_MULTIPLIER = 100;

    /**
     * Returns BigDecimal with precision of 2 (or 0, if float represents a whole value)
     *
     * @param value
     * @return
     */
    public static BigDecimal getRoundedAndScaledValue(BigDecimal value) {
        if (value == null)
            return null;

        if (value.doubleValue() - value.intValue() != 0)
            return value.setScale(DEFAULT_SCALE, BigDecimal.ROUND_HALF_EVEN);
        else
            return value.setScale(0, BigDecimal.ROUND_HALF_EVEN);
    }

    public static BigDecimal getRoundedAndScaledValue(double value) {
        long v = (long) value;

        if (value - v != 0)
            return BigDecimal.valueOf(value).setScale(DEFAULT_SCALE, BigDecimal.ROUND_HALF_EVEN);
        else
            return BigDecimal.valueOf(value).setScale(0);
    }

    /**
     * Returns BigDecimal with precision of scale value
     *
     * @param value
     * @return
     */
    public static BigDecimal getRoundedAndScaledValue(BigDecimal value, int scale) {
        if (value == null)
            return null;
        return value.setScale(scale, BigDecimal.ROUND_HALF_EVEN);
    }

    public static BigDecimal getScaledVatValue(BigDecimal value) {
        return getRoundedAndScaledValue(value, VAT_SCALE);
    }

    public static BigDecimal getScaledVatValue(double value) {
        return getScaledVatValue(BigDecimal.valueOf(value));
    }

    /**
     * Returns value in the lowest unit of the currency i.e. 100 euro would be entered as 10000.
     *
     * @param value
     * @return
     */
    public static int getValueInLowestCurrencyUnit(BigDecimal value) {
        BigDecimal ret = value.multiply(BigDecimal.valueOf(LOWEST_CURRENCY_MULTIPLIER), MoneyUtils.mc);
        return ret.setScale(0, BigDecimal.ROUND_HALF_EVEN).intValue();
    }

    public static BigDecimal getValueInStandardCurrencyUnit(int value) {
        BigDecimal bAmount = BigDecimal.valueOf(value);
        bAmount = bAmount.divide(BigDecimal.valueOf(LOWEST_CURRENCY_MULTIPLIER), MoneyUtils.mc);
        return getRoundedAndScaledValue(bAmount);
    }

    public static BigDecimal getBigDecimalValueInStandardCurrencyUnit(int value) {
        BigDecimal bAmount = BigDecimal.valueOf(value);
        bAmount = bAmount.divide(BigDecimal.valueOf(LOWEST_CURRENCY_MULTIPLIER), MoneyUtils.mc);
        return getRoundedAndScaledValue(bAmount);
    }

    public static BigDecimal getBigDecimalValueInStandardCurrencyUnit(Integer value) {
        if (value == null)
            return null;
        BigDecimal bAmount = BigDecimal.valueOf(value);
        bAmount = bAmount.divide(BigDecimal.valueOf(LOWEST_CURRENCY_MULTIPLIER), MoneyUtils.mc);
        return getRoundedAndScaledValue(bAmount);
    }

    public static BigDecimal getBigDecimalValueInStandardCurrencyUnit(int value, int scale) {
        BigDecimal bAmount = BigDecimal.valueOf(value);
        bAmount = bAmount.divide(BigDecimal.valueOf(LOWEST_CURRENCY_MULTIPLIER), MoneyUtils.mc);
        return getRoundedAndScaledValue(bAmount, scale);
    }

    public static BigDecimal getBigDecimalValueInStandardCurrencyUnit(Integer value, int scale) {
        if (value == null)
            return null;
        BigDecimal bAmount = BigDecimal.valueOf(value);
        bAmount = bAmount.divide(BigDecimal.valueOf(LOWEST_CURRENCY_MULTIPLIER), MoneyUtils.mc);
        return getRoundedAndScaledValue(bAmount, scale);
    }

    public static BigDecimal subtract(BigDecimal minuend, BigDecimal subreahend) {
        BigDecimal retVal = minuend;
        retVal = retVal.subtract(subreahend, mc);
        return getRoundedAndScaledValue(retVal);
    }

    public static BigDecimal add(BigDecimal value1, BigDecimal value2) {
        BigDecimal retVal = value1;
        retVal = retVal.add(value2, mc);
        return getRoundedAndScaledValue(retVal);
    }

    public static BigDecimal multiply(BigDecimal value1, BigDecimal value2) {
        BigDecimal retVal = value1;
        retVal = retVal.multiply(value2, mc);
        return getRoundedAndScaledValue(retVal);
    }

    public static BigDecimal divide(BigDecimal value1, int value2) {
        BigDecimal retVal = value1;
        retVal = retVal.divide(BigDecimal.valueOf(value2), mc);
        return getRoundedAndScaledValue(retVal);
    }

    public static boolean hasThirdDecimalPlace(BigDecimal price, BigDecimal vatRate) {
        BigDecimal vatValue = price.multiply(vatRate);
        if (BigDecimal.ZERO.compareTo(vatValue) == 0) {
            return false;
        }
        // If you won't recreate there will be a problem with scale!!!
        BigDecimal stripped = new BigDecimal(vatValue.stripTrailingZeros().toPlainString());
        String vatValueAsString = stripped.toPlainString();
        int decimalPointIndex = vatValueAsString.indexOf(".");
        int decimalPlaces = decimalPointIndex < 0 ? 0 : vatValueAsString.length() - decimalPointIndex - 1;
        return decimalPlaces >= 3;
    }
}
