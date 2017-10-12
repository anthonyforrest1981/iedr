package pl.nask.crs.api.users;

import java.util.List;

import pl.nask.crs.api.SessionExpiredException;
import pl.nask.crs.api.WsSessionAware;
import pl.nask.crs.api.validation.ValidationHelper;
import pl.nask.crs.api.vo.AuthenticatedUserVO;
import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationException;

public class UserAppServiceEndpoint extends WsSessionAware implements CRSPermissionsAppService {

    private UserAppService service;

    public void setService(UserAppService service) {
        this.service = service;
    }

    @Override
    public int getUserLevel(AuthenticatedUserVO user) throws SessionExpiredException, AuthenticationException {
        ValidationHelper.validate(user);
        AuthenticatedUser u = validateSessionAndRetrieveFullUserInfo(user, false);
        return service.getUserLevel(u).getLevel();
    }

    @Override
    public List<String> getInternalUsers() {
        return service.getInternalUsers();
    }

}
