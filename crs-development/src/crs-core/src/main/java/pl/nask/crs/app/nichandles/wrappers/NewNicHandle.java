package pl.nask.crs.app.nichandles.wrappers;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.List;

import pl.nask.crs.commons.utils.Validator;

public class NewNicHandle {

    private String name;
    private String companyName;
    private String email;
    private String address;
    private int countryId;
    private int countyId;
    private List<String> phones;
    private List<String> faxes;
    private String vatNo;
    private String vatCategory;


    public NewNicHandle(String name, /*>>>@Nullable*/ String companyName, String email, String address, int countryId,
            int countyId, /*>>>@Nullable*/ List<String> phones, /*>>>@Nullable*/ List<String> faxes,
            /*>>>@Nullable*/ String vatNo, /*>>>@Nullable*/ String vatCategory) {
        this.name = name;
        if (!Validator.isEmpty(companyName)) {
            this.companyName = companyName;
        }
        this.email = email;
        this.address = address;
        this.countryId = countryId;
        this.countyId = countyId;
        this.phones = phones;
        this.faxes = faxes;
        this.vatNo = vatNo;
        this.vatCategory = vatCategory;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getPhones() {
        return phones;
    }

    public List<String> getFaxes() {
        return faxes;
    }

    public String getAddress() {
        return address;
    }

    public int getCountyId() {
        return countyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getVatNo() {
        return vatNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompanyName(String companyName) {
        this.companyName = Validator.isEmpty(companyName) ? null : companyName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public void setFaxes(List<String> faxes) {
        this.faxes = faxes;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public String getVatCategory() {
        return vatCategory;
    }

    public void setVatCategory(String vatCategory) {
        this.vatCategory = vatCategory;
    }

}
