package pl.nask.crs.accounts.email;

import java.util.Arrays;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.utils.Validator;

/**
 * @author Marianna Mysiorska
 */
public class AccountEmailParameters implements EmailParameters {

    private Account account;
    private String username;

    public AccountEmailParameters(Account account, String username) {
        Validator.assertNotNull(account, "account");
        this.account = account;
        this.username = username;
    }

    public String getLoggedInNicHandle() {
        return username;
    }

    public String getAccountRelatedNicHandle(boolean gaining) {
        return account.getBillingContact().getNicHandle();
    }

    public String getDomainName() {
        return null; // Newly created account unrelated to a domain
    }

    public List<ParameterName> getParameterNames() {
        return Arrays.<ParameterName>asList(ParameterNameEnum.BILL_C_EMAIL, ParameterNameEnum.BILL_C_NIC,
                ParameterNameEnum.BILL_C_NAME);
    }

    public String getParameterValue(String name, boolean html) {
        ParameterNameEnum param = ParameterNameEnum.forName(name);
        switch (param) {
            case BILL_C_EMAIL:
                return account.getBillingContact().getEmail();
            case BILL_C_NIC:
                return account.getBillingContact().getNicHandle();
            case BILL_C_NAME:
                return account.getBillingContact().getName();
            default:
                return null;
        }
    }
}
