package pl.nask.crs.api.vo;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.app.commons.ApplicationConfiguration;

@XmlRootElement
@XmlType
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ApplicationConfigurationVO {

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

    public ApplicationConfigurationVO() {}

    public ApplicationConfigurationVO(ApplicationConfiguration data) {
        topLevelDomains = data.getTopLevelDomains();
        oneOrTwoLetterDomainAllowed = data.isOneOrTwoLetterDomainAllowed();
        idnDomainAllowed = data.isIdnDomainAllowed();
        nameserversMinCount = data.getNameserversMinCount();
        nameserversMaxCount = data.getNameserversMaxCount();
        depositMinLimit = data.getDepositMinLimit();
        depositMaxLimit = data.getDepositMaxLimit();
        authCodeFailureLimit = data.getAuthCodeFailureLimit();
        ticketExpirationPeriod = data.getTicketExpirationPeriod();
        secondaryMarketCountdownPeriod = data.getSecondaryMarketCountdownPeriod();
        secondaryMarketAuthcodeExpirationPeriod = data.getSecondaryMarketAuthcodeExpirationPeriod();
    }

    public List<String> getTopLevelDomains() {
        return topLevelDomains;
    }

    public void setTopLevelDomains(List<String> topLevelDomains) {
        this.topLevelDomains = topLevelDomains;
    }

    public boolean isOneOrTwoLetterDomainAllowed() {
        return oneOrTwoLetterDomainAllowed;
    }

    public void setOneOrTwoLetterDomainAllowed(boolean oneOrTwoLetterDomainAllowed) {
        this.oneOrTwoLetterDomainAllowed = oneOrTwoLetterDomainAllowed;
    }

    public int getNameserversMinCount() {
        return nameserversMinCount;
    }

    public void setNameserversMinCount(int nameserversMinCount) {
        this.nameserversMinCount = nameserversMinCount;
    }

    public int getNameserversMaxCount() {
        return nameserversMaxCount;
    }

    public void setNameserversMaxCount(int nameserversMaxCount) {
        this.nameserversMaxCount = nameserversMaxCount;
    }

    public BigDecimal getDepositMinLimit() {
        return depositMinLimit;
    }

    public void setDepositMinLimit(BigDecimal depositMinLimit) {
        this.depositMinLimit = depositMinLimit;
    }

    public BigDecimal getDepositMaxLimit() {
        return depositMaxLimit;
    }

    public void setDepositMaxLimit(BigDecimal depositMaxLimit) {
        this.depositMaxLimit = depositMaxLimit;
    }

    public int getAuthCodeFailureLimit() {
        return authCodeFailureLimit;
    }

    public void setAuthCodeFailureLimit(int authCodeFailureLimit) {
        this.authCodeFailureLimit = authCodeFailureLimit;
    }

    public boolean isIdnDomainAllowed() {
        return idnDomainAllowed;
    }

    public void setIdnDomainAllowed(boolean idnDomainAllowed) {
        this.idnDomainAllowed = idnDomainAllowed;
    }

    public int getTicketExpirationPeriod() {
        return ticketExpirationPeriod;
    }

    public void setTicketExpirationPeriod(int ticketExpirationPeriod) {
        this.ticketExpirationPeriod = ticketExpirationPeriod;
    }

    public int getsecondaryMarketCountdownPeriod() {
        return secondaryMarketCountdownPeriod;
    }

    public void setsecondaryMarketCountdownPeriod(int secondaryMarketCountdownPeriod) {
        this.secondaryMarketCountdownPeriod = secondaryMarketCountdownPeriod;
    }

    public int getSecondaryMarketAuthcodeExpirationPeriod() {
        return secondaryMarketAuthcodeExpirationPeriod;
    }

    public void setSecondaryMarketAuthcodeExpirationPeriod(int secondaryMarketAuthcodeExpirationPeriod) {
        this.secondaryMarketAuthcodeExpirationPeriod = secondaryMarketAuthcodeExpirationPeriod;
    }
}
