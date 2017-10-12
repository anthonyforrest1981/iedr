package pl.nask.crs.contacts;

public enum ContactType {
    ADMIN("Admin"), TECH("Tech"), BILLING("Billing"), CREATOR("Creator");

    private final String code;

    ContactType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ContactType forCode(String code) {
        for (ContactType t : ContactType.values()) {
            if (t.getCode().equals(code)) {
                return t;
            }
        }
        throw new IllegalArgumentException(String.format("\"%s\" value is not a valid contact type", code));
    }

}
