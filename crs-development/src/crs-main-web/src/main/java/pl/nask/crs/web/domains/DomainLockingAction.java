package pl.nask.crs.web.domains;

import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public class DomainLockingAction extends AuthenticatedUserAwareAction {
    private final DomainAppService service;

    private String domainName;

    private String hostmastersRemark;

    private boolean disableLockingService;

    public DomainLockingAction(DomainAppService service) {
        this.service = service;
    }

    public String lock() throws Exception {
        service.lock(getUser(), domainName, hostmastersRemark);
        return SUCCESS;
    }

    public String unlock() throws Exception {
        service.unlock(getUser(), domainName, hostmastersRemark, disableLockingService);
        return SUCCESS;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public void setHostmastersRemark(String hostmastersRemark) {
        this.hostmastersRemark = hostmastersRemark;
    }

    public String getHostmastersRemark() {
        return hostmastersRemark;
    }

    public void setDisableLockingService(boolean disableLockingService) {
        this.disableLockingService = disableLockingService;
    }
}
