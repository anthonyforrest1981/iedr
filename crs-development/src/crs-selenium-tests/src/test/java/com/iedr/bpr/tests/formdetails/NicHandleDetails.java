package com.iedr.bpr.tests.formdetails;

public class NicHandleDetails {

    private String nhName;
    private String email;
    private String phone;
    private String fax;
    private String company;
    private String address;
    private String country;
    private String county;

    private boolean nameEditable;

    public NicHandleDetails(String nhName, String email, String phone, String fax, String company, String address,
            String country, final String county) {
        this.setNhName(nhName);
        this.setEmail(email);
        this.setPhone(phone);
        this.setFax(fax);
        this.setCompany(company);
        this.setAddress(address);
        this.setCountry(country);
        this.setCounty(county);
        this.setNameEditable(true);
    }

    public NicHandleDetails(String nhName, String email) {
        this(nhName, email, "123456789", "987654321", "iedr", "1 The Road, Some Street", "Northern Ireland",
                "Co. Tyrone");
    }

    public String getNhName() {
        return nhName;
    }

    public void setNhName(String nhName) {
        this.nhName = nhName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public boolean isNameEditable() {
        return nameEditable;
    }

    public void setNameEditable(boolean nameEditable) {
        this.nameEditable = nameEditable;
    }

}
