package pl.nask.crs.iedrapi.impl.secondarymarket;

import java.util.List;

import pl.nask.crs.commons.search.LimitedSearchResult;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.iedrapi.*;
import pl.nask.crs.iedrapi.exceptions.CommandFailed;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.impl.TypeConverter;
import pl.nask.crs.nichandle.exception.NicHandleNotFoundException;
import pl.nask.crs.secondarymarket.BuyRequest;
import pl.nask.crs.secondarymarket.search.BuyRequestSearchCriteria;
import pl.nask.crs.security.authentication.AccessDeniedException;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_registranttransferbuyrequest_1.QueryType;
import ie.domainregistry.ieapi_registranttransferbuyrequest_1.RequestType;
import ie.domainregistry.ieapi_registranttransferbuyrequest_1.ResDataType;

public class BuyRequestQueryCommandHandler extends AbstractSecondaryMarketCommandHandler<QueryType> {

    @Override
    public ResponseType handle(AuthData auth, QueryType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        ApiValidator.assertNoError(callback);

        BuyRequestSearchCriteria criteria = new BuyRequestSearchCriteria();
        if (!Validator.isEmpty(command.getDomainName())) {
            criteria.setDomainName(command.getDomainName());
        }
        long offset = TypeConverter.pageToOffset(command.getPage());
        long limit = IedrApiConfig.getPageSize();
        try {
            LimitedSearchResult<BuyRequest> results =
                    getSecondaryMarketAppService().findOwnBuyRequests(auth.getUser(), criteria, offset, limit, null);
            return ResponseTypeFactory.success(prepareResponse(results, command.getPage()));
        } catch (NicHandleNotFoundException e) {
            throw new CommandFailed(e);
        }
    }

    private ResDataType prepareResponse(LimitedSearchResult<BuyRequest> results, int page) {
        ResDataType response = new ResDataType();
        response.setPage(page);
        response.setTotalPages(TypeConverter.totalResultsToPages(results.getTotalResults()));
        List<RequestType> requests = response.getRequest();
        for (BuyRequest request : results.getResults()) {
            requests.add(prepareBuyRequestTypeResponse(request));
        }
        return response;
    }

}
