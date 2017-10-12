package pl.nask.crs.secondarymarket.emails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.secondarymarket.BuyRequest;

public class SecondaryMarketRequestEmailParameters implements EmailParameters {

    private Domain domain;
    private String username;
    private int period;
    private BuyRequestParameterMap buyRequestParameterMap;

    public SecondaryMarketRequestEmailParameters(Domain domain, BuyRequest buyRequest, String username) {
        this(domain, buyRequest, null, username);
    }

    public SecondaryMarketRequestEmailParameters(Domain domain, BuyRequest buyRequest, NicHandle nicHandle,
            String username) {
        this.domain = domain;
        this.username = username;
        buyRequestParameterMap = new BuyRequestParameterMap(buyRequest, domain, nicHandle);
    }

    public SecondaryMarketRequestEmailParameters(Domain domain, BuyRequest buyRequest, int period, String username) {
        this(domain, buyRequest, username);
        this.period = period;
    }

    @Override
    public List<? extends ParameterName> getParameterNames() {
        List<ParameterNameEnum> parameterNames = new ArrayList<>();
        parameterNames.addAll(buyRequestParameterMap.getParameterNames());
        parameterNames.addAll(Arrays.asList(ParameterNameEnum.COUNTDOWN_PERIOD));
        return parameterNames;
    }

    @Override
    public String getParameterValue(String name, boolean html) {
        ParameterNameEnum parameterName = ParameterNameEnum.forName(name);
        switch (parameterName) {
            case COUNTDOWN_PERIOD:
                return String.valueOf(period);
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
        // TODO: CRS-1285 - This email parameters class should probably split in two, one for buyer, one for seller.
        // This is to avoid the problem of email disabler not being able to disable email delivery for one side only.
        return null;
    }

    @Override
    public String getDomainName() {
        return domain.getName();
    }
}
