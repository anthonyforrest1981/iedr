package pl.nask.crs.domains.dsm.events;

import pl.nask.crs.domains.dsm.DsmEventName;

public class Lock extends AbstractEvent {
    public Lock() {
        super(DsmEventName.Lock);
    }
}
