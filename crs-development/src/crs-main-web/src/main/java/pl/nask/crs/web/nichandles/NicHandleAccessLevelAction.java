package pl.nask.crs.web.nichandles;

import java.util.*;

import pl.nask.crs.app.authorization.PermissionAppService;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.nichandles.wrappers.NicHandleWrapper;
import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.User;
import pl.nask.crs.user.permissions.NamedPermissionQuery;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.service.AuthorizationGroupsFactory;
import pl.nask.crs.web.displaytag.TableParams;
import pl.nask.crs.web.displaytag.TicketsPaginatedList;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public class NicHandleAccessLevelAction extends AuthenticatedUserAwareAction {

    public static final String INPUT = "input";
    private static final int LIMIT = 15;

    private final NicHandleAppService nicHandleAppService;
    private final UserAppService userAppService;
    private AuthorizationGroupsFactory authorizationGroupsFactory;
    private PermissionAppService permissionService;
    private NicHandle nicHandle;
    private NicHandleWrapper wrapper;
    private String nicHandleId;
    private List<Group> groups;
    private Map<String, Group> groupsMap = new HashMap<>();
    private TableParams tableParams = new TableParams();
    TicketsPaginatedList<HistoricalObject<User>> paginatedHistory;
    private long changeId;
    private int historicalSelected = -1;
    private int selectedPage;

    private String permissionName;

    public NicHandleAccessLevelAction(NicHandleAppService nicHandleAppService, UserAppService userAppService,
            AuthorizationGroupsFactory authorizationGroupsFactory, PermissionAppService permissionService) {
        Validator.assertNotNull(nicHandleAppService, "nicHandleAppService");
        Validator.assertNotNull(userAppService, "user app service");
        Validator.assertNotNull(authorizationGroupsFactory, "authorization groups factory");
        Validator.assertNotNull(permissionService, "permission service");
        this.nicHandleAppService = nicHandleAppService;
        this.userAppService = userAppService;
        this.authorizationGroupsFactory = authorizationGroupsFactory;
        this.permissionService = permissionService;
        groups = authorizationGroupsFactory.getAllGroups();
        for (Group g : groups) {
            groupsMap.put(g.getName(), g);
        }
    }

    public NicHandleWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(NicHandleWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public NicHandle getNicHandle() {
        return nicHandle;
    }

    public void setNicHandle(NicHandle nicHandle) {
        this.nicHandle = nicHandle;
    }

    public String getNicHandleId() {
        return nicHandleId;
    }

    public void setNicHandleId(String nicHandleId) {
        this.nicHandleId = nicHandleId;
    }

    public String input() throws Exception {
        LimitedSearchResult<HistoricalObject<User>> l = userAppService.getHistory(getUser(), getNicHandleId(),
                (tableParams.getPage() - 1) * LIMIT, LIMIT);
        paginatedHistory = new TicketsPaginatedList<>(l.getResults(), (int) l.getTotalResults(),
                tableParams.getPage(), LIMIT);

        nicHandle = nicHandleAppService.get(getUser(), nicHandleId);
        wrapper = new NicHandleWrapper(nicHandle);
        wrapper.createPermissionGroupsWrapper(authorizationGroupsFactory);

        List<HistoricalObject<User>> hist = l.getResults();
        if (changeId >= 0 && hist != null && !hist.isEmpty()) {
            for (int i = 0; i < hist.size(); i++) {
                HistoricalObject<User> hObject = hist.get(i);
                if (hObject.getChangeId() == changeId) {
                    wrapper.setUser(hObject.getObject());
                    wrapper.setGroupsFromUser();
                    historicalSelected = i;
                }
            }
            setSelectedPage(tableParams.getPage());
        } else {
            User u = userAppService.getUser(getUser(), nicHandle.getNicHandleId());
            // if the user is null, then we'll have to create a stub
            if (u == null) {
                u = new User();
                u.setUsername(nicHandleId);
                u.setPermissionGroups(new HashSet<Group>());
                u.setPermissions(Collections.EMPTY_MAP);
            }
            wrapper.setUser(u);
            wrapper.setGroupsFromUser();
            setSelectedPage(-1);
        }

        return INPUT;
    }

    public String edit() throws Exception {
        getData();
        return INPUT;
    }

    public String editPermissions() throws Exception {
        getData();
        return INPUT;
    }

    private Group groupForName(String groupName) {
        if (Validator.isEmpty(groupName))
            return null;
        return groupsMap.get(groupName);
    }

    public List<Group> getAllGroups() {
        return groups;
    }

    public List<String> getAllPermissions() {
        return authorizationGroupsFactory.getAllPermissionNames();
    }

    public List<Group> getSortedGroups(Set<Group> ug) {
        List<Group> l = new ArrayList<>();
        for (Group g : groups) { // to maintain order
            if (ug.contains(g))
                l.add(g);
        }
        return l;
    }

    public List<Group> getUserGroups() {
        Set<Group> ug = getWrapper().getPermissionGroupsWrapper().getPermissionGroups();

        return getSortedGroups(ug);
    }

    public Set<Permission> getGroupPermissions() {
        Set<Permission> permissions = new HashSet<>();
        for (Group g : getWrapper().getPermissionGroupsWrapper().getPermissionGroups()) {
            permissions.addAll(g.getPermissions());
        }
        return permissions;
    }

    public Set<String> getUserPermissions() {
        return getWrapper().getUserPermissions();
    }

    public String save() throws Exception {
        Set<Group> newGroups = wrapper.getPermissionGroupsWrapper().getPermissionGroups();
        Set<Group> permissionGroups = new HashSet<>(newGroups);
        newGroups.clear();

        userAppService.changePermissionGroups(getUser(), nicHandleId, permissionGroups);
        getData();
        LimitedSearchResult<HistoricalObject<User>> l = userAppService.getHistory(getUser(), getNicHandleId(),
                (tableParams.getPage() - 1) * LIMIT, LIMIT);
        paginatedHistory = new TicketsPaginatedList<>(l.getResults(), (int) l.getTotalResults(), tableParams.getPage(),
                LIMIT);

        return SUCCESS;
    }

    public boolean isFullAccess() {
        return permissionService.verifyUserPermission(getUser(), new NamedPermissionQuery("fullAccess"));
    }

    public String addPermission() throws Exception {
        nicHandleAppService.addUserPermission(getUser(), nicHandleId, permissionName);
        getData();
        return SUCCESS;
    }

    public String removePermission() throws Exception {
        nicHandleAppService.removeUserPermission(getUser(), nicHandleId, permissionName);
        getData();
        return SUCCESS;
    }

    private void getData() throws Exception {
        nicHandle = nicHandleAppService.get(getUser(), nicHandleId);
        wrapper = new NicHandleWrapper(nicHandle);
        wrapper.createPermissionGroupsWrapper(authorizationGroupsFactory);
        User u = userAppService.getUser(getUser(), nicHandle.getNicHandleId());
        //        if the user is null, then we'll have to create a stub
        if (u == null) {
            u = new User();
            u.setUsername(nicHandleId);
            u.setPermissionGroups(new HashSet<Group>());
            u.setPermissions(Collections.EMPTY_MAP);
        }
        wrapper.setUser(u);
        wrapper.setGroupsFromUser();
    }

    public TableParams getTableParams() {
        return tableParams;
    }

    public void setTableParams(TableParams tableParams) {
        this.tableParams = tableParams;
    }

    public TicketsPaginatedList<HistoricalObject<User>> getPaginatedHistory() {
        return paginatedHistory;
    }

    public boolean isHistory() {
        return historicalSelected >= 0;
    }

    public void setChangeId(long changeId) {
        this.changeId = changeId;
    }

    public int getHistoricalSelected() {
        return historicalSelected;
    }

    public void setSelectedPage(int selectedPage) {
        this.selectedPage = selectedPage;
    }

    public int getSelectedPage() {
        return selectedPage;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionName() {
        return permissionName;
    }

}
