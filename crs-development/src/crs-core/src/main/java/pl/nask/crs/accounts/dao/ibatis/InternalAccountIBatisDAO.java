package pl.nask.crs.accounts.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.accounts.InternalRegistrar;
import pl.nask.crs.accounts.dao.ibatis.objects.InternalAccount;
import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;

public class InternalAccountIBatisDAO extends GenericIBatisDAO<InternalAccount, Long> {

    private static Map<String, String> sortingMap = new HashMap<>();
    {
        sortingMap.put("id", "id");
        sortingMap.put("name", "name");
        sortingMap.put("billingContactId", "billingContactId");
        sortingMap.put("billingContactName", "billingContactName");
        sortingMap.put("billingContactEmail", "billingContactEmail");
        sortingMap.put("billingContactCompanyName", "billingContactCompanyName");
        sortingMap.put("billingContactCountry", "billingContactCountry");
        sortingMap.put("status", "status");
        sortingMap.put("webAddress", "webAddress");
        sortingMap.put("creationDate", "creationDate");
        sortingMap.put("statusChangeDate", "statusChangeDate");
        sortingMap.put("changeDate", "changeDate");
    }

    public InternalAccountIBatisDAO() {
        setGetQueryId("account.getAccountById");
        setFindQueryId("account.findAccounts");
        setUpdateUsingHistoryQueryId("account.updateAccountUsingHistory");
        setCountFindQueryId("account.countTotalSearchResult");
        setLockQueryId("account.getLockedAccountById");

        setSortMapping(sortingMap);
    }

    public Long createAccount(InternalAccount internalAccount) {
        return performInsert("account.createAccount", internalAccount);
    }

    public List<InternalRegistrar> getRegistrarForLogin() {
        return performQueryForList("account.getInternalRegistrar");
    }

}
