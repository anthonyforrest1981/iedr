package pl.nask.crs.payment.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.search.SortCriterion;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.*;
import pl.nask.crs.payment.exceptions.*;
import pl.nask.crs.security.authentication.AuthenticatedUser;

public interface DepositService {

    ExtendedDeposit viewDeposit(String nicHandleId) throws DepositNotFoundException;

    /**
     * Create new deposit for nic handle
     *
     * @param nicHandleId deposit nic handle
     * @return new deposit
     * @throws IllegalStateException when deposit for given nic handle exists
     */
    ExtendedDeposit initDeposit(String nicHandleId);

    LimitedSearchResult<ExtendedDeposit> findDeposits(DepositSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy);

    LimitedSearchResult<Deposit> findHistoricalDeposits(DepositSearchCriteria criteria, long offset, long limit,
            List<SortCriterion> sortBy);

    ExtendedDeposit depositFunds(AuthenticatedUser user, OpInfo opInfo, String nicHandle,
            BigDecimal valueInStandardCurrencyUnit, CreditCard card)
            throws CardPaymentException, ExportException, DepositBelowLimitException, DepositOverLimitException;

    LimitedSearchResult<DepositTopUp> getTopUpHistory(String nicHandle, Date fromDate, Date toDate, long offset,
            long limit);

    /**
     * Manual correction of the deposit. This will create a transaction, which Order_Id will be the hostmaster remark, trancaction type will be 'manual, by {username}'.
     *
     * @param opInfo info about the logged-in user performing the action
     * @param nicHandle deposit ID to be corrected
     * @param amountInStandardCurrencyUnit the amount of which the deposit should be increased/decreased in standard currency unit
     * @param remark the remark supplied by the user who made the correction (will be used as order id)
     * @return
     * @throws NotEnoughDepositFundsException
     * @throws ExportException
     * @throws NicHandleNotFoundException
     */
    Deposit correctDeposit(OpInfo opInfo, String nicHandle, BigDecimal amountInStandardCurrencyUnit, String remark)
            throws NotEnoughDepositFundsException, NicHandleNotFoundException, ExportException;

    Deposit depositFundsOffline(AuthenticatedUser user, OpInfo opInfo, String nicHandle,
            BigDecimal amountInStandardCurrencyUnit, String remark)
            throws NotEnoughDepositFundsException, NicHandleNotFoundException, ExportException,
            DepositBelowLimitException, DepositOverLimitException;

    Deposit reduceDeposit(String nicHandleId, BigDecimal amountInStandardCurrencyUnit, String orderId,
            DepositTransactionType transType, String correctorNH, String remark) throws NotEnoughDepositFundsException;
}
