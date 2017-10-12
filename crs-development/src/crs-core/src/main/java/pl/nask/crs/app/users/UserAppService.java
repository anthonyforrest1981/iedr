package pl.nask.crs.app.users;

import java.util.List;
import java.util.Set;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.*;

public interface UserAppService {

    User getUser(AuthenticatedUser user, String nicHandleId) throws AccessDeniedException;

    void changePermissionGroups(AuthenticatedUser hostmaster, String nicHandleId, Set<Group> permissionGroups)
            throws AccessDeniedException;

    LimitedSearchResult<HistoricalObject<User>> getHistory(AuthenticatedUser hostmaster, String nicHandleId,
            int offset, int limit) throws AccessDeniedException;

    NRCLevel getUserLevel(AuthenticatedUser user) throws AccessDeniedException;

    List<String> getInternalUsers();

    boolean isTfaUsed(AuthenticatedUser user) throws AccessDeniedException;

    String changeTfa(AuthenticatedUser user, boolean useTfa) throws AccessDeniedException;

}
