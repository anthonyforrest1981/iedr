package pl.nask.crs.price;

import java.math.BigDecimal;
import java.util.Date;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class DomainPrice {

    private Integer id;
    private String code;
    private String description;
    private BigDecimal price;
    private int duration;
    private boolean defaultPrice;
    private Date validFrom;
    private Date validTo;
    private boolean forRegistration;
    private boolean forRenewal;
    private boolean direct;

    private DomainPrice(Integer id, String code, String description, BigDecimal price, int duration, boolean defaultPrice,
            Date validFrom, Date validTo, boolean forRegistration, boolean forRenewal, boolean direct) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.defaultPrice = defaultPrice;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.forRegistration = forRegistration;
        this.forRenewal = forRenewal;
        this.direct = direct;
    }

    public DomainPrice() {}

    public static DomainPrice newInstance(String code, String description, BigDecimal price, int duration,
            Date validFrom, Date validTo, boolean forRegistration, boolean forRenewal, boolean direct) {
        return new DomainPrice(-1, code, description, price, duration, false, validFrom, validTo, forRegistration,
                forRenewal, direct);
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(boolean defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public boolean isForRegistration() {
        return forRegistration;
    }

    public void setForRegistration(boolean forRegistration) {
        this.forRegistration = forRegistration;
    }

    public boolean isForRenewal() {
        return forRenewal;
    }

    public void setForRenewal(boolean forRenewal) {
        this.forRenewal = forRenewal;
    }

    public boolean isDirect() {
        return direct;
    }

    public void setDirect(boolean direct) {
        this.direct = direct;
    }

    @Override
    public String toString() {
        return String.format(
                "DomainPrice[id: %s, price: %s, duration: %s, forRegistration: %s, forRenewal: %s, direct: %s]", id,
                price, duration, forRegistration, forRenewal, direct);
    }

}
