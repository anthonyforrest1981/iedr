package pl.nask.crs.contacts.dao.ibatis.objects;

import pl.nask.crs.contacts.ContactType;

public class InternalContact {

    private String nicHandle;

    private String name;

    private ContactType type;

    private String email;

    private String domainName;

    private String companyName;

    private String country;

    private String county;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(String nicHandle) {
        this.nicHandle = nicHandle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isAdmin() {
        return type == ContactType.ADMIN;
    }

    public boolean isTech() {
        return type == ContactType.TECH;
    }

    public boolean isBilling() {
        return type == ContactType.BILLING;
    }

    public boolean isEmpty() {
        return nicHandle == null;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }
}
