package pl.nask.crs.user.service;

import java.util.List;

import pl.nask.crs.user.permissions.Permission;

/**
 * @author Kasia Fulara
 */
public interface PermissionsFactory {

    List<Permission> getPermissions();

}
