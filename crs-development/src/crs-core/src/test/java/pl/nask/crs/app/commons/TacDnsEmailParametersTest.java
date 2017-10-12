package pl.nask.crs.app.commons;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.app.commons.impl.TacDnsEmailParameters;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleDAO;

public class TacDnsEmailParametersTest extends AbstractContextAwareTest {

    @Autowired
    DomainDAO domainDAO;

    @Autowired
    NicHandleDAO nicHandleDAO;

    @Test
    public void testAdminEmailWithTwoAdmins() {
        NicHandle creator = nicHandleDAO.get("APIT1-IEDR");
        Domain domain = domainDAO.get("twoadmincontacts.ie");
        List<Contact> adminContacts = domain.getAdminContacts();
        Assert.assertEquals(adminContacts.size(), 2);
        TacDnsEmailParameters params = new TacDnsEmailParameters("user", creator, domain, Collections.EMPTY_LIST);
        String email = params.getParameterValue(ParameterNameEnum.ADMIN_C_EMAIL.getName(), false);
        Assert.assertEquals(email, adminContacts.get(0).getEmail() + "," + adminContacts.get(1).getEmail());
    }

}
