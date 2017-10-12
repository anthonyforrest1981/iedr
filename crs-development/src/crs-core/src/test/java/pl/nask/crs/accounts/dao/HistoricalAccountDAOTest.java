package pl.nask.crs.accounts.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.AccountStatus;
import pl.nask.crs.accounts.search.HistoricalAccountSearchCriteria;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.nichandle.AbstractContextAwareTest;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;
import static pl.nask.crs.accounts.helpers.AccountTestHelper.compareAccounts;

public class HistoricalAccountDAOTest extends AbstractContextAwareTest {

    @Autowired
    HistoricalAccountDAO historicalAccountDAO;

    @Autowired
    AccountDAO accountDAO;

    @Test
    public void findHistoricalAccount() {
        HistoricalAccountSearchCriteria criteria = new HistoricalAccountSearchCriteria();
        criteria.setId(120L);
        SearchResult<HistoricalObject<Account>> result = historicalAccountDAO.find(criteria);
        assertEquals(result.getResults().size(), 9);
    }

    @Test
    public void createHistoricalAccount() {
        Date changeDate = new Date();
        String changedBy = "IDL2-IEDR";
        Account a1 = accountDAO.get(1L);
        a1.setBillingContact(new Contact("AAA22-IEDR"));
        a1.setChangeDate(changeDate);
        a1.setName("new name");
        a1.setRemark("new remark");
        a1.setWebAddress("new web addresss");
        a1.setStatus(AccountStatus.Active);
        a1.setAgreementSigned(false);
        a1.setTicketEdit(false);

        long changeId = historicalAccountDAO.create(a1, changeDate, changedBy);

        HistoricalAccountSearchCriteria criteria = new HistoricalAccountSearchCriteria();
        criteria.setId(a1.getId());
        SearchResult<HistoricalObject<Account>> accountHistory = historicalAccountDAO.find(criteria);
        HistoricalObject<Account> historicalAccount = accountHistory.getResults().get(0);
        Assert.assertEquals(historicalAccount.getChangeId(), changeId);
        compareAccounts(historicalAccount, a1);
    }

    @Test
    public void testFlagsAreCorrectlyInsertedAndTakenOut() {
        Date aDate = new Date(((new Date().getTime()) / 1000) * 1000);
        final long accountId = 120l;
        final String changedBy = "testNH";

        Account account = accountDAO.get(accountId);
        account.setTicketEdit(true);
        account.setAgreementSigned(true);

        HistoricalAccountSearchCriteria criteria = new HistoricalAccountSearchCriteria();
        criteria.setId(accountId);
        SearchResult<HistoricalObject<Account>> result = historicalAccountDAO.find(criteria);
        assertEquals(9, result.getResults().size());

        historicalAccountDAO.create(account, aDate, changedBy);

        result = historicalAccountDAO.find(criteria);
        assertEquals(10, result.getResults().size());
        HistoricalObject<Account> hisAcc = result.getResults().get(0);
        assertTrue(hisAcc.getObject().isAgreementSigned());
        assertTrue(hisAcc.getObject().isTicketEdit());
    }

    // TODO: CRS-72
    //    @Test
    //    public void findHistoricalAccountCheckFields(){
    //        HistoricalAccountSearchCriteria criteria = new HistoricalAccountSearchCriteria();
    //        criteria.setId(105L);
    //        SearchResult<HistoricalObject<Account>> result = historicalAccountDAO.find(criteria);
    //        assertEquals(result.getResults().size(), 1);
    //        HistoricalObject<Account> hisAcc = result.getResults().get(0);
    //        assertTrue(hisAcc.getObject().getName().equals("UTV Internet"));
    //        assertTrue(hisAcc.getObject().getBillingContact().getNicHandle().equals("DNA10-IEDR"));
    //        assertTrue(hisAcc.getObject().getAddress().equals("Ormeau Road\r\nBelfast\r\nBT7 1EB"));
    //        assertTrue(hisAcc.getObject().getCounty().equals("Co. Antrim"));
    //        assertTrue(hisAcc.getObject().getCountry().equals("Northern Ireland"));
    //        assertTrue(hisAcc.getObject().getWebAddress().equals("Web"));
    //        assertTrue(hisAcc.getObject().getPhone().equals("+44 1232 201 555"));
    //        assertTrue(hisAcc.getObject().getFax().equals("+44 1232 201 555"));
    //        assertTrue(hisAcc.getObject().getStatus().equals("Active"));
    //        assertEquals(hisAcc.getObject().getStatusChangeDate().getTime(), 1031349600000L);
    //        assertTrue(hisAcc.getObject().getTariff().equals("Silver"));
    //        assertEquals(hisAcc.getObject().getCreationDate().getTime(), 1031349600000L);
    //        assertEquals(hisAcc.getObject().getChangeDate().getTime(), 1051709340000L);
    //        assertTrue(hisAcc.getObject().getInvoiceFreq().equals("Monthly"));
    //        assertTrue(hisAcc.getObject().getNextInvMonth().equals("January"));
    //        assertTrue(hisAcc.getObject().getRemark().equals("Updated Address"));
    //        assertEquals(hisAcc.getChangeDate().getTime(), 1051705740000L);
    //        assertTrue(hisAcc.getChangedBy().equals("IH4-IEDR"));
    //    }

