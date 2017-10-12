package pl.nask.crs.payment;

import pl.nask.crs.commons.search.SearchCriteria;

public class DepositSearchCriteria extends AbstractDepositSearchCriteria implements SearchCriteria<Deposit> {

    public DepositSearchCriteria() {
        super();
    }

    public DepositSearchCriteria(String nicHandleId, String accountBillNH) {
        super(nicHandleId, accountBillNH);
    }

}
