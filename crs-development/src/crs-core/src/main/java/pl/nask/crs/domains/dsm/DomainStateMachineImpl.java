package pl.nask.crs.domains.dsm;

import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.NewDomain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dsm.events.ParameterlessEvent;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.services.DomainService;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public class DomainStateMachineImpl implements DomainStateMachine {
    private static final Logger LOG = Logger.getLogger(DomainStateMachineImpl.class);

    private final DsmDAO dao;
    private final DomainDAO domainDao;
    private final ApplicationConfig applicationConfig;

    private final DsmActionFactory dsmActionFactory;

    private DomainService domainService;

    public DomainStateMachineImpl(DsmDAO dao, DomainDAO domainDao, ApplicationConfig applicationConfig,
            DsmActionFactory dsmActionFactory) {
        this.dao = dao;
        this.domainDao = domainDao;
        this.applicationConfig = applicationConfig;
        this.dsmActionFactory = dsmActionFactory;
    }

    @Override
    public List<String> getValidEventNames(String domainName) {
        return dao.getValidEventNames(domainName);
    }

    @Override
    public void handleEvent(AuthenticatedUser user, Domain domain, DsmEvent event, OpInfo opInfo)
            throws DomainIllegalStateException {
        Validator.assertNotNull(domain, "domain");
        String domainName = domain.getName();
        DsmEventName eventName = event.getName();
        LOG.debug("Handling event " + eventName + " for domain " + domain.getName());

        DsmTransition trans = dao.getTransitionFor(domainName, eventName);
        if (trans == null) {
            List<String> names = dao.getValidEventNames(domainName);
            throw new DomainIllegalStateException(eventName + " is not a valid event for a domain " + domainName
                    + " in the current state (" + domain.getDsmState().getId() + "). Valid events are: " + names,
                    domain.getName());
        }
        executePreEventActions(user, domain, event, trans);
        String eventRemark = "DSM event: " + eventName;
        domain.setRemark(opInfo.getRemark() == null ? eventRemark : opInfo.getRemark() + " (" + eventRemark + ")");
        domainService.updateDomainAndHistory(domain, trans.getTargetState(), opInfo);

        Domain updatedDomain = domainDao.get(domainName);
        executePostEventActions(user, updatedDomain, event, trans);
        deleteIfEligible(user, updatedDomain, opInfo);
        LOG.debug("Finished handling event " + eventName + " for domain " + domainName);
    }

    private void deleteIfEligible(AuthenticatedUser user, Domain domain, OpInfo opInfo) {
        if (domain.getDsmState().getId() == applicationConfig.getEligibleForDeletionDomainState()) {
            domainService.delete(user, domain, opInfo);
        }
    }

    @Override
    public void handleEvent(AuthenticatedUser user, NewDomain newDomain, DsmEvent event, OpInfo opInfo) {
        String domainName = newDomain.getName();
        LOG.debug("Creating a new domain object: " + domainName);
        domainService.create(newDomain, opInfo);
        try {
            handleEvent(user, domainName, event, opInfo);
        } catch (DomainNotFoundException e) {
            // the domain was just created, impossible to not exist
            LOG.error("Just created domain " + newDomain.getName() + " was not found in DSM event handle");
            throw new IllegalStateException(e);
        } catch (DomainIllegalStateException e) {
            // the domain was just created, impossible to already be in incorrect state
            LOG.error("Just created domain " + newDomain.getName() + " was in illegal state already");
            throw new IllegalStateException(e);
        }
    }

    private void executePreEventActions(AuthenticatedUser user, Domain domain, DsmEvent event,
            DsmTransition trans) {
        for (DsmAction action : trans.getPreActions(dsmActionFactory)) {
            action.invoke(user, domain, event);
        }
    }

    private void executePostEventActions(AuthenticatedUser user, Domain domain, DsmEvent event,
            DsmTransition trans) {
        for (DsmAction action : trans.getPostActions(dsmActionFactory)) {
            action.invoke(user, domain, event);
        }
    }

    @Override
    public boolean validateEvent(String domainName, DsmEventName... eventNames) {
        boolean res = true;

        if (eventNames == null)
            return res;

        for (DsmEventName name : eventNames) {
            res = res && validate(domainName, name);
        }

        return res;
    }

    @Override
    public boolean validateEventAndLockDomain(String domainName, DsmEventName... eventName) {
        boolean res = validateEvent(domainName, eventName);
        if (res) {
            domainDao.lock(domainName);
        }

        return res;
    }

    @Override
    public boolean validateEvent(String domainName, DsmEvent... events) {
        boolean res = false;

        if (events == null)
            return res;

        for (DsmEvent e : events) {
            res = res || validate(domainName, e.getName());
        }

        return res;
    }

    @Override
    public boolean validateEventAndLockDomain(String domainName, DsmEvent... event) {
        boolean res = validateEvent(domainName, event);
        if (res) {
            domainDao.lock(domainName);
        }

        return res;
    }

    private boolean validate(String domainName, DsmEventName eventName) {
        boolean res = dao.checkEvent(domainName, eventName);
        LOG.debug("Validating event " + eventName + " for domain " + domainName + ": " + (res ? "valid" : "invalid"));
        return res;
    }

    @Override
    public void handleEvent(AuthenticatedUser user, String domainName, DsmEventName eventName,
            OpInfo opInfo) throws DomainIllegalStateException, DomainNotFoundException {
        handleEvent(user, domainName, new ParameterlessEvent(eventName), opInfo);
    }

    @Override
    public void handleEvent(AuthenticatedUser user, String domainName, DsmEvent event, OpInfo opInfo)
            throws DomainIllegalStateException, DomainNotFoundException {
        if (domainDao.lock(domainName)) {
            Domain domain = domainDao.get(domainName);
            handleEvent(user, domain, event, opInfo);
        } else {
            throw new DomainNotFoundException(domainName);
        }
    }

    public DomainService getDomainService() {
        return domainService;
    }

    public void setDomainService(DomainService domainService) {
        this.domainService = domainService;
    }
}
