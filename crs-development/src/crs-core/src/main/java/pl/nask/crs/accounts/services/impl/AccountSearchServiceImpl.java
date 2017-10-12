package pl.nask.crs.accounts.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.AccountStatus;
import pl.nask.crs.accounts.InternalRegistrar;
import pl.nask.crs.accounts.dao.AccountDAO;
import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.search.AccountSearchCriteria;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.commons.utils.Validator;

public class AccountSearchServiceImpl implements AccountSearchService {

    private AccountDAO dao;
    static final List<SortCriterion> NAME_SORT_CRITERIA = Arrays.asList(new SortCriterion("name", true));
    static final long GENERAL_EXLUDED_ACCOUNT_ID = 315;

    public AccountSearchServiceImpl(AccountDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Account> getAccounts() {
        return dao.find(new AccountSearchCriteria(), NAME_SORT_CRITERIA).getResults();
    }

    @Override
    public List<Account> getAccounts(AccountSearchCriteria criteria) {
        return dao.find(criteria, NAME_SORT_CRITERIA).getResults();
    }

    @Override
    public List<Account> getAccountsWithExclusion() {
        return getAccountsWithExclusion(null, NAME_SORT_CRITERIA);
    }

    @Override
    public List<Account> getAccountsWithExclusion(List<Long> additionalExcludedAccounts, List<SortCriterion> sortBy) {
        List<Account> accounts = dao.find(new AccountSearchCriteria(), sortBy).getResults();
        List<Account> retAccounts = new ArrayList<Account>(accounts.size());
        for (Account account : accounts) {
            if (account.getId() != GENERAL_EXLUDED_ACCOUNT_ID
                    && !containsId(additionalExcludedAccounts, account.getId())) {
                retAccounts.add(account);
            }
        }
        return retAccounts;
    }

    private boolean containsId(List<Long> additionalExcludedAccounts, long id) {
        return !Validator.isEmpty(additionalExcludedAccounts) && additionalExcludedAccounts.contains(id);
    }

    @Override
    public Account getAccount(long id) throws AccountNotFoundException {
        Account account = dao.get(id);
        if (account == null)
            throw new AccountNotFoundException(id);
        return account;
    }

    @Override
    public List<Account> getAccountsForDocuments() {
        List<Account> accounts = getAccounts();
        List<Account> retAccounts = new ArrayList<Account>(accounts.size());
        for (Account account : accounts)
            if (account.getId() != GENERAL_EXLUDED_ACCOUNT_ID && account.getId() > 2 && account.isActive())
                retAccounts.add(account);
        return retAccounts;
    }

    @Override
    public LimitedSearchResult<Account> findAccount(AccountSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> orderBy) {
        return dao.find(criteria, offset, limit, orderBy);
    }

    @Override
    public void confirmAccountActive(long id) throws AccountNotActiveException, AccountNotFoundException {
        Account account = dao.get(id);
        if (account == null)
            throw new AccountNotFoundException(id);
        if (AccountStatus.Active != account.getStatus())
            throw new AccountNotActiveException(id);
    }

    @Override
    public List<InternalRegistrar> getRegistrarsForLogin() {
        return dao.getRegistrarForInternalLogin();
    }

    @Override
    public boolean exists(AccountSearchCriteria criteria) {
        return dao.exists(criteria);
    }
}
