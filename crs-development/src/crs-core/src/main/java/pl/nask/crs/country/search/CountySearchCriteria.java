package pl.nask.crs.country.search;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.country.County;

public class CountySearchCriteria implements SearchCriteria<County> {
    private Integer countryId;
    private Integer countyId;
    private String countyName;

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

}
