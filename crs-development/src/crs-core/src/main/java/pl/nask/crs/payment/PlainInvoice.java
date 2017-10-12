package pl.nask.crs.payment;

import java.math.BigDecimal;
import java.util.*;

import pl.nask.crs.commons.config.NameFormatter;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.County;

public class PlainInvoice {
    protected int id;
    protected String invoiceNumber;
    protected String accountName;
    protected long accountNumber;
    protected String address1;
    protected String address2;
    protected String address3;
    protected String billingNicHandle;
    protected String billingNicHandleName;
    protected County county;
    protected Country country;
    protected String crsVersion;
    protected Date invoiceDate;
    protected String MD5;
    protected boolean completed;
    protected BigDecimal totalCost;
    protected BigDecimal totalNetAmount;
    protected BigDecimal totalVatAmount;
    protected Date settlementDate;
    protected PaymentMethod paymentMethod;

    public PlainInvoice() {}

    public PlainInvoice(int id, String invoiceNumber, String accountName, long accountNumber, String address1,
            String address2, String address3, String billingNicHandle, String billingNicHandleName, Country country,
            County county, String crsVersion, Date invoiceDate, String MD5, boolean completed,
            BigDecimal totalCost, BigDecimal totalNetAmount, BigDecimal totalVatAmount,
            Date settlementDate, PaymentMethod paymentMethod) {
        // generated by db
        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.billingNicHandle = billingNicHandle;
        this.billingNicHandleName = billingNicHandleName;
        this.country = country;
        this.county = county;
        this.crsVersion = crsVersion;
        this.invoiceDate = invoiceDate;
        this.MD5 = MD5;
        this.completed = completed;
        this.totalCost = totalCost;
        this.totalNetAmount = totalNetAmount;
        this.totalVatAmount = totalVatAmount;
        this.settlementDate = settlementDate;
        this.paymentMethod = paymentMethod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getBillingNicHandle() {
        return billingNicHandle;
    }

    public void setBillingNicHandle(String billingNicHandle) {
        this.billingNicHandle = billingNicHandle;
    }

    public String getBillingNicHandleName() {
        return billingNicHandleName;
    }

    public void setBillingNicHandleName(String billingNicHandleName) {
        this.billingNicHandleName = billingNicHandleName;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getCrsVersion() {
        return crsVersion;
    }

    public void setCrsVersion(String crsVersion) {
        this.crsVersion = crsVersion;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getTotalNetAmount() {
        return totalNetAmount;
    }

    public void setTotalNetAmount(BigDecimal totalNetAmount) {
        this.totalNetAmount = totalNetAmount;
    }

    public BigDecimal getTotalVatAmount() {
        return totalVatAmount;
    }

    public void setTotalVatAmount(BigDecimal totalVatAmount) {
        this.totalVatAmount = totalVatAmount;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return String.format("PlainInvoice[id=%s, invoiceNumber=%s, billingNH=%s, completed=%s]", id, invoiceNumber,
                billingNicHandle, completed);
    }
}