    @Test
    public void testFindUtf8Unnormalized() {
        HistoricalAccountSearchCriteria criteria = new HistoricalAccountSearchCriteria();
        criteria.setId(799L);
        SearchResult<HistoricalObject<Account>> result = historicalAccountDAO.find(criteria);
        AssertJUnit.assertEquals(2, result.getResults().size());
        HistoricalObject<Account> historicalAccount = result.getResults().get(0);
        Assert.assertEquals(historicalAccount.getChangedBy(), "XY\u01788-IEDR");
        Account account = historicalAccount.getObject();
        Assert.assertEquals(account.getName(), "Unnormalized-dt Regi\u1e61tra\u1e5f");
        Assert.assertEquals(account.getWebAddress(), "\u1e81\u1e89\u1e98.server.xxx");
    }

    @Test
    public void testLooseUtf8Validation() {
        HistoricalAccountSearchCriteria criteria = new HistoricalAccountSearchCriteria();
        criteria.setId(797l);
        SearchResult<HistoricalObject<Account>> result = historicalAccountDAO.find(criteria);
        Assert.assertEquals(result.getResults().size(), 3);
        Account histAccount = findBadUtfResult(result.getResults());
        assertNotNull(histAccount);
        Assert.assertEquals(histAccount.getName(), "Bad utf8 Registrar\01");
        Assert.assertEquals(histAccount.getWebAddress(), "web.server.xxx\01");
        Assert.assertEquals(histAccount.getRemark(), "Remark\01");
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testUpdateAccountWithForbiddenName() {
        Account account = accountDAO.get(799L);
        account.setName(getForbiddenString());
        accountDAO.updateUsingHistory(historicalAccountDAO.create(account, new Date(), "IDL2-IEDR"));
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testUpdateAccountWithForbiddenBillingContact() {
        Account account = accountDAO.get(799L);
        Contact contact = new Contact(getForbiddenString(), "Forbidden");
        account.setBillingContact(contact);
        accountDAO.updateUsingHistory(historicalAccountDAO.create(account, new Date(), "IDL2-IEDR"));
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testUpdateAccountWithForbiddenWebAddress() {
        Account account = accountDAO.get(799L);
        account.setWebAddress(getForbiddenString());
        accountDAO.updateUsingHistory(historicalAccountDAO.create(account, new Date(), "IDL2-IEDR"));
    }

    @Test(expectedExceptions = UncategorizedSQLException.class)
    public void testUpdateAccountWithForbiddenRemark() {
        Account account = accountDAO.get(799L);
        account.setRemark(getForbiddenString());
        accountDAO.updateUsingHistory(historicalAccountDAO.create(account, new Date(), "IDL2-IEDR"));
    }

    private Account findBadUtfResult(List<HistoricalObject<Account>> results) {
        Account badUtfResult = null;
        for (HistoricalObject<Account> histAccount : results) {
            if (histAccount.getObject().getName().startsWith("Bad utf8")) {
                badUtfResult = histAccount.getObject();
            }
        }
        return badUtfResult;
    }

    private String getForbiddenString() {
        // an exaple of 4-byte UTF-8
        return new String(Character.toChars(Integer.parseInt("1034F", 16)));
    }

}
