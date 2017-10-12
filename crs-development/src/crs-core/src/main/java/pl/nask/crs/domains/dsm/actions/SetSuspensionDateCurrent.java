package pl.nask.crs.domains.dsm.actions;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class SetSuspensionDateCurrent extends AbstractDsmAction {

    private ApplicationConfig appConfig;

    public SetSuspensionDateCurrent(ApplicationConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    protected void invokeAction(AuthenticatedUser user, Domain domain, DsmEvent event) {
        domain.setSuspensionDate(DateUtils.addDays(new Date(), appConfig.getNRPConfig().getNrpMailedPeriod() + 1));
    }
}
