package pl.nask.crs.domains;

import java.util.Date;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.EntitySubcategory;
import pl.nask.crs.payment.PaymentMethod;

public class ExtendedDomain extends Domain {

    private PaymentMethod pendingReservationPaymentMethod;

    public ExtendedDomain(String name, String holder, EntityClass holderClass, EntityCategory holderCategory,
            EntitySubcategory holderSubcategory, Contact creator, Account resellerAccount, Date registrationDate,
            Date renewalDate, String remark, Date changeDate, boolean clikPaid, List<Contact> techContacts,
            List<Contact> billingContacts, List<Contact> adminContacts, List<Nameserver> nameservers, DsmState dsmState,
            boolean zonePublished, Date lockingDate, Date lockingRenewalDate,
            PaymentMethod pendingReservationPaymentMethod) {
        super(name, holder, holderClass, holderCategory, holderSubcategory, creator, resellerAccount, registrationDate,
                renewalDate, remark, changeDate, clikPaid, techContacts, billingContacts, adminContacts, nameservers,
                dsmState, zonePublished, lockingDate, lockingRenewalDate);
        this.setPendingReservationPaymentMethod(pendingReservationPaymentMethod);
    }

    public PaymentMethod getPendingReservationPaymentMethod() {
        return pendingReservationPaymentMethod;
    }

    public void setPendingReservationPaymentMethod(PaymentMethod pendingReservationPaymentMethod) {
        this.pendingReservationPaymentMethod = pendingReservationPaymentMethod;
    }

    public boolean hasPendingReservations() {
        return pendingReservationPaymentMethod != null;
    }

}
