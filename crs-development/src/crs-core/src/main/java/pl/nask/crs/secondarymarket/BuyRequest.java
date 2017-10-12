package pl.nask.crs.secondarymarket;

import java.util.*;

import pl.nask.crs.accounts.PlainAccount;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.County;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.nichandle.dao.ibatis.objects.TelecomType;
import pl.nask.crs.ticket.operation.FailureReason;

public class BuyRequest extends PlainBuyRequest {

    private PlainAccount account;
    private EntityClass holderClass;
    private EntityCategory holderCategory;
    private Country adminCountry;
    private County adminCounty;
    private List<String> phones = new ArrayList<>();
    private List<String> faxes = new ArrayList<>();
    private BuyRequestStatus status;
    private Date changeDate;

    private FailureReason domainNameFR;
    private FailureReason domainHolderFR;
    private FailureReason holderClassFR;
    private FailureReason holderCategoryFR;
    private FailureReason adminNameFR;
    private FailureReason adminEmailFR;
    private FailureReason adminCompanyNameFR;
    private FailureReason adminAddressFR;
    private FailureReason adminCountryFR;
    private FailureReason adminCountyFR;
    private FailureReason phonesFR;
    private FailureReason faxesFR;

    /**
     * Constructor for Ibatis, should not be explicitly used in production code.
     */
    public BuyRequest() {
    }

    /**
     * Creates new BuyRequest object. Not only it sets all passed values (self-explanatory) but also automatically
     * sets status.
     * @param domainName
     * @param creatorNH
     * @param domainHolder
     * @param holderClass
     * @param holderCategory
     * @param remark
     * @param adminName
     * @param adminEmail
     * @param adminCompanyName
     * @param adminAddress
     * @param adminCountry
     * @param adminCounty
     */
    public BuyRequest(String domainName, String creatorNH, PlainAccount account, String domainHolder,
            EntityClass holderClass, EntityCategory holderCategory, String remark, String adminName, String adminEmail,
            String adminCompanyName, String adminAddress, List<String> phones, List<String> faxes, Country adminCountry,
            County adminCounty, Date creationDate) {
        super(domainName, creatorNH, domainHolder, remark, adminName, adminEmail, adminCompanyName, adminAddress, creationDate);
        this.account = account;
        this.holderClass = holderClass;
        this.holderCategory = holderCategory;
        if (phones != null) this.phones.addAll(phones);
        if (faxes != null) this.faxes.addAll(faxes);
        this.adminCountry = adminCountry;
        this.adminCounty = adminCounty;
        this.status = BuyRequestStatus.NEW;
    }

    public PlainAccount getAccount() {
        return account;
    }

    public void setAccount(PlainAccount account) {
        this.account = account;
    }

    public EntityClass getHolderClass() {
        return holderClass;
    }

    public void setHolderClass(EntityClass holderClass) {
        this.holderClass = holderClass;
    }

    public EntityCategory getHolderCategory() {
        return holderCategory;
    }

    public void setHolderCategory(EntityCategory holderCategory) {
        this.holderCategory = holderCategory;
    }

    public Country getAdminCountry() {
        return adminCountry;
    }

    public void setAdminCountry(Country adminCountry) {
        this.adminCountry = adminCountry;
    }

    public County getAdminCounty() {
        return adminCounty;
    }

    public void setAdminCounty(County adminCounty) {
        this.adminCounty = adminCounty;
    }

    public void setTelecoms(Collection<BuyRequestTelecom> telecoms) {
        this.phones = new LinkedList<>();
        this.faxes = new LinkedList<>();
        if (telecoms != null) {
            SortedSet<BuyRequestTelecom> sortedTelecoms = new TreeSet<BuyRequestTelecom>(new Comparator<BuyRequestTelecom>() {
                @Override
                public int compare(BuyRequestTelecom o1, BuyRequestTelecom o2) {
                    if (o1.getType() == o2.getType()) {
                        return Integer.compare(o1.getOrder(), o2.getOrder());
                    } else {
                        return o1.getType() == TelecomType.PHONE ? -1 : 1;
                    }
                }
            });
            sortedTelecoms.addAll(telecoms);
            for (BuyRequestTelecom telecom : sortedTelecoms) {
                List<String> destList = telecom.getType() == TelecomType.FAX ? faxes : phones;
                destList.add(telecom.getValue());
            }
        }
    }

