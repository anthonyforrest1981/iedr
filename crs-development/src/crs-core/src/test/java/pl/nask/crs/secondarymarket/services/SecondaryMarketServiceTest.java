package pl.nask.crs.secondarymarket.services;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.Sets;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.dao.AccountDAO;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.app.tickets.exceptions.DomainHolderMandatoryException;
import pl.nask.crs.app.tickets.exceptions.DomainHolderTooLongException;
import pl.nask.crs.commons.AuthcodeGenerator;
import pl.nask.crs.commons.DateTestHelper;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.service.ContactPasswordEmailParameters;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.impl.EmailTemplateSenderImpl;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.InvalidEmailException;
import pl.nask.crs.country.*;
import pl.nask.crs.documents.Document;
import pl.nask.crs.documents.search.DocumentSearchCriteria;
import pl.nask.crs.documents.service.DocumentService;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.PlainDomain;
import pl.nask.crs.domains.SecondaryMarketStatus;
import pl.nask.crs.domains.dao.DomainDAO;
import pl.nask.crs.domains.dao.HistoricalDomainDAO;
import pl.nask.crs.domains.dao.PlainDomainDAO;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.domains.search.PlainDomainSearchCriteria;
import pl.nask.crs.domains.services.impl.DomainEmailParameters;
import pl.nask.crs.entities.EntityCategory;
import pl.nask.crs.entities.EntityClass;
import pl.nask.crs.entities.OwnerType;
import pl.nask.crs.entities.exceptions.CharityOwnerTypeNotAllowed;
import pl.nask.crs.entities.exceptions.ClassDoesNotMatchCategoryException;
import pl.nask.crs.entities.exceptions.OwnerTypeNotExistException;
import pl.nask.crs.entities.service.EntityService;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.dao.HistoricalNicHandleDAO;
import pl.nask.crs.nichandle.dao.NicHandleDAO;
import pl.nask.crs.nichandle.email.NicHandleEmailParameters;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.exceptions.NotEnoughDepositFundsException;
import pl.nask.crs.payment.service.DepositService;
import pl.nask.crs.payment.service.PaymentSearchService;
import pl.nask.crs.secondarymarket.AbstractContextAwareTest;
import pl.nask.crs.secondarymarket.*;
import pl.nask.crs.secondarymarket.dao.*;
import pl.nask.crs.secondarymarket.emails.BuyRequestExpirationEmailParameters;
import pl.nask.crs.secondarymarket.emails.SecondaryMarketPaymentEmailParameters;
import pl.nask.crs.secondarymarket.emails.SecondaryMarketRequestEmailParameters;
import pl.nask.crs.secondarymarket.exceptions.*;
import pl.nask.crs.secondarymarket.search.BuyRequestSearchCriteria;
import pl.nask.crs.secondarymarket.search.HistoricalBuyRequestSearchCriteria;
import pl.nask.crs.secondarymarket.search.HistoricalSellRequestSearchCriteria;
import pl.nask.crs.secondarymarket.search.SellRequestSearchCriteria;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.AuthenticationService;
import pl.nask.crs.ticket.operation.FailureReason;
import pl.nask.crs.ticket.services.TicketSearchService;
import pl.nask.crs.ticket.services.TicketService;
import pl.nask.crs.user.User;
import pl.nask.crs.user.dao.UserDAO;
import pl.nask.crs.user.service.AuthorizationGroupsFactory;
import pl.nask.crs.vat.PriceWithVat;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import static org.testng.Assert.*;
import static pl.nask.crs.commons.DateTestHelper.assertEqualDates;
import static pl.nask.crs.payment.testhelp.PaymentTestHelp.compareSettledReservation;
import static pl.nask.crs.payment.testhelp.PaymentTestHelp.compareTransactions;
import static pl.nask.crs.payment.testhelp.PaymentTestHelp.createBasicCreditCard;
import static pl.nask.crs.secondarymarket.SecondaryMarketHelper.*;
import static pl.nask.crs.ticket.operation.FailureReason.INCORRECT;

public class SecondaryMarketServiceTest extends AbstractContextAwareTest {

    @Autowired
    BuyRequestDAO buyRequestDAO;

    @Autowired
    HistoricalBuyRequestDAO historicalBuyRequestDAO;

    @Autowired
    SellRequestDAO sellRequestDAO;

    @Autowired
    HistoricalSellRequestDAO historicalSellRequestDAO;

    @Autowired
    DomainDAO domainDAO;

    @Autowired
    PlainDomainDAO plainDomainDAO;

    @Autowired
    HistoricalDomainDAO historicalDomainDAO;

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    NicHandleDAO nicHandleDAO;

    @Autowired
    HistoricalNicHandleDAO historicalNicHandleDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    AuthorizationGroupsFactory groupsFactory;

    @Autowired
    BuyRequestNotificationDAO buyRequestNotificationDAO;

    @Autowired
    DocumentService documentService;

    @Mocked
    private EmailTemplateSenderImpl sender;

    @Autowired
    SecondaryMarketService secondaryMarketService;

    @Autowired
    PaymentSearchService paymentSearchService;

    @Autowired
    TicketSearchService ticketSearchService;

    @Autowired
    TicketService ticketService;

    @Autowired
    DepositService depositService;

    @Autowired
    EntityService entityService;

    @Autowired
    CountryFactory countryFactory;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    ApplicationConfig applicationConfig;

    private AuthenticatedUser user;
    private OpInfo opInfo;

    @BeforeClass
    public void setupUserAndOpInfo() throws Exception {
        user = getAuthenticatedUser("IDL2-IEDR");
        opInfo = new OpInfo(user, "remark");
    }

    @Test
    public void testCanDomainBePurchased() throws Exception {
        String directNH = "SAM-IEDR";
        String registrarNH = "APITEST-IEDR";

        assertTrue(secondaryMarketService.canDomainBePurchased("futuredomena.ie", directNH), "Some registrar's domain");
        assertTrue(secondaryMarketService.canDomainBePurchased("pizzaonline.ie", directNH), "Some registrar's domain");
        assertTrue(secondaryMarketService.canDomainBePurchased("directdomain.ie", directNH), "Direct's domain");
        assertFalse(secondaryMarketService.canDomainBePurchased("paydomain4.ie", directNH), "Domain in NRP");
        assertFalse(secondaryMarketService.canDomainBePurchased("nonexistent.ie", directNH), "Nonexistent domain");

        assertTrue(secondaryMarketService.canDomainBePurchased("futuredomena.ie", registrarNH),
                "Same registrar's domain");
        assertFalse(secondaryMarketService.canDomainBePurchased("pizzaonline.ie", registrarNH),
                "Another registrar's domain");
        assertFalse(secondaryMarketService.canDomainBePurchased("directdomain.ie", registrarNH), "Direct's domain");
        assertFalse(secondaryMarketService.canDomainBePurchased("paydomain4.ie", registrarNH),
                "Domain in NRP (same account)");
        assertFalse(secondaryMarketService.canDomainBePurchased("nonexistent.ie", registrarNH), "Nonexistent domain");
    }

    @Test
    public void testCanDomainBeSold() throws Exception {
        assertTrue(secondaryMarketService.canDomainBeSold("sec-mrkt-dir-from-dir.ie", "AAG061-IEDR"),
                "Direct's Domain");
        assertTrue(secondaryMarketService.canDomainBeSold("sec-mrkt-dir-from-reg.ie", "APITEST-IEDR"),
                "Registrar's Domain");
        assertFalse(secondaryMarketService.canDomainBeSold("sec-mrkt-domain-1.ie", "IDL2-IEDR"),
                "Domain with a sell request");
        assertFalse(secondaryMarketService.canDomainBeSold("pizzaonline.ie", "IDL2-IEDR"),
                "Domain without buy requests");
        assertFalse(secondaryMarketService.canDomainBeSold("sec-mrkt-nrp.ie", "IDL2-IEDR"), "Domain in NRP");
        assertFalse(secondaryMarketService.canDomainBeSold("nonexistent.ie", "IDL2-IEDR"), "Nonexistent domain");
    }

    @Test
    public void testGetSellRequest() throws Exception {
        BuyRequest buyRequest = secondaryMarketService.getBuyRequest(2L);
        Date date = DateTestHelper.setHour(DateUtils.addDays(new Date(), -1), 12, 54, 1, 0);
        SellRequest sellRequest = new SellRequest("IDL2-IEDR", date, buyRequest);
        sellRequest.setId(2L);
        SellRequest dbRequest = secondaryMarketService.getSellRequest(2L);
        assertNotNull(dbRequest);
        assertEqualSellRequests(dbRequest, sellRequest);
    }

    @Test
    public void testGetHistoricalBuyRequest() throws Exception {
        BuyRequest buyRequest = secondaryMarketService.getBuyRequest(8L);
        Date changeDate = DateTestHelper.setHour(new Date(), 10, 15, 28, 0);
        HistoricalObject<BuyRequest> historicalBuyRequest = new HistoricalObject<>(8L, buyRequest, changeDate,
                "AAG061-IEDR");
        HistoricalObject<BuyRequest> dbRequest = secondaryMarketService.getHistoricalBuyRequest(8L);
        assertNotNull(dbRequest);
        assertEqualHistoricalBuyRequests(dbRequest, historicalBuyRequest);
    }

    @Test
    public void testGetBuyRequestHistory() throws Exception {
        List<HistoricalObject<BuyRequest>> list = secondaryMarketService.getBuyRequestHistory(15L);
        HistoricalObject<BuyRequest> dbRequest = secondaryMarketService.getHistoricalBuyRequest(16L);
        assertEqualHistoricalBuyRequests(list.get(0), dbRequest);
        dbRequest = secondaryMarketService.getHistoricalBuyRequest(15L);
        assertEqualHistoricalBuyRequests(list.get(1), dbRequest);
    }

    @Test
    public void testGetHistoricalSellRequest() throws Exception {
        BuyRequest buyRequest = historicalBuyRequestDAO.get(1L).getObject();
        Date creationDate = DateTestHelper.setHour(DateUtils.addDays(new Date(), -1), 12, 54, 1, 0);
        SellRequest sellRequest = new SellRequest("IDL2-IEDR", creationDate, buyRequest);
        sellRequest.setId(2L);
        Date changeDate = DateTestHelper.setHour(DateUtils.addDays(new Date(), -1), 16, 19, 26, 0);
        HistoricalObject<SellRequest> historicalSellRequest = new HistoricalObject<>(1L, sellRequest, changeDate,
                "IDL2-IEDR");
        HistoricalObject<SellRequest> dbRequest = secondaryMarketService.getHistoricalSellRequest(1L);
        assertNotNull(dbRequest);
        assertEqualHistoricalSellRequests(dbRequest, historicalSellRequest);
    }

    @Test(expectedExceptions = SellRequestNotFoundException.class)
    public void testGetSellRequestNonExistent() throws Exception {
        secondaryMarketService.getSellRequest(99L);
    }

    @Test
    public void testFindBuyRequests() throws Exception {

        BuyRequestSearchCriteria criteria = new BuyRequestSearchCriteria();
        LimitedSearchResult<BuyRequest> result = secondaryMarketService.findBuyRequests(criteria, 0, 0, null);
        assertEquals(result.getTotalResults(), 25);

        criteria = new BuyRequestSearchCriteria();
        result = secondaryMarketService.findBuyRequests(criteria, 1, 20, null);
        assertEquals(result.getTotalResults(), 25);
        assertEquals(result.getResults().size(), 20);

        criteria = new BuyRequestSearchCriteria();
        result = secondaryMarketService.findBuyRequests(criteria, 1, 1, null);
        assertEquals(result.getTotalResults(), 25);
        assertEquals(result.getResults().size(), 1);

        criteria = new BuyRequestSearchCriteria();
        criteria.setDomainName("sec-mrkt-domain-1");
        checkFindBuyRequestResult(criteria, 2L);

        criteria = new BuyRequestSearchCriteria();
        criteria.setDomainName("sec-mrkt-domain-2");
        checkFindBuyRequestResult(criteria, 3L);

        criteria = new BuyRequestSearchCriteria();
        criteria.setBuyRequestId(2L);
        checkFindBuyRequestResult(criteria, 2L);

        criteria = new BuyRequestSearchCriteria();
        criteria.setBuyerAccountId(262L);
        checkFindBuyRequestResult(criteria, 13L);

        criteria = new BuyRequestSearchCriteria();
        criteria.setSellerAccountId(102L);
        checkFindBuyRequestResult(criteria, 3L);

        criteria = new BuyRequestSearchCriteria();
        criteria.setBuyerName("Admin1");
        checkFindBuyRequestResult(criteria, 2L);

        criteria = new BuyRequestSearchCriteria();
        criteria.setCreationDateFrom(DateUtils.addDays(new Date(), -21));
        criteria.setCreationDateTo(DateUtils.addDays(new Date(), -19));
        checkFindBuyRequestResult(criteria, 2L);

        criteria = new BuyRequestSearchCriteria();
        criteria.setCreationDateFrom(DateUtils.addDays(new Date(), -12));
        checkFindBuyRequestResult(criteria, 3L);

        criteria = new BuyRequestSearchCriteria();
        criteria.setCheckedOutTo("TDI2");
        checkFindBuyRequestResult(criteria, 3L);

        criteria = new BuyRequestSearchCriteria();
        criteria.setCreatorNH("IOA3-IEDR");
        checkFindBuyRequestResult(criteria, 3L);

        criteria = new BuyRequestSearchCriteria();
        criteria.setAuthCodeCreationDateFrom(DateUtils.addDays(new Date(), 1));
        checkFindBuyRequestResult(criteria, 3L);

        criteria = new BuyRequestSearchCriteria();
        criteria.setAuthCodeCreationDateTo(DateUtils.addDays(new Date(), -90));
        checkFindBuyRequestResult(criteria, 38L);
    }

