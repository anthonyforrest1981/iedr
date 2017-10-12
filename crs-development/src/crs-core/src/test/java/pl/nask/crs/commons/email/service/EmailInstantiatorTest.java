package pl.nask.crs.commons.email.service;

import java.util.Arrays;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.EmailTemplate;
import pl.nask.crs.commons.email.dao.EmailTemplateDAO;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dao.HistoricalDomainDAO;
import pl.nask.crs.domains.services.impl.DomainEmailParameters;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.dao.BuyRequestDAO;
import pl.nask.crs.secondarymarket.emails.SecondaryMarketRequestEmailParameters;
import pl.nask.crs.ticket.AbstractContextAwareTest;
import pl.nask.crs.ticket.Ticket;
import pl.nask.crs.ticket.dao.TicketDAO;
import pl.nask.crs.ticket.services.impl.TicketEmailParameters;

import static org.easymock.EasyMock.createMock;

public class EmailInstantiatorTest extends AbstractContextAwareTest {

    private EmailTemplateDAO daoMock;
    private EmailParameters param;
    @Resource
    EmailInstantiator emailInst;
    @Resource
    TicketDAO ticketDao;
    @Resource
    EmailTemplateDAO emailTemplateDAO;
    @Resource
    NicHandleDAO nicHandleDAO;
    @Resource
    DomainDAO domainDAO;
    @Resource
    HistoricalDomainDAO historicalDomainDAO;
    @Resource
    BuyRequestDAO buyRequestDAO;

    @BeforeMethod
    public void init() {
        daoMock = createMock(EmailTemplateDAO.class);
        Ticket actual = ticketDao.get(7L);
        param = new TicketEmailParameters(null, actual);
    }

    @Test
    public void testInstantiate() {
        try {
            EmailTemplate template = emailTemplateDAO.get(43);
            Email email = emailInst.instantiate(template, param, true);
        } catch (TemplateInstantiatingException e) {
            Logger.getLogger(EmailInstantiatorTest.class).error(e);
        }
    }

    @Test
    public void testInstantiateWhenTicketHasTwoAdmins() throws TemplateInstantiatingException {
        // in case there are two admins email parameters tend to either ignore second one
        // or worse case - join then with a comma. Email instantiator must handle such joined
        // emails as two separate emails
        EmailTemplate template = emailTemplateDAO.get(EmailTemplateNamesEnum.NREG_APPLICATION.getId());
        Ticket ticket = ticketDao.get(259887l);
        EmailParameters params = new TicketEmailParameters(null, ticket);
        Email email = emailInst.instantiate(template, params, true);
        Assert.assertEquals(email.getToList(), Arrays.asList("NHEmail000033@server.xxx"));
        Assert.assertEquals(email.getCcList(),
                Arrays.asList("registrations@iedr.ie", "NHEmail000051@server.xxx", "NHEmail000037@server.xxx"));
        Assert.assertEquals(email.getBccList(),
                Arrays.asList("hostmaster-archive@domainregistry.ie"));
    }

    @Test
    public void testInstantiateWhenTicketHasOneAdmin() throws TemplateInstantiatingException {
        // in case there are two admins email parameters tend to either ignore second one
        // or worse case - join then with a comma. Email instantiator must handle such joined
        // emails as two separate emails
        EmailTemplate template = emailTemplateDAO.get(EmailTemplateNamesEnum.NREG_APPLICATION.getId());
        Ticket ticket = ticketDao.get(258195l);
        EmailParameters params = new TicketEmailParameters(null, ticket);
        Email email = emailInst.instantiate(template, params, true);
        Assert.assertEquals(email.getToList(), Arrays.asList("NHEmail000900@server.xxx"));
        Assert.assertEquals(email.getCcList(), Arrays.asList("registrations@iedr.ie", "NHEmail000371@server.xxx"));
        Assert.assertEquals(email.getBccList(),
                Arrays.asList("hostmaster-archive@domainregistry.ie"));
    }

    @Test
    public void testInstantiateWhenDomainHasOneAdmin() throws TemplateInstantiatingException {
        // in case there are two admins email parameters tend to either ignore second one
        // or worse case - join then with a comma. Email instantiator must handle such joined
        // emails as two separate emails
        EmailTemplate template = emailTemplateDAO.get(11);
        final String domainName = "pizzaonline.ie";
        Domain domain = domainDAO.get(domainName);
        EmailParameters params = new DomainEmailParameters(null, nicHandleDAO, domain);
        Email email = emailInst.instantiate(template, params, true);
        Assert.assertEquals(email.getToList(), Arrays.asList("NHEmail000085@server.xxx"));
        Assert.assertEquals(email.getCcList(), Arrays.asList("NHEmail000901@server.xxx", "webmaster@pizzaonline.ie",
                "info@pizzaonline.ie", "postmaster@pizzaonline.ie"));
        Assert.assertEquals(email.getBccList(), Arrays.asList("nrp@iedr.ie", "donotreply@iedr.ie"));
    }