    public List<BuyRequestTelecom> getTelecoms() {
        List<BuyRequestTelecom> result = new ArrayList<>(faxes.size() + phones.size());
        int order = 0;
        for (String fax : faxes) {
            result.add(new BuyRequestTelecom(TelecomType.FAX, fax, order++));
        }
        order = 0;
        for (String phone : phones) {
            result.add(new BuyRequestTelecom(TelecomType.PHONE, phone, order++));
        }
        return result;
    }

    public List<String> getPhones() {
        return new ArrayList<>(phones);
    }

    public void setPhones(List<String> phones) {
        this.phones = new ArrayList<>(phones);
    }

    public List<String> getFaxes() {
        return new ArrayList<>(faxes);
    }

    public void setFaxes(List<String> faxes) {
        if (faxes != null) {
            this.faxes = new ArrayList<>(faxes);
        }
    }

    public BuyRequestStatus getStatus() {
        return status;
    }

    public void setStatus(BuyRequestStatus status) {
        this.status = status;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public FailureReason getHolderClassFR() {
        return holderClassFR;
    }

    public void setHolderClassFR(FailureReason holderClassFR) {
        this.holderClassFR = holderClassFR;
    }

    public FailureReason getHolderCategoryFR() {
        return holderCategoryFR;
    }

    public void setHolderCategoryFR(FailureReason holderCategoryFR) {
        this.holderCategoryFR = holderCategoryFR;
    }

    public FailureReason getPhonesFR() {
        return phonesFR;
    }

    public void setPhonesFR(FailureReason phonesFR) {
        this.phonesFR = phonesFR;
    }

    public FailureReason getFaxesFR() {
        return faxesFR;
    }

    public void setFaxesFR(FailureReason faxesFR) {
        this.faxesFR = faxesFR;
    }

    public FailureReason getAdminCountryFR() {
        return adminCountryFR;
    }

    public void setAdminCountryFR(FailureReason adminCountryFR) {
        this.adminCountryFR = adminCountryFR;
    }

    public FailureReason getAdminCountyFR() {
        return adminCountyFR;
    }

    public void setAdminCountyFR(FailureReason adminCountyFR) {
        this.adminCountyFR = adminCountyFR;
    }

    public FailureReason getDomainNameFR() {
        return domainNameFR;
    }

    public void setDomainNameFR(FailureReason domainNameFR) {
        this.domainNameFR = domainNameFR;
    }

    public FailureReason getDomainHolderFR() {
        return domainHolderFR;
    }

    public void setDomainHolderFR(FailureReason domainHolderFR) {
        this.domainHolderFR = domainHolderFR;
    }

    public FailureReason getAdminNameFR() {
        return adminNameFR;
    }

    public void setAdminNameFR(FailureReason adminNameFR) {
        this.adminNameFR = adminNameFR;
    }

    public FailureReason getAdminEmailFR() {
        return adminEmailFR;
    }

    public void setAdminEmailFR(FailureReason adminEmailFR) {
        this.adminEmailFR = adminEmailFR;
    }

    public FailureReason getAdminCompanyNameFR() {
        return adminCompanyNameFR;
    }

    public void setAdminCompanyNameFR(FailureReason adminCompanyNameFR) {
        this.adminCompanyNameFR = adminCompanyNameFR;
    }

    public FailureReason getAdminAddressFR() {
        return adminAddressFR;
    }

    public void setAdminAddressFR(FailureReason adminAddressFR) {
        this.adminAddressFR = adminAddressFR;
    }

}
