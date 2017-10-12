package pl.nask.crs.security.authentication.impl;

import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.User;
import pl.nask.crs.user.service.UserSearchService;

public class ArtificialUsersRegistry {

    private ApplicationConfig applicationConfig;
    private NicHandleDAO nicHandleDAO;
    private UserSearchService userSearchService;

    public ArtificialUsersRegistry(ApplicationConfig applicationConfig, NicHandleDAO nicHandleDAO,
            UserSearchService userSearchService) {
        this.applicationConfig = applicationConfig;
        this.nicHandleDAO = nicHandleDAO;
        this.userSearchService = userSearchService;
    }

    public AuthenticatedUser getPortalAuthenticatedUser() throws NicHandleNotFoundException {
        String nicHandleId = applicationConfig.getPortalUserNicHandle();
        validateNicHandleExists(nicHandleId);
        return new AuthenticatedUserImpl(nicHandleId);
    }

    public AuthenticatedUser getSchedulerJobAuthenticatedUser(String nicHandleId)
            throws NicHandleNotFoundException, AccessDeniedException {
        validateNicHandleExists(nicHandleId);
        validateNhLevel(nicHandleId, Level.Batch);
        return new AuthenticatedUserImpl(nicHandleId);
    }

    private void validateNhLevel(String nicHandleId, Level level) throws AccessDeniedException {
        User user = userSearchService.get(nicHandleId);
        if (!user.hasGroup(level)) {
            throw new AccessDeniedException(String.format("Provided user (%s) should have %s NH_Level", nicHandleId,
                    level.getLevel()));
        }
    }

    private void validateNicHandleExists(String nicHandleId) throws NicHandleNotFoundException {
        NicHandle nicHandle = nicHandleDAO.get(nicHandleId);
        if (nicHandle == null) {
            throw new NicHandleNotFoundException(nicHandleId);
        }
    }

}
