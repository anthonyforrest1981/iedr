package pl.nask.crs.app.authorization.aspects;

import org.aspectj.lang.JoinPoint;

import pl.nask.crs.app.authorization.queries.TransactionPermissionQuery;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.payment.dao.TransactionHistDAO;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authorization.AuthorizationService;
import pl.nask.crs.user.permissions.PermissionDeniedException;

public class TransactionHistViewAspect implements AuthorizationAspect {
    private AuthorizationService authorizationService;
    private TransactionHistDAO transactionHistDAO;

    public TransactionHistViewAspect(AuthorizationService authorizationService, TransactionHistDAO transactionDao) {
        this.transactionHistDAO = transactionDao;
        Validator.assertNotNull(authorizationService, "authorizationService");
        this.authorizationService = authorizationService;
    }

    public void checkPermission(JoinPoint joinPoint) throws AccessDeniedException {
        AuthenticatedUser user = (AuthenticatedUser) joinPoint.getArgs()[0];
        Object transactionRef = joinPoint.getArgs()[1];
        String permissionName = PermissionAspectHelper.makePermissionName(joinPoint);

        TransactionPermissionQuery query;
        if (transactionRef instanceof String) {
            query = new TransactionPermissionQuery(permissionName,
                    transactionHistDAO.getByOrderId((String) transactionRef), user);
        } else {
            query = new TransactionPermissionQuery(permissionName, transactionHistDAO.get((Long) transactionRef),
                    user);
        }

        authorizationService.authorize(user, query);
    }

}