    private void checkFindBuyRequestResult(BuyRequestSearchCriteria criteria, long expectedId) throws Exception {
        BuyRequest expectedRequest = secondaryMarketService.getBuyRequest(expectedId);
        List<BuyRequest> result;
        BuyRequest dbRequest;
        result = secondaryMarketService.findBuyRequests(criteria, 0, 10, null).getResults();
        assertEquals(result.size(), 1);
        dbRequest = result.get(0);
        assertEqualBuyRequests(dbRequest, expectedRequest);
        result = secondaryMarketService.findBuyRequests(criteria, null);
        assertEquals(result.size(), 1);
        dbRequest = result.get(0);
        assertEqualBuyRequests(dbRequest, expectedRequest);
    }

    @Test
    public void testFindSellRequests() throws Exception {
        SellRequestSearchCriteria criteria = new SellRequestSearchCriteria();
        LimitedSearchResult<SellRequest> limitedResult = secondaryMarketService.findSellRequests(criteria, 0, 10, null);
        assertEquals(limitedResult.getResults().size(), 9);
        assertEquals(limitedResult.getTotalResults(), 9);

        limitedResult = secondaryMarketService.findSellRequests(criteria, 0, 1, null);
        assertEquals(limitedResult.getResults().size(), 1);
        assertEquals(limitedResult.getTotalResults(), 9);

        criteria = new SellRequestSearchCriteria();
        criteria.setSellRequestId(2L);
        checkFindSellRequestResult(criteria, 2L);

        criteria = new SellRequestSearchCriteria();
        criteria.setDomainName("sec-mrkt-domain-2.ie");
        checkFindSellRequestResult(criteria, 3L);

        criteria = new SellRequestSearchCriteria();
        criteria.setCreatedFrom(DateUtils.addDays(new Date(), -1));
        checkFindSellRequestResult(criteria, 2L);

        criteria = new SellRequestSearchCriteria();
        criteria.setCreatedTo(DateUtils.addDays(new Date(), -11));
        checkFindSellRequestResult(criteria, 19L);

        criteria = new SellRequestSearchCriteria();
        criteria.setCompleteFrom(DateUtils.addDays(new Date(), 2));
        checkFindSellRequestResult(criteria, 2L);

        criteria = new SellRequestSearchCriteria();
        criteria.setCompleteTo(DateUtils.addDays(new Date(), -8));
        checkFindSellRequestResult(criteria, 19L);

        criteria = new SellRequestSearchCriteria();
        criteria.setCreatedFrom(DateUtils.addDays(new Date(), -10));
        criteria.setCompleteFrom(DateUtils.addDays(new Date(), 2));
        checkFindSellRequestResult(criteria, 2L);

        criteria = new SellRequestSearchCriteria();
        criteria.setCreatedTo(DateUtils.addDays(new Date(), 10));
        criteria.setCompleteTo(DateUtils.addDays(new Date(), -2));
        checkFindSellRequestResult(criteria, 19L);

        criteria = new SellRequestSearchCriteria();
        criteria.setCreatedFrom(DateUtils.addDays(new Date(), -1));
        criteria.setCompleteFrom(DateUtils.addDays(new Date(), -10));
        checkFindSellRequestResult(criteria, 2L);

        criteria = new SellRequestSearchCriteria();
        criteria.setCreatedTo(DateUtils.addDays(new Date(), -5));
        criteria.setCompleteTo(DateUtils.addDays(new Date(), 10));
        checkFindSellRequestResult(criteria, 19L);

        criteria = new SellRequestSearchCriteria();
        criteria.setCreatorNH("IOA3-IEDR");
        checkFindSellRequestResult(criteria, 3L);

        criteria = new SellRequestSearchCriteria();
        criteria.setDomainHolder("Second");
        checkFindSellRequestResult(criteria, 3L);

        criteria = new SellRequestSearchCriteria();
        criteria.setAccountId(262L);
        checkFindSellRequestResult(criteria, 13L);

        criteria = new SellRequestSearchCriteria();
        criteria.setContactNH("AHK693-IEDR");
        checkFindSellRequestResult(criteria, 19L);
    }

    private void checkFindSellRequestResult(SellRequestSearchCriteria criteria, long expectedId) throws Exception {
        SellRequest expectedRequest = secondaryMarketService.getSellRequest(expectedId);
        List<SellRequest> requests = secondaryMarketService.findSellRequests(criteria, 0, 10, null).getResults();
        assertEquals(requests.size(), 1);
        assertEqualSellRequests(requests.get(0), expectedRequest);
        requests = secondaryMarketService.findSellRequests(criteria, null);
        assertEquals(requests.size(), 1);
        assertEqualSellRequests(requests.get(0), expectedRequest);
    }

    @Test
    public void testFindHistoricalBuyRequests() {
        HistoricalBuyRequestSearchCriteria criteria = new HistoricalBuyRequestSearchCriteria();
        LimitedSearchResult<HistoricalObject<BuyRequest>> results = secondaryMarketService.findHistoricalBuyRequests(
                criteria, 0, 10, null);
        assertEquals(results.getResults().size(), 10);
        assertEquals(results.getTotalResults(), 30);

        criteria = new HistoricalBuyRequestSearchCriteria();
        criteria.setBuyRequestId(2L);
        checkFindHistoricalBuyRequestResult(criteria, 1L);

        criteria = new HistoricalBuyRequestSearchCriteria();
        criteria.setDomainName("sec-mrkt-domain-1.ie");
        checkFindHistoricalBuyRequestResult(criteria, 1L);

        criteria = new HistoricalBuyRequestSearchCriteria();
        criteria.setDomainHolder("Another");
        checkFindHistoricalBuyRequestResult(criteria, 13L);

        criteria = new HistoricalBuyRequestSearchCriteria();
        criteria.setAccountId(102L);
        checkFindHistoricalBuyRequestResult(criteria, 2L);

    }

    private void checkFindHistoricalBuyRequestResult(HistoricalBuyRequestSearchCriteria criteria,
            long expectedChangeId) {
        List<HistoricalObject<BuyRequest>> requests = secondaryMarketService.findHistoricalBuyRequests(criteria, 0,
                10, null).getResults();
        assertEquals(requests.size(), 1);
        HistoricalObject<BuyRequest> expected = historicalBuyRequestDAO.get(expectedChangeId);
        assertEqualHistoricalBuyRequests(requests.get(0), expected);
    }

    @Test
    public void testFindHistoricalSellRequests() {
        HistoricalSellRequestSearchCriteria criteria = new HistoricalSellRequestSearchCriteria();
        LimitedSearchResult<HistoricalObject<SellRequest>> results = secondaryMarketService
                .findHistoricalSellRequests(criteria, 0, 10, null);
        assertEquals(results.getResults().size(), 10);
        assertEquals(results.getTotalResults(), 17);

        criteria = new HistoricalSellRequestSearchCriteria();
        criteria.setSellRequestId(2L);
        checkFindHistoricalSellRequestResult(criteria, 1L);

        criteria = new HistoricalSellRequestSearchCriteria();
        criteria.setDomainName("sec-mrkt-domain-1.ie");
        checkFindHistoricalSellRequestResult(criteria, 1L);

        criteria = new HistoricalSellRequestSearchCriteria();
        criteria.setDomainHolder("Another");
        checkFindHistoricalSellRequestResult(criteria, 13L);

        criteria = new HistoricalSellRequestSearchCriteria();
        criteria.setAccountId(102L);
        checkFindHistoricalSellRequestResult(criteria, 2L);

        criteria = new HistoricalSellRequestSearchCriteria();
        criteria.setStatus(SellRequestStatus.CANCELLED);
        checkFindHistoricalSellRequestResult(criteria, 4L);

    }

    private void checkFindHistoricalSellRequestResult(HistoricalSellRequestSearchCriteria criteria,
            long expectedChangeId) {
        List<HistoricalObject<SellRequest>> requests = secondaryMarketService.findHistoricalSellRequests(criteria, 0,
                10, null).getResults();
        assertEquals(requests.size(), 1);
        HistoricalObject<SellRequest> expected = secondaryMarketService.getHistoricalSellRequest(expectedChangeId);
        assertEqualHistoricalSellRequests(requests.get(0), expected);
    }

    @Test
    public void testBuyRequestRegistrationADP() throws Exception {
        String domainName = "pizzaonline.ie";
        PaymentMethod paymentMethod = PaymentMethod.ADP;
        NewNicHandle newNicHandle = getNewNicHandle();
        registerBuyRequestAndCheck(user, domainName, newNicHandle, paymentMethod, null, opInfo);
    }

    @Test
    public void testBuyRequestRegistrationCC() throws Exception {
        String domainName = "pizzaonline.ie";
        PaymentMethod paymentMethod = PaymentMethod.CC;
        CreditCard creditCard = createBasicCreditCard();
        NewNicHandle newNicHandle = getNewNicHandle();
        registerBuyRequestAndCheck(user, domainName, newNicHandle, paymentMethod, creditCard, opInfo);
    }

    @Test
    public void testBuyRequestRegistrationEmptyCompanyName() throws Exception {
        String domainName = "pizzaonline.ie";
        PaymentMethod paymentMethod = PaymentMethod.ADP;
        NewNicHandle newNicHandle = getNewNicHandle();
        newNicHandle.setCompanyName("");
        registerBuyRequestAndCheck(user, domainName, newNicHandle, paymentMethod, null, opInfo);
    }

