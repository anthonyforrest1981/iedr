package pl.nask.crs.payment.email;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.email.service.EmailParameters;
import pl.nask.crs.commons.email.service.ParameterName;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.payment.TransactionDetails;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class PaymentEmailParameters implements EmailParameters {

    private final String orderId;
    private final BigDecimal transactionValue;
    private final NicHandle billingContact;
    private final String domainName;
    private final TransactionDetails transactionDetails;
    private String username;

    public PaymentEmailParameters(String username, String orderId, BigDecimal transactionValue,
            NicHandle billingContact, String domainName, TransactionDetails transactionDetails) {
        this.orderId = orderId;
        this.transactionValue = MoneyUtils.getRoundedAndScaledValue(transactionValue);
        this.billingContact = billingContact;
        this.domainName = domainName;
        this.transactionDetails = transactionDetails;
        this.username = username;
    }

    public String getLoggedInNicHandle() {
        return this.username;
    }

    public String getAccountRelatedNicHandle(boolean gaining) {
        return this.billingContact.getNicHandleId();
    }

    public String getDomainName() {
        return this.domainName;
    }

    @Override
    public List<ParameterName> getParameterNames() {
        return Arrays.<ParameterName>asList(ParameterNameEnum.BILL_C_NAME, ParameterNameEnum.BILL_C_CO_NAME,
                ParameterNameEnum.BILL_C_NIC, ParameterNameEnum.BILL_C_EMAIL, ParameterNameEnum.ORDER_ID,
                ParameterNameEnum.GAINING_BILL_C_EMAIL, ParameterNameEnum.GAINING_BILL_C_NAME,
                ParameterNameEnum.DOMAIN, ParameterNameEnum.TRANSACTION_VALUE, ParameterNameEnum.TRANSACTION_DETAIL);
    }

    @Override
    public String getParameterValue(String name, boolean html) {
        ParameterNameEnum params = ParameterNameEnum.forName(name);
        switch (params) {
            case ORDER_ID:
                return orderId;
            case BILL_C_EMAIL:
            case GAINING_BILL_C_EMAIL:
                return billingContact.getEmail();
            case BILL_C_CO_NAME:
                return billingContact.getCompanyName();
            case BILL_C_NAME:
            case GAINING_BILL_C_NAME:
                return billingContact.getName();
            case BILL_C_NIC:
                return billingContact.getNicHandleId();
            case TRANSACTION_VALUE:
                return MoneyUtils.getRoundedAndScaledValue(transactionValue, 2).toString();
            case DOMAIN:
                return domainName;
            case TRANSACTION_DETAIL:
                if (transactionDetails == null) {
                    return null;
                }
                return html ? transactionDetails.toHtmlString() : transactionDetails.toString();
            default:
                return null;
        }
    }
}
