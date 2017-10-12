package pl.nask.crs.commons.dns.exceptions;


public class InvalidIPv4AddressException extends IpSyntaxException {

    public InvalidIPv4AddressException(String address) {
        super(address);
    }
}
