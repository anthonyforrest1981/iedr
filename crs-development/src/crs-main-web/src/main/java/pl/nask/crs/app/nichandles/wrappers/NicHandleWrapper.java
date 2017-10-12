package pl.nask.crs.app.nichandles.wrappers;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.NicHandleStatus;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.User;
import pl.nask.crs.user.service.AuthorizationGroupsFactory;

public class NicHandleWrapper {
    private String nichandleId;

    private String name;
    private String companyName;
    private Long accountNumber;
    private String email;
    private String address;
    private Integer countryId;
    private Integer countyId;
    private String vatNo;
    private String vatCategory;
    private String hostmastersRemark;
    private Date changeDate;
    private NicHandleStatus status;
    private List<String> phones = new ArrayList<>();
    private List<String> faxes = new ArrayList<>();

    private PhoneWrapper phonesWrapper;
    private PhoneWrapper faxesWrapper;
    private PermissionGroupsWrapper permissionGroupsWrapper;
    private User user;

    public NicHandleWrapper() {
        phonesWrapper = new PhoneWrapper(phones);
        faxesWrapper = new PhoneWrapper(faxes);
    }

    public NicHandleWrapper(NicHandle nicHandle) {
        this.nichandleId = nicHandle.getNicHandleId();
        this.name = nicHandle.getName();
        this.companyName = nicHandle.getCompanyName();
        this.accountNumber = nicHandle.getAccount().getId();
        this.email = nicHandle.getEmail();
        this.address = nicHandle.getAddress();
        this.countyId = nicHandle.getCounty().getId();
        this.countryId = nicHandle.getCountry().getId();
        this.vatNo = nicHandle.getVatNo();
        this.setVatCategory(nicHandle.getVatCategory());
        this.setChangeDate(nicHandle.getChangeDate());
        this.setStatus(nicHandle.getStatus());
        this.phones = nicHandle.getPhones() != null ? nicHandle.getPhones() : new ArrayList<String>();
        this.faxes = nicHandle.getFaxes() != null ? nicHandle.getFaxes() : new ArrayList<String>();
        this.phonesWrapper = new PhoneWrapper(phones);
        this.faxesWrapper = new PhoneWrapper(faxes);
    }

    public String getNicHandleId() {
        return nichandleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public String getVatNo() {
        return vatNo;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = Validator.isEmpty(vatNo) ? null : vatNo;
    }

    public String getVatCategory() {
        return vatCategory;
    }

    public void setVatCategory(String vatCategory) {
        this.vatCategory = Validator.isEmpty(vatCategory) ? null : vatCategory;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public NicHandleStatus getStatus() {
        return status;
    }

    public void setStatus(NicHandleStatus status) {
        this.status = status;
    }

    public String getHostmastersRemark() {
        return hostmastersRemark;
    }

    public void setHostmastersRemark(String hostmastersRemark) {
        this.hostmastersRemark = hostmastersRemark;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public List<String> getFaxes() {
        return faxes;
    }

    public void setFaxes(List<String> faxes) {
        this.faxes = faxes;
    }

    public void createPermissionGroupsWrapper(AuthorizationGroupsFactory authorizationGroupsFactory) {
        this.permissionGroupsWrapper = new PermissionGroupsWrapper(authorizationGroupsFactory);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PhoneWrapper getPhonesWrapper() {
        return phonesWrapper;
    }

    public PhoneWrapper getFaxesWrapper() {
        return faxesWrapper;
    }

    public PermissionGroupsWrapper getPermissionGroupsWrapper() {
        return permissionGroupsWrapper;
    }

    public void setPermissionGroupsWrapper(PermissionGroupsWrapper permissionGroupsWrapper) {
        this.permissionGroupsWrapper = permissionGroupsWrapper;
    }

    public void setGroupsFromUser() {
        if (user != null) {
            for (Group g : user.getPermissionGroups()) {
                permissionGroupsWrapper.setGroup(g, true);
            }
        }
    }

    public void setGroupsToUser() {
        if (user == null) {
            user = new User();
            user.setUsername(nichandleId);
        }
        user.setPermissionGroups(permissionGroupsWrapper.getPermissionGroups());
    }

    public Set<String> getUserPermissions() {
        return user.getPermissions().keySet();
    }

    public boolean hasUserPermission(String name) {
        return user.getPermissions().containsKey(name);
    }

    public NewNicHandle makeNewNicHandle() {
        return new NewNicHandle(name, companyName, email, address, countryId, countyId, phones, faxes, vatNo, vatCategory);
    }
}
