package pl.nask.crs.entities;

import java.util.List;

public class EntityCategory {
    private long id;
    private String name;
    private List<EntitySubcategory> subcategories;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<EntitySubcategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<EntitySubcategory> subcategories) {
        this.subcategories = subcategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityCategory)) return false;

        EntityCategory that = (EntityCategory) o;

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
        return "EntityCategory{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
