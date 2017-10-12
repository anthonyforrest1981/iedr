package pl.nask.crs.dnscheck.email;

import java.util.Arrays;
import java.util.List;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DnsNotificationEmailParameters implements EmailParameters {

    private String nicHandleId;
    private String email;
    private String message;
    private String domainName;
    private String registrarNicHandleId;

    public DnsNotificationEmailParameters(String nicHandleId, String registrarNicHandleId, String email,
            String domainName, String message) {
        this.nicHandleId = nicHandleId;
        this.registrarNicHandleId = registrarNicHandleId;
        this.message = message;
        this.domainName = domainName;
        this.email = email;
    }

    public String getLoggedInNicHandle() {
        return null; // Sent by a job
    }

    public String getAccountRelatedNicHandle(boolean gaining) {
        return registrarNicHandleId;
    }

    public String getDomainName() {
        return domainName;
    }

    @Override
    public List<ParameterName> getParameterNames() {
        return Arrays.asList(new ParameterName[] {ParameterNameEnum.TECH_C_EMAIL, ParameterNameEnum.DNS_FAILURES,
                ParameterNameEnum.TECH_C_NIC, ParameterNameEnum.DOMAIN});
    }

    @Override
    public String getParameterValue(String name, boolean html) {
        ParameterNameEnum parameter = ParameterNameEnum.forName(name);
        switch (parameter) {
            case TECH_C_EMAIL:
                return email;
            case DNS_FAILURES:
                return message;
            case TECH_C_NIC:
                return nicHandleId;
            case DOMAIN:
                return domainName;
            default:
                //throw new IllegalArgumentException("wrong parameter name");
                return null;
        }
    }
}
