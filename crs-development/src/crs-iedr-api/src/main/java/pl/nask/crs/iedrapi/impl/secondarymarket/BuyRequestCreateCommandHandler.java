package pl.nask.crs.iedrapi.impl.secondarymarket;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.accounts.exceptions.AccountNotFoundException;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.app.tickets.exceptions.DomainHolderMandatoryException;
import pl.nask.crs.app.tickets.exceptions.DomainHolderTooLongException;
import pl.nask.crs.commons.exceptions.EmptyRemarkException;
import pl.nask.crs.commons.utils.InvalidEmailException;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.domains.dsm.DomainIllegalStateException;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.entities.exceptions.CharityOwnerTypeNotAllowed;
import pl.nask.crs.entities.exceptions.OwnerTypeNotExistException;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.*;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.CreditCard;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.exceptions.CardPaymentException;
import pl.nask.crs.payment.exceptions.NotEnoughDepositFundsException;
import pl.nask.crs.payment.exceptions.TransactionInvalidStateForSettlement;
import pl.nask.crs.payment.exceptions.TransactionNotFoundException;
import pl.nask.crs.secondarymarket.exceptions.BuyRequestNotFoundException;
import pl.nask.crs.secondarymarket.exceptions.DomainNotAvailableForUserException;
import pl.nask.crs.security.authentication.AccessDeniedException;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_registranttransferbuyrequest_1.CreateType;

import static pl.nask.crs.xml.Constants.IEAPICOM_NAMESPACE;
import static pl.nask.crs.xml.Constants.IEAPI_BUY_REQUEST_NAMESPACE;

public class BuyRequestCreateCommandHandler extends AbstractSecondaryMarketCommandHandler<CreateType> {

    @Override
    public ResponseType handle(AuthData auth, CreateType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        ApiValidator.assertNoError(callback);
        BuyRequestValidationHelper.commandPlainCheck(command);
        long domainOwnerTypeId = findOwnerTypeId(auth.getUser(), command.getHolder().getHolderType());
        NewNicHandle newNicHandle = getNewNicHandle(command.getContact());
        PaymentMethod paymentMethod = getPaymentMethod(command.getMethod());
        CreditCard creditCard = null;
        if (paymentMethod == PaymentMethod.CC) {
            creditCard = createCreditCard(command.getMethod().getCard());
        }

        try {
            long buyRequestId = getSecondaryMarketAppService().registerBuyRequest(auth.getUser(),
                    command.getDomainName(), command.getHolder().getHolderName(), domainOwnerTypeId, newNicHandle,
                    paymentMethod, creditCard, command.getHolder().getHolderRemarks());
            return ResponseTypeFactory.success(prepareBuyRequestTypeResponse(auth.getUser(), buyRequestId));
        } catch (Exception e) {
            throw mapException(e, command);
        }
    }

    private IedrApiException mapException(Exception ee, CreateType command) {
        try {
            throw ee;
        } catch (InvalidCountryException e) {
            return new ParamValuePolicyErrorException(ReasonCode.COUNTRY_DOES_NOT_EXIST,
                    new Value("country", IEAPICOM_NAMESPACE, command.getContact().getCountry()), e);
        } catch (InvalidCountyException e) {
            return new ParamValuePolicyErrorException(ReasonCode.COUNTY_DOES_NOT_EXIST,
                    new Value("county", IEAPICOM_NAMESPACE, command.getContact().getCounty()), e);
        } catch (OwnerTypeNotExistException e) {
            return new ParameterValueRangeErrorException(ReasonCode.HOLDER_TYPE_DOESNT_EXIST,
                    new Value("holderType", IEAPICOM_NAMESPACE, command.getHolder().getHolderType()), e);
        } catch (CharityOwnerTypeNotAllowed e) {
            return new ParamValuePolicyErrorException(ReasonCode.CHARITY_HOLDER_TYPE_NOT_ALLOWED,
                    new Value("holderType", IEAPICOM_NAMESPACE, command.getHolder().getHolderType()), e);
        } catch (DomainHolderTooLongException e) {
            return new ParamValueSyntaxErrorException(ReasonCode.HOLDER_NAME_TOO_LONG,
                    new Value("holderName", IEAPICOM_NAMESPACE, command.getHolder().getHolderName()), e);
        } catch (EmptyRemarkException e) {
            return new RequiredParameterMissingException(ReasonCode.HOLDER_REMARK_MANDATORY, e);
        } catch (DomainHolderMandatoryException e) {
            return new RequiredParameterMissingException(ReasonCode.DOMAIN_HOLDER_MANDATORY, e);
        } catch (InvalidEmailException e) {
            return new ParamValueSyntaxErrorException(ReasonCode.CONTACT_EMAIL_IS_INVALID,
                    new Value("email", IEAPICOM_NAMESPACE, e.getEmail()), e);
        } catch (AccountNotActiveException e) {
            return new DataManagementPolicyViolationException(ReasonCode.INACTIVE_ACCOUNT, e);
        } catch (CardPaymentException e) {
            return new BillingFailureException(ReasonCode.ACCOUNT_PAYMENT_SYSTEM_ERROR, e);
        } catch (NotEnoughDepositFundsException e) {
            return new BillingFailureException(ReasonCode.NOT_ENOUGHT_DEPOSIT_FUNDS, e);
        } catch (DomainNotFoundException e) {
            return new ObjectDoesNotExistException(ReasonCode.DOMAIN_NAME_DOES_NOT_EXIST,
                    new Value("domainName", IEAPI_BUY_REQUEST_NAMESPACE, e.getDomainName()), e);
        } catch (DomainIllegalStateException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_INCORRECT_STATE_FOR_OPERATION,
                    new Value("domainName", IEAPI_BUY_REQUEST_NAMESPACE, e.getDomainName()), e);
        } catch (DomainNotAvailableForUserException e) {
            return new AuthorizationErrorException(ReasonCode.DOMAIN_IS_MANAGED_BY_ANOTHER_RESELLER,
                    new Value("domainName", IEAPI_BUY_REQUEST_NAMESPACE, e.getDomainName()), e);
        } catch (TransactionNotFoundException | AccountNotFoundException | NicHandleNotFoundException |
                TransactionInvalidStateForSettlement | BuyRequestNotFoundException e) {
            return new CommandFailed(e);
        } catch (Exception e) {
            return new CommandFailed(e);
        }
    }

}
