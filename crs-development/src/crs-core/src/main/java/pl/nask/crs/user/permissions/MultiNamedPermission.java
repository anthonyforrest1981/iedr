package pl.nask.crs.user.permissions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.nask.crs.commons.utils.CollectionUtils;


public class MultiNamedPermission extends NamedPermission {

    protected Set<String> permissionNames = new HashSet<>();

    public MultiNamedPermission(String id, String name, List<String> methodNames) {
        super(id, name);
        for (String methodName : methodNames) {
            permissionNames.add(name + "." + methodName);
        }
    }

    public MultiNamedPermission(String id, List<String> methodNames) {
        super(id, id);
        permissionNames.addAll(methodNames);
    }

    @Override
    public String getDescription() {
        if (getClass() != MultiNamedPermission.class)
            return null;
        return "Non contextual, granting access to " + (permissionNames.size() > 1 ? ": " : "")
                + CollectionUtils.toString(getDescriptionsFromTheResourceBundle(permissionNames), ", ");
    }

    @Override
    public boolean implies(PermissionQuery query) throws PermissionDeniedException {
        return permissionNames.contains(query.getName());
    }

    @Override
    public String toString() {
        return "MultiNamed[id: " + getId() + ", names: " + permissionNames + "]";
    }
}
