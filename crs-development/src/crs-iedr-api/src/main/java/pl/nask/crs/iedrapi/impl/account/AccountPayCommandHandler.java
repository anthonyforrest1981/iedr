package pl.nask.crs.iedrapi.impl.account;

import java.util.Map;

import pl.nask.crs.commons.Period;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.exceptions.ParamValuePolicyErrorException;
import pl.nask.crs.iedrapi.exceptions.ReasonCode;
import pl.nask.crs.iedrapi.impl.TypeConverter;
import pl.nask.crs.payment.CreditCard;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.PaymentSummary;
import pl.nask.crs.security.authentication.AccessDeniedException;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_account_1.PayDataType;
import ie.domainregistry.ieapi_account_1.PayType;
import ie.domainregistry.ieapi_account_1.PayType.Method;

public class AccountPayCommandHandler extends AbstractAccountCommandHandler<PayType> {

    @Override
    public ResponseType handle(AuthData auth, PayType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        AccountValidationHelper.commandPlainCheck(command);

        Period period = Period.fromYears(command.getPeriod() == null ? 1 : command.getPeriod());

        PaymentMethod paymentMethod;
        Method m = command.getMethod();
        boolean isCard = (m.getCard() != null);
        boolean isDeposit = (m.getDeposit() != null);
        if (!isCard && isDeposit) {
            paymentMethod = PaymentMethod.ADP;
        } else if (isCard && !isDeposit) {
            paymentMethod = PaymentMethod.CC;
        } else {
            throw new ParamValuePolicyErrorException(ReasonCode.INVALID_PAYMENT_METHOD);
        }
        ApiValidator.assertNoError(callback);
        CreditCard card = null;
        if (paymentMethod == PaymentMethod.CC) {
            card = createCreditCard(m.getCard());
        }

        Map<String, Period> domainsWithPeriods = TypeConverter.convertToDomainsPeriodMap(command.getDomain(), period);
        try {
            PaymentSummary methodRes = getPaymentAppService().payForDomainRenewal(auth.getUser(), domainsWithPeriods,
                    paymentMethod, card);
            PayDataType res = new PayDataType();
            res.setFee(methodRes.getFee());
            res.setTotal(methodRes.getTotal());
            res.setVat(methodRes.getVat());
            res.setOrderId("" + methodRes.getOrderId()); //FIX ME ""?

            return ResponseTypeFactory.success(res);
        } catch (Exception e) {
            throw handleException(e);
        }
    }
}
