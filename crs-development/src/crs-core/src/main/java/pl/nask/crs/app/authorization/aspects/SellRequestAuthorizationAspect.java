package pl.nask.crs.app.authorization.aspects;

import org.aspectj.lang.JoinPoint;

import pl.nask.crs.app.authorization.queries.SellRequestPermissionQuery;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.user.permissions.PermissionDeniedException;

public class SellRequestAuthorizationAspect implements AuthorizationAspect {

    private AuthorizationService authorizationService;

    public SellRequestAuthorizationAspect(AuthorizationService authorizationService) {
        Validator.assertNotNull(authorizationService, "authorization service");
        this.authorizationService = authorizationService;
    }

    public void checkPermission(JoinPoint joinPoint) throws AccessDeniedException {
        AuthenticatedUser user = (AuthenticatedUser) joinPoint.getArgs()[0];
        long sellRequestId = (Long) joinPoint.getArgs()[1];
        authorizationService.authorize(user,
                new SellRequestPermissionQuery(PermissionAspectHelper.makePermissionName(joinPoint), sellRequestId,
                        user));
    }

}
