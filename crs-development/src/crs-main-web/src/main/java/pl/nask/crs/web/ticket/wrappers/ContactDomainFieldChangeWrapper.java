package pl.nask.crs.web.ticket.wrappers;

import pl.nask.crs.contacts.Contact;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

public class ContactDomainFieldChangeWrapper extends SimpleDomainFieldChangeWrapper<Contact> {

    public ContactDomainFieldChangeWrapper(SimpleDomainFieldChange<Contact> orig, DomainOperationType type) {
        super(orig, type);
    }

    public String getNicHandle() {
        Contact contact = getNewValue();
        return contact == null ? null : contact.getNicHandle();
    }

    public void setNicHandle(String nicHandle) {
        if (nicHandle == null || nicHandle.trim().length() == 0) {
            setNewValue(null);
        } else {
            Contact newContact = new Contact(nicHandle);
            if (!newContact.equals(getNewValue())) {
                setNewValue(newContact);
            }
        }
    }

}
