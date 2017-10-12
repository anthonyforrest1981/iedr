package pl.nask.crs.entities.exceptions;

public class OwnerTypeNotExistException extends Exception {

    private String ownerType;

    public OwnerTypeNotExistException(String ownerType) {
        super("Owner type does not exist: " + ownerType);
        this.ownerType = ownerType;
    }

    public OwnerTypeNotExistException(Long ownerTypeId) {
        super("Owner type id does not exist: " + ownerTypeId);
    }

    public String getOwnerType() {
        return ownerType;
    }

}
