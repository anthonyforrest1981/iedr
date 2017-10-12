package pl.nask.crs.app.permissions;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.dao.AccountDAO;
import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.config.ConfigAppService;
import pl.nask.crs.app.dnscheck.DnsNotificationAppService;
import pl.nask.crs.app.document.DocumentAppService;
import pl.nask.crs.app.domains.BulkTransferAppService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.email.EmailGroupAppService;
import pl.nask.crs.app.email.EmailTemplateAppService;
import pl.nask.crs.app.emaildisabler.EmailDisablerAppService;
import pl.nask.crs.app.invoicing.InvoicingAppService;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.app.reports.ReportsAppService;
import pl.nask.crs.app.secondarymarket.SecondaryMarketAppService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.triplepass.TriplePassAppService;
import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.commons.Period;
import pl.nask.crs.country.CountryFactory;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.search.DeletedDomainSearchCriteria;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.domains.search.ExtendedDomainSearchCriteria;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.nichandle.search.NicHandleSearchCriteria;
import pl.nask.crs.secondarymarket.BuyRequestStatus;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationException;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.user.Group;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.UserDAO;
import pl.nask.crs.user.permissions.Permission;

@ContextConfiguration(locations = {"/application-services-config.xml", "/application-services-test-config.xml"})
public abstract class AbstractPermissionTest extends AbstractTransactionalTestNGSpringContextTests implements
        AccountTestMethods, BulkTransferTestMethods, CommonTestMethods, ConfigTestMethods, DnsNotificationTestMethods,
        DocumentTestMethods, DomainTestMethods, EmailTestMethods, EmailDisablerTestMethods, InvoicingTestMethods,
        NicHandleTestMethods, PaymentTestMethods, ReportsTestMethods, SecondaryMarketTestMethods, TicketTestMethods,
        TriplePassTestMethods, UserTestMethods {

    protected final static String IP = "1.1.1.1";

    @Resource
    AuthenticationService authenticationService;

    @Resource
    AccountAppService accountAppService;

    @Resource
    BulkTransferAppService bulkTransferAppService;

    @Resource
    CommonAppService commonAppService;

    @Resource
    ConfigAppService configAppService;

    @Resource
    DnsNotificationAppService dnsNotificationAppService;

    @Resource
    DocumentAppService documentAppService;

    @Resource
    DomainAppService domainAppService;

    @Resource
    EmailDisablerAppService emailDisablerAppService;

    @Resource
    EmailGroupAppService emailGroupAppService;

    @Resource
    EmailTemplateAppService emailTemplateAppService;

    @Resource
    InvoicingAppService invoicingAppService;

    @Resource
    NicHandleAppService nicHandleAppService;

    @Resource
    PaymentAppService paymentAppService;

    @Resource
    ReportsAppService reportsAppService;

    @Resource
    SecondaryMarketAppService secondaryMarketAppService;

    @Resource
    TicketAppService ticketAppService;

    @Resource
    TriplePassAppService triplePassAppService;

    @Resource
    UserAppService userAppService;

    @Resource
    UserDAO userDAO;

    @Resource
    AccountDAO accountDAO;

    @Resource
    DomainDAO domainDAO;

    @Resource
    NicHandleDAO nicHandleDAO;

    @Resource
    CountryFactory countryFactory;

    protected AuthenticatedUser authenticatedUser;

    protected abstract String getUserName();

    protected abstract Level getGroup();

    @BeforeClass
    public void setUp() throws AuthenticationException {
        this.authenticatedUser = authenticate(getUserName());
    }

    protected abstract String getSystemDiscriminator();

    protected AuthenticatedUser authenticate(String userName) throws AuthenticationException {
        return authenticationService.authenticate(userName, "Passw0rd!", false, IP, false, null, true,
                getSystemDiscriminator());
    }

    @Test
    public void isInProperGroupTest() throws AccessDeniedException {
        User user = userDAO.get(authenticatedUser.getUsername());
        Assert.assertTrue(user.hasGroup(getGroup()), "User:" + authenticatedUser.getUsername()
                + " does not belong to group:" + getGroup());
        System.out.println("User's permissions:");
        for (Group g : user.getPermissionGroups()) {
            System.out.println("User role: " + g + " with permissions: ");
            for (Permission p : g.getPermissions()) {
                System.out.println(p);
            }
        }

    }

    protected void requestRegistration(AuthenticatedUser user) throws AccessDeniedException {
        try {
            commonAppService.registerDomain(user, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip only AccessDeniedException is important
        }
    }

    protected void topUpDeposit(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.depositFunds(user, BigDecimal.ZERO, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void viewXmlInvoice(AuthenticatedUser user, String invoiceNumber) throws AccessDeniedException {
        try {
            paymentAppService.viewXmlInvoice(user, invoiceNumber);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void viewPdfInvoice(AuthenticatedUser user, String invoiceNumber) throws AccessDeniedException {
        try {
            paymentAppService.viewPdfInvoice(user, invoiceNumber);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void viewMergedInvoices(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.viewMergedInvoices(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void payForDomainRenewal(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        try {
            Map<String, Period> map = new HashMap<>();
            map.put(domainName, null);
            paymentAppService.payForDomainRenewal(user, map, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void transferRequest(AuthenticatedUser user) throws AccessDeniedException {
        try {
            commonAppService.transfer(user, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void cancelTicket(AuthenticatedUser user, long ticketId) throws AccessDeniedException {
        try {
            commonAppService.cancelTicketAsOwner(user, ticketId);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void enterVoluntaryNRP(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        try {
            domainAppService.enterVoluntaryNRP(user, domainName);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void removeFromVoluntaryNRP(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        try {
            domainAppService.removeFromVoluntaryNRP(user, domainName);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getRelatedDomains(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.getRelatedDomains(user, null, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void modifyDomain(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        try {
            commonAppService.modifyDomain(user, domainName, null, null, null, null, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void modifyNameservers(AuthenticatedUser user, List<String> domainNames) throws AccessDeniedException {
        try {
            commonAppService.modifyNameservers(user, domainNames, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void editTicket(AuthenticatedUser user, long ticketId) throws AccessDeniedException {
        try {
            ticketAppService.edit(user, ticketId);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void viewTicket(AuthenticatedUser user, long ticketId) throws AccessDeniedException {
        try {
            ticketAppService.view(user, ticketId);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void reauthoriseCCTransaction(AuthenticatedUser user, long transactionId) throws AccessDeniedException {
        try {
            commonAppService.reauthoriseTransaction(user, transactionId, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void cleanupTicket(AuthenticatedUser user) throws AccessDeniedException {
        try {
            commonAppService.cleanupTicket(user, null, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void generateOrProlongAuthCode(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        try {
            commonAppService.generateOrProlongAuthCode(user, domainName);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void sendAuthCodeByEmail(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        try {
            commonAppService.sendAuthCodeByEmail(user, domainName);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void verifyAuthCode(AuthenticatedUser user) throws AccessDeniedException {
        try {
            commonAppService.verifyAuthCode(user, null, null, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getOwnerTypeByName(AuthenticatedUser user) throws AccessDeniedException {
        try {
            commonAppService.getOwnerTypeByName(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip;
        }
    }

    protected void getOwnerTypes(AuthenticatedUser user) throws AccessDeniedException {
        commonAppService.getOwnerTypes(user);
    }

    protected void sendNotifications(AuthenticatedUser user) throws AccessDeniedException {
        try {
            dnsNotificationAppService.sendNotifications(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void viewUserDeposit(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.viewUserDeposit(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findUserHistoricalDeposits(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.findUserHistoricalDeposits(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getRequestPrice(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.getRequestPrice(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getDomainPricing(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.getDomainPricing(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getVatRate(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.getVatRate(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getPrice(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.getPrice(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void addPrice(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.addPrice(user, null, null, BigDecimal.ZERO, 0, null, null, false, false, false);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void modifyPrice(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.modifyPrice(user, 0, null, BigDecimal.ZERO, 0, null, null, false, false, false);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void addVatRate(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.addVatRate(user, null, null, BigDecimal.ZERO);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void invalidateVat(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.invalidate(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getValidVat(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.getValid(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getVatCategories(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.getVatCategories(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getTopUpHistory(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.getTopUpHistory(user, null, null, 0, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void sendEmailWithInvoices(AuthenticatedUser user, String invoiceNumber) throws AccessDeniedException {
        try {
            paymentAppService.sendEmailWithInvoices(user, invoiceNumber);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findOwnReservations(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.findOwnReservations(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findDomainReservations(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        try {
            paymentAppService.findDomainReservations(user, domainName, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getNotSettledReservationsTotals(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.getNotSettledReservationsTotals(user, false);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findHistoricalReservations(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.findHistoricalReservations(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getSettledTransactionHistory(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.getSettledTransactionHistory(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getTransactionToReauthorise(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.getTransactionToReauthorise(user, 0, 10, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findHistoricalTransactions(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.findHistoricalTransactions(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findUserInvoices(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.findUserInvoices(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getInvoiceInfo(AuthenticatedUser user, String invoiceNumber) throws AccessDeniedException {
        try {
            paymentAppService.getInvoiceInfo(user, invoiceNumber);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findDeposits(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.findDeposits(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findDepositWithHistory(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.findDepositWithHistory(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getReadyADPTransactionsReport(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.getReadyADPTransactionsReport(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findInvoices(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.findInvoices(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findExtendedReservations(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.findExtendedReservations(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findAllPrices(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.findAllPrices(user, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void viewDeposit(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.viewDeposit(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void correctDeposit(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.correctDeposit(user, null, BigDecimal.ZERO, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void depositFundsOffline(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.depositFundsOffline(user, null, BigDecimal.ZERO, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findAllTransactions(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.findAllTransactions(user, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findTransactionsToCleanup(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.findTransactionsToCleanup(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void cleanupTransaction(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.cleanupTransaction(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void autorenewAll(AuthenticatedUser user) throws AccessDeniedException {
        try {
            paymentAppService.autorenewAll(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void generateInvoice(AuthenticatedUser user) throws AccessDeniedException {
        try {
            invoicingAppService.generateInvoice(user, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void invalidateTransactionIfNeeded(AuthenticatedUser user) throws AccessDeniedException {
        try {
            invoicingAppService.invalidateTransactionIfNeeded(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void settleTransaction(AuthenticatedUser user) throws AccessDeniedException {
        try {
            invoicingAppService.settleTransaction(user, null, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void setTransactionStartedSettlement(AuthenticatedUser user) throws AccessDeniedException {
        try {
            invoicingAppService.setTransactionStartedSettlement(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void isTransferPossible(AuthenticatedUser user) throws AccessDeniedException {
        try {
            commonAppService.isTransferPossible(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void zoneCommit(AuthenticatedUser user) throws AccessDeniedException {
        try {
            commonAppService.zoneCommit(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void zonePublished(AuthenticatedUser user) throws AccessDeniedException {
        try {
            commonAppService.zonePublished(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void zoneUnpublished(AuthenticatedUser user) throws AccessDeniedException {
        try {
            commonAppService.zoneUnpublished(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void createBulkTransferProcess(AuthenticatedUser user) throws AccessDeniedException {
        try {
            bulkTransferAppService.createBulkTransferProcess(user, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void addDomains(AuthenticatedUser user) throws AccessDeniedException {
        try {
            bulkTransferAppService.addDomains(user, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Throwable e) {
            //skip
        }
    }

    protected void findTransfers(AuthenticatedUser user) throws AccessDeniedException {
        try {
            bulkTransferAppService.findTransfers(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getTransferRequest(AuthenticatedUser user) throws AccessDeniedException {
        try {
            bulkTransferAppService.getTransferRequest(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void removeDomain(AuthenticatedUser user) throws AccessDeniedException {
        try {
            bulkTransferAppService.removeDomain(user, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void closeTransferRequest(AuthenticatedUser user) throws AccessDeniedException {
        try {
            bulkTransferAppService.closeTransferRequest(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void forceCloseTransferRequest(AuthenticatedUser user) throws AccessDeniedException {
        try {
            bulkTransferAppService.forceCloseTransferRequest(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void transferAll(AuthenticatedUser user) throws AccessDeniedException {
        try {
            bulkTransferAppService.transferAll(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void transferValid(AuthenticatedUser user) throws AccessDeniedException {
        try {
            bulkTransferAppService.transferValid(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void view(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        try {
            domainAppService.view(user, domainName);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void viewPlain(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        try {
            domainAppService.viewPlain(user, domainName);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void edit(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        try {
            domainAppService.edit(user, domainName);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void save(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        try {
            Domain d = domainDAO.get(domainName);
            domainAppService.save(user, d);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void searchForDomainsByNicHandle(AuthenticatedUser user, String nicHandle) throws AccessDeniedException {
        try {
            DomainSearchCriteria criteria = new DomainSearchCriteria();
            criteria.setBillingNH(nicHandle);
            domainAppService.search(user, criteria, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void searchForDomainsByAccountId(AuthenticatedUser user, long accountId) throws AccessDeniedException {
        try {
            DomainSearchCriteria criteria = new DomainSearchCriteria();
            criteria.setAccountId(accountId);
            domainAppService.search(user, criteria, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void searchForDomainsEmptyCriteria(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.search(user, new DomainSearchCriteria(), 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void searchFull(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.searchFull(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void searchFullWithLockingActive(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.searchFullWithLockingActive(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findExtendedOwnDomains(AuthenticatedUser user) throws AccessDeniedException {
        try {
            ExtendedDomainSearchCriteria criteria = new ExtendedDomainSearchCriteria();
            criteria.setBillingNH(user.getUsername());
            domainAppService.findExtended(user, criteria, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findExtendedDomainsOwnAccount(AuthenticatedUser user) throws AccessDeniedException {
        try {
            long accountId = nicHandleDAO.get(user.getUsername()).getAccount().getId();
            ExtendedDomainSearchCriteria criteria = new ExtendedDomainSearchCriteria();
            criteria.setAccountId(accountId);
            domainAppService.findExtended(user, criteria, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findExtendedNotOwnDomains(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.findExtended(user, new ExtendedDomainSearchCriteria(), 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void isEventValid(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        try {
            domainAppService.isEventValid(user, domainName, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void checkAvailability(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.checkAvailability(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void forceDSMEvent(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.forceDSMEvent(user, null, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void forceDSMState(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.forceDSMState(user, null, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getDsmStates(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.getDsmStates(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void updateHolderType(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        try {
            domainAppService.updateHolderType(user, domainName, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void lock(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.lock(user, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void unlock(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.unlock(user, null, null, false);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void revertToBillable(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        try {
            domainAppService.revertToBillable(user, Arrays.asList(domainName));
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void checkPayAvailable(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        try {
            domainAppService.checkPayAvailable(user, Arrays.asList(domainName));
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void modifyRenewalMode(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        try {
            domainAppService.modifyRenewalMode(user, Arrays.asList(domainName), null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findDomainsToAuthCodeCleanup(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.findDomainsToAuthCodeCleanup(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findDomainsToAuthCodePortalCleanup(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.findDomainsToAuthCodePortalCleanup(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void authCodeCleanup(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.authCodeCleanup(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void authCodePortalCleanup(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.authCodePortalCleanup(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void bulkExportAuthCodes(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.bulkExportAuthCodes(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findDomainAutorenewals(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.findDomainAutorenewals(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findDomainCountForContact(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.findDomainCountForContact(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findDomainsForCurrentRenewal(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.findDomainsForCurrentRenewal(user, null, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findDomainsForFutureRenewal(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.findDomainsForFutureRenewal(user, 0, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findOwnDomain(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.findOwn(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findOwnPlainDomain(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.findOwnPlain(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findOwnExtendedDomain(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.findOwnExtended(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findTransferredInDomains(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.findTransferredInDomains(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findTransferredAwayDomains(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.findTransferredAwayDomains(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getAllDomains(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        try {
            domainAppService.getAll(user, Arrays.asList(domainName));
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getNsReports(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.getNsReports(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void isCharity(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.isCharity(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void rollLockRenewalDates(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.rollLockRenewalDates(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void runDeleteProcess(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.runDeleteProcess(user, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void runDeletionDatePasses(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.runDeletionDatePasses(user, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void runNotificationProcess(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.runNotificationProcess(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void runRenewalDatePasses(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.runRenewalDatePasses(user, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void runSuspensionDatePasses(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.runSuspensionDatePasses(user, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void sendAuthCodeByEmailAsHostmaster(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.sendAuthCodeByEmail(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void sendWhoisDataEmail(AuthenticatedUser user) throws AccessDeniedException {
        try {
            domainAppService.sendWhoisDataEmail(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void validateDomainToModify(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        try {
            domainAppService.validateDomainToModify(user, domainName, false);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findTotalDomains(AuthenticatedUser user) throws AccessDeniedException {
        try {
            reportsAppService.findTotalDomains(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findTotalDomainsPerDate(AuthenticatedUser user) throws AccessDeniedException {
        try {
            reportsAppService.findTotalDomainsPerDate(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findTotalDomainsPerClass(AuthenticatedUser user) throws AccessDeniedException {
        try {
            reportsAppService.findTotalDomainsPerClass(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void searchForReports(AuthenticatedUser user) throws AccessDeniedException {
        try {
            reportsAppService.search(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void historyTicket(AuthenticatedUser user, long ticketId) throws AccessDeniedException {
        try {
            ticketAppService.history(user, ticketId);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void reviseTicket(AuthenticatedUser user, long ticketId) throws AccessDeniedException {
        try {
            ticketAppService.revise(user, ticketId);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void checkOutTicket(AuthenticatedUser user) throws AccessDeniedException {
        try {
            ticketAppService.checkOut(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void checkInTicket(AuthenticatedUser user) throws AccessDeniedException {
        try {
            ticketAppService.checkIn(user, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void alterStatusTicket(AuthenticatedUser user) throws AccessDeniedException {
        try {
            ticketAppService.alterStatus(user, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void reassignTicket(AuthenticatedUser user) throws AccessDeniedException {
        try {
            ticketAppService.reassign(user, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void acceptTicket(AuthenticatedUser user) throws AccessDeniedException {
        try {
            ticketAppService.accept(user, 0, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void rejectTicket(AuthenticatedUser user) throws AccessDeniedException {
        try {
            ticketAppService.reject(user, 0, null, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findTicketsToCleanup(AuthenticatedUser user) throws AccessDeniedException {
        try {
            ticketAppService.findTicketsToCleanup(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findTicketNotifications(AuthenticatedUser user) throws AccessDeniedException {
        try {
            ticketAppService.findTicketNotifications(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void sendTicketExpirationEmail(AuthenticatedUser user) throws AccessDeniedException {
        try {
            ticketAppService.sendTicketExpirationEmail(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void updateAsAdminTicket(AuthenticatedUser user, long ticketId) throws AccessDeniedException {
        try {
            ticketAppService.updateAsAdmin(user, ticketId, null, null, null, false, false);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void updateAsOwnerTicket(AuthenticatedUser user, long ticketId) throws AccessDeniedException {
        try {
            ticketAppService.updateAsOwner(user, ticketId, null, null, null, false, false);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getTicketForDomain(AuthenticatedUser user, String domainName) throws AccessDeniedException{
        try {
            ticketAppService.getTicketForDomain(user, domainName);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void findOwnTickets(AuthenticatedUser user) throws AccessDeniedException {
        try {
            ticketAppService.findOwn(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void searchForTickets(AuthenticatedUser user) throws AccessDeniedException {
        try {
            ticketAppService.search(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void saveTicket(AuthenticatedUser user, long ticketId) throws AccessDeniedException {
        try {
            ticketAppService.save(user, ticketId, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getAdminPassedTickets(AuthenticatedUser user) throws AccessDeniedException {
        try {
            triplePassAppService.getAdminPassedTickets(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void performFinancialCheck(AuthenticatedUser user) throws AccessDeniedException {
        try {
            triplePassAppService.performFinancialCheck(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void performTechnicalCheck(AuthenticatedUser user) throws AccessDeniedException {
        try {
            triplePassAppService.performTechnicalCheck(user, 0, false);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void performTicketCancellation(AuthenticatedUser user) throws AccessDeniedException {
        try {
            triplePassAppService.performTicketCancellation(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void promoteModificationTicket(AuthenticatedUser user) throws AccessDeniedException {
        try {
            triplePassAppService.promoteModificationTicket(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void promoteTicketToDomain(AuthenticatedUser user) throws AccessDeniedException {
        try {
            triplePassAppService.promoteTicketToDomain(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void promoteTransferTicket(AuthenticatedUser user) throws AccessDeniedException {
        try {
            triplePassAppService.promoteTransferTicket(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getEntries(AuthenticatedUser user) throws AccessDeniedException {
        try {
            configAppService.getEntries(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void updateEntry(AuthenticatedUser user) throws AccessDeniedException {
        try {
            configAppService.updateEntry(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void createEntry(AuthenticatedUser user) throws AccessDeniedException {
        try {
            configAppService.createEntry(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getEntry(AuthenticatedUser user) throws AccessDeniedException {
        try {
            configAppService.getEntry(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getAccount(AuthenticatedUser user, long accountId) throws AccessDeniedException {
        try {
            accountAppService.get(user, accountId);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void historyAccount(AuthenticatedUser user) throws AccessDeniedException {
        try {
            accountAppService.history(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void alterStatusAccount(AuthenticatedUser user) throws AccessDeniedException {
        try {
            accountAppService.alterStatus(user, 0, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void saveAccount(AuthenticatedUser user) throws AccessDeniedException {
        try {
            accountAppService.save(user, null, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void saveAccountFlagChanged(AuthenticatedUser user) throws AccessDeniedException {
        try {
            Account account = accountDAO.get(262L);
            account.setAgreementSigned(!account.isAgreementSigned());
            accountAppService.save(user, account, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void createAccount(AuthenticatedUser user) throws AccessDeniedException {
        try {
            accountAppService.create(user, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getNicHandle(AuthenticatedUser user, String nh) throws AccessDeniedException {
        try {
            nicHandleAppService.get(user, nh);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
            e.printStackTrace();
        }
    }

    protected void historyNicHandle(AuthenticatedUser user, String nh) throws AccessDeniedException {
        try {
            nicHandleAppService.history(user, nh, 0, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void alterStatusNicHandle(AuthenticatedUser user) throws AccessDeniedException {
        try {
            nicHandleAppService.alterStatus(user, null, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void modifyNicHandleOwnAccount(AuthenticatedUser user, String nh) throws AccessDeniedException {
        try {
            NewNicHandle nic = new NewNicHandle(null, null, null, null, 0, 0, null, null, null, null);
            nicHandleAppService.modifyNicHandleOwnAccount(user, nh, nic, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void modifyNicHandleAsHostmaster(AuthenticatedUser user, String nh) throws AccessDeniedException {
        try {
            NewNicHandle nic = new NewNicHandle(null, null, null, null, 0, 0, null, null, null, null);
            nicHandleAppService.modifyNicHandle(user, nh, 0L, nic, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void saveNewPassword(AuthenticatedUser user, String nh) throws AccessDeniedException {
        try {
            nicHandleAppService.saveNewPassword(user, nh, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void changePassword(AuthenticatedUser user, String nh) throws AccessDeniedException {
        try {
            nicHandleAppService.changePassword(user, nh, null, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void resetPassword(AuthenticatedUser user, String nh, String ip) throws AccessDeniedException {
        try {
            nicHandleAppService.resetPassword(user, nh, ip);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void createNicHandle(AuthenticatedUser user) throws AccessDeniedException {
        try {
            nicHandleAppService.createNicHandle(user, 101L, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void createNicHandleOwnAccount(AuthenticatedUser user) throws AccessDeniedException {
        try {
            nicHandleAppService.createNicHandleOwnAccount(user, null, null, false);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getDefaults(AuthenticatedUser user) throws AccessDeniedException {
        try {
            nicHandleAppService.getDefaults(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void saveDefaults(AuthenticatedUser user) throws AccessDeniedException {
        try {
            nicHandleAppService.saveDefaults(user, null, null, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void removeUserPermission(AuthenticatedUser user) throws AccessDeniedException {
        try {
            nicHandleAppService.removeUserPermission(user, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void addUserPermission(AuthenticatedUser user, String nicHandleId) throws AccessDeniedException {
        try {
            nicHandleAppService.addUserPermission(user, nicHandleId, "Passw0rd!");
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
            e.printStackTrace();

        }
    }

    protected void getUser(AuthenticatedUser user, String nicHandleId) throws AccessDeniedException {
        try {
            userAppService.getUser(user, nicHandleId);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getUserLevel(AuthenticatedUser user) throws AccessDeniedException {
        try {
            userAppService.getUserLevel(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getUserHistory(AuthenticatedUser user) throws AccessDeniedException {
        try {
            userAppService.getHistory(user, null, 0, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void isTfaUsed(AuthenticatedUser user) throws AccessDeniedException {
        try {
            userAppService.isTfaUsed(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void changePermissionGroups(AuthenticatedUser user) throws AccessDeniedException {
        try {
            userAppService.changePermissionGroups(user, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void changeTfaAsUser(AuthenticatedUser user) throws AccessDeniedException {
        try {
            userAppService.changeTfa(user, false);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void changeTfaAsHostmaster(AuthenticatedUser user, String nicHandleId) throws AccessDeniedException {
        try {
            nicHandleAppService.changeTfa(user, nicHandleId, false);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void canBeABillingContact(AuthenticatedUser user, String nicHandleId) throws AccessDeniedException {
        try {
            nicHandleAppService.canBeABillingContact(user, nicHandleId);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void cleanupLoginAttempts(AuthenticatedUser user) throws AccessDeniedException {
        try {
            nicHandleAppService.cleanupLoginAttempts(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void cleanupResetPassword(AuthenticatedUser user) throws AccessDeniedException {
        try {
            nicHandleAppService.cleanupResetPassword(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void removeDeletedNichandles(AuthenticatedUser user) throws AccessDeniedException {
        try {
            nicHandleAppService.removeDeletedNichandles(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void searchForNicHandles(AuthenticatedUser user, NicHandleSearchCriteria criteria)
            throws AccessDeniedException {
        try {
            nicHandleAppService.search(user, criteria, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            //skip
        }
    }

    protected void getTransactionInfoByOrderId(AuthenticatedUser user, String orderId) throws AccessDeniedException {
        try {
            paymentAppService.getTransactionInfo(user, orderId);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void getTransactionInfoById(AuthenticatedUser user, long transactionId) throws AccessDeniedException {
        try {
            paymentAppService.getTransactionInfo(user, transactionId);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void findDeletedDomains(AuthenticatedUser user, DeletedDomainSearchCriteria crit)
            throws AccessDeniedException {
        try {
            domainAppService.findDeletedDomains(user, crit, 0, 1, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void findOwnDeletedDomains(AuthenticatedUser user, DeletedDomainSearchCriteria crit)
            throws AccessDeniedException {
        try {
            domainAppService.findOwnDeletedDomains(user, crit, 0, 1, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void addDocument(AuthenticatedUser user)
            throws AccessDeniedException {
        try {
            documentAppService.add(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void deleteDocumentFile(AuthenticatedUser user)
            throws AccessDeniedException {
        try {
            documentAppService.deleteDocumentFile(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void documentFileExists(AuthenticatedUser user)
            throws AccessDeniedException {
        try {
            documentAppService.documentFileExists(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void findDocuments(AuthenticatedUser user)
            throws AccessDeniedException {
        try {
            documentAppService.findDocuments(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void getDocument(AuthenticatedUser user)
            throws AccessDeniedException {
        try {
            documentAppService.get(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void getDocumentSettings(AuthenticatedUser user)
            throws AccessDeniedException {
        try {
            documentAppService.getDocumentSettings(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void getNewDocuments(AuthenticatedUser user)
            throws AccessDeniedException {
        try {
            documentAppService.getNewDocuments(user, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void getPath(AuthenticatedUser user)
            throws AccessDeniedException {
        try {
            documentAppService.getPath(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void handleMailUpload(AuthenticatedUser user)
            throws AccessDeniedException {
        try {
            documentAppService.handleMailUpload(user, null, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void handleUpload(AuthenticatedUser user)
            throws AccessDeniedException {
        try {
            documentAppService.handleUpload(user, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void updateDocument(AuthenticatedUser user)
            throws AccessDeniedException {
        try {
            documentAppService.update(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void canDomainBePurchased(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.canDomainBePurchased(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void canDomainBeSold(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        try {
            secondaryMarketAppService.canDomainBeSold(user, domainName);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void findBuyRequests(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.findBuyRequests(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void findOwnBuyRequests(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.findOwnBuyRequests(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void findSellRequests(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.findSellRequests(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void findOwnSellRequests(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.findOwnSellRequests(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void findHistoricalBuyRequests(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.findHistoricalBuyRequests(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void findHistoricalSellRequests(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.findHistoricalSellRequests(user, null, 0, 0, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void getBuyRequest(AuthenticatedUser user, long buyRequestId) throws AccessDeniedException {
        try {
            secondaryMarketAppService.getBuyRequest(user, buyRequestId);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void getSellRequest(AuthenticatedUser user, long sellRequestId) throws AccessDeniedException {
        try {
            secondaryMarketAppService.getSellRequest(user, sellRequestId);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void getBuyRequestHistory(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.getBuyRequestHistory(user, 0L);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void getHistoricalBuyRequest(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.getHistoricalBuyRequest(user, 0L);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void getHistoricalSellRequest(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.getHistoricalSellRequest(user, 0L);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void registerBuyRequest(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.registerBuyRequest(user, null, null, 1L, null, null, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void registerSellRequest(AuthenticatedUser user, String domainName) throws AccessDeniedException {
        try {
            secondaryMarketAppService.registerSellRequest(user, domainName, null, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void verifySecondaryMarketAuthCode(AuthenticatedUser user, String domainName)
            throws AccessDeniedException {
        try {
            secondaryMarketAppService.verifyAuthCode(user, domainName, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void regenerateAndResendAuthCode(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.regenerateAndResendAuthCode(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void modifyBuyRequest(AuthenticatedUser user, long buyRequestId) throws AccessDeniedException {
        try {
            secondaryMarketAppService.modifyBuyRequest(user, buyRequestId, null, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void modifyBuyRequestAsHostmaster(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.modifyBuyRequestAsHostmaster(user, 2L, null, 1L, 1L, null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void acceptBuyRequest(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.acceptBuyRequest(user, 2L, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void cancelBuyRequest(AuthenticatedUser user, long buyRequestId) throws AccessDeniedException {
        try {
            secondaryMarketAppService.cancelBuyRequest(user, buyRequestId);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void saveBuyRequest(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.saveBuyRequest(user, 1, null, null, null, null, null, null, null, null, null, null,
                    null, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void rejectBuyRequest(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.rejectBuyRequest(user, 1, null, null, null, null, null, null, null, null, null,
                    null, null, null, null, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void isBuyRequestUsedInSale(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.isBuyRequestUsedInSale(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void invalidateBuyRequest(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.invalidateBuyRequest(user, 0);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void checkoutBuyRequest(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.checkoutBuyRequest(user, 2);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void checkinBuyRequest(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.checkinBuyRequest(user, 2, BuyRequestStatus.RENEW);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void reassignBuyRequest(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.reassignBuyRequest(user, 2, "IDL2-IEDR");
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void cancelSellRequest(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.cancelSellRequest(user, 0L);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void findSellRequestsToComplete(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.findSellRequestsToComplete(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void completeSellRequest(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.completeSellRequest(user, 0L);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void findBuyRequestNotifications(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.findBuyRequestNotifications(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void findBuyRequestsWithCompletedSales(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.findBuyRequestsWithCompletedSales(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void sendBuyRequestNotification(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.sendBuyRequestNotification(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void findBuyRequestsWithExpiredAuthCode(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.findBuyRequestsWithExpiredAuthCode(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void deleteBuyRequestWithCompletedSale(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.deleteBuyRequestWithCompletedSale(user, 0L);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void deleteBuyRequestWithExpiredAuthCode(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.deleteBuyRequestWithExpiredAuthCode(user, 0L);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void findBuyRequestsToCleanup(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.findBuyRequestsToCleanup(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void cleanupBuyRequest(AuthenticatedUser user) throws AccessDeniedException {
        try {
            secondaryMarketAppService.cleanupBuyRequest(user, 0L, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void getEmailGroup(AuthenticatedUser user) throws AccessDeniedException {
        try {
            emailGroupAppService.get(user, 1);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void updateEmailGroup(AuthenticatedUser user) throws AccessDeniedException {
        try {
            emailGroupAppService.update(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void createEmailGroup(AuthenticatedUser user) throws AccessDeniedException {
        try {
            emailGroupAppService.create(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void deleteEmailGroup(AuthenticatedUser user) throws AccessDeniedException {
        try {
            emailGroupAppService.delete(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void getAllEmailGroups(AuthenticatedUser user) throws AccessDeniedException {
        try {
            emailGroupAppService.getAllGroups(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void searchForEmailGroups(AuthenticatedUser user) throws AccessDeniedException {
        try {
            emailGroupAppService.search(user, null, 0, 1, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void getEmailTemplate(AuthenticatedUser user) throws AccessDeniedException {
        try {
            emailTemplateAppService.get(user, 1);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void saveEditableFieldsInEmailTemplate(AuthenticatedUser user) throws AccessDeniedException {
        try {
            emailTemplateAppService.saveEditableFields(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void searchForEmailTemplates(AuthenticatedUser user) throws AccessDeniedException {
        try {
            emailTemplateAppService.search(user, null, 0, 1, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void getAllFor(AuthenticatedUser user) throws AccessDeniedException {
        try {
            emailDisablerAppService.getAllFor(user);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void getAllOfEmailGroup(AuthenticatedUser user) throws AccessDeniedException {
        try {
            emailDisablerAppService.getAllOfEmailGroup(user, 1);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void getAllOfTemplate(AuthenticatedUser user) throws AccessDeniedException {
        try {
            emailDisablerAppService.getAllOfTemplate(user, 1);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }

    protected void modifySuppressionMode(AuthenticatedUser user) throws AccessDeniedException {
        try {
            emailDisablerAppService.modifySuppressionMode(user, null);
        } catch (AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            // skip
        }
    }
}
