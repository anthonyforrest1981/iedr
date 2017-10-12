package pl.nask.crs.dnscheck.impl;

public enum DnsCheckIdType {
    GLUE_IPv4("Glue IPv4"), GLUE_IPv6("Glue IPv6"), HOSTNAME("Nameserver hostname");

    private String fullName;

    DnsCheckIdType(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

}
