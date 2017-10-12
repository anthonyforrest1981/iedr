package pl.nask.crs.domains;

public enum CustomerType {
    Registrar("R", "Registrar"), Direct("D", "Direct"), NA("N/A", "N/A");

    private final String code;
    private final String description;

    CustomerType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static CustomerType forCode(String code) {
        if (code == null) {
            return NA;
        }
        for (CustomerType t : CustomerType.values()) {
            if (t.getCode().equals(code)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unsupported code for CustomerType: " + code);
    }

    public static CustomerType forName(String name) {
        if (name == null) {
            return NA;
        }
        for (CustomerType t : CustomerType.values()) {
            if (t.name().equals(name)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unsupported name for CustomerType: " + name);
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
