package pl.nask.crs.domains;

public enum SecondaryMarketStatus {
    NoProcess("NP", "No Process"), BuyRequestRegistered("BR", "Buy Request Registered"),
    SellRequestRegistered("SR", "Sell Request Registered"), NA("N/A", "N/A");

    private final String code;
    private final String description;

    SecondaryMarketStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static SecondaryMarketStatus forCode(String code) {
        if (code == null) {
            return NA;
        }
        for (SecondaryMarketStatus t : SecondaryMarketStatus.values()) {
            if (t.getCode().equals(code)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unsupported code for HolderType: " + code);
    }

}
