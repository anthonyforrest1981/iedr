package pl.nask.crs.domains.nameservers;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import pl.nask.crs.commons.utils.Validator;

public class Nameserver {

    private String name;

    private /*>>>@Nullable*/ String ipv4Address;

    private /*>>>@Nullable*/ String ipv6Address;

    public Nameserver() {}

    public Nameserver(String name, /*>>>@Nullable*/ String ipv4Address, /*>>>@Nullable*/ String ipv6Address) {
        Validator.assertNotEmpty(name, "host name");
        this.name = name;
        if (!Validator.isEmpty(ipv4Address)) {
            this.ipv4Address = ipv4Address;
        }
        if (!Validator.isEmpty(ipv6Address)) {
            this.ipv6Address = ipv6Address;
        }
    }

    /*>>>@Pure*/
    public String getName() {
        return name;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ String getIpv4Address() {
        return ipv4Address;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ String getIpv6Address() {
        return ipv6Address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIpv4Address(String newIpv4Address) {
        if (Validator.isEmpty(newIpv4Address)) {
            this.ipv4Address = null;
        } else {
            this.ipv4Address = newIpv4Address;
        }
    }

    public void setIpv6Address(String newIpv6Address) {
        if (Validator.isEmpty(newIpv6Address)) {
            this.ipv6Address = null;
        } else {
            this.ipv6Address = newIpv6Address;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Nameserver))
            return false;

        Nameserver that = (Nameserver) o;

        if (ipv4Address != null ? !ipv4Address.equals(that.ipv4Address) : that.ipv4Address != null)
            return false;
        if (ipv6Address != null ? !ipv6Address.equals(that.ipv6Address) : that.ipv6Address != null)
            return false;
        if (!name.equals(that.name))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (ipv4Address != null ? ipv4Address.hashCode() : 0);
        result = 31 * result + (ipv6Address != null ? ipv6Address.hashCode() : 0);
        return result;
    }
}
