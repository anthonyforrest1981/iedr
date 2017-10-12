package pl.nask.crs.secondarymarket.exceptions;

public class SellRequestNotFoundException extends Exception {

    private Long id;

    public SellRequestNotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
