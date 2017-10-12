package pl.nask.crs.domains.email;

import java.util.*;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailParametersUtils;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.Domain;

public class WhoisDataEmailParameters implements EmailParameters {
    private String username;
    private Domain domain;
    private String whoisData;

    private static Set<ParameterName> parameterNames;
    static {
        parameterNames = new HashSet<ParameterName>(Arrays.asList(ParameterNameEnum.DOMAIN,
                ParameterNameEnum.BILL_C_NAME, ParameterNameEnum.BILL_C_EMAIL, ParameterNameEnum.ADMIN_C_EMAIL,
                ParameterNameEnum.WHOIS));
        parameterNames.addAll(EmailParametersUtils.getAdminRelatedParameterNames());
    }

    public WhoisDataEmailParameters(String username, Domain domain, String whoisData) {
        this.username = username;
        this.domain = domain;
        this.whoisData = whoisData;
    }

    @Override
    public List<? extends ParameterName> getParameterNames() {
        return new ArrayList<>(parameterNames);
    }

    @Override
    public String getParameterValue(String name, boolean html) {
        Validator.assertNotNull(name, "parameter name");

        if (EmailParametersUtils.isAdminRelatedParameterName(name)) {
            return EmailParametersUtils.getAdminRelatedParameterValue(domain.getAdminContacts(), name, html);
        }

        ParameterNameEnum param = ParameterNameEnum.forName(name);
        switch (param) {
            case DOMAIN:
                return domain.getName();
            case BILL_C_NAME:
                return domain.getBillingContact().getName();
            case BILL_C_EMAIL:
                return domain.getBillingContact().getEmail();
            case WHOIS:
                return whoisData;
            default:
                return null;
        }
    }

    @Override
    public String getLoggedInNicHandle() {
        return username;
    }

    @Override
    public String getAccountRelatedNicHandle(boolean gaining) {
        return domain.getBillingContactNic();
    }

    @Override
    public String getDomainName() {
        return domain.getName();
    }

}
