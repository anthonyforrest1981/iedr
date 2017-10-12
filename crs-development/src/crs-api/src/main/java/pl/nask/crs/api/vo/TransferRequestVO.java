package pl.nask.crs.api.vo;

import pl.nask.crs.commons.Period;
import pl.nask.crs.ticket.AdminStatus;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TransferRequestVO extends AbstractTicketRequestVO {

    private static final String CHARITY_MARKER = "charityMarker";

    private String authCode;

    private boolean charity = false;

    public void setCharity(boolean charity) {
        this.charity = charity;
    }

    @Override
    public boolean isCharity() {
        return charity;
    }

    @Override
    public String getCharityCode() {
        return charity ? CHARITY_MARKER : null;
    }

    @Override
    public Period getRegPeriod() {
        return charity ? null : super.getRegPeriod();
    }

    @Override
    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }


    public AdminStatus getDefaultAdminStatus() {
        return AdminStatus.PASSED;
    }
}
