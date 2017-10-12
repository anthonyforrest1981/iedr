package pl.nask.crs.dnscheck.impl;

import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.dnscheck.DnsCheckService;
import pl.nask.crs.dnscheck.email.DnsErrorEmailParameters;
import pl.nask.crs.dnscheck.exceptions.DnsCheckProcessingException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.nameservers.Nameserver;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DnsCheckTestService implements DnsCheckService {

    private EmailTemplateSender emailTemplateSender;

    public DnsCheckTestService(EmailTemplateSender emailTemplateSender) {
        this.emailTemplateSender = emailTemplateSender;
    }

    /**
     * will throw HostNotConfiguredException if the nsName starts with 'bad'
     */
    @Override
    public void check(List<String> domainNames, List<Nameserver> nameservers, String username, boolean sendError)
            throws DnsCheckProcessingException, HostNotConfiguredException {
        List<DnsCheckError> errors = new ArrayList<>();
        for (String domainName : domainNames) {
            for (Nameserver nameserver : nameservers) {
                String nsName = nameserver.getName();
                if (nsName != null && nsName.startsWith("bad")) {
                    DnsCheckError error = new DnsCheckError(domainName, nsName, DnsCheckIdType.GLUE_IPv4,
                            "Starts with 'bad', so it's bad",
                            "OK First line is OK\nFATAL message for starts with 'bad', so it's bad");
                    errors.add(error);
                }
                if (nsName != null && nsName.startsWith("error")) {
                    String errMsg = "Starts with 'error', so it`s error";
                    if (sendError) {
                        sendError(username, domainName, nsName, errMsg);
                    }
                    throw new DnsCheckProcessingException(errMsg);
                }
            }
        }
        if (!errors.isEmpty()) {
            throw new HostNotConfiguredException(errors);
        }
    }

    private void sendError(String username, String domainName, String nsName, String errorMessage) {
        try {
            String nicHandle = "AA11-IEDR";
            EmailParameters params = new DnsErrorEmailParameters(domainName, nsName, errorMessage, username, nicHandle);
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.DNS_CHECK_ERROR.getId(), params);
        } catch (Exception e) {
            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
