package pl.nask.crs.domains.dsm.actions;

import org.apache.commons.lang.time.DateUtils;

import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class SetSuspensionDateRenewal extends AbstractDsmAction {

    private ApplicationConfig appConfig;

    public SetSuspensionDateRenewal(ApplicationConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    protected void invokeAction(AuthenticatedUser user, Domain domain, DsmEvent event) {
        domain.setSuspensionDate(DateUtils.addDays(domain.getRenewalDate(),
                appConfig.getNRPConfig().getNrpMailedPeriod() + 1));
    }
}
