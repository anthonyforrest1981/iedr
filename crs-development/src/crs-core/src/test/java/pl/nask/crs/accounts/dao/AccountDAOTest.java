package pl.nask.crs.accounts.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.testng.Assert;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.AccountStatus;
import pl.nask.crs.accounts.InternalRegistrar;
import pl.nask.crs.accounts.search.AccountSearchCriteria;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.nichandle.AbstractContextAwareTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static pl.nask.crs.accounts.helpers.AccountTestHelper.compareAccounts;
import static pl.nask.crs.accounts.helpers.AccountTestHelper.getExpectedGuestAccount;

public class AccountDAOTest extends AbstractContextAwareTest {

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    HistoricalAccountDAO historicalAccountDAO;

    @Test
    public void testFind() {
        accountDAO.find(null).getResults();
    }

    @Test
    public void testGet() {
        Account a = accountDAO.get(1L);
        Account expected = getExpectedGuestAccount();
        compareAccounts(a, expected);
    }

    @Test
    public void testGetEmptyResult() {
        Account a = accountDAO.get(55555L);
        assertNull(a);
    }

    @Test
    public void testFindAllAccounts() {
        SearchResult<Account> result = accountDAO.find(null);
        assertEquals(result.getResults().size(), 55);
    }

    @Test
    public void testFindAccountById() {
        AccountSearchCriteria criteria = new AccountSearchCriteria();
        criteria.setId(1L);
        SearchResult<Account> result = accountDAO.find(criteria);
        assertEquals(result.getResults().size(), 1);
        compareAccounts(result.getResults().get(0), getExpectedGuestAccount());
    }

    @Test
    public void testFindAccountByNicHandle() {
        AccountSearchCriteria criteria = new AccountSearchCriteria();
        criteria.setNicHandle("I");
        SearchResult<Account> res = accountDAO.find(criteria);
        Assert.assertEquals(14, res.getResults().size());
    }

    @Test
    public void testFindAccountByAccountName() {
        AccountSearchCriteria criteria = new AccountSearchCriteria();
        criteria.setName("E");
        SearchResult<Account> res = accountDAO.find(criteria);
        Assert.assertEquals(res.getResults().size(), 4);
    }

    @Test
    public void testFindAccountByDomainName() {
        AccountSearchCriteria criteria = new AccountSearchCriteria();
        criteria.setDomainName("T");
        SearchResult<Account> res = accountDAO.find(criteria);
        Assert.assertEquals(res.getResults().size(), 7);
    }

    private AccountSearchCriteria getAllCriteria() {
        AccountSearchCriteria criteria = new AccountSearchCriteria();
        criteria.setId(1L);
        criteria.setName("GU");
        criteria.setNicHandle("I");
        criteria.setDomainName("T");
        return criteria;
    }

    @Test
    public void testFindAllCriteria() {
        LimitedSearchResult<Account> res = accountDAO.find(getAllCriteria(), 0, 10);
        Assert.assertEquals(res.getResults().size(), 1);
        Assert.assertEquals(res.getTotalResults(), 1);
    }

    @Test
    public void testCount() {
        Assert.assertEquals(accountDAO.count(getAllCriteria()), 1);
    }

    @Test
    public void testExists() {
        Assert.assertTrue(accountDAO.exists(getAllCriteria()));
    }

    @Test
    public void testNotExists() {
        AccountSearchCriteria criteria = getAllCriteria();
        criteria.setName("GUS");
        Assert.assertFalse(accountDAO.exists(criteria));
    }

