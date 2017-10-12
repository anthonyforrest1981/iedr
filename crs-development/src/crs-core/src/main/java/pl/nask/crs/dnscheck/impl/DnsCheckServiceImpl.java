package pl.nask.crs.dnscheck.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.IDN;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import pl.nask.crs.commons.dns.DomainNameValidator;
import pl.nask.crs.commons.dns.NameserverValidator;
import pl.nask.crs.commons.dns.exceptions.*;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.dnscheck.DnsCheckService;
import pl.nask.crs.dnscheck.email.DnsErrorEmailParameters;
import pl.nask.crs.dnscheck.exceptions.DnsCheckProcessingException;
import pl.nask.crs.dnscheck.exceptions.HostNotConfiguredException;
import pl.nask.crs.domains.nameservers.Nameserver;

import static pl.nask.crs.commons.utils.Validator.isEmpty;

public class DnsCheckServiceImpl implements DnsCheckService {
    private final static Logger LOG = Logger.getLogger(DnsCheckServiceImpl.class);
    private static final String CKDNS_IPV4_OPT = "-4";
    private static final String CKDNS_IPV6_OPT = "-6";
    private String checkDnsExecPath;
    private EmailTemplateSender emailTemplateSender;

    private class NsMessageValidationException extends Exception {
        private String fatalMessage;
        private String fullOutput;

        NsMessageValidationException(String fatalMessage, String fullOutput) {
            this.fatalMessage = fatalMessage;
            this.fullOutput = fullOutput;
        }
    }

    public DnsCheckServiceImpl(String checkDnsExecPath, EmailTemplateSender emailTemplateSender) {
        Validator.assertNotNull(checkDnsExecPath, "checkDns exec path");
        Validator.assertNotNull(emailTemplateSender, "emailTemplateSender");
        this.checkDnsExecPath = checkDnsExecPath;
        this.emailTemplateSender = emailTemplateSender;
    }

