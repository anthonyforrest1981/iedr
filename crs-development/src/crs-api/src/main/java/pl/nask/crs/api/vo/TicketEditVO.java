package pl.nask.crs.api.vo;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

import pl.nask.crs.ticket.operation.IpFieldChange;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;
import pl.nask.crs.ticket.operation.TicketEdit;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class TicketEditVO {

    @XmlElement(required = true)
    private String domainNameField;

    @XmlElement(required = true)
    private String domainHolderField;

    @XmlElement(required = true)
    private Long resellerAccountField;

    @XmlElement(required = true)
    private List<String> adminContactsField;

    @XmlElement(required = true)
    private List<String> techContactsField;

    @XmlElement(required = true)
    private List<String> billingContactsField;

    @XmlElement(required = true)
    private List<NameserverVO> nameservers;

    @SuppressWarnings("nullness")
    public TicketEditVO() {}

    public TicketEdit toTicketEdit() {
        return new TicketEdit(new SimpleDomainFieldChange<>(null, domainNameField),
                new SimpleDomainFieldChange<>(null, domainHolderField),
                new SimpleDomainFieldChange<Long>(null, null),
                new SimpleDomainFieldChange<Long>(null, null),
                new SimpleDomainFieldChange<Long>(null, null),
                new SimpleDomainFieldChange<>(null, resellerAccountField),
                convertToContacts(adminContactsField), convertToContacts(techContactsField),
                convertToContacts(billingContactsField), convertToNameservers(nameservers));
    }

    private List<NameserverChange> convertToNameservers(List<NameserverVO> nameservers) {
        List<NameserverChange> result = new ArrayList<>();
        for (NameserverVO nameserver : nameservers) {
            NameserverChange nameserverChange = new NameserverChange(
                    new SimpleDomainFieldChange<>(null, nameserver.getName()),
                    new IpFieldChange(null, nameserver.getIpv4Address()),
                    new IpFieldChange(null, nameserver.getIpv6Address()));
            result.add(nameserverChange);
        }
        return result;
    }

    private List<SimpleDomainFieldChange<String>> convertToContacts(List<String> contacts) {
        List<SimpleDomainFieldChange<String>> result = new ArrayList<>();
        for (String contact : contacts) {
            SimpleDomainFieldChange<String> contactChange = new SimpleDomainFieldChange<>(null, contact);
            result.add(contactChange);
        }
        return result;
    }

    public String getDomainNameField() {
        return domainNameField;
    }

    public String getDomainHolderField() {
        return domainHolderField;
    }

    public Long getResellerAccountField() {
        return resellerAccountField;
    }

    public List<String> getAdminContactsField() {
        return adminContactsField;
    }

    public List<String> getTechContactsField() {
        return techContactsField;
    }

    public List<String> getBillingContactsField() {
        return billingContactsField;
    }

    public List<NameserverVO> getNameservers() {
        return nameservers;
    }

}
