package pl.nask.crs.entities.exceptions;

public class HolderClassNotExistException extends Exception {

    private static final long serialVersionUID = -5523051646268991575L;

    String holderClassName;

    public HolderClassNotExistException(String holderClassName) {
        super("Holder class does not exist: " + holderClassName);
        this.holderClassName = holderClassName;
    }

    public HolderClassNotExistException(Long id) {
        super("Holder class id does not exist: " + id);
    }

    public String getHolderClassName() {
        return holderClassName;
    }

}
