package pl.nask.crs.payment.exceptions;

import pl.nask.crs.user.permissions.PermissionDeniedException;

public class DomainManagedByAnotherResellerException extends PermissionDeniedException {

    private String domainName;

    public DomainManagedByAnotherResellerException() {}

    public DomainManagedByAnotherResellerException(String domainName) {
        super();
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }
}
