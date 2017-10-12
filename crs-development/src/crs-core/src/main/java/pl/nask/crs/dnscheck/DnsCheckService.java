package pl.nask.crs.dnscheck;

import java.util.List;

import pl.nask.crs.dnscheck.exceptions.DnsCheckProcessingException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.nameservers.Nameserver;

public interface DnsCheckService {

    void check(List<String> domainNames, List<Nameserver> nameservers, String username, boolean sendError)
            throws DnsCheckProcessingException, HostNotConfiguredException;

}
