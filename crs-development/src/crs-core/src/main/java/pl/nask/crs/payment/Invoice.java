package pl.nask.crs.payment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import pl.nask.crs.commons.config.NameFormatter;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.County;

public class Invoice extends PlainInvoice {
    private final List<Transaction> transactions;

    public static Invoice newInstance(int invoiceNumber, String accountName, long accountNumber, String address1,
            String address2, String address3, String billingNicHandle, Country country, County county,
            String crsVersion, Date invoiceDate, String MD5, BigDecimal totalCost, BigDecimal totalNetAmount,
            BigDecimal totalVatAmount) {
        return new Invoice(-1, format(invoiceNumber), accountName, accountNumber, address1, address2, address3,
                billingNicHandle, null, country, county, crsVersion, invoiceDate, MD5, false,
                Collections.<Transaction>emptyList(), totalCost, totalNetAmount, totalVatAmount, null, null);
    }

    private static String format(int invoiceNumber) {
        return NameFormatter.getFormattedName(invoiceNumber, NameFormatter.NamePrefix.INV);
    }

    public Invoice(int id, String invoiceNumber, String accountName, long accountNumber, String address1,
            String address2, String address3, String billingNicHandle, String billingNicHandleName, Country country,
            County county, String crsVersion, Date invoiceDate, String MD5, boolean completed,
            List<Transaction> transactions, BigDecimal totalCost, BigDecimal totalNetAmount, BigDecimal totalVatAmount,
            Date settlementDate, PaymentMethod paymentMethod) {
        super(id, invoiceNumber, accountName, accountNumber, address1, address2, address3, billingNicHandle,
                billingNicHandleName, country, county, crsVersion, invoiceDate, MD5, completed, totalCost,
                totalNetAmount, totalVatAmount, settlementDate, paymentMethod);
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Map<BigDecimal, BigDecimal> getTotalVatMap() {
        Map<BigDecimal, BigDecimal> vatRateToTotalMap = new HashMap<BigDecimal, BigDecimal>();
        for (Transaction transaction : transactions) {
            BigDecimal vatRate = getVatRateInPercents(transaction);
            BigDecimal vatIdTotal = vatRateToTotalMap.get(vatRate);
            if (vatIdTotal == null) {
                vatIdTotal = transaction.getTotalVatAmount();
                vatRateToTotalMap.put(vatRate, vatIdTotal);
            } else {
                vatIdTotal = vatIdTotal.add(transaction.getTotalVatAmount());
                vatRateToTotalMap.put(vatRate, vatIdTotal);
            }
        }
        return vatRateToTotalMap;
    }

    private BigDecimal getVatRateInPercents(Transaction transaction) {
        BigDecimal vatRate = transaction.getReservations().get(0).getVatRate();
        BigDecimal inPercents = vatRate.movePointRight(2);
        return inPercents.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public String toString() {
        return String.format("Invoice[id=%s, invoiceNumber=%s, billingNH=%s, completed=%s]", id, invoiceNumber,
                billingNicHandle, completed);
    }
}
