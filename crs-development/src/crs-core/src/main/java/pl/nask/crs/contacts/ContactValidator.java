package pl.nask.crs.contacts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.nask.crs.app.ServicesRegistry;
import pl.nask.crs.app.tickets.exceptions.ContactSyntaxException;
import pl.nask.crs.app.tickets.exceptions.DuplicatedContactException;
import pl.nask.crs.app.tickets.exceptions.TooFewContactsException;
import pl.nask.crs.app.tickets.exceptions.TooManyContactsException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.nichandle.exception.NicHandleException;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

public class ContactValidator {

    public static void checkTicketContacts(Ticket ticket, long accountId, ServicesRegistry registry)
            throws TooFewContactsException, TooManyContactsException, DuplicatedContactException,
            ContactSyntaxException, NicHandleException {
        checkTicketContacts(accountId, ticket.getOperation().getAdminContactsField(), 1, 2, ContactType.ADMIN, registry);
        checkTicketContacts(accountId, ticket.getOperation().getTechContactsField(), 1, 1, ContactType.TECH, registry);
    }

    public static void checkDomainContacts(Domain domain, long accountId, ServicesRegistry registry)
            throws TooFewContactsException, TooManyContactsException, DuplicatedContactException,
            ContactSyntaxException, NicHandleException {
        checkDomainContacts(accountId, domain.getBillingContacts(), 1, 1, ContactType.BILLING, registry);
        checkDomainContacts(accountId, domain.getAdminContacts(), 1, 2, ContactType.ADMIN, registry);
        checkDomainContacts(accountId, domain.getTechContacts(), 1, 1, ContactType.TECH, registry);
    }

    private static void checkTicketContacts(long accountId, List<SimpleDomainFieldChange<Contact>> contacts, int min,
            int max, ContactType type, ServicesRegistry registry)
            throws TooFewContactsException, TooManyContactsException, DuplicatedContactException,
            ContactSyntaxException, NicHandleException {
        // filter out empty contacts from the list!
        List<Contact> filteredContacts = new ArrayList<>();
        for (SimpleDomainFieldChange<Contact> contactChange : contacts) {
            if (contactChange.getNewValue() != null) {
                Contact contact = contactChange.getNewValue();
                if (!Validator.isEmpty(contact.getNicHandle())) {
                    filteredContacts.add(contact);
                }
            }
        }
        checkFilteredContacts(accountId, filteredContacts, min, max, type, registry);
    }

    private static void checkDomainContacts(long accountId, List<Contact> contacts, int min, int max, ContactType type,
            ServicesRegistry registry)
            throws TooFewContactsException, TooManyContactsException, DuplicatedContactException,
            ContactSyntaxException, NicHandleException {
        List<Contact> filteredContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact != null && !Validator.isEmpty(contact.getNicHandle())) {
                filteredContacts.add(contact);
            }
        }
        checkFilteredContacts(accountId, filteredContacts, min, max, type, registry);
    }

    private static void checkFilteredContacts(long accountId, List<Contact> contacts, int min, int max, ContactType type,
            ServicesRegistry registry)
            throws TooFewContactsException, TooManyContactsException, DuplicatedContactException,
            ContactSyntaxException, NicHandleException {
        checkDomainContactsSize(min, max, contacts.size(), type);

        Set<String> contactsSet = new HashSet<>();
        for (Contact contact : contacts) {
            String nicHandle = contact.getNicHandle();
            if (!contactsSet.add(nicHandle)) {
                throw new DuplicatedContactException(nicHandle);
            }
            registry.getNicHandleSearchService().confirmNicHandleActive(nicHandle);
            registry.getNicHandleSearchService().validateNicHandleWithAccount(nicHandle, accountId);
        }
    }

    private static void checkDomainContactsSize(int min, int max, int size, ContactType type)
            throws TooManyContactsException, TooFewContactsException {
        if (size < min) {
            throw new TooFewContactsException(type, min, size);
        }
        if (size > max) {
            throw new TooManyContactsException(type, min, size);
        }
    }

}
