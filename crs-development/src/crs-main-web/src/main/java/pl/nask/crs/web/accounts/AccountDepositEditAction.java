package pl.nask.crs.web.accounts;

import java.math.BigDecimal;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.payment.exceptions.NotEnoughDepositFundsException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public class AccountDepositEditAction extends AuthenticatedUserAwareAction {
    private final PaymentAppService paymentAppService;
    private final AccountAppService accountAppService;
    private final ApplicationConfig applicationConfig;

    // action parameter
    private long accountId;

    private String depositId;

    private String remark;
    private BigDecimal correctionAmount;
    private BigDecimal topUpAmount;

    public AccountDepositEditAction(PaymentAppService paymentAppService, AccountAppService accountAppService,
            ApplicationConfig applicationConfig) {
        this.paymentAppService = paymentAppService;
        this.accountAppService = accountAppService;
        this.applicationConfig = applicationConfig;
    }

    public String correct() throws Exception {
        try {
            paymentAppService.correctDeposit(getUser(), getDepositId(), correctionAmount, remark);
        } catch (NotEnoughDepositFundsException e) {
            addActionError("The user does not have enough funds to deduct this amount.");
            return ERROR;
        }
        return SUCCESS;
    }

    public String topup() throws Exception {
        paymentAppService.depositFundsOffline(getUser(), getDepositId(), topUpAmount, remark);
        return SUCCESS;
    }

    private String getDepositId() throws AccessDeniedException, AccountNotFoundException {
        if (depositId == null) {
            Account account = accountAppService.get(getUser(), accountId);
            depositId = account.getBillingContact().getNicHandle();
        }

        return depositId;
    }

    public long getId() {
        return accountId;
    }

    public void setId(long accountId) {
        this.accountId = accountId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getCorrectionAmount() {
        return correctionAmount;
    }

    public void setCorrectionAmount(BigDecimal correctionAmount) {
        this.correctionAmount = correctionAmount;
    }

    public BigDecimal getTopUpAmount() {
        return topUpAmount;
    }

    public void setTopUpAmount(BigDecimal topUpAmount) {
        this.topUpAmount = topUpAmount;
    }

    public BigDecimal getDepositMinLimit() {
        return applicationConfig.getDepositMinLimit();
    }

    public BigDecimal getDepositMaxLimit() {
        return applicationConfig.getDepositMaxLimit();
    }
}
