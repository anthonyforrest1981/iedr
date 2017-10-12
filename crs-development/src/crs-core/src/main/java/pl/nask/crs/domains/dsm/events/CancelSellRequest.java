package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.secondarymarket.SellRequest;

public class CancelSellRequest extends AbstractEvent {
    public CancelSellRequest(SellRequest sellRequest, int countdownPeriod) {
        super(DsmEventName.CancelSellRequest);
        Validator.assertNotNull(sellRequest, "sellRequest");
        setParameter(SELL_REQUEST, sellRequest);
        setParameter(COUNTDOWN_PERIOD, String.valueOf(countdownPeriod));
    }
}
