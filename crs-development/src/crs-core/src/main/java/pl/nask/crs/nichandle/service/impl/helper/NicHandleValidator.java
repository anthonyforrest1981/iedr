package pl.nask.crs.nichandle.service.impl.helper;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.commons.utils.EmailValidator;
import pl.nask.crs.commons.utils.InvalidEmailException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.CountryFactory;
import pl.nask.crs.country.County;
import pl.nask.crs.country.InvalidCountyException;

public class NicHandleValidator {

    public static void validateNicHandle(String name, String email, String address, Country country, County county,
            Long accountNumber, CountryFactory countryFactory, AccountSearchService accountSearchService)
            throws InvalidCountyException, InvalidEmailException, AccountNotFoundException, AccountNotActiveException {
        Validator.assertNotEmpty(name, "name");
        Validator.assertNotEmpty(address, "address");
        Validator.assertNotNull(country, "country");
        Validator.assertNotNull(accountNumber, "account number");
        EmailValidator.validateEmail(email);
        countryFactory.validate(country, county);
        accountSearchService.confirmAccountActive(accountNumber);
    }

}
