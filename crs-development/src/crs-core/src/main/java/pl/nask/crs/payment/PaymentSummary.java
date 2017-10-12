package pl.nask.crs.payment;

import java.math.BigDecimal;
import java.util.*;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.vat.ProductPriceWithVat;

public class PaymentSummary {
    private final Payment totalPayment;
    private final String orderId;
    private final List<PaymentDomain> paymentDomains;

    private final Comparator<PaymentDomain> paymentDomainComparator = new PaymentDomainComparator();

    public PaymentSummary(Map<String, ProductPriceWithVat> pricePerDomain, Payment totalPayment, String orderId,
            Map<String, Domain> domainMap) {
        this.totalPayment = totalPayment;
        this.orderId = orderId;
        this.paymentDomains = preparePaymentDomains(pricePerDomain, domainMap);
    }

    public PaymentSummary(String domainName, Date registrationDate, Date renewalDate, int periodInYears,
            BigDecimal totalFee, BigDecimal totalVat, BigDecimal total, String orderId) {
        this.totalPayment = new Payment(totalFee, totalVat, total);
        this.orderId = orderId;
        this.paymentDomains = Arrays.asList(new PaymentDomain(domainName, registrationDate, renewalDate, periodInYears,
                totalFee, totalVat, total));
    }

    private List<PaymentDomain> preparePaymentDomains(Map<String, ProductPriceWithVat> pricePerDomain,
            Map<String, Domain> domainMap) {
        List<PaymentDomain> ret = new ArrayList<>(pricePerDomain.size());
        for (Map.Entry<String, ProductPriceWithVat> entry : pricePerDomain.entrySet()) {
            String domainName = entry.getKey();
            Domain domain = domainMap.get(domainName);
            ProductPriceWithVat productPriceWithVat = entry.getValue();
            ret.add(new PaymentDomain(domain.getName(), domain.getRegistrationDate(), domain.getRenewalDate(),
                    productPriceWithVat.getPeriod().getYears(), productPriceWithVat.getNetAmount(), productPriceWithVat.getVatAmount(),
                    productPriceWithVat.getTotal()));
        }
        Collections.sort(ret, paymentDomainComparator);

        return ret;
    }

    public BigDecimal getFee() {
        return totalPayment.getFee();
    }

    public BigDecimal getVat() {
        return totalPayment.getVat();
    }

    public BigDecimal getTotal() {
        return totalPayment.getTotal();
    }

    public String getOrderId() {
        return orderId;
    }

    public List<PaymentDomain> getPaymentDomains() {
        return paymentDomains;
    }

    class PaymentDomainComparator implements Comparator<PaymentDomain> {
        @Override
        public int compare(PaymentDomain o1, PaymentDomain o2) {
            return o1.getDomainName().compareToIgnoreCase(o2.getDomainName());
        }
    }

}
