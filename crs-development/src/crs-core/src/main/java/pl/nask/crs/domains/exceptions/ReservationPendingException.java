package pl.nask.crs.domains.exceptions;

public class ReservationPendingException extends Exception {

    private String domainName;

    public ReservationPendingException(String domainName, String errorMessage) {
        super(errorMessage);
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }

}
