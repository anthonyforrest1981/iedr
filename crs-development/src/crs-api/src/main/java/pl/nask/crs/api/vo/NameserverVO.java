package pl.nask.crs.api.vo;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.domains.nameservers.Nameserver;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class NameserverVO {

    private String name;

    private /*>>>@Nullable*/ String ipv4Address;

    private /*>>>@Nullable*/ String ipv6Address;

    @SuppressWarnings("nullness")
    public NameserverVO() {}

    public NameserverVO(String name, /*>>>@Nullable*/ String ipv4Address, /*>>>@Nullable*/ String ipv6Address) {
        this.name = name;
        this.ipv4Address = ipv4Address;
        this.ipv6Address = ipv6Address;
    }

    public NameserverVO(Nameserver ns) {
        this.name = ns.getName();
        this.ipv4Address = ns.getIpv4Address();
        this.ipv6Address = ns.getIpv6Address();
    }

    /*>>>@Pure*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ String getIpv4Address() {
        return ipv4Address;
    }

    public void setIpv4Address(/*>>>@Nullable*/ String ipv4Address) {
        this.ipv4Address = ipv4Address;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ String getIpv6Address() {
        return ipv6Address;
    }

    public void setIpv6Address(/*>>>@Nullable*/ String ipv6Address) {
        this.ipv6Address = ipv6Address;
    }
}