    @Test
    public void testInstantiateWhenDomainHasTwoAdmins() throws TemplateInstantiatingException {
        // in case there are two admins email parameters tend to either ignore second one
        // or worse case - join then with a comma. Email instantiator must handle such joined
        // emails as two separate emails
        EmailTemplate template = emailTemplateDAO.get(11);
        final String domainName = "pizzaonline.ie";
        Domain domain = domainDAO.get(domainName);
        Contact secondAdmin = new Contact("AGZ573-IEDR");
        final Contact previousAdmin = domain.getAdminContacts().get(0);
        domain.setAdminContacts(Arrays.asList(previousAdmin, secondAdmin));
        domainDAO.updateUsingHistory(historicalDomainDAO.create(domain, new Date(), TestOpInfo.DEFAULT.getActorName()));
        domain = domainDAO.get(domainName);
        EmailParameters params = new DomainEmailParameters(null, nicHandleDAO, domain);
        Email email = emailInst.instantiate(template, params, true);
        Assert.assertEquals(email.getToList(), Arrays.asList("NHEmail000085@server.xxx", "NHEmail000087@server.xxx"));
        Assert.assertEquals(email.getCcList(), Arrays.asList("NHEmail000901@server.xxx", "webmaster@pizzaonline.ie",
                "info@pizzaonline.ie", "postmaster@pizzaonline.ie"));
        Assert.assertEquals(email.getBccList(), Arrays.asList("nrp@iedr.ie", "donotreply@iedr.ie"));
    }

    @Test
    public void testInstantiateWithDunplicateCC() throws TemplateInstantiatingException {
        EmailTemplate template = emailTemplateDAO.get(200);
        BuyRequest buyRequest = buyRequestDAO.get(2L);
        Domain domain = domainDAO.get(buyRequest.getDomainName());
        NicHandle nicHandle = nicHandleDAO.get(buyRequest.getCreatorNH());
        String emailAddress = "NHEmail000901@server.xxx";
        // The same email in domain billing contact and in creator Nic Handle
        Assert.assertEquals(domain.getBillingContact().getEmail(), emailAddress);
        Assert.assertEquals(nicHandle.getEmail(), emailAddress);
        EmailParameters params = new SecondaryMarketRequestEmailParameters(domain, buyRequest, nicHandle,
                TestOpInfo.DEFAULT.getActorName());
        Email email = emailInst.instantiate(template, params, true);
        // Only once in the instantiated email
        Assert.assertEquals(email.getCcList().size(), 1);
        Assert.assertEquals(email.getCcList().get(0), emailAddress);
    }

    @Test
    public void testInstantiateWithCCAndToEqual() throws TemplateInstantiatingException {
        EmailTemplate template = emailTemplateDAO.get(211);
        Domain domain = domainDAO.get("webwebweb.ie");
        String emailAddress = domain.getBillingContact().getEmail();
        EmailParameters params = new ContactPasswordEmailParameters("IDL2-IEDR", emailAddress, "Passw0rd!", domain,
                TestOpInfo.DEFAULT.getActorName());
        Email email = emailInst.instantiate(template, params, true);
        Assert.assertEquals(email.getToList().size(), 1);
        Assert.assertEquals(email.getToList().get(0), emailAddress);
        // "CC" should be filled with bill C's email, which is equal to the one in "To" field though
        Assert.assertTrue(email.getCcList().isEmpty());
        Assert.assertEquals(email.getBccList().size(), 1);
        Assert.assertEquals(email.getBccList().get(0), "registrations@iedr.ie");
    }

    @Test
    public void testInstantiateWithBCCAndToEqual() throws TemplateInstantiatingException {
        EmailTemplate template = emailTemplateDAO.get(211);
        Domain domain = domainDAO.get("webwebweb.ie");
        String emailAddress = "registrations@iedr.ie";
        EmailParameters params = new ContactPasswordEmailParameters("IDL2-IEDR", emailAddress, "Passw0rd!", domain,
                TestOpInfo.DEFAULT.getActorName());
        Email email = emailInst.instantiate(template, params, true);
        Assert.assertEquals(email.getToList().size(), 1);
        Assert.assertEquals(email.getToList().get(0), emailAddress);
        Assert.assertEquals(email.getCcList().size(), 1);
        Assert.assertEquals(email.getCcList().get(0), domain.getBillingContact().getEmail());
        // "BCC" should be filled with "registrations@iedr.ie", which is equal to the one in "To" field though
        Assert.assertTrue(email.getBccList().isEmpty());
    }
}
