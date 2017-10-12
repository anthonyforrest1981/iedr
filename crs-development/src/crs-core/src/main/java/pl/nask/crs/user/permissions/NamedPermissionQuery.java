package pl.nask.crs.user.permissions;

public class NamedPermissionQuery implements PermissionQuery {

    private String name;

    public NamedPermissionQuery(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
