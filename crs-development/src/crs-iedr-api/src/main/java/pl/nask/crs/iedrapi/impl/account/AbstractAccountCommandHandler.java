package pl.nask.crs.iedrapi.impl.account;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.exceptions.DomainNotFoundException;
import pl.nask.crs.domains.exceptions.ReservationPendingException;
import pl.nask.crs.iedrapi.APICommandHandler;
import pl.nask.crs.iedrapi.exceptions.*;
import pl.nask.crs.iedrapi.impl.AbstractCommandHandler;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.payment.CreditCard;
import pl.nask.crs.payment.exceptions.*;
import pl.nask.crs.xml.Constants;

import ie.domainregistry.ieapi_account_1.CreditCardType;

/**
 * @author: Marcin Tkaczyk
 */
public abstract class AbstractAccountCommandHandler<T> extends AbstractCommandHandler implements APICommandHandler<T> {

    public static final String DOMAIN_NAMESPACE = Constants.IEAPI_DOMAIN_NAMESPACE;
    public static final String ACCOUNT_NAMESPACE = Constants.IEAPI_ACCOUNT_NAMESPACE;
    public final static Logger log = Logger.getLogger(AbstractAccountCommandHandler.class);

    protected IedrApiException handleException(Exception ee) {
        try {
            throw ee;
        } catch (NicHandleNotFoundException e) {
            return new ApiAuthenticationException(e);
        } catch (DomainNotFoundException e) {
            return new ObjectDoesNotExistException(ReasonCode.DOMAIN_NAME_DOES_NOT_EXIST, new Value("name",
                    DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (DomainManagedByAnotherResellerException e) {
            return new AuthorizationErrorException(ReasonCode.DOMAIN_IS_MANAGED_BY_ANOTHER_RESELLER, new Value("name",
                    DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (DomainIncorrectStateForPaymentException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.DOMAIN_INCORRECT_STATE_FOR_REACTIVATION,
                    new Value("name", DOMAIN_NAMESPACE, e.getDomainName()), e);
        } catch (NotAdmissiblePeriodException e) {
            return new ParamValuePolicyErrorException(ReasonCode.PERIOD_VALUE_NOT_ADMISSIBLE, e);
        } catch (NotEnoughDepositFundsException e) {
            return new BillingFailureException(ReasonCode.NOT_ENOUGHT_DEPOSIT_FUNDS, e);
        } catch (CardPaymentException e) {
            return new BillingFailureException(ReasonCode.ACCOUNT_PAYMENT_SYSTEM_ERROR, e.getMessage());
        } catch (DuplicatedDomainException e) {
            return new ParamValuePolicyErrorException(ReasonCode.ACCOUNT_DUPLICATE_DOMAIN, new Value("domain",
                    DOMAIN_NAMESPACE, e.getDomainName()));
        } catch (ReservationPendingException e) {
            return new ObjectStatusProhibitsOperationException(ReasonCode.PAYMENT_PENDING, new Value("domain",
                    DOMAIN_NAMESPACE, e.getDomainName()));
        } catch (Exception e) {
            return new CommandFailed(e);
        }
    }

    protected CreditCard createCreditCard(CreditCardType card) {
        return new CreditCard(card.getCardNumber(), DateUtils.getCardExpDateAsString(card.getExpiryDate()), card
                .getCardType().value(), card.getCardHolderName(), card.getCvnNumber(), !Validator.isEmpty(card
                .getCvnPresInd()) ? Integer.valueOf(card.getCvnPresInd()) : null);
    }

}
