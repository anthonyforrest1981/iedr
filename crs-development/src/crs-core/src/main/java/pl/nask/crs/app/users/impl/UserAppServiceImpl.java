package pl.nask.crs.app.users.impl;

import java.util.List;
import java.util.Set;

import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.UserNotAuthenticatedException;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.NRCLevel;
import pl.nask.crs.user.User;
import pl.nask.crs.user.search.HistoricalUserSearchCriteria;
import pl.nask.crs.user.service.HistoricalUserSearchService;
import pl.nask.crs.user.service.UserSearchService;
import pl.nask.crs.user.service.UserService;

import static pl.nask.crs.app.utils.UserValidator.validateLoggedIn;

public class UserAppServiceImpl implements UserAppService {

    private UserSearchService userSearchService;
    private HistoricalUserSearchService historicalUserSearchService;
    private UserService userService;

    public UserAppServiceImpl(UserSearchService userSearchService, UserService userService,
            HistoricalUserSearchService historicalUserSearchService) {
        Validator.assertNotNull(userSearchService, "user search service");
        Validator.assertNotNull(userService, "user service");
        Validator.assertNotNull(historicalUserSearchService, "historical user search service");
        this.userSearchService = userSearchService;
        this.userService = userService;
        this.historicalUserSearchService = historicalUserSearchService;
    }

    @Override
    public User getUser(AuthenticatedUser user, String nicHandleId) throws UserNotAuthenticatedException {
        validateLoggedIn(user);
        return userSearchService.get(nicHandleId);
    }

    @Override
    public void changePermissionGroups(AuthenticatedUser hostmaster, String nicHandleId, Set<Group> permissionGroups)
            throws AccessDeniedException {
        validateLoggedIn(hostmaster);
        userService.changePermissionGroups(nicHandleId, permissionGroups, new OpInfo(hostmaster));
    }

    @Override
    public LimitedSearchResult<HistoricalObject<User>> getHistory(AuthenticatedUser hostmaster, String nicHandleId,
            int offset, int limit) {
        return historicalUserSearchService.find(new HistoricalUserSearchCriteria(nicHandleId), offset, limit);
    }

    @Override
    public NRCLevel getUserLevel(AuthenticatedUser user) throws AccessDeniedException {
        validateLoggedIn(user);
        return userService.getUserLevel(user.getUsername());
    }

    @Override
    public boolean isTfaUsed(AuthenticatedUser user) throws UserNotAuthenticatedException {
        validateLoggedIn(user);
        return userSearchService.get(user.getUsername()).isUseTwoFactorAuthentication();
    }

    @Override
    public List<String> getInternalUsers() {
        return userSearchService.findInternalUsers();
    }

    @Override
    public String changeTfa(AuthenticatedUser user, boolean useTfa) throws UserNotAuthenticatedException {
        validateLoggedIn(user);
        OpInfo opInfo = new OpInfo(user);
        return userService.changeTfa(user.getUsername(), useTfa, opInfo);
    }

}
