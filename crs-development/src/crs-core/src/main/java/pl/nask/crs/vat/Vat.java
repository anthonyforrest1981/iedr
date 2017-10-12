package pl.nask.crs.vat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class Vat {

    private int id;
    private String category;
    private Date fromDate;
    private BigDecimal vatRate;

    public Vat() {}

    public Vat(int id, String category, Date fromDate, BigDecimal vatRate) {
        this.id = id;
        this.category = category;
        this.fromDate = fromDate;
        this.vatRate = vatRate;
    }

    public static Vat newInstance(String category, Date fromDate, BigDecimal vatRate) {
        return new Vat(-1, category, fromDate, vatRate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Vat))
            return false;
        Vat vat = (Vat) o;
        if (id != vat.getId())
            return false;
        if (!category.toLowerCase().equals(vat.getCategory().toLowerCase()))
            return false;
        if (fromDate.compareTo(vat.getFromDate()) != 0)
            return false;
        if (vatRate.compareTo(vat.getVatRate()) != 0)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + id;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (fromDate != null ? fromDate.hashCode() : 0);
        result = 31 * result + (vatRate != null ? vatRate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("Vat[id: %s, category: %s, fromDate: %s, vatRate: %s]", id, category, fromDate, vatRate);
    }
}
