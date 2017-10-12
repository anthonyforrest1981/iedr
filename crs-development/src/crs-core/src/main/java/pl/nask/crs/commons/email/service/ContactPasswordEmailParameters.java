package pl.nask.crs.commons.email.service;

import java.util.Arrays;
import java.util.List;

import pl.nask.crs.domains.Domain;

public class ContactPasswordEmailParameters implements EmailParameters {

    private String nicHandle;
    private String email;
    private String password;
    private Domain domain;
    private String username;

    public ContactPasswordEmailParameters(String nicHandle, String email, String password, Domain domain,
            String username) {
        this.nicHandle = nicHandle;
        this.email = email;
        this.password = password;
        this.domain = domain;
        this.username = username;
    }

    @Override
    public List<? extends ParameterName> getParameterNames() {
        return Arrays.<ParameterName>asList(ParameterNameEnum.NIC, ParameterNameEnum.NIC_EMAIL,
                ParameterNameEnum.PASSWORD, ParameterNameEnum.DOMAIN, ParameterNameEnum.BILL_C_EMAIL);
    }

    @Override
    public String getParameterValue(String name, boolean html) {

        ParameterNameEnum param = ParameterNameEnum.forName(name);
        switch (param) {
            case NIC:
                return nicHandle;
            case NIC_EMAIL:
                return email;
            case PASSWORD:
                return password;
            case DOMAIN:
                return domain.getName();
            case BILL_C_EMAIL:
                return domain.getBillingContact().getEmail();
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
        return nicHandle;
    }

    @Override
    public String getDomainName() {
        return domain.getName();
    }

}
