package pl.nask.crs.user.service.impl;

import java.util.List;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.user.permissions.Permission;
import pl.nask.crs.user.service.PermissionsFactory;

/**
 * @author Kasia Fulara
 */
public class PermissionsFactoryImpl implements PermissionsFactory {

    private List<Permission> permissions;

    public PermissionsFactoryImpl(List<Permission> permissions) {
        Validator.assertNotNull(permissions, "permissions");
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

}
