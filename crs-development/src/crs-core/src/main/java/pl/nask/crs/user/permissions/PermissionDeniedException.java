package pl.nask.crs.user.permissions;

import pl.nask.crs.security.authentication.AccessDeniedException;

public class PermissionDeniedException extends AccessDeniedException {

    public PermissionDeniedException() {
        super();
    }

    public PermissionDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

}
