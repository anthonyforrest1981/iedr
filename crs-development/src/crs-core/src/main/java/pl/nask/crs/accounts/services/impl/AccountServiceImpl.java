package pl.nask.crs.accounts.services.impl;

import java.util.Date;

import org.apache.log4j.Logger;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.AccountStatus;
import pl.nask.crs.accounts.dao.AccountDAO;
import pl.nask.crs.accounts.dao.HistoricalAccountDAO;
import pl.nask.crs.accounts.email.AccountEmailParameters;
import pl.nask.crs.accounts.exceptions.AccountEmailException;
import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.search.AccountSearchCriteria;
import pl.nask.crs.accounts.services.AccountHelperService;
import pl.nask.crs.accounts.services.AccountService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.exceptions.NicHandleAssignedToDomainException;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.SearchResult;
import pl.nask.crs.commons.utils.InvalidEmailException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.contacts.dao.ContactDAO;
import pl.nask.crs.contacts.exceptions.ContactCannotChangeException;
import pl.nask.crs.contacts.exceptions.ContactNotFoundException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.nichandle.exception.NicHandleIsAccountBillingContactException;
import pl.nask.crs.nichandle.exception.NicHandleNotActiveException;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.nichandle.service.NicHandleSearchService;
import pl.nask.crs.nichandle.service.NicHandleService;
import pl.nask.crs.user.Level;
import pl.nask.crs.user.service.UserSearchService;
import pl.nask.crs.user.service.UserService;

/**
 * @author Marianna Mysiorska
 */
public class AccountServiceImpl implements AccountService, AccountHelperService {

    private static final String DEFAULT_REMARK_FOR_ACCOUNT_CREATE_IN_THE_NIC_HANDLE = "Account create. This nic handle is assigned as a billing contact for the account ";
    private static final Logger logger = Logger.getLogger(AccountServiceImpl.class);

    private AccountDAO accountDAO;
    private HistoricalAccountDAO historicalAccountDAO;
    private ContactDAO contactDAO;
    private UserSearchService userSearchService;
    private NicHandleSearchService nicHandleSearchService;
    private NicHandleService nicHandleService;
    private EmailTemplateSender emailTemplateSender;
    private ApplicationConfig applicationConfig;
    private UserService userService;

    public AccountServiceImpl(AccountDAO accountDAO, HistoricalAccountDAO historicalAccountDAO, ContactDAO contactDAO,
            UserSearchService userSearchService, NicHandleSearchService nicHandleSearchService,
            NicHandleService nicHandleService, EmailTemplateSender emailTemplateSender, ApplicationConfig appConf,
            UserService userService) {
        Validator.assertNotNull(accountDAO, "account dao");
        Validator.assertNotNull(historicalAccountDAO, "historical account dao");
        Validator.assertNotNull(contactDAO, "contact dao");
        Validator.assertNotNull(userSearchService, "user search service");
        Validator.assertNotNull(nicHandleSearchService, "nic handle search service");
        Validator.assertNotNull(nicHandleService, "nic handle service");
        Validator.assertNotNull(emailTemplateSender, "email template sender");
        Validator.assertNotNull(userService, "userService");
        this.accountDAO = accountDAO;
        this.historicalAccountDAO = historicalAccountDAO;
        this.contactDAO = contactDAO;
        this.userSearchService = userSearchService;
        this.nicHandleSearchService = nicHandleSearchService;
        this.nicHandleService = nicHandleService;
        this.emailTemplateSender = emailTemplateSender;
        this.applicationConfig = appConf;
        this.userService = userService;
    }

    public void alterStatus(Long id, AccountStatus status, OpInfo opInfo)
            throws AccountNotFoundException, NicHandleAssignedToDomainException, EmptyRemarkException {
        Validator.assertNotNull(opInfo, "opInfo");
        validateRemark(opInfo.getRemark());
        Validator.assertNotNull(status, "nic handle status");
        Account account = lock(id);
        Validator.assertNotNull(account.getBillingContact(), "account billing contact");
        if ("Deleted".equals(status) && !status.equals(account.getStatus()))
            nicHandleService.confirmNicHandleIsNotAssignedToAnyDomain(account.getBillingContact().getNicHandle());
        if (account.updateStatus(status))
            updateAccountAndHistory(account, opInfo);
    }

    @Override
    public void createAccount(CreateAccountContener createAccountContener, OpInfo opInfo)
            throws EmptyRemarkException, ContactNotFoundException, NicHandleNotFoundException,
            NicHandleNotActiveException, AccountNotFoundException, AccountNotActiveException,
            NicHandleIsAccountBillingContactException, NicHandleEmailException, AccountEmailException, ExportException,
            InvalidEmailException, InvalidCountyException {
        Validator.assertNotNull(opInfo, "opInfo");
        validateRemark(opInfo.getRemark());
        String billingContactNicHandleId = createAccountContener.getAccount().getBillingContact().getNicHandle();
        confirmBillingContactIsNotAssignedToAnyAccount(billingContactNicHandleId);
        confirmBillingContactActive(billingContactNicHandleId);
        createAccountContener.getAccount().updateRemark(opInfo.getRemark(), opInfo.getActorNameForRemark());
        Account acc = createAccountContener.getAccount();
        Long newId = accountDAO.createAccount(acc);
        Account newAccount = accountDAO.get(newId);
        // Create entry in history and use its changeId to update the account.
        updateAccountAndHistory(newAccount, opInfo);
        changeAccountNumberInNicHandle(billingContactNicHandleId, newAccount, opInfo);
        createAccountContener.setAccount(newAccount);
        setRegistrarGroup(createAccountContener.getAccount().getBillingContact().getNicHandle(), opInfo);
        //TODO redundant?
        checkGroupsAssigned(createAccountContener);
        AccountEmailParameters params = new AccountEmailParameters(createAccountContener.getAccount(),
                opInfo.getUserName());
        try {
            emailTemplateSender.sendEmail(EmailTemplateNamesEnum.CREATE_ACCOUNT.getId(), params);
        } catch (Exception e) {
            throw new AccountEmailException(e);
        }
    }

