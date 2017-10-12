package pl.nask.crs.ticket.dao.ibatis.objects;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/
/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.ticket.operation.IpFailureReason;

public class InternalTicketNameserver {

    private String name;
    private /*>>>@Nullable*/ FailureReason nameFailureReason;
    private /*>>>@Nullable*/ String ipv4;
    private /*>>>@Nullable*/ IpFailureReason ipv4FailureReason;
    private /*>>>@Nullable*/ String ipv6;
    private /*>>>@Nullable*/ IpFailureReason ipv6FailureReason;

    @SuppressWarnings("nullness")
    public InternalTicketNameserver() {}

    public InternalTicketNameserver(String name, /*>>>@Nullable*/ FailureReason nameFailureReason,
    /*>>>@Nullable*/ String ipv4, /*>>>@Nullable*/ IpFailureReason ipv4FailureReason,
    /*>>>@Nullable*/ String ipv6, /*>>>@Nullable*/ IpFailureReason ipv6FailureReason) {
        this.name = name;
        this.nameFailureReason = nameFailureReason;
        this.ipv4 = ipv4;
        this.ipv4FailureReason = ipv4FailureReason;
        this.ipv6 = ipv6;
        this.ipv6FailureReason = ipv6FailureReason;
    }

    /*>>>@Pure*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ FailureReason getNameFailureReason() {
        return nameFailureReason;
    }

    public void setNameFailureReason(/*>>>@Nullable*/ FailureReason nameFailureReason) {
        this.nameFailureReason = nameFailureReason;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ String getIpv4() {
        return ipv4;
    }

    public void setIpv4(/*>>>@Nullable*/ String ipv4) {
        this.ipv4 = ipv4;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ IpFailureReason getIpv4FailureReason() {
        return ipv4FailureReason;
    }

    public void setIpv4FailureReason(/*>>>@Nullable*/ IpFailureReason ipv4FailureReason) {
        this.ipv4FailureReason = ipv4FailureReason;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ String getIpv6() {
        return ipv6;
    }

    public void setIpv6(/*>>>@Nullable*/ String ipv6) {
        this.ipv6 = ipv6;
    }

    /*>>>@Pure*/
    public /*>>>@Nullable*/ IpFailureReason getIpv6FailureReason() {
        return ipv6FailureReason;
    }

    public void setIpv6FailureReason(/*>>>@Nullable*/ IpFailureReason ipv6FailureReason) {
        this.ipv6FailureReason = ipv6FailureReason;
    }
}
