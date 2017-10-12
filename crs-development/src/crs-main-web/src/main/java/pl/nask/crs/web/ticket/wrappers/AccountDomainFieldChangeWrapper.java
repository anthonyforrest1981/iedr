package pl.nask.crs.web.ticket.wrappers;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

public class AccountDomainFieldChangeWrapper extends SimpleDomainFieldChangeWrapper<Account> {

    private final AccountSearchService accountSearchService;

    private static final Logger LOG = Logger.getLogger(AccountDomainFieldChangeWrapper.class);

    public AccountDomainFieldChangeWrapper(SimpleDomainFieldChange<Account> orig,
            AccountSearchService accountSearchService, DomainOperationType type) {
        super(orig, type);
        this.accountSearchService = accountSearchService;
    }

    public long getAccountId() {
        return getOrig().getNewValue() == null ? -1 : getOrig().getNewValue().getId();
    }

    public void setAccountId(long accountId) {
        LOG.debug("Set account Id: " + accountId);
        if (getOrig().getNewValue() != null && getOrig().getNewValue().getId() == accountId)
            return;
        try {
            getOrig().setNewValue(accountSearchService.getAccount(accountId));
        } catch (AccountNotFoundException e) {
            throw new IllegalArgumentException("Wrong account number");
        }
    }

    public List<Account> getAccounts() {
        return accountSearchService.getAccounts();
    }

    public String getName() {
        return getOrig().getNewValue() == null ? null : StringEscapeUtils.escapeHtml(getOrig().getNewValue().getName());
    }

    public boolean isAgreementSigned() {
        return getOrig().getNewValue() == null ? false : getOrig().getNewValue().isAgreementSigned();
    }

    public boolean isTicketEdit() {
        return getOrig().getNewValue() == null ? false : getOrig().getNewValue().isTicketEdit();
    }
}
