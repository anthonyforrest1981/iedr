package pl.nask.crs.user.dao.ibatis.converters;

import java.util.ArrayList;

import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.ibatis.objects.InternalUser;
import pl.nask.crs.user.service.AuthorizationGroupsFactory;

public class UserConverter extends AbstractConverter<InternalUser, User> {

    AuthorizationGroupsFactory groupsFactory;

    public UserConverter(AuthorizationGroupsFactory groupsFactory) {
        Validator.assertNotNull(groupsFactory, "authorization groups factory");
        this.groupsFactory = groupsFactory;
    }

    protected User _to(InternalUser internalUser) {
        return new User(internalUser.getUsername(), internalUser.getPassword(),
                groupsFactory.getGroups(internalUser.getLevel()), internalUser.isInternal(), internalUser.getSalt(),
                internalUser.getPasswordChangeDate(), internalUser.isForcePasswordChange(),
                internalUser.isUseTwoFactorAuthentication(), internalUser.getSecret(), internalUser.getName(),
                groupsFactory.getPermissionsByName(internalUser.getPermissionNames()));
    }

    protected InternalUser _from(User user) {
        return new InternalUser(user.getUsername(), user.getPassword(),
                groupsFactory.getLevel(user.getPermissionGroups()), user.isInternal(), user.getSalt(),
                user.getPasswordChangeDate(), user.isForcePasswordChange(), user.isUseTwoFactorAuthentication(),
                user.getSecret(), user.getName(), new ArrayList<>(user.getPermissions().keySet()));
    }
}
