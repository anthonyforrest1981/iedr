package pl.nask.crs.secondarymarket.emails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.secondarymarket.BuyRequest;

public class BuyRequestExpirationEmailParameters implements EmailParameters {

    private final BuyRequest buyRequest;
    private final int daysPassed;
    private final int daysRemaining;
    private final String username;
    private final BuyRequestParameterMap buyRequestParameterMap;

    public BuyRequestExpirationEmailParameters(Domain domain, BuyRequest buyRequest, Date startDate,
            int expirationPeriod, String username) {
        this(domain, buyRequest, null, startDate, expirationPeriod, username);
    }

    public BuyRequestExpirationEmailParameters(Domain domain, BuyRequest buyRequest, NicHandle nicHandle,
            Date startDate, int expirationPeriod, String username) {
        this.buyRequest = buyRequest;
        this.daysPassed = DateUtils.diffInDays(startDate, new Date());
        this.daysRemaining = expirationPeriod - this.daysPassed;
        this.username = username;
        buyRequestParameterMap = new BuyRequestParameterMap(buyRequest, domain, nicHandle);
    }

    @Override
    public List<? extends ParameterName> getParameterNames() {
        List<ParameterNameEnum> parameterNames = new ArrayList<>();
        parameterNames.addAll(buyRequestParameterMap.getParameterNames());
        parameterNames.addAll(Arrays.asList(ParameterNameEnum.BILL_C_EMAIL, ParameterNameEnum.DAYS_PASSED,
                ParameterNameEnum.DAYS_REMAINING_WITH_DAYS_SUFFIX));
        return parameterNames;
    }

    @Override
    public String getParameterValue(String name, boolean html) {
        ParameterNameEnum parameterName = ParameterNameEnum.forName(name);
        switch (parameterName) {
            case DAYS_PASSED:
                return String.valueOf(this.daysPassed);
            case DAYS_REMAINING_WITH_DAYS_SUFFIX:
                return String.valueOf(this.daysRemaining) + (this.daysRemaining == 1 ? " day" : " days");
            default:
                return buyRequestParameterMap.getParameterValue(parameterName);
        }
    }

    @Override
    public String getLoggedInNicHandle() {
        return username;
    }

    @Override
    public String getAccountRelatedNicHandle(boolean gaining) {
        return buyRequest.getCreatorNH();
    }

    @Override
    public String getDomainName() {
        return buyRequest.getDomainName();
    }
}
