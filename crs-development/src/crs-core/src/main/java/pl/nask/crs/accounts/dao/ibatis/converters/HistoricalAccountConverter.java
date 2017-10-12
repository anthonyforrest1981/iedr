package pl.nask.crs.accounts.dao.ibatis.converters;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.accounts.dao.ibatis.objects.InternalAccount;
import pl.nask.crs.accounts.dao.ibatis.objects.InternalHistoricalAccount;
import pl.nask.crs.commons.dao.AbstractConverter;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.history.HistoricalObject;

/**
 * @author Marianna Mysiorska
 */
public class HistoricalAccountConverter extends AbstractConverter<InternalHistoricalAccount, HistoricalObject<Account>> {

    private AccountConverter accountConverter;

    public HistoricalAccountConverter(AccountConverter accountConverter) {
        Validator.assertNotNull(accountConverter, "account converter");
        this.accountConverter = accountConverter;
    }

    protected HistoricalObject<Account> _to(InternalHistoricalAccount src) {
        return new HistoricalObject<>(src.getChangeId(), accountConverter.to(src), src.getHistChangeDate(),
                src.getChangedByNicHandle());
    }

    protected InternalHistoricalAccount _from(HistoricalObject<Account> accountHistoricalObject) {
        InternalAccount ia = accountConverter.from(accountHistoricalObject.getObject());
        return new InternalHistoricalAccount(ia, accountHistoricalObject.getChangeId(),
                accountHistoricalObject.getChangeDate(), accountHistoricalObject.getChangedBy());
    }
}
