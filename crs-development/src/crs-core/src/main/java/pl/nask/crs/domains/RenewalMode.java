package pl.nask.crs.domains;

public enum RenewalMode {
    NoAutorenew("N", "No auto renew"), RenewOnce("R", "Renew Once"), Autorenew("A", "Autorenew"), NA("N/A", "N/A");

    private final String description;
    private final String code;

    RenewalMode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static RenewalMode forCode(String code) {
        if (code == null) {
            return NA;
        }
        for (RenewalMode t : RenewalMode.values()) {
            if (t.getCode().equals(code)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unsupported code for RenewalMode: " + code);
    }

    public static RenewalMode forName(String n) {
        for (RenewalMode c : RenewalMode.values()) {
            if (c.name().equalsIgnoreCase(n))
                return c;
        }
        throw new IllegalArgumentException(n);
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
