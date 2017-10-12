package pl.nask.crs.price;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class PriceNotFoundException extends Exception {

    private int id;

    public PriceNotFoundException(int id) {
        super("Price not found");
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
