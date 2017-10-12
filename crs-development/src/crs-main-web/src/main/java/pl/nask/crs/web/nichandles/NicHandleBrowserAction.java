package pl.nask.crs.web.nichandles;

import java.util.Arrays;
import java.util.List;

import org.apache.struts2.interceptor.SessionAware;

import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.NicHandleStatus;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

/**
 * @author Marianna Mysiorska
 */
@Deprecated
public class NicHandleBrowserAction extends AuthenticatedUserAwareAction implements SessionAware {

    public static final String SEARCH = "search";

    private NicHandleAppService nicHandleAppService;
    private List<NicHandleStatus> statuses;
    private String nicHandleId;
    private NicHandle nicHandle;
    private NicHandleStatus newStatus;
    private String hostmastersRemark;

    public NicHandleAppService getNicHandleAppService() {
        return nicHandleAppService;
    }

    public void setNicHandleAppService(NicHandleAppService nicHandleAppService) {
        this.nicHandleAppService = nicHandleAppService;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public NicHandleStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(NicHandleStatus newStatus) {
        this.newStatus = newStatus;
    }

    public String getHostmastersRemark() {
        return hostmastersRemark;
    }

    public void setHostmastersRemark(String hostmastersRemark) {
        this.hostmastersRemark = hostmastersRemark;
    }

    public List<NicHandleStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<NicHandleStatus> statuses) {
        this.statuses = statuses;
    }

    public String alterStatus() throws Exception {
        statuses = Arrays.asList(NicHandleStatus.values());
        nicHandleAppService.alterStatus(getUser(), nicHandleId, newStatus, hostmastersRemark);
        return SEARCH;
    }

}
