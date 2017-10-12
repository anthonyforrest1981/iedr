package pl.nask.crs.accounts;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.Date;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;

public class Account extends PlainAccount {

    protected Contact billingContact;

    public Account(long id) {
        this(id, null, null);
    }

    public Account(long id, /*>>>@Nullable*/ String name, /*>>>@Nullable*/ String nicHandle) {
        this.id = id;
        this.name = name;
        this.billingNH = nicHandle;
        this.billingContact = (billingNH == null ? null : new Contact(billingNH));
    }

    public Account(long id, String name, Contact contact, AccountStatus status, String webAddress, String remark,
            Date creationDate, Date statusChangeDate, Date changeDate, boolean agreementSigned, boolean editTicket,
            Long segmentId) {
        Validator.assertNotNull(id, "id");
        if (!agreementSigned && ticketEdit)
            throw new IllegalArgumentException("ticketEdit cannot be set to true if agreementSigned is not set to true");
        this.agreementSigned = agreementSigned;
        this.ticketEdit = editTicket;
        this.billingNH = contact.getNicHandle();
        this.billingContact = contact;
        this.id = id;
        this.name = name;
        this.status = status;
        this.webAddress = webAddress;
        this.remark = remark;
        this.creationDate = creationDate;
        this.statusChangeDate = statusChangeDate;
        this.changeDate = changeDate;
        this.segmentId = segmentId;
    }

    public Account(String name, Contact contact, AccountStatus status, String webAddress, String remark,
            Date creationDate, Date statusChangeDate, Date changeDate, boolean agreementSigned, boolean editTicket) {
        this(-1, name, contact, status, webAddress, remark, creationDate, statusChangeDate, changeDate,
                agreementSigned, editTicket, null);
    }

    public Contact getBillingContact() {
        return billingContact;
    }

    public void setBillingContact(Contact billingContact) {
        this.billingNH = billingContact.getNicHandle();
        this.billingContact = billingContact;
    }

}
