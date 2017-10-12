package pl.nask.crs.app.authorization.queries;

import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.user.permissions.NamedPermissionQuery;

public class DomainPermissionQuery extends NamedPermissionQuery {

    private final AuthenticatedUser user;
    private final QueriedDomains dquery;

    public DomainPermissionQuery(String name, QueriedDomains domains, AuthenticatedUser user) {
        super(name);
        this.user = user;
        this.dquery = domains;
    }

    public QueriedDomains getDomains() {
        return dquery;
    }

    public AuthenticatedUser getUser() {
        return user;
    }
}
