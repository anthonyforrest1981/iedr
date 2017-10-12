package com.iedr.bpr.tests.utils;

public class DomainNameServer {
    public String name;
    public String ipv4;
    public String ipv6;

    public DomainNameServer(String name, String ipv4, String ipv6) {
        assert (name != null);
        this.name = name;
        this.ipv4 = ipv4;
        this.ipv6 = ipv6;
    }

    public DomainNameServer(String name) {
        this(name, null, null);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (!(otherObject instanceof DomainNameServer)) {
            return false;
        }
        DomainNameServer other = (DomainNameServer) otherObject;
        boolean result = true;
        result = result && this.name.equals(other.name);
        if (this.ipv4 == null) {
            result = result && (other.ipv4 == null);
        } else {
            result = result && this.ipv4.equals(other.ipv4);
        }
        if (this.ipv6 == null) {
            result = result && (other.ipv6 == null);
        } else {
            result = result && this.ipv6.equals(other.ipv6);
        }
        return result;
    }

    @Override
    public String toString() {
        return "DomainNameServer{" +
                "name='" + name + '\'' +
                ", ipv4='" + ipv4 + '\'' +
                ", ipv6='" + ipv6 + '\'' +
                '}';
    }
}
