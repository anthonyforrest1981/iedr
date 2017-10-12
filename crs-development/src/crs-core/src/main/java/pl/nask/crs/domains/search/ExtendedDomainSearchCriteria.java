package pl.nask.crs.domains.search;

import pl.nask.crs.domains.ExtendedDomain;

public class ExtendedDomainSearchCriteria extends AbstractDomainSearchCriteria<ExtendedDomain> {

    private Boolean currentRenewal;
    private Boolean futureRenewal;

    public Boolean getCurrentRenewal() {
        return currentRenewal;
    }

    public void setCurrentRenewal(Boolean currentRenewal) {
        this.currentRenewal = currentRenewal;
    }

    public Boolean getFutureRenewal() {
        return futureRenewal;
    }

    public void setFutureRenewal(Boolean futureRenewal) {
        this.futureRenewal = futureRenewal;
    }

}
