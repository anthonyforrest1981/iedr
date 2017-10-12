package pl.nask.crs.api.vo.search;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class NicHandleSearchCriteriaVO {

    private String nicHandleId;
    private String name;
    private String companyName;
    private String email;
    private Long accountNumber;
    private String address;
    private String countyName;
    private String countryName;
    private String phone;

    private String creator;

    public NicHandleSearchCriteria toSearchCriteria() {
        NicHandleSearchCriteria criteria = new NicHandleSearchCriteria();
        criteria.setNicHandleId(nicHandleId);
        criteria.setName(name);
        criteria.setCompanyName(companyName);
        criteria.setEmail(email);
        criteria.setAccountNumber(accountNumber);
        criteria.setAddress(address);
        criteria.setCountyName(countyName);
        criteria.setCountryName(countryName);
        criteria.setPhone(phone);
        criteria.setCreator(creator);
        return criteria;
    }

}
