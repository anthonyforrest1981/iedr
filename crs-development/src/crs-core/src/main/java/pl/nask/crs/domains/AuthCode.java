package pl.nask.crs.domains;

import java.util.Date;

public class AuthCode {

    private final String authCode;
    private final Date validUntil;

    public AuthCode(String authCode, Date validUntil) {
        this.authCode = authCode;
        this.validUntil = validUntil;
    }

    public String getAuthCode() {
        return authCode;
    }

    public Date getValidUntil() {
        return validUntil;
    }
}
