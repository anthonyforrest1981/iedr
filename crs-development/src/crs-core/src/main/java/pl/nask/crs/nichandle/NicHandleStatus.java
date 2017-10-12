package pl.nask.crs.nichandle;

public enum NicHandleStatus {
    Active("Active"), Suspended("Suspended"), Deleted("Deleted");

    private final String code;

    NicHandleStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static NicHandleStatus forCode(String code) {
        for (NicHandleStatus t : NicHandleStatus.values()) {
            if (t.getCode().equals(code)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unsupported code for NicHandleStatus: " + code);
    }
}
