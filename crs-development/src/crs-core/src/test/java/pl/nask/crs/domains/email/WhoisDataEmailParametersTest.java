package pl.nask.crs.domains.email;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.DomainDAO;

public class WhoisDataEmailParametersTest extends AbstractContextAwareTest {
    @Autowired
    DomainDAO domainDAO;

    @Test
    public void testDomainNameParameterValue() throws Exception {
        Domain domain = getDomain();
        WhoisDataEmailParameters params = new WhoisDataEmailParameters("user", domain, "data");

        Assert.assertTrue(params.getParameterNames().contains(ParameterNameEnum.DOMAIN));

        Assert.assertEquals(params.getParameterValue(ParameterNameEnum.DOMAIN.getName(), false), domain.getName(),
                "Domain name is wrong");
    }

    @Test
    public void testBillCParametersValues() throws Exception {
        Domain domain = getDomain();
        WhoisDataEmailParameters params = new WhoisDataEmailParameters("user", domain, "data");

        Assert.assertTrue(params.getParameterNames().contains(ParameterNameEnum.BILL_C_NAME));
        Assert.assertTrue(params.getParameterNames().contains(ParameterNameEnum.BILL_C_EMAIL));

        Assert.assertEquals(params.getParameterValue(ParameterNameEnum.BILL_C_NAME.getName(), false), domain
                .getBillingContact().getName(), "BillC name is wrong");

        Assert.assertEquals(params.getParameterValue(ParameterNameEnum.BILL_C_EMAIL.getName(), false), domain
                .getBillingContact().getEmail(), "BillC email is wrong");
    }

    @Test
    public void testWhoisParameterValue() throws Exception {
        Domain domain = getDomain();
        WhoisDataEmailParameters params = new WhoisDataEmailParameters("user", domain, "data");

        Assert.assertTrue(params.getParameterNames().contains(ParameterNameEnum.WHOIS));

        Assert.assertEquals(params.getParameterValue(ParameterNameEnum.WHOIS.getName(), false), "data",
                "whois datais wrong");
    }

    @Test
    public void testAdminParametersValues() throws Exception {
        Domain domain = getDomain();
        WhoisDataEmailParameters params = new WhoisDataEmailParameters("user", domain, "data");

        Assert.assertTrue(params.getParameterNames().contains(ParameterNameEnum.ADMIN_C_NAME));
        Assert.assertTrue(params.getParameterNames().contains(ParameterNameEnum.ADMIN_C_EMAIL));
        Assert.assertTrue(params.getParameterNames().contains(ParameterNameEnum.ADMIN_C_NIC));

        // thedomain.ie has only one admin contact, we count on that

        Assert.assertEquals(params.getParameterValue(ParameterNameEnum.ADMIN_C_NAME.getName(), false), domain
                .getFirstAdminContact().getName(), "Admin name is wrong");

        Assert.assertEquals(params.getParameterValue(ParameterNameEnum.ADMIN_C_EMAIL.getName(), false), domain
                .getFirstAdminContact().getEmail(), "Admin email is wrong");

        Assert.assertEquals(params.getParameterValue(ParameterNameEnum.ADMIN_C_NIC.getName(), false), domain
                .getFirstAdminContact().getNicHandle(), "Admin nic is wrong");
    }

    @Test
    public void testAdminParametersValuesWithTwoAdmins() throws Exception {
        Domain domain = domainDAO.get("twoadmincontacts.ie");
        List<Contact> adminContacts = domain.getAdminContacts();
        Assert.assertEquals(adminContacts.size(), 2);
        String adminEmails = adminContacts.get(0).getEmail() + "," + adminContacts.get(1).getEmail();
        WhoisDataEmailParameters params = new WhoisDataEmailParameters("user", domain, "data");

        Assert.assertTrue(params.getParameterNames().contains(ParameterNameEnum.ADMIN_C_NAME));
        Assert.assertTrue(params.getParameterNames().contains(ParameterNameEnum.ADMIN_C_EMAIL));
        Assert.assertTrue(params.getParameterNames().contains(ParameterNameEnum.ADMIN_C_NIC));

        Assert.assertEquals(params.getParameterValue(ParameterNameEnum.ADMIN_C_NAME.getName(), false), domain
                .getFirstAdminContact().getName(), "Admin name is wrong");

        Assert.assertEquals(params.getParameterValue(ParameterNameEnum.ADMIN_C_EMAIL.getName(), false), adminEmails,
                "Admin email is wrong");

        Assert.assertEquals(params.getParameterValue(ParameterNameEnum.ADMIN_C_NIC.getName(), false), domain
                .getFirstAdminContact().getNicHandle(), "Admin nic is wrong");
    }

    @Test
    public void testGetParameterValue() throws Exception {
        Domain domain = getDomain();
        WhoisDataEmailParameters params = new WhoisDataEmailParameters("user", domain, "data");

        Assert.assertNotNull(params.getParameterNames(), "Parameter names' list should not be empty");
    }

    private Domain getDomain() {
        return domainDAO.get("thedomain.ie");
    }
}
