package pl.nask.crs.country;

import java.util.List;

public class Country {

    private String name;
    private int id;
    private String vatCategory;
    private List<County> counties;

    public Country() {}

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getVatCategory() {
        return vatCategory;
    }

    public void setVatCategory(String vatCategory) {
        this.vatCategory = vatCategory;
    }

    public List<County> getCounties() {
        return counties;
    }

    public void setCounties(List<County> counties) {
        this.counties = counties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Country)) return false;

        Country country = (Country) o;

        if (getId() != country.getId()) return false;
        if (getName() != null ? !getName().equals(country.getName()) : country.getName() != null) return false;
        return getVatCategory() != null ?
                getVatCategory().equals(country.getVatCategory()) :
                country.getVatCategory() == null;

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + getId();
        result = 31 * result + (getVatCategory() != null ? getVatCategory().hashCode() : 0);
        return result;
    }
}
