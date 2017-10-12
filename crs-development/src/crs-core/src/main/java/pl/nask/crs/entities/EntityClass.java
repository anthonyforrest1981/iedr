package pl.nask.crs.entities;

import java.util.List;

public class EntityClass {

    private long id;
    private String name;
    private List<EntityCategory> categories;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EntityCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<EntityCategory> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityClass)) return false;

        EntityClass that = (EntityClass) o;

        if (id != that.id) return false;
        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "EntityClass{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
