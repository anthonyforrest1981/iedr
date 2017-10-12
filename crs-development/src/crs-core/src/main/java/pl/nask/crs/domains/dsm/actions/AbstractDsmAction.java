package pl.nask.crs.domains.dsm.actions;

import org.apache.log4j.Logger;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dsm.DsmAction;
import pl.nask.crs.domains.dsm.DsmEvent;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public abstract class AbstractDsmAction implements DsmAction {
    private final static Logger log = Logger.getLogger(DsmAction.class);

    private String actionParam;

    @Override
    public final void invoke(AuthenticatedUser user, Domain domain, DsmEvent event) {
        if (log.isDebugEnabled())
            log.debug("Executing DSM action: " + this);

        try {
            invokeAction(user, domain, event);
        } catch (RuntimeException e) {
            log.error("Error invoking DSM action: " + this);
            throw e;
        } catch (Exception e) {
            log.error("Error invoking DSM action: " + this);
            throw new RuntimeException(e);
        }

        if (log.isDebugEnabled())
            log.debug("Execution of DSM action " + this + " finished ");
    }

    protected abstract void invokeAction(AuthenticatedUser user, Domain domain, DsmEvent event) throws Exception;

    @Override
    public void parseAndSetActionParameters(String actionParam) {
        this.actionParam = actionParam;
    }

    @Override
    public String getActionParam() {
        return actionParam;
    }

    @Override
    public String toString() {
        return "DsmAction(" + getClass().getSimpleName() + ") [actionParam=" + actionParam + "]";
    }

}
