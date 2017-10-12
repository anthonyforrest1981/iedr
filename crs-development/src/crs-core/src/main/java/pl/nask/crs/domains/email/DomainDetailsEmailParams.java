package pl.nask.crs.domains.email;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.domains.Domain;

public class DomainDetailsEmailParams implements EmailParameters {

    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd-MM-yyyy");

    private Domain domain;
    private String username;

    public DomainDetailsEmailParams(String username, Domain domain) {
        this.domain = domain;
        this.username = username;
    }

    public String getLoggedInNicHandle() {
        return username;
    }

    public String getAccountRelatedNicHandle(boolean gaining) {
        return domain.getBillingContactNic();
    }

    public String getDomainName() {
        return domain.getName();
    }

    @Override
    public List<ParameterName> getParameterNames() {
        return Arrays.<ParameterName>asList(ParameterNameEnum.BILL_C_NIC, ParameterNameEnum.BILL_C_EMAIL,
                ParameterNameEnum.BILL_C_NAME, ParameterNameEnum.DOMAIN, ParameterNameEnum.REGISTRATION_DATE,
                ParameterNameEnum.RENEWAL_DATE, ParameterNameEnum.DAYS_TO_RENEWAL, ParameterNameEnum.SUSPENSION_DATE,
                ParameterNameEnum.DELETION_DATE);
    }

    @Override
    public String getParameterValue(String name, boolean html) {
        ParameterNameEnum param = ParameterNameEnum.forName(name);
        switch (param) {
            case BILL_C_NIC:
                return domain.getBillingContactNic();
            case BILL_C_EMAIL:
                return domain.getBillingContact().getEmail();
            case BILL_C_NAME:
                return domain.getBillingContact().getName();
            case DOMAIN:
                return domain.getName();
            case REGISTRATION_DATE:
                return FORMATTER.format(domain.getRegistrationDate());
            case RENEWAL_DATE:
                return FORMATTER.format(domain.getRenewalDate());
            case DAYS_TO_RENEWAL:
                return "" + DateUtils.diffInDays(new Date(), domain.getRenewalDate());
            case SUSPENSION_DATE:
                return domain.getSuspensionDate() == null ? "" : FORMATTER.format(domain.getSuspensionDate());
            case DELETION_DATE:
                return domain.getDeletionDate() == null ? "" : FORMATTER.format(domain.getDeletionDate());
            default:
                //throw new IllegalArgumentException("Wrong parameter name: " + param);
                return null;
        }
    }
}
