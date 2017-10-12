package pl.nask.crs.iedrapi.impl.ticket;

/*>>>import org.checkerframework.checker.initialization.qual.Initialized;*/
/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.math.BigInteger;
import java.util.List;

import pl.nask.crs.contacts.Contact;
import pl.nask.crs.iedrapi.*;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.impl.TypeConverter;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_ticket_1.*;
import ie.domainregistry.ieapicom_1.ContactAttrType;

import static pl.nask.crs.commons.utils.Validator.isEmpty;

public class TicketInfoCommandHandler extends AbstractTicketCommandHandler implements APICommandHandler<SNameType> {

    @Override
    public ResponseType handle(AuthData auth, SNameType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        ApiValidator.assertNoError(callback);
        AuthenticatedUser user = auth.getUser();
        boolean fillIpv6 = TypeConverter.commandFieldToBoolean(command.isFillNsAddr6());
        Ticket res = getTicketForDomain(user, command.getName());
        checkTicket(user, res, command.getName());

        DomainOperation op = res.getOperation();
        assert op.getResellerAccountField().getNewValue() != null : "@AssumeAssertion(nullness)";
        long resellerId = op.getResellerAccountField().getNewValue().getId();
        InfDataType idt = new InfDataType();
        idt.setAccount(BigInteger.valueOf(resellerId));

        idt.setDnsStatus(res.getTechStatus().getDescription());

        HolderType ht = new HolderType();
        ht.setHolderName(op.getDomainHolderField().getNewValue());
        ht.setHolderRemarks(res.getRequestersRemark());
        idt.setHolder(ht);

        idt.setHostmasterRemarks(res.getHostmastersRemark());
        idt.setHostmasterStatus(res.getAdminStatus().getDescription());
        idt.setName(op.getDomainNameField().getNewValue());
        idt.setRegDate(res.getCreationDate());
        idt.setRenDate(op.getRenewalDate());
        idt.setType(op.getType().getFullName());
        addContacts(idt, op.getAdminContactsField(), ContactAttrType.ADMIN);
        addContacts(idt, op.getTechContactsField(), ContactAttrType.TECH);

        // add nameservers
        for (NameserverChange ns : op.getNameserversField()) {
            final String name = ns.getName().getNewValue();
            final String ipv4 = ns.getIpv4Address().getNewValue();
            final String ipv6 = ns.getIpv6Address().getNewValue();
            if (!fillIpv6 && isEmpty(ipv4) && !isEmpty(ipv6)) {
                // Skip nameservers that only have ipv6 glue if fillIpv6 is not set
                continue;
            }
            if (!isEmpty(name)) {
                NsType nst = new NsType();
                nst.setNsName(name);
                if (!isEmpty(ipv4)) {
                    nst.getNsAddr().add(ipv4);
                }
                if (fillIpv6 && !isEmpty(ipv6)) {
                    nst.setNsAddr6(ipv6);
                }
                idt.getNs().add(nst);
            }
        }
        return ResponseTypeFactory.success(idt);
    }

    private void addContacts(InfDataType idt, List<SimpleDomainFieldChange<Contact>> contacts, ContactAttrType type) {
        for (SimpleDomainFieldChange<Contact> sdf : contacts) {
            final Contact contact = sdf.getNewValue();
            if (contact != null) {
                final String nicHandle = contact.getNicHandle();
                if (!isEmpty(nicHandle)) {
                    ContactType e = new ContactType();
                    e.setType(type);
                    e.setValue(nicHandle);
                    idt.getContact().add(e);
                }
            }
        }
    }

}
