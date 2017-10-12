package pl.nask.crs.app.domains.wrappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import pl.nask.crs.domains.nameservers.Nameserver;

/**
 * Wrapper for the list of dns servers.
 *
 * @author Artur Gniadzik
 */
public class DnsWrapper {

    private static class NameserverStub {
        private String hostname;
        private String ipv4Addr;
        private String ipv6Addr;

        public NameserverStub(String hostname, String ipv4Addr, String ipv6Addr) {
            this.hostname = hostname;
            this.ipv4Addr = ipv4Addr;
            this.ipv6Addr = ipv6Addr;
        }
    }

    public static interface Setter {
        public static final Setter HOSTNAME = new Setter() {
            @Override
            public void set(NameserverStub object, String newValue) {
                object.hostname = newValue;
            }
        };

        public static final Setter IPV4 = new Setter() {
            @Override
            public void set(NameserverStub object, String newValue) {
                object.ipv4Addr = newValue;
            }
        };

        public static final Setter IPV6 = new Setter() {
            @Override
            public void set(NameserverStub object, String newValue) {
                object.ipv6Addr = newValue;
            }
        };

        void set(NameserverStub object, String newValue);
    }

    private final List<NameserverStub> currentNameservers;
    private final String domainName;

    public String getDomainName() {
        return domainName;
    }

    public DnsWrapper(List<Nameserver> initialNameservers, String domainName) {
        this.domainName = domainName;

        this.currentNameservers = new ArrayList<>(Lists.transform(initialNameservers,
                new Function<Nameserver, NameserverStub>() {
                    @Override
                    public NameserverStub apply(Nameserver nameserver) {
                        return new NameserverStub(nameserver.getName(), nameserver.getIpv4Address(), nameserver
                                .getIpv6Address());
                    }
                }));
    }

    public List<Nameserver> getNameservers() {
        return Lists.transform(currentNameservers, new Function<NameserverStub, Nameserver>() {
            @Override
            public Nameserver apply(NameserverStub nameserverStub) {
                return new Nameserver(nameserverStub.hostname, nameserverStub.ipv4Addr, nameserverStub.ipv6Addr);
            }
        });
    }

    public void setNewNames(String[] names) {
        setNameserverValues(names, Setter.HOSTNAME);
    }

    public void setNewIpv4s(String[] ips) {
        setNameserverValues(ips, Setter.IPV4);
    }

    public void setNewIpv6s(String[] ips) {
        setNameserverValues(ips, Setter.IPV6);
    }

    private void setNameserverValues(String[] newValues, Setter setter) {
        // first element of the list is artificial so indexing should be started from 1
        List<String> newIps = Arrays.asList(newValues).subList(1, newValues.length);

        adjustStubsToSize(newIps.size());
        for (int i = 0; i < newIps.size(); i++) {
            NameserverStub ns = currentNameservers.get(i);
            setter.set(ns, newIps.get(i));
        }
    }

    private void adjustStubsToSize(final int finalSize) {
        if (finalSize < currentNameservers.size()) {
            currentNameservers.subList(finalSize, currentNameservers.size()).clear();
        } else {
            for (int i = currentNameservers.size(); i < finalSize; i++) {
                currentNameservers.add(new NameserverStub(null, null, null));
            }
        }
    }

}
