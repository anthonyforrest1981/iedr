package pl.nask.crs.domains.dsm;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DomainIllegalStateException extends Exception {

    private final String domainName;

    public DomainIllegalStateException(String msg, String domain) {
        super(msg);
        this.domainName = domain;
    }

    public String getDomainName() {
        return domainName;
    }
}
