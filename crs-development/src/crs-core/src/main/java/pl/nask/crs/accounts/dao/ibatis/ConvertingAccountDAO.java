package pl.nask.crs.accounts.dao.ibatis;

import java.util.List;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.InternalRegistrar;
import pl.nask.crs.accounts.dao.AccountDAO;
import pl.nask.crs.accounts.dao.ibatis.objects.InternalAccount;
import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.commons.utils.Validator;

/**
 * @author Marianna Mysiorska
 */
public class ConvertingAccountDAO extends ConvertingGenericDAO<InternalAccount, Account, Long>
        implements AccountDAO {

    InternalAccountIBatisDAO internalDao;

    public ConvertingAccountDAO(InternalAccountIBatisDAO internalDao,
            Converter<InternalAccount, Account> internalConverter) {
        super(internalDao, internalConverter);
        Validator.assertNotNull(internalDao, "internal dao");
        Validator.assertNotNull(internalConverter, "internal converter");
        this.internalDao = internalDao;
    }

    public Long createAccount(Account account) {
        Validator.assertNotNull(account, "account");
        Validator.assertNotNull(account.getName(), "account name");
        Validator.assertNotNull(account.getBillingContact(), "account billing contact");
        Validator.assertNotNull(account.getBillingContact().getNicHandle(), "account billing contact id");
        Validator.assertNotNull(account.getWebAddress(), "account web address");
        Validator.assertNotNull(account.getStatusChangeDate(), "account status change date");
        Validator.assertNotNull(account.getRemark(), "account remark");
        account.validateFlags();
        return internalDao.createAccount(getInternalConverter().from(account));
    }

    @Override
    public List<InternalRegistrar> getRegistrarForInternalLogin() {
        return internalDao.getRegistrarForLogin();
    }
}
