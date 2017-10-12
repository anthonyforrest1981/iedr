package pl.nask.crs.ticket.operation;

/*>>>import org.checkerframework.dataflow.qual.Pure;*/

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.nameservers.Nameserver;

/**
 * @author Patrycja Wegrzynowicz
 */
public class NameserverChange implements DomainFieldChange {

    private SimpleDomainFieldChange<String> name;
    private IpFieldChange ipv4Address;
    private IpFieldChange ipv6Address;

    public NameserverChange(SimpleDomainFieldChange<String> name, IpFieldChange ipv4Address, IpFieldChange ipv6Address) {
        Validator.assertNotNull(name, "host name change");
        Validator.assertNotNull(ipv4Address, "ipv4 address change");
        Validator.assertNotNull(ipv6Address, "ipv6 address change");
        this.name = name;
        this.ipv4Address = ipv4Address;
        this.ipv6Address = ipv6Address;
    }

    /*>>>@Pure*/
    public SimpleDomainFieldChange<String> getName() {
        return name;
    }

    /*>>>@Pure*/
    public IpFieldChange getIpv4Address() {
        return ipv4Address;
    }

    /*>>>@Pure*/
    public IpFieldChange getIpv6Address() {
        return ipv6Address;
    }

    public void populateFailureReason(NameserverChange change) {
        name.populateFailureReason(change.getName());
        ipv4Address.setFailureReason(change.getIpv4Address().getFailureReason());
        ipv6Address.setFailureReason(change.getIpv6Address().getFailureReason());
    }

    public void populateValue(NameserverChange change) {
        name.populateValue(change.getName());
        ipv4Address.setNewValue(change.getIpv4Address().getNewValue());
        ipv6Address.setNewValue(change.getIpv6Address().getNewValue());
        nullEmptyValues();
    }

    private void nullEmptyValues() {
        if (Validator.isEmpty(name.getNewValue())) {
            name.setNewValue(null);
        }
        if (Validator.isEmpty(ipv4Address.getNewValue())) {
            ipv4Address.setNewValue(null);
        }
        if (Validator.isEmpty(ipv6Address.getNewValue())) {
            ipv6Address.setNewValue(null);
        }
    }

    public boolean isFailed() {
        return name.isFailed() || ipv4Address.isFailed() || ipv6Address.isFailed();
    }

    public boolean isModification() {
        return name.isModification() || ipv4Address.isModification() || ipv6Address.isModification();
    }

    public Nameserver getNewNameserver() {
        assert name.getNewValue() != null : "@AssumeAssertion(nullness)";
        return new Nameserver(name.getNewValue(), ipv4Address.getNewValue(), ipv6Address.getNewValue());
    }
}
