package pl.nask.crs.nichandle.service.impl.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.commons.utils.InvalidEmailException;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.CountryFactory;
import pl.nask.crs.country.County;
import pl.nask.crs.country.CountyNotExistsException;
import pl.nask.crs.nichandle.AbstractContextAwareTest;

public class NicHandleValidatorTest extends AbstractContextAwareTest {

    @Autowired
    CountryFactory countryFactory;

    @Autowired
    AccountSearchService accountSearchService;

    @Test
    public void testValidationPassed() throws Exception {
        Country country = countryFactory.getCountry(121);
        County county = countryFactory.getCounty(14);
        NicHandleValidator.validateNicHandle("Name", "email@iedr.ie", "Address", country, county,
                666L, countryFactory, accountSearchService);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEmptyName() throws Exception {
        Country country = countryFactory.getCountry(121);
        County county = countryFactory.getCounty(14);
        NicHandleValidator.validateNicHandle("", "email@iedr.ie", "Address", country, county,
                666L, countryFactory, accountSearchService);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullAccountNumber() throws Exception {
        Country country = countryFactory.getCountry(121);
        County county = countryFactory.getCounty(14);
        NicHandleValidator.validateNicHandle("Name", "email@iedr.ie", "Address", country, county,
                null, countryFactory, accountSearchService);

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullAddress() throws Exception {
        Country country = countryFactory.getCountry(121);
        County county = countryFactory.getCounty(14);
        NicHandleValidator.validateNicHandle("Name", "email@iedr.ie", null, country, county,
                666L, countryFactory, accountSearchService);

    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void testInvalidEmail() throws Exception {
        Country country = countryFactory.getCountry(121);
        County county = countryFactory.getCounty(14);
        NicHandleValidator.validateNicHandle("Name", "em@ail@iedr.ie", "Address", country, county,
                666L, countryFactory, accountSearchService);

    }

    @Test(expectedExceptions = CountyNotExistsException.class)
    public void testCountryCountyMismatch() throws Exception {
        Country country = countryFactory.getCountry(119);
        County county = countryFactory.getCounty(14);
        NicHandleValidator.validateNicHandle("Name", "email@iedr.ie", "Address", country, county,
                666L, countryFactory, accountSearchService);

    }

    @Test(expectedExceptions = AccountNotFoundException.class)
    public void testNonexistentAccount() throws Exception {
        Country country = countryFactory.getCountry(121);
        County county = countryFactory.getCounty(14);
        NicHandleValidator.validateNicHandle("Name", "email@iedr.ie", "Address", country, county,
                600L, countryFactory, accountSearchService);

    }

    @Test(expectedExceptions = AccountNotFoundException.class)
    public void testInactiveAccount() throws Exception {
        Country country = countryFactory.getCountry(121);
        County county = countryFactory.getCounty(14);
        NicHandleValidator.validateNicHandle("Name", "email@iedr.ie", "Address", country, county,
                100L, countryFactory, accountSearchService);

    }

}
