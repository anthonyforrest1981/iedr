package pl.nask.crs.payment.dao.ibatis;

import pl.nask.crs.commons.dao.Converter;
import pl.nask.crs.commons.dao.ConvertingGenericDAO;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.dao.DepositDAO;
import pl.nask.crs.payment.dao.ibatis.objects.InternalDeposit;

public class ConvertingDepositDAO extends ConvertingGenericDAO<InternalDeposit, Deposit, String> implements DepositDAO {

    public ConvertingDepositDAO(InternalDepositIbatisDAO internalDao,
            Converter<InternalDeposit, Deposit> internalConverter) {
        super(internalDao, internalConverter);
    }

}