    private void setRegistrarGroup(String billingNH, OpInfo opInfo) {
        userService.setUserGroup(billingNH, Level.Registrar, opInfo);
    }

    @Override
    public void save(Account account, OpInfo opInfo)
            throws AccountNotFoundException, EmptyRemarkException, ContactNotFoundException,
            NicHandleNotFoundException, ContactCannotChangeException, ExportException {
        Validator.assertNotNull(opInfo, "opInfo");
        validateRemark(opInfo.getRemark());
        Validator.assertNotNull(account, "account");
        Validator.assertTrue(account.getId() >= 0, "account id >= 0");
        Validator.assertNotNull(account.getBillingContact(), "account billing contact");
        Account accountDB = lock(account.getId());
        confirmBillingContactDoesNotChange(accountDB.getBillingContact(), account.getBillingContact());

        updateAccountAndHistory(account, opInfo);
    }

    private void checkGroupsAssigned(CreateAccountContener createAccountContener) {
        Validator.assertNotNull(createAccountContener, "create account contener");
        if (userSearchService.get(createAccountContener.getAccount().getBillingContact().getNicHandle()) == null)
            createAccountContener.setAccessGroups(false);
    }

    private void confirmBillingContactDoesNotChange(Contact from, Contact to) throws ContactCannotChangeException {
        Validator.assertNotNull(from, "contact from");
        Validator.assertNotNull(to, "contact to");
        if (!from.getNicHandle().equals(to.getNicHandle()))
            throw new ContactCannotChangeException(from.getNicHandle(), to.getNicHandle());
    }

    private void changeAccountNumberInNicHandle(String billingContactNicHandle, Account newAccount, OpInfo opInfo)
            throws NicHandleNotFoundException, EmptyRemarkException, AccountNotFoundException,
            AccountNotActiveException, NicHandleIsAccountBillingContactException, NicHandleEmailException,
            ExportException, InvalidEmailException, InvalidCountyException {
        Validator.assertNotNull(billingContactNicHandle, "billing contact nic handle");
        Validator.assertNotNull(newAccount, "new id");
        String remark = DEFAULT_REMARK_FOR_ACCOUNT_CREATE_IN_THE_NIC_HANDLE + newAccount.getId();
        opInfo = OpInfo.withModifiedRemark(opInfo, remark);
        nicHandleService.changeNicHandleAccount(billingContactNicHandle, newAccount.getId(), opInfo);
    }

    private void confirmBillingContactIsNotAssignedToAnyAccount(String billingContactNicHandle)
            throws ContactNotFoundException, NicHandleIsAccountBillingContactException {
        confirmBillingContactExists(billingContactNicHandle);
        AccountSearchCriteria criteria = new AccountSearchCriteria();
        criteria.setNicHandle(billingContactNicHandle);
        SearchResult<Account> res = accountDAO.find(criteria);
        if (res.getResults().size() > 0)
            throw new NicHandleIsAccountBillingContactException(billingContactNicHandle, res.getResults().get(0)
                    .getId());
    }

    private void confirmBillingContactActive(String billingContactNicHandleId)
            throws NicHandleNotFoundException, NicHandleNotActiveException {
        nicHandleSearchService.confirmNicHandleActive(billingContactNicHandleId);
    }

    private void confirmBillingContactExists(String billingContactNicHandle) throws ContactNotFoundException {
        Contact contact = contactDAO.get(billingContactNicHandle);
        if (contact == null)
            throw new ContactNotFoundException(billingContactNicHandle);
    }

    private void updateAccountAndHistory(Account account, OpInfo opInfo) {
        account.validateFlags();
        account.setChangeDate(new Date());
        account.updateRemark(opInfo.getRemark(), opInfo.getActorNameForRemark());
        long changeId = historicalAccountDAO.create(account, account.getChangeDate(), opInfo.getActorName());
        accountDAO.updateUsingHistory(changeId);
    }

    private Account lock(Long id) throws AccountNotFoundException {
        if (accountDAO.lock(id)) {
            return accountDAO.get(id);
        } else {
            throw new AccountNotFoundException(id);
        }
    }

    private void validateRemark(String remark) throws EmptyRemarkException {
        if (Validator.isEmpty(remark)) {
            throw new EmptyRemarkException();
        }
    }

    // from the AccountHelperService
    @Override
    public NicHandle getIEDRAccount() {
        Account iedrAcc = accountDAO.get(applicationConfig.getIedrAccountId());
        NicHandle iedrNh;
        try {
            iedrNh = nicHandleSearchService.getNicHandle(iedrAcc.getBillingContact().getNicHandle());
            iedrNh.setName(iedrAcc.getName());
            return iedrNh;
        } catch (NicHandleNotFoundException e) {
            throw new IllegalStateException("Couldn't find IEDR account data", e);
        }
    }

    @Override
    public String getVatNo(long accountNumber) throws AccountNotFoundException {
        Account acc = accountDAO.get(accountNumber);
        if (acc == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        String billC = acc.getBillingContact().getNicHandle();
        NicHandle nh;
        try {
            nh = nicHandleSearchService.getNicHandle(billC);
            return nh.getVatNo();
        } catch (NicHandleNotFoundException e) {
            logger.warn("No nichandle found for " + billC + " - not a valid billing contact for accountId="
                    + accountNumber);
            return null;
        }
    }
}
