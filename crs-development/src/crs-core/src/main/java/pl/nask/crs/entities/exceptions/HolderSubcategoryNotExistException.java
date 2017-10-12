package pl.nask.crs.entities.exceptions;

public class HolderSubcategoryNotExistException extends Exception {

    public HolderSubcategoryNotExistException(Long id) {
        super("Holder subcategory id does not exist: " + id);
    }

}
