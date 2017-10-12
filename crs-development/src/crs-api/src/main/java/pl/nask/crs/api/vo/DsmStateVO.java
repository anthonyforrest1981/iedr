package pl.nask.crs.api.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.domains.*;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class DsmStateVO {
    private Boolean locked;

    private CustomerType customerType;

    private NRPStatus nrpStatus;

    private ShortNRPStatus shortNrpStatus;

    private RenewalMode renewalMode;

    private DomainHolderType holderType;

    private Boolean nrp;

    private Boolean voluntaryNRP;

    private Boolean removeFromVoluntaryNRPPossible;

    private Boolean enterVoluntaryNRPPossible;

    private SecondaryMarketStatus secondaryMarketStatus;

    DsmStateVO() {}

    public DsmStateVO(DsmState dsmState) {
        this(dsmState, true);
    }

    public DsmStateVO(DsmState dsmState, boolean allData) {
        if (dsmState == null) {
            dsmState = DsmState.initialState();
        }
        shortNrpStatus = ShortNRPStatus.fromNRPStatus(dsmState.getNrpStatus());
        nrpStatus = dsmState.getNrpStatus();
        locked = dsmState.getLocked();
        customerType = dsmState.getCustomerType();
        renewalMode = dsmState.getRenewalMode();
        holderType = dsmState.getDomainHolderType();
        voluntaryNRP = dsmState.getNrpStatus().isVoluntaryNRP();
        nrp = dsmState.getNrpStatus().isNRP();
        secondaryMarketStatus = dsmState.getSecondaryMarketStatus();
    }

    public Boolean getLocked() {
        return locked;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public NRPStatus getNrpStatus() {
        return nrpStatus;
    }

    public ShortNRPStatus getShortNrpStatus() {
        return shortNrpStatus;
    }

    public RenewalMode getRenewalMode() {
        return renewalMode;
    }

    public DomainHolderType getHolderType() {
        return holderType;
    }

    public boolean isVoluntaryNRP() {
        return voluntaryNRP;
    }

    public void setRemoveFromVoluntaryNRPPossible(boolean flag) {
        this.removeFromVoluntaryNRPPossible = flag;
    }

    public boolean isRemoveFromVoluntaryNRPPossible() {
        return removeFromVoluntaryNRPPossible;
    }

    public boolean isEnterVoluntaryNRPPossible() {
        return enterVoluntaryNRPPossible;
    }

    public void setEnterVoluntaryNRPPossible(boolean enterVoluntaryNRPPossible) {
        this.enterVoluntaryNRPPossible = enterVoluntaryNRPPossible;
    }

    public boolean isNrp() {
        return nrp;
    }

    public SecondaryMarketStatus getSecondaryMarketStatus() {
        return secondaryMarketStatus;
    }

    public void setSecondaryMarketStatus(SecondaryMarketStatus secondaryMarketStatus) {
        this.secondaryMarketStatus = secondaryMarketStatus;
    }
}
