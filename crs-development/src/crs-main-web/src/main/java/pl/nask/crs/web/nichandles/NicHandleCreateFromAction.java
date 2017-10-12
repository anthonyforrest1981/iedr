package pl.nask.crs.web.nichandles;

import pl.nask.crs.accounts.services.AccountSearchService;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.nichandles.wrappers.NicHandleWrapper;
import pl.nask.crs.country.CountryFactory;

public class NicHandleCreateFromAction extends NicHandleCreateAction {

    public NicHandleCreateFromAction(NicHandleAppService nicHandleAppService,
            AccountSearchService accountSearchService, CountryFactory countryFactory) {
        super(nicHandleAppService, accountSearchService, countryFactory);
    }

    @Override
    public String input() throws Exception {
        setNicHandleWrapper(new NicHandleWrapper(getNicHandleAppService().get(getUser(), getNicHandleId())));
        return INPUT;
    }

}
