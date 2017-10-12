package pl.nask.crs.payment.dao;

import pl.nask.crs.commons.dao.GenericDAO;
import pl.nask.crs.payment.Deposit;

public interface DepositDAO extends GenericDAO<Deposit, String> {

    void update(Deposit d);
}
