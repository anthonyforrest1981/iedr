package pl.nask.crs.app.tickets;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.EntitySubcategory;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.IpFieldChange;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

public final class TicketConverter {

    public static NameserverChange toNameserverChange(Nameserver nameserver) {
        SimpleDomainFieldChange<String> nameChange = new SimpleDomainFieldChange<>(null, nameserver.getName());
        IpFieldChange ipv4Change = new IpFieldChange(null, nameserver.getIpv4Address());
        IpFieldChange ipv6Change = new IpFieldChange(null, nameserver.getIpv6Address());
        return new NameserverChange(nameChange, ipv4Change, ipv6Change);
    }

    public static List<NameserverChange> asNameserverChangeList(List<Nameserver> nameservers) {
        List<NameserverChange> nsChangeList = new ArrayList<>();
        for (Nameserver ns : nameservers) {
            nsChangeList.add(toNameserverChange(ns));
        }
        return nsChangeList;
    }

    public static DomainOperation asDomainOperation(DomainOperation.DomainOperationType type, Date renewalDate,
            String domainName, String domainHolder, EntityClass domainHolderClass, EntityCategory domainHolderCategory,
            /*>>>@Nullable*/ EntitySubcategory domainHolderSubcategory, long resellerAccountId,
            List<SimpleDomainFieldChange<Contact>> adminList, List<SimpleDomainFieldChange<Contact>> techList,
            List<SimpleDomainFieldChange<Contact>> billingList, List<NameserverChange> nsList) {

        return new DomainOperation(type, renewalDate, new SimpleDomainFieldChange<>(null, domainName),
                new SimpleDomainFieldChange<>(null, domainHolder),
                new SimpleDomainFieldChange<>(null, domainHolderClass),
                new SimpleDomainFieldChange<>(null, domainHolderCategory),
                new SimpleDomainFieldChange<>(null, domainHolderSubcategory),
                new SimpleDomainFieldChange<>(null, new Account(resellerAccountId)), adminList, techList, billingList,
                nsList);
    }
}
