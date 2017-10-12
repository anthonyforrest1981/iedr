package pl.nask.crs.domains;

import java.util.List;

import pl.nask.crs.commons.Period;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.EntitySubcategory;

public class NewDomain {

    private final String name;
    private final String holder;
    private EntityClass holderClass;
    private EntityCategory holderCategory;
    private EntitySubcategory holderSubcategory;
    private final String creatorNicHandle;
    private final long resellerAccountId;
    private final String remark;
    private final List<String> techContacts;
    private final List<String> billContacts;
    private final List<String> adminContacts;
    private final List<Nameserver> nameservers;
    private final Period period;

    public NewDomain(String name, String holder, EntityClass holderClass, EntityCategory holderCategory,
            EntitySubcategory holderSubcategory, String creatorNicHandle, long resellerAccountId, String remark,
            List<String> techContacts, List<String> billContacts, List<String> adminContacts,
            List<Nameserver> nameservers, Period period) {
        this.name = name;
        this.holder = holder;
        this.holderClass = holderClass;
        this.holderCategory = holderCategory;
        this.holderSubcategory = holderSubcategory;
        this.creatorNicHandle = creatorNicHandle;
        this.resellerAccountId = resellerAccountId;
        this.remark = remark;
        this.techContacts = techContacts;
        this.billContacts = billContacts;
        this.adminContacts = adminContacts;
        this.nameservers = nameservers;
        this.period = period;
    }

    public List<String> getAdminContacts() {
        return adminContacts;
    }

    public List<String> getTechContacts() {
        return techContacts;
    }

    public List<String> getBillingContacts() {
        return billContacts;
    }

    public String getName() {
        return name;
    }

    public EntityClass getHolderClass() {
        return holderClass;
    }

    public EntityCategory getHolderCategory() {
        return holderCategory;
    }

    public EntitySubcategory getHolderSubcategory() {
        return holderSubcategory;
    }

    public void setHolderClass(EntityClass holderClass) {
        this.holderClass = holderClass;
    }

    public void setHolderCategory(EntityCategory holderCategory) {
        this.holderCategory = holderCategory;
    }

    public String getCreator() {
        return creatorNicHandle;
    }

    public String getHolder() {
        return holder;
    }

    public List<Nameserver> getNameservers() {
        return nameservers;
    }

    public String getRemark() {
        return remark;
    }

    public long getResellerAccountId() {
        return resellerAccountId;
    }

    public Period getPeriod() {
        return period;
    }
}
