package pl.nask.crs.accounts;

public enum AccountStatus {

    Active("Active"), Suspended("Suspended"), Deleted("Deleted");

    private final String code;

    private AccountStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static AccountStatus forCode(String code) {
        if ("Active".equals(code)) {
            return Active;
        } else if ("Suspended".equals(code)) {
            return Suspended;
        } else if ("Deleted".equals(code)) {
            return Deleted;
        } else {
            throw new IllegalArgumentException("Unsupported code for AccountStatus: " + code);
        }
    }
}
