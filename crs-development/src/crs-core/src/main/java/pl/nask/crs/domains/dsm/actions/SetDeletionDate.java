package pl.nask.crs.domains.dsm.actions;

import org.apache.commons.lang.time.DateUtils;

import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class SetDeletionDate extends AbstractDsmAction {

    private ApplicationConfig appConfig;

    public SetDeletionDate(ApplicationConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    protected void invokeAction(AuthenticatedUser user, Domain domain, DsmEvent event) {
        if (domain.getSuspensionDate() == null) {
            throw new IllegalStateException("Domain " + domain.getName() + " does not have a suspension date set");
        }

        int suspendedPeriod = appConfig.getNRPConfig().getNrpSuspendedPeriod();
        domain.setDeletionDate(DateUtils.addDays(domain.getSuspensionDate(), suspendedPeriod));
    }
}
