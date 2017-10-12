package pl.nask.crs.accounts.services;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.AccountStatus;
import pl.nask.crs.accounts.dao.AccountDAO;
import pl.nask.crs.accounts.dao.HistoricalAccountDAO;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.search.HistoricalAccountSearchCriteria;
import pl.nask.crs.accounts.services.impl.CreateAccountContener;
import pl.nask.crs.commons.TestOpInfo;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.exceptions.ContactCannotChangeException;
import pl.nask.crs.contacts.exceptions.ContactNotFoundException;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.AbstractContextAwareTest;
import pl.nask.crs.nichandle.exception.NicHandleIsAccountBillingContactException;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static org.testng.Assert.assertTrue;
import static pl.nask.crs.accounts.helpers.AccountTestHelper.compareAccounts;

public class AccountServiceDAOTest extends AbstractContextAwareTest {

    @Resource
    AccountService service;
    @Resource
    AccountSearchService searchService;
    @Resource
    AccountDAO accountDAO;
    @Resource
    HistoricalAccountDAO historicalAccountDAO;

    @Test
    public void alterStatus() throws Exception {
        service.alterStatus(101L, AccountStatus.Suspended, new TestOpInfo("TEST-IEDR", "remark"));
        Account account = searchService.getAccount(101L);
        AssertJUnit.assertTrue(AccountStatus.Suspended.equals(account.getStatus()));
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(account.getStatusChangeDate());
        cal2.setTime(new Date());
        AssertJUnit.assertEquals(cal1.get(YEAR), cal2.get(YEAR));
        AssertJUnit.assertEquals(cal1.get(MONTH), cal2.get(MONTH));
        AssertJUnit.assertEquals(cal1.get(DAY_OF_MONTH), cal2.get(DAY_OF_MONTH));
    }

    @Test
    public void alterStatusShouldCreateHistory() throws Exception {
        service.alterStatus(101L, AccountStatus.Suspended, new TestOpInfo("TEST-IEDR", "remark"));
        Account account = searchService.getAccount(101L);
        HistoricalAccountSearchCriteria criteria = new HistoricalAccountSearchCriteria();
        criteria.setId(account.getId());
        SearchResult<HistoricalObject<Account>> accountHistory = historicalAccountDAO.find(criteria);
        HistoricalObject<Account> historicalAccount = accountHistory.getResults().get(0);
        compareAccounts(historicalAccount, account);
    }

    @Test(expectedExceptions = AccountNotFoundException.class)
    public void alterStatusNoExists() throws Exception {
        service.alterStatus(123456789L, AccountStatus.Suspended, new TestOpInfo("TEST-IEDR", "remark"));
    }

    @Test
    public void save() throws Exception {
        Account account = searchService.getAccount(104L);
        account.setName("name1");
        account.setWebAddress("webAddress1");
        account.setAgreementSigned(true);
        account.setTicketEdit(true);
        service.save(account, new TestOpInfo("AAA-IEDR", "remark1"));
        Account account2 = searchService.getAccount(104L);
        // We can't know the exact change date, because it's set in the object's updateChangeDate method.
        account.setChangeDate(account2.getChangeDate());
        compareAccounts(account2, account);
        // Compare with a margin of error.
        Date now = new Date();
        assertTrue(now.getTime() - account2.getChangeDate().getTime() < 5000,
                String.format("Expected: %s, Actual: %s", now, account2.getChangeDate()));
    }

    @Test
    public void saveAccountShouldCreateHistory() throws Exception {
        Account account = searchService.getAccount(104L);
        account.setName("name1");
        service.save(account, new TestOpInfo("AAA-IEDR", "remark1"));
        account = accountDAO.get(account.getId());
        HistoricalAccountSearchCriteria criteria = new HistoricalAccountSearchCriteria();
        criteria.setId(account.getId());
        SearchResult<HistoricalObject<Account>> accountHistory = historicalAccountDAO.find(criteria);
        HistoricalObject<Account> historicalAccount = accountHistory.getResults().get(0);
        compareAccounts(historicalAccount, account);
    }

    @Test(expectedExceptions = ContactCannotChangeException.class)
    public void saveNicHandleIsBillingContactException() throws Exception {
        Account account = searchService.getAccount(104L);
        account.setBillingContact(new Contact("AAA22-IEDR", "IEG Design "));
        service.save(account, new TestOpInfo("AAA-IEDR", "remark1"));
    }

