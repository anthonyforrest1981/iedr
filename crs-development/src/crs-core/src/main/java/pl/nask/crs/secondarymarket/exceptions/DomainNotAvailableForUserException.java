package pl.nask.crs.secondarymarket.exceptions;

public class DomainNotAvailableForUserException extends Exception {

    private String domainName;
    private String creatorNH;

    public DomainNotAvailableForUserException(String domainName, String creatorNH) {
        this.domainName = domainName;
        this.creatorNH = creatorNH;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getCreatorNH() {
        return creatorNH;
    }

}
