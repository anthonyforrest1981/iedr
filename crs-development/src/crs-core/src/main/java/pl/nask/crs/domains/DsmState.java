package pl.nask.crs.domains;

public class DsmState {

    private static final DsmState INITIAL_DSM_STATE = new DsmStateStub(0);

    private int id;

    private DomainHolderType holderType;

    private RenewalMode renewalMode;

    private Boolean locked;

    private CustomerType customerType;

    private NRPStatus nrpStatus;

    private SecondaryMarketStatus secondaryMarketStatus;

    private Boolean published;

    public static DsmState initialState() {
        return INITIAL_DSM_STATE;
    }

    public DsmState(int id) {
        this.id = id;
    }

    public DsmState() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DomainHolderType getDomainHolderType() {
        return holderType != null ? holderType : DomainHolderType.NA;
    }

    public void setDomainHolderType(DomainHolderType type) {
        this.holderType = type;
    }

    public RenewalMode getRenewalMode() {
        return renewalMode != null ? renewalMode : RenewalMode.NA;
    }

    public void setRenewalMode(RenewalMode mode) {
        this.renewalMode = mode;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public CustomerType getCustomerType() {
        return customerType != null ? customerType : CustomerType.NA;
    }

    public void setCustomerType(CustomerType type) {
        this.customerType = type;
    }

    public NRPStatus getNrpStatus() {
        return nrpStatus != null ? nrpStatus : NRPStatus.NA;
    }

    public void setNrpStatus(NRPStatus status) {
        this.nrpStatus = status;
    }

    public SecondaryMarketStatus getSecondaryMarketStatus() {
        return secondaryMarketStatus != null ? secondaryMarketStatus : SecondaryMarketStatus.NA;
    }

    public void setSecondaryMarketStatus(SecondaryMarketStatus status) {
        this.secondaryMarketStatus = status;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public boolean isValid() {
        return true;
    }

    @Override
    public String toString() {
        return "DsmState [id=" + id + ", holderType=" + holderType + ", renewalMode=" + renewalMode
                + ", locked=" + locked + ", customerType=" + customerType + ", nrpStatus=" + nrpStatus
                + ", secondaryMarketStatus=" + secondaryMarketStatus + ", published=" + published + "]";
    }

}
