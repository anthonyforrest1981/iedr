package pl.nask.crs.app.authorization.queries;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.user.permissions.NamedPermissionQuery;

public class TicketPermissionQuery extends NamedPermissionQuery {

    private /*>>>@Nullable*/ Long ticketId;
    private /*>>>@Nullable*/ String domainName;
    private final AuthenticatedUser user;

    public TicketPermissionQuery(String permissionName, long ticketId, AuthenticatedUser user) {
        super(permissionName);
        this.ticketId = ticketId;
        this.user = user;
    }

    public TicketPermissionQuery(String permissionName, String domainName, AuthenticatedUser user) {
        super(permissionName);
        this.domainName = domainName;
        this.user = user;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ Long getTicketId() {
        return ticketId;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ String getDomainName() {
        return domainName;
    }

    /*>>>@Pure*/
    public AuthenticatedUser getUser() {
        return user;
    }
}
