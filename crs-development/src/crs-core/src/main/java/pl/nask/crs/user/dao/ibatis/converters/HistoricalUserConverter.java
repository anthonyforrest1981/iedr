package pl.nask.crs.user.dao.ibatis.converters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.ibatis.objects.InternalHistoricalUser;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.service.AuthorizationGroupsFactory;

public class HistoricalUserConverter extends AbstractConverter<InternalHistoricalUser, HistoricalObject<User>> {

    AuthorizationGroupsFactory groupsFactory;

    public HistoricalUserConverter(AuthorizationGroupsFactory groupsFactory) {
        Validator.assertNotNull(groupsFactory, "authorization groups factory");
        this.groupsFactory = groupsFactory;
    }

    @Override
    protected InternalHistoricalUser _from(HistoricalObject<User> dst) {
        User user = dst.getObject();
        return new InternalHistoricalUser(user.getUsername(), user.getPassword(),
                groupsFactory.getLevel(dst.getObject().getPermissionGroups()), user.isInternal(), user.getSalt(),
                user.getPasswordChangeDate(), user.isForcePasswordChange(), user.isUseTwoFactorAuthentication(),
                user.getSecret(), user.getName(), new ArrayList<>(user.getPermissions().keySet()), dst.getChangeDate(),
                dst.getChangedBy());
    }

    @Override
    protected HistoricalObject<User> _to(InternalHistoricalUser src) {
        Map<String, Permission> userPermissions = src.getPermissionNames() == null ?
                new HashMap<String, Permission>() : groupsFactory.getPermissionsByName(src.getPermissionNames());
        User u = new User(src.getUsername(), src.getPassword(), groupsFactory.getGroups(src.getLevel()),
                src.isInternal(), src.getSalt(), src.getPasswordChangeDate(), src.isForcePasswordChange(),
                src.isUseTwoFactorAuthentication(), src.getSecret(), src.getName(), userPermissions);
        return new HistoricalObject<>(src.getChangeId(), u, src.getChangeDate(), src.getChangedBy());
    }

}
