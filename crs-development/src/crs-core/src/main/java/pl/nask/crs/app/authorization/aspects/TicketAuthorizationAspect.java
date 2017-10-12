package pl.nask.crs.app.authorization.aspects;

import org.aspectj.lang.JoinPoint;

import pl.nask.crs.app.authorization.queries.TicketPermissionQuery;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.user.permissions.PermissionDeniedException;
import pl.nask.crs.user.permissions.PermissionQuery;

public class TicketAuthorizationAspect implements AuthorizationAspect {
    private AuthorizationService authorizationService;

    public TicketAuthorizationAspect(AuthorizationService authorizationService) {
        Validator.assertNotNull(authorizationService, "authorization service");
        this.authorizationService = authorizationService;
    }

    public void checkPermissionTicketId(JoinPoint joinPoint) throws AccessDeniedException {
        AuthenticatedUser user = (AuthenticatedUser) joinPoint.getArgs()[0];
        long ticketId = (Long) joinPoint.getArgs()[1];

        authorizationService.authorize(user, new TicketPermissionQuery(PermissionAspectHelper.makePermissionName(joinPoint), ticketId, user));
    }

    public void checkPermissionDomainName(JoinPoint joinPoint) throws AccessDeniedException {
        AuthenticatedUser user = (AuthenticatedUser) joinPoint.getArgs()[0];
        String domainName = (String) joinPoint.getArgs()[1];

        String permissionName = PermissionAspectHelper.makePermissionName(joinPoint);
        authorizationService.authorize(user, new TicketPermissionQuery(permissionName, domainName, user));
    }

}
