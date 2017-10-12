package pl.nask.crs.domains.dsm;

import java.util.Arrays;
import java.util.List;

import pl.nask.crs.commons.search.SearchCriteria;
import pl.nask.crs.domains.*;

public class DsmStateSearchCriteria implements SearchCriteria<DsmState> {

    private Integer id;
    private List<DomainHolderType> holderTypes;
    private List<RenewalMode> renewalModes;
    private Boolean locked;
    private List<CustomerType> customerTypes;
    private List<NRPStatus> nrpStatuses;
    private List<SecondaryMarketStatus> secondaryMarketStatuses;
    private Boolean published;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<DomainHolderType> getHolderTypes() {
        return holderTypes;
    }

    public void setHolderTypes(DomainHolderType... holderTypes) {
        this.holderTypes = Arrays.asList(holderTypes);
    }

    public List<RenewalMode> getRenewalModes() {
        return renewalModes;
    }

    public void setRenewalModes(RenewalMode... renewalModes) {
        this.renewalModes = Arrays.asList(renewalModes);
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public List<CustomerType> getCustomerTypes() {
        return customerTypes;
    }

    public void setCustomerTypes(CustomerType... customerTypes) {
        this.customerTypes = Arrays.asList(customerTypes);
    }

    public List<NRPStatus> getNrpStatuses() {
        return nrpStatuses;
    }

    public void setNrpStatuses(NRPStatus... nrpStatuses) {
        this.nrpStatuses = Arrays.asList(nrpStatuses);
    }

    public List<SecondaryMarketStatus> getSecondaryMarketStatuses() {
        return secondaryMarketStatuses;
    }

    public void setSecondaryMarketStatuses(SecondaryMarketStatus... secondaryMarketStatuses) {
        this.secondaryMarketStatuses = Arrays.asList(secondaryMarketStatuses);
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

}
