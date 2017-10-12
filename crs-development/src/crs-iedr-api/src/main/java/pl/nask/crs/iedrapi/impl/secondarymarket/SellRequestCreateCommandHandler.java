package pl.nask.crs.iedrapi.impl.secondarymarket;

import pl.nask.crs.app.tickets.exceptions.DomainTransferPendingException;
import pl.nask.crs.commons.exceptions.InvalidAuthCodeException;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.DomainLockedException;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.*;
import pl.nask.crs.payment.CreditCard;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.exceptions.NotEnoughDepositFundsException;
import pl.nask.crs.secondarymarket.SellRequest;
import pl.nask.crs.secondarymarket.exceptions.DomainNotAvailableForUserException;
import pl.nask.crs.secondarymarket.exceptions.SellRequestExistsException;
import pl.nask.crs.security.authentication.AccessDeniedException;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_registranttransfersellrequest_1.CreateType;
import ie.domainregistry.ieapi_registranttransfersellrequest_1.RequestType;

public class SellRequestCreateCommandHandler extends AbstractSecondaryMarketCommandHandler<CreateType> {

    @Override
    public ResponseType handle(AuthData auth, CreateType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        ApiValidator.assertNoError(callback);
        String domainName = command.getDomainName();
        findDomain(auth.getUser(), domainName, SELL_REQUEST_NAMESPACE, "domainName");
        CreditCard creditCard = null;
        PaymentMethod paymentMethod = getPaymentMethod(command.getMethod());
        if (paymentMethod == PaymentMethod.CC) {
            creditCard = createCreditCard(command.getMethod().getCard());
        }
        try {
            long requestId = getSecondaryMarketAppService().registerSellRequest(auth.getUser(), domainName,
                    command.getAuthCode(), paymentMethod, creditCard);
            SellRequest sellRequest = getSecondaryMarketAppService().getSellRequest(auth.getUser(), requestId);
            RequestType res = prepareSellRequestTypeResponse(sellRequest);
            return ResponseTypeFactory.success(res);
        } catch (DomainIllegalStateException e) {
            throw new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_INCORRECT_STATE_FOR_OPERATION,
                    new Value("domainName", SELL_REQUEST_NAMESPACE, e.getDomainName()), e);
        } catch (DomainLockedException e) {
            throw new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_INCORRECT_STATE_FOR_OPERATION,
                    new Value("domainName", SELL_REQUEST_NAMESPACE, e.getDomainName()), e);
        } catch (DomainTransferPendingException e) {
            throw new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_TRANSFER_PENDING,
                    new Value("domainName", SELL_REQUEST_NAMESPACE, e.getDomainName()), e);
        } catch (NotEnoughDepositFundsException e) {
            throw new BillingFailureException(ReasonCode.NOT_ENOUGHT_DEPOSIT_FUNDS, e);
        } catch (InvalidAuthCodeException e) {
            throw new AuthorizationErrorException(ReasonCode.INVALID_AUTHCODE, e);
        } catch (DomainNotAvailableForUserException e) {
            throw new AuthorizationErrorException(ReasonCode.DOMAIN_IS_MANAGED_BY_ANOTHER_RESELLER, e);
        } catch (SellRequestExistsException e) {
            throw new ObjectExistException(ReasonCode.SELL_REQUEST_EXISTS, new Value("domainName",
                    SELL_REQUEST_NAMESPACE, e.getDomainName()), e);
        } catch (CardPaymentException e) {
            throw new BillingFailureException(ReasonCode.ACCOUNT_PAYMENT_SYSTEM_ERROR, e);
        } catch (Exception e) {
            throw new CommandFailed(e);
        }
    }

}