    @Test(expectedExceptions = AccountNotFoundException.class)
    public void saveNotExists() throws Exception {
        Contact contact = new Contact("TEST-IEDR");
        Account account = new Account(123455L, "name", contact, AccountStatus.Active, "webAddress",
                "hostmastersRemark", new Date(), new Date(), new Date(), false, false, null);
        service.save(account, new TestOpInfo("USER-IEDR", "remark1"));
    }

    @Test
    public void createAccount() throws Exception {
        Date nowDate = new Date();
        Date dateWithTime = DateUtils.truncate(nowDate, Calendar.SECOND);
        Date dateNoTime = DateUtils.truncate(nowDate, Calendar.DAY_OF_MONTH);
        CreateAccountContener createAccountContener = new CreateAccountContener("name", "webAddress", "AA11-IEDR",
                "hostmastersRemark", true, true);
        service.createAccount(createAccountContener, new TestOpInfo("AA11-IEDR", "hostmastersRemark"));
        Account newAccount = createAccountContener.getAccount();
        AssertJUnit.assertEquals("name", newAccount.getName());
        AssertJUnit.assertEquals("webAddress", newAccount.getWebAddress());
        AssertJUnit.assertEquals("AA11-IEDR", newAccount.getBillingContact().getNicHandle());
        AssertJUnit.assertTrue(newAccount.isAgreementSigned());
        AssertJUnit.assertTrue(newAccount.isTicketEdit());

        AssertJUnit.assertEquals(dateWithTime, newAccount.getCreationDate());
        AssertJUnit.assertEquals(dateWithTime, newAccount.getStatusChangeDate());
        assertTrue(dateWithTime.getTime() - newAccount.getChangeDate().getTime() < 5000,
                String.format("Expected: %s, Actual: %s", dateWithTime, newAccount.getChangeDate()));
    }

    @Test
    public void createAccountShouldCreateHistory() throws Exception {
        CreateAccountContener createAccountContener = new CreateAccountContener("name", "webAddress", "AA11-IEDR",
                "hostmastersRemark", true, true);
        service.createAccount(createAccountContener, new TestOpInfo("AA11-IEDR", "hostmastersRemark"));
        long accountId = createAccountContener.getAccount().getId();
        Account account = accountDAO.get(accountId);
        HistoricalAccountSearchCriteria criteria = new HistoricalAccountSearchCriteria();
        criteria.setId(accountId);
        SearchResult<HistoricalObject<Account>> accountHistory = historicalAccountDAO.find(criteria);
        HistoricalObject<Account> historicalAccount = accountHistory.getResults().get(0);
        compareAccounts(historicalAccount, account);
    }

    @Test(expectedExceptions = EmptyRemarkException.class)
    public void createAccountNullRemark() throws Exception {
        CreateAccountContener createAccountContener = new CreateAccountContener("name", "webAddress", "AA11-IEDR",
                null, false, false);
        service.createAccount(createAccountContener, new TestOpInfo("TEST-IEDR"));
    }

    @Test(expectedExceptions = EmptyRemarkException.class)
    public void createAccountEmptyRemark() throws Exception {
        CreateAccountContener createAccountContener = new CreateAccountContener("name", "webAddress", "AA11-IEDR", "",
                false, false);
        service.createAccount(createAccountContener, new TestOpInfo("TEST-IEDR", ""));
    }

    @Test(expectedExceptions = ContactNotFoundException.class)
    public void createAccountBillingContactNotExists() throws Exception {
        CreateAccountContener createAccountContener = new CreateAccountContener("name", "webAddress", "NOTEX-IEDR",
                "remark", false, false);
        service.createAccount(createAccountContener, new TestOpInfo("TEST-IEDR", "remark"));
    }

    @Test(expectedExceptions = NicHandleIsAccountBillingContactException.class)
    public void createAccountBillingContactAssignedToAnotherAccount() throws Exception {
        CreateAccountContener createAccountContener = new CreateAccountContener("name", "webAddress", "AAA22-IEDR",
                "remark", false, false);
        service.createAccount(createAccountContener, new TestOpInfo("TEST-IEDR", "remark"));
    }

}
