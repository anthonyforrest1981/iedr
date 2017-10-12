package pl.nask.crs.ticket.services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.EntitySubcategory;
import pl.nask.crs.entities.dao.EntityCategoryDAO;
import pl.nask.crs.entities.dao.EntityClassDAO;
import pl.nask.crs.ticket.*;
import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.operation.IpFieldChange;
import pl.nask.crs.ticket.operation.NameserverChange;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;
import pl.nask.crs.ticket.services.impl.TicketEmailParameters;

@Test
@SuppressWarnings("nullness")
public class TicketEmailParametersTest extends AbstractContextAwareTest {

    @Resource
    EntityClassDAO entityClassDAO;

    @Resource
    EntityCategoryDAO entityCategoryDAO;

    private Ticket ticket;
    private TicketEmailParameters params;

    private String domainName = "domain.ie";
    private String domainHolder = "domain holder";
    private String creatorContact = "CREATOR";
    private String domainHolderOld = "old domain holder";
    private String billingContact = "BILLING";
    private String billingContactOld = "BILLING-OLD";
    private String billingContactCompany = "BILLING_COMPANY";
    private DomainOperationType ticketType = DomainOperationType.MOD;
    private String adminContactOld = "ADMIN-OLD";
    private String adminContactOld1 = "ADMIN-OLD";
    private String techContactOld = "TECH-OLD";
    private String techContact = "TECH";
    private String adminContact = "ADMIN";
    private String adminContact1 = "ADMIN1";

    private String remark = "hostmasters remark\n";

    private void prepareTicketParameters(boolean useSecondAdminContact) {
        ticket = newTicket(useSecondAdminContact);
        params = new TicketEmailParameters(null, ticket);
    }

    @Test
    public void shouldSupportAllParameterNames() {
        prepareTicketParameters(true);
        List<ParameterName> names = params.getParameterNames();
        for (ParameterName n : names) {
            // should not throw any exceptions!
            params.getParameterValue(n.getName(), false);
        }
    }

    @DataProvider(name = "dataProviderTwoAdmins")
    public Object[][] expectedParamValuesTwoAdmins() {
        return expectedParamValues(true);
    }

    @DataProvider(name = "dataProviderOneAdmin")
    public Object[][] expectedParamValuesOneAdmin() {
        return expectedParamValues(false);
    }

    private Object[][] expectedParamValues(boolean useSecondAdmin) {
        return new Object[][] {
                {ParameterNameEnum.DOMAIN, domainName},
                {ParameterNameEnum.HOLDER, domainHolder},
                {ParameterNameEnum.HOLDER_OLD, domainHolderOld},
                {ParameterNameEnum.ADMIN_C_EMAIL,
                        contactEmail(adminContact) + (useSecondAdmin ? ("," + contactEmail(adminContact1)) : "")},
                {ParameterNameEnum.ADMIN_C_NIC, contactNh(adminContact)},
                {ParameterNameEnum.ADMIN_C_NIC_OLD, contactNh(adminContactOld)},
                {ParameterNameEnum.TECH_C_NIC, contactNh(techContact)}, {ParameterNameEnum.TECH_C_NAME, techContact},
                {ParameterNameEnum.TECH_C_NIC_OLD, contactNh(techContactOld)},
                {ParameterNameEnum.TECH_C_EMAIL, contactEmail(techContact)},
                {ParameterNameEnum.CREATOR_C_NAME, creatorContact},
                {ParameterNameEnum.CREATOR_C_EMAIL, contactEmail(creatorContact)},
                {ParameterNameEnum.BILL_C_EMAIL, contactEmail(billingContact)},
                {ParameterNameEnum.REGISTRAR_NAME, billingContact}, {ParameterNameEnum.BILL_C_NAME, billingContact},
                {ParameterNameEnum.BILL_C_CO_NAME, billingContactCompany}, {ParameterNameEnum.TICKET_ID, "0"},
                {ParameterNameEnum.REMARK, remark},
                {ParameterNameEnum.TICKET_TYPE, ticketType.getFullName().toLowerCase()}};
    }

    @Test(dataProvider = "dataProviderTwoAdmins")
    public void shouldReturnProperParameterValueTwoAdmins(ParameterNameEnum param, String expectedValue) {
        prepareTicketParameters(true);
        assertProper(expectedValue, param);
    }

    @Test(dataProvider = "dataProviderOneAdmin")
    public void shouldReturnProperParameterValueOneAdmin(ParameterNameEnum param, String expectedValue) {
        prepareTicketParameters(false);
        assertProper(expectedValue, param);
    }

    @Test
    public void shouldHaveAllChangesMentionedInTheRemarks() {
        prepareTicketParameters(true);
        System.out.println(params.getParameterValue("REMARK", false));
    }

    private void assertProper(String value, ParameterNameEnum paramName) {
        AssertJUnit.assertEquals("value for parameter name: " + paramName, value,
                params.getParameterValue(paramName.getName(), false));
    }

    // helper methods

