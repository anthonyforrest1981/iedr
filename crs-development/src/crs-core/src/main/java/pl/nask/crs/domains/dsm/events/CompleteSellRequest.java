package pl.nask.crs.domains.dsm.events;

import java.util.Date;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.dsm.DsmEventName;
import pl.nask.crs.secondarymarket.SellRequest;

public class CompleteSellRequest extends AbstractEvent {

    public CompleteSellRequest(SellRequest sellRequest, String nicHandle, Date eventDate) {
        super(DsmEventName.CompleteSellRequest);
        Validator.assertNotNull(sellRequest, "sellRequest");
        Validator.assertNotNull(nicHandle, "nicHandle");
        Validator.assertNotNull(eventDate, "eventDate");
        setParameter(SELL_REQUEST, sellRequest);
        setParameter(NEW_ADMIN_C, nicHandle);
        setParameter(EVENT_DATE, eventDate);
    }
}
