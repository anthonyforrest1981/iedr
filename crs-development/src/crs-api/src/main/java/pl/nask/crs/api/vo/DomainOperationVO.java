package pl.nask.crs.api.vo;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.api.validation.ValidationHelper;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class DomainOperationVO {

    @XmlElement(required = true)
    private SimpleDomainFieldChangeVO<String> domainNameField;

    @XmlElement(required = true)
    private SimpleDomainFieldChangeVO<String> domainHolderField;

    @XmlElement(required = true)
    private SimpleDomainFieldChangeVO<Long> resellerAccountField;

    @XmlElement(required = true)
    private List<SimpleDomainContactChangeVO> adminContactsField;

    @XmlElement(required = true)
    private List<SimpleDomainContactChangeVO> techContactsField;

    @XmlElement(required = true)
    private List<SimpleDomainContactChangeVO> billingContactsField;

    @XmlElement(required = true)
    private List<NameserverChangeVO> nameservers;

    public DomainOperationVO() {}

    public DomainOperationVO(DomainOperation op) {
        domainNameField = new SimpleDomainFieldChangeVO<>(op.getDomainNameField());
        domainHolderField = new SimpleDomainFieldChangeVO<>(op.getDomainHolderField());
        resellerAccountField = createAccountField(op.getResellerAccountField());
        adminContactsField = convertFromContacts(op.getAdminContactsField());
        techContactsField = convertFromContacts(op.getTechContactsField());
        billingContactsField = convertFromContacts(op.getBillingContactsField());
        nameservers = convertFromNameservers(op.getNameserversField());
    }

    private SimpleDomainFieldChangeVO<Long> createAccountField(SimpleDomainFieldChange<Account> dc) {
        return new SimpleDomainFieldChangeVO<>(getAccountId(dc.getCurrentValue()), getAccountId(dc.getNewValue()),
                dc.getFailureReason());
    }

    private Long getAccountId(Account a) {
        if (a == null)
            return null;
        else
            return a.getId();
    }

    private List<NameserverChangeVO> convertFromNameservers(List<NameserverChange> nameservers) {
        List<NameserverChangeVO> res = new ArrayList<>();
        for (NameserverChange ns : nameservers) {
            if (!isEmptyNameserverChange(ns))
                res.add(new NameserverChangeVO(ns));
        }
        return res;
    }

    private boolean isEmptyNameserverChange(NameserverChange ns) {
        return (ValidationHelper.isEmptySimpleDomainFieldChange(ns.getName())
                && ValidationHelper.isEmptyIpFieldChange(ns.getIpv4Address())
                && ValidationHelper.isEmptyIpFieldChange(ns.getIpv6Address()));
    }

    private List<SimpleDomainContactChangeVO> convertFromContacts(List<SimpleDomainFieldChange<Contact>> contacts) {
        List<SimpleDomainContactChangeVO> res = new ArrayList<>();
        for (SimpleDomainFieldChange<Contact> c : contacts) {
            if (!isEmptyContactFieldChange(c))
                res.add(new SimpleDomainContactChangeVO(c));
        }
        return res;
    }

    private boolean isEmptyContactFieldChange(SimpleDomainFieldChange<Contact> c) {
        return (c.getCurrentValue() == null || Validator.isEmpty(c.getCurrentValue().getNicHandle()))
                && (c.getNewValue() == null || Validator.isEmpty(c.getNewValue().getNicHandle()))
                && c.getFailureReason() == null;
    }

    public SimpleDomainFieldChangeVO<String> getDomainNameField() {
        return domainNameField;
    }

    public SimpleDomainFieldChangeVO<String> getDomainHolderField() {
        return domainHolderField;
    }

    public SimpleDomainFieldChangeVO<Long> getResellerAccountField() {
        return resellerAccountField;
    }

    public List<SimpleDomainContactChangeVO> getAdminContactsField() {
        return adminContactsField;
    }

    public List<SimpleDomainContactChangeVO> getTechContactsField() {
        return techContactsField;
    }

    public List<SimpleDomainContactChangeVO> getBillingContactsField() {
        return billingContactsField;
    }

    public List<NameserverChangeVO> getNameservers() {
        return nameservers;
    }
}
