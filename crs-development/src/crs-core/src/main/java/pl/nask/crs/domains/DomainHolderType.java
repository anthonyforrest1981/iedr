package pl.nask.crs.domains;

public enum DomainHolderType {
    Billable("B", "Billable"), Charity("C", "Charity"), IEDRUnpublished("IU", "IEDR-Unpublished"), IEDRPublished(
            "IP",
            "IEDR-Published"), NonBillable("N", "Non-Billable"), NA("N/A", "N/A");

    private final String code;
    private final String description;

    DomainHolderType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static DomainHolderType forCode(String code) {
        if (code == null) {
            return NA;
        }
        for (DomainHolderType t : DomainHolderType.values()) {
            if (t.getCode().equals(code)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unsupported code for HolderType: " + code);
    }

}
