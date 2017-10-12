package pl.nask.crs.web.security;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import pl.nask.crs.app.authorization.PermissionAppService;
import pl.nask.crs.commons.utils.ServletUtils;
import pl.nask.crs.security.authentication.*;

/**
 * @author Patrycja Wegrzynowicz
 */
public class LogonAction extends ActionSupport implements SessionAware, ServletRequestAware {
    public final static String EXPIRED = "expired";
    private String username;
    private String password;
    private Map session;
    private AuthenticationService authenticationService;
    private PermissionAppService permissionAppService;
    private HttpServletRequest request;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSession(Map map) {
        this.session = map;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public void setPermissionAppService(PermissionAppService permissionAppService) {
        this.permissionAppService = permissionAppService;
    }

    public PermissionAppService getPermissionAppService() {
        return permissionAppService;
    }

    public String in() throws Exception {
        try {
            AuthenticatedUser u = authenticationService.authenticate(username, password, true,
                    ServletUtils.getRemoteIP(request), false, null, false, "crs");
            session.put(SecurityConstants.USER_KEY, u);
            session.put(SecurityConstants.PERMISSION_KEY, permissionAppService);
            return SUCCESS;
        } catch (InvalidUsernameOrPasswordException ex) {
            addActionError("Invalid username or password: " + ex.getUsername());
            return ERROR;
        } catch (NoLoginPermissionException ex) {
            addActionError("No login permission for user " + ex.getUsername());
            return ERROR;
        } catch (PasswordExpiredException ex) {
            return EXPIRED;
        } catch (LoginException ex) {
            addActionError(ex.getMessage());
            return ERROR;
        }
    }

    public String out() throws Exception {
        AuthenticatedUser user = (AuthenticatedUser) session.get(SecurityConstants.USER_KEY);
        session.remove(SecurityConstants.USER_KEY);
        HttpSession s = request.getSession(false);
        if (s != null) {
            s.invalidate();
        }
        if (user != null) {
            Logger.getLogger(LogonAction.class).info("User logged out of crsweb: " + user.getUsername());
        }
        return LOGIN;
    }

    public String input() throws Exception {
        if (session.get(SecurityConstants.USER_KEY) != null) {
            return SUCCESS;
        } else
            return ERROR;
    }

    public void setServletRequest(HttpServletRequest request) {
        this.request = request;

    }
}
