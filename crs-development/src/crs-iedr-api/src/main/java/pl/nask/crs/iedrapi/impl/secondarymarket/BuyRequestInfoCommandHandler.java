package pl.nask.crs.iedrapi.impl.secondarymarket;

import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.exceptions.BuyRequestNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_registranttransferbuyrequest_1.IdType;
import ie.domainregistry.ieapi_registranttransferbuyrequest_1.InfoDataType;

public class BuyRequestInfoCommandHandler extends AbstractSecondaryMarketCommandHandler<IdType> {

    @Override
    public ResponseType handle(AuthData auth, IdType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        ApiValidator.assertNoError(callback);

        long buyRequestId = command.getId().longValue();
        try {
            return ResponseTypeFactory.success(prepareBuyRequestInfoDataTypeResponse(auth.getUser(), buyRequestId));
        } catch (BuyRequestNotFoundException e) {
            throw handleBuyRequestNotFound(e.getBuyRequestId());
        }
    }

    private InfoDataType prepareBuyRequestInfoDataTypeResponse(AuthenticatedUser user, long buyRequestId)
            throws AccessDeniedException, BuyRequestNotFoundException {
        BuyRequest buyRequest = getHelper().getSecondaryMarketAppService().getBuyRequest(user, buyRequestId);
        InfoDataType result = new InfoDataType();
        result.setId(buyRequest.getId());
        result.setDomainName(buyRequest.getDomainName());
        result.setHolder(createHolderForBuyRequest(buyRequest));
        result.setCrDate(buyRequest.getCreationDate());
        result.setContact(createContact(buyRequest));
        result.setStatus(buyRequest.getStatus().getDescription());
        return result;
    }

}
