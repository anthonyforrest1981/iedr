package pl.nask.crs.country;

public class County {

    private String name;
    private int id;

    public final static int NOT_SPECIFIED = 0;

    public County() {}

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public boolean isSpecified() {
        return id != NOT_SPECIFIED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof County)) return false;

        County county = (County) o;

        if (getId() != county.getId()) return false;
        return getName() != null ? getName().equals(county.getName()) : county.getName() == null;

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + getId();
        return result;
    }
}
