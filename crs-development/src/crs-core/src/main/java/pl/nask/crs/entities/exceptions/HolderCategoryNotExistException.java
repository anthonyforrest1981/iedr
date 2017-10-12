package pl.nask.crs.entities.exceptions;

/**
 * @author: Marcin Tkaczyk
 */
public class HolderCategoryNotExistException extends Exception {
    private static final long serialVersionUID = 739141510361395399L;

    String holderCategoryName;

    public HolderCategoryNotExistException(String holderCategoryName) {
        super("Holder category does not exist: " + holderCategoryName);
        this.holderCategoryName = holderCategoryName;
    }

    public HolderCategoryNotExistException(Long id) {
        super("Holder category id does not exist: " + id);
    }

    public String getHolderCategoryName() {
        return holderCategoryName;
    }
}
