package pl.nask.crs.domains.dsm.actions;

import org.apache.commons.lang.time.DateUtils;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class SetRenewalDate extends AbstractDsmAction {

    private int yearsToAdd;

    @Override
    public void parseAndSetActionParameters(String actionParam) {
        super.parseAndSetActionParameters(actionParam);
        yearsToAdd = Integer.parseInt(actionParam);
    }

    @Override
    protected void invokeAction(AuthenticatedUser user, Domain domain, DsmEvent event) {
        domain.setRenewalDate(DateUtils.addYears(domain.getRegistrationDate(), yearsToAdd));
    }
}
