package pl.nask.crs.dnscheck.impl;

import pl.nask.crs.domains.nameservers.Nameserver;

public class DnsCheckInvocation {
    private final String domainName;
    private final Nameserver nameserver;
    private final DnsCheckIdType checkType;
    private final ProcessBuilder processBuilder;
    private Process process;

    public DnsCheckInvocation(String domainName, Nameserver nameserver, DnsCheckIdType checkType,
            ProcessBuilder processBuilder) {
        this.domainName = domainName;
        this.nameserver = nameserver;
        this.checkType = checkType;
        this.processBuilder = processBuilder;
    }

    public String getDomainName() {
        return domainName;
    }

    public Nameserver getNameserver() {
        return nameserver;
    }

    public ProcessBuilder getProcessBuilder() {
        return processBuilder;
    }

    public DnsCheckIdType getCheckType() {
        return checkType;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }
}
