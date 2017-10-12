package pl.nask.crs.iedrapi.impl.secondarymarket;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.iedrapi.*;
import pl.nask.crs.iedrapi.exceptions.CommandFailed;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.impl.TypeConverter;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.secondarymarket.SellRequest;
import pl.nask.crs.secondarymarket.search.SellRequestSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_registranttransfersellrequest_1.QueryType;
import ie.domainregistry.ieapi_registranttransfersellrequest_1.ResDataType;

public class SellRequestQueryCommandHandler extends AbstractSecondaryMarketCommandHandler<QueryType> {

    @Override
    public ResponseType handle(AuthData auth, QueryType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        ApiValidator.assertNoError(callback);
        try {
            SellRequestSearchCriteria criteria = new SellRequestSearchCriteria();
            int page = command.getPage();
            long offset = TypeConverter.pageToOffset(page);
            LimitedSearchResult<SellRequest> results;
            results = getSecondaryMarketAppService().findOwnSellRequests(auth.getUser(),
                    criteria, offset, IedrApiConfig.getPageSize(), null);
            ResDataType res = new ResDataType();
            res.setPage(page);
            res.setTotalPages(TypeConverter.totalResultsToPages(results.getTotalResults()));
            for (SellRequest request : results.getResults()) {
                res.getRequest().add(prepareSellRequestTypeResponse(request));
            }
            return ResponseTypeFactory.success(res);
        } catch (NicHandleNotFoundException e) {
            throw new CommandFailed(e);
        }
    }

}
