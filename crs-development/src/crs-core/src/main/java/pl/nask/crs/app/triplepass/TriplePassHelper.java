package pl.nask.crs.app.triplepass;

import java.util.ArrayList;
import java.util.List;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.ticket.*;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public final class TriplePassHelper {

    private TriplePassHelper() {}

    public static boolean needsFinancialCheck(Ticket t) {
        return !isCanceled(t) && isAdminPassed(t) && isTechPassed(t) && !isFinancialPassed(t)
                && !isModificationTicket(t);
    }

    public static boolean needsDnsCheck(Ticket t) {
        return !isCanceled(t) && isAdminPassed(t) && !isTechPassed(t);
    }

    public static boolean isRegistrationTicket(Ticket t) {
        return DomainOperation.DomainOperationType.REG == t.getOperation().getType();
    }

    public static boolean isTransferTicket(Ticket t) {
        return DomainOperation.DomainOperationType.XFER == t.getOperation().getType();
    }

    public static boolean isModificationTicket(Ticket t) {
        return DomainOperation.DomainOperationType.MOD == t.getOperation().getType();
    }

    public static boolean isFullPassed(Ticket t) {
        if (isModificationTicket(t)) {
            return isAdminPassed(t) && isTechPassed(t);
        } else {
            return isAdminPassed(t) && isTechPassed(t) && isFinancialPassed(t);
        }
    }

    public static boolean isFinancialPassed(Ticket t) {
        return t.getFinancialStatus() == FinancialStatus.PASSED;
    }

    public static boolean isTechPassed(Ticket t) {
        return t.getTechStatus() == TechStatus.PASSED;
    }

    public static boolean isAdminPassed(Ticket t) {
        return t.getAdminStatus() == AdminStatus.PASSED;
    }

    public static boolean isCanceled(Ticket t) {
        return t.getCustomerStatus() == CustomerStatus.CANCELLED;
    }

    public static boolean isAdminCancelled(Ticket t) {
        return t.getAdminStatus() == AdminStatus.CANCELLED;
    }

    public static List<Nameserver> convertNameservers(List<NameserverChange> nameserversField) {
        List<Nameserver> result = new ArrayList<>(nameserversField.size());
        for (NameserverChange chng : nameserversField) {
            if (!Validator.isEmpty(chng.getName().getNewValue())) {
                result.add(new Nameserver(chng.getName().getNewValue(), chng.getIpv4Address().getNewValue(), chng
                        .getIpv6Address().getNewValue()));
            }
        }
        return result;
    }

    public static List<String> convertContactsToStringList(List<SimpleDomainFieldChange<Contact>> contacts) {
        List<String> res = new ArrayList<String>();
        for (SimpleDomainFieldChange<Contact> c : contacts) {
            if (c.getNewValue() != null) {
                res.add(c.getNewValue().getNicHandle());
            }
        }
        return res;
    }

    public static List<Contact> convertContactsToContactList(List<SimpleDomainFieldChange<Contact>> contacts) {
        List<Contact> res = new ArrayList<Contact>();
        for (SimpleDomainFieldChange<Contact> c : contacts) {
            if (c.getNewValue() != null) {
                res.add(c.getNewValue());
            }
        }
        return res;
    }

}
