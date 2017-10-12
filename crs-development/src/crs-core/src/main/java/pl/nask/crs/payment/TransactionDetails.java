package pl.nask.crs.payment;

import java.math.BigDecimal;
import java.util.*;

import pl.nask.crs.commons.utils.HtmlPrintable;
import pl.nask.crs.commons.utils.TableFormatter;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.NRPStatus;
import pl.nask.crs.vat.ProductPriceWithVat;

public class TransactionDetails implements HtmlPrintable {

    private List<TransactionDetail> details = new ArrayList<>();

    private class TransactionDetail {

        private String domainName;
        private String holderName;
        private Integer years;
        private OperationType operationType;
        private Date registrationDate;
        private Date renewalDate;
        private BigDecimal value;
        private NRPStatus nrpStatus;

        public TransactionDetail(String domainName, String holderName, Integer years, OperationType operationType,
                Date registrationDate, Date renewalDate, BigDecimal value, NRPStatus nrpStatus) {
            this.domainName = domainName;
            this.holderName = holderName;
            this.years = years;
            this.operationType = operationType;
            this.registrationDate = registrationDate;
            this.renewalDate = renewalDate;
            this.value = value;
            this.nrpStatus = nrpStatus;
        }

        public String getDomainName() {
            return domainName;
        }

        public String getHolderName() {
            return holderName;
        }

        public Integer getYears() {
            return years;
        }

        public OperationType getOperationType() {
            return operationType;
        }

        public Date getRegistrationDate() {
            return registrationDate;
        }

        public Date getRenewalDate() {
            return renewalDate;
        }

        public BigDecimal getValue() {
            return value;
        }

        public NRPStatus getNrpStatus() {
            return nrpStatus;
        }
    }

    public TransactionDetails(Map<String, ProductPriceWithVat> pricePerDomain, OperationType operationType,
            Map<String, Domain> domainMap) {
        for (Map.Entry<String, ProductPriceWithVat> entry : pricePerDomain.entrySet()) {
            String domainName = entry.getKey();
            Domain domain = domainMap.get(domainName);
            ProductPriceWithVat productPriceWithVat = entry.getValue();
            details.add(new TransactionDetail(domain.getName(), domain.getHolder(),
                    productPriceWithVat.getPeriod().getYears(), operationType, domain.getRegistrationDate(), domain
                            .getRenewalDate(), productPriceWithVat.getTotal(), null));
        }
    }

    public TransactionDetails(String domainName, String domainHolder, int years, OperationType operationType,
            BigDecimal value) {
        details.add(new TransactionDetail(domainName, domainHolder, years, operationType, null, null, value, null));
    }

    public TransactionDetails(String domainName, String domainHolder, int years, OperationType operationType,
            Date regDate, Date renDate, BigDecimal value) {
        details.add(
                new TransactionDetail(domainName, domainHolder, years, operationType, regDate, renDate, value, null));
    }

    /**
     * Returns transaction details in a nice table format:
     * <pre>
         |domainName    |HolderName    |    period|NRPStatus    |OperationType    |RegistrationDate    |RenewalDate    |    Value|
     * </pre>
     *
     *
     * @return
     */

    @Override
    public String toString() {
        return getFormattedDetailsText(false);
    }

    @Override
    public String toHtmlString() {
        return getFormattedDetailsText(true);
    }

    protected String getFormattedDetailsText(boolean html) {
        TableFormatter tableFormatter = new TableFormatter(Locale.ENGLISH);

        boolean initiated = false;
        for (TransactionDetail detail : details) {
            if (!initiated) {
                initTable(tableFormatter, detail);
                initiated = true;
            }

            tableFormatter.addDataLine(objectArray(detail));
        }

        return html ? tableFormatter.toHtmlString() : tableFormatter.toString();
    }

    private Object[] objectArray(TransactionDetail detail) {
        return new Object[] {detail.getDomainName(), detail.getHolderName(), detail.getYears(), detail.getNrpStatus(),
                detail.getOperationType(), detail.getRegistrationDate(), detail.getRenewalDate(), detail.getValue()};
    }

    private void initTable(TableFormatter tableFormatter, TransactionDetail data) {
        tableFormatter
                .addColumn("Domain", 60, TableFormatter.leftAlignedStringFormat(60), data.getDomainName() != null);
        tableFormatter
                .addColumn("Holder", 60, TableFormatter.leftAlignedStringFormat(60), data.getHolderName() != null);
        tableFormatter.addColumn("Years", 5, TableFormatter.digitsFormat(5), data.getYears() != null);
        tableFormatter.addColumn("NRP", 10, TableFormatter.rightAlignedStringFormat(10), data.getNrpStatus() != null);
        tableFormatter.addColumn("Type", 12, TableFormatter.rightAlignedStringFormat(12),
                data.getOperationType() != null);
        tableFormatter.addColumn("RegDt", 10, TableFormatter.dateYMDFormat(10), data.getRegistrationDate() != null);
        tableFormatter.addColumn("RenDt", 10, TableFormatter.dateYMDFormat(10), data.getRenewalDate() != null);
        tableFormatter.addColumn("Value", 10, TableFormatter.moneyFormat(10), data.getValue() != null);
    }
}