    private Ticket newTicket(boolean useSecondAdminContact) {
        Date anyDate = new Date();
        Date adminStatusChangeDate = anyDate;
        Date techStatusChangeDate = anyDate;
        Date creationDate = anyDate;
        Date changeDate = anyDate;
        Date financialStatusChangeDate = anyDate;
        Date customerStatusChangeDate = anyDate;
        AdminStatus adminStatus = AdminStatus.PASSED;
        TechStatus techStatus = TechStatus.PASSED;
        FinancialStatus financialStatus = FinancialStatus.PASSED;
        CustomerStatus customerStatus = CustomerStatus.NEW;
        Contact creator = newContact(creatorContact);
        String charityCode = "111";
        Period domainPeriod = Period.fromYears(1);
        boolean hasDocuments = false;
        boolean clikPaid = false;
        Contact checkedOutTo = newContact("CHKOUT");
        DomainOperation operation = newOperation(useSecondAdminContact);
        Ticket t = new Ticket(0L, operation, adminStatus, adminStatusChangeDate, techStatus, techStatusChangeDate,
                "requesters remark", "hostmasters remark", creator, creationDate, changeDate, checkedOutTo, clikPaid,
                hasDocuments, domainPeriod, charityCode, false, financialStatus, financialStatusChangeDate, customerStatus,
                customerStatusChangeDate);
        return t;
    }

    private DomainOperation newOperation(boolean useSecondAdminContact) {
        List<NameserverChange> nameserversField = newNameserversChange();
        List<SimpleDomainFieldChange<Contact>> billingContactFieldChange = newContactChange(billingContactOld,
                billingContact);
        List<SimpleDomainFieldChange<Contact>> techContactFieldChange = newContactChange(techContactOld, techContact);
        List<SimpleDomainFieldChange<Contact>> adminContactFieldChange;
        if (useSecondAdminContact) {
            adminContactFieldChange = newContactChange(adminContactOld, adminContact, adminContactOld1, adminContact1);
        } else {
            adminContactFieldChange = newContactChange(adminContactOld, adminContact);
        }
        SimpleDomainFieldChange<Account> resellerAccountField = newAccountChange();
        SimpleDomainFieldChange<EntitySubcategory> domainHolderSubcategoryField = new SimpleDomainFieldChange<>(
                null, null);
        SimpleDomainFieldChange<EntityCategory> domainHolderCategoryField = new SimpleDomainFieldChange<>(
                null, null);
        SimpleDomainFieldChange<EntityClass> domainHolderClassFieldChange = new SimpleDomainFieldChange<>(
                null, null);
        SimpleDomainFieldChange<String> domainHolderField = new SimpleDomainFieldChange<>(domainHolderOld,
                domainHolder);
        SimpleDomainFieldChange<String> domainNameField = new SimpleDomainFieldChange<>(null, domainName);
        Date renewalDate = new Date(); // any date
        DomainOperationType type = ticketType;
        DomainOperation op = new DomainOperation(type, renewalDate, domainNameField, domainHolderField,
                domainHolderClassFieldChange, domainHolderCategoryField, domainHolderSubcategoryField,
                resellerAccountField, adminContactFieldChange, techContactFieldChange, billingContactFieldChange,
                nameserversField);

        return op;
    }

    private List<NameserverChange> newNameserversChange() {
        return Arrays.asList(
                new NameserverChange(new SimpleDomainFieldChange<>("old.ns.1", "new.ns.1"),
                new IpFieldChange("old.ipv4.1", "new.ipv4.1"), new IpFieldChange("old.ipv6.1", "new.ipv6.1")),
                new NameserverChange(new SimpleDomainFieldChange<>("old.ns.2", "new.ns.2"),
                new IpFieldChange("old.ipv4.2", "new.ipv4.2"), new IpFieldChange("old.ipv6.2", "new.ipv6.2")));
    }

    private SimpleDomainFieldChange<Account> newAccountChange() {
        Account acc = new Account(1111L, "account name", "account nh");
        return new SimpleDomainFieldChange<Account>(acc, acc);
    }

    @SuppressWarnings("unchecked")
    private List<SimpleDomainFieldChange<Contact>> newContactChange(String oldC, String newC) {
        return Arrays.asList(new SimpleDomainFieldChange<Contact>(newContact(oldC), newContact(newC)));
    }

    @SuppressWarnings("unchecked")
    private List<SimpleDomainFieldChange<Contact>> newContactChange(String oldC, String newC, String oldC1, String newC1) {
        return Arrays.asList(new SimpleDomainFieldChange<Contact>(newContact(oldC), newContact(newC)),
                new SimpleDomainFieldChange<Contact>(newContact(oldC1), newContact(newC1)));
    }

    private Contact newContact(String nh) {
        if (nh == null) {
            return null;
        } else {
            return new Contact(contactNh(nh), nh, contactEmail(nh), billingContactCompany, "country", "county");
        }
    }

    private String contactNh(String nh) {
        if (nh == null) {
            return null;
        } else {
            return nh + "-NH";
        }

    }

    private String contactEmail(String nh) {
        if (nh == null) {
            return null;
        } else {
            return nh.toLowerCase() + "@somedomain.ie";
        }
    }

}
