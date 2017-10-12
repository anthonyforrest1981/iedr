package pl.nask.crs.domains.dsm;

public enum DsmEventName {
    BulkTransfer,
    CancelLastBuyRequest,
    CancelSellRequest,
    CompleteSellRequest,
    CreateBillableDomainRegistrar,
    CreateBillableAutorenewedDomainRegistrar,
    CreateBillableDomainDirect,
    CreateCharityDomainRegistrar,
    CreateCharityDomainDirect,
    DeletedDomainRemoval,
    DeletionDatePasses,
    EnterVoluntaryNRP,
    Lock,
    PaymentCancelled,
    PaymentInitiated,
    PaymentSettled,
    RegisterBuyRequest,
    RegisterSellRequest,
    RemoveFromVoluntaryNRP,
    RenewalDatePasses,
    SetAutoRenew,
    SetBillable,
    SetCharity,
    SetIEDRPublished,
    SetIEDRUnpublished,
    SetNoAutoRenew,
    SetNonBillable,
    SetOnceAutoRenew,
    SuspensionDatePasses,
    TransferCancellation,
    TransferRequest,
    TransferToDirect,
    TransferToRegistrar,
    TransferWithAutorenewToRegistrar,
    Unlock;

    public static DsmEventName forName(String name) {
        for (DsmEventName eventName : DsmEventName.values()) {
            if (eventName.name().equalsIgnoreCase(name)) {
                return eventName;
            }
        }
        throw new IllegalArgumentException(name);
    }
}
