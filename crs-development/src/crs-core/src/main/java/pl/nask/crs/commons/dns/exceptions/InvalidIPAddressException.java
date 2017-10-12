package pl.nask.crs.commons.dns.exceptions;

/**
 * @author Patrycja Wegrzynowicz
 */
public class InvalidIPAddressException extends Exception {

    private String address;

    public InvalidIPAddressException(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