    @Test
    public void testFindLimited() {
        accountDAO.find(null, 1, 10, null);

    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testUpdate() {
        Account a = getExpectedGuestAccount();
        accountDAO.update(a);
    }

    @Test
    public void testUpdateUsingHistory() {
        Date changeDate = new Date();
        String changedBy = "IDL2-IEDR";
        Account a1 = accountDAO.get(1L);
        a1.setBillingContact(new Contact("AAA22-IEDR", "IEG Design ", "NHEmail000002@server.xxx"));
        a1.setChangeDate(DateUtils.truncate(changeDate, Calendar.SECOND));
        a1.setName("new name");
        a1.setRemark("new remark");
        a1.setWebAddress("new web addresss");
        a1.setStatus(AccountStatus.Active);
        a1.setAgreementSigned(false);
        a1.setTicketEdit(false);

        long changeId = historicalAccountDAO.create(a1, changeDate, changedBy);
        accountDAO.updateUsingHistory(changeId);
        Account a2 = accountDAO.get(1L);
        compareAccounts(a2, a1);
    }

    @Test
    public void testInsertAccountFlagOnDuplicate() {
        Account a = accountDAO.get(180l);
        assertFalse(a.isTicketEdit());
        assertFalse(a.isAgreementSigned());
        a.setTicketEdit(true);
        a.setAgreementSigned(false);
        accountDAO.updateUsingHistory(historicalAccountDAO.create(a, new Date(), "IDL2-IEDR"));
        Account b = accountDAO.get(180l);
        assertTrue(b.isTicketEdit());
        assertFalse(b.isAgreementSigned());
    }

    private Account getTestAccount(Date date, boolean agreementSigned, boolean editTicket) {
        Contact contact = new Contact("AAA22-IEDR", "IEG Design ", "NHEmail000002@server.xxx");
        return new Account("name", contact, AccountStatus.Active, "webAddress", "remark", date, date, date,
                agreementSigned, editTicket);
    }

    private Account getAnotherTestAccount(Account a1, long id, boolean agreementSigned, boolean editTicket) {
        return new Account(id, a1.getName(), a1.getBillingContact(), a1.getStatus(), a1.getWebAddress(),
                a1.getRemark(), a1.getCreationDate(), a1.getStatusChangeDate(), a1.getChangeDate(), agreementSigned,
                editTicket, a1.getSegmentId());
    }

    @Test
    public void createAccount() {
        Account a1 = getTestAccount(new Date(), false, false);
        Long newId = accountDAO.createAccount(a1);
        Account a2 = getAnotherTestAccount(a1, newId, false, false);
        Account a3 = accountDAO.get(newId);
        compareAccounts(a3, a2);
    }

    @Test
    public void createAccountAgreementSigned() {
        Account a1 = getTestAccount(new Date(), true, false);
        Long newId = accountDAO.createAccount(a1);
        Account a2 = getAnotherTestAccount(a1, newId, true, false);
        Account a3 = accountDAO.get(newId);
        compareAccounts(a3, a2);
    }

    // exception is expected since ticketEdit flag can be set only, if agreementSigned flag is set
    @Test(expectedExceptions = IllegalStateException.class)
    public void createAccountTicketEdit() {
        Account a1 = getTestAccount(new Date(), false, true);
        Long newId = accountDAO.createAccount(a1);
        Account a2 = getAnotherTestAccount(a1, newId, false, true);
        Account a3 = accountDAO.get(newId);
        compareAccounts(a3, a2);
    }

    // the right way: set both: agreementSigned flag and tiketEdit flag
    @Test
    public void createAccountTicketEditAS() {
        Account a1 = getTestAccount(new Date(), true, true);
        Long newId = accountDAO.createAccount(a1);
        Account a2 = getAnotherTestAccount(a1, newId, true, true);
        Account a3 = accountDAO.get(newId);
        compareAccounts(a3, a2);
    }

    @Test
    public void truncateDatesOnCreation() {
        // bacause Date holds only YYYY-MM-DD, to check that rounding does not
        // occur we should check 23:59:59.xxxxxx times
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);

        Date baseDate = cal.getTime();
        long baseTime = baseDate.getTime();
        final Date expected = new Date(baseTime);
        final Date expectedSqlTimestamp = DateUtils.truncate(expected, Calendar.SECOND);

        final Date a1Date = new Date(baseTime);
        final Date a2Date = new Date(baseTime + 100);
        final Date a3Date = new Date(baseTime + 499);
        final Date a4Date = new Date(baseTime + 999);
        long a1Id = accountDAO.createAccount(getTestAccount(a1Date, true, true));
        long a2Id = accountDAO.createAccount(getTestAccount(a2Date, true, true));
        long a3Id = accountDAO.createAccount(getTestAccount(a3Date, true, true));
        long a4Id = accountDAO.createAccount(getTestAccount(a4Date, true, true));

        final Account a1 = accountDAO.get(a1Id);
        assertEquals(a1.getCreationDate(), expectedSqlTimestamp);
        assertEquals(a1.getStatusChangeDate(), expectedSqlTimestamp);
        assertEquals(a1.getChangeDate(), expectedSqlTimestamp);

        final Account a2 = accountDAO.get(a2Id);
        assertEquals(a2.getCreationDate(), expectedSqlTimestamp);
        assertEquals(a2.getStatusChangeDate(), expectedSqlTimestamp);
        assertEquals(a2.getChangeDate(), expectedSqlTimestamp);

        final Account a3 = accountDAO.get(a3Id);
        assertEquals(a3.getCreationDate(), expectedSqlTimestamp);
        assertEquals(a3.getStatusChangeDate(), expectedSqlTimestamp);
        assertEquals(a3.getChangeDate(), expectedSqlTimestamp);

        final Account a4 = accountDAO.get(a4Id);
        assertEquals(a4.getCreationDate(), expectedSqlTimestamp);
        assertEquals(a4.getStatusChangeDate(), expectedSqlTimestamp);
        assertEquals(a4.getChangeDate(), expectedSqlTimestamp);
    }

