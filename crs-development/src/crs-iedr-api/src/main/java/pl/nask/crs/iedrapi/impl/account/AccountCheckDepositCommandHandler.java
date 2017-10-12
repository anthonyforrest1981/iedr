package pl.nask.crs.iedrapi.impl.account;

import java.math.BigDecimal;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.payment.ExtendedDeposit;
import pl.nask.crs.payment.exceptions.DepositNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_account_1.CheckDepositType;
import ie.domainregistry.ieapi_account_1.ChkDepositDataType;

/**
 * @author: Marcin Tkaczyk
 */
public class AccountCheckDepositCommandHandler extends AbstractAccountCommandHandler<CheckDepositType> {

    public ResponseType handle(AuthData auth, CheckDepositType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        ApiValidator.assertNoError(callback);
        ChkDepositDataType response = new ChkDepositDataType();
        ExtendedDeposit deposit = null;
        try {
            deposit = getPaymentAppService().viewUserDeposit(auth.getUser());
            if (deposit != null) {
                response.setActualBalance(MoneyUtils.getRoundedAndScaledValue(deposit.getCloseBal()));
                response.setAvailableBalance(MoneyUtils.getRoundedAndScaledValue(deposit.getCloseBalMinusReservations()));
                response.setTransDate(deposit.getTransactionDate());
            }
        } catch (DepositNotFoundException e) {
            response.setActualBalance(BigDecimal.ZERO);
            response.setAvailableBalance(BigDecimal.ZERO);
        }
        return ResponseTypeFactory.success(response);
    }
}
