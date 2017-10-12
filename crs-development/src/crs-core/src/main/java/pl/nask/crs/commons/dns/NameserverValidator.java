package pl.nask.crs.commons.dns;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.ValidationHelper;
import pl.nask.crs.commons.dns.exceptions.*;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.ticket.operation.NameserverChange;

import static pl.nask.crs.commons.utils.Validator.isEmpty;

public class NameserverValidator {

    public static void checkNameserverNames(List<String> nameserverNames, ServicesRegistry registry)
            throws NameserverNameSyntaxException, DuplicatedNameserverException, TooManyNameserversException {
        if (nameserverNames == null) {
            return;
        }
        checkNameserverListMaxSize(nameserverNames.size(), registry);
        List<Nameserver> nameserversList = new ArrayList<>();
        for (String name : nameserverNames) {
            getValidatedNameserverName(name);
            nameserversList.add(new Nameserver(name, null, null));
        }
        checkForNSDuplications(nameserversList);
    }

    public static void checkNameserverChanges(String domainName, List<NameserverChange> nsList,
            ServicesRegistry registry)
            throws TooFewNameserversException, TooManyNameserversException, DuplicatedNameserverException,
            NameserverNameSyntaxException, IpSyntaxException, GlueNotAllowedException, GlueRequiredException,
            InvalidDomainNameException {
        List<Nameserver> nameservers = new ArrayList<>(nsList.size());
        for (NameserverChange chng : nsList) {
            final String name = chng.getName().getNewValue();
            final String ipv4 = chng.getIpv4Address().getNewValue();
            final String ipv6 = chng.getIpv6Address().getNewValue();
            if (name != null) {
                nameservers.add(new Nameserver(name, ipv4, ipv6));
            }
        }
        checkNameservers(domainName, nameservers, registry);
    }

    public static void checkNameservers(String domainName, List<Nameserver> nsList, ServicesRegistry registry)
            throws TooFewNameserversException, TooManyNameserversException, DuplicatedNameserverException,
            NameserverNameSyntaxException, IpSyntaxException, GlueNotAllowedException, GlueRequiredException,
            InvalidDomainNameException {
        checkNameserverListSize(nsList.size(), registry);
        checkForNSDuplications(nsList);
        String canonicalDomainName = getValidatedName(domainName);
        for (Nameserver ns : nsList) {
            checkNameserverWithCanonicalDomainName(canonicalDomainName, ns);
        }
    }

    public static void checkNameserver(String domainName, Nameserver nameserver)
            throws InvalidDomainNameException, InvalidIPv4AddressException, GlueRequiredException,
            GlueNotAllowedException, InvalidIPv6AddressException, NameserverNameSyntaxException {
        checkNameserverWithCanonicalDomainName(getValidatedName(domainName), nameserver);
    }

    private static void checkForNSDuplications(List<Nameserver> nsList) throws DuplicatedNameserverException {
        Set<String> nsSet = new HashSet<>();
        for (Nameserver ns : nsList) {
            try {
                String asciiLowercase = getValidatedName(ns.getName());
                if (!nsSet.add(asciiLowercase))
                    throw new DuplicatedNameserverException(ns.getName());
            } catch (InvalidDomainNameException e) {
                Logger.getLogger(ValidationHelper.class).warn(
                        "IDN.toAscii failed while checking for duplicates among nameservers", e);
            }
        }
    }

    private static void checkNameserverListSize(int size, ServicesRegistry registry)
            throws TooFewNameserversException, TooManyNameserversException {
        checkNameserverListMinSize(size, registry);
        checkNameserverListMaxSize(size, registry);
    }

    private static void checkNameserverListMinSize(int size, ServicesRegistry registry)
            throws TooFewNameserversException {
        int min = registry.getApplicationConfig().getNameserverMinCount();
        if (size < min) {
            throw new TooFewNameserversException(min);
        }
    }

    private static void checkNameserverListMaxSize(int size, ServicesRegistry registry)
            throws TooManyNameserversException {
        int max = registry.getApplicationConfig().getNameserverMaxCount();
        if (max >= 0 && size > max) {
            throw new TooManyNameserversException(max);
        }
    }

    private static void checkNameserverWithCanonicalDomainName(String domainName, Nameserver nameserver)
            throws GlueNotAllowedException, GlueRequiredException, NameserverNameSyntaxException,
            InvalidIPv4AddressException, InvalidIPv6AddressException {
        String name = getValidatedNameserverName(nameserver.getName());

        // Glue if needed?
        boolean glueDefined = !(isEmpty(nameserver.getIpv4Address()) && isEmpty(nameserver.getIpv6Address()));
        boolean glueRequired = name.equalsIgnoreCase(domainName) || name.endsWith("." + domainName);
        if (glueDefined && !glueRequired) {
            throw new GlueNotAllowedException(name);
        }
        if (!glueDefined && glueRequired) {
            throw new GlueRequiredException(name);
        }

        // Are ip addresses OK?
        if (!isEmpty(nameserver.getIpv4Address())) {
            IPAddressValidator.validateIPv4(nameserver.getIpv4Address());
        }
        if (!isEmpty(nameserver.getIpv6Address())) {
            IPAddressValidator.validateIPv6(nameserver.getIpv6Address());
        }
    }

    private static String getValidatedNameserverName(String name) throws NameserverNameSyntaxException {
        try {
            return getValidatedName(name);
        } catch (InvalidDomainNameException e) {
            throw new NameserverNameSyntaxException(name);
        }
    }

    private static String getValidatedName(String name) throws InvalidDomainNameException {
        return DomainNameValidator.getValidatedPunycodeName(name);
    }
}
