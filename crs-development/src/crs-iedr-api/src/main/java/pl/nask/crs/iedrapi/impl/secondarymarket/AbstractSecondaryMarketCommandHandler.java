package pl.nask.crs.iedrapi.impl.secondarymarket;

import java.util.Date;
import java.util.List;

import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.iedrapi.APICommandHandler;
import pl.nask.crs.iedrapi.exceptions.ObjectDoesNotExistException;
import pl.nask.crs.iedrapi.exceptions.ParamValuePolicyErrorException;
import pl.nask.crs.iedrapi.exceptions.ReasonCode;
import pl.nask.crs.iedrapi.exceptions.Value;
import pl.nask.crs.iedrapi.impl.AbstractCommandHandler;
import pl.nask.crs.iedrapi.impl.TypeConverter;
import pl.nask.crs.payment.CreditCard;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.SellRequest;
import pl.nask.crs.secondarymarket.exceptions.BuyRequestNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.xml.Constants;

import ie.domainregistry.ieapicom_1.*;

import static org.apache.commons.lang.time.DateUtils.addDays;
import static pl.nask.crs.commons.utils.DateUtils.getCardExpDateAsString;
import static pl.nask.crs.xml.Constants.IEAPICOM_NAMESPACE;
import static pl.nask.crs.xml.Constants.IEAPI_BUY_REQUEST_NAMESPACE;

public abstract class AbstractSecondaryMarketCommandHandler<T> extends AbstractCommandHandler implements
        APICommandHandler<T> {

    protected static final String SELL_REQUEST_NAMESPACE = Constants.IEAPI_SELL_REQUEST_NAMESPACE;

    protected CreditCard createCreditCard(CreditCardType card) {
        return new CreditCard(card.getCardNumber(), getCardExpDateAsString(card.getExpiryDate()), card.getCardType(),
                card.getCardHolderName(), card.getCvnNumber(),
                !Validator.isEmpty(card.getCvnPresInd()) ? Integer.valueOf(card.getCvnPresInd()) : null);
    }

    protected ie.domainregistry.ieapi_registranttransferbuyrequest_1.RequestType prepareBuyRequestTypeResponse(
            AuthenticatedUser user, long buyRequestId) throws AccessDeniedException, BuyRequestNotFoundException {
        BuyRequest buyRequest = getHelper().getSecondaryMarketAppService().getBuyRequest(user, buyRequestId);
        return prepareBuyRequestTypeResponse(buyRequest);
    }

    protected ie.domainregistry.ieapi_registranttransferbuyrequest_1.RequestType prepareBuyRequestTypeResponse(
            BuyRequest buyRequest) {
        ie.domainregistry.ieapi_registranttransferbuyrequest_1.RequestType res =
                new ie.domainregistry.ieapi_registranttransferbuyrequest_1.RequestType();
        res.setId(buyRequest.getId());
        res.setDomainName(buyRequest.getDomainName());
        res.setHolder(createHolderForBuyRequest(buyRequest));
        res.setCrDate(buyRequest.getCreationDate());
        return res;
    }

    protected PaymentMethod getPaymentMethod(PaymentMethodType methodType) throws ParamValuePolicyErrorException {
        PaymentMethod paymentMethod;
        boolean isCard = (methodType.getCard() != null);
        boolean isDeposit = (methodType.getDeposit() != null);
        if (!isCard && isDeposit) {
            paymentMethod = PaymentMethod.ADP;
        } else if (isCard && !isDeposit) {
            paymentMethod = PaymentMethod.CC;
        } else {
            throw new ParamValuePolicyErrorException(ReasonCode.INVALID_PAYMENT_METHOD);
        }
        return paymentMethod;
    }

    protected NewNicHandle getNewNicHandle(ContactType contact) throws ParamValuePolicyErrorException {
        int countryId = findCountryId(contact.getCountry(), IEAPICOM_NAMESPACE);
        int countyId = findCountyId(contact.getCounty(), IEAPICOM_NAMESPACE);
        List<String> phones = TypeConverter.stringToListNoDuplicates(contact.getVoice());
        List<String> faxes = TypeConverter.stringToListNoDuplicates(contact.getFax());
        return new NewNicHandle(contact.getName(), contact.getCompanyName(), contact.getEmail(), contact.getAddr(),
                countryId, countyId, phones, faxes, null, null);
    }

    protected HolderType createHolderForBuyRequest(BuyRequest buyRequest) {
        HolderType holder = new HolderType();
        holder.setHolderName(buyRequest.getDomainHolder());
        holder.setHolderRemarks(buyRequest.getRemark());
        return holder;
    }

    protected Date getCompletionDateForSellRequest(SellRequest sellRequest) {
        return addDays(sellRequest.getCreationDate(), getServicesRegistry().getApplicationConfig()
                .getSecondaryMarketCountdownPeriod());
    }

    protected ie.domainregistry.ieapi_registranttransfersellrequest_1.RequestType prepareSellRequestTypeResponse(
            SellRequest sellRequest) {
        ie.domainregistry.ieapi_registranttransfersellrequest_1.RequestType request =
                new ie.domainregistry.ieapi_registranttransfersellrequest_1.RequestType();
        request.setId(sellRequest.getId());
        request.setDomainName(sellRequest.getDomainName());
        request.setHolder(createHolderForBuyRequest(sellRequest.getBuyRequest()));
        request.setCrDate(sellRequest.getCreationDate());
        request.setCompDate(getCompletionDateForSellRequest(sellRequest));
        return request;
    }

    protected ContactType createContact(BuyRequest buyRequest) {
        ContactType res = new ContactType();
        res.setName(buyRequest.getAdminName());
        res.setCompanyName(buyRequest.getAdminCompanyName());
        res.setAddr(buyRequest.getAdminAddress());
        res.setCountry(buyRequest.getAdminCountry().getName());
        res.setCounty(buyRequest.getAdminCounty().getName());
        res.setVoice(TypeConverter.listToString(buyRequest.getPhones()));
        res.setFax(TypeConverter.listToString(buyRequest.getFaxes()));
        res.setEmail(buyRequest.getAdminEmail());
        return res;
    }

    protected ObjectDoesNotExistException handleBuyRequestNotFound(Long requestId) {
        String requestStringId = requestId == null ? "" : Long.toString(requestId);
        return new ObjectDoesNotExistException(ReasonCode.BUY_REQUEST_DOES_NOT_EXIST,
                new Value("id", IEAPI_BUY_REQUEST_NAMESPACE, requestStringId));
    }

}
