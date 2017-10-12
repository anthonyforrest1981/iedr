package pl.nask.crs.app.commons;

import java.math.BigDecimal;
import java.util.List;

public class ApplicationConfiguration {
    private List<String> topLevelDomains;
    private boolean oneOrTwoLetterDomainAllowed;
    private boolean idnDomainAllowed;
    private int nameserversMinCount;
    private int nameserversMaxCount;
    private BigDecimal depositMinLimit;
    private BigDecimal depositMaxLimit;
    private int authCodeFailureLimit;
    private int ticketExpirationPeriod;
    private int secondaryMarketCountdownPeriod;
    private int secondaryMarketAuthcodeExpirationPeriod;

    public ApplicationConfiguration(List<String> topLevelDomains, boolean oneLetterDomainAllowed,
            boolean idnDomainAllowed, int minCount, int maxCount, BigDecimal minDeposit, BigDecimal maxDeposit,
            int failureLimit, int ticketExpirationPeriod, int secondaryMarketCountdownPeriod,
            int secondaryMarketAuthcodeExpirationPeriod) {
        this.topLevelDomains = topLevelDomains;
        this.oneOrTwoLetterDomainAllowed = oneLetterDomainAllowed;
        this.idnDomainAllowed = idnDomainAllowed;
        this.nameserversMinCount = minCount;
        this.nameserversMaxCount = maxCount;
        this.depositMinLimit = minDeposit;
        this.depositMaxLimit = maxDeposit;
        this.authCodeFailureLimit = failureLimit;
        this.ticketExpirationPeriod = ticketExpirationPeriod;
        this.secondaryMarketCountdownPeriod = secondaryMarketCountdownPeriod;
        this.secondaryMarketAuthcodeExpirationPeriod = secondaryMarketAuthcodeExpirationPeriod;
    }

    public List<String> getTopLevelDomains() {
        return topLevelDomains;
    }

    public int getNameserversMinCount() {
        return nameserversMinCount;
    }

    public int getNameserversMaxCount() {
        return nameserversMaxCount;
    }

    public BigDecimal getDepositMinLimit() {
        return depositMinLimit;
    }

    public BigDecimal getDepositMaxLimit() {
        return depositMaxLimit;
    }

    public int getAuthCodeFailureLimit() {
        return authCodeFailureLimit;
    }

    public boolean isOneOrTwoLetterDomainAllowed() {
        return oneOrTwoLetterDomainAllowed;
    }

    public boolean isIdnDomainAllowed() {
        return idnDomainAllowed;
    }

    public int getTicketExpirationPeriod() {
        return ticketExpirationPeriod;
    }

    public int getSecondaryMarketCountdownPeriod() {
        return secondaryMarketCountdownPeriod;
    }

    public int getSecondaryMarketAuthcodeExpirationPeriod() {
        return secondaryMarketAuthcodeExpirationPeriod;
    }
}
