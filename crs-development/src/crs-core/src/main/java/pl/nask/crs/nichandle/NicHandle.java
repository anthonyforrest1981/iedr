package pl.nask.crs.nichandle;

import java.util.Date;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.utils.CollectionUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.County;

/**
 * @author Marianna Mysiorska
 */

public class NicHandle {

    private String nicHandleId;
    private String name;
    private Account account;
    private String companyName;
    private String address;
    private List<String> phones;
    private List<String> faxes;
    private Country country;
    private County county;
    private String email;
    private NicHandleStatus status;
    private Date statusChangeDate;
    private Date registrationDate;
    private Date changeDate;
    private boolean billCInd;
    private String nicHandleRemark;
    private String creator;
    private String vatNo;
    protected String vatCategory;
    private boolean exported;

    public NicHandle(String nicHandleId, String name, Account account, String companyName, String address,
            List<String> phones, List<String> faxes, Country country, County county, String email,
            NicHandleStatus status, Date statusChangeDate, Date registrationDate, Date changeDate,
            boolean billCInd, String nicHandleRemark, String creator, String vatNo, String vatCategory,
            boolean exported) {
        Validator.assertNotEmpty(nicHandleId, "nic handle id");
        Validator.assertNotNull(name, "name");
        Validator.assertNotNull(account, "account");
        Validator.assertNotNull(address, "address");
        Validator.assertNotNull(email, "email");
        Validator.assertNotNull(status, "status");
        Validator.assertNotNull(statusChangeDate, "status change date");
        Validator.assertNotNull(changeDate, "change date");
        this.nicHandleId = nicHandleId;
        this.name = name;
        this.account = account;
        this.companyName = companyName;
        this.address = address;
        this.phones = phones;
        this.faxes = faxes;
        this.country = country;
        this.county = county;
        this.email = email;
        this.status = status;
        this.statusChangeDate = statusChangeDate;
        this.registrationDate = registrationDate;
        this.changeDate = changeDate;
        this.billCInd = billCInd;
        this.nicHandleRemark = nicHandleRemark;
        this.creator = creator;
        this.vatNo = vatNo;
        this.vatCategory = vatCategory;
        this.exported = exported;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public String getName() {
        return name;
    }

    public Account getAccount() {
        return account;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getPhones() {
        return phones;
    }

    public List<String> getFaxes() {
        return faxes;
    }

    public Country getCountry() {
        return country;
    }

    public County getCounty() {
        return county;
    }

    public String getEmail() {
        return email;
    }

    public NicHandleStatus getStatus() {
        return status;
    }

    public Date getStatusChangeDate() {
        return statusChangeDate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public boolean isBillCInd() {
        return billCInd;
    }

    public String getNicHandleRemark() {
        return nicHandleRemark;
    }

    public String getCreator() {
        return creator;
    }

    public String getVatNo() {
        return vatNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public void setFaxes(List<String> faxes) {
        this.faxes = faxes;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean setStatus(NicHandleStatus status) throws IllegalArgumentException {
        Validator.assertNotNull(status, "nic handle status");
        if (this.status != status) {
            this.status = status;
            this.statusChangeDate = new Date();
            return true;
        }
        return false;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public void setChangeDate(Date date) {
        this.changeDate = date;
    }

    public void updateChangeDate() {
        setChangeDate(new Date());
    }

    public void setNicHandleRemark(String nicHandleRemark) {
        this.nicHandleRemark = nicHandleRemark;
    }

    public void addActorNameToRemark(String hostmasterHandle) {
        nicHandleRemark += " by " + hostmasterHandle + " on " + new Date();
    }

    public String getVatCategory() {
        return vatCategory;
    }

    public void setVatCategory(String vatCategory) {
        this.vatCategory = vatCategory;
    }

    @Override
    public String toString() {
        return nicHandleId;
    }

    public String getPhonesAsString() {
        return listAsString(getPhones());
    }

    private String listAsString(List<String> list) {
        return CollectionUtils.toString(list, true, ", ");
    }

    public String getFaxesAsString() {
        return listAsString(getFaxes());
    }

    public boolean isExported() {
        return exported;
    }

    public void setExported(boolean exported) {
        this.exported = exported;
    }
}
