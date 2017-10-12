package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.secondarymarket.BuyRequest;

public class CancelLastBuyRequest extends AbstractEvent {
    public CancelLastBuyRequest(BuyRequest buyRequest) {
        super(DsmEventName.CancelLastBuyRequest);
        Validator.assertNotNull(buyRequest, "buyRequest");
        setParameter(BUY_REQUEST, buyRequest);
    }
}
