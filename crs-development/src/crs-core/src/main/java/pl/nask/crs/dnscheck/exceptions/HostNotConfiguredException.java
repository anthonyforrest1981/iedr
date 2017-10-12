package pl.nask.crs.dnscheck.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import pl.nask.crs.dnscheck.impl.DnsCheckError;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class HostNotConfiguredException extends Exception {

    private final List<DnsCheckError> errors;

    public HostNotConfiguredException(List<DnsCheckError> errors) {
        this.errors = new ArrayList<>(errors);
    }

    public String getMessage() {
        List<String> nameList = getNameserverNames();
        String names = Joiner.on(", ").join(nameList);
        return "Host" + ((nameList.size() == 1) ? " " : "s: ") + names + " not configured";
    }

    public List<String> getNameserverNames() {
        List<String> names = Lists.transform(errors, new Function<DnsCheckError, String>() {
            public String apply(DnsCheckError error) {
                return error.getNameserverName();
            }
        });
        return names;
    }

    public String getFatalMessage() {
        Iterable<String> fatalMessages = Iterables.transform(errors, new Function<DnsCheckError, String>() {
            @Override
            public String apply(DnsCheckError errorInfo) {
                return getCheckHeader(errorInfo) + errorInfo.getFatalMessage();
            }
        });
        return Joiner.on("\n").join(fatalMessages);
    }

    public String getFullOutputMessage() {
        return getFullOutputMessage(false);
    }

    public String getFullOutputMessage(final boolean onlyFatalLines) {
        Iterable<String> fatalMessages = Iterables.transform(errors, new Function<DnsCheckError, String>() {
            @Override
            public String apply(DnsCheckError dnsCheckError) {
                return getFullOutput(dnsCheckError, onlyFatalLines, true);
            }
        });
        return Joiner.on("\n").join(fatalMessages);
    }

    public String getSingleOutputMessage(final String domainName, final String nsName, final boolean onlyFatalLines) {
        Iterable<DnsCheckError> filteredErrors = Iterables.filter(errors, new Predicate<DnsCheckError>() {
            @Override
            public boolean apply(DnsCheckError error) {
                return (error.getDomainName().equals(domainName) && error.getNameserverName().equals(nsName));
            }
        });
        Iterable<String> fatalMessages = Iterables.transform(filteredErrors, new Function<DnsCheckError, String>() {
            @Override
            public String apply(DnsCheckError dnsCheckError) {
                return getFullOutput(dnsCheckError, onlyFatalLines, false);
            }
        });
        return Joiner.on("\n").join(fatalMessages);
    }

    private String getFullOutput(DnsCheckError errorInfo, boolean includeOnlyFatalLines, boolean addCheckTypeHeader) {
        StringBuilder result = new StringBuilder();
        if (addCheckTypeHeader) {
            result.append(getCheckHeader(errorInfo));
        }
        if (!includeOnlyFatalLines) {
            result.append(errorInfo.getFullOutputMessage());
        } else {
            String[] lines = errorInfo.getFullOutputMessage().split("\n");
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                if (line.startsWith("FATAL"))
                    result.append(line).append("\n");
            }
        }
        return result.toString();
    }

    private String getCheckHeader(DnsCheckError errorInfo) {
        return "While checking " + errorInfo.getCheckId().getFullName() + " for nameserver: "
                + errorInfo.getNameserverName() + " for domain: " + errorInfo.getDomainName() + "\n";
    }
}
