package pl.nask.crs.web.accounts;

import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.AccountStatus;
import pl.nask.crs.app.accounts.AccountAppService;
import pl.nask.crs.app.accounts.wrappers.AccountWrapper;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.Country;
import pl.nask.crs.country.CountryFactory;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.web.security.AuthenticatedUserAwareAction;

public abstract class AbstractAccountAction extends AuthenticatedUserAwareAction {
    private final static Logger log = Logger.getLogger(AbstractAccountAction.class);

    protected AccountAppService accountAppService;
    protected List<Country> countries;
    protected PaymentAppService paymentAppService;
    protected List<AccountStatus> statuses;
    protected Long id;

    protected Account account;
    protected AccountWrapper wrapper;
    protected String hostmastersRemark;

    public AbstractAccountAction(AccountAppService accountAppService, CountryFactory countryFactory,
            PaymentAppService paymentAppService) {
        Validator.assertNotNull(accountAppService, "account app service");
        Validator.assertNotNull(countryFactory, "country factory");
        Validator.assertNotNull(paymentAppService, "paymentAppService");
        this.accountAppService = accountAppService;
        this.countries = countryFactory.getCountries();
        this.paymentAppService = paymentAppService;
    }

    public List<String> getCategories() throws AccessDeniedException {
        return paymentAppService.getVatCategories(getUser());
    }

    public AccountAppService getAccountAppService() {
        return accountAppService;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<AccountStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<AccountStatus> statuses) {
        this.statuses = statuses;
    }

    public AccountWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(AccountWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public void setHostmastersRemark(String hostmastersRemark) {
        this.hostmastersRemark = hostmastersRemark;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getHostmastersRemark() {
        return hostmastersRemark;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccountAppService(AccountAppService accountAppService) {
        this.accountAppService = accountAppService;
    }

}
