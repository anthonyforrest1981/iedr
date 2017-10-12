package pl.nask.crs.domains.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.AbstractContextAwareTest;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.services.impl.BulkTransferEmailParameters;
import pl.nask.crs.domains.transfer.BulkTransferRequest;
import pl.nask.crs.nichandle.service.NicHandleSearchService;

public class BulkTransferEmailParametersTest extends AbstractContextAwareTest {

    @Autowired
    DomainDAO domainDAO;

    @Autowired
    NicHandleSearchService nicHandleSearchService;

    @Autowired
    AccountSearchService accountService;

    @Test
    public void testAdminEmailWithTwoAdmins() throws Exception {
        Domain domain = domainDAO.get("twoadmincontacts.ie");
        BulkTransferRequest request = new BulkTransferRequest(666, 667, "Remarks");
        String hostmasterHandle = "IDL2-IEDR";
        List<Contact> adminContacts = domain.getAdminContacts();
        Assert.assertEquals(adminContacts.size(), 2);
        BulkTransferEmailParameters params = new BulkTransferEmailParameters(request, hostmasterHandle, accountService,
                nicHandleSearchService, domain);
        String email = params.getParameterValue(ParameterNameEnum.ADMIN_C_EMAIL.getName(), false);
        Assert.assertEquals(email, adminContacts.get(0).getEmail() + "," + adminContacts.get(1).getEmail());
    }

}
