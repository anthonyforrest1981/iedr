package pl.nask.crs.reports;

import java.util.Date;

import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TotalDomainsPerDate extends TotalDomains {

    private Date date;
    private EntityClass domainClass;
    private EntityCategory domainCategory;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public EntityClass getDomainClass() {
        return domainClass;
    }

    public void setDomainClass(EntityClass domainClass) {
        this.domainClass = domainClass;
    }

    public EntityCategory getDomainCategory() {
        return domainCategory;
    }

    public void setDomainCategory(EntityCategory domainCategory) {
        this.domainCategory = domainCategory;
    }

}
