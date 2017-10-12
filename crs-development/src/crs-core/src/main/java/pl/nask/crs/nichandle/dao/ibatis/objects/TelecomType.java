package pl.nask.crs.nichandle.dao.ibatis.objects;

import org.apache.log4j.Logger;

public enum TelecomType {
    PHONE("Phone"), FAX("Fax");

    private final String code;

    TelecomType(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static TelecomType forCode(final String code) {
        for (TelecomType t : TelecomType.values()) {
            if (t.getCode().equals(code))
                return t;
        }
        Logger.getLogger(Telecom.class).error("Unknown telecom type " + code + ". Assuming phone.");
        return PHONE;
    }

}