    @Test
    public void testTwoBuyRequests() throws Exception {
        String domainName = "pizzaonline.ie";
        PaymentMethod paymentMethod = PaymentMethod.ADP;
        NewNicHandle newNicHandle = getNewNicHandle();

        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.FIRST_BUY_REQUEST_REGISTERED.getId(),
                        withInstanceOf(SecondaryMarketRequestEmailParameters.class));
                times = 1;
                sender.sendEmail(EmailTemplateNamesEnum.BUY_REQUEST_REGISTERED.getId(),
                        withInstanceOf(SecondaryMarketRequestEmailParameters.class));
                times = 2;
            }
        };

        assertEquals(getDomainSecondaryMarketStatus(domainName), SecondaryMarketStatus.NoProcess);
        secondaryMarketService.registerBuyRequest(user, domainName, "Holder", 1L, newNicHandle,
                paymentMethod, null, opInfo);
        assertEquals(getDomainSecondaryMarketStatus(domainName), SecondaryMarketStatus.BuyRequestRegistered);
        secondaryMarketService.registerBuyRequest(user, domainName, "Holder", 1L, newNicHandle,
                paymentMethod, null, opInfo);
        assertEquals(getDomainSecondaryMarketStatus(domainName), SecondaryMarketStatus.BuyRequestRegistered);
    }

    @Test(expectedExceptions = DomainIllegalStateException.class)
    public void testDomainInNrp() throws Exception {
        String domainName = "paydomain4.ie";
        Domain domain = domainDAO.get(domainName);
        assertTrue(domain.isNRP());
        AuthenticatedUser user = getAuthenticatedUser("APITEST-IEDR");
        OpInfo opInfo = new OpInfo(user, "remark");
        secondaryMarketService.registerBuyRequest(user, domainName, "Holder", 1L, getNewNicHandle(),
                PaymentMethod.ADP, null, opInfo);
    }

    @Test(expectedExceptions = DomainNotAvailableForUserException.class)
    public void testBuyRequestDifferentRegistrars() throws Exception {
        String domainName = "pizzaonline.ie";
        AuthenticatedUser user = getAuthenticatedUser("APITEST-IEDR");
        OpInfo opInfo = new OpInfo(user, "remark");
        secondaryMarketService.registerBuyRequest(user, domainName, "Holder", 1L, getNewNicHandle(),
                PaymentMethod.ADP, null, opInfo);
    }

    @Test
    public void testBuyRequestDirectFromDirect() throws Exception {
        String domainName = "directdomain.ie";
        AuthenticatedUser user = getAuthenticatedUser("SAM-IEDR");
        OpInfo opInfo = new OpInfo(user, "remark");
        CreditCard creditCard = createBasicCreditCard();
        secondaryMarketService.registerBuyRequest(user, domainName, "Holder", 1L, getNewNicHandle(),
                PaymentMethod.CC, creditCard, opInfo);
    }

    @Test
    public void testBuyRequestDirectFromRegistrar() throws Exception {
        String domainName = "pizzaonline.ie";
        AuthenticatedUser user = getAuthenticatedUser("SAM-IEDR");
        OpInfo opInfo = new OpInfo(user, "remark");
        CreditCard creditCard = createBasicCreditCard();
        secondaryMarketService.registerBuyRequest(user, domainName, "Holder", 1L, getNewNicHandle(),
                PaymentMethod.CC, creditCard, opInfo);
    }

    @Test(expectedExceptions = CardPaymentException.class)
    public void testBuyRequestFailedCardAuthorization() throws Exception {
        String domainName = "pizzaonline.ie";
        PaymentMethod paymentMethod = PaymentMethod.CC;
        CreditCard creditCard = createBasicCreditCard();
        createExpectationForFailedCardAuthorization();
        secondaryMarketService.registerBuyRequest(user, domainName, "Holder", 1L, getNewNicHandle(), paymentMethod,
                creditCard, opInfo);
    }

    @Test(expectedExceptions = CardPaymentException.class)
    public void testBuyRequestFailedCardSettlement() throws Exception {
        String domainName = "pizzaonline.ie";
        PaymentMethod paymentMethod = PaymentMethod.CC;
        CreditCard creditCard = createBasicCreditCard();

        createExpectationForFailedCardSettlement();
        secondaryMarketService.registerBuyRequest(user, domainName, "Holder", 1L, getNewNicHandle(), paymentMethod,
                creditCard, opInfo);
    }

    @Test(expectedExceptions = CardPaymentException.class)
    public void testBuyRequestFailedCardSettlementAndCancellation() throws Exception {
        String domainName = "pizzaonline.ie";
        PaymentMethod paymentMethod = PaymentMethod.CC;
        CreditCard creditCard = createBasicCreditCard();
        createExpectationForFailedCardSettlementAndCancellation();
        secondaryMarketService.registerBuyRequest(user, domainName, "Holder", 1L, getNewNicHandle(), paymentMethod,
                creditCard, opInfo);
    }

    @Test(expectedExceptions = NotEnoughDepositFundsException.class)
    public void testBuyRequestInsufficientFunds() throws Exception {
        String domainName = "pizzaonline.ie";
        String nicHandleId = user.getUsername();
        PaymentMethod paymentMethod = PaymentMethod.ADP;
        clearDeposit(nicHandleId);

        new NonStrictExpectations() {
            {
                sender.sendEmail(anyInt, withInstanceOf(EmailParameters.class));
                times = 0;
            }
        };
        secondaryMarketService.registerBuyRequest(user, domainName, "Holder", 1L, getNewNicHandle(), paymentMethod,
                null, opInfo);
    }

    @Test(expectedExceptions = DomainNotFoundException.class)
    public void testBuyRequestNonexistentDomain() throws Exception {
        String domainName = "nonexistent.ie";
        PaymentMethod paymentMethod = PaymentMethod.ADP;
        secondaryMarketService.registerBuyRequest(user, domainName, "Holder", 1L, getNewNicHandle(), paymentMethod,
                null, opInfo);
    }

    @Test(expectedExceptions = EmptyRemarkException.class)
    public void testBuyRequestEmptyRemark() throws Exception {
        String domainName = "pizzaonline.ie";
        PaymentMethod paymentMethod = PaymentMethod.ADP;
        secondaryMarketService.registerBuyRequest(user, domainName, "Holder", 1L, getNewNicHandle(), paymentMethod,
                null, new OpInfo(user, ""));
    }

    @Test(expectedExceptions = DomainHolderMandatoryException.class)
    public void testBuyRequestEmptyHolder() throws Exception {
        testHolder("", 1L);
    }

    @Test(expectedExceptions = DomainHolderTooLongException.class)
    public void testBuyRequestHolderTooLong() throws Exception {
        String domainHolder = new String(new char[500]).replace("\0", "a");
        testHolder(domainHolder, 1L);
    }

    @Test(expectedExceptions = OwnerTypeNotExistException.class)
    public void testBuyRequestNonExistentOwnerType() throws Exception {
        testHolder("Holder", 999L);
    }

    @Test(expectedExceptions = CharityOwnerTypeNotAllowed.class)
    public void testBuyRequestCharityOwnerType() throws Exception {
        long ownerTypeId = 6L;
        OwnerType ownerType = entityService.getOwnerType(ownerTypeId);
        assertTrue(ownerType.isCharity());
        testHolder("Holder", ownerTypeId);
    }

    private void testHolder(String domainHolder, long domainOwnerType) throws Exception {
        String domainName = "pizzaonline.ie";
        PaymentMethod paymentMethod = PaymentMethod.ADP;
        secondaryMarketService.registerBuyRequest(user, domainName, domainHolder, domainOwnerType, getNewNicHandle(),
                paymentMethod, null, opInfo);
    }

    @Test(expectedExceptions = InvalidCountryException.class)
    public void testBuyRequestNonExistentCountry() throws Exception {
        String domainName = "pizzaonline.ie";
        PaymentMethod paymentMethod = PaymentMethod.ADP;
        NewNicHandle newNicHandle = getNewNicHandle();
        newNicHandle.setCountryId(9999);
        secondaryMarketService.registerBuyRequest(user, domainName, "Holder", 1L, newNicHandle, paymentMethod, null,
                opInfo);
    }

    @Test(expectedExceptions = InvalidCountyException.class)
    public void testBuyRequestNonExistentCounty() throws Exception {
        String domainName = "pizzaonline.ie";
        PaymentMethod paymentMethod = PaymentMethod.ADP;
        NewNicHandle newNicHandle = getNewNicHandle();
        newNicHandle.setCountyId(9999);
        secondaryMarketService.registerBuyRequest(user, domainName, "Holder", 1L, newNicHandle, paymentMethod, null,
                opInfo);
    }

    @Test(expectedExceptions = CountyNotExistsException.class)
    public void testBuyRequestCountryCountyMismatch() throws Exception {
        String domainName = "pizzaonline.ie";
        PaymentMethod paymentMethod = PaymentMethod.ADP;
        NewNicHandle newNicHandle = getNewNicHandle();
        newNicHandle.setCountryId(19);
        newNicHandle.setCountyId(121);
        secondaryMarketService.registerBuyRequest(user, domainName, "Holder", 1L, newNicHandle, paymentMethod, null,
                opInfo);
    }

    @Test(expectedExceptions = InvalidEmailException.class)
    public void testBuyRequestInvalidEmail() throws Exception {
        String domainName = "pizzaonline.ie";
        PaymentMethod paymentMethod = PaymentMethod.ADP;
        NewNicHandle newNicHandle = getNewNicHandle();
        newNicHandle.setEmail("ema@il@iedr.ie");
        secondaryMarketService.registerBuyRequest(user, domainName, "Holder", 1L, newNicHandle, paymentMethod, null,
                opInfo);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBuyRequestInvalidPaymentDetails() throws Exception {
        String domainName = "pizzaonline.ie";
        PaymentMethod paymentMethod = PaymentMethod.ADP;
        CreditCard creditCard = createBasicCreditCard();
        secondaryMarketService.registerBuyRequest(user, domainName, "Holder", 1L, getNewNicHandle(), paymentMethod,
                creditCard, opInfo);
    }

    @Test(expectedExceptions = NotEnoughDepositFundsException.class)
    public void testBuyRequestDirectAdpPayment() throws Exception {
        String domainName = "pizzaonline.ie";
        AuthenticatedUser user = getAuthenticatedUser("SAM-IEDR");
        OpInfo opInfo = new OpInfo(user, "remark");
        PaymentMethod paymentMethod = PaymentMethod.ADP;
        secondaryMarketService.registerBuyRequest(user, domainName, "Holder", 1L, getNewNicHandle(), paymentMethod,
                null, opInfo);
    }

    @Test
    public void testHistory() throws Exception {
        String domainName = "pizzaonline.ie";
        PaymentMethod paymentMethod = PaymentMethod.ADP;
        long buyRequestId = secondaryMarketService.registerBuyRequest(user, domainName, "Holder", 1L,
                getNewNicHandle(), paymentMethod, null, opInfo);
        BuyRequest buyRequest = secondaryMarketService.getBuyRequest(buyRequestId);
        List<HistoricalObject<BuyRequest>> history = getBuyRequestHistory(buyRequest.getId());
        assertEquals(history.size(), 1);
        HistoricalObject<BuyRequest> historicalBuyRequest = history.get(0);
        assertEquals(historicalBuyRequest.getChangedBy(), opInfo.getActorName());
        assertEquals(historicalBuyRequest.getChangeDate(), buyRequest.getCreationDate());
        assertEqualBuyRequests(historicalBuyRequest.getObject(), buyRequest);
    }

    @Test
    public void testVerifyAuthCode() throws Exception {
        assertTrue(secondaryMarketService.verifyAuthCode("sec-mrkt-domain-3.ie", "ANOTHERAUTHC"));
        assertFalse(secondaryMarketService.verifyAuthCode("sec-mrkt-domain-3.ie", "WRONGAUTHCODE"));
    }

    @Test
    public void testCancelLastBuyRequest() throws Exception {
        long buyRequestId = 24;
        testCancelBuyRequest(buyRequestId, true);
    }

    @Test
    public void testCancelNotLastBuyRequest() throws Exception {
        long buyRequestId = 23;
        testCancelBuyRequest(buyRequestId, false);
    }

    private void testCancelBuyRequest(long buyRequestId, final boolean isLast) throws Exception {
        BuyRequest buyRequest = buyRequestDAO.get(buyRequestId);
        checkIfBuyRequestIsLast(buyRequest, isLast);
        assertEquals(getDomainSecondaryMarketStatus(buyRequest.getDomainName()),
                SecondaryMarketStatus.BuyRequestRegistered);

        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.BUY_REQUEST_REMOVED.getId(),
                        withInstanceOf(SecondaryMarketRequestEmailParameters.class));
                times = 1;
                sender.sendEmail(EmailTemplateNamesEnum.LAST_BUY_REQUEST_CANCELLED.getId(),
                        withInstanceOf(DomainEmailParameters.class));
                times = isLast ? 1 : 0;
            }
        };

        assertNotEquals(buyRequest.getStatus(), BuyRequestStatus.CANCELLED);
        assertEquals(getBuyRequestHistory(buyRequestId).size(), 1);
        secondaryMarketService.cancelBuyRequest(user, opInfo, buyRequestId);
        assertNull(buyRequestDAO.get(buyRequestId));
        List<HistoricalObject<BuyRequest>> history = getBuyRequestHistory(buyRequestId);
        assertEquals(history.size(), 2);
        assertEquals(history.get(1).getObject().getStatus(), BuyRequestStatus.CANCELLED);

        assertEquals(getDomainSecondaryMarketStatus(buyRequest.getDomainName()),
                isLast ? SecondaryMarketStatus.NoProcess : SecondaryMarketStatus.BuyRequestRegistered);
    }

    @Test(expectedExceptions = BuyRequestNotFoundException.class)
    public void testCancelNonExistingBuyRequest() throws Exception {
        long buyRequestId = 999;
        assertNull(buyRequestDAO.get(buyRequestId));
        secondaryMarketService.cancelBuyRequest(user, opInfo, buyRequestId);
    }

    @Test(expectedExceptions = BuyRequestFrozenAsPassed.class)
    public void testCancelPassedBuyRequest() throws Exception {
        long buyRequestId = 2;
        BuyRequest buyRequest = buyRequestDAO.get(buyRequestId);
        assertEquals(buyRequest.getStatus(), BuyRequestStatus.PASSED);
        assertNotNull(buyRequest.getAuthcode());
        secondaryMarketService.cancelBuyRequest(user, opInfo, buyRequestId);
    }

    @Test
    public void testSellRequestRegistrationADP() throws Exception {
        registerSellRequestAndCheck(user, "sec-mrkt-domain-3.ie", "ANOTHERAUTHC", PaymentMethod.ADP, null, opInfo);
    }

    @Test
    public void testSellRequestRegistrationCC() throws Exception {
        registerSellRequestAndCheck(user, "sec-mrkt-domain-3.ie", "ANOTHERAUTHC", PaymentMethod.CC,
                createBasicCreditCard(), opInfo);
    }

    @Test(expectedExceptions = DomainNotFoundException.class)
    public void tesSellRequestNonexistentDomain() throws Exception {
        secondaryMarketService.registerSellRequest(user, "nonexistent.ie", "ANOTHERAUTHC", PaymentMethod.ADP, null,
                opInfo);
    }

    @Test(expectedExceptions = InvalidAuthCodeException.class)
    public void tesSellRequestInvalidAuthCode() throws Exception {
        secondaryMarketService.registerSellRequest(user, "sec-mrkt-domain-3.ie", "INVALIDAUTHC", PaymentMethod.ADP,
                null, opInfo);
    }

    @Test(expectedExceptions = DomainIllegalStateException.class)
    public void testSellRequestDomainInNrp() throws Exception {
        String domainName = "sec-mrkt-nrp.ie";
        Domain domain = domainDAO.get(domainName);
        assertTrue(domain.isNRP());
        secondaryMarketService.registerSellRequest(user, domainName, "AUTHCODEFOR7", PaymentMethod.ADP, null, opInfo);
    }

    @Test(expectedExceptions = SellRequestExistsException.class)
    public void testSellRequestAlreadyExists() throws Exception {
        secondaryMarketService.registerSellRequest(user, "sec-mrkt-domain-1.ie", "TESTAUTHCODE", PaymentMethod.ADP,
                null, opInfo);
    }

    @Test(expectedExceptions = DomainNotAvailableForUserException.class)
    public void testSellRequestWrongAccount() throws Exception {
        BuyRequest buyRequest = secondaryMarketService.getBuyRequest(4L);
        Account account = accountDAO.get(259L);
        buyRequest.setAccount(account);
        long changeId = historicalBuyRequestDAO.create(buyRequest, new Date(), user.getUsername());
        buyRequestDAO.updateUsingHistory(changeId);
        secondaryMarketService.registerSellRequest(user, buyRequest.getDomainName(), "ANOTHERAUTHC", PaymentMethod.ADP,
                null, opInfo);
    }

    @Test(expectedExceptions = CardPaymentException.class)
    public void testSellRequestFailedCardAuthorization() throws Exception {
        createExpectationForFailedCardAuthorization();
        secondaryMarketService.registerSellRequest(user, "sec-mrkt-domain-3.ie", "ANOTHERAUTHC", PaymentMethod.CC,
                createBasicCreditCard(), opInfo);
    }

    @Test(expectedExceptions = CardPaymentException.class)
    public void testSellRequestFailedCardSettlement() throws Exception {
        createExpectationForFailedCardSettlement();
        secondaryMarketService.registerSellRequest(user, "sec-mrkt-domain-3.ie", "ANOTHERAUTHC", PaymentMethod.CC,
                createBasicCreditCard(), opInfo);
    }

    @Test(expectedExceptions = CardPaymentException.class)
    public void testSellRequestFailedCardSettlementAndCancellation() throws Exception {
        createExpectationForFailedCardSettlementAndCancellation();
        secondaryMarketService.registerSellRequest(user, "sec-mrkt-domain-3.ie", "ANOTHERAUTHC", PaymentMethod.CC,
                createBasicCreditCard(), opInfo);
    }

    @Test(expectedExceptions = NotEnoughDepositFundsException.class)
    public void testSellRequestInsufficientFunds() throws Exception {
        clearDeposit(user.getUsername());

        new NonStrictExpectations() {
            {
                sender.sendEmail(anyInt, withInstanceOf(EmailParameters.class));
                times = 0;
            }
        };
        secondaryMarketService.registerSellRequest(user, "sec-mrkt-domain-3.ie", "ANOTHERAUTHC", PaymentMethod.ADP,
                null, opInfo);
    }

    @Test
    public void testSellRequestRegistrationWithCancellingModificationTicket() throws Exception {
        String domainName = "sec-mrkt-domain-3.ie";
        ticketService.createModificationTicket(user, domainName, "Holder", Arrays.asList("IDL2-IEDR"),
                Arrays.asList("IDL2-IEDR"), Arrays.asList(new Nameserver("ns1.example.ie", null, null),
                        new Nameserver("ns2.example.ie", null, null)), "Remark");
        assertNotNull(ticketSearchService.getTicketForDomain(domainName));
        registerSellRequestAndCheck(user, domainName, "ANOTHERAUTHC", PaymentMethod.CC, createBasicCreditCard(),
                opInfo);
        assertNull(ticketSearchService.getTicketForDomain(domainName));
    }

    @Test(expectedExceptions = BuyRequestCheckedOutException.class)
    public void testModifyBuyRequestCheckedOut() throws Exception {
        NewNicHandle nicHandle = new NewNicHandle("Test name", "Test company", "test@email.com", "Test address",
                119, 2, Collections.singletonList("123654234"), Collections.singletonList("987987987"), "", "");
        secondaryMarketService.modifyBuyRequest(opInfo, 2L, "TestHolder", nicHandle);
    }

    @Test(expectedExceptions = InvalidCountyException.class)
    public void testModifyBuyRequestNotMatchingCountries() throws Exception {
        NewNicHandle nicHandle = new NewNicHandle("Test name", "Test company", "test@email.com", "Test address",
                1, 2, Collections.singletonList("123654234"), Collections.singletonList("987987987"), "", "");
        secondaryMarketService.modifyBuyRequest(opInfo, 23L, "TestHolder", nicHandle);
    }

    @Test
    public void testModifyBuyRequest() throws Exception {
        NewNicHandle nicHandle = new NewNicHandle("Test name", "Test company", "test@email.com", "Test address",
                119, 2, Collections.singletonList("654654654"), Collections.singletonList("987987987"), "", "");
        final long buyRequestId = 23L;
        secondaryMarketService.modifyBuyRequest(opInfo, buyRequestId, "TestHolder", nicHandle);

        BuyRequest dbRequest = buyRequestDAO.get(buyRequestId);
        assertFalse(dbRequest.isCheckedOut());
        assertEquals(dbRequest.getRemark(), opInfo.getRemark());
        assertEquals(dbRequest.getDomainHolder(), "TestHolder");
        assertEquals(dbRequest.getHolderCategory().getId(), 1L);
        assertEquals(dbRequest.getHolderClass().getId(), 1L);
        assertEquals(dbRequest.getAdminName(), "Test name");
        assertEquals(dbRequest.getAdminCompanyName(), "Test company");
        assertEquals(dbRequest.getAdminEmail(), "test@email.com");
        assertEquals(dbRequest.getAdminAddress(), "Test address");
        assertEquals(dbRequest.getAdminCountry().getId(), 119);
        assertEquals(dbRequest.getAdminCounty().getId(), 2);
        assertEquals(dbRequest.getPhones().size(), 1);
        assertEquals(dbRequest.getPhones().get(0), "654654654");
        assertEquals(dbRequest.getFaxes().size(), 1);
        assertEquals(dbRequest.getFaxes().get(0), "987987987");
    }

    @Test(expectedExceptions = BuyRequestNotCheckedOutToUserException.class)
    public void testModifyBuyRequestAsHostmasterNotCheckedOut() throws Exception {
        NewNicHandle nicHandle = new NewNicHandle("Test name", "Test company", "test@email.com", "Test address", 119, 2,
                Collections.singletonList("123654234"), Collections.singletonList("987987987"), "", "");
        secondaryMarketService
                .modifyBuyRequestAsHostmaster(opInfo, 22L, "TestHolder", 1L, 1L, nicHandle, null, null, null, null,
                        null, null, null, null, null, null, null, null);
    }

    @Test(expectedExceptions = InvalidCountyException.class)
    public void testModifyBuyRequestAsHostmasterNotMatchingCountries() throws Exception {
        NewNicHandle nicHandle = new NewNicHandle("Test name", "Test company", "test@email.com", "Test address", 1, 2,
                Collections.singletonList("123654234"), Collections.singletonList("987987987"), "", "");
        secondaryMarketService
                .modifyBuyRequestAsHostmaster(opInfo, 21L, "TestHolder", 1L, 1L, nicHandle, null, null, null, null,
                        null, null, null, null, null, null, null, null);
    }

    @Test(expectedExceptions = ClassDoesNotMatchCategoryException.class)
    public void testModifyBuyRequestAsHostmasterClassDontMatchCategory() throws Exception {
        NewNicHandle nicHandle = new NewNicHandle("Test name", "Test company", "test@email.com", "Test address", 1, 2,
                Collections.singletonList("123654234"), Collections.singletonList("987987987"), "", "");
        secondaryMarketService
                .modifyBuyRequestAsHostmaster(opInfo, 21L, "TestHolder", 1L, 2L, nicHandle, null, null, null, null,
                        null, null, null, null, null, null, null, null);
    }

    @Test
    public void testModifyBuyRequestAsHostmaster() throws Exception {
        NewNicHandle nicHandle = new NewNicHandle("Test name", "Test company", "test@email.com", "Test address", 119, 2,
                Collections.singletonList("654654654"), Collections.singletonList("987987987"), "", "");
        final long buyRequestId = 21L;
        secondaryMarketService
                .modifyBuyRequestAsHostmaster(opInfo, buyRequestId, "TestHolder", 2L, 3L, nicHandle, null, null, null,
                        null, null, null, null, null, null, null, null, null);

        BuyRequest dbRequest = buyRequestDAO.get(buyRequestId);
        assertTrue(dbRequest.isCheckedOut());
        assertEquals(dbRequest.getHostmasterRemark(), opInfo.getRemark());
        assertEquals(dbRequest.getDomainHolder(), "TestHolder");
        assertEquals(dbRequest.getHolderClass().getId(), 2L);
        assertEquals(dbRequest.getHolderCategory().getId(), 3L);
        assertEquals(dbRequest.getAdminName(), "Test name");
        assertEquals(dbRequest.getAdminCompanyName(), "Test company");
        assertEquals(dbRequest.getAdminEmail(), "test@email.com");
        assertEquals(dbRequest.getAdminAddress(), "Test address");
        assertEquals(dbRequest.getAdminCountry().getId(), 119);
        assertEquals(dbRequest.getAdminCounty().getId(), 2);
        assertEquals(dbRequest.getPhones().size(), 1);
        assertEquals(dbRequest.getPhones().get(0), "654654654");
        assertEquals(dbRequest.getFaxes().size(), 1);
        assertEquals(dbRequest.getFaxes().get(0), "987987987");
    }

    @Test
    public void testModifyBuyRequestNotSetCounty() throws Exception {
        NewNicHandle nicHandle = new NewNicHandle("Test name", "Test company", "test@email.com", "Test address",
                1, County.NOT_SPECIFIED, Collections.singletonList("123654234"), Collections.singletonList("987987987"), "", "");
        final long buyRequestId = 23L;
        secondaryMarketService.modifyBuyRequest(opInfo, buyRequestId, "TestHolder", nicHandle);

        BuyRequest dbRequest = buyRequestDAO.get(buyRequestId);
        assertEquals(dbRequest.getAdminCounty().getId(), County.NOT_SPECIFIED);
    }

    @DataProvider
    public static Object[][] failureReasonFields() {
        return new Object[][] {
                {"domainNameFR",       INCORRECT, null, null, null, null, null, null, null, null, null, null, null},
                {"domainHolderFR",     null, INCORRECT, null, null, null, null, null, null, null, null, null, null},
                {"holderCategoryFR",   null, null, INCORRECT, null, null, null, null, null, null, null, null, null},
                {"holderClassFR",      null, null, null, INCORRECT, null, null, null, null, null, null, null, null},
                {"adminNameFR",        null, null, null, null, INCORRECT, null, null, null, null, null, null, null},
                {"adminEmailFR",       null, null, null, null, null, INCORRECT, null, null, null, null, null, null},
                {"adminCompanyNameFR", null, null, null, null, null, null, INCORRECT, null, null, null, null, null},
                {"adminAddressFR",     null, null, null, null, null, null, null, INCORRECT, null, null, null, null},
                {"adminCountryFR",     null, null, null, null, null, null, null, null, INCORRECT, null, null, null},
                {"adminCountyFR",      null, null, null, null, null, null, null, null, null, INCORRECT, null, null},
                {"phonesFR",           null, null, null, null, null, null, null, null, null, null, INCORRECT, null},
                {"faxesFR",            null, null, null, null, null, null, null, null, null, null, null, INCORRECT}};
    }

    @Test(dataProvider = "failureReasonFields")
    public void testRejectBuyRequestFR(final String fieldName, FailureReason fr1, FailureReason fr2, FailureReason fr3,
            FailureReason fr4, FailureReason fr5, FailureReason fr6, FailureReason fr7, FailureReason fr8,
            FailureReason fr9, FailureReason fr10, FailureReason fr11, FailureReason fr12) {
        try {
            final long buyRequestId = 21l;
            BuyRequest origRequest = buyRequestDAO.get(buyRequestId);
            PropertyDescriptor property = new PropertyDescriptor(fieldName, BuyRequest.class);
            property.getWriteMethod().invoke(origRequest, INCORRECT);
            secondaryMarketService
                    .rejectBuyRequest(user, opInfo, buyRequestId, BuyRequestStatus.FINANCE_HOLDUP, fr1, fr2, fr3, fr4,
                            fr5, fr6, fr7, fr8, fr9, fr10, fr11, fr12);
            BuyRequest dbRequest = buyRequestDAO.get(buyRequestId);
            // we'll implicitly check if other values are correctly set
            origRequest.setChangeDate(dbRequest.getChangeDate());
            origRequest.setHostmasterRemark(opInfo.getRemark());
            origRequest.setStatus(BuyRequestStatus.FINANCE_HOLDUP);
            origRequest.setCheckedOutTo(null);
            assertEqualBuyRequests(dbRequest, origRequest);
        } catch (Exception e) {
            fail("Failed automating test for rejecting buyRequest with FailureReason " + fieldName, e);
        }
    }

    @Test
    public void testRejectBuyRequestAsCancelled() throws Exception {
        long buyRequestId = 21;
        int initialHistorySize = getBuyRequestHistory(buyRequestId).size();
        checkEveryFailureReasonEquals(buyRequestDAO.get(buyRequestId), null);
        secondaryMarketService
                .rejectBuyRequest(user, opInfo, buyRequestId, BuyRequestStatus.CANCELLED, INCORRECT, INCORRECT,
                        INCORRECT, INCORRECT, INCORRECT, INCORRECT, INCORRECT, INCORRECT, INCORRECT, INCORRECT,
                        INCORRECT, INCORRECT);
        assertNull(buyRequestDAO.get(buyRequestId));
        List<HistoricalObject<BuyRequest>> results = getBuyRequestHistory(buyRequestId,
                Collections.singletonList(new SortCriterion("buyHistChangeId", false)));
        assertEquals(results.size(), initialHistorySize + 1);
        BuyRequest histBuyRequest = results.get(0).getObject();
        assertEquals(histBuyRequest.getStatus(), BuyRequestStatus.CANCELLED);
        assertEquals(histBuyRequest.getHostmasterRemark(), opInfo.getRemark());
        checkEveryFailureReasonEquals(histBuyRequest, INCORRECT);
    }

    @Test(expectedExceptions = BuyRequestNotCheckedOutToUserException.class)
    public void testRejectNotCheckedOutFR() throws Exception {
        secondaryMarketService
                .rejectBuyRequest(user, opInfo, 22, BuyRequestStatus.FINANCE_HOLDUP, null, null, null, null, null, null,
                        null, null, null, null, null, null);
    }

    @Test(expectedExceptions = BadBuyRequestStatusException.class)
    public void testRejectCannotPassFR() throws Exception {
        secondaryMarketService
                .rejectBuyRequest(user, opInfo, 21, BuyRequestStatus.PASSED, null, null, null, null, null, null, null,
                        null, null, null, null, null);
    }

    @Test(dataProvider = "failureReasonFields")
    public void testSaveBuyRequestFR(final String fieldName, FailureReason fr1, FailureReason fr2, FailureReason fr3,
            FailureReason fr4, FailureReason fr5, FailureReason fr6, FailureReason fr7, FailureReason fr8,
            FailureReason fr9, FailureReason fr10, FailureReason fr11, FailureReason fr12) {
        try {
            final long buyRequestId = 21l;
            BuyRequest origRequest = buyRequestDAO.get(buyRequestId);
            PropertyDescriptor property = new PropertyDescriptor(fieldName, BuyRequest.class);
            property.getWriteMethod().invoke(origRequest, INCORRECT);
            secondaryMarketService
                    .saveBuyRequest(opInfo, buyRequestId, fr1, fr2, fr3, fr4, fr5, fr6, fr7, fr8, fr9, fr10, fr11, fr12);
            BuyRequest dbRequest = buyRequestDAO.get(buyRequestId);
            // we'll implicitly check if other values are correctly set
            origRequest.setChangeDate(dbRequest.getChangeDate());
            origRequest.setHostmasterRemark(opInfo.getRemark());
            assertEqualBuyRequests(dbRequest, origRequest);
        } catch (Exception e) {
            fail("Failed automating test for rejecting buyRequest with FailureReason " + fieldName, e);
        }
    }

    @Test
    public void testCheckout() throws Exception {
        final long buyRequestId = 23l;
        assertEquals(getBuyRequestHistory(buyRequestId).size(), 1);
        BuyRequest dbRequest = buyRequestDAO.get(buyRequestId);
        assertFalse(dbRequest.isCheckedOut());
        secondaryMarketService.checkoutBuyRequest(opInfo, buyRequestId);
        dbRequest = buyRequestDAO.get(buyRequestId);
        assertTrue(dbRequest.isCheckedOut());
        assertEquals(dbRequest.getCheckedOutTo(), opInfo.getUserName());
        List<HistoricalObject<BuyRequest>> histRequest = getBuyRequestHistory(buyRequestId);
        assertEquals(histRequest.size(), 2);
        BuyRequest histDb = histRequest.get(1).getObject();
        assertEqualBuyRequests(histDb, dbRequest);
    }

    @Test(expectedExceptions = BuyRequestFrozenAsPassed.class)
    public void testCheckoutOfPassedBuyRequest() throws Exception {
        secondaryMarketService.checkoutBuyRequest(opInfo, 4l);
    }

    @Test(expectedExceptions = BuyRequestCheckedOutException.class)
    public void testReCheckout() throws Exception {
        secondaryMarketService.checkoutBuyRequest(opInfo, 21l);
    }

    @Test
    public void testCheckin() throws Exception {
        final long buyRequestId = 21l;
        assertEquals(getBuyRequestHistory(buyRequestId).size(), 1);
        BuyRequest dbRequest = buyRequestDAO.get(buyRequestId);
        assertTrue(dbRequest.isCheckedOut());
        assertEquals(dbRequest.getCheckedOutTo(), opInfo.getUserName());
        secondaryMarketService.checkinBuyRequest(opInfo, buyRequestId, BuyRequestStatus.FINANCE_HOLDUP);
        dbRequest = buyRequestDAO.get(buyRequestId);
        assertFalse(dbRequest.isCheckedOut());
        assertEquals(dbRequest.getStatus(), BuyRequestStatus.FINANCE_HOLDUP);
        List<HistoricalObject<BuyRequest>> histRequest = getBuyRequestHistory(buyRequestId);
        assertEquals(histRequest.size(), 2);
        BuyRequest histDb = histRequest.get(1).getObject();
        assertEqualBuyRequests(histDb, dbRequest);
    }

    @Test(expectedExceptions = BuyRequestFrozenAsPassed.class)
    public void testCheckinAlreadyPassedBuyRequest() throws Exception {
        final long buyRequestId = 2l;
        BuyRequest dbRequest = buyRequestDAO.get(buyRequestId);
        assertTrue(dbRequest.isCheckedOut());
        assertEquals(dbRequest.getCheckedOutTo(), opInfo.getUserName());
        secondaryMarketService.checkinBuyRequest(opInfo, buyRequestId, BuyRequestStatus.FINANCE_HOLDUP);
    }

    private static Set<BuyRequestStatus> FORBIDDEN_CHECKIN_STATUSES =
            Sets.immutableEnumSet(BuyRequestStatus.PASSED, BuyRequestStatus.CANCELLED);

    @DataProvider
    public static Object[][] forbiddenCheckinStatuses() {
        Object[][] result = new Object[FORBIDDEN_CHECKIN_STATUSES.size()][];
        int i = 0;
        for (BuyRequestStatus status : FORBIDDEN_CHECKIN_STATUSES) {
            result[i++] = new Object[] { status };
        }
        return result;
    }

    @Test(expectedExceptions = BadBuyRequestStatusException.class, dataProvider = "forbiddenCheckinStatuses")
    public void testCheckinForbiddenStatus(BuyRequestStatus newStatus) throws Exception {
        final long buyRequestId = 21l;
        secondaryMarketService.checkinBuyRequest(opInfo, buyRequestId, newStatus);
    }

    @DataProvider
    public static Object[][] allowedCheckinStatuses() {
        Set<BuyRequestStatus> allowedStatuses = new HashSet<>(Arrays.asList(BuyRequestStatus.values()));
        allowedStatuses.removeAll(FORBIDDEN_CHECKIN_STATUSES);
        Object[][] result = new Object[allowedStatuses.size()][];
        int i = 0;
        for (BuyRequestStatus status : allowedStatuses) {
            result[i++] = new Object[] { status };
        }
        return result;
    }

    @Test(dataProvider = "allowedCheckinStatuses")
    public void testCheckinAllowedStatus(BuyRequestStatus newStatus) throws Exception {
        final long buyRequestId = 21l;
        secondaryMarketService.checkinBuyRequest(opInfo, buyRequestId, newStatus);
        BuyRequest dbRequest = buyRequestDAO.get(buyRequestId);
        assertFalse(dbRequest.isCheckedOut());
        assertEquals(dbRequest.getStatus(), newStatus);
    }

    @Test(expectedExceptions = BuyRequestNotCheckedOutToUserException.class)
    public void testCheckinNotOwner() throws Exception {
        secondaryMarketService.checkinBuyRequest(opInfo, 3l, BuyRequestStatus.FINANCE_HOLDUP);
    }

    @Test(expectedExceptions = BuyRequestNotCheckedOutToUserException.class)
    public void testCheckinNotCheckedOutRequests() throws Exception {
        secondaryMarketService.checkinBuyRequest(opInfo, 23l, BuyRequestStatus.FINANCE_HOLDUP);
    }

    @Test
    public void testReassign() throws Exception {
        final String newCheckoutToNicHandle = "AAE553-IEDR";
        for (long buyRequestId : Arrays.asList(21l, 22l)) {
            assertEquals(getBuyRequestHistory(buyRequestId).size(), 1);
            BuyRequest dbRequest = buyRequestDAO.get(buyRequestId);
            assertTrue(dbRequest.isCheckedOut());
            secondaryMarketService.reassignBuyRequest(opInfo, buyRequestId, newCheckoutToNicHandle);
            dbRequest = buyRequestDAO.get(buyRequestId);
            assertTrue(dbRequest.isCheckedOut());
            assertEquals(dbRequest.getCheckedOutTo(), newCheckoutToNicHandle);
            List<HistoricalObject<BuyRequest>> histRequest = getBuyRequestHistory(buyRequestId);
            assertEquals(histRequest.size(), 2);
            BuyRequest histDb = histRequest.get(1).getObject();
            assertEqualBuyRequests(histDb, dbRequest);
        }
    }

    @Test(expectedExceptions = BuyRequestNotCheckedOutToUserException.class)
    public void testReassingNotCheckedOutRequest() throws Exception {
        secondaryMarketService.reassignBuyRequest(opInfo, 23l, "AAE553-IEDR");
    }

    @Test(expectedExceptions = UserCannotCheckoutBuyRequestException.class)
    public void testReassignNewHostmasterInsufficient() throws Exception {
        secondaryMarketService.reassignBuyRequest(opInfo, 23l, "AAA22-IEDR");
    }
        @Test
    public void testCancelSellRequest() throws Exception {
        long sellRequestId = 3L;
        HistoricalSellRequestSearchCriteria criteria = new HistoricalSellRequestSearchCriteria();
        criteria.setSellRequestId(sellRequestId);
        SearchResult<HistoricalObject<SellRequest>> result = historicalSellRequestDAO.find(criteria);
        assertEquals(result.getResults().size(), 1);
        secondaryMarketService.cancelSellRequest(user, sellRequestId, opInfo);
        assertNull(sellRequestDAO.get(sellRequestId));
        result = historicalSellRequestDAO.find(criteria);
        assertEquals(result.getResults().size(), 2);
        assertEquals(result.getResults().get(1).getObject().getStatus(), SellRequestStatus.CANCELLED);
        assertEquals(getDomainSecondaryMarketStatus("sec-mrkt-domain-2.ie"), SecondaryMarketStatus.NoProcess);
    }

    @Test
    public void testAcceptBuyRequest() throws Exception {
        Date start = new Date();
        long buyRequestId = 21L;

        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.BUY_REQUEST_ACCEPTED.getId(),
                        withInstanceOf(BuyRequestExpirationEmailParameters.class));
                times = 1;
            }
        };

        checkBuyRequestReadyToBeAccepted(buyRequestId);
        secondaryMarketService.acceptBuyRequest(opInfo, buyRequestId);
        checkBuyRequestAccepted(buyRequestId, start);
    }

    @Test(expectedExceptions = BuyRequestNotCheckedOutToUserException.class)
    public void testAcceptNotCheckedOutBuyRequest() throws Exception {
        long buyRequestId = 4L;
        BuyRequest buyRequest = buyRequestDAO.get(buyRequestId);
        assertFalse(buyRequest.isCheckedOut());
        secondaryMarketService.acceptBuyRequest(opInfo, buyRequestId);
    }

    @Test(expectedExceptions = BuyRequestFrozenAsPassed.class)
    public void testAcceptPassedBuyRequest() throws Exception {
        long buyRequestId = 2L;
        BuyRequest buyRequest = buyRequestDAO.get(buyRequestId);
        assertEquals(buyRequest.getStatus(), BuyRequestStatus.PASSED);
        secondaryMarketService.acceptBuyRequest(opInfo, buyRequestId);
    }

    @Test
    public void testIsBuyRequestUsedInSale() throws Exception {
        assertTrue(secondaryMarketService.isBuyRequestUsedInSale(2L));
        assertFalse(secondaryMarketService.isBuyRequestUsedInSale(11L));
    }

    @Test
    public void testInvalidateLastBuyRequest() throws Exception {
        final long buyRequestId = 5L;
        testInvalidateBuyRequest(buyRequestId, true);
    }

    @Test
    public void testInvalidateNotLastBuyRequest() throws Exception {
        final long buyRequestId = 4L;
        testInvalidateBuyRequest(buyRequestId, false);
    }

    private void testInvalidateBuyRequest(long buyRequestId, final boolean isLast) throws Exception {
        BuyRequest buyRequest = buyRequestDAO.get(buyRequestId);
        checkIfBuyRequestIsLast(buyRequest, isLast);
        assertEquals(getDomainSecondaryMarketStatus(buyRequest.getDomainName()),
                SecondaryMarketStatus.BuyRequestRegistered);

        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.BUY_REQUEST_INVALIDATED.getId(),
                        withInstanceOf(SecondaryMarketRequestEmailParameters.class));
                times = 1;
                sender.sendEmail(EmailTemplateNamesEnum.LAST_BUY_REQUEST_CANCELLED.getId(),
                        withInstanceOf(DomainEmailParameters.class));
                times = isLast ? 1 : 0;
            }
        };

        assertNotNull(buyRequest.getAuthcode());
        assertNotNull(buyRequest.getAuthcodeCreationDate());
        assertNotEquals(buyRequest.getStatus(), BuyRequestStatus.CANCELLED);
        assertEquals(getBuyRequestHistory(buyRequestId).size(), 1);
        secondaryMarketService.invalidateBuyRequest(user, opInfo, buyRequestId);
        assertNull(buyRequestDAO.get(buyRequestId));
        List<HistoricalObject<BuyRequest>> history = getBuyRequestHistory(buyRequestId);
        assertEquals(history.size(), 2);
        assertEquals(history.get(1).getObject().getStatus(), BuyRequestStatus.CANCELLED);

        assertEquals(getDomainSecondaryMarketStatus(buyRequest.getDomainName()),
                isLast ? SecondaryMarketStatus.NoProcess : SecondaryMarketStatus.BuyRequestRegistered);
    }

    @Test(expectedExceptions = BadBuyRequestStatusException.class)
    public void testInvalidateBuyRequestWithNoAuthcode() throws Exception {
        long buyRequestId = 21L;
        BuyRequest buyRequest = buyRequestDAO.get(buyRequestId);
        assertNull(buyRequest.getAuthcode());
        secondaryMarketService.invalidateBuyRequest(user, opInfo, buyRequestId);
    }

    @Test(expectedExceptions = BuyRequestUsedInSaleException.class)
    public void testInvalidateBuyRequestWithSellRequestPresent() throws Exception {
        long buyRequestId = 2L;
        long sellRequestId = 2L;
        SellRequest sellRequest = secondaryMarketService.getSellRequest(sellRequestId);
        assertEquals(sellRequest.getBuyRequest().getId(), buyRequestId);
        secondaryMarketService.invalidateBuyRequest(user, opInfo, buyRequestId);
    }

    @Test
    public void testResendAuthCode() throws Exception {
        long buyRequestId = 2L;

        BuyRequest before = buyRequestDAO.get(buyRequestId);
        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.BUY_REQUEST_ACCEPTED.getId(),
                        withInstanceOf(BuyRequestExpirationEmailParameters.class));
                times = 1;
            }
        };

        secondaryMarketService.regenerateAndResendAuthCode(opInfo, buyRequestId);
        BuyRequest after = buyRequestDAO.get(buyRequestId);
        assertNotNull(before.getAuthcode());
        assertNotNull(after.getAuthcode());
        assertNotEquals(before.getAuthcode(), after.getAuthcode());
        assertNotEquals(before.getAuthcodeCreationDate(), after.getAuthcodeCreationDate());
        assertEquals(after.getHostmasterRemark(), "Regenerated AuthCode");
    }

    @Test(expectedExceptions = BuyRequestNotPassed.class)
    public void testResendAuthCodeWhenAuthCodeNotPresent() throws Exception {
        long buyRequestId = 21L;
        BuyRequest buyRequest = buyRequestDAO.get(buyRequestId);
        assertNull(buyRequest.getAuthcode());
        secondaryMarketService.regenerateAndResendAuthCode(opInfo, buyRequestId);
    }

    @Test
    public void testCompleteRegistrarSellRequest() throws Exception {
        BuyRequest buyRequest = buyRequestDAO.get(19L);
        String domainName = "sec-mrkt-reg-reg-to-be-completed.ie";
        Domain oldDomain = domainDAO.get(domainName);
        assertEquals(oldDomain.getDsmState().getSecondaryMarketStatus(), SecondaryMarketStatus.SellRequestRegistered);
        createExpectiationForCompletionEmails(false);
        secondaryMarketService.completeSellRequest(user, 19L, opInfo);
        // sell request cleaned
        SellRequestSearchCriteria sellCriteria = new SellRequestSearchCriteria();
        sellCriteria.setSellRequestId(19L);
        assertFalse(sellRequestDAO.exists(sellCriteria));
        // winner's buy request cleaned
        BuyRequestSearchCriteria buyCriteria = new BuyRequestSearchCriteria();
        buyCriteria.setBuyRequestId(19L);
        assertEquals(buyRequestDAO.count(buyCriteria), 0);
        // other buy requests not affected
        buyCriteria.setBuyRequestId(20L);
        assertEquals(buyRequestDAO.count(buyCriteria), 1);

        Domain domain = domainDAO.get(domainName);
        verifyAdminContactOnlyNewData(domain, buyRequest, oldDomain);
        verifySaleNotConsideredToBeTransfer(domain, oldDomain.getBillingContactNic());
    }

    @Test
    public void testCompleteDirectFromRegistrarSellRequest() throws Exception {
        BuyRequest buyRequest = buyRequestDAO.get(18L);
        String domainName = "sec-mrkt-dir-reg-to-be-completed.ie";
        Domain oldDomain = domainDAO.get(domainName);
        assertEquals(oldDomain.getDsmState().getSecondaryMarketStatus(), SecondaryMarketStatus.SellRequestRegistered);
        createExpectiationForCompletionEmails(false);
        secondaryMarketService.completeSellRequest(user, 18L, opInfo);
        // sell request cleaned
        SellRequestSearchCriteria sellCriteria = new SellRequestSearchCriteria();
        sellCriteria.setSellRequestId(18L);
        assertFalse(sellRequestDAO.exists(sellCriteria));

        Domain domain = domainDAO.get(domainName);
        verifyAdminContactOnlyNewData(domain, buyRequest, oldDomain);
        verifySaleNotConsideredToBeTransfer(domain, oldDomain.getBillingContactNic());
    }

    @Test
    public void testCompleteDirectSellRequest() throws Exception {
        Date startDate = DateUtils.truncate(new Date(), Calendar.SECOND);
        BuyRequest buyRequest = buyRequestDAO.get(17L);
        String domainName = "sec-mrkt-dir-dir-to-be-completed.ie";
        Domain oldDomain = domainDAO.get(domainName);
        assertEquals(oldDomain.getDsmState().getSecondaryMarketStatus(), SecondaryMarketStatus.SellRequestRegistered);
        createExpectiationForCompletionEmails(true);
        secondaryMarketService.completeSellRequest(user, 17L, opInfo);
        // sell request cleaned
        SellRequestSearchCriteria sellCriteria = new SellRequestSearchCriteria();
        sellCriteria.setSellRequestId(17L);
        assertFalse(sellRequestDAO.exists(sellCriteria));

        Domain domain = domainDAO.get(domainName);
        verifyAllContactsNewData(domain, buyRequest);
        verifySaleConsideredToBeTransfer(domain, oldDomain.getBillingContactNic(), startDate);
    }

    @Test
    public void testCompletingSellRequestMovesDocumentsToDomain() throws Exception {
        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setDomainName("sec-mrkt-new-sale.ie");
        SearchResult<Document> documents = documentService.findDocuments(criteria, null);
        assertEquals(documents.getResults().size(), 0);
        secondaryMarketService.completeSellRequest(user, 37, opInfo);
        documents = documentService.findDocuments(criteria, null);
        assertEquals(documents.getResults().size(), 2);
    }

    @Test
    public void testCompletingSellRequestRemovesHolderSubcategory() throws Exception {
        String domainName = "sec-mrkt-with-subcategory-to-be-completed.ie";
        Domain oldDomain = domainDAO.get(domainName);
        assertNotNull(oldDomain.getHolderSubcategory());
        secondaryMarketService.completeSellRequest(user, 41L, opInfo);
        Domain domain = domainDAO.get(domainName);
        assertNull(domain.getHolderSubcategory());
    }

    @Test
    public void testFindBuyRequestNotifications() {
        int allExpiringRequestsCount = 5;
        int activeExpiringRequestsCount = 4;
        List<BuyRequest> allExpiringRequests = buyRequestDAO.find(getAllExpiringRequestsCriteria(), null).getResults();
        List<BuyRequest> activeExpiringRequests =
                buyRequestDAO.find(getActiveExpiringRequestsCriteria(), null).getResults();
        List<BuyRequestNotification> notifications =
                secondaryMarketService.findBuyRequestNotifications(BuyRequestNotificationType.REQUEST);
        assertEquals(allExpiringRequests.size(), allExpiringRequestsCount);
        assertEquals(activeExpiringRequests.size(), activeExpiringRequestsCount);
        assertEquals(notifications.size(), activeExpiringRequestsCount);
        for (BuyRequestNotification notification : notifications) {
            assertNotEquals(notification.getBuyRequest().getStatus(), BuyRequestStatus.PASSED);
            assertNotEquals(notification.getBuyRequest().getStatus(), BuyRequestStatus.CANCELLED);
        }
    }

    @Test
    public void testFindBuyRequestAuthCodeNotifications() {
        int allExpiringAuthCodeCount = 2;
        List<BuyRequest> allExpiringRequests = buyRequestDAO.find(getAllExpiringAuthCodeCriteria(), null).getResults();
        List<BuyRequestNotification> notifications =
                secondaryMarketService.findBuyRequestNotifications(BuyRequestNotificationType.AUTHCODE);
        assertEquals(allExpiringRequests.size(), allExpiringAuthCodeCount);
        assertEquals(notifications.size(), allExpiringAuthCodeCount);
    }

    private BuyRequestSearchCriteria getAllExpiringRequestsCriteria() {
        int expirationPeriod = applicationConfig.getTicketExpirationPeriod();
        List<Integer> notificationPeriods = applicationConfig.getTicketExpirationNotificationPeriods();
        Collections.sort(notificationPeriods, Collections.reverseOrder());
        int largestPeriod = notificationPeriods.get(0);

        BuyRequestSearchCriteria criteria = new BuyRequestSearchCriteria();
        criteria.setCreationDateFrom(DateUtils.addDays(new Date(), -expirationPeriod + 1));
        criteria.setCreationDateTo(DateUtils.addDays(new Date(), largestPeriod - expirationPeriod));
        return criteria;
    }

    private BuyRequestSearchCriteria getActiveExpiringRequestsCriteria() {
        BuyRequestSearchCriteria criteria = getAllExpiringRequestsCriteria();
        criteria.setStatuses(BuyRequestStatus.valuesExcept(BuyRequestStatus.PASSED, BuyRequestStatus.CANCELLED));
        return criteria;
    }

    private BuyRequestSearchCriteria getAllExpiringAuthCodeCriteria() {
        int expirationPeriod = applicationConfig.getSecondaryMarketAuthcodeExpirationPeriod();
        List<Integer> notificationPeriods = applicationConfig.getSecondaryMarketAuthcodeExpirationNotificationPeriods();
        Collections.sort(notificationPeriods, Collections.reverseOrder());
        int largestPeriod = notificationPeriods.get(0);

        BuyRequestSearchCriteria criteria = new BuyRequestSearchCriteria();
        criteria.setAuthCodeCreationDateFrom(DateUtils.addDays(new Date(), -expirationPeriod + 1));
        criteria.setAuthCodeCreationDateTo(DateUtils.addDays(new Date(), largestPeriod - expirationPeriod));
        return criteria;
    }

    @Test
    public void testSendBuyRequestNotifications() throws Exception {
        sendAndCheckNotifications(BuyRequestNotificationType.REQUEST, EmailTemplateNamesEnum.BUY_REQUEST_EXPIRATION);
    }

    @Test
    public void testSendAuthCodeNotifications() throws Exception {
        sendAndCheckNotifications(BuyRequestNotificationType.AUTHCODE,
                EmailTemplateNamesEnum.BUY_REQUEST_AUTHCODE_EXPIRATION);
    }

    private void sendAndCheckNotifications(BuyRequestNotificationType notificationType,
            final EmailTemplateNamesEnum template)
            throws Exception {
        final List<BuyRequestNotification> notifications =
                secondaryMarketService.findBuyRequestNotifications(notificationType);
        assertFalse(notifications.isEmpty());
        new NonStrictExpectations() {
            {
                sender.sendEmail(template.getId(),
                        withInstanceOf(BuyRequestExpirationEmailParameters.class));
                times = notifications.size();
            }
        };

        for (BuyRequestNotification notification : notifications) {
            assertFalse(buyRequestNotificationDAO.notificationSent(notification.getBuyRequest().getId(),
                    notification.getNotificationPeriod(), notificationType));
            secondaryMarketService.sendBuyRequestNotification(opInfo, notification);
            assertTrue(buyRequestNotificationDAO.notificationSent(notification.getBuyRequest().getId(),
                    notification.getNotificationPeriod(), notificationType));
            // Send twice to make sure the second attempt is ignored.
            secondaryMarketService.sendBuyRequestNotification(opInfo, notification);
        }
    }

    @Test
    public void testFindBuyRequestsWithCompletedSales() {
        List<Long> results = secondaryMarketService.findBuyRequestsWithCompletedSales();
        assertEquals(results.size(), 1);
        assertEquals(results.get(0).longValue(), 31L);
    }

    @Test
    public void testDeleteBuyRequestWithCompletedSale() throws Exception {
        BuyRequestSearchCriteria buyCriteria = new BuyRequestSearchCriteria();
        buyCriteria.setBuyRequestId(31L);
        assertTrue(buyRequestDAO.exists(buyCriteria));
        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.BUY_REQUEST_REMOVED_ANOTHER_SALE_COMPLETED.getId(),
                        withInstanceOf(EmailParameters.class));
                times = 1;
            }
        };
        secondaryMarketService.deleteBuyRequestWithCompletedSale(user, 31L, opInfo);
        assertFalse(buyRequestDAO.exists(buyCriteria));
    }

    @Test
    public void testDeleteBuyRequestWithExpiredAuthCode() throws Exception {
        BuyRequestSearchCriteria buyCriteria = new BuyRequestSearchCriteria();
        buyCriteria.setBuyRequestId(38L);
        assertTrue(buyRequestDAO.exists(buyCriteria));
        assertEquals(domainDAO.get("sec-mrkt-authcode-expired.ie").getDsmState().getSecondaryMarketStatus(),
                SecondaryMarketStatus.BuyRequestRegistered);
        secondaryMarketService.deleteBuyRequestWithExpiredAuthCode(user, 38L, opInfo);
        assertFalse(buyRequestDAO.exists(buyCriteria));
        assertEquals(domainDAO.get("sec-mrkt-authcode-expired.ie").getDsmState().getSecondaryMarketStatus(),
                SecondaryMarketStatus.NoProcess);
    }

    @Test
    public void testCleanupBuyRequest() throws Exception {
        BuyRequestSearchCriteria buyCriteria = new BuyRequestSearchCriteria();
        buyCriteria.setBuyRequestId(24L);
        assertTrue(buyRequestDAO.exists(buyCriteria));
        assertEquals(domainDAO.get("sec-mrkt-domain-5.ie").getDsmState().getSecondaryMarketStatus(),
                SecondaryMarketStatus.BuyRequestRegistered);
        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.BUY_REQUEST_EXPIRED.getId(),
                        withInstanceOf(EmailParameters.class));
                times = 1;
            }
        };
        secondaryMarketService.cleanupBuyRequest(user, 24L, opInfo);
        assertFalse(buyRequestDAO.exists(buyCriteria));
        assertEquals(domainDAO.get("sec-mrkt-domain-5.ie").getDsmState().getSecondaryMarketStatus(),
                SecondaryMarketStatus.NoProcess);
    }

    @Test
    public void testDeleteAllRequests() throws Exception {
        String domainName = "webwebweb.ie";
        BuyRequestSearchCriteria buyCriteria = new BuyRequestSearchCriteria();
        buyCriteria.setDomainName(domainName);
        SellRequestSearchCriteria sellCriteria = new SellRequestSearchCriteria();
        sellCriteria.setDomainName(domainName);
        assertEquals(buyRequestDAO.count(buyCriteria), 3);
        assertEquals(sellRequestDAO.count(sellCriteria), 1);
        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.BUY_REQUEST_REMOVED.getId(),
                        withInstanceOf(EmailParameters.class));
                times = 1;
                sender.sendEmail(EmailTemplateNamesEnum.BUY_REQUEST_INVALIDATED.getId(),
                        withInstanceOf(EmailParameters.class));
                times = 1;
                sender.sendEmail(EmailTemplateNamesEnum.SELL_REQUEST_CANCELLED.getId(),
                        withInstanceOf(EmailParameters.class));
                times = 1;
            }
        };
        secondaryMarketService.deleteAllRequests(user, domainDAO.get(domainName), opInfo);
        assertFalse(buyRequestDAO.exists(buyCriteria));
        assertFalse(sellRequestDAO.exists(sellCriteria));
    }

    private void registerBuyRequestAndCheck(AuthenticatedUser user, String domainName, NewNicHandle newNicHandle,
            PaymentMethod paymentMethod, CreditCard creditCard, OpInfo opInfo)
            throws Exception {
        // prepare NicHandle as not exported to check if export happens on registering buy request
        String creatorNH = opInfo.getUserName();
        NicHandle nicHandleBefore = nicHandleDAO.get(creatorNH);
        nicHandleBefore.setExported(false);
        nicHandleDAO.updateUsingHistory(historicalNicHandleDAO.create(nicHandleBefore, new Date(), creatorNH));
        nicHandleBefore = nicHandleDAO.get(creatorNH);
        assertFalse(nicHandleBefore.isExported());
        final int paymentEmailId = paymentMethod == PaymentMethod.ADP ?
                EmailTemplateNamesEnum.BUY_REQUEST_ADP_PAYMENT.getId() :
                EmailTemplateNamesEnum.BUY_REQUEST_CC_PAYMENT.getId();
        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.FIRST_BUY_REQUEST_REGISTERED.getId(),
                        withInstanceOf(SecondaryMarketRequestEmailParameters.class));
                times = 1;
                sender.sendEmail(paymentEmailId, withInstanceOf(SecondaryMarketPaymentEmailParameters.class));
                times = 1;
                sender.sendEmail(EmailTemplateNamesEnum.BUY_REQUEST_REGISTERED.getId(),
                        withInstanceOf(SecondaryMarketRequestEmailParameters.class));
                times = 1;
            }
        };
        ExtendedDeposit initialDeposit = depositService.viewDeposit(creatorNH);
        assertEquals(getDomainSecondaryMarketStatus(domainName), SecondaryMarketStatus.NoProcess);
        String domainHolder = "Holder";
        Long domainOwnerTypeId = 1L;
        String remark = "remark";
        Date startDate = new Date();
        long buyRequestId = secondaryMarketService.registerBuyRequest(user, domainName, domainHolder, domainOwnerTypeId,
                newNicHandle, paymentMethod, creditCard, opInfo);
        assertEquals(getDomainSecondaryMarketStatus(domainName), SecondaryMarketStatus.BuyRequestRegistered);
        Domain domain = domainDAO.get(domainName);
        checkBuyRequest(buyRequestId, domainName, domain.getResellerAccount(), creatorNH, domainHolder,
                domainOwnerTypeId, remark, newNicHandle, startDate);
        checkTransactionAndReservation(domain, creatorNH, paymentMethod, OperationType.BUY_REQUEST);
        checkAccountBalance(creatorNH, initialDeposit, paymentMethod, OperationType.BUY_REQUEST);
        NicHandle nicHandleAfter = nicHandleDAO.get(creatorNH);
        assertTrue(nicHandleAfter.isExported());
        assertTrue(nicHandleAfter.getNicHandleRemark().startsWith("NicHandle was exported due to registrant transfer"));
    }

    private void checkBuyRequest(long buyRequestId, String domainName, Account domainAccount, String creatorNH,
            String domainHolder, Long domainOwnerTypeId, String remark, NewNicHandle newNicHandle, Date startDate)
            throws Exception {
        BuyRequest buyRequest = secondaryMarketService.getBuyRequest(buyRequestId);
        assertNotNull(buyRequest);
        OwnerType domainOwnerType = entityService.getOwnerType(domainOwnerTypeId);
        EntityClass holderClass = entityService.getClass(domainOwnerType.getClassId());
        EntityCategory holderCategory = entityService.getCategory(domainOwnerType.getCategoryId());
        Country adminCountry = countryFactory.getCountry(newNicHandle.getCountryId());
        County adminCounty = countryFactory.getCounty(newNicHandle.getCountyId());
        BuyRequest expected = new BuyRequest(domainName, creatorNH, domainAccount, domainHolder, holderClass,
                holderCategory, remark, newNicHandle.getName(), newNicHandle.getEmail(), newNicHandle.getCompanyName(),
                newNicHandle.getAddress(), newNicHandle.getPhones(), newNicHandle.getFaxes(), adminCountry,
                adminCounty, buyRequest.getCreationDate());
        expected.setChangeDate(buyRequest.getCreationDate());
        assertFalse(buyRequest.getCreationDate().before(DateUtils.truncate(startDate, Calendar.SECOND)),
                String.format("start date: %s (%s), creation date: %s (%s)",
                        startDate, startDate.getTime(),
                        buyRequest.getCreationDate(), buyRequest.getCreationDate().getTime()));
        assertEqualBuyRequests(buyRequest, expected);
    }

    private void checkTransactionAndReservation(Domain domain, String nicHandleId, PaymentMethod paymentMethod,
            OperationType operationType)
            throws Exception {
        TransactionSearchCriteria criteria = new TransactionSearchCriteria();
        criteria.setBillingNH(nicHandleId);
        List<Transaction> transactions = paymentSearchService.findAllTransactions(criteria, null);
        assertEquals(transactions.size(), 1);
        Transaction transaction = transactions.get(0);
        checkTransaction(transaction, nicHandleId, paymentMethod, operationType);
        assertEquals(transaction.getReservations().size(), 1);
        Reservation reservation = transaction.getReservations().get(0);
        checkReservation(reservation, domain, nicHandleId, transaction.getId(), paymentMethod, operationType);
    }

    private void checkTransaction(Transaction transaction, String nicHandleId, PaymentMethod paymentMethod,
            OperationType operationType)
            throws Exception {
        PriceWithVat priceWithVat = paymentSearchService.getRequestPriceWithVat(operationType, nicHandleId);
        Integer invoiceId = null;
        String invoiceNumber = null;
        boolean settlementStarted = true;
        boolean settlementEnded = true;
        boolean cancelled = false;
        Date cancelledDate = null;
        List<Reservation> reservations = null;
        boolean invalidated = false;
        Date invalidatedDate = null;
        Long reauthorisedId = null;
        Transaction expected = new Transaction(transaction.getId(), nicHandleId, invoiceId, invoiceNumber,
                transaction.getOrderId(), settlementStarted, settlementEnded, priceWithVat.getTotal(),
                priceWithVat.getNetAmount(), priceWithVat.getVatAmount(), cancelled, cancelledDate,
                transaction.getFinanciallyPassedDate(), reservations, invalidated, invalidatedDate, reauthorisedId,
                transaction.getSettlementDate(), paymentMethod, operationType, transaction.getRealexAuthCode(),
                transaction.getRealexPasRef());
        compareTransactions(expected, transaction);
        assertNotNull(transaction.getId());
        assertNotNull(transaction.getOrderId());
        assertNotNull(transaction.getSettlementDate());
        assertNotNull(transaction.getFinanciallyPassedDate());
        if (paymentMethod == PaymentMethod.CC) {
            assertNotNull(transaction.getRealexAuthCode());
            assertNotNull(transaction.getRealexPasRef());
        }
    }

    private void checkReservation(Reservation reservation, Domain domain, String nicHandleId, long transactionId,
            PaymentMethod paymentMethod, OperationType operationType) throws Exception {
        PriceWithVat priceWithVat = paymentSearchService.getRequestPriceWithVat(operationType, nicHandleId);
        Integer productId = null;
        String invoiceNumber = null;
        boolean readyForSettlement = true;
        boolean settled = true;
        Long ticketId = null;
        Reservation expected = new Reservation(reservation.getId(), nicHandleId, domain.getName(), 0,
                reservation.getCreationDate(), priceWithVat, productId, readyForSettlement, settled,
                reservation.getSettledDate(), ticketId, transactionId, operationType, paymentMethod,
                invoiceNumber, reservation.getOrderId(), null, false);
        compareSettledReservation(reservation, expected);
        assertEqualDates(reservation.getEndDate(), domain.getRenewalDate(), Calendar.DATE);
        assertNotNull(reservation.getId());
        assertNotNull(reservation.getCreationDate());
        assertNotNull(reservation.getSettledDate());
        assertNotNull(reservation.getOrderId());
    }

    private void checkAccountBalance(String nicHandleId, ExtendedDeposit initialDeposit, PaymentMethod paymentMethod,
            OperationType operationType)
            throws Exception {
        ExtendedDeposit deposit = depositService.viewDeposit(nicHandleId);
        if (paymentMethod == PaymentMethod.ADP) {
            PriceWithVat priceWithVat =
                    paymentSearchService.getRequestPriceWithVat(operationType, nicHandleId);
            assertEquals(deposit.getCloseBal(), initialDeposit.getCloseBal().subtract(priceWithVat.getTotal()));
        } else {
            assertEquals(deposit.getCloseBal(), initialDeposit.getCloseBal());
        }
        assertEquals(deposit.getReservedFunds(), initialDeposit.getReservedFunds());
    }

    private SecondaryMarketStatus getDomainSecondaryMarketStatus(String domainName) {
        return domainDAO.get(domainName).getDsmState().getSecondaryMarketStatus();
    }

    private List<HistoricalObject<BuyRequest>> getBuyRequestHistory(long buyRequestId) {
        return getBuyRequestHistory(buyRequestId, null);
    }

    private List<HistoricalObject<BuyRequest>> getBuyRequestHistory(long buyRequestId,
            List<SortCriterion> sortCriteria) {
        HistoricalBuyRequestSearchCriteria criteria = new HistoricalBuyRequestSearchCriteria();
        criteria.setBuyRequestId(buyRequestId);
        return historicalBuyRequestDAO.find(criteria, sortCriteria).getResults();
    }

    private void checkBuyRequestReadyToBeAccepted(long buyRequestId) {
        BuyRequest buyRequest = buyRequestDAO.get(buyRequestId);
        assertNotEquals(buyRequest.getStatus(), BuyRequestStatus.PASSED);
        assertNull(buyRequest.getAuthcode());
        assertNull(buyRequest.getAuthcodeCreationDate());
        assertTrue(buyRequest.isCheckedOut());
        assertEquals(buyRequest.getCheckedOutTo(), opInfo.getUserName());
    }

    private void checkBuyRequestAccepted(long buyRequestId, Date start) throws Exception {
        BuyRequest buyRequest = buyRequestDAO.get(buyRequestId);
        assertEquals(buyRequest.getStatus(), BuyRequestStatus.PASSED);
        assertNotNull(buyRequest.getAuthcode());
        assertNotNull(buyRequest.getAuthcodeCreationDate());
        String pureAuthcode = buyRequest.getAuthcode().substring(2);
        assertTrue(AuthcodeGenerator.isValid(pureAuthcode));
        assertFalse(DateUtils.truncate(start, Calendar.SECOND).after(buyRequest.getAuthcodeCreationDate()));
        assertFalse(buyRequest.isCheckedOut());
    }

    private void checkIfBuyRequestIsLast(BuyRequest buyRequest, boolean isLast) {
        BuyRequestSearchCriteria criteria = new BuyRequestSearchCriteria();
        criteria.setDomainName(buyRequest.getDomainName());
        if (isLast) {
            assertEquals(buyRequestDAO.count(criteria), 1);
        } else {
            assertTrue(buyRequestDAO.count(criteria) > 1);
        }
    }

    private void checkEveryFailureReasonEquals(BuyRequest buyRequest, FailureReason expected) throws Exception {
        for (Object[] failureReasonField : failureReasonFields()) {
            PropertyDescriptor property = new PropertyDescriptor((String) failureReasonField[0], BuyRequest.class);
            assertEquals(property.getReadMethod().invoke(buyRequest), expected);
        }
    }

    private void registerSellRequestAndCheck(AuthenticatedUser user, String domainName, String authCode,
            PaymentMethod paymentMethod, CreditCard creditCard, OpInfo opInfo)
                    throws Exception {
        String creatorNH = opInfo.getUserName();
        final int paymentEmailId = paymentMethod == PaymentMethod.ADP ?
                EmailTemplateNamesEnum.SELL_REQUEST_ADP_PAYMENT.getId() :
                EmailTemplateNamesEnum.SELL_REQUEST_CC_PAYMENT.getId();
        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.SELL_REQUEST_REGISTERED.getId(),
                        withInstanceOf(SecondaryMarketRequestEmailParameters.class));
                times = 1;
                sender.sendEmail(paymentEmailId, withInstanceOf(SecondaryMarketPaymentEmailParameters.class));
                times = 1;
            }
        };
        ExtendedDeposit initialDeposit = depositService.viewDeposit(user.getUsername());
        assertEquals(getDomainSecondaryMarketStatus(domainName), SecondaryMarketStatus.BuyRequestRegistered);
        Date startDate = new Date();
        long sellRequestId = secondaryMarketService.registerSellRequest(user, domainName, authCode, paymentMethod,
                creditCard, opInfo);
        assertEquals(getDomainSecondaryMarketStatus(domainName), SecondaryMarketStatus.SellRequestRegistered);
        checkSellRequest(sellRequestId, authCode, creatorNH, startDate);
        Domain domain = domainDAO.get(domainName);
        checkTransactionAndReservation(domain, creatorNH, paymentMethod, OperationType.SELL_REQUEST);
        checkAccountBalance(creatorNH, initialDeposit, paymentMethod, OperationType.SELL_REQUEST);
    }

    private void checkSellRequest(long sellRequestId, String authCode, String creatorNH,
            Date startDate)
            throws Exception {
        SellRequest sellRequest = secondaryMarketService.getSellRequest(sellRequestId);
        assertNotNull(sellRequest);
        BuyRequest buyRequest = buyRequestDAO.getByAuthcode(authCode);
        SellRequest expected = new SellRequest(creatorNH, sellRequest.getCreationDate(), buyRequest);
        expected.setId(sellRequestId);
        assertFalse(sellRequest.getCreationDate().before(DateUtils.truncate(startDate, Calendar.SECOND)),
                String.format("start date: %s (%s), creation date: %s (%s)",
                        startDate, startDate.getTime(),
                        sellRequest.getCreationDate(), sellRequest.getCreationDate().getTime()));
        assertEqualSellRequests(sellRequest, expected);
    }

    private NewNicHandle getNewNicHandle() {
        return new NewNicHandle("name", "company", "adminemail@iedr.ie", "address", 121, 14, Arrays.asList("123456789"),
                Arrays.asList("987654321"), null, "A");
    }

    private void verifyAdminContactOnlyNewData(Domain domain, BuyRequest buyRequest, Domain oldDomain) {
        assertEquals(domain.getDsmState().getSecondaryMarketStatus(), SecondaryMarketStatus.NoProcess);
        verifyDomainHolderData(domain, buyRequest);
        assertEquals(domain.getAdminContacts().size(), 1);
        NicHandle nicHandle = nicHandleDAO.get(domain.getFirstAdminContactNic());
        verifyNicHandleData(nicHandle, buyRequest);
        // billing contact and tech contact not affected
        assertEquals(domain.getBillingContactNic(), oldDomain.getBillingContactNic());
        assertEquals(domain.getTechContactNic(), oldDomain.getTechContactNic());
    }

    private void verifyAllContactsNewData(Domain domain, BuyRequest buyRequest) {
        assertEquals(domain.getDsmState().getSecondaryMarketStatus(), SecondaryMarketStatus.NoProcess);
        verifyDomainHolderData(domain, buyRequest);
        assertEquals(domain.getAdminContacts().size(), 1);
        NicHandle nicHandle = nicHandleDAO.get(domain.getFirstAdminContactNic());
        // billing contact and tech contact equal to admin
        verifyNicHandleData(nicHandle, buyRequest);
        assertEquals(domain.getBillingContactNic(), nicHandle.getNicHandleId());
        assertEquals(domain.getTechContactNic(), nicHandle.getNicHandleId());
        verifyNewDirectAccount(nicHandle.getNicHandleId());
    }

    private void verifySaleConsideredToBeTransfer(Domain domain, String oldBillingContact, Date startDate) {
        assertNotNull(domain.getTransferDate());
        assertFalse(domain.getTransferDate().before(startDate));
        PlainDomainSearchCriteria transferCriteria = new PlainDomainSearchCriteria();
        transferCriteria.setDomainName(domain.getName());
        transferCriteria.setBillingNH(oldBillingContact);
        LimitedSearchResult<PlainDomain> transfers = plainDomainDAO.findTransferredAwayDomains(transferCriteria, 0, 1,
                null);
        assertEquals(transfers.getTotalResults(), 1);
        assertFalse(transfers.getResults().get(0).getTransferDate().before(startDate));
    }

    private void verifySaleNotConsideredToBeTransfer(Domain domain, String oldBillingContact) {
        assertNull(domain.getTransferDate());
        PlainDomainSearchCriteria transferCriteria = new PlainDomainSearchCriteria();
        transferCriteria.setDomainName(domain.getName());
        transferCriteria.setBillingNH(oldBillingContact);
        assertEquals(plainDomainDAO.findTransferredAwayDomains(transferCriteria, 0, 1, null).getTotalResults(), 0);
    }

    private void verifyNewDirectAccount(String nicHandle) {
        User user = userDAO.get(nicHandle);
        assertEquals(user.getPermissionGroups().size(), 2);
        assertTrue(user.getPermissionGroups().contains(groupsFactory.getGroupByName("Default")));
        assertTrue(user.getPermissionGroups().contains(groupsFactory.getGroupByName("Direct")));
        assertTrue(user.isForcePasswordChange());
    }

    private void verifyDomainHolderData(Domain domain, BuyRequest buyRequest) {
        assertEquals(domain.getHolder(), buyRequest.getDomainHolder());
        assertEquals(domain.getHolderClass(), buyRequest.getHolderClass());
        assertEquals(domain.getHolderCategory(), buyRequest.getHolderCategory());
        assertNull(domain.getHolderSubcategory());
    }

    private void verifyNicHandleData(NicHandle nicHandle, BuyRequest buyRequest) {
        assertEquals(nicHandle.getName(), buyRequest.getAdminName());
        assertEquals(nicHandle.getCompanyName(), buyRequest.getAdminCompanyName());
        assertEquals(nicHandle.getEmail(), buyRequest.getAdminEmail());
        assertEquals(nicHandle.getAddress(), buyRequest.getAdminAddress());
        assertEquals(nicHandle.getCountry(), buyRequest.getAdminCountry());
        assertEquals(nicHandle.getCounty(), buyRequest.getAdminCounty());
        assertEquals(nicHandle.getAccount().getId(), buyRequest.getAccount().getId());
        assertEqualPhoneLists(nicHandle.getPhones(), buyRequest.getPhones());
        assertEqualPhoneLists(nicHandle.getFaxes(), buyRequest.getFaxes());
    }

    private void createExpectiationForCompletionEmails(final boolean isDirect) throws Exception {
        new NonStrictExpectations() {
            {
                sender.sendEmail(EmailTemplateNamesEnum.SELL_REQUEST_COMPLETED.getId(),
                        withInstanceOf(EmailParameters.class));
                times = 1;
                sender.sendEmail(EmailTemplateNamesEnum.CREATE_NIC_HANDLE.getId(),
                        withInstanceOf(NicHandleEmailParameters.class));
                times = 1;
                sender.sendEmail(EmailTemplateNamesEnum.SECONDARY_MARKET_NEW_DIRECT_PASSWORD.getId(),
                        withInstanceOf(ContactPasswordEmailParameters.class));
                times = isDirect ? 1 : 0;
            }
        };
    }

    private void createExpectationForFailedCardAuthorization() throws Exception {
        final CardPaymentServiceMock anyInstance = new CardPaymentServiceMock();
        new NonStrictExpectations(CardPaymentServiceMock.class) {
            {
                anyInstance.authorisePaymentTransaction(withInstanceOf(PaymentRequest.class));
                result = new CardPaymentException("Failed authorization", CardPaymentException.Type.FAILED_TRANSACTION);
                sender.sendEmail(anyInt, withInstanceOf(EmailParameters.class));
                times = 0;
            }
        };
    }

    private void createExpectationForFailedCardSettlement() throws Exception {
        final CardPaymentServiceMock anyInstance = new CardPaymentServiceMock();
        new NonStrictExpectations(CardPaymentServiceMock.class) {
            {
                anyInstance.settleRealexAuthorisation(withInstanceOf(RealexTransactionIdentifier.class));
                result = new CardPaymentException("Failed settlement", CardPaymentException.Type.FAILED_TRANSACTION);
                anyInstance.cancelRealexAuthorisation(withInstanceOf(RealexTransactionIdentifier.class));
                times = 1;
                sender.sendEmail(anyInt, withInstanceOf(EmailParameters.class));
                times = 0;
            }
        };
    }

    private void createExpectationForFailedCardSettlementAndCancellation() throws Exception {
        final CardPaymentServiceMock anyInstance = new CardPaymentServiceMock();
        new NonStrictExpectations(CardPaymentServiceMock.class) {
            {
                anyInstance.settleRealexAuthorisation(withInstanceOf(RealexTransactionIdentifier.class));
                result = new CardPaymentException("Failed settlement", CardPaymentException.Type.FAILED_TRANSACTION);
                anyInstance.cancelRealexAuthorisation(withInstanceOf(RealexTransactionIdentifier.class));
                result = new CardPaymentException("Failed cancellation", CardPaymentException.Type.UNKNOWN_ERROR);
                sender.sendEmail(anyInt, withInstanceOf(EmailParameters.class));
                times = 0;
            }
        };
    }

    private void clearDeposit(String nicHandleId) throws Exception {
        BigDecimal depositAmount = depositService.viewDeposit(nicHandleId).getCloseBalMinusReservations();
        depositService.correctDeposit(opInfo, nicHandleId, depositAmount.negate(), "zero");
        assertEquals(depositService.viewDeposit(nicHandleId).getCloseBalMinusReservations().intValue(), 0);
    }

    private AuthenticatedUser getAuthenticatedUser(String user) throws Exception {
        return authenticationService.authenticate(user, "Passw0rd!", false, "1.1.1.1", false, null, true, "ws");
    }

}
