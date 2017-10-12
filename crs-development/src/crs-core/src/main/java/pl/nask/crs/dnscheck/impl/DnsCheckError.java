package pl.nask.crs.dnscheck.impl;

public class DnsCheckError {

    private final String domainName;
    private final String nameserverName;
    private final DnsCheckIdType checkId;
    private final String fatalMessage;
    private final String fullOutputMessage;

    public DnsCheckError(String domainName, String nameserverName, DnsCheckIdType checkId, String fatalMessage,
            String fullOutputMessage) {
        this.domainName = domainName;
        this.nameserverName = nameserverName;
        this.checkId = checkId;
        this.fatalMessage = fatalMessage;
        this.fullOutputMessage = fullOutputMessage;
    }

    public DnsCheckIdType getCheckId() {
        return checkId;
    }

    public String getFatalMessage() {
        return fatalMessage;
    }

    public String getFullOutputMessage() {
        return fullOutputMessage;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getNameserverName() {
        return nameserverName;
    }
}
