package pl.nask.crs.iedrapi.impl.secondarymarket;

import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.CommandFailed;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.exceptions.ObjectStatusProhibitsOperationException;
import pl.nask.crs.iedrapi.exceptions.ReasonCode;
import pl.nask.crs.secondarymarket.exceptions.BuyRequestCheckedOutException;
import pl.nask.crs.secondarymarket.exceptions.BuyRequestFrozenAsPassed;
import pl.nask.crs.secondarymarket.exceptions.BuyRequestNotFoundException;
import pl.nask.crs.security.authentication.AccessDeniedException;
import pl.nask.crs.security.authentication.AuthenticatedUser;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_registranttransferbuyrequest_1.IdType;

public class BuyRequestDeleteCommandHandler extends AbstractSecondaryMarketCommandHandler<IdType> {

    @Override
    public ResponseType handle(AuthData auth, IdType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        ApiValidator.assertNoError(callback);

        AuthenticatedUser user = auth.getUser();
        long buyRequestId = command.getId().longValue();
        try {
            getSecondaryMarketAppService().cancelBuyRequest(user, buyRequestId);
            return ResponseTypeFactory.success();
        } catch (BuyRequestNotFoundException e) {
            throw handleBuyRequestNotFound(e.getBuyRequestId());
        } catch (BuyRequestFrozenAsPassed e) {
            throw new ObjectStatusProhibitsOperationException(ReasonCode.BUY_REQUEST_ALREADY_ACCEPPTED);
        } catch (BuyRequestCheckedOutException e) {
            throw new ObjectStatusProhibitsOperationException(ReasonCode.BUY_REQUEST_IN_USE);
        } catch (Exception e) {
            throw new CommandFailed(e);
        }
    }

}
