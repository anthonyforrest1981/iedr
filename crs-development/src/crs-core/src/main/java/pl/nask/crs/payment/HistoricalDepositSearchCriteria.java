package pl.nask.crs.payment;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.history.HistoricalObject;

public class HistoricalDepositSearchCriteria extends AbstractDepositSearchCriteria
        implements SearchCriteria<HistoricalObject<Deposit>> {

    public HistoricalDepositSearchCriteria() {
        super();
    }

    public HistoricalDepositSearchCriteria(String nicHandleId, String accountBillNH) {
        super(nicHandleId, accountBillNH);
    }

}
