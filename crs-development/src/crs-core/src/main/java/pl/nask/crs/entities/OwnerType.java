package pl.nask.crs.entities;

public class OwnerType {

    private long id;
    private String name;
    private long classId;
    private long categoryId;
    private boolean charity;

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

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isCharity() {
        return charity;
    }

    public void setCharity(boolean charity) {
        this.charity = charity;
    }

}
