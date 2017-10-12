package pl.nask.crs.web.accounts;

import java.util.Arrays;
import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.AccountStatus;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.accounts.wrappers.AccountWrapper;
import pl.nask.crs.app.utils.ContactHelper;
import pl.nask.crs.app.utils.ValidationHelper;
import pl.nask.crs.commons.exceptions.NicHandleAssignedToDomainException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.contacts.Contact;
import pl.nask.crs.history.HistoricalObject;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

/**
 * @author Marianna Mysiorska
 */
public class AccountViewAction extends AuthenticatedUserAwareAction {

    public static final String VIEW = "view";
    public static final String SEARCH = "search";

    private AccountAppService accountAppService;

    private Account account;
    private AccountWrapper wrapper;
    private List<HistoricalObject<Account>> accountHist;
    private List<AccountStatus> statuses;
    private Long id;

    private int historicalSelected = -1;
    private long changeId = -1;

    private ValidationHelper vh = new ValidationHelper(this);

    private String previousAction;
    private String hostmastersRemark;
    private AccountStatus newStatus;

    public AccountViewAction(AccountAppService accountAppService) {
        Validator.assertNotNull(accountAppService, "account app service");
        this.accountAppService = accountAppService;
    }

    public AccountWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(AccountWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public List<AccountStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<AccountStatus> statuses) {
        this.statuses = statuses;
    }

    public String getHostmastersRemark() {
        return hostmastersRemark;
    }

    public void setHostmastersRemark(String hostmastersRemark) {
        this.hostmastersRemark = hostmastersRemark;
    }

    public String getNewStatus() {
        return newStatus.getCode();
    }

    public void setNewStatus(String newStatus) {
        if (newStatus == null || newStatus.trim().length() == 0) {
            this.newStatus = null;
        } else {
            this.newStatus = AccountStatus.forCode(newStatus);
        }
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<HistoricalObject<Account>> getAccountHist() {
        return accountHist;
    }

    public void setAccountHist(List<HistoricalObject<Account>> accountHist) {
        this.accountHist = accountHist;
    }

    public AccountAppService getAccountAppService() {
        return accountAppService;
    }

    public void setAccountAppService(AccountAppService accountAppService) {
        this.accountAppService = accountAppService;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHistoricalSelected() {
        return historicalSelected;
    }

    public void setChangeId(long changeId) {
        this.changeId = changeId;
    }

    public void setChangeDate(long changeId) {
        this.changeId = changeId;
    }

    public String getPreviousAction() {
        return previousAction;
    }

    public void setPreviousAction(String previousAction) {
        this.previousAction = previousAction;
    }

    public boolean isHistory() {
        return this.historicalSelected >= 0;
    }

    public boolean hasCurrent() throws Exception {
        try {
            accountAppService.get(getUser(), id);
            return true;
        } catch (AccountNotFoundException e) {
            return false;
        }
    }

    public String view() throws Exception {
        statuses = Arrays.asList(AccountStatus.values());
        accountHist = accountAppService.history(getUser(), id);
        if (changeId >= 0 && accountHist != null && !accountHist.isEmpty()) {
            for (int i = 0; i < accountHist.size(); i++) {
                HistoricalObject<Account> hObject = accountHist.get(i);
                if (hObject.getChangeId() == changeId) {
                    account = hObject.getObject();
                    wrapper = new AccountWrapper(account);
                    historicalSelected = i;
                }
            }
        } else {
            account = accountAppService.get(getUser(), id);
            wrapper = new AccountWrapper(account);
        }
        return VIEW;
    }

    public String alterStatus() throws Exception {
        try {
            statuses = Arrays.asList(AccountStatus.values());
            boolean valid = true;
            if (vh.isFieldEmpty("hostmastersRemark")) {
                addActionError("Hostmaster's remark must be filled");
                valid = false;
            }
            if (newStatus == null) {
                addActionError("Status must be set");
                valid = false;
            }
            if (valid) {
                accountAppService.alterStatus(getUser(), id, newStatus, hostmastersRemark);
                account = accountAppService.get(getUser(), id);
                wrapper = new AccountWrapper(account);
                accountHist = accountAppService.history(getUser(), id);
                return VIEW;
            } else {
                return ERROR;
            }
        } catch (NicHandleAssignedToDomainException ex) {
            addActionError("Cannot alter status to DELETED. The nic handle " + ex.getNicHandleId()
                    + " is assigned to some domain.");
            return ERROR;
        }
    }

    public String makeContactInfo(Contact contact) {
        return ContactHelper.makeContactInfo(contact);
    }

}
