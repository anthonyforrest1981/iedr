package pl.nask.crs.country;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.country.dao.CountryDAO;
import pl.nask.crs.country.dao.CountyDAO;
import pl.nask.crs.country.search.CountySearchCriteria;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class TestCountryDAO extends AbstractTest {

    @Resource
    CountryDAO countryDAO;

    @Resource
    CountyDAO countyDAO;

    @Test
    public void testFind() {
        SearchResult<Country> r = countryDAO.find(null);
        assertNotNull(r, "SearchResult object was null");
        assertNotNull(r.getResults(), "Results were null");
        assertEquals(r.getResults().size(), 273);
        for (Country c : r.getResults()) {
            testCountry(c);
        }
    }

    @Test
    public void testGet() {
        Country c = countryDAO.get(121);
        assertEquals(c.getName(), "Ireland");
        testCountry(c);
    }

    private void testCountry(Country c) {
        assertNotNull(c.getName(), "Name field not filled");
        assertNotNull(c.getCounties(), "Counties list was null for country: " + c.getName());
        CountySearchCriteria criteria = new CountySearchCriteria();
        criteria.setCountryId(c.getId());
        assertEquals(c.getCounties().size(), countyDAO.count(criteria));
    }

}
