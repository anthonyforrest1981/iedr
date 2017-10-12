package pl.nask.crs.app.authorization.aspects;

import org.aspectj.lang.JoinPoint;

import pl.nask.crs.app.authorization.queries.TransactionPermissionQuery;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.payment.dao.TransactionDAO;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.user.permissions.PermissionDeniedException;

public class TransactionViewAspect implements AuthorizationAspect {
    private AuthorizationService authorizationService;
    private TransactionDAO transactionDAO;

    public TransactionViewAspect(AuthorizationService authorizationService, TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
        Validator.assertNotNull(authorizationService, "authorizationService");
        this.authorizationService = authorizationService;
    }

    public void checkPermission(JoinPoint joinPoint) throws AccessDeniedException {
        AuthenticatedUser user = (AuthenticatedUser) joinPoint.getArgs()[0];
        Object transactionRef = joinPoint.getArgs()[1];
        String permissionName = PermissionAspectHelper.makePermissionName(joinPoint);
        authorizationService.authorize(user, new TransactionPermissionQuery(permissionName,
                transactionDAO.get((Long) transactionRef), user));
    }

}
