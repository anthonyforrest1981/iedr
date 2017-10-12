package pl.nask.crs.domains.exceptions;

/**
 * @author Kasia Fulara
 * @author Artur Gniadzik
 */
public class DomainNotFoundException extends Exception {

    private String domainName;

    public DomainNotFoundException(String domainName) {
        super(domainName);
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }
}