    @Override
    public void check(List<String> domainNames, List<Nameserver> nameservers, String username, boolean sendError)
            throws DnsCheckProcessingException, HostNotConfiguredException {
        List<DnsCheckInvocation> ckdnsInvocations = new ArrayList<>();
        List<DnsCheckError> errors = new ArrayList<>();
        for (String domainName : domainNames) {
            for (Nameserver nameserver : nameservers) {
                try {
                    DomainNameValidator.validateName(domainName);
                    NameserverValidator.checkNameserver(domainName, nameserver);
                    String punycodeNsName = IDN.toASCII(nameserver.getName()).toLowerCase();
                    String punycodeDomainName = IDN.toASCII(domainName).toLowerCase();
                    String nsName = nameserver.getName();
                    String ipv4Addr = nameserver.getIpv4Address();
                    String ipv6Addr = nameserver.getIpv6Address();
                    Validator.assertNotEmpty(nsName, "nsName");
                    if (isGlueRecord(nameserver)) {
                        if (!isEmpty(ipv4Addr)) {
                            ckdnsInvocations
                                    .add(new DnsCheckInvocation(domainName, nameserver, DnsCheckIdType.GLUE_IPv4,
                                            new ProcessBuilder(checkDnsExecPath, CKDNS_IPV4_OPT, ipv4Addr,
                                                    punycodeDomainName)));
                        }
                        if (!isEmpty(ipv6Addr)) {
                            ckdnsInvocations
                                    .add(new DnsCheckInvocation(domainName, nameserver, DnsCheckIdType.GLUE_IPv6,
                                            new ProcessBuilder(checkDnsExecPath, CKDNS_IPV6_OPT, ipv6Addr,
                                                    punycodeDomainName)));
                        }
                    } else {
                        ckdnsInvocations.add(new DnsCheckInvocation(domainName, nameserver, DnsCheckIdType.HOSTNAME,
                                new ProcessBuilder(checkDnsExecPath, punycodeNsName, punycodeDomainName)));
                    }
                } catch (InvalidDomainNameException e) {
                    String errorMessage = "FATAL Invalid domain name";
                    errors.add(
                            new DnsCheckError(domainName, nameserver.getName(), DnsCheckIdType.HOSTNAME, errorMessage,
                                    errorMessage));
                } catch (NameserverNameSyntaxException e) {
                    String errorMessage = "FATAL Invalid nameserver name";
                    errors.add(
                            new DnsCheckError(domainName, nameserver.getName(), DnsCheckIdType.HOSTNAME, errorMessage,
                                    errorMessage));
                } catch (GlueNotAllowedException e) {
                    String errorMessage = "FATAL Glue not allowed";
                    errors.add(
                            new DnsCheckError(domainName, nameserver.getName(), DnsCheckIdType.GLUE_IPv4, errorMessage,
                                    errorMessage));
                } catch (GlueRequiredException e) {
                    String errorMessage = "FATAL Glue required";
                    errors.add(
                            new DnsCheckError(domainName, nameserver.getName(), DnsCheckIdType.GLUE_IPv4, errorMessage,
                                    errorMessage));
                } catch (InvalidIPv6AddressException e) {
                    String errorMessage = "FATAL Invalid IPv6 address";
                    errors.add(
                            new DnsCheckError(domainName, nameserver.getName(), DnsCheckIdType.GLUE_IPv6, errorMessage,
                                    errorMessage));
                } catch (InvalidIPv4AddressException e) {
                    String errorMessage = "FATAL Invalid IPv4 address";
                    errors.add(
                            new DnsCheckError(domainName, nameserver.getName(), DnsCheckIdType.GLUE_IPv4, errorMessage,
                                    errorMessage));
                }
            }
        }

        try {

            for (DnsCheckInvocation ckdnsInvocation : ckdnsInvocations) {
                try {
                    Process process = ckdnsInvocation.getProcessBuilder().start();
                    ckdnsInvocation.setProcess(process);
                } catch (IOException e) {
                    if (sendError) {
                        sendError(username, ckdnsInvocation.getDomainName(), ckdnsInvocation.getNameserver().getName(),
                                e.toString());
                    }
                    throw new DnsCheckProcessingException(e);
                }
            }
            for (DnsCheckInvocation ckdnsInvocation : ckdnsInvocations) {
                try {
                    Process process = ckdnsInvocation.getProcess();
                    if (process.waitFor() != 0) {
                        StringBuilder errorMessage = getMessage(process.getErrorStream(), ckdnsInvocation);
                        if (sendError) {
                            sendError(username, ckdnsInvocation.getDomainName(),
                                    ckdnsInvocation.getNameserver().getName(), errorMessage.toString());
                        }
                        throw new DnsCheckProcessingException("Cannot run ckdnsExec with ns: "
                                + ckdnsInvocation.getNameserver().getName() + "domain: "
                                + ckdnsInvocation.getDomainName() + "because: " + errorMessage);
                    } else {
                        try {
                            validateNsMessage(ckdnsInvocation);
                        } catch (NsMessageValidationException e) {
                            errors.add(new DnsCheckError(ckdnsInvocation.getDomainName(), ckdnsInvocation
                                    .getNameserver().getName(), ckdnsInvocation.getCheckType(), e.fatalMessage,
                                    e.fullOutput));
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    if (sendError) {
                        sendError(username, ckdnsInvocation.getDomainName(), ckdnsInvocation.getNameserver().getName(),
                                e.toString());
                    }
                    throw new DnsCheckProcessingException(e);
                }
            }
        } finally {
            for (DnsCheckInvocation ckdnsInvocation : ckdnsInvocations) {
                Process process = ckdnsInvocation.getProcess();
                if (process != null) {
                    IOUtils.closeQuietly(process.getInputStream());
                    IOUtils.closeQuietly(process.getOutputStream());
                    IOUtils.closeQuietly(process.getErrorStream());
                }
            }
        }
        if (!errors.isEmpty()) {
            throw new HostNotConfiguredException(errors);
        }
    }

    private void sendError(String username, String domainName, String nsName, String errorMessage) {
        try {
            EmailParameters params = new DnsErrorEmailParameters(domainName, nsName, errorMessage, username, username);
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.DNS_CHECK_ERROR.getId(), params);
        } catch (Exception e) {
            LOG.error("Error sending dns failure email", e);
        }
    }

    private boolean isGlueRecord(Nameserver nameserver) {
        return !isEmpty(nameserver.getIpv4Address()) || !isEmpty(nameserver.getIpv6Address());
    }

    private StringBuilder getMessage(InputStream inputStream, DnsCheckInvocation ckdnsInvocation) throws IOException {
        String origDomainName = ckdnsInvocation.getDomainName();
        String ckdnsDomainName = IDN.toASCII(origDomainName).toLowerCase();

        String origNsName = ckdnsInvocation.getNameserver().getName();
        String ckdnsNsName = IDN.toASCII(origNsName).toLowerCase();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder ret = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (!ckdnsDomainName.equalsIgnoreCase(origDomainName)) {
                line = line.replace(ckdnsDomainName, origDomainName);
            }
            if (!ckdnsNsName.equalsIgnoreCase(origNsName)) {
                line = line.replace(ckdnsNsName, origNsName);
            }
            ret.append(line);
            ret.append("\n");
        }
        return ret;
    }

    private void validateNsMessage(DnsCheckInvocation ckdnsInvocation) throws NsMessageValidationException, IOException {
        StringBuilder sb = getMessage(ckdnsInvocation.getProcess().getInputStream(), ckdnsInvocation);
        int lastFatal = sb.lastIndexOf("FATAL");
        if (lastFatal != -1 && sb.indexOf("OK", lastFatal) == -1) {
            throw new NsMessageValidationException(getFirstFatalMessage(sb), sb.toString());
        }
    }

    private String getFirstFatalMessage(StringBuilder sb) {
        int firstFatal = sb.indexOf("FATAL");
        int endOfLineWithFatal = sb.indexOf("\n", firstFatal);
        return sb.substring(firstFatal + 6, endOfLineWithFatal);
    }

}
