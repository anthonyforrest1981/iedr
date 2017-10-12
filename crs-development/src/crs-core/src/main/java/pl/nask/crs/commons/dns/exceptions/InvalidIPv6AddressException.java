package pl.nask.crs.commons.dns.exceptions;


public class InvalidIPv6AddressException extends IpSyntaxException {

    public InvalidIPv6AddressException(String address) {
        super(address);
    }
}
