package pl.nask.crs.app.invoicing.email;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Joiner;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.nichandle.NicHandle;

public class InvalidatedInvoiceEmailParams implements EmailParameters {

    private NicHandle billingNH;
    private List<String> domains;
    private String username;

    public InvalidatedInvoiceEmailParams(NicHandle billingNH, List<String> domains, String username) {
        this.billingNH = billingNH;
        this.domains = domains;
        this.username = username;
    }

    public String getLoggedInNicHandle() {
        return username;
    }

    public String getAccountRelatedNicHandle(boolean gaining) {
        return billingNH.getNicHandleId();
    }

    public String getDomainName() {
        return null; // No admin verification for a list of domains
    }

    @Override
    public List<ParameterName> getParameterNames() {
        return Arrays.<ParameterName>asList(ParameterNameEnum.DOMAIN, ParameterNameEnum.BILL_C_NIC,
                ParameterNameEnum.BILL_C_EMAIL, ParameterNameEnum.BILL_C_CO_NAME);
    }

    @Override
    public String getParameterValue(String name, boolean html) {
        ParameterNameEnum param = ParameterNameEnum.forName(name);
        switch (param) {
            case BILL_C_NIC:
                return billingNH.getNicHandleId();
            case BILL_C_EMAIL:
                return billingNH.getEmail();
            case DOMAIN:
                return Joiner.on(",").join(domains);
            case BILL_C_CO_NAME:
                return billingNH.getCompanyName();
            default:
                return null;
        }
    }

}
