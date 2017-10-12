package pl.nask.crs.user.permissions;

/**
 * Note: this class is single-threaded
 *
 * @author Patrycja Wegrzynowicz
 */
public abstract class ContextualPermission extends NamedPermission {

    protected ContextualPermission(String id, String name) {
        super(id, name);
    }

    public final boolean implies(PermissionQuery query) throws PermissionDeniedException {
        if (isImplied(query)) {
            return verifyContext(query);
        }
        return false;
    }

    protected boolean isImplied(PermissionQuery query) throws PermissionDeniedException {
        return super.implies(query);
    }

    protected abstract boolean verifyContext(PermissionQuery query) throws PermissionDeniedException;

}
