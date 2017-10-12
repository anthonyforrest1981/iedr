package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.secondarymarket.SellRequest;

public class RegisterSellRequest extends AbstractEvent {

    public RegisterSellRequest(SellRequest sellRequest) {
        super(DsmEventName.RegisterSellRequest);
        Validator.assertNotNull(sellRequest, "sellRequest");
        setParameter(SELL_REQUEST, sellRequest);
    }

}
