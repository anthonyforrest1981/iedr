package pl.nask.crs.iedrapi.impl.secondarymarket;

import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.exceptions.ObjectDoesNotExistException;
import pl.nask.crs.iedrapi.exceptions.ReasonCode;
import pl.nask.crs.iedrapi.exceptions.Value;
import pl.nask.crs.secondarymarket.SellRequest;
import pl.nask.crs.secondarymarket.exceptions.SellRequestNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_registranttransfersellrequest_1.IdType;
import ie.domainregistry.ieapi_registranttransfersellrequest_1.InfoDataType;

public class SellRequestInfoCommandHandler extends AbstractSecondaryMarketCommandHandler<IdType> {

    @Override
    public ResponseType handle(AuthData auth, IdType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        ApiValidator.assertNoError(callback);
        try {
            SellRequest sellRequest = getSecondaryMarketAppService().getSellRequest(auth.getUser(),
                    command.getId().longValue());
            InfoDataType res = createInfoData(sellRequest);
            return ResponseTypeFactory.success(res);
        } catch (SellRequestNotFoundException e) {
            throw new ObjectDoesNotExistException(ReasonCode.SELL_REQUEST_DOES_NOT_EXIST, new Value("id",
                    SELL_REQUEST_NAMESPACE, e.getId().toString()), e);
        }
    }

    private InfoDataType createInfoData(SellRequest sellRequest) {
        InfoDataType res = new InfoDataType();
        res.setId(sellRequest.getId());
        res.setDomainName(sellRequest.getDomainName());
        res.setHolder(createHolderForBuyRequest(sellRequest.getBuyRequest()));
        res.setCrDate(sellRequest.getCreationDate());
        res.setCompDate(getCompletionDateForSellRequest(sellRequest));
        res.setContact(createContact(sellRequest.getBuyRequest()));
        return res;
    }

}
