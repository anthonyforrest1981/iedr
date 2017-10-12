package pl.nask.crs.domains;

import pl.nask.crs.country.Country;
import pl.nask.crs.country.County;

public class DeletedDomain extends PlainDomain {

    private String billingName;
    private Country country;
    private County county;

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public String getBillingName() {
        return billingName;
    }

    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    @Override
    public String toString() {
        return String.format("DeletedDomain[name=%s]", name);
    }

}
