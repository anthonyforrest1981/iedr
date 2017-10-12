package pl.nask.crs.api.vo;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.County;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class NicHandleEditVO {
    @XmlElement(required = true)
    private String name;
    @XmlElement(nillable = true)
    private String companyName;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private Integer countryId;
    @XmlElement(required = true)
    private Integer countyId;
    @XmlElement(required = true)
    private Long accountNumber;
    @XmlElement(required = true)
    private List<String> phones;
    // optional
    @XmlElement
    private List<String> faxes;
    // optional
    private String vatNo;

    public NicHandleEditVO() {}

    public NicHandleEditVO(NicHandleVO vo) {
        name = vo.getName();
        companyName = vo.getCompanyName();
        email = vo.getEmail();
        address = vo.getAddress();
        countryId = vo.getCountry().getId();
        countyId = vo.getCounty().getId();
        accountNumber = vo.getAccountId();
        phones = vo.getPhones();
        faxes = vo.getFaxes();
        vatNo = vo.getVatNo();
    }

    public NewNicHandle toNewNicHandle() {
        if (phones != null) {
            phones.remove(null);
            phones = new ArrayList<>(new LinkedHashSet<>(phones));
        }
        if (faxes != null) {
            faxes.remove(null);
            faxes = new ArrayList<>(new LinkedHashSet<>(faxes));
        }
        Validator.assertNotNull(countryId, "country");
        return new NewNicHandle(name, companyName, email, address, countryId,
                countyId == null ? County.NOT_SPECIFIED : countyId, phones, faxes, null, null);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public void setFaxes(List<String> faxes) {
        this.faxes = faxes;
    }

    public String getName() {
        return name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public List<String> getPhones() {
        return phones;
    }

    public List<String> getFaxes() {
        return faxes;
    }

    public String getVatNo() {
        return vatNo;
    }
}
