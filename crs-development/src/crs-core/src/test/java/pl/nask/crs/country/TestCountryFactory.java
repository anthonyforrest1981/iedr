package pl.nask.crs.country;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;

import static org.testng.Assert.assertEquals;

public class TestCountryFactory extends AbstractTest {
    @Resource
    CountryFactory countryFactory;

    @Test
    public void testFind() {
        List<Country> r = countryFactory.getCountries();
        AssertJUnit.assertNotNull("No results found 1", r);
        AssertJUnit.assertTrue("No results found 3", r.size() > 0);
        AssertJUnit.assertNotNull("Name field not filled", r.get(0).getName());
        AssertJUnit.assertNotNull("Counties field not filled", r.get(0).getCounties());
    }

    @Test
    public void testCountriesCache() {
        // This is to test CRS-835. Due to this bug, counties list for country was extended with each call.
        List<Country> countries = countryFactory.getCountries();
        Map<Integer, Integer> countiesCount = new HashMap<>();
        for (Country c : countries) {
            countiesCount.put(c.getId(), c.getCounties().size());
        }
        countries = countryFactory.getCountries();
        for (Country c : countries) {
            assertEquals(c.getCounties().size(), countiesCount.get(c.getId()).intValue());
        }
    }

    @Test
    public void testGetUSA() throws InvalidCountryException {
        Country r = countryFactory.getCountry(254);
        AssertJUnit.assertNotNull("No results found 1", r);
        AssertJUnit.assertNotNull("Name field not filled", r.getName());
        AssertJUnit.assertNotNull("Counties field not filled", r.getCounties());
        AssertJUnit.assertNotNull("No counties found", r.getCounties().size() > 0);
    }

    @Test
    public void validateCountyOk() throws InvalidCountryException, InvalidCountyException {
        validateCountryCounty(254, 90);
    }

    @Test(expectedExceptions = InvalidCountyException.class)
    public void validateCountyFail() throws InvalidCountryException, InvalidCountyException {
        validateCountryCounty(254, -1);
    }

    @Test
    public void validateCountyAny() throws InvalidCountryException, InvalidCountyException {
        validateCountryCounty(199, 0); // Poland
    }

    @Test(expectedExceptions = CountyNotExistsException.class)
    public void validateCountyStrict() throws InvalidCountryException, InvalidCountyException {
        validateCountryCounty(199, 90);
    }

    private void validateCountryCounty(int countryId, int countyId)
            throws InvalidCountryException, InvalidCountyException {
        Country country = countryFactory.getCountry(countryId);
        County county = countryFactory.getCounty(countyId);
        countryFactory.validate(country, county);
    }

}
