package pl.nask.crs.security.authentication;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

/**
 * Interface that represents authenticated user in system.
 *
 * @author Marianna Mysiorska
 */
public interface AuthenticatedUser {

    /**
     *
     * @return the username of the authenticated user; never null
     */
    String getUsername();

    /**
     * @return superuser if set
     */
    /*>>>@Nullable*/ String getSuperUserName();

    /**
     *
     * @return authentication token
     */
    String getAuthenticationToken();

}
