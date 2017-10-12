package pl.nask.crs.accounts.services;

import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.InternalRegistrar;
import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.search.AccountSearchCriteria;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;

public interface AccountSearchService {

    List<Account> getAccounts();

    List<Account> getAccounts(AccountSearchCriteria criteria);

    List<Account> getAccountsWithExclusion();

    List<Account> getAccountsWithExclusion(List<Long> additionalExcludedAccounts, List<SortCriterion> sortBy);

    Account getAccount(long id) throws AccountNotFoundException;

    List<Account> getAccountsForDocuments();

    LimitedSearchResult<Account> findAccount(AccountSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> orderBy);

    void confirmAccountActive(long id) throws AccountNotActiveException, AccountNotFoundException;

    List<InternalRegistrar> getRegistrarsForLogin();

    boolean exists(AccountSearchCriteria criteria);

}
