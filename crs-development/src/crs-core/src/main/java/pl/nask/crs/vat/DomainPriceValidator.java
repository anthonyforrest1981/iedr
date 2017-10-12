package pl.nask.crs.vat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.exceptions.DomainPriceDatesOutOfBoundException;
import pl.nask.crs.commons.exceptions.ThirdDecimalPlaceException;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.price.DomainPrice;

public final class DomainPriceValidator {

    // hiding constructor of helper class
    private DomainPriceValidator() {}

    static public void validate(List<Vat> vatRates, DomainPrice domainPrice)
            throws ThirdDecimalPlaceException, DomainPriceDatesOutOfBoundException {
        Validator.assertNotEmpty(domainPrice.getCode(), "code");
        Validator.assertNotEmpty(domainPrice.getDescription(), "description");
        Validator.assertNotNull(domainPrice.getPrice(), "price");
        Validator.assertNotNull(domainPrice.getValidFrom(), "valid from");
        Validator.assertNotNull(domainPrice.getValidTo(), "valid to");
        validateDates(domainPrice.getValidFrom(), domainPrice.getValidTo());
        validateVat(vatRates, domainPrice.getPrice(), domainPrice.getValidFrom(), domainPrice.getValidTo());
    }

    static private void validateDates(Date from, Date to) throws DomainPriceDatesOutOfBoundException {
        if (from.after(to)) {
            throw new DomainPriceDatesOutOfBoundException("The start date: " + from +
                    " cannot be later than the end date: " + to);
        }
    }

    static private void validateVat(List<Vat> vatRates, BigDecimal price, Date validFrom, Date validTo)
            throws ThirdDecimalPlaceException {
        List<Vat> rates = new LinkedList<Vat>(vatRates);
        while (!rates.isEmpty()) {
            Vat rate = rates.remove(0);
            if (isVatMatching(rate, validFrom, validTo, vatRates)) {
                if (MoneyUtils.hasThirdDecimalPlace(price, rate.getVatRate())) {
                    String msg = String.format("Vat rate: %s, Price: %s valid from %s to %s", new Object[] {rate,
                            price, validFrom, validTo});
                    throw new ThirdDecimalPlaceException(msg);
                }
            }
        }
    }

    /**
     * check if the vat rate given as the first argument matches the period determined by from and to dates.
     *
     */
    static private boolean isVatMatching(Vat rate, Date from, Date validTo, List<Vat> otherVatRates) {
        if (DateUtils.isDateBetween(rate.getFromDate(), from, validTo, true, true)) {
            return true; // starts after the from, ends before it ends
        }

        if (DateUtils.isDateBetween(rate.getFromDate(), validTo, null, false, false)) {
            return false; // the vat rate starts
        }

        // now: the vat.from is earlier than 'from' date: have to check, if there is no vat rate which would invalidate this one before the 'from' date.

        for (Vat r : otherVatRates) {
            if (rate.getCategory().equals(r.getCategory())
                    && DateUtils.isDateBetween(r.getFromDate(), rate.getFromDate(), from, false, true)) {
                return false; // there is a date, which would invalidate the one being checked
            }
        }

        return true;

    }

}
