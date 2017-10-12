package pl.nask.crs.invoicing;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.services.impl.CreateAccountContener;
import pl.nask.crs.api.authentication.CRSAuthenticationService;
import pl.nask.crs.api.vo.RegistrationRequestVO;
import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.commons.TicketRequest.PeriodType;
import pl.nask.crs.app.invoicing.InvoicingSupportService;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.tickets.exceptions.TooManyTicketsException;
import pl.nask.crs.app.triplepass.TriplePassSupportService;
import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.nichandle.NewAccount;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;

/**
 *
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 *
 * @author Artur Gniadzik
 *
 * This is to reproduce bug #9266
 * <pre>
 In a round of user testing, not all transactions were followed by the expected pdf and xml files.
 The invoice is created in the database correctly and payment settled etc., but no files are generated.
 In the files that are created, the invoice number that was generated is skipped.
 ie. in this example Inv0060048 was written to Invoice table but no file was generated for it,
 a file however was generated for INV0060047 and INV0060049 and INV0060050
 so crs seems to have just had a problem creating that particular invoice.
 It seems to be happening for larger invoices but not always.
 * </pre>
 */

@ContextConfiguration(locations = {"/crs-api-config.xml", "/crs-api-test-config.xml"})
public class LargeInvoiceTest extends AbstractTestNGSpringContextTests {

    @Resource
    AccountAppService accountAppService;

    @Resource
    CRSAuthenticationService crsAuthenticationService;

    @Resource
    PaymentAppService paymentAppService;

    @Resource
    CommonAppService commonApService;

    @Resource
    TicketAppService ticketAppService;

    @Resource
    TriplePassSupportService triplePassSupportService;

    @Resource
    InvoicingSupportService invoicingSupportService;

    private final String password = "Marysia1";

    AuthenticatedUser adminUser;

    String billingContactNicHandle;
    private Account registrar;

    private AuthenticatedUser registrarUser;

    @Test
    public void foo() {
        // to make surefire happy
    }

    @Test(enabled = false)
    public void createLargeInvoice() throws Exception {
        adminLogIn();
        createRegistrar();
        topUpRegistrarDeposit();
        resellerLogIn();
        registerDomains();
        runInvoicing();
        checkDbInvoice();
        checkPdfInvoice();
        checkXmlInvioce();
        logger.info("Done!");
    }

    private void resellerLogIn() throws Exception {
        registrarUser = crsAuthenticationService.authenticate(billingContactNicHandle, password, "1.1.1.1", null);
    }

    private void checkXmlInvioce() {
        // TODO Auto-generated method stub

    }

    private void checkPdfInvoice() {
        // TODO Auto-generated method stub

    }

    private void checkDbInvoice() {
        // TODO Auto-generated method stub
    }

    private void runInvoicing()
            throws DomainNotFoundException, AccessDeniedException, TooManyTicketsException,
            DomainIllegalStateException {
        logger.info("triplePass");
        triplePassSupportService.triplePass(adminUser);
        logger.info("invoicing");
        invoicingSupportService.runInvoicing(adminUser);
    }

    private void registerDomains() throws Exception {
        logger.info("register domains");
        for (int i = 0; i < 500; i++) {
            registerDomain(i);
        }
    }

    private void registerDomain(int i) throws Exception {
        String domainName = i + "domain" + registrar.getId() + ".ie";
        registerDomain(domainName);
    }

    private void registerDomain(String domainName) throws Exception {
        RegistrationRequestVO request = new RegistrationRequestVO();
        request.setAdminContactNicHandles(Arrays.asList(billingContactNicHandle, null));
        request.setTechContactNicHandle(billingContactNicHandle);
        request.setDomainHolder("any holder");
        request.setDomainName(domainName);
        request.setDomainOwnerTypeId(1L);
        request.setPeriod(1);
        request.setPeriodType(PeriodType.Y);
        request.setRequestersRemark("any remark");
        request.setNameservers(Arrays.asList(new Nameserver("ns.iedr.ie", null, null), new Nameserver(
                "ns1.iedr.ie", null, null)));

        long ticketId = commonApService.registerDomain(registrarUser, request, null);

        Ticket ticket = ticketAppService.getTicketForDomain(adminUser, request.getDomainName());
        ticketAppService.accept(adminUser, ticketId, ticket.getOperation(), "auto accept");
    }

    private void topUpRegistrarDeposit() throws Exception {
        paymentAppService.correctDeposit(adminUser, billingContactNicHandle,
                MoneyUtils.getRoundedAndScaledValue(10000000), "Initial top-up");
    }

    private void adminLogIn() throws Exception {
        adminUser = crsAuthenticationService.authenticate("IDL2-IEDR", "Passw0rd!", "1.1.1.1", null);
    }

    private void createRegistrar() throws Exception {
        NewNicHandle nh = new NewNicHandle("any name", "any company name", "any@email.ie", "any address", 199, 0,
                Arrays.asList("111111"), Arrays.asList("111111"), "GB747832695", null);

        NewAccount na = accountAppService.createDirectAccount(nh, password, false);;
        billingContactNicHandle = na.getNicHandleId();
        CreateAccountContener createAccountContener = new CreateAccountContener("Any name", "http://anyhost.ie/",
                billingContactNicHandle, "any account", true, true);
        registrar = accountAppService.create(adminUser, createAccountContener, "Account to test bug#9266");
    }

}
