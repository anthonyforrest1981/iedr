package pl.nask.crs.web.ticket.wrappers;

import pl.nask.crs.ticket.operation.*;

public class NameserverWrapper {

    private String name = "";
    private String ipv4 = "";
    private String ipv6 = "";
    private FailureReason nameFr;
    private IpFailureReason ipv4Fr;
    private IpFailureReason ipv6Fr;
    private boolean nameModified = true;
    private boolean ipv4Modified = true;
    private boolean ipv6Modified = true;

    public NameserverWrapper() {}

    public NameserverWrapper(NameserverChange ns) {
        final SimpleDomainFieldChange<String> nameChange = ns.getName();
        if (nameChange != null) {
            name = nameChange.getNewValue();
            nameFr = nameChange.getFailureReason();
            nameModified = nameChange.isModification();
        }
        final IpFieldChange ipv4Change = ns.getIpv4Address();
        if (ipv4Change != null) {
            ipv4 = ipv4Change.getNewValue();
            ipv4Fr = ipv4Change.getFailureReason();
            ipv4Modified = ipv4Change.isModification();
        }
        final IpFieldChange ipv6Change = ns.getIpv6Address();
        if (ipv6Change != null) {
            ipv6 = ipv6Change.getNewValue();
            ipv6Fr = ipv6Change.getFailureReason();
            ipv6Modified = ipv6Change.isModification();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public String getIpv6() {
        return ipv6;
    }

    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
    }

    public FailureReason getNameFr() {
        return nameFr;
    }

    public void setNameFr(FailureReason nameFr) {
        this.nameFr = nameFr;
    }

    public IpFailureReason getIpv4Fr() {
        return ipv4Fr;
    }

    public void setIpv4Fr(IpFailureReason ipv4Fr) {
        this.ipv4Fr = ipv4Fr;
    }

    public IpFailureReason getIpv6Fr() {
        return ipv6Fr;
    }

    public void setIpv6Fr(IpFailureReason ipv6Fr) {
        this.ipv6Fr = ipv6Fr;
    }

    public boolean isNameModification() {
        return nameModified;
    }

    public boolean isIpv4Modification() {
        return ipv4Modified;
    }

    public boolean isIpv6Modification() {
        return ipv6Modified;
    }
}
