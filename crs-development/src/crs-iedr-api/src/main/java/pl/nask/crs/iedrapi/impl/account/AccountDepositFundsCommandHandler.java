package pl.nask.crs.iedrapi.impl.account;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.exceptions.ParamValuePolicyErrorException;
import pl.nask.crs.iedrapi.exceptions.ParameterValueRangeErrorException;
import pl.nask.crs.iedrapi.exceptions.ReasonCode;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.exceptions.DepositBelowLimitException;
import pl.nask.crs.payment.exceptions.DepositOverLimitException;
import pl.nask.crs.security.authentication.AccessDeniedException;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_account_1.DepositFundsDataType;
import ie.domainregistry.ieapi_account_1.DepositFundsType;

/**
 * @author: Marcin Tkaczyk
 */
public class AccountDepositFundsCommandHandler extends AbstractAccountCommandHandler<DepositFundsType> {

    protected static Logger log = Logger.getLogger(AccountDepositFundsCommandHandler.class);

    public ResponseType handle(AuthData auth, DepositFundsType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        AccountValidationHelper.commandPlainCheck(command);

        ApiValidator.assertNoError(callback);

        BigDecimal commandValue = MoneyUtils.getRoundedAndScaledValue(command.getValue());
        Deposit newDeposit = null;
        try {
            // FIXME: remove reference to PaymentRequestVO
            newDeposit = getPaymentAppService().depositFunds(auth.getUser(), commandValue,
                    createCreditCard(command.getCard()));
        } catch (DepositBelowLimitException e) {
            throw new ParameterValueRangeErrorException(ReasonCode.TOO_LOW_VALUE_TO_DEPOSIT);
        } catch (DepositOverLimitException e) {
            throw new ParameterValueRangeErrorException(ReasonCode.TOO_HIGH_VALUE_TO_DEPOSIT);
        } catch (CardPaymentException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.ACCOUNT_PAYMENT_SYSTEM_ERROR, e.getMessage());
        } catch (ExportException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.ACCOUNT_PAYMENT_SYSTEM_ERROR, e.getMessage());
        }

        BigDecimal oldValue = BigDecimal.ZERO;
        BigDecimal depositCloseBal = newDeposit.getCloseBal();

        if (isDepositUpdated(depositCloseBal, commandValue)) {
            oldValue = depositCloseBal.subtract(commandValue);
        }

        DepositFundsDataType res = new DepositFundsDataType();
        res.setOldValue(MoneyUtils.getRoundedAndScaledValue(oldValue));
        res.setNewValue(MoneyUtils.getRoundedAndScaledValue(newDeposit.getCloseBal()));
        res.setTransDate(new Date());

        return ResponseTypeFactory.success(res);
    }

    private boolean isDepositUpdated(BigDecimal closeBal, BigDecimal commandValue) {
        return closeBal.compareTo(commandValue) != 0;

    }
}