    @Test
    public void truncateDatesOnUpdate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);

        Date baseDate = cal.getTime();
        long baseTime = baseDate.getTime();
        final Date expected = new Date(baseTime);
        final Date expectedSqlTimestamp = DateUtils.truncate(expected, Calendar.SECOND);

        final Date a1Date = new Date(baseTime);
        final Date a4Date = new Date(baseTime + 999);
        long a1Id = accountDAO.createAccount(getTestAccount(a1Date, true, true));

        final Account a1 = accountDAO.get(a1Id);
        a1.setStatus(AccountStatus.Suspended);
        Date statusChangeDate = a1.getStatusChangeDate();
        statusChangeDate.setTime(baseTime + 999);
        a1.setChangeDate(a4Date);
        accountDAO.updateUsingHistory(historicalAccountDAO.create(a1, new Date(), "IDL2-IEDR"));
        Account a2 = accountDAO.get(a1Id);
        assertEquals(a2.getCreationDate(), expectedSqlTimestamp);
        assertEquals(a2.getStatusChangeDate(), expectedSqlTimestamp);
        assertEquals(a2.getChangeDate(), expectedSqlTimestamp);
    }

    @Test
    public void test() {
        accountDAO.get(103L);
    }

    @Test
    public void getInternalRegistrars() {
        List<InternalRegistrar> registrars = accountDAO.getRegistrarForInternalLogin();
        assertEquals(registrars.size(), 8);
    }

    @Test
    public void testGetDomainWithUnnormalizedDetails() {
        Account account = accountDAO.get(798L);
        Assert.assertEquals(account.getBillingContact().getNicHandle(), "XY\u01788-IEDR");
        account = accountDAO.get(799L);
        Assert.assertEquals(account.getName(), "Unnormalized-dt Regi\u1e61tra\u1e5f");
        Assert.assertEquals(account.getWebAddress(), "\u1e81\u1e89\u1e98.server.xxx");
    }

    @Test
    public void testFindUtf8NicHandleWithSearchCriteria() {
        long accountId = 797L;
        AccountSearchCriteria criteria = new AccountSearchCriteria();
        criteria.setName("Normalized regi\u1e61tra\u1e5f");
        SearchResult<Account> result = accountDAO.find(criteria);
        Assert.assertEquals(result.getResults().size(), 1);
        Assert.assertEquals(result.getResults().get(0).getId(), accountId);
        criteria = new AccountSearchCriteria();
        criteria.setNicHandle("XX\u01787-IEDR");
        result = accountDAO.find(criteria);
        Assert.assertEquals(result.getResults().size(), 1);
        Assert.assertEquals(result.getResults().get(0).getId(), accountId);
    }

    private String getForbiddenString() {
        // an exaple of 4-byte UTF-8
        return new String(Character.toChars(Integer.parseInt("1034F", 16)));
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testCreateAccountWithForbiddenName() {
        Account account = getTestAccount(new Date(), false, false);
        account.setName(getForbiddenString());
        accountDAO.createAccount(account);
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testCreateAccountWithForbiddenBillingContact() {
        Account account = getTestAccount(new Date(), false, false);
        Contact contact = new Contact(getForbiddenString(), "Forbidden");
        account.setBillingContact(contact);
        accountDAO.createAccount(account);
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testCreateAccountWithForbiddenWebAddress() {
        Account account = getTestAccount(new Date(), false, false);
        account.setWebAddress(getForbiddenString());
        accountDAO.createAccount(account);
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testCreateAccountWithForbiddenRemark() {
        Account account = getTestAccount(new Date(), false, false);
        account.setRemark(getForbiddenString());
        accountDAO.createAccount(account);
    }

}
