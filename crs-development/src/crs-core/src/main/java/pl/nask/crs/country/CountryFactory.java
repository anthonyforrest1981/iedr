package pl.nask.crs.country;

import java.util.List;

import pl.nask.crs.country.dao.CountryDAO;
import pl.nask.crs.country.dao.CountyDAO;
import pl.nask.crs.country.search.CountySearchCriteria;

public class CountryFactory {
    private CountryDAO countryDAO;
    private CountyDAO countyDAO;

    public CountryFactory(CountryDAO countryDAO, CountyDAO countyDAO) {
        super();
        this.countryDAO = countryDAO;
        this.countyDAO = countyDAO;
    }

    public List<Country> getCountries() {
        return countryDAO.find(null).getResults();
    }

    public Country getCountry(int id) throws InvalidCountryException {
        Country country = countryDAO.get(id);
        if (country == null) {
            throw new InvalidCountryException(id);
        }
        return country;
    }

    public Country getCountryByName(String countryName) throws InvalidCountryException {
        CountrySearchCriteria criteria = new CountrySearchCriteria(countryName);
        List<Country> results = countryDAO.find(criteria).getResults();
        if (results.size() != 1) {
            throw new InvalidCountryException(countryName);
        }
        return results.get(0);
    }

    public County getCounty(int id) throws CountyNotExistsException {
        County county = countyDAO.get(id);
        if (county == null) {
            throw new CountyNotExistsException(id);
        }
        return county;
    }

    public List<County> getAllCounties() {
        return countyDAO.find(new CountySearchCriteria()).getResults();
    }

    public County getCountyByName(String countyName) throws InvalidCountyException {
        CountySearchCriteria criteria = new CountySearchCriteria();
        criteria.setCountyName(countyName);
        List<County> results = countyDAO.find(criteria).getResults();
        if (results.size() != 1) {
            throw new InvalidCountyException(countyName);
        }
        return results.get(0);
    }

    /**
     * returns true, if the country and the county exist in the dictionary, or if the country exists and the county may take any value
     *
     */
    public void validate(Country country, County county)
            throws InvalidCountyException {

        if (county != null && county.isSpecified()) {
            // County specified - correct if included in county list defined for the country
            validateCountyWithCountry(country, county.getId());
        } else {
            // County not specified - correct only if county list is not defined for the country
            if (!country.getCounties().isEmpty()) {
                throw new CountyRequiredException(country.getId());
            }
        }
    }

    private void validateCountyWithCountry(Country country, int countyId) throws CountyNotExistsException {
        for (County county : country.getCounties()) {
            if (county.getId() == countyId) {
                return;
            }
        }
        throw new CountyNotExistsException(countyId, country.getId());
    }

    public String getCountryVatCategory(int countryId) {
        return countryDAO.get(countryId).getVatCategory();
    }
}
