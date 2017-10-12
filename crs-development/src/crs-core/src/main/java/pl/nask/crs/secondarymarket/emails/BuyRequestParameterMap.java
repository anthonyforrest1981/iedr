package pl.nask.crs.secondarymarket.emails;

import java.util.Arrays;
import java.util.List;

import pl.nask.crs.commons.email.service.EmailParametersUtils;
import pl.nask.crs.commons.email.service.ParameterMap;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.domains.Domain;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.secondarymarket.BuyRequest;

class BuyRequestParameterMap implements ParameterMap {

    private BuyRequest buyRequest;
    private Domain domain;
    private NicHandle nicHandle;

    BuyRequestParameterMap(BuyRequest buyRequest, Domain domain) {
        this.buyRequest = buyRequest;
        this.domain = domain;
    }

    BuyRequestParameterMap(BuyRequest buyRequest, Domain domain, NicHandle nicHandle) {
        this.buyRequest = buyRequest;
        this.domain = domain;
        this.nicHandle = nicHandle;
    }

    public List<ParameterNameEnum> getParameterNames() {
        return Arrays.asList(ParameterNameEnum.DOMAIN, ParameterNameEnum.HOLDER, ParameterNameEnum.BUYER_EMAIL,
                ParameterNameEnum.BUYER_NAME, ParameterNameEnum.REQUEST_ID, ParameterNameEnum.BUY_REQUEST_AUTHCODE,
                ParameterNameEnum.ADMIN_C_EMAIL, ParameterNameEnum.BILL_C_EMAIL, ParameterNameEnum.NIC_EMAIL,
                ParameterNameEnum.REMARK);
    }

    @Override
    public String getParameterValue(ParameterNameEnum parameterName) {
        switch (parameterName) {
            case DOMAIN:
                return buyRequest.getDomainName();
            case HOLDER:
                return buyRequest.getDomainHolder();
            case BUYER_EMAIL:
                return buyRequest.getAdminEmail();
            case BUYER_NAME:
                return buyRequest.getAdminName();
            case REQUEST_ID:
                return String.valueOf(buyRequest.getId());
            case BUY_REQUEST_AUTHCODE:
                return buyRequest.getAuthcode();
            case ADMIN_C_EMAIL:
                return EmailParametersUtils.getAdminContactEmails(domain.getAdminContacts());
            case BILL_C_EMAIL:
                return domain.getBillingContact().getEmail();
            case NIC_EMAIL:
                return nicHandle == null ? null : nicHandle.getEmail();
            case REMARK:
                return buyRequest.getHostmasterRemark();
            default:
                return null;
        }
    }

}
