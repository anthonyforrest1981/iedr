package pl.nask.crs.secondarymarket.emails;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.nichandle.NicHandle;

public class SecondaryMarketPaymentEmailParameters implements EmailParameters {

    private final Domain domain;
    private final long requestId;
    private final BigDecimal transactionValue;
    private final NicHandle creator;
    private String username;

    public SecondaryMarketPaymentEmailParameters(String username, Domain domain, long requestId,
            BigDecimal transactionValue, NicHandle creator) {
        this.domain = domain;
        this.requestId = requestId;
        this.transactionValue = MoneyUtils.getRoundedAndScaledValue(transactionValue);
        this.creator = creator;
        this.username = username;
    }

    @Override
    public List<? extends ParameterName> getParameterNames() {
        return Arrays.<ParameterName>asList(ParameterNameEnum.NIC_EMAIL, ParameterNameEnum.BILL_C_EMAIL,
                ParameterNameEnum.REQUEST_ID, ParameterNameEnum.DOMAIN, ParameterNameEnum.TRANSACTION_VALUE);
    }

    @Override
    public String getParameterValue(String name, boolean html) {
        ParameterNameEnum params = ParameterNameEnum.forName(name);
        switch (params) {
            case NIC_EMAIL:
                return creator.getEmail();
            case BILL_C_EMAIL:
                return domain.getBillingContact().getEmail();
            case REQUEST_ID:
                return String.valueOf(requestId);
            case TRANSACTION_VALUE:
                return MoneyUtils.getRoundedAndScaledValue(transactionValue, 2).toString();
            case DOMAIN:
                return domain.getName();
            default:
                return null;
        }
    }

    @Override
    public String getLoggedInNicHandle() {
        return this.username;
    }

    @Override
    public String getAccountRelatedNicHandle(boolean gaining) {
        return creator.getNicHandleId();
    }

    @Override
    public String getDomainName() {
        return domain.getName();
    }

}
