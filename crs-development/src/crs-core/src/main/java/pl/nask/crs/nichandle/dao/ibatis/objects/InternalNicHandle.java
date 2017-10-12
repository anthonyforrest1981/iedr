package pl.nask.crs.nichandle.dao.ibatis.objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.nask.crs.country.Country;
import pl.nask.crs.country.County;
import pl.nask.crs.nichandle.NicHandleStatus;

public class InternalNicHandle {

    private String nicHandleId;
    private String name;
    private Long accountNumber;
    private String accountName;
    private String accountBillingContact;
    private boolean agreementSigned;
    private boolean ticketEdit;
    private String companyName;
    private String address;
    private List<Telecom> telecoms = new ArrayList<>();
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
    private String vatCategory;
    private boolean exported;

    public InternalNicHandle() {}

    public InternalNicHandle(String nicHandleId, String name, Long accountNumber, String accountName,
            String companyName, String address, List<Telecom> telecoms, Country country, County county, String email,
            NicHandleStatus status, Date statusChangeDate, Date registrationDate, Date changeDate,
            boolean billCInd, String nicHandleRemark, String creator, String vatNo, String vatCategory, boolean exported) {
        this.nicHandleId = nicHandleId;
        this.name = name;
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.companyName = companyName;
        this.address = address;
        this.telecoms.addAll(telecoms);
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

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountBillingContact() {
        return accountBillingContact;
    }

    public void setAccountBillingContact(String accountBillingContact) {
        this.accountBillingContact = accountBillingContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Telecom> getTelecoms() {
        return telecoms;
    }

    public void setTelecoms(List<Telecom> telecoms) {
        this.telecoms.clear();
        if (telecoms != null) {
            this.telecoms.addAll(telecoms);
        }
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public NicHandleStatus getStatus() {
        return status;
    }

    public void setStatus(NicHandleStatus status) {
        this.status = status;
    }

    public Date getStatusChangeDate() {
        return statusChangeDate;
    }

    public void setStatusChangeDate(Date statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public boolean isBillCInd() {
        return billCInd;
    }

    public void setBillCInd(boolean billCInd) {
        this.billCInd = billCInd;
    }

    public String getNicHandleRemark() {
        return nicHandleRemark;
    }

    public void setNicHandleRemark(String nicHandleRemark) {
        this.nicHandleRemark = nicHandleRemark;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getVatNo() {
        return vatNo;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public boolean vatNoNotEmpty() {
        return (vatNo != null && !vatNo.trim().equals(""));
    }

    public boolean isAgreementSigned() {
        return agreementSigned;
    }

    public void setAgreementSigned(boolean agreementSigned) {
        this.agreementSigned = agreementSigned;
    }

    public boolean isTicketEdit() {
        return ticketEdit;
    }

    public void setTicketEdit(boolean ticketEdit) {
        this.ticketEdit = ticketEdit;
    }

    public String getVatCategory() {
        return vatCategory;
    }

    public void setVatCategory(String vatCategory) {
        this.vatCategory = vatCategory;
    }

    public boolean isExported() {
        return exported;
    }

    public void setExported(boolean exported) {
        this.exported = exported;
    }
}
