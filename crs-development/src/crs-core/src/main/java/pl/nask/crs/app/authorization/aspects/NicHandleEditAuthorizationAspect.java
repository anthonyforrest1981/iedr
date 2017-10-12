package pl.nask.crs.app.authorization.aspects;

import org.aspectj.lang.JoinPoint;

import pl.nask.crs.app.authorization.queries.NicHandleQuery;
import pl.nask.crs.app.authorization.queries.NicHandleSearchPermissionQuery;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.user.permissions.PermissionDeniedException;

public class NicHandleEditAuthorizationAspect implements AuthorizationAspect {

    private AuthorizationService authorizationService;

    public NicHandleEditAuthorizationAspect(AuthorizationService authorizationService) {
        Validator.assertNotNull(authorizationService, "authorization service");
        this.authorizationService = authorizationService;
    }

    /**
     * expects, that NicHandle (full object or name) will be given as a second parameter
     * @param joinPoint
     * @throws AccessDeniedException
     */
    public void checkPermission(JoinPoint joinPoint) throws AccessDeniedException {
        AuthenticatedUser user = (AuthenticatedUser) joinPoint.getArgs()[0];
        Object nicHandleObject = joinPoint.getArgs()[1];
        if (nicHandleObject == null) {
            throw new IllegalArgumentException("empty nicHandle");
        }
        String permissionName = PermissionAspectHelper.makePermissionName(joinPoint);
        NicHandleQuery query = null;
        if (nicHandleObject instanceof NicHandle) {
            query = new NicHandleQuery(permissionName, (NicHandle) nicHandleObject, user);
        } else if (nicHandleObject instanceof String) {
            query = new NicHandleQuery(permissionName, (String) nicHandleObject, user);
        }
        authorizationService.authorize(user, query);
    }

    public void checkPermissionSearch(JoinPoint joinPoint) throws AccessDeniedException {
        AuthenticatedUser user = (AuthenticatedUser) joinPoint.getArgs()[0];
        NicHandleSearchCriteria crit = (NicHandleSearchCriteria) joinPoint.getArgs()[1];
        Long accountNumber = crit != null ? crit.getAccountNumber() : null;
        String creator = crit != null ? crit.getCreator() : null;
        String permissionName = PermissionAspectHelper.makePermissionName(joinPoint);
        authorizationService.authorize(user,
                new NicHandleSearchPermissionQuery(permissionName, accountNumber, creator, user));
    }

}
