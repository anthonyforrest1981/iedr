package pl.nask.crs.ticket.services.impl;

/*>>>import org.checkerframework.checker.nullness.qual.Nullable;*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.operation.*;

public abstract class AbstractTicketEmailParameters implements EmailParameters {

    // wrapped object
    protected Ticket ticket;
    protected int daysRemaining;
    protected int daysPassed;
    protected /*>>>@Nullable*/ String username;

    public AbstractTicketEmailParameters(/*>>>@Nullable*/ String username, Ticket ticket) {
        Validator.assertNotNull(ticket, "ticket");
        this.ticket = ticket;
        this.username = username;
    }

    public AbstractTicketEmailParameters(/*>>>@Nullable*/ String username, Ticket ticket, int daysRemaining,
            int daysPassed) {
        Validator.assertNotNull(ticket, "ticket");
        this.ticket = ticket;
        this.daysRemaining = daysRemaining;
        this.daysPassed = daysPassed;
        this.username = username;
    }

    @Override
    public List<ParameterName> getParameterNames() {
        return Arrays
                .<ParameterName>asList(ParameterNameEnum.DOMAIN, ParameterNameEnum.HOLDER,
                        ParameterNameEnum.HOLDER_OLD, ParameterNameEnum.ADMIN_C_NAME,
                        ParameterNameEnum.ADMIN_C_EMAIL, ParameterNameEnum.ADMIN_C_NIC,
                        ParameterNameEnum.ADMIN_C_NIC_OLD, ParameterNameEnum.TECH_C_NAME,
                        ParameterNameEnum.TECH_C_EMAIL, ParameterNameEnum.TECH_C_NIC, ParameterNameEnum.TECH_C_NIC_OLD,
                        ParameterNameEnum.CREATOR_C_NAME, ParameterNameEnum.CREATOR_C_EMAIL,
                        ParameterNameEnum.TICKET_ID, ParameterNameEnum.TICKET_TYPE,
                        ParameterNameEnum.DAYS_REMAINING_WITH_DAYS_SUFFIX, ParameterNameEnum.DAYS_PASSED,
                        ParameterNameEnum.REMARK);
    }

    @Override
    public /*>>>@Nullable*/ String getParameterValue(String name, boolean html) {
        Validator.assertNotNull(name, "parameter name");
        ParameterNameEnum ticketName = ParameterNameEnum.forName(name);
        return getParameterValue(ticketName);
    }

    public /*>>>@Nullable*/ String getParameterValue(ParameterNameEnum ticketName) {
        DomainOperation domainOp = ticket.getOperation();
        Contact c = null;
        switch (ticketName) {
            case DOMAIN:
                return domainOp.getDomainNameField().getNewValue();
            case HOLDER:
                return domainOp.getDomainHolderField().getNewValue();
            case HOLDER_OLD:
                return domainOp.getDomainHolderField().getCurrentValue();
            case ADMIN_C_NAME:
                assert domainOp.getAdminContactsField().get(0).getNewValue() != null : "@AssumeAssertion(nullness)";
                return domainOp.getAdminContactsField().get(0).getNewValue().getName();
            case ADMIN_C_EMAIL:
                return getAdminEmail();
            case ADMIN_C_NIC:
                assert domainOp.getAdminContactsField().get(0).getNewValue() != null : "@AssumeAssertion(nullness)";
                return domainOp.getAdminContactsField().get(0).getNewValue().getNicHandle();
            case ADMIN_C_NIC_OLD:
                c = domainOp.getAdminContactsField().get(0).getCurrentValue();
                return c == null ? null : c.getNicHandle();
            case TECH_C_NAME:
                return getNewName(domainOp.getTechContactsField().get(0));
            case TECH_C_EMAIL:
                return getNewEmail(domainOp.getTechContactsField().get(0));
            case TECH_C_NIC:
                assert domainOp.getTechContactsField().get(0).getNewValue() != null : "@AssumeAssertion(nullness)";
                return domainOp.getTechContactsField().get(0).getNewValue().getNicHandle();
            case TECH_C_NIC_OLD:
                c = domainOp.getTechContactsField().get(0).getCurrentValue();
                return c == null ? null : c.getNicHandle();
            case CREATOR_C_NAME:
                return ticket.getCreator().getName();
            case CREATOR_C_EMAIL:
                return ticket.getCreator().getEmail();
            case TICKET_ID:
                return String.valueOf(ticket.getId());
            case TICKET_TYPE:
                return domainOp.getType().getFullName().toLowerCase();
            case DAYS_REMAINING_WITH_DAYS_SUFFIX:
                return String.format("%s day%s", daysRemaining, daysRemaining == 1 ? "" : "s");
            case DAYS_PASSED:
                return String.valueOf(daysPassed);
            case REMARK:
                return getRemark();
            default:
                return null;
        }
    }

    @Override
    public /*>>>@Nullable*/ String getLoggedInNicHandle() {
        return this.username;
    }

    @Override
    public String getAccountRelatedNicHandle(boolean gaining) {
        assert ticket.getOperation().getBillingContactsField().get(0).getNewValue() != null : "@AssumeAssertion(nullness)";
        return this.ticket.getOperation().getBillingContactsField().get(0).getNewValue().getNicHandle();
    }

    @Override
    public String getDomainName() {
        assert ticket.getOperation().getDomainNameField().getNewValue() != null : "@AssumeAssertion(nullness)";
        return this.ticket.getOperation().getDomainNameField().getNewValue();
    }

    protected /*>>>@Nullable*/ String getEmail(/*>>>@Nullable*/ Contact contact) {
        if (contact == null) {
            return null;
        } else {
            return contact.getEmail();
        }
    }

    protected /*>>>@Nullable*/ String getNewEmail(/*>>>@Nullable*/ SimpleDomainFieldChange<Contact> contact) {
        if (contact == null) {
            return null;
        } else {
            return getEmail(contact.getNewValue());
        }
    }

    protected /*>>>@Nullable*/ String getName(/*>>>@Nullable*/ Contact contact) {
        if (contact == null) {
            return null;
        } else {
            return contact.getName();
        }
    }

    protected /*>>>@Nullable*/ String getNewName(/*>>>@Nullable*/ SimpleDomainFieldChange<Contact> contact) {
        if (contact == null) {
            return null;
        } else {
            return getName(contact.getNewValue());
        }
    }

    private String addToRemark(String remark, String text1, String name, String failure) {
        return remark.concat(text1 + " " + name + " [" + failure + "] \n");
    }

    private String getAdminEmail() {
        List<SimpleDomainFieldChange<Contact>> adminList = ticket.getOperation().getAdminContactsField();
        List<String> emails = new ArrayList<String>(adminList.size());
        for (SimpleDomainFieldChange<Contact> admin_field : adminList) {
            final Contact adminContact = admin_field.getNewValue();
            final String email = getEmail(adminContact);
            if (email != null) {
                emails.add(email);
            }
        }
        return StringUtils.join(emails, ",");
    }

    private String getRemark() {
        String remark = "";
        remark = remark.concat(ticket.getHostmastersRemark() + "\n");
        DomainOperation domainOp = ticket.getOperation();

        FailureReason failure = domainOp.getDomainNameField().getFailureReason();
        if (failure != null) {
            if (domainOp.getDomainNameField().getNewValue() != null) {
                remark = addToRemark(remark, "Domain Name :", domainOp.getDomainNameField().getNewValue(),
                        failure.getDescription());
            } else {
                remark = addToRemark(remark, "Domain Name :", "", failure.getDescription());
            }
        }

        failure = domainOp.getDomainHolderField().getFailureReason();
        if (failure != null) {
            if (domainOp.getDomainHolderField().getNewValue() != null) {
                remark = addToRemark(remark, "Domain Holder :", domainOp.getDomainHolderField().getNewValue(),
                        failure.getDescription());
            } else {
                remark = addToRemark(remark, "Domain Holder :", "", failure.getDescription());
            }
        }

        failure = domainOp.getResellerAccountField().getFailureReason();
        if ((failure != null)) {
            if (domainOp.getResellerAccountField().getNewValue() != null) {
                remark = addToRemark(remark, "Account Name :", domainOp.getResellerAccountField().getNewValue()
                        .getName(), failure.getDescription());
            } else {
                remark = addToRemark(remark, "Account Name :", "", failure.getDescription());
            }
        }
        List<SimpleDomainFieldChange<Contact>> adminList = domainOp.getAdminContactsField();
        if (!adminList.isEmpty()) {
            for (SimpleDomainFieldChange<Contact> admin : adminList) {
                failure = admin.getFailureReason();
                if (failure != null) {
                    if (admin.getNewValue() != null) {
                        remark = addToRemark(remark, "Admin Contact :", admin.getNewValue().getNicHandle(),
                                failure.getDescription());
                    } else {
                        remark = addToRemark(remark, "Admin Contact :", "", failure.getDescription());
                    }
                }
            }
        }
        adminList = domainOp.getTechContactsField();
        if (!adminList.isEmpty()) {
            SimpleDomainFieldChange<Contact> tech = adminList.get(0);
            failure = tech.getFailureReason();
            if (failure != null) {
                if (tech.getNewValue() != null) {
                    remark = addToRemark(remark, "Tech Contact :", tech.getNewValue().getNicHandle(),
                            failure.getDescription());
                } else {
                    remark = addToRemark(remark, "Tech Contact :", "", failure.getDescription());
                }
            }
        }
        adminList = domainOp.getBillingContactsField();
        if (!adminList.isEmpty()) {
            SimpleDomainFieldChange<Contact> bill = adminList.get(0);
            failure = bill.getFailureReason();
            if (failure != null) {
                if (bill.getNewValue() != null) {
                    remark = addToRemark(remark, "Billing Contact :", bill.getNewValue().getNicHandle(),
                            failure.getDescription());
                } else {
                    remark = addToRemark(remark, "Billing Contact :", "", failure.getDescription());
                }
            }
        }
        List<NameserverChange> nameServers = domainOp.getNameserversField();
        if (!nameServers.isEmpty()) {
            int i = 0;
            for (NameserverChange nameServer : nameServers) {
                final SimpleDomainFieldChange<String> name = nameServer.getName();
                failure = name.getFailureReason();
                if (failure != null) {
                    final String newValue = name.getNewValue() != null ? name.getNewValue() : "";
                    remark = addToRemark(remark, "DNS Name " + (i + 1) + " :", newValue, failure.getDescription());
                }
                final IpFieldChange ipv4Address = nameServer.getIpv4Address();
                IpFailureReason ipFailure = ipv4Address.getFailureReason();
                if (ipFailure != null) {
                    final String newValue = ipv4Address.getNewValue() != null ? ipv4Address.getNewValue() : "";
                    remark = addToRemark(remark, "DNS IPv4 Addr. " + (i + 1) + " :", newValue, ipFailure.getDescription());
                }
                final IpFieldChange ipv6Address = nameServer.getIpv6Address();
                ipFailure = ipv6Address.getFailureReason();
                if (ipFailure != null) {
                    final String newValue = ipv6Address.getNewValue() != null ? ipv6Address.getNewValue() : "";
                    remark = addToRemark(remark, "DNS IPv6 Addr. " + (i + 1) + " :", newValue, ipFailure.getDescription());
                }
            }
        }

        failure = domainOp.getDomainHolderClassField().getFailureReason();
        if (failure != null) {
            if (domainOp.getDomainHolderClassField() != null) {
                final EntityClass domainHolderClass = domainOp.getDomainHolderClassField().getNewValue();
                assert domainHolderClass != null : "@AssumeAssertion(nullness)";
                remark = addToRemark(remark, "Class :", domainHolderClass.getName(), failure.getDescription());
            } else {
                remark = addToRemark(remark, "Class :", "", failure.getDescription());
            }
        }

        failure = domainOp.getDomainHolderCategoryField().getFailureReason();
        if (failure != null) {
            if (domainOp.getDomainHolderCategoryField() != null) {
                final EntityCategory domainHolderCategory = domainOp.getDomainHolderCategoryField().getNewValue();
                assert domainHolderCategory != null : "@AssumeAssertion(nullness)";
                remark = addToRemark(remark, "Category :", domainHolderCategory.getName(), failure.getDescription());
            } else {
                remark = addToRemark(remark, "Category :", "", failure.getDescription());
            }
        }
        return remark;
    }

}
