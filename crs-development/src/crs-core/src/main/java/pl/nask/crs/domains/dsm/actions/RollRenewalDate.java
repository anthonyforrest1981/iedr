package pl.nask.crs.domains.dsm.actions;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class RollRenewalDate extends AbstractDsmAction {

    private int years = -1;

    public RollRenewalDate() {
        super();
    }

    public RollRenewalDate(int years) {
        this.years = years;
    }

    @Override
    protected void invokeAction(AuthenticatedUser user, Domain domain, DsmEvent event) {
        int periodInYears = years == -1 ? event.getIntParameter(DsmEvent.RENEWAL_PERIOD) : years;
        Date newDate = DateUtils.addYears(domain.getRenewalDate(), periodInYears);
        domain.setRenewalDate(newDate);
    }

}
