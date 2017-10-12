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

public class AuthCodeEmailParametersTest extends AbstractContextAwareTest {

    @Autowired
    DomainDAO domainDAO;

    @Test
    public void testAdminEmailWithTwoAdmins() {
        Domain domain = domainDAO.get("twoadmincontacts.ie");
        List<Contact> adminContacts = domain.getAdminContacts();
        Assert.assertEquals(adminContacts.size(), 2);
        AuthCodeEmailParameters params = new AuthCodeEmailParameters("user", domain);
        String email = params.getParameterValue(ParameterNameEnum.ADMIN_C_EMAIL.getName(), false);
        Assert.assertEquals(email, adminContacts.get(0).getEmail() + "," + adminContacts.get(1).getEmail());
    }

}
